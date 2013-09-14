/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.MaterialEstado;
import com.dreamer8.yosimce.server.utils.SecurityFilter;

/**
 * @author jorge
 * 
 */
public class MaterialEstadoDAO extends
		AbstractHibernateDAO<MaterialEstado, Integer> {

	public MaterialEstado findByNombre(String nombre) {

		MaterialEstado me = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT me.* FROM MATERIAL_ESTADO me"
				+ " WHERE me.nombre='" + SecurityFilter.escapeString(nombre)
				+ "'";
		Query q = s.createSQLQuery(query).addEntity(MaterialEstado.class);
		me = ((MaterialEstado) q.uniqueResult());
		return me;
	}
}
