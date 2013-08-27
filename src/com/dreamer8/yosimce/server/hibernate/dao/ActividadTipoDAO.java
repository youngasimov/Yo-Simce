/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.ActividadTipo;
import com.dreamer8.yosimce.server.utils.SecurityFilter;

/**
 * @author jorge
 * 
 */
public class ActividadTipoDAO extends
		AbstractHibernateDAO<ActividadTipo, Integer> {

	/**
	 * @param idAplicacion
	 * @param idNivel
	 * @param id
	 * @return
	 */
	public List<ActividadTipo> findByIdAplicacionANDIdNivelANDIdUsuario(
			Integer idAplicacion, Integer idNivel, Integer idUsuario) {

		List<ActividadTipo> ats = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT DISTINCT at.* FROM USUARIO_x_APLICACION_x_NIVEL uxaxn"
				+ " JOIN APLICACION_x_NIVEL axn ON (uxaxn.aplicacion_x_nivel_id=axn.id AND axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ ")"
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON axn.id=axnxat.aplicacion_x_nivel_id"
				+ " JOIN ACTIVIDAD_TIPO at ON axnxat.actividad_tipo_id=at.id"
				+ " WHERE uxaxn.usuario_id="
				+ SecurityFilter.escapeString(idUsuario);
		Query q = s.createSQLQuery(query).addEntity(ActividadTipo.class);
		ats = q.list();
		return ats;
	}

	public ActividadTipo finByNombre(String nombre) {

		ActividadTipo at = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT at.* FROM ACTIVIDAD_TIPO at"
				+ " WHERE at.nombre='" + SecurityFilter.escapeString(nombre)
				+ "'";
		Query q = s.createSQLQuery(query).addEntity(ActividadTipo.class);
		at = ((ActividadTipo) q.uniqueResult());
		return at;
	}

}
