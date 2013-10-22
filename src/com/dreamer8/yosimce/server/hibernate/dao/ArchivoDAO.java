/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.Archivo;

/**
 * @author jorge
 * 
 */
public class ArchivoDAO extends AbstractHibernateDAO<Archivo, Integer> {
	/**
	 * 
	 */
	public ArchivoDAO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	public ArchivoDAO(Session s) {
		super();
		setSession(s);
	}

}
