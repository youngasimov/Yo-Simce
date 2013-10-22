/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.ActividadXDocumentoTipo;
import com.dreamer8.yosimce.server.utils.SecurityFilter;

/**
 * @author jorge
 * 
 */
public class ActividadXDocumentoTipoDAO extends
		AbstractHibernateDAO<ActividadXDocumentoTipo, Integer> {
	/**
	 * 
	 */
	public ActividadXDocumentoTipoDAO() {
		// TODO Auto-generated constructor stub
	}

	public ActividadXDocumentoTipoDAO(Session s) {
		super();
		setSession(s);
	}

	public ActividadXDocumentoTipo findByIdActividadANDDocumentoTipo(
			Integer idActividad, String documentoTipo) {

		ActividadXDocumentoTipo axdt = null;
		Session s = getSession();
		String query = "SELECT axdt.* FROM ACTIVIDAD_x_DOCUMENTO_TIPO axdt"
				+ " JOIN DOCUMENTO_TIPO dt ON (axdt.documento_tipo_id=dt.id AND dt.nombre='"
				+ SecurityFilter.escapeString(documentoTipo) + "')"
				+ " WHERE axdt.actividad_id="
				+ SecurityFilter.escapeString(idActividad);
		Query q = s.createSQLQuery(query).addEntity(
				ActividadXDocumentoTipo.class);
		axdt = ((ActividadXDocumentoTipo) q.uniqueResult());
		return axdt;
	}
}
