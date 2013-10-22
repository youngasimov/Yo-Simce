/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.MaterialXActividad;
import com.dreamer8.yosimce.server.hibernate.pojo.MaterialXActividadId;
import com.dreamer8.yosimce.server.utils.SecurityFilter;

/**
 * @author jorge
 * 
 */
public class MaterialXActividadDAO extends
		AbstractHibernateDAO<MaterialXActividad, MaterialXActividadId> {
	/**
	 * 
	 */
	public MaterialXActividadDAO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	public MaterialXActividadDAO(Session s) {
		super();
		setSession(s);
	}

	public List<MaterialXActividad> findByIdMaterial(Integer idMaterial) {

		List<MaterialXActividad> mxas = null;
		Session s = getSession();
		String query = "SELECT mxa.* FROM  MATERIAL_x_ACTIVIDAD mxa"
				+ " WHERE mxa.material_id="
				+ SecurityFilter.escapeString(idMaterial);
		Query q = s.createSQLQuery(query).addEntity(MaterialXActividad.class);
		mxas = q.list();
		return mxas;
	}
}
