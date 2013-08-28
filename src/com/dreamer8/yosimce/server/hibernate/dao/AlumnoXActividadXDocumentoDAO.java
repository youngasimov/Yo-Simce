/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.AlumnoXActividadXDocumento;
import com.dreamer8.yosimce.server.hibernate.pojo.UsuarioTipo;
import com.dreamer8.yosimce.server.utils.SecurityFilter;

/**
 * @author jorge
 * 
 */
public class AlumnoXActividadXDocumentoDAO extends
		AbstractHibernateDAO<AlumnoXActividadXDocumento, Integer> {
	public List<AlumnoXActividadXDocumento> findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCurso(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			Integer idCurso) {

		List<AlumnoXActividadXDocumento> axaxds = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT DISTINCT axaxd.* FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id AND axnxat.actividad_tipo_id="
				+ SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN ACTIVIDAD a ON (axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id AND a.curso_id="
				+ SecurityFilter.escapeString(idCurso) + ")";

		Query q = s.createSQLQuery(query).addEntity(
				AlumnoXActividadXDocumento.class);
		axaxds = q.list();
		return axaxds;

	}
}
