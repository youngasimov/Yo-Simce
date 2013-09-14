/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.Lugar;
import com.dreamer8.yosimce.server.utils.SecurityFilter;

/**
 * @author jorge
 * 
 */
public class LugarDAO extends AbstractHibernateDAO<Lugar, Integer> {
	public List<Lugar> findByIdAplicacion(Integer idAplicacion) {

		List<Lugar> ls = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT l.* FROM LUGAR l" + " WHERE (l.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " OR l.aplicacion_id IS NULL) AND l.nombre !='"
				+ SecurityFilter.escapeString(Lugar.CENTRO_DE_OPERACIONES)
				+ "'";
		Query q = s.createSQLQuery(query).addEntity(Lugar.class);
		ls = q.list();
		return ls;
	}
}
