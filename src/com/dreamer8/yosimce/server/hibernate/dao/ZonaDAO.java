/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.Zona;
import com.dreamer8.yosimce.server.utils.SecurityFilter;

/**
 * @author jorge
 * 
 */
public class ZonaDAO extends AbstractHibernateDAO<Zona, Integer> {
	public List<Zona> findByIdAplicacion(Integer idAplicacion) {

		List<Zona> zs = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT DISTINCT z.* FROM  cr "
				+ " JOIN ZONA z ON (cr.id=z.centro_regional_id AND cr.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion) + ")";
		Query q = s.createSQLQuery(query).addEntity(Zona.class);
		zs = q.list();
		return zs;
	}

	public List<Zona> findByIdAplicacionANDIdComuna(Integer idAplicacion,
			Integer idComuna) {

		List<Zona> zs = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT DISTINCT z.* FROM  cr "
				+ " JOIN ZONA z ON (cr.id=z.centro_regional_id AND cr.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion) + ")"
				+ " JOIN CO ON (z.id=co.zona_id AND co.comuna_id="
				+ SecurityFilter.escapeString(idComuna) + ")";
		Query q = s.createSQLQuery(query).addEntity(Zona.class);
		zs = q.list();
		return zs;
	}

	public List<Zona> findByIdAplicacionANDIdRegion(Integer idAplicacion,
			Integer idRegion) {

		List<Zona> zs = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT DISTINCT z.* FROM  cr "
				+ " JOIN ZONA z ON (cr.id=z.centro_regional_id AND cr.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ ")"
				+ " JOIN CO ON z.id=co.zona_id "
				+ " JOIN COMUNA com ON co.comuna_id=com.id"
				+ " JOIN PROVINCIA p ON (com.provincia_id=p.id AND p.region_id="
				+ SecurityFilter.escapeString(idRegion) + ")";
		Query q = s.createSQLQuery(query).addEntity(Zona.class);
		zs = q.list();
		return zs;
	}
}
