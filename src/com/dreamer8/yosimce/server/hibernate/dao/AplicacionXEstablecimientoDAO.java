/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.AplicacionXEstablecimiento;
import com.dreamer8.yosimce.server.utils.SecurityFilter;

/**
 * @author jorge
 * 
 */
public class AplicacionXEstablecimientoDAO extends
		AbstractHibernateDAO<AplicacionXEstablecimiento, Integer> {
	/**
	 * 
	 */
	public AplicacionXEstablecimientoDAO() {
		// TODO Auto-generated constructor stub
	}

	public AplicacionXEstablecimientoDAO(Session s) {
		super();
		setSession(s);
	}

	public AplicacionXEstablecimiento findByIdAplicacionANDIdEstablecimiento(
			Integer idAplicacion, Integer idEstablecimiento) {

		AplicacionXEstablecimiento axe = null;
		Session s = getSession();
		String query = "SELECT axe.* FROM APLICACION_x_ESTABLECIMIENTO axe"
				+ " WHERE axe.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axe.establecimiento_id="
				+ SecurityFilter.escapeString(idEstablecimiento);
		Query q = s.createSQLQuery(query).addEntity(
				AplicacionXEstablecimiento.class);
		axe = ((AplicacionXEstablecimiento) q.uniqueResult());
		return axe;
	}
}
