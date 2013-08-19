package com.dreamer8.yosimce.server.hibernate.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.AplicacionXNivel;
import com.dreamer8.yosimce.server.utils.SecurityFilter;

public class AplicacionXNivelDAO extends
		AbstractHibernateDAO<AplicacionXNivel, Integer> {
	public AplicacionXNivel findByIdAplicacionANDIdNivel(Integer idAplicacion,
			Integer idNivel) {

		AplicacionXNivel axn = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT axn.* FROM APLICACION_x_NIVEL axn "
				+ " WHERE axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id=" + SecurityFilter.escapeString(idNivel);
		Query q = s.createSQLQuery(query).addEntity(AplicacionXNivel.class);
		axn = ((AplicacionXNivel) q.uniqueResult());
		return axn;
	}
}
