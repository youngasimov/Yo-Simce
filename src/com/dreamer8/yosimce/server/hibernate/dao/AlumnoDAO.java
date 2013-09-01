/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.Alumno;
import com.dreamer8.yosimce.server.utils.SecurityFilter;
import com.dreamer8.yosimce.server.utils.StringUtils;

/**
 * @author jorge
 * 
 */
public class AlumnoDAO extends AbstractHibernateDAO<Alumno, Integer> {

	public Alumno findByRut(String rut) {
		Alumno a = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT a.* FROM ALUMNO a " + " WHERE a.rut='"
				+ SecurityFilter.escapeString(StringUtils.formatRut(rut)) + "'";
		Query q = s.createSQLQuery(query).addEntity(Alumno.class);
		a = ((Alumno) q.uniqueResult());
		return a;
	}
}
