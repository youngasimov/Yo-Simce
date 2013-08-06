/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.Zona;
import com.dreamer8.yosimce.server.utils.SecurityFilter;

/**
 * @author jorge
 * 
 */
public class ZonaDAO extends AbstractHibernateDAO<Zona, Integer> {
	public List<Zona> findByIdAplicacion(Integer idAplicacion){
		

		List<Zona> zs = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT z.* FROM  cr "
				+ " JOIN ZONA z ON (cr.id=z.centro_regional_id AND cr.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion) + ")";
		Query q = s.createSQLQuery(query).addEntity(Zona.class);
		zs = q.list();
		return zs;
	}
}
