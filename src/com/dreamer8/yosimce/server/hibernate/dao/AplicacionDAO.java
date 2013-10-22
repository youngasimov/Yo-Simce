/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.Aplicacion;
import com.dreamer8.yosimce.server.utils.SecurityFilter;

/**
 * @author jorge
 * 
 */
public class AplicacionDAO extends AbstractHibernateDAO<Aplicacion, Integer> {

	/**
 * 
 */
	public AplicacionDAO() {
		// TODO Auto-generated constructor stub
	}

	public AplicacionDAO(Session s) {
		super();
		setSession(s);
	}

	public List<Aplicacion> findByIdUsuario(Integer idUsuario) {

		List<Aplicacion> as = null;
		Session s = getSession();
		String query = "SELECT DISTINCT a.* FROM USUARIO_x_APLICACION_x_NIVEL uxaxn"
				+ " JOIN APLICACION_x_NIVEL axn ON uxaxn.aplicacion_x_nivel_id=axn.id"
				+ " JOIN APLICACION a ON axn.aplicacion_id=a.id"
				+ " WHERE uxaxn.usuario_id="
				+ SecurityFilter.escapeString(idUsuario);
		Query q = s.createSQLQuery(query).addEntity(Aplicacion.class);
		as = q.list();
		return as;
	}
}
