package com.dreamer8.yosimce.server.hibernate.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.Curso;
import com.dreamer8.yosimce.server.hibernate.pojo.UsuarioTipo;
import com.dreamer8.yosimce.server.utils.SecurityFilter;
import com.dreamer8.yosimce.shared.dto.DetalleCursoDTO;

public class CursoDAO extends AbstractHibernateDAO<Curso, Integer> {
	public List<Curso> findByByActividadANDRbd(Integer idAplicacion,
			Integer idNivel, Integer idActividadTipo, Integer idUsuario,
			String usuarioTipo, String rbd) {

		List<Curso> ca = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT DISTINCT c.* FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id AND axnxat.actividad_tipo_id="
				+ SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id"
				+ " JOIN CURSO c ON a.curso_id=c.id"
				+ " JOIN ESTABLECIMIENTO e ON c.establecimiento_id=e.id";
		if (usuarioTipo.equals(UsuarioTipo.JEFE_REGIONAL)
				|| usuarioTipo.equals(UsuarioTipo.JEFE_ZONAL)
				|| usuarioTipo.equals(UsuarioTipo.JEFE_CENTRO_OPERACIONES)) {
			query += " JOIN CO_x_ESTABLECIMIENTO coxe ON e.id=coxe.establecimiento_id";
			if (usuarioTipo.equals(UsuarioTipo.JEFE_CENTRO_OPERACIONES)) {
				query += " JOIN JO_x_CO joxco ON (coxe.co_id=joxco.co_id AND joxco.jo_id="
						+ SecurityFilter.escapeString(idUsuario)
						+ ") AND joxco.activo=TRUE";
			} else {
				query += " JOIN CO co ON coxe.co_id=co.id";
				if (usuarioTipo.equals(UsuarioTipo.JEFE_ZONAL)) {
					query += " JOIN JZ_x_ZONA jzxz ON (co.zona_id=jzxz.zona_id AND jzxz.jz_id="
							+ SecurityFilter.escapeString(idUsuario)
							+ ") AND jzxz.activo=TRUE";
				} else {
					query += " JOIN ZONA z ON co.zona_id=z.id"
							+ " JOIN JR_x_CENTRO_REGIONAL jrxcr ON (z.centro_regional_id=jrxcr.centro_regional_id AND jrxcr.jr_id="
							+ SecurityFilter.escapeString(idUsuario)
							+ ") AND jrxcr.activo=TRUE";
				}
			}
		} else if (usuarioTipo.equals(UsuarioTipo.SUPERVISOR)
				|| usuarioTipo.equals(UsuarioTipo.SUPERVISOR_CON_AUTO)
				|| usuarioTipo.equals(UsuarioTipo.EXAMINADOR)
				|| usuarioTipo.equals(UsuarioTipo.EXAMINADOR_NEE)
				|| usuarioTipo.equals(UsuarioTipo.COORDINADOR_COMPUTACION)) {
			query += " JOIN USUARIO_x_ACTIVIDAD uxa ON a.id=uxa.actividad_id"
					+ " JOIN USUARIO_SELECCION us ON uxa.usuario_seleccion_id=us.id"
					+ " JOIN USUARIO_x_APLICACION_x_NIVEL uxaxn ON (us.usuario_x_aplicacion_x_nivel_id=uxaxn.id AND uxaxn.usuario_id="
					+ SecurityFilter.escapeString(idUsuario) + ")";
		}
		query += " WHERE e.id=" + SecurityFilter.escapeString(rbd);
		Query q = s.createSQLQuery(query).addEntity(Curso.class);
		ca = q.list();
		return ca;
	}

	public DetalleCursoDTO findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCurso(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			Integer idCurso) {

		DetalleCursoDTO dcdto = new DetalleCursoDTO();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT c.id as curso_id,c.nombre as curso_nombre,e.id as est_id,e.nombre as est_nombre,"
				+ "r.nombre as region_nombre, COMUNA.nombre as comuna_nombre,et.nombre as est_tipo,"
				+ "a.contacto_nombre,a.contacto_telefono,a.contacto_email,cc.nombre as cont_cargo  FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id AND axnxat.actividad_tipo_id="
				+ SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN ACTIVIDAD a ON (axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id AND a.curso_id="
				+ SecurityFilter.escapeString(idCurso)
				+ ")"
				+ " LEFT JOIN CONTACTO_CARGO cc ON a.contacto_cargo_id=cc.id"
				+ " JOIN CURSO c ON a.curso_id=c.id"
				+ " JOIN ESTABLECIMIENTO e ON c.establecimiento_id=e.id"
				+ " LEFT JOIN APLICACION_x_ESTABLECIMIENTO axe ON (e.id=axe.establecimiento_id AND axe.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ ")"
				+ " LEFT JOIN ESTABLECIMIENTO_TIPO et ON axe.establecimiento_tipo_id=et.id"
				+ " JOIN COMUNA ON e.comuna_id=COMUNA.id"
				+ " JOIN PROVINCIA p ON COMUNA.provincia_id=p.id"
				+ " JOIN REGION r ON p.region_id=r.id";

		Query q = s.createSQLQuery(query);
		List<Object[]> os = q.list();
		for (Object[] o : os) {
			dcdto.setId((Integer) o[0]);
			dcdto.setCurso((String) o[1]);
			dcdto.setRbd(Integer.toString((Integer) o[2]));
			dcdto.setEstablecimiento((String) o[3]);
			dcdto.setRegion((String) o[4]);
			dcdto.setComuna((String) o[5]);
			dcdto.setTipoEstablecimiento((String) o[6]);
			dcdto.setNombreContacto((String) o[7]);
			dcdto.setTelefonoContacto((String) o[8]);
			dcdto.setEmailContacto((String) o[9]);
			dcdto.setCargoContacto((String) o[10]);
			break;
		}
		return dcdto;
	}

	public Curso findByIdAplicacionANDIdNivelANIdEstablecimientoDANDNombreCurso(
			Integer idAplicacion, Integer idNivel, Integer idEstablecimiento,
			String nombreCurso) {

		Curso c = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT c.* FROM APLICACION_x_NIVEL axn "
				+ " JOIN CURSO c ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id=" + SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=c.aplicacion_x_nivel_id)"
				+ " WHERE c.establecimiento_id="
				+ SecurityFilter.escapeString(idEstablecimiento)
				+ " AND c.nombre='" + SecurityFilter.escapeString(nombreCurso)
				+ "'";
		Query q = s.createSQLQuery(query).addEntity(Curso.class);
		c = ((Curso) q.uniqueResult());
		return c;
	}

	public List<Curso> findByIdAplicacionANDIdNivelANIdEstablecimiento(
			Integer idAplicacion, Integer idNivel, Integer idEstablecimiento) {

		List<Curso> cs = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT c.* FROM APLICACION_x_NIVEL axn "
				+ " JOIN CURSO c ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id=" + SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=c.aplicacion_x_nivel_id)"
				+ " WHERE c.establecimiento_id="
				+ SecurityFilter.escapeString(idEstablecimiento);
		Query q = s.createSQLQuery(query).addEntity(Curso.class);
		cs = q.list();
		return cs;
	}
}
