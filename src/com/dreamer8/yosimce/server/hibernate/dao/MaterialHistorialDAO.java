/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.MaterialHistorial;
import com.dreamer8.yosimce.server.hibernate.pojo.MaterialHistorialId;
import com.dreamer8.yosimce.server.utils.SecurityFilter;

/**
 * @author jorge
 * 
 */
public class MaterialHistorialDAO extends
		AbstractHibernateDAO<MaterialHistorial, MaterialHistorialId> {

	public List<MaterialHistorial> findByIdMaterial(Integer idMaterial) {

		List<MaterialHistorial> mhs = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT mh.* FROM  MATERIAL_HISTORIAL mh"
				+ " WHERE mh.material_id="
				+ SecurityFilter.escapeString(idMaterial);
		Query q = s.createSQLQuery(query).addEntity(MaterialHistorial.class);
		mhs = q.list();
		return mhs;
	}
}
