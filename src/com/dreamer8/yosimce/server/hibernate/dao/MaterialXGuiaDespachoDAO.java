/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.MaterialXGuiaDespacho;

/**
 * @author jorge
 * 
 */
public class MaterialXGuiaDespachoDAO extends
		AbstractHibernateDAO<MaterialXGuiaDespacho, Integer> {
	/**
	 * 
	 */
	public MaterialXGuiaDespachoDAO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	public MaterialXGuiaDespachoDAO(Session s) {
		super();
		setSession(s);
	}

}
