/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.AlumnoEstado;
import com.dreamer8.yosimce.server.utils.SecurityFilter;

/**
 * @author jorge
 * 
 */
public class AlumnoEstadoDAO extends
		AbstractHibernateDAO<AlumnoEstado, Integer> {
	
	public AlumnoEstado findByNombre(String nombre) {

		AlumnoEstado ae = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT ae.* FROM ALUMNO_ESTADO ae"
				+ " WHERE ae.nombre='" + SecurityFilter.escapeString(nombre)
				+ "'";
		Query q = s.createSQLQuery(query).addEntity(AlumnoEstado.class);
		ae = ((AlumnoEstado) q.uniqueResult());
		return ae;
	}
}
