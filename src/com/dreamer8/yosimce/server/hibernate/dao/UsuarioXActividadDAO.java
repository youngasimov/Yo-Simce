/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.UsuarioTipo;
import com.dreamer8.yosimce.server.hibernate.pojo.UsuarioXActividad;
import com.dreamer8.yosimce.server.utils.SecurityFilter;
import com.dreamer8.yosimce.server.utils.StringUtils;
import com.dreamer8.yosimce.shared.dto.EvaluacionSupervisorDTO;
import com.dreamer8.yosimce.shared.dto.UserDTO;

/**
 * @author jorge
 * 
 */
public class UsuarioXActividadDAO extends
		AbstractHibernateDAO<UsuarioXActividad, Integer> {

	public List<UsuarioXActividad> findExaminadoresByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCurso(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			Integer idCurso) {

		List<UsuarioXActividad> uxas = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT DISTINCT uxa.* FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id AND axnxat.actividad_tipo_id="
				+ SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN ACTIVIDAD a ON (axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id AND a.curso_id="
				+ SecurityFilter.escapeString(idCurso)
				+ " AND (a.actividad_estado_id!=7 OR a.actividad_estado_id IS NULL))"
				+ " JOIN USUARIO_x_ACTIVIDAD uxa ON a.id=uxa.actividad_id AND (uxa.asistencia != false OR uxa.asistencia IS NULL)"
				+ " JOIN USUARIO_SELECCION us ON (uxa.usuario_seleccion_id=us.id AND us.seleccion=true AND us.renuncia=false)"
				// +
				// " JOIN USUARIO_TIPO ut ON us.usuario_tipo_id=ut.id AND (ut.nombre='"
				// +
				// " JOIN USUARIO_TIPO ut ON uxa.usuario_tipo_id=ut.id AND (ut.nombre='"
				// + UsuarioTipo.EXAMINADOR + "' OR ut.nombre='"
				// + UsuarioTipo.EXAMINADOR_NEE + "' OR ut.nombre='"
				// + UsuarioTipo.EXAMINADOR_SUPLENTE + "' OR ut.nombre='"
				// + UsuarioTipo.EXAMINADOR_ASISTENTE + "')";
				+ " JOIN USUARIO_TIPO ut ON uxa.usuario_tipo_id=ut.id AND ut.rol='"
				+ UsuarioTipo.EXAMINADOR + "'";

		Query q = s.createSQLQuery(query).addEntity(UsuarioXActividad.class);
		uxas = q.list();
		return uxas;
	}

	public List<UsuarioXActividad> findSupervisoresByIdAplicacionANDIdNivelANDIdActividadTipo(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			Integer idUsuario, String usuarioTipo) {

		List<UsuarioXActividad> uxas = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT DISTINCT uxa.* FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id AND axnxat.actividad_tipo_id="
				+ SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id AND (a.actividad_estado_id!=7 OR a.actividad_estado_id IS NULL)"
				+ ""
				+ " JOIN USUARIO_x_ACTIVIDAD uxa ON a.id=uxa.actividad_id"
				+ " JOIN USUARIO_SELECCION us ON (uxa.usuario_seleccion_id=us.id AND us.seleccion=true AND us.renuncia=false)"
				// +
				// " JOIN USUARIO_TIPO ut ON us.usuario_tipo_id=ut.id AND (ut.nombre='"
				// +
				// " JOIN USUARIO_TIPO ut ON uxa.usuario_tipo_id=ut.id AND (ut.nombre='"
				// + UsuarioTipo.SUPERVISOR + "' OR ut.nombre='"
				// + UsuarioTipo.SUPERVISOR_CON_AUTO + "')"
				+ " JOIN USUARIO_TIPO ut ON uxa.usuario_tipo_id=ut.id AND ut.rol='"
				+ UsuarioTipo.SUPERVISOR + "'"
				+ " JOIN CURSO c ON a.curso_id=c.id"
				+ " JOIN ESTABLECIMIENTO e ON c.establecimiento_id=e.id";
		if (usuarioTipo.equals(UsuarioTipo.JEFE_REGIONAL)
				|| usuarioTipo.equals(UsuarioTipo.JEFE_ZONAL)
				|| usuarioTipo.equals(UsuarioTipo.JEFE_CENTRO_OPERACIONES)
				|| usuarioTipo.equals(UsuarioTipo.LOGISTICA_Y_SOPORTE)) {
			query += " JOIN CO_x_ESTABLECIMIENTO coxe ON (e.id=coxe.establecimiento_id  AND axn.id=coxe.aplicacion_x_nivel_id)";
			if (usuarioTipo.equals(UsuarioTipo.JEFE_CENTRO_OPERACIONES)
					|| usuarioTipo.equals(UsuarioTipo.LOGISTICA_Y_SOPORTE)) {
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
		}
		Query q = s.createSQLQuery(query).addEntity(UsuarioXActividad.class);
		uxas = q.list();
		return uxas;
	}

	public List<EvaluacionSupervisorDTO> findEvaluacionSupervisoresByIdAplicacionANDIdNivelANDIdActividadTipo(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			Integer idUsuario, String usuarioTipo) {

		List<EvaluacionSupervisorDTO> esdtos = new ArrayList<EvaluacionSupervisorDTO>();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT DISTINCT u.id as u_id,u.username,u.email,u.nombres,u.apellido_paterno,"
				+ "u.apellido_materno,u.celular,uxa.nota_presentacion_personal,uxa.nota_puntualidad,uxa.nota_despempeno,"
				+ "e.id as establecimiento_id,e.nombre as e_nombre,c.nombre as c_nombre,a.fecha_inicio,uxa.asistencia,co.nombre as centro FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id AND axnxat.actividad_tipo_id="
				+ SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id AND (a.actividad_estado_id!=7 OR a.actividad_estado_id IS NULL)"
				+ ""
				+ " JOIN USUARIO_x_ACTIVIDAD uxa ON a.id=uxa.actividad_id"
				+ " JOIN USUARIO_SELECCION us ON (uxa.usuario_seleccion_id=us.id AND us.seleccion=true AND us.renuncia=false)"
				+ " JOIN USUARIO_x_APLICACION_x_NIVEL uxaxn ON us.usuario_x_aplicacion_x_nivel_id=uxaxn.id"
				+ " JOIN USUARIO u ON uxaxn.usuario_id=u.id"
				// +
				// " JOIN USUARIO_TIPO ut ON us.usuario_tipo_id=ut.id AND (ut.nombre='"
				+ " JOIN USUARIO_TIPO ut ON uxa.usuario_tipo_id=ut.id AND ut.rol='"
				+ UsuarioTipo.SUPERVISOR
				+ "'"
				+ " JOIN CURSO c ON a.curso_id=c.id"
				+ " JOIN ESTABLECIMIENTO e ON c.establecimiento_id=e.id"
				+ " JOIN CO_x_ESTABLECIMIENTO coxe ON (e.id=coxe.establecimiento_id  AND axn.id=coxe.aplicacion_x_nivel_id)"
				+ " JOIN CO co ON coxe.co_id=co.id";
		if (usuarioTipo.equals(UsuarioTipo.JEFE_REGIONAL)
				|| usuarioTipo.equals(UsuarioTipo.JEFE_ZONAL)
				|| usuarioTipo.equals(UsuarioTipo.JEFE_CENTRO_OPERACIONES)
				|| usuarioTipo.equals(UsuarioTipo.LOGISTICA_Y_SOPORTE)) {

			if (usuarioTipo.equals(UsuarioTipo.JEFE_CENTRO_OPERACIONES)
					|| usuarioTipo.equals(UsuarioTipo.LOGISTICA_Y_SOPORTE)) {
				query += " JOIN JO_x_CO joxco ON (coxe.co_id=joxco.co_id AND joxco.jo_id="
						+ SecurityFilter.escapeString(idUsuario)
						+ ") AND joxco.activo=TRUE";
			} else {
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
		}
		query += " ORDER BY u.id,e_nombre,c.nombre";
		Query q = s.createSQLQuery(query);
		List<Object[]> os = q.list();
		EvaluacionSupervisorDTO esdto = null;
		UserDTO udto = null;
		for (Object[] o : os) {
			esdto = new EvaluacionSupervisorDTO();
			udto = new UserDTO();
			udto.setId((Integer) o[0]);
			udto.setUsername((String) o[1]);
			udto.setRut((String) o[1]);
			udto.setEmail((String) o[2]);
			udto.setNombres((String) o[3]);
			udto.setApellidoPaterno((String) o[4]);
			udto.setApellidoMaterno((String) o[5]);
			udto.setTelefono((String) o[6]);
			esdto.setSupervisor(udto);
			esdto.setPresentacionPersonal((Integer) o[7]);
			esdto.setPuntualidad((Integer) o[8]);
			esdto.setGeneral((Integer) o[9]);
			esdto.setRbd(String.valueOf((Integer) o[10]));
			esdto.setEstablecimiento((String) o[11]);
			esdto.setCurso((String) o[12]);
			esdto.setPlanificacionActividad(StringUtils
					.getDateString((Date) o[13]));
			esdto.setPresente((Boolean) o[14]);
			esdto.setCo((String) o[15]);
			esdtos.add(esdto);
		}
		return esdtos;
	}

	public UsuarioXActividad findExaminadoresByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCursoANDIdUsuario(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			Integer idCurso, Integer idUsuario) {

		UsuarioXActividad uxa = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT DISTINCT uxa.* FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id AND axnxat.actividad_tipo_id="
				+ SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN ACTIVIDAD a ON (axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id AND a.curso_id="
				+ SecurityFilter.escapeString(idCurso)
				+ " AND (a.actividad_estado_id!=7 OR a.actividad_estado_id IS NULL))"
				+ " JOIN USUARIO_x_ACTIVIDAD uxa ON a.id=uxa.actividad_id AND uxa.usuario_tipo_id < 16"
				+ " JOIN USUARIO_SELECCION us ON (uxa.usuario_seleccion_id=us.id AND us.seleccion=true AND us.renuncia=false)"
				+ " JOIN USUARIO_x_APLICACION_x_NIVEL uxaxn ON us.usuario_x_aplicacion_x_nivel_id=uxaxn.id AND uxaxn.usuario_id="
				+ SecurityFilter.escapeString(idUsuario);
		Query q = s.createSQLQuery(query).addEntity(UsuarioXActividad.class);
		q.setMaxResults(1);
		uxa = (UsuarioXActividad) q.uniqueResult();
		return uxa;
	}

	public UsuarioXActividad findExaminadorNoAsignadoByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCurso(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			Integer idCurso) {

		UsuarioXActividad uxa = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT DISTINCT uxa.* FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id AND axnxat.actividad_tipo_id="
				+ SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN ACTIVIDAD a ON (axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id AND a.curso_id="
				+ SecurityFilter.escapeString(idCurso)
				+ " AND (a.actividad_estado_id!=7 OR a.actividad_estado_id IS NULL))"
				+ " JOIN USUARIO_x_ACTIVIDAD uxa ON a.id=uxa.actividad_id"
				+ " JOIN USUARIO_TIPO ut ON uxa.usuario_tipo_id=ut.id AND ut.id < 16 AND ut.rol='"
				+ UsuarioTipo.EXAMINADOR + "'"
				+ " WHERE uxa.usuario_seleccion_id IS NULL";
		Query q = s.createSQLQuery(query).addEntity(UsuarioXActividad.class);
		q.setMaxResults(1);
		uxa = (UsuarioXActividad) q.uniqueResult();
		return uxa;
	}

	public List<UsuarioXActividad> findSupervisorByIdAplicacionANDIdNivelANDIdActividadTipoANDIdUsuario(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			Integer idUsuario) {

		List<UsuarioXActividad> uxas = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT DISTINCT uxa.* FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id AND axnxat.actividad_tipo_id="
				+ SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id AND (a.actividad_estado_id!=7 OR a.actividad_estado_id IS NULL)"
				+ " JOIN USUARIO_x_ACTIVIDAD uxa ON a.id=uxa.actividad_id"
				+ " JOIN USUARIO_SELECCION us ON (uxa.usuario_seleccion_id=us.id AND us.seleccion=true AND us.renuncia=false)"
				+ " JOIN USUARIO_x_APLICACION_x_NIVEL uxaxn ON us.usuario_x_aplicacion_x_nivel_id=uxaxn.id AND uxaxn.usuario_id="
				+ SecurityFilter.escapeString(idUsuario);
		Query q = s.createSQLQuery(query).addEntity(UsuarioXActividad.class);
		uxas = q.list();
		return uxas;
	}

	public UsuarioXActividad findSupervisorByIdAplicacionANDIdNivelANDIdActividadTipoANDIdUsuarioANDIdEstablecimientoANDNombreCurso(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			Integer idUsuario, Integer idEstablecimiento, String curso) {

		UsuarioXActividad uxa = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT DISTINCT uxa.* FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id AND axnxat.actividad_tipo_id="
				+ SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id AND (a.actividad_estado_id!=7 OR a.actividad_estado_id IS NULL)"
				+ " JOIN CURSO c ON (a.curso_id=c.id AND c.nombre='"
				+ SecurityFilter.escapeString(curso)
				+ "' AND c.establecimiento_id="
				+ SecurityFilter.escapeString(idEstablecimiento)
				+ ")"
				+ " JOIN USUARIO_x_ACTIVIDAD uxa ON a.id=uxa.actividad_id"
				+ " JOIN USUARIO_SELECCION us ON (uxa.usuario_seleccion_id=us.id AND us.seleccion=true AND us.renuncia=false)"
				+ " JOIN USUARIO_x_APLICACION_x_NIVEL uxaxn ON us.usuario_x_aplicacion_x_nivel_id=uxaxn.id AND uxaxn.usuario_id="
				+ SecurityFilter.escapeString(idUsuario);
		Query q = s.createSQLQuery(query).addEntity(UsuarioXActividad.class);
		uxa = ((UsuarioXActividad) q.uniqueResult());
		return uxa;
	}

	public UsuarioXActividad findByIdActividadANDIdUsuario(Integer idActividad,
			Integer idUsuario) {

		UsuarioXActividad uxa = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT uxa.* FROM USUARIO_x_ACTIVIDAD uxa"
				+ " JOIN USUARIO_SELECCION us ON (uxa.usuario_seleccion_id=us.id AND us.seleccion=true AND us.renuncia=false AND uxa.actividad_id="
				+ SecurityFilter.escapeString(idActividad)
				+ ")"
				+ " JOIN USUARIO_x_APLICACION_x_NIVEL uxaxn ON (us.usuario_x_aplicacion_x_nivel_id=uxaxn.id AND uxaxn.usuario_id="
				+ SecurityFilter.escapeString(idUsuario) + ")";
		Query q = s.createSQLQuery(query).addEntity(UsuarioXActividad.class);
		uxa = ((UsuarioXActividad) q.uniqueResult());
		return uxa;
	}
}
