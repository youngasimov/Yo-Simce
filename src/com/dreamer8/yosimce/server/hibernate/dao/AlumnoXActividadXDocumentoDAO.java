/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.AlumnoXActividadXDocumento;
import com.dreamer8.yosimce.server.hibernate.pojo.DocumentoEstado;
import com.dreamer8.yosimce.server.hibernate.pojo.DocumentoTipo;
import com.dreamer8.yosimce.server.hibernate.pojo.UsuarioTipo;
import com.dreamer8.yosimce.server.utils.SecurityFilter;
import com.dreamer8.yosimce.server.utils.StringUtils;
import com.dreamer8.yosimce.shared.dto.EstadoSincronizacionDTO;
import com.dreamer8.yosimce.shared.dto.MaterialDefectuosoDTO;
import com.dreamer8.yosimce.shared.dto.SincAlumnoDTO;

/**
 * @author jorge
 * 
 */
public class AlumnoXActividadXDocumentoDAO extends
		AbstractHibernateDAO<AlumnoXActividadXDocumento, Integer> {
	public List<SincAlumnoDTO> findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCurso(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			Integer idCurso) {

		List<SincAlumnoDTO> sadtos = new ArrayList<SincAlumnoDTO>();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT DISTINCT axaxd.id as axaxd_id,al.nombres as al_nombres,"
				+ "al.apellido_paterno,al.apellido_materno,al.rut,d.codigo,"
				+ "de.id as doc_estado_id,de.nombre as doc_estado,axaxd.comentario as axaxd_com,"
				+ "axaxd_form.recibido IS NOT NULL FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id AND axnxat.actividad_tipo_id="
				+ SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN ACTIVIDAD a ON (axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id AND a.curso_id="
				+ SecurityFilter.escapeString(idCurso)
				+ ")"
				+ " JOIN ALUMNO_x_ACTIVIDAD axa ON a.id=axa.actividad_id"
				+ " JOIN ALUMNO al ON axa.alumno_id=al.id"
				+ " LEFT JOIN ALUMNO_x_ACTIVIDAD_x_DOCUMENTO axaxd ON axa.id=axaxd.alumno_x_actividad_id"
				+ " LEFT JOIN DOCUMENTO d ON axaxd.documento_id=d.id"
				+ " LEFT  JOIN DOCUMENTO_TIPO dt ON (d.documento_tipo_id=dt.id AND dt.nombre='"
				+ SecurityFilter.escapeString(DocumentoTipo.PRUEBA)
				+ "')"
				+ " LEFT  JOIN DOCUMENTO_ESTADO de ON axaxd.documento_estado_id=de.id"
				+ " LEFT JOIN ALUMNO_x_ACTIVIDAD_x_DOCUMENTO axaxd_form ON axa.id=axaxd_form.alumno_x_actividad_id"
				+ " LEFT  JOIN DOCUMENTO d_form ON axaxd_form.documento_id=d_form.id"
				+ " LEFT  JOIN DOCUMENTO_TIPO dt_form ON (d_form.documento_tipo_id=dt_form.id AND dt_form.nombre='"
				+ SecurityFilter.escapeString(DocumentoTipo.CUESTIONARIO_PADRE)
				+ "')";

		Query q = s.createSQLQuery(query);
		List<Object[]> os = q.list();
		SincAlumnoDTO sadto = null;
		EstadoSincronizacionDTO esdto = null;
		for (Object[] o : os) {
			sadto = new SincAlumnoDTO();
			sadto.setIdSincronizacion((Integer) o[0]);
			sadto.setNombres((String) o[1]);
			sadto.setApellidoPaterno((String) o[2]);
			sadto.setApellidoMaterno((String) o[3]);
			sadto.setRut((String) o[4]);
			sadto.setIdPendrive((String) o[5]);
			esdto = new EstadoSincronizacionDTO();
			esdto.setIdEstadoSincronizacion((Integer) o[6]);
			esdto.setNombreEstado((String) o[7]);
			sadto.setEstado(esdto);
			sadto.setComentario((String) o[8]);
			sadto.setEntregoFormulario((Boolean) o[9]);
			sadtos.add(sadto);
		}
		return sadtos;

	}

	public AlumnoXActividadXDocumento findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCursoANDIdAlumnoANDCodigoDocumento(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			Integer idCurso, Integer idAlumno, String codigo,
			String tipoDocumento) {

		AlumnoXActividadXDocumento axaxd = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT axaxd.* FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id AND axnxat.actividad_tipo_id="
				+ SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN ACTIVIDAD a ON (axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id AND a.curso_id="
				+ SecurityFilter.escapeString(idCurso)
				+ ")"
				+ " JOIN ALUMNO_x_ACTIVIDAD axa ON (a.id=axa.actividad_id AND axa.alumno_id="
				+ SecurityFilter.escapeString(idAlumno)
				+ ")"
				+ " JOIN ALUMNO_x_ACTIVIDAD_x_DOCUMENTO axaxd ON axa.id=axaxd.alumno_x_actividad_id"
				+ " JOIN DOCUMENTO d ON ( axaxd.documento_id=d.id AND d.codigo='"
				+ SecurityFilter.escapeString(codigo)
				+ "')"
				+ " JOIN DOCUMENTO_TIPO dt ON (d.documento_tipo_id=dt.id AND dt.nombre='"
				+ SecurityFilter.escapeString(tipoDocumento) + "')";
		Query q = s.createSQLQuery(query).addEntity(
				AlumnoXActividadXDocumento.class);
		axaxd = ((AlumnoXActividadXDocumento) q.uniqueResult());
		return axaxd;
	}

	public AlumnoXActividadXDocumento findByIdAlumnoXActividadANDCodigoDocumentoANDTipoDocumento(
			Integer idAlumnoXActividad, String codigo, String tipoDocumento) {

		AlumnoXActividadXDocumento axaxd = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT axaxd.* FROM ALUMNO_x_ACTIVIDAD_x_DOCUMENTO axaxd"
				+ " JOIN DOCUMENTO d ON ( axaxd.documento_id=d.id AND d.codigo='"
				+ SecurityFilter.escapeString(codigo)
				+ "')"
				+ " JOIN DOCUMENTO_TIPO dt ON (d.documento_tipo_id=dt.id AND dt.nombre='"
				+ SecurityFilter.escapeString(tipoDocumento)
				+ "')"
				+ " WHERE axaxd.alumno_x_actividad_id="
				+ SecurityFilter.escapeString(idAlumnoXActividad);
		Query q = s.createSQLQuery(query).addEntity(
				AlumnoXActividadXDocumento.class);
		axaxd = ((AlumnoXActividadXDocumento) q.uniqueResult());
		return axaxd;
	}

	public AlumnoXActividadXDocumento findByIdAlumnoXActividadANDIdDocumento(
			Integer idAlumnoXActividad, Integer idDocumento) {

		AlumnoXActividadXDocumento axaxd = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT axaxd.* FROM ALUMNO_x_ACTIVIDAD_x_DOCUMENTO axaxd"
				+ " WHERE axaxd.alumno_x_actividad_id="
				+ SecurityFilter.escapeString(idAlumnoXActividad)
				+ " AND axaxd.documento_id="
				+ SecurityFilter.escapeString(idDocumento);
		Query q = s.createSQLQuery(query).addEntity(
				AlumnoXActividadXDocumento.class);
		axaxd = ((AlumnoXActividadXDocumento) q.uniqueResult());
		return axaxd;
	}

	public AlumnoXActividadXDocumento findByIdAlumnoXActividadANDTipoDocumento(
			Integer idAlumnoXActividad, String tipoDocumento) {

		AlumnoXActividadXDocumento axaxd = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT axaxd.* FROM ALUMNO_x_ACTIVIDAD_x_DOCUMENTO axaxd"
				+ " JOIN DOCUMENTO d ON axaxd.documento_id=d.id"
				+ " JOIN DOCUMENTO_TIPO dt ON (d.documento_tipo_id=dt.id AND dt.nombre='"
				+ SecurityFilter.escapeString(tipoDocumento)
				+ "')"
				+ " WHERE axaxd.alumno_x_actividad_id="
				+ SecurityFilter.escapeString(idAlumnoXActividad);
		Query q = s.createSQLQuery(query).addEntity(
				AlumnoXActividadXDocumento.class);
		axaxd = ((AlumnoXActividadXDocumento) q.uniqueResult());
		return axaxd;
	}

	public List<MaterialDefectuosoDTO> findDefectuososByIdctividadANDTipoDocumento(
			Integer idActividad, String tipoDocumento) {

		List<MaterialDefectuosoDTO> mddtos = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT d.codigo,de.id as doc_estado_id,de.nombre as doc_estado FROM ALUMNO_x_ACTIVIDAD axa"
				+ " JOIN ALUMNO_x_ACTIVIDAD_x_DOCUMENTO axaxd ON (axa.id=axaxd.alumno_x_actividad_id AND axa.actividad_id="
				+ SecurityFilter.escapeString(idActividad)
				+ " AND axa.alumno_id IS NULL)"
				+ " JOIN DOCUMENTO d ON axaxd.documento_id=d.id"
				+ " JOIN DOCUMENTO_TIPO dt ON (d.documento_tipo_id=dt.id AND dt.nombre='"
				+ SecurityFilter.escapeString(tipoDocumento)
				+ "')"
				+ " LEFT  JOIN DOCUMENTO_ESTADO de ON axaxd.documento_estado_id=de.id";
		Query q = s.createSQLQuery(query);
		List<Object[]> os = q.list();
		MaterialDefectuosoDTO mddto = null;
		EstadoSincronizacionDTO esdto = null;
		for (Object[] o : os) {
			mddto = new MaterialDefectuosoDTO();
			mddto.setIdMaterial((String) o[0]);
			esdto = new EstadoSincronizacionDTO();
			esdto.setIdEstadoSincronizacion((Integer) o[1]);
			esdto.setNombreEstado((String) o[2]);
			mddto.setEstado(esdto);
			mddtos.add(mddto);
		}
		return mddtos;
	}
}
