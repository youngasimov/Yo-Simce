/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.IncidenciaTipo;
import com.dreamer8.yosimce.server.utils.SecurityFilter;

/**
 * @author jorge
 * 
 */
public class IncidenciaTipoDAO extends
		AbstractHibernateDAO<IncidenciaTipo, Integer> {

	public IncidenciaTipo findByNombre(String nombre) {

		IncidenciaTipo it = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT it.* FROM INCIDENCIA_TIPO it"
				+ " WHERE it.nombre='" + SecurityFilter.escapeString(nombre)
				+ "'";
		Query q = s.createSQLQuery(query).addEntity(IncidenciaTipo.class);
		it = ((IncidenciaTipo) q.uniqueResult());
		return it;
	}
}
