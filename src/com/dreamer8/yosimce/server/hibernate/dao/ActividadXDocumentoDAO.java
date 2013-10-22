/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.ActividadXDocumento;
import com.dreamer8.yosimce.server.hibernate.pojo.ActividadXDocumentoId;
import com.dreamer8.yosimce.server.utils.SecurityFilter;

/**
 * @author jorge
 * 
 */
public class ActividadXDocumentoDAO extends
		AbstractHibernateDAO<ActividadXDocumento, ActividadXDocumentoId> {

	/**
	 * 
	 */
	public ActividadXDocumentoDAO() {
		// TODO Auto-generated constructor stub
	}

	public ActividadXDocumentoDAO(Session s) {
		super();
		setSession(s);
	}

	public ActividadXDocumento findByIdActividadANDIdDocumento(
			Integer idActividad, Integer idDocumento) {

		ActividadXDocumento axd = null;
		Session s = getSession();
		String query = "SELECT axd.* FROM ACTIVIDAD_x_DOCUMENTO axd"
				+ " WHERE axd.actividad_id="
				+ SecurityFilter.escapeString(idActividad)
				+ " AND axd.documento_id="
				+ SecurityFilter.escapeString(idDocumento);
		Query q = s.createSQLQuery(query).addEntity(ActividadXDocumento.class);
		axd = ((ActividadXDocumento) q.uniqueResult());
		return axd;
	}

	public List<ActividadXDocumento> findByIdActividadANDDocumentoTipo(
			Integer idActividad, String documentoTipo) {

		List<ActividadXDocumento> axds = null;
		Session s = getSession();
		String query = "SELECT axd.* FROM  ACTIVIDAD_x_DOCUMENTO axd"
				+ " JOIN DOCUMENTO d ON (axd.documento_id=d.id AND axd.actividad_id="
				+ SecurityFilter.escapeString(idActividad) + ")"
				+ " JOIN DOCUMENTO_TIPO dt ON d.documento_tipo_id=dt.id"
				+ " WHERE dt.nombre='"
				+ SecurityFilter.escapeString(documentoTipo) + "'";
		Query q = s.createSQLQuery(query).addEntity(ActividadXDocumento.class);
		axds = q.list();
		return axds;
	}
}
