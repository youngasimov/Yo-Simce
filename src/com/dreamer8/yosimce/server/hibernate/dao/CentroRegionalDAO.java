/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.CentroRegional;
import com.dreamer8.yosimce.server.utils.SecurityFilter;

/**
 * @author jorge
 * 
 */
public class CentroRegionalDAO extends
		AbstractHibernateDAO<CentroRegional, Integer> {
	public List<CentroRegional> findByIdAplicacion(Integer idAplicacion) {

		List<CentroRegional> crs = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT cr.* FROM  CENTRO_REGIONAL cr "
				+ " WHERE cr.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion);
		Query q = s.createSQLQuery(query).addEntity(CentroRegional.class);
		crs = q.list();
		return crs;
	}
}
