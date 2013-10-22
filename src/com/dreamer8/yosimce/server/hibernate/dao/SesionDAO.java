package com.dreamer8.yosimce.server.hibernate.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.dreamer8.yosimce.server.hibernate.pojo.Sesion;
import com.dreamer8.yosimce.server.utils.SecurityFilter;

public class SesionDAO extends AbstractHibernateDAO<Sesion, String> {
	/**
	 * 
	 */
	public SesionDAO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	public SesionDAO(Session s) {
		super();
		setSession(s);
	}

	public List<Sesion> findBySessionId(String sessionid) {
		return findByCriteria(Restrictions.eq("sessionId",
				SecurityFilter.escapeString(sessionid)));
	}
}
