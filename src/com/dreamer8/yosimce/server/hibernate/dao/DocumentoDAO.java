/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import java.math.BigInteger;
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
	/**
	 * 
	 */
	public DocumentoDAO() {
		// TODO Auto-generated constructor stub
	}

	public DocumentoDAO(Session s) {
		super();
		setSession(s);
	}

	public Documento findByCodigoANDTipoDocumento(String codigo, String tipo) {

		Documento d = null;
		Session s = getSession();
		String query = "SELECT d.* FROM DOCUMENTO d" + " JOIN DOCUMENTO_TIPO dt ON (d.documento_tipo_id=dt.id AND dt.nombre='"
				+ SecurityFilter.escapeString(tipo) + "')" + " WHERE d.codigo='" + SecurityFilter.escapeString(codigo) + "'";
		Query q = s.createSQLQuery(query).addEntity(Documento.class);
		d = ((Documento) q.uniqueResult());
		return d;
	}

	public Documento findByCodigo(String codigo) {

		Documento d = null;
		Session s = getSession();
		String query = "SELECT d.* FROM DOCUMENTO d"
		+ " WHERE d.codigo='" + SecurityFilter.escapeString(codigo) + "'";
		Query q = s.createSQLQuery(query).addEntity(Documento.class);
		d = ((Documento) q.uniqueResult());
		return d;
	}

	public Documento findByIdArchivo(Integer idArchivo) {

		Documento d = null;
		Session s = getSession();
		String query = "SELECT d.* FROM DOCUMENTO d" + " WHERE d.archivo_id=" + SecurityFilter.escapeString(idArchivo);
		Query q = s.createSQLQuery(query).addEntity(Documento.class);
		d = ((Documento) q.uniqueResult());
		return d;
	}

	public List<Documento> findByIdActividadANDDocumentoTipo(Integer idActividad, String documentoTipo) {

		List<Documento> axds = null;
		Session s = getSession();
		String query = "SELECT d.* FROM  ACTIVIDAD_x_DOCUMENTO axd" + " JOIN DOCUMENTO d ON (axd.documento_id=d.id AND axd.actividad_id="
				+ SecurityFilter.escapeString(idActividad) + ")" + " JOIN DOCUMENTO_TIPO dt ON d.documento_tipo_id=dt.id" + " WHERE dt.nombre='"
				+ SecurityFilter.escapeString(documentoTipo) + "'" + " ORDER BY d.id ASC";
		Query q = s.createSQLQuery(query).addEntity(Documento.class);
		axds = q.list();
		return axds;
	}

	public Integer countByIdAplicacionANDIdNivelANDTipoDocumento(Integer idAplicacion, Integer idNivel, String tipoDocumento, String tipoDocumento2) {

		Integer result = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT COUNT(documento_id)  FROM APLICACION_x_NIVEL axn " + " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion) + " AND axn.nivel_id=" + SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id)" + " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id"
				+ " JOIN ACTIVIDAD_x_DOCUMENTO axd ON a.id=axd.actividad_id" + " JOIN DOCUMENTO d ON axd.documento_id=d.id"
				+ " JOIN DOCUMENTO_TIPO dt ON d.documento_tipo_id=dt.id" + " WHERE dt.nombre='" + SecurityFilter.escapeString(tipoDocumento) + "'";
		if (tipoDocumento2 != null && !tipoDocumento2.isEmpty()) {
			query += " OR dt.nombre='" + SecurityFilter.escapeString(tipoDocumento2) + "'";
		}
		Query q = s.createSQLQuery(query).addEntity(Integer.class);
		result = ((BigInteger) q.uniqueResult()).intValue();
		return result;
	}

	public Integer countProcesadosByIdAplicacionANDIdNivelANDTipoDocumento(Integer idAplicacion, Integer idNivel, String tipoDocumento, String tipoDocumento2) {

		Integer result = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT COUNT(documento_id)  FROM APLICACION_x_NIVEL axn " + " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion) + " AND axn.nivel_id=" + SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id)" + " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id"
				+ " JOIN ACTIVIDAD_x_DOCUMENTO axd ON a.id=axd.actividad_id" + " JOIN DOCUMENTO d ON axd.documento_id=d.id AND d.documento_estado_id=1"
				+ " JOIN DOCUMENTO_TIPO dt ON d.documento_tipo_id=dt.id" + " WHERE dt.nombre='" + SecurityFilter.escapeString(tipoDocumento) + "'";
		if (tipoDocumento2 != null && !tipoDocumento2.isEmpty()) {
			query += " OR dt.nombre='" + SecurityFilter.escapeString(tipoDocumento2) + "'";
		}
		Query q = s.createSQLQuery(query).addEntity(Integer.class);
		result = ((BigInteger) q.uniqueResult()).intValue();
		return result;
	}
}
