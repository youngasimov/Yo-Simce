/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.client.actividad.ActividadService;
import com.dreamer8.yosimce.server.hibernate.pojo.Alumno;
import com.dreamer8.yosimce.server.hibernate.pojo.DocumentoTipo;
import com.dreamer8.yosimce.server.hibernate.pojo.UsuarioTipo;
import com.dreamer8.yosimce.server.utils.SecurityFilter;
import com.dreamer8.yosimce.server.utils.StringUtils;

/**
 * @author jorge
 * 
 */
public class AlumnoDAO extends AbstractHibernateDAO<Alumno, Integer> {

	public Alumno findByRut(String rut) {
		Alumno a = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT a.* FROM ALUMNO a " + " WHERE a.rut='"
				+ SecurityFilter.escapeString(StringUtils.formatRut(rut)) + "'";
		Query q = s.createSQLQuery(query).addEntity(Alumno.class);
		a = ((Alumno) q.uniqueResult());
		return a;
	}

	public List<String> findAlumnosCsvByIdAplicacionANDIdNivelANDIdTipoActividadANDFiltros(
			Integer idAplicacion, Integer idNivel,Integer idActividadTipo, Integer idUsuario,
			String usuarioTipo, Integer offset, Integer lenght,
			Map<String, String> filtros) {

		List<String> csv = new ArrayList<String>();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT DISTINCT c.id as curso_id,e.id as establecimiento_id,e.nombre as establecimiento_nombre,"
				+ "et.nombre as est_tipo_nombre,"
				+ "r.nombre as region_nombre,COMUNA.id as comuna_id,COMUNA.nombre as comuna_nombre,"
				+ "at.nombre as act_tipo_nombre,a.total_alumnos,al.rut,al.nombres,al.apellido_paterno,al.apellido_materno,"
				+ "alt.nombre as al_tipo,ale.nombre as al_estado,d_pendrive.codigo,de_pendrive.nombre as pendrive_estado,"
				+ "axaxd_cuest.entregado,axa.id"
				+ " FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id AND axnxat.actividad_tipo_id="
				+ SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN ACTIVIDAD_TIPO at ON axnxat.actividad_tipo_id=at.id"
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id"
				+ " LEFT JOIN ACTIVIDAD_ESTADO ae ON a.actividad_estado_id=ae.id"
				+ " JOIN ALUMNO_x_ACTIVIDAD axa ON a.id=axa.actividad_id"
				+ " JOIN ALUMNO al ON axa.alumno_id=al.id"
				+ " JOIN ALUMNO_TIPO alt ON axa.alumno_tipo_id=alt.id"
				+ " JOIN ALUMNO_ESTADO ale ON axa.alumno_estado_id=ale.id"
				+ " LEFT JOIN ALUMNO_x_ACTIVIDAD_x_DOCUMENTO axaxd_pendrive ON axa.id=axaxd_pendrive.alumno_x_actividad_id"
				+ " LEFT JOIN DOCUMENTO_ESTADO de_pendrive ON axaxd_pendrive.documento_estado_id=de_pendrive.id"
				+ " LEFT JOIN DOCUMENTO d_pendrive ON axaxd_pendrive.documento_id=d_pendrive.id"
				+ " LEFT JOIN DOCUMENTO_TIPO dt_pendrive ON (d_pendrive.documento_tipo_id=dt_pendrive.id AND dt_pendrive.nombre='"
				+ SecurityFilter.escapeString(DocumentoTipo.PRUEBA)
				+ "')"
				+ " LEFT JOIN ALUMNO_x_ACTIVIDAD_x_DOCUMENTO axaxd_cuest ON axa.id=axaxd_cuest.alumno_x_actividad_id"
				+ " LEFT JOIN DOCUMENTO d_cuest ON axaxd_cuest.documento_id=d_cuest.id"
				+ " LEFT JOIN DOCUMENTO_TIPO dt_cuest ON (d_cuest.documento_tipo_id=dt_cuest.id AND dt_cuest.nombre='"
				+ SecurityFilter.escapeString(DocumentoTipo.CUESTIONARIO_PADRE)
				+ "')"
				+ " JOIN CURSO c ON a.curso_id=c.id"
				+ " JOIN ESTABLECIMIENTO e ON c.establecimiento_id=e.id"
				+ " LEFT JOIN APLICACION_x_ESTABLECIMIENTO axe ON (e.id=axe.establecimiento_id AND axe.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ ")"
				+ " LEFT JOIN ESTABLECIMIENTO_TIPO et ON axe.establecimiento_tipo_id=et.id"
				+ " JOIN COMUNA ON e.comuna_id=COMUNA.id"
				+ " JOIN PROVINCIA p ON COMUNA.provincia_id=p.id"
				+ " JOIN REGION r ON p.region_id=r.id";
		;
		if (usuarioTipo.equals(UsuarioTipo.JEFE_REGIONAL)
				|| usuarioTipo.equals(UsuarioTipo.JEFE_ZONAL)
				|| usuarioTipo.equals(UsuarioTipo.JEFE_CENTRO_OPERACIONES) || usuarioTipo.equals(UsuarioTipo.LOGISTICA_Y_SOPORTE)) {
			query += " JOIN CO_x_ESTABLECIMIENTO coxe ON (e.id=coxe.establecimiento_id  AND axn.id=coxe.aplicacion_x_nivel_id)";
			if (usuarioTipo.equals(UsuarioTipo.JEFE_CENTRO_OPERACIONES) || usuarioTipo.equals(UsuarioTipo.LOGISTICA_Y_SOPORTE)) {
				query += " JOIN JO_x_CO joxco ON (coxe.co_id=joxco.co_id AND joxco.jo_id="
						+ SecurityFilter.escapeString(idUsuario)
						+ ") AND joxco.activo=TRUE";
			} else {
				query += " JOIN CO co ON coxe.co_id=co.id";
				if (usuarioTipo.equals(UsuarioTipo.JEFE_ZONAL)) {
					query += " JOIN JZ_x_ZONA jzxz ON (co.zona_id=jzxz.zona_id AND jzxz.jz_id="
							+ SecurityFilter.escapeString(idUsuario)
							+ ") AND jzxz.activo=TRUE";
				} else {
					query += " JOIN ZONA z ON co.zona_id=z.id"
							+ " JOIN JR_x_CENTRO_REGIONAL jrxcr ON (z.centro_regional_id=jrxcr.centro_regional_id AND jrxcr.jr_id="
							+ SecurityFilter.escapeString(idUsuario)
							+ ") AND jrxcr.activo=TRUE";
				}
			}
		} else if (usuarioTipo.equals(UsuarioTipo.SUPERVISOR)
				|| usuarioTipo.equals(UsuarioTipo.SUPERVISOR_CON_AUTO)
				|| usuarioTipo.equals(UsuarioTipo.EXAMINADOR)
				|| usuarioTipo.equals(UsuarioTipo.EXAMINADOR_NEE)
				|| usuarioTipo.equals(UsuarioTipo.COORDINADOR_COMPUTACION)) {
			query += " JOIN USUARIO_x_ACTIVIDAD uxa ON a.id=uxa.actividad_id"
					+ " JOIN USUARIO_SELECCION us ON uxa.usuario_seleccion_id=us.id"
					+ " JOIN USUARIO_x_APLICACION_x_NIVEL uxaxn ON (us.usuario_x_aplicacion_x_nivel_id=uxaxn.id AND uxaxn.usuario_id="
					+ SecurityFilter.escapeString(idUsuario) + ")";
		}
		if (filtros != null && !filtros.isEmpty()) {
			String where = "";
			String opciones = "";
			for (String key : filtros.keySet()) {
				if (!filtros.get(key).equals("")) {
					if (key.equals(ActividadService.FKEY_COMUNA)) {
						if (!where.equals("")) {
							where += " AND ";
						}
						where += " e.comuna_id ="
								+ SecurityFilter.escapeString(filtros.get(key));
					} else if (key.equals(ActividadService.FKEY_REGION)) {
						if (!where.equals("")) {
							where += " AND ";
						}
						where += " p.region_id ="
								+ SecurityFilter.escapeString(filtros.get(key));
					} else if (key.equals(ActividadService.FKEY_ESTADOS)) {
						if (!where.equals("")) {
							where += " AND ";
						}
						String[] estados = filtros.get(key).split(
								ActividadService.SEPARATOR);
						if (estados.length != 0) {
							where += " (";
							for (int i = 0; i < estados.length; i++) {
								where += "a.actividad_estado_id="
										+ SecurityFilter
												.escapeString(estados[i]);
								if (i < estados.length - 1) {
									where += " OR ";
								}
							}
							where += ") ";
						}
					} else if (key
							.equals(ActividadService.FKEY_ACTIVIDADES_CONTINGENCIA)) {
						if (!opciones.equals("")) {
							opciones += " OR ";
						}
						// opciones += " contingencia=true";
						opciones += " false";
					} else if (key
							.equals(ActividadService.FKEY_ACTIVIDADES_CONTINGENCIA_INHABILITANTE)) {
						if (!opciones.equals("")) {
							opciones += " OR ";
						}
						// opciones += " limitante=true";
						opciones += " false";
					} else if (key
							.equals(ActividadService.FKEY_ACTIVIDADES_MATERIAL_CONTINGENCIA)) {
						if (!opciones.equals("")) {
							opciones += " OR ";
						}
						opciones += " a.material_contingencia=true";
					} else if (key
							.equals(ActividadService.FKEY_ACTIVIDADES_NO_SINCRONIZADAS)) {
						if (!opciones.equals("")) {
							opciones += " OR ";
						}
						opciones += " (a.total_alumnos_presentes IS NULL OR a.total_alumnos_presentes=0)";
					} else if (key
							.equals(ActividadService.FKEY_ACTIVIDADES_PARCIALMENTE_SINCRONIZADAS)) {
						if (!opciones.equals("")) {
							opciones += " OR ";
						}
						opciones += " (a.total_alumnos_presentes IS NOT NULL AND a.total_alumnos_presentes!=0  AND a.total_alumnos_presentes!=a.total_alumnos)";
					} else if (key
							.equals(ActividadService.FKEY_ACTIVIDADES_SINCRONIZADAS)) {
						if (!opciones.equals("")) {
							opciones += " OR ";
						}
						opciones += " (a.total_alumnos_presentes IS NOT NULL AND a.total_alumnos_presentes=a.total_alumnos)";
					}
				}
			}
			if (!where.equals("") || !opciones.equals("")) {
				query += " WHERE ";
			}
			query += where;
			if (!opciones.equals("")) {
				if (!where.equals("")) {
					query += " AND ";
				}
				query += "(" + opciones + ")";
			}

		}
		query += " ORDER BY COMUNA.id,e.id,c.id,axa.id";
		Query q = s.createSQLQuery(query);
		if (offset != null) {
			q.setFirstResult(offset);
		}
		if (lenght != null) {
			q.setMaxResults(lenght);
		}
		List<Object[]> os = q.list();
		String linea = null;
		if (os != null && !os.isEmpty()) {
			
			for (Object[] o : os) {
				linea = Integer.toString((Integer) o[1]) + ";"
						+ ((String) o[2]) + ";" + ((String) o[3]) + ";"
						+ ((String) o[4]) + ";" + ((String) o[6]) + ";"
						+ ((String) o[7]) + ";" + ((Integer) o[8]) + ";"
						+ ((String) o[9]) + ";" + ((String) o[11]) + ";"
						+ ((String) o[12]) + ";" + ((String) o[10]) + ";"
						+ ((String) o[13]) + ";" + ((String) o[14]) + ";"
						+ ((String) o[15]) + ";" + ((String) o[16]) + ";"
						+ ((Boolean) o[17]) + ";";
				csv.add(linea);
			}
		}
		return csv;
	}

	public Integer countAlumnosCsvByIdAplicacionANDIdNivelANDIdTipoActividadANDFiltros(
			Integer idAplicacion, Integer idNivel,Integer idActividadTipo, Integer idUsuario,
			String usuarioTipo, Map<String, String> filtros) {

		Integer result = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT COUNT(al.id)"
				+ " FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id AND axnxat.actividad_tipo_id="
				+ SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN ACTIVIDAD_TIPO at ON axnxat.actividad_tipo_id=at.id"
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id"
				+ " LEFT JOIN ACTIVIDAD_ESTADO ae ON a.actividad_estado_id=ae.id"
				+ " JOIN ALUMNO_x_ACTIVIDAD axa ON a.id=axa.actividad_id"
				+ " JOIN ALUMNO al ON axa.alumno_id=al.id"
				+ " JOIN ALUMNO_TIPO alt ON axa.alumno_tipo_id=alt.id"
				+ " JOIN ALUMNO_ESTADO ale ON axa.alumno_estado_id=ale.id"
				+ " LEFT JOIN ALUMNO_x_ACTIVIDAD_x_DOCUMENTO axaxd_pendrive ON axa.id=axaxd_pendrive.alumno_x_actividad_id"
				+ " LEFT JOIN DOCUMENTO_ESTADO de_pendrive ON axaxd_pendrive.documento_estado_id=de_pendrive.id"
				+ " LEFT JOIN DOCUMENTO d_pendrive ON axaxd_pendrive.documento_id=d_pendrive.id"
				+ " LEFT JOIN DOCUMENTO_TIPO dt_pendrive ON (d_pendrive.documento_tipo_id=dt_pendrive.id AND dt_pendrive.nombre='"
				+ SecurityFilter.escapeString(DocumentoTipo.PRUEBA)
				+ "')"
				+ " LEFT JOIN ALUMNO_x_ACTIVIDAD_x_DOCUMENTO axaxd_cuest ON axa.id=axaxd_cuest.alumno_x_actividad_id"
				+ " LEFT JOIN DOCUMENTO d_cuest ON axaxd_cuest.documento_id=d_cuest.id"
				+ " LEFT JOIN DOCUMENTO_TIPO dt_cuest ON (d_cuest.documento_tipo_id=dt_cuest.id AND dt_cuest.nombre='"
				+ SecurityFilter.escapeString(DocumentoTipo.CUESTIONARIO_PADRE)
				+ "')"
				+ " JOIN CURSO c ON a.curso_id=c.id"
				+ " JOIN ESTABLECIMIENTO e ON c.establecimiento_id=e.id"
				+ " LEFT JOIN APLICACION_x_ESTABLECIMIENTO axe ON (e.id=axe.establecimiento_id AND axe.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ ")"
				+ " LEFT JOIN ESTABLECIMIENTO_TIPO et ON axe.establecimiento_tipo_id=et.id"
				+ " JOIN COMUNA ON e.comuna_id=COMUNA.id"
				+ " JOIN PROVINCIA p ON COMUNA.provincia_id=p.id"
				+ " JOIN REGION r ON p.region_id=r.id";
		;
		if (usuarioTipo.equals(UsuarioTipo.JEFE_REGIONAL)
				|| usuarioTipo.equals(UsuarioTipo.JEFE_ZONAL)
				|| usuarioTipo.equals(UsuarioTipo.JEFE_CENTRO_OPERACIONES) || usuarioTipo.equals(UsuarioTipo.LOGISTICA_Y_SOPORTE)) {
			query += " JOIN CO_x_ESTABLECIMIENTO coxe ON (e.id=coxe.establecimiento_id  AND axn.id=coxe.aplicacion_x_nivel_id)";
			if (usuarioTipo.equals(UsuarioTipo.JEFE_CENTRO_OPERACIONES) || usuarioTipo.equals(UsuarioTipo.LOGISTICA_Y_SOPORTE)) {
				query += " JOIN JO_x_CO joxco ON (coxe.co_id=joxco.co_id AND joxco.jo_id="
						+ SecurityFilter.escapeString(idUsuario)
						+ ") AND joxco.activo=TRUE";
			} else {
				query += " JOIN CO co ON coxe.co_id=co.id";
				if (usuarioTipo.equals(UsuarioTipo.JEFE_ZONAL)) {
					query += " JOIN JZ_x_ZONA jzxz ON (co.zona_id=jzxz.zona_id AND jzxz.jz_id="
							+ SecurityFilter.escapeString(idUsuario)
							+ ") AND jzxz.activo=TRUE";
				} else {
					query += " JOIN ZONA z ON co.zona_id=z.id"
							+ " JOIN JR_x_CENTRO_REGIONAL jrxcr ON (z.centro_regional_id=jrxcr.centro_regional_id AND jrxcr.jr_id="
							+ SecurityFilter.escapeString(idUsuario)
							+ ") AND jrxcr.activo=TRUE";
				}
			}
		} else if (usuarioTipo.equals(UsuarioTipo.SUPERVISOR)
				|| usuarioTipo.equals(UsuarioTipo.SUPERVISOR_CON_AUTO)
				|| usuarioTipo.equals(UsuarioTipo.EXAMINADOR)
				|| usuarioTipo.equals(UsuarioTipo.EXAMINADOR_NEE)
				|| usuarioTipo.equals(UsuarioTipo.COORDINADOR_COMPUTACION)) {
			query += " JOIN USUARIO_x_ACTIVIDAD uxa ON a.id=uxa.actividad_id"
					+ " JOIN USUARIO_SELECCION us ON uxa.usuario_seleccion_id=us.id"
					+ " JOIN USUARIO_x_APLICACION_x_NIVEL uxaxn ON (us.usuario_x_aplicacion_x_nivel_id=uxaxn.id AND uxaxn.usuario_id="
					+ SecurityFilter.escapeString(idUsuario) + ")";
		}
		if (filtros != null && !filtros.isEmpty()) {
			String where = "";
			String opciones = "";
			for (String key : filtros.keySet()) {
				if (!filtros.get(key).equals("")) {
					if (key.equals(ActividadService.FKEY_COMUNA)) {
						if (!where.equals("")) {
							where += " AND ";
						}
						where += " e.comuna_id ="
								+ SecurityFilter.escapeString(filtros.get(key));
					} else if (key.equals(ActividadService.FKEY_REGION)) {
						if (!where.equals("")) {
							where += " AND ";
						}
						where += " p.region_id ="
								+ SecurityFilter.escapeString(filtros.get(key));
					} else if (key.equals(ActividadService.FKEY_ESTADOS)) {
						if (!where.equals("")) {
							where += " AND ";
						}
						String[] estados = filtros.get(key).split(
								ActividadService.SEPARATOR);
						if (estados.length != 0) {
							where += " (";
							for (int i = 0; i < estados.length; i++) {
								where += "a.actividad_estado_id="
										+ SecurityFilter
												.escapeString(estados[i]);
								if (i < estados.length - 1) {
									where += " OR ";
								}
							}
							where += ") ";
						}
					} else if (key
							.equals(ActividadService.FKEY_ACTIVIDADES_CONTINGENCIA)) {
						if (!opciones.equals("")) {
							opciones += " OR ";
						}
						// opciones += " contingencia=true";
						opciones += " false";
					} else if (key
							.equals(ActividadService.FKEY_ACTIVIDADES_CONTINGENCIA_INHABILITANTE)) {
						if (!opciones.equals("")) {
							opciones += " OR ";
						}
						// opciones += " limitante=true";
						opciones += " false";
					} else if (key
							.equals(ActividadService.FKEY_ACTIVIDADES_MATERIAL_CONTINGENCIA)) {
						if (!opciones.equals("")) {
							opciones += " OR ";
						}
						opciones += " a.material_contingencia=true";
					} else if (key
							.equals(ActividadService.FKEY_ACTIVIDADES_NO_SINCRONIZADAS)) {
						if (!opciones.equals("")) {
							opciones += " OR ";
						}
						opciones += " (a.total_alumnos_presentes IS NULL OR a.total_alumnos_presentes=0)";
					} else if (key
							.equals(ActividadService.FKEY_ACTIVIDADES_PARCIALMENTE_SINCRONIZADAS)) {
						if (!opciones.equals("")) {
							opciones += " OR ";
						}
						opciones += " (a.total_alumnos_presentes IS NOT NULL AND a.total_alumnos_presentes!=0  AND a.total_alumnos_presentes!=a.total_alumnos)";
					} else if (key
							.equals(ActividadService.FKEY_ACTIVIDADES_SINCRONIZADAS)) {
						if (!opciones.equals("")) {
							opciones += " OR ";
						}
						opciones += " (a.total_alumnos_presentes IS NOT NULL AND a.total_alumnos_presentes=a.total_alumnos)";
					}
				}
			}
			if (!where.equals("") || !opciones.equals("")) {
				query += " WHERE ";
			}
			query += where;
			if (!opciones.equals("")) {
				if (!where.equals("")) {
					query += " AND ";
				}
				query += "(" + opciones + ")";
			}

		}

		Query q = s.createSQLQuery(query);
		result = ((BigInteger) q.uniqueResult()).intValue();
		return result;
	}
}
