/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.Sexo;
import com.dreamer8.yosimce.server.utils.SecurityFilter;

/**
 * @author jorge
 * 
 */
public class SexoDAO extends AbstractHibernateDAO<Sexo, Integer> {
	/**
 * 
 */
	public SexoDAO() {
		// TODO Auto-generated constructor stub
	}

	public SexoDAO(Session s) {
		super();
		setSession(s);
	}

	public Sexo findByNombre(String nombre) {

		Sexo sexo = null;
		Session s = getSession();
		String query = "SELECT s.* FROM SEXO s" + " WHERE s.nombre='"
				+ SecurityFilter.escapeString(nombre) + "'";
		Query q = s.createSQLQuery(query).addEntity(Sexo.class);
		sexo = ((Sexo) q.uniqueResult());
		return sexo;
	}
}
