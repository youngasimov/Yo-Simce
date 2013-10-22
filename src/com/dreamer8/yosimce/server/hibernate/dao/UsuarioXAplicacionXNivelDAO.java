package com.dreamer8.yosimce.server.hibernate.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.UsuarioXAplicacionXNivel;
import com.dreamer8.yosimce.server.utils.SecurityFilter;

public class UsuarioXAplicacionXNivelDAO extends
		AbstractHibernateDAO<UsuarioXAplicacionXNivel, Integer> {
	/**
	 * 
	 */
	public UsuarioXAplicacionXNivelDAO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	public UsuarioXAplicacionXNivelDAO(Session s) {
		super();
		setSession(s);
	}

	public UsuarioXAplicacionXNivel findByIdUsuarioANDIdAplicacionANDIdNivel(
			Integer idUsuario, Integer idAplicacion, Integer idNivel) {

		UsuarioXAplicacionXNivel uxaxn = null;
		Session s = getSession();
		String query = "SELECT uxaxn.* FROM USUARIO_x_APLICACION_x_NIVEL uxaxn"
				+ " JOIN APLICACION_x_NIVEL axn ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id=" + SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=uxaxn.aplicacion_x_nivel_id)"
				+ " WHERE uxaxn.usuario_id="
				+ SecurityFilter.escapeString(idUsuario);
		Query q = s.createSQLQuery(query).addEntity(
				UsuarioXAplicacionXNivel.class);
		uxaxn = ((UsuarioXAplicacionXNivel) q.uniqueResult());
		return uxaxn;
	}
}
