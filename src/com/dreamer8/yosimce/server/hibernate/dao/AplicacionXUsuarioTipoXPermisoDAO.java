/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.AplicacionXUsuarioTipoXPermiso;
import com.dreamer8.yosimce.server.utils.SecurityFilter;

/**
 * @author jorge
 * 
 */
public class AplicacionXUsuarioTipoXPermisoDAO extends
		AbstractHibernateDAO<AplicacionXUsuarioTipoXPermiso, Integer> {

	public List<AplicacionXUsuarioTipoXPermiso> findByIdAplicacionSortedByIdPermiso(
			Integer idAplicacion) {

		List<AplicacionXUsuarioTipoXPermiso> axutxps = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT axutxp.* FROM  APLICACION_x_USUARIO_TIPO_x_PERMISO axutxp"
				+ " JOIN APLICACION_x_USUARIO_TIPO axut ON (axutxp.aplicacion_x_usuario_tipo_id=axut.id AND axut.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ ")"
				+ " ORDER BY axutxp.permiso_id ASC, axutxp.aplicacion_usuario_tipo_id DESC";
		Query q = s.createSQLQuery(query).addEntity(
				AplicacionXUsuarioTipoXPermiso.class);
		axutxps = q.list();
		return axutxps;
	}

	public List<AplicacionXUsuarioTipoXPermiso> findByIdAplicacionANDIdPermiso(
			Integer idAplicacion, Integer idPermiso) {

		List<AplicacionXUsuarioTipoXPermiso> axutxps = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT axutxp.* FROM  APLICACION_x_USUARIO_TIPO_x_PERMISO axutxp"
				+ " JOIN APLICACION_x_USUARIO_TIPO axut ON (axutxp.aplicacion_x_usuario_tipo_id=axut.id AND axut.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axutxp.permiso_id="
				+ SecurityFilter.escapeString(idPermiso) + ")";
		Query q = s.createSQLQuery(query).addEntity(
				AplicacionXUsuarioTipoXPermiso.class);
		axutxps = q.list();
		return axutxps;
	}
}
