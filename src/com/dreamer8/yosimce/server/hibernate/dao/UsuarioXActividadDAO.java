/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.UsuarioTipo;
import com.dreamer8.yosimce.server.hibernate.pojo.UsuarioXActividad;
import com.dreamer8.yosimce.server.utils.SecurityFilter;

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
				+ ")"
				+ " JOIN USUARIO_x_ACTIVIDAD uxa ON a.id=uxa.actividad_id"
				+ " JOIN USUARIO_SELECCION us ON uxa.usuario_seleccion_id=us.id"
				+ " JOIN USUARIO_TIPO ut ON us.usuario_tipo_id=ut.id AND (ut.nombre='"
				+ UsuarioTipo.EXAMINADOR + "' OR ut.nombre='"
				+ UsuarioTipo.EXAMINADOR_NEE + "' OR ut.nombre='"
				+ UsuarioTipo.EXAMINADOR_SUPLENTE + "')";
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
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id"
				+ ""
				+ " JOIN USUARIO_x_ACTIVIDAD uxa ON a.id=uxa.actividad_id"
				+ " JOIN USUARIO_SELECCION us ON uxa.usuario_seleccion_id=us.id"
				+ " JOIN USUARIO_TIPO ut ON us.usuario_tipo_id=ut.id AND (ut.nombre='"
				+ UsuarioTipo.SUPERVISOR + "' OR ut.nombre='"
				+ UsuarioTipo.SUPERVISOR_CON_AUTO + "')"
				+ " JOIN CURSO c ON a.curso_id=c.id"
				+ " JOIN ESTABLECIMIENTO e ON c.establecimiento_id=e.id";
		if (usuarioTipo.equals(UsuarioTipo.JEFE_REGIONAL)
				|| usuarioTipo.equals(UsuarioTipo.JEFE_ZONAL)
				|| usuarioTipo.equals(UsuarioTipo.JEFE_CENTRO_OPERACIONES) || usuarioTipo.equals(UsuarioTipo.LOGISTICA_Y_SOPORTE)) {
			query += " JOIN CO_x_ESTABLECIMIENTO coxe ON (e.id=coxe.establecimiento_id  AND axn.id=coxe.aplicacion_x_nivel_id)";
			if (usuarioTipo.equals(UsuarioTipo.JEFE_CENTRO_OPERACIONES) || usuarioTipo.equals(UsuarioTipo.LOGISTICA_Y_SOPORTE)) {
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
				+ ")"
				+ " JOIN USUARIO_x_ACTIVIDAD uxa ON a.id=uxa.actividad_id"
				+ " JOIN USUARIO_SELECCION us ON uxa.usuario_seleccion_id=us.id"
				+ " JOIN USUARIO_x_APLICACION_x_NIVEL uxaxn ON us.usuario_x_aplicacion_x_nivel_id=uxaxn.id AND uxaxn.usuario_id="
				+ SecurityFilter.escapeString(idUsuario);
		Query q = s.createSQLQuery(query).addEntity(UsuarioXActividad.class);
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
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id"
				+ " JOIN USUARIO_x_ACTIVIDAD uxa ON a.id=uxa.actividad_id"
				+ " JOIN USUARIO_SELECCION us ON uxa.usuario_seleccion_id=us.id"
				+ " JOIN USUARIO_x_APLICACION_x_NIVEL uxaxn ON us.usuario_x_aplicacion_x_nivel_id=uxaxn.id AND uxaxn.usuario_id="
				+ SecurityFilter.escapeString(idUsuario);
		Query q = s.createSQLQuery(query).addEntity(UsuarioXActividad.class);
		uxas = q.list();
		return uxas;
	}
}
