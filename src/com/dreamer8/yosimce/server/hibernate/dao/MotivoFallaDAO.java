/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.MotivoFalla;
import com.dreamer8.yosimce.server.utils.SecurityFilter;

/**
 * @author jorge
 * 
 */
public class MotivoFallaDAO extends AbstractHibernateDAO<MotivoFalla, Integer> {
	public List<MotivoFalla> findByIdIncidenciaTipo(Integer idIncidenciaTipo) {

		List<MotivoFalla> mfs = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT mf.* FROM  MOTIVO_FALLA mf"
				+ " WHERE mf.incidencia_tipo_id="
				+ SecurityFilter.escapeString(idIncidenciaTipo);
		Query q = s.createSQLQuery(query).addEntity(MotivoFalla.class);
		mfs = q.list();
		return mfs;
	}
}
