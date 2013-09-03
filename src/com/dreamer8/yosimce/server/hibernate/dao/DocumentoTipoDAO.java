/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.DocumentoTipo;
import com.dreamer8.yosimce.server.utils.SecurityFilter;

/**
 * @author jorge
 * 
 */
public class DocumentoTipoDAO extends
		AbstractHibernateDAO<DocumentoTipo, Integer> {
	public DocumentoTipo findByNombre(String nombre) {

		DocumentoTipo dt = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT dt.* FROM DOCUMENTO_TIPO dt"
				+ " WHERE dt.nombre='" + SecurityFilter.escapeString(nombre)
				+ "'";
		Query q = s.createSQLQuery(query).addEntity(DocumentoTipo.class);
		dt = ((DocumentoTipo) q.uniqueResult());
		return dt;
	}
}
