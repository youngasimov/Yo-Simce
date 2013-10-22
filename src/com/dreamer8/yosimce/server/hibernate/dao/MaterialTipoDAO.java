/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.MaterialTipo;
import com.dreamer8.yosimce.server.utils.SecurityFilter;

/**
 * @author jorge
 * 
 */
public class MaterialTipoDAO extends
		AbstractHibernateDAO<MaterialTipo, Integer> {
	/**
	 * 
	 */
	public MaterialTipoDAO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	public MaterialTipoDAO(Session s) {
		super();
		setSession(s);
	}

	public MaterialTipo findByNombre(String nombre) {

		MaterialTipo mt = null;
		Session s = getSession();
		String query = "SELECT mt.* FROM MATERIAL_TIPO mt"
				+ " WHERE mt.nombre='" + SecurityFilter.escapeString(nombre)
				+ "'";
		Query q = s.createSQLQuery(query).addEntity(MaterialTipo.class);
		mt = ((MaterialTipo) q.uniqueResult());
		return mt;
	}
}
