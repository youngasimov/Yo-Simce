package com.dreamer8.yosimce.server.hibernate.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.ActividadEstado;
import com.dreamer8.yosimce.server.utils.SecurityFilter;

public class ActividadEstadoDAO extends
		AbstractHibernateDAO<ActividadEstado, Integer> {

	public ActividadEstado findByNombre(String nombre) {

		ActividadEstado ae = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT ae.* FROM ACTIVIDAD_ESTADO ae"
				+ " WHERE ae.nombre='" + SecurityFilter.escapeString(nombre)
				+ "'";
		Query q = s.createSQLQuery(query).addEntity(ActividadEstado.class);
		ae = ((ActividadEstado) q.uniqueResult());
		return ae;
	}
}
