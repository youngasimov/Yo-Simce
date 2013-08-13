/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.Region;
import com.dreamer8.yosimce.server.utils.SecurityFilter;

/**
 * @author jorge
 * 
 */
public class RegionDAO extends AbstractHibernateDAO<Region, Integer> {
	public List<Region> findByIdAplicacionANDIdNivelANDIdActividadTipo(Integer idAplicacion,
			Integer idNivel, Integer idActividadTipo) {

		List<Region> rs = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT DISTINCT r.* FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.id_actividad="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.id_nivel="
				+ SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id AND axnxat.actividad_tipo_id="
				+ SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN APLICACION a ON axnxat.id=a.aplicacion_x_nivel_actividad_tipo_id"
				+ " JOIN CURSO c ON a.curso_id=c.id"
				+ " JOIN ESTABLECIMIENTO e ON c.establecimiento_id=e.id"
				+ " JOIN COMUNA co ON e.comuna_id=co.id"
				+ " JOIN PROVINCIA p ON co.provincia_id=p.id"
				+ " JOIN REGION r ON p.region_id=r.id";
		Query q = s.createSQLQuery(query).addEntity(Region.class);
		rs = q.list();
		return rs;
	}
}
