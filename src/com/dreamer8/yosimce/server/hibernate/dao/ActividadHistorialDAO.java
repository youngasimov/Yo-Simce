/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.ActividadHistorial;
import com.dreamer8.yosimce.server.hibernate.pojo.ActividadHistorialId;
import com.dreamer8.yosimce.server.utils.SecurityFilter;

/**
 * @author jorge
 * 
 */
public class ActividadHistorialDAO extends
		AbstractHibernateDAO<ActividadHistorial, ActividadHistorialId> {

	public ActividadHistorial findLastByIdActividad(Integer idActividad) {

		ActividadHistorial ah = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT ah.* FROM  ACTIVIDAD_HISTORIAL ah"
				+ " JOIN (SELECT ah.actividad_id,MAX(ah.fecha) as fecha"
				+ " FROM ACTIVIDAD_HISTORIAL ah"
				+ " WHERE ah.actividad_id="
				+ SecurityFilter.escapeString(idActividad)
				+ " GROUP BY ah.actividad_id) ah_max ON ah.actividad_id=ah_max.actividad_id AND ah.fecha=ah_max.fecha";
		Query q = s.createSQLQuery(query).addEntity(ActividadHistorial.class);
		ah = ((ActividadHistorial) q.uniqueResult());
		return ah;
	}
}
