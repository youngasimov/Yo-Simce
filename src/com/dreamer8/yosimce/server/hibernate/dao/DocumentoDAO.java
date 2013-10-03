/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.ActividadXDocumento;
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
				+ " JOIN DOCUMENTO_TIPO dt ON (d.documento_tipo_id=dt.id AND dt.nombre='"
				+ SecurityFilter.escapeString(tipo) + "')"
				+ " WHERE d.codigo='" + SecurityFilter.escapeString(codigo)
				+ "'";
		Query q = s.createSQLQuery(query).addEntity(Documento.class);
		d = ((Documento) q.uniqueResult());
		return d;
	}

	public Documento findByIdArchivo(Integer idArchivo) {

		Documento d = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT d.* FROM DOCUMENTO d" + " WHERE d.archivo_id="
				+ SecurityFilter.escapeString(idArchivo);
		Query q = s.createSQLQuery(query).addEntity(Documento.class);
		d = ((Documento) q.uniqueResult());
		return d;
	}

	public List<Documento> findByIdActividadANDDocumentoTipo(
			Integer idActividad, String documentoTipo) {

		List<Documento> axds = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT d.* FROM  ACTIVIDAD_x_DOCUMENTO axd"
				+ " JOIN DOCUMENTO d ON (axd.documento_id=d.id AND axd.actividad_id="
				+ SecurityFilter.escapeString(idActividad) + ")"
				+ " JOIN DOCUMENTO_TIPO dt ON d.documento_tipo_id=dt.id"
				+ " WHERE dt.nombre='"
				+ SecurityFilter.escapeString(documentoTipo) + "'"
						+ " ORDER BY d.id ASC";
		Query q = s.createSQLQuery(query).addEntity(Documento.class);
		axds = q.list();
		return axds;
	}
}
