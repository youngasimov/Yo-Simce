/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.Co;
import com.dreamer8.yosimce.server.utils.SecurityFilter;

/**
 * @author jorge
 * 
 */
public class CoDAO extends AbstractHibernateDAO<Co, Integer> {

	public List<Co> findByIdAplicacion(Integer idAplicacion) {

		List<Co> cos = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT co.* FROM  CENTRO_REGIONAL cr "
				+ " JOIN ZONA z ON (cr.id=z.centro_regional_id AND cr.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion) + ")"
				+ " JOIN CO co ON z.id=co.zona_id";
		Query q = s.createSQLQuery(query).addEntity(Co.class);
		cos = q.list();
		return cos;
	}
}
