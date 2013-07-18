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

	public List<Aplicacion> findByIdUsuario(Integer idUsuario) {

		List<Aplicacion> as = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT a.* FROM USUARIO_SELECCION us"
				+ " JOIN USUARIO_x_APLICACION_x_NIVEL uxaxn ON (us.usuario_x_aplicacion_x_nivel_id=uxaxn.id AND uxaxn.usuario_id="
				+ SecurityFilter.escapeString(idUsuario)
				+ ")"
				+ " JOIN APLICACION_x_NIVEL axn ON uxaxn.aplicacion_x_nivel_id=axn.id"
				+ " JOIN APLICACION a ON axn.aplicacion_id=a.id";
		Query q = s.createSQLQuery(query).addEntity(Aplicacion.class);
		as = q.list();
		return as;
	}
}
