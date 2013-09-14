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

	public List<Integer> findIdMaterialesByIdLote(Integer idLote) {

		List<Integer> res = new ArrayList<Integer>();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT mxl.material_id FROM  MATERIAL_x_LOTE mxl"
				+ " WHERE mxl.lote_id=" + SecurityFilter.escapeString(idLote);
		Query q = s.createSQLQuery(query);
		List<Object[]> os = q.list();
		for (Object[] o : os) {
			res.add((Integer) o[0]);
		}
		return res;
	}
}
