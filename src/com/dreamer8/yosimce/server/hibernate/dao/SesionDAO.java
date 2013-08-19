package com.dreamer8.yosimce.server.hibernate.dao;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import com.dreamer8.yosimce.server.hibernate.pojo.Sesion;
import com.dreamer8.yosimce.server.utils.SecurityFilter;

public class SesionDAO extends AbstractHibernateDAO<Sesion, String> {
	public List<Sesion> findBySessionId(String sessionid) {
		return findByCriteria(Restrictions.eq("sessionid",
				SecurityFilter.escapeString(sessionid)));
	}
}
