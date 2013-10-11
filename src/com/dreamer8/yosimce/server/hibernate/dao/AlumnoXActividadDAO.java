/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.AlumnoXActividad;
import com.dreamer8.yosimce.server.utils.SecurityFilter;
import com.dreamer8.yosimce.server.utils.StringUtils;

/**
 * @author jorge
 * 
 */
public class AlumnoXActividadDAO extends
		AbstractHibernateDAO<AlumnoXActividad, Integer> {
	public AlumnoXActividad findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCursoANDRutAlumno(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			Integer idCurso, String rutAlumno) {

		AlumnoXActividad axa = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT axa.* FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id AND axnxat.actividad_tipo_id="
				+ SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN ACTIVIDAD a ON (axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id AND a.curso_id="
				+ SecurityFilter.escapeString(idCurso) + ")"
				+ " JOIN ALUMNO_x_ACTIVIDAD axa ON a.id=axa.actividad_id"
				+ " JOIN ALUMNO al ON (axa.alumno_id=al.id AND al.rut='"
				+ SecurityFilter.escapeString(StringUtils.formatRut(rutAlumno))
				+ "')";
		Query q = s.createSQLQuery(query).addEntity(AlumnoXActividad.class);
		axa = ((AlumnoXActividad) q.uniqueResult());
		return axa;
	}

	public AlumnoXActividad findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdEstablecimientoANDRutAlumno(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			Integer iDEstablecimiento, String rutAlumno) {

		AlumnoXActividad axa = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT axa.* FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id AND axnxat.actividad_tipo_id="
				+ SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id"
				+ " JOIN CURSO c ON (a.curso_id=c.id AND c.establecimiento_id="
				+ SecurityFilter.escapeString(iDEstablecimiento) + ")"
				+ " JOIN ALUMNO_x_ACTIVIDAD axa ON a.id=axa.actividad_id"
				+ " JOIN ALUMNO al ON (axa.alumno_id=al.id AND al.rut='"
				+ SecurityFilter.escapeString(StringUtils.formatRut(rutAlumno))
				+ "')";
		Query q = s.createSQLQuery(query).addEntity(AlumnoXActividad.class);
		axa = ((AlumnoXActividad) q.uniqueResult());
		return axa;
	}

	public AlumnoXActividad findSinAlumnoByIdActividad(Integer idActividad) {

		AlumnoXActividad axa = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT axa.* FROM ALUMNO_x_ACTIVIDAD axa "
				+ " WHERE axa.actividad_id="
				+ SecurityFilter.escapeString(idActividad)
				+ " AND axa.alumno_id IS NULL";
		Query q = s.createSQLQuery(query).addEntity(AlumnoXActividad.class);
		axa = ((AlumnoXActividad) q.uniqueResult());
		return axa;
	}

	public AlumnoXActividad findByIdAlumnoANDActividad(Integer idAlumno,
			Integer idActividad) {

		AlumnoXActividad axa = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT axa.* FROM ALUMNO_x_ACTIVIDAD axa "
				+ " WHERE axa.actividad_id="
				+ SecurityFilter.escapeString(idActividad)
				+ " AND axa.alumno_id=" + SecurityFilter.escapeString(idAlumno);
		Query q = s.createSQLQuery(query).addEntity(AlumnoXActividad.class);
		axa = ((AlumnoXActividad) q.uniqueResult());
		return axa;
	}
}
