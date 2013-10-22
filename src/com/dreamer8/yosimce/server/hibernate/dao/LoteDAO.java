/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.Lote;

/**
 * @author jorge
 * 
 */
public class LoteDAO extends AbstractHibernateDAO<Lote, Integer> {
	/**
	 * 
	 */
	public LoteDAO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	public LoteDAO(Session s) {
		super();
		setSession(s);
	}

}
