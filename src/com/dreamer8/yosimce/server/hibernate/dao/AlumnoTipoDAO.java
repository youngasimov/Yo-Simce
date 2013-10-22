/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.AlumnoTipo;
import com.dreamer8.yosimce.server.utils.SecurityFilter;

/**
 * @author jorge
 * 
 */
public class AlumnoTipoDAO extends AbstractHibernateDAO<AlumnoTipo, Integer> {
	/**
	 * 
	 */
	public AlumnoTipoDAO() {
		// TODO Auto-generated constructor stub
	}

	public AlumnoTipoDAO(Session s) {
		super();
		setSession(s);
	}

	public AlumnoTipo findByNombre(String nombre) {

		AlumnoTipo at = null;
		Session s = getSession();
		String query = "SELECT at.* FROM ALUMNO_TIPO at" + " WHERE at.nombre='"
				+ SecurityFilter.escapeString(nombre) + "'";
		Query q = s.createSQLQuery(query).addEntity(AlumnoTipo.class);
		at = ((AlumnoTipo) q.uniqueResult());
		return at;
	}
}
