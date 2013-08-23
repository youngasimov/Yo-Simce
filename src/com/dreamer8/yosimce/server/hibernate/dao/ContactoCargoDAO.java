package com.dreamer8.yosimce.server.hibernate.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.ContactoCargo;
import com.dreamer8.yosimce.server.utils.SecurityFilter;

public class ContactoCargoDAO extends
		AbstractHibernateDAO<ContactoCargo, Integer> {

	public ContactoCargo findByName(String nombre) {

		ContactoCargo cc = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT cc.* FROM CONTACTO_CARGO cc"
				+ " WHERE cc.nombre='" + SecurityFilter.escapeString(nombre)
				+ "'";
		Query q = s.createSQLQuery(query).addEntity(ContactoCargo.class);
		cc = ((ContactoCargo) q.uniqueResult());
		return cc;
	}

}
