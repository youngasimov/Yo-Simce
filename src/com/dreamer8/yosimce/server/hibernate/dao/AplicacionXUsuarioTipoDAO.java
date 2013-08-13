/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.AplicacionXUsuarioTipo;
import com.dreamer8.yosimce.server.utils.SecurityFilter;

/**
 * @author jorge
 * 
 */
public class AplicacionXUsuarioTipoDAO extends
		AbstractHibernateDAO<AplicacionXUsuarioTipo, Integer> {

	public List<AplicacionXUsuarioTipo> findByIdAplicacionSortedByIdUsuarioTipo(Integer idAplicacion) {
		List<AplicacionXUsuarioTipo> axuts = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT axut.* FROM  APLICACION_x_USUARIO_TIPO axut"
				+ " WHERE axut.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " ORDER BY axut.usuario_tipo_id DESC";
		Query q = s.createSQLQuery(query).addEntity(
				AplicacionXUsuarioTipo.class);
		axuts = q.list();
		return axuts;
	}
}
