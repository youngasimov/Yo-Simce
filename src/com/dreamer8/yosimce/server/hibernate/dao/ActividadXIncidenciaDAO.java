/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.ActividadXIncidencia;
import com.dreamer8.yosimce.server.utils.SecurityFilter;

/**
 * @author jorge
 * 
 */
public class ActividadXIncidenciaDAO extends
		AbstractHibernateDAO<ActividadXIncidencia, Integer> {
	public ActividadXIncidencia findByIdActividadANDIdMotivoFalla(
			Integer idActividad, Integer idMotivoFalla) {
		ActividadXIncidencia axi = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT axi.* FROM ACTIVIDAD_x_INCIDENCIA axi"
				+ " WHERE axi.actividad_id="
				+ SecurityFilter.escapeString(idActividad)
				+ " AND axi.motivo_falla_id="
				+ SecurityFilter.escapeString(idMotivoFalla);
		Query q = s.createSQLQuery(query).addEntity(ActividadXIncidencia.class);
		axi = ((ActividadXIncidencia) q.uniqueResult());
		return axi;
	}

	public List<ActividadXIncidencia> findByIdActividadANDIncidenciaTipo(
			Integer idActividad, String incidenciaTipo) {
		List<ActividadXIncidencia> axis = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT axi.* FROM  ACTIVIDAD_x_INCIDENCIA axi"
				+ " JOIN MOTIVO_FALLA mf ON axi.motivo_falla_id=mf.id AND axi.actividad_id="
				+ SecurityFilter.escapeString(idActividad)
				+ " JOIN INCIDENCIA_TIPO it ON mf.incidencia_tipo_id=it.id"
				+ " WHERE it.nombre='"
				+ SecurityFilter.escapeString(incidenciaTipo) + "'";
		Query q = s.createSQLQuery(query).addEntity(ActividadXIncidencia.class);
		axis = q.list();
		return axis;
	}
}
