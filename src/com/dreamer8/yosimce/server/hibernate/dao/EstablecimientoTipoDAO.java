/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.EstablecimientoTipo;
import com.dreamer8.yosimce.server.utils.SecurityFilter;

/**
 * @author jorge
 * 
 */
public class EstablecimientoTipoDAO extends
		AbstractHibernateDAO<EstablecimientoTipo, Integer> {
	/**
	 * 
	 */
	public EstablecimientoTipoDAO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	public EstablecimientoTipoDAO(Session s) {
		super();
		setSession(s);
	}

	public List<EstablecimientoTipo> findAll() {

		List<EstablecimientoTipo> ets = null;
		Session s = getSession();
		String query = "SELECT et.* FROM ESTABLECIMIENTO_TIPO et"
				+ " ORDER et.id ASC";
		Query q = s.createSQLQuery(query).addEntity(EstablecimientoTipo.class);
		ets = q.list();
		return ets;
	}

	public EstablecimientoTipo findByIdAplicacionANDIdEstablecimiento(
			Integer idAplicacion, Integer idEstablecimiento) {

		EstablecimientoTipo et = null;
		Session s = getSession();
		String query = "SELECT et.* FROM ESTABLECIMIENTO_TIPO et"
				+ " JOIN APLICACION_x_ESTABLECIMIENTO axe ON axe.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axe.establecimiento_id="
				+ SecurityFilter.escapeString(idEstablecimiento)
				+ " AND et.id=axe.establecimiento_tipo_id";
		Query q = s.createSQLQuery(query).addEntity(EstablecimientoTipo.class);
		et = ((EstablecimientoTipo) q.uniqueResult());
		return et;
	}

	public EstablecimientoTipo findByNombre(String nombre) {

		EstablecimientoTipo et = null;
		Session s = getSession();
		String query = "SELECT et.* FROM ESTABLECIMIENTO_TIPO et"
				+ " WHERE et.nombre='" + SecurityFilter.escapeString(nombre)
				+ "'";
		Query q = s.createSQLQuery(query).addEntity(EstablecimientoTipo.class);
		et = ((EstablecimientoTipo) q.uniqueResult());
		return et;
	}
}
