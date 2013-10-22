/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.DocumentoEstado;

/**
 * @author jorge
 * 
 */
public class DocumentoEstadoDAO extends
		AbstractHibernateDAO<DocumentoEstado, Integer> {
	/**
	 * 
	 */
	public DocumentoEstadoDAO() {
		// TODO Auto-generated constructor stub
	}

	public DocumentoEstadoDAO(Session s) {
		super();
		setSession(s);
	}

	public List<DocumentoEstado> findForSincronizacion() {

		List<DocumentoEstado> des = null;
		Session s = getSession();
		String query = "SELECT de.* FROM  DOCUMENTO_ESTADO de"
				+ " WHERE de.id <= 6";
		Query q = s.createSQLQuery(query).addEntity(DocumentoEstado.class);
		des = q.list();
		return des;
	}

	public List<DocumentoEstado> findForSincronizacionFallida() {

		List<DocumentoEstado> des = null;
		Session s = getSession();
		String query = "SELECT de.* FROM  DOCUMENTO_ESTADO de"
				+ " WHERE de.id < 6 AND de.id > 1";
		Query q = s.createSQLQuery(query).addEntity(DocumentoEstado.class);
		des = q.list();
		return des;
	}
}
