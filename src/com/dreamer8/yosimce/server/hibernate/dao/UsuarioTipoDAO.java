/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.UsuarioTipo;
import com.dreamer8.yosimce.server.utils.SecurityFilter;

/**
 * @author jorge
 * 
 */
public class UsuarioTipoDAO extends AbstractHibernateDAO<UsuarioTipo, Integer> {

	/**
	 * @param idAplicacion
	 * @param idNivel
	 * @param id
	 * @return
	 */
	public UsuarioTipo findByIdAplicacionANDIdNivelANDIdUsuario(
			Integer idAplicacion, Integer idNivel, Integer idUsuario) {

		UsuarioTipo ut = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT ut,* FROM USUARIO_x_APLICACION_x_NIVEL uxaxn"
				+ " JOIN APLICACION_x_NIVEL axn ON (uxaxn.aplicacion_x_nivel_id=axn.id AND axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ ")"
				+ " JOIN USUARIO_SELECCION us ON us.usuario_x_aplicacion_x_nivel_id=uxaxn.id"
				+ " JOIN USUARIO_TIPO ut ON us.usuario_tipo_id=ut.id"
				+ " WHERE uxaxn.usuario_id="
				+ SecurityFilter.escapeString(idUsuario);
		Query q = s.createSQLQuery(query).addEntity(UsuarioTipo.class);
		ut = ((UsuarioTipo) q.uniqueResult());
		return ut;
	}

}
