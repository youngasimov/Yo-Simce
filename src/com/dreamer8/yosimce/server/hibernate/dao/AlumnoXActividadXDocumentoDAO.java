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
		String query = "SELECT DISTINCT axaxd.id as axaxd_id,al.nombre as al_nombre,"
				+ "al.apellido_paterno,al.apellido_paterno_materno,al.rut,d.codigo,"
				+ "de.nombre doc_estado,axaxd.comentario axaxd_com  FROM APLICACION_x_NIVEL axn "
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
				+ " JOIN ALUMNO_x_ACTIVIDAD_x_DOCUMENTO axaxd ON axa.id=axaxd.alumno_x_actividad_id"
				+ " JOIN DOCUMENTO d ON axaxd.documento_id=d.id"
				+ " JOIN DOCUMENTO_TIPO dt ON (d.documento_tipo_id=dt.id AND dt.nombre='"
				+ SecurityFilter.escapeString(DocumentoTipo.PRUEBA)
				+ "')"
				+ " JOIN DOCUMENTO_ESTADO de ON axaxd.documento_estado_id=de.id";

		Query q = s.createSQLQuery(query);
		List<Object[]> os = q.list();
		SincAlumnoDTO sadto = null;
		for (Object[] o : os) {
			sadto = new SincAlumnoDTO();
			sadto.setIdSincronizacion((Integer) o[0]);
			sadto.setNombres((String) o[1]);
			sadto.setApellidoPaterno((String) o[2]);
			sadto.setApellidoMaterno((String) o[3]);
			sadto.setRut((String) o[4]);
			sadto.setIdPendrive((String) o[5]);
			sadto.setSincronizado(DocumentoEstado.SINCRONIZADO
					.equals((String) o[6]));
			sadto.setComentario((String) o[7]);
			sadtos.add(sadto);
		}
		return sadtos;

	}
}
