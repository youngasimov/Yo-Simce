package com.dreamer8.yosimce.server.hibernate.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.ContactoCargo;
import com.dreamer8.yosimce.server.utils.SecurityFilter;

public class ContactoCargoDAO extends
		AbstractHibernateDAO<ContactoCargo, Integer> {
	/**
	 * 
	 */
	public ContactoCargoDAO() {
		// TODO Auto-generated constructor stub
	}

	public ContactoCargoDAO(Session s) {
		super();
		setSession(s);
	}

	public ContactoCargo findByName(String nombre) {

		ContactoCargo cc = null;
		Session s = getSession();
		String query = "SELECT cc.* FROM CONTACTO_CARGO cc"
				+ " WHERE cc.nombre='" + SecurityFilter.escapeString(nombre)
				+ "'";
		Query q = s.createSQLQuery(query).addEntity(ContactoCargo.class);
		cc = ((ContactoCargo) q.uniqueResult());
		return cc;
	}

}
