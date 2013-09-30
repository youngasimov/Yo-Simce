/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.SuplenteXCo;
import com.dreamer8.yosimce.server.utils.SecurityFilter;

/**
 * @author jorge
 * 
 */
public class SuplenteXCoDAO extends AbstractHibernateDAO<SuplenteXCo, Integer> {
	public SuplenteXCo findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdUsuario(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			Integer idUsuario) {

		SuplenteXCo sco = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT sco.* FROM FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id AND axnxat.actividad_tipo_id="
				+ SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN SUPLENTE_x_CO sco ON axnxat.id=aplicacion_x_nivel_x_actividad_tipo_id"
				+ " JOIN USUARIO_SELECCION us ON sco.usuario_seleccion_id=us.id"
				+ " JOIN USUARIO_x_APLICACION_x_NIVEL uxaxn ON (us.usuario_x_aplicacion_x_nivel_id=uxaxn.id AND uxaxn.usuario_id="
				+ SecurityFilter.escapeString(idUsuario) + ")";
		Query q = s.createSQLQuery(query).addEntity(SuplenteXCo.class);
		sco = ((SuplenteXCo) q.uniqueResult());
		return sco;
	}
}
