/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.Nivel;
import com.dreamer8.yosimce.server.utils.SecurityFilter;

/**
 * @author jorge
 * 
 */
public class NivelDAO extends AbstractHibernateDAO<Nivel, Integer> {

	/**
	 * @param idAplicacion
	 * @param id
	 * @return
	 */
	public List<Nivel> findByIdAplicacionANDIdUsuario(Integer idAplicacion,
			Integer idUsuario) {

		List<Nivel> ns = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT DISTINCT n.* FROM USUARIO_x_APLICACION_x_NIVEL uxaxn "
				+ " JOIN APLICACION_x_NIVEL axn ON (uxaxn.aplicacion_x_nivel_id=axn.id AND axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion) + ")"
				+ " JOIN NIVEL n ON axn.nivel_id=n.id "
				+ " WHERE uxaxn.usuario_id="
				+ SecurityFilter.escapeString(idUsuario);
		Query q = s.createSQLQuery(query).addEntity(Nivel.class);
		ns = q.list();
		return ns;
	}

}
