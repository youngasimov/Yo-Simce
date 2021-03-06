/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.GuiaDespacho;
import com.dreamer8.yosimce.server.utils.SecurityFilter;

/**
 * @author jorge
 * 
 */
public class GuiaDespachoDAO extends
		AbstractHibernateDAO<GuiaDespacho, Integer> {
	/**
	 * 
	 */
	public GuiaDespachoDAO() {
		// TODO Auto-generated constructor stub
	}

	public GuiaDespachoDAO(Session s) {
		super();
		setSession(s);
	}

	public List<GuiaDespacho> findByIdMaterial(Integer idMaterial) {

		List<GuiaDespacho> gds = null;
		Session s = getSession();
		String query = "SELECT gd.* FROM  MATERIAL_x_GUIA_DESPACHO mxgd"
				+ " JOIN GUIA_DESPACHO gd ON mxgd.guia_despacho_id=gd.id AND mxgd.material_id="
				+ SecurityFilter.escapeString(idMaterial);
		Query q = s.createSQLQuery(query).addEntity(GuiaDespacho.class);
		gds = q.list();
		return gds;
	}
}
