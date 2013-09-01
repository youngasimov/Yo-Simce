/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.Documento;
import com.dreamer8.yosimce.server.utils.SecurityFilter;

/**
 * @author jorge
 * 
 */
public class DocumentoDAO extends AbstractHibernateDAO<Documento, Integer> {
	public Documento findByCodigoANDTipoDocumento(String codigo, String tipo) {

		Documento d = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT d.* FROM DOCUMENTO d"
				+ " JOIN DOCUMENTO_ESTADO de ON (d.documento_estado_id=de.id AND de.nombre='"
				+ SecurityFilter.escapeString(tipo) + "')"
				+ " WHERE d.codigo='" + SecurityFilter.escapeString(codigo)
				+ "'";
		Query q = s.createSQLQuery(query).addEntity(Documento.class);
		d = ((Documento) q.uniqueResult());
		return d;
	}
}
