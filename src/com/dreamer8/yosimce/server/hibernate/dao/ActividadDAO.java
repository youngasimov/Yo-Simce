/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.Actividad;
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
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.id_actividad="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.id_nivel="
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
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.id_actividad="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.id_nivel="
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
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.id_actividad="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.id_nivel="
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
}
