/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.Actividad;
import com.dreamer8.yosimce.server.hibernate.pojo.UsuarioTipo;
import com.dreamer8.yosimce.server.utils.SecurityFilter;

/**
 * @author jorge
 * 
 */
public class ActividadDAO extends AbstractHibernateDAO<Actividad, Integer> {
	public List<Actividad> findByIdAplicacionANDIdNivelANDIdActividadTipo(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo) {

		List<Actividad> as = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT a.* FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id AND axnxat.actividad_tipo_id="
				+ SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id";
		Query q = s.createSQLQuery(query).addEntity(Actividad.class);
		as = q.list();
		return as;
	}

	/**
	 * @param idAplicacion
	 * @param idNivel
	 * @param idActividadTipo
	 * @param idEstablecimiento
	 * @return
	 */
	public Actividad findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdEstablecimient(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			Integer idEstablecimiento) {

		Actividad a = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT DISTINCT a.* FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel="
				+ SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id AND axnxat.actividad_tipo_id="
				+ SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id"
				+ " JOIN CURSO c ON a.curso_id=c.id"
				+ " WHERE c.establecimiento_id="
				+ SecurityFilter.escapeString(idEstablecimiento);

		Query q = s.createSQLQuery(query).addEntity(Actividad.class);
		a = ((Actividad) q.uniqueResult());
		return a;
	}

	public Actividad findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCurso(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			Integer idCurso) {

		Actividad a = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT a.* FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id AND axnxat.actividad_tipo_id="
				+ SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN ACTIVIDAD a ON (axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id AND a.curso_id="
				+ SecurityFilter.escapeString(idCurso) + ")";

		Query q = s.createSQLQuery(query).addEntity(Actividad.class);
		a = ((Actividad) q.uniqueResult());
		return a;
	}

	public List<Actividad> findByIdAplicacionANDIdNivelANDIdActividadTipoANDFiltros(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			Integer idUsuario, String usuarioTipo, Integer offset,
			Integer lenght, Map<String, String> filtros) {

		List<Actividad> as = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT DISTINCT a.* FROM APLICACION_x_NIVEL axn "
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
				query += " JOIN CO co ON coxe.co_id=co.co_id";
				if (usuarioTipo.equals(UsuarioTipo.JEFE_ZONAL)) {
					query += " JOIN JZ_x_ZONA jzxz ON (co.zona_id=jzxz=zona_id AND jzxz.jz_id="
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
		Query q = s.createSQLQuery(query).addEntity(Actividad.class);
		if (offset != null) {
			q.setFirstResult(offset);
		}
		if (lenght != null) {
			q.setMaxResults(lenght);
		}
		as = q.list();
		return as;
	}

	public Integer countByIdAplicacionANDIdNivelANDIdActividadTipoANDFiltros(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			Integer idUsuario, String usuarioTipo, Map<String, String> filtros) {

		Integer result = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT COUNT(a.*) FROM APLICACION_x_NIVEL axn "
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
				query += " JOIN CO co ON coxe.co_id=co.co_id";
				if (usuarioTipo.equals(UsuarioTipo.JEFE_ZONAL)) {
					query += " JOIN JZ_x_ZONA jzxz ON (co.zona_id=jzxz=zona_id AND jzxz.jz_id="
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
		Query q = s.createSQLQuery(query);
		result = ((BigInteger) q.uniqueResult()).intValue();
		return result;
	}
}
