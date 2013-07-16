/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.Establecimiento;
import com.dreamer8.yosimce.server.utils.SecurityFilter;

/**
 * @author jorge
 * 
 */
public class EstablecimientoDAO extends
		AbstractHibernateDAO<Establecimiento, Integer> {

	public List<Establecimiento> findEstablecimientosByActividadWithFilters(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			Integer offset, Integer lenght, Map<String, String> filtros) {

		List<Establecimiento> es = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT DISTINCT e.* FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.id_actividad="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.id_nivel="
				+ SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axna.aplicacion_x_nivel_id AND axnxat.actividad_tipo_id="
				+ SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id"
				+ " JOIN CURSO c ON a.curso_id=c.id"
				+ " JOIN ESTABLECIMIENTO e ON c.establecimiento_id=e.id";
		Query q = s.createSQLQuery(query).addEntity(Establecimiento.class);
		es = q.list();
		return es;
	}
}
