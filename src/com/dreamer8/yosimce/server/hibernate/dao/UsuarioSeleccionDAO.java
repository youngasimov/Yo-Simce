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

	public UsuarioSeleccion findByIdAplicacionANDIdNivelANDIdUsuario(
			Integer idAplicacion, Integer idNivel, Integer idUsuario) {

		UsuarioSeleccion us = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT us.* FROM USUARIO_SELECCION us"
				+ " JOIN USUARIO_x_APLICACION_x_NIVEL uxaxn ON (us.usuario_x_aplicacion_x_nivel_id=uxaxn.id AND uxaxn.usuario_id="
				+ SecurityFilter.escapeString(idUsuario)
				+ ")"
				+ " JOIN APLICACION_x_NIVEL axn ON (uxaxn.aplicacion_x_nivel_id=axn.id  AND axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id=" + SecurityFilter.escapeString(idNivel)
				+ ")";
		Query q = s.createSQLQuery(query).addEntity(UsuarioSeleccion.class);
		us = ((UsuarioSeleccion) q.uniqueResult());
		return us;
	}
}
