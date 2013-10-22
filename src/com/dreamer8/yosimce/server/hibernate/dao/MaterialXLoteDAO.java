/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.MaterialXLote;
import com.dreamer8.yosimce.server.hibernate.pojo.MaterialXLoteId;
import com.dreamer8.yosimce.server.utils.SecurityFilter;

/**
 * @author jorge
 * 
 */
public class MaterialXLoteDAO extends
		AbstractHibernateDAO<MaterialXLote, MaterialXLoteId> {
	/**
	 * 
	 */
	public MaterialXLoteDAO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	public MaterialXLoteDAO(Session s) {
		super();
		setSession(s);
	}

	public List<Integer> findIdMaterialesByIdLote(Integer idLote) {

		List<Integer> res = new ArrayList<Integer>();
		Session s = getSession();
		String query = "SELECT mxl.material_id FROM  MATERIAL_x_LOTE mxl"
				+ " WHERE mxl.lote_id=" + SecurityFilter.escapeString(idLote);
		Query q = s.createSQLQuery(query);
		List<Integer> os = q.list();
		for (Integer o : os) {
			res.add(o);
		}
		return res;
	}

	public void deleteByIdMaterial(Integer idMaterial) {

		Session s = getSession();
		String query = "DELETE FROM MATERIAL_x_LOTE WHERE material_id="
				+ SecurityFilter.escapeString(idMaterial);
		s.createSQLQuery(query).executeUpdate();
	}
}
