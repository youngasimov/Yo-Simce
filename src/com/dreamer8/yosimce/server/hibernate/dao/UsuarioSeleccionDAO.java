package com.dreamer8.yosimce.server.hibernate.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.UsuarioSeleccion;
import com.dreamer8.yosimce.server.utils.SecurityFilter;

public class UsuarioSeleccionDAO extends
		AbstractHibernateDAO<UsuarioSeleccion, Integer> {
	public UsuarioSeleccion findBYIdUsuarioXAplicacionXNivel(
			Integer idUsuarioXAplicacionXNivel) {

		UsuarioSeleccion us = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT us.* FROM USUARIO_SELECCION us"
				+ " WHERE us.usuario_x_aplicacion_x_nivel_id="
				+ SecurityFilter.escapeString(idUsuarioXAplicacionXNivel);
		Query q = s.createSQLQuery(query).addEntity(UsuarioSeleccion.class);
		us = ((UsuarioSeleccion) q.uniqueResult());
		return us;
	}
}
