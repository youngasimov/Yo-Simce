/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.client.actividad.ActividadService;
import com.dreamer8.yosimce.client.planificacion.PlanificacionService;
import com.dreamer8.yosimce.server.hibernate.pojo.Actividad;
import com.dreamer8.yosimce.server.hibernate.pojo.ActividadTipo;
import com.dreamer8.yosimce.server.hibernate.pojo.Archivo;
import com.dreamer8.yosimce.server.hibernate.pojo.DocumentoEstado;
import com.dreamer8.yosimce.server.hibernate.pojo.DocumentoTipo;
import com.dreamer8.yosimce.server.hibernate.pojo.UsuarioTipo;
import com.dreamer8.yosimce.server.utils.SecurityFilter;
import com.dreamer8.yosimce.server.utils.StringUtils;
import com.dreamer8.yosimce.shared.dto.ActividadPreviewDTO;
import com.dreamer8.yosimce.shared.dto.AgendaItemDTO;
import com.dreamer8.yosimce.shared.dto.AgendaPreviewDTO;
import com.dreamer8.yosimce.shared.dto.DocumentoDTO;
import com.dreamer8.yosimce.shared.dto.EstadoAgendaDTO;
import com.dreamer8.yosimce.shared.dto.TipoEstablecimientoDTO;
import com.dreamer8.yosimce.shared.dto.UserDTO;

/**
 * @author jorge
 * 
 */
public class ActividadDAO extends AbstractHibernateDAO<Actividad, Integer> {
	public List<Actividad> findByIdAplicacionANDIdNivelANDIdActividadTipo(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo) {

		List<Actividad> as = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT a.* FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id AND axnxat.actividad_tipo_id="
				+ SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id"
				+ " WHERE a.actividad_estado_id!=7";
		Query q = s.createSQLQuery(query).addEntity(Actividad.class);
		as = q.list();
		return as;
	}

	/**
	 * @param idAplicacion
	 * @param idNivel
	 * @param idActividadTipo
	 * @param idEstablecimiento
	 * @return
	 */
	public Actividad findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdEstablecimiento(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			Integer idEstablecimiento) {

		Actividad a = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT DISTINCT a.* FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id AND axnxat.actividad_tipo_id="
				+ SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id"
				+ " JOIN CURSO c ON a.curso_id=c.id"
				+ " WHERE c.establecimiento_id="
				+ SecurityFilter.escapeString(idEstablecimiento)
				+ " AND a.actividad_estado_id!=7";

		Query q = s.createSQLQuery(query).addEntity(Actividad.class);
		a = ((Actividad) q.uniqueResult());
		return a;
	}

	public Actividad findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdEstablecimientoANDNombreCursoANDDia(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			Integer idEstablecimiento, String nombreCurso, Integer dia) {

		Actividad a = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT DISTINCT a.* FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id AND axnxat.actividad_tipo_id="
				+ SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id"
				+ " JOIN CURSO c ON a.curso_id=c.id"
				+ " WHERE c.establecimiento_id="
				+ SecurityFilter.escapeString(idEstablecimiento)
				+ " AND c.nombre='" + SecurityFilter.escapeString(nombreCurso)
				+ "'" + " AND a.actividad_estado_id!=7";
		if (dia != null) {
			query += " AND a.dia=" + SecurityFilter.escapeString(dia);
		}

		Query q = s.createSQLQuery(query).addEntity(Actividad.class);
		a = ((Actividad) q.uniqueResult());
		return a;
	}

	public Actividad findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdEstablecimientoANDCodigoCursoANDDia(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			Integer idEstablecimiento, String nombreCurso, Integer dia) {

		Actividad a = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT DISTINCT a.* FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id AND axnxat.actividad_tipo_id="
				+ SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id"
				+ " JOIN CURSO c ON a.curso_id=c.id"
				+ " WHERE c.establecimiento_id="
				+ SecurityFilter.escapeString(idEstablecimiento)
				+ " AND c.codigo='" + SecurityFilter.escapeString(nombreCurso)
				+ "'" + " AND a.actividad_estado_id!=7";
		if (dia != null) {
			query += " AND a.dia=" + SecurityFilter.escapeString(dia);
		}

		Query q = s.createSQLQuery(query).addEntity(Actividad.class);
		a = ((Actividad) q.uniqueResult());
		return a;
	}

	public Actividad findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCurso(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			Integer idCurso) {

		Actividad a = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT a.* FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id AND axnxat.actividad_tipo_id="
				+ SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN ACTIVIDAD a ON (axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id AND a.curso_id="
				+ SecurityFilter.escapeString(idCurso) + ")"
				+ " WHERE a.actividad_estado_id!=7";

		Query q = s.createSQLQuery(query).addEntity(Actividad.class);
		a = ((Actividad) q.uniqueResult());
		return a;
	}

	public List<AgendaPreviewDTO> findAgendasByIdAplicacionANDIdNivelANDIdActividadTipoANDFiltros(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			Integer idUsuario, String usuarioTipo, Integer offset,
			Integer lenght, Map<String, String> filtros) {

		List<AgendaPreviewDTO> apdtos = new ArrayList<AgendaPreviewDTO>();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT DISTINCT c.id as curso_id,c.nombre as nombre_curso,e.id as establecimiento_id,e.nombre as establecimiento_nombre,"
				+ "et.id as est_tipo_id,et.nombre as est_tipo_nombre,"
				+ "r.nombre as region_nombre,COMUNA.id as comuna_id,COMUNA.nombre as comuna_nombre,a.fecha_inicio,a.comentario,u.id as usuario_id,u.username,"
				+ "u.email,u.nombres,u.apellido_paterno,u.apellido_materno,ae.id as act_est_id,ae.nombre as act_est_nombre,"
				+ "u_ex.id as ex_id,u_ex.username as user_ex,u_ex.email as email_ex,u_ex.nombres as nom_ex,u_ex.apellido_paterno as ap_pat_ex,u_ex.apellido_materno as ap_mat_ex,"
				+ "u_sup.id as sup_id,u_sup.username as user_sup,u_sup.email as email_sup,u_sup.nombres as nom_sup,u_sup.apellido_paterno as ap_pat_sup,u_sup.apellido_materno as ap_mat_sup,"
				+ "a.total_alumnos,a.contacto_nombre, a.contacto_telefono, a.contacto_email"
				+ " FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id AND axnxat.actividad_tipo_id="
				+ SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id AND a.actividad_estado_id!=7"
				+ " LEFT JOIN ACTIVIDAD_ESTADO ae ON a.actividad_estado_id=ae.id"
				+ " JOIN CURSO c ON a.curso_id=c.id"
				+ " JOIN ESTABLECIMIENTO e ON c.establecimiento_id=e.id"
				+ " LEFT JOIN APLICACION_x_ESTABLECIMIENTO axe ON (e.id=axe.establecimiento_id AND axe.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ ")"
				+ " LEFT JOIN ESTABLECIMIENTO_TIPO et ON axe.establecimiento_tipo_id=et.id"
				+ " JOIN COMUNA ON e.comuna_id=COMUNA.id"
				+ " JOIN PROVINCIA p ON COMUNA.provincia_id=p.id"
				+ " JOIN REGION r ON p.region_id=r.id"
				+ " LEFT JOIN USUARIO u ON a.modificador_id=u.id"

				+ " LEFT JOIN USUARIO_x_ACTIVIDAD uxa_ex ON (a.id=uxa_ex.actividad_id AND (uxa_ex.asistencia != false OR uxa_ex.asistencia IS NULL) AND uxa_ex.usuario_seleccion_id IS NOT NULL AND (uxa_ex.usuario_tipo_id=11 OR uxa_ex.usuario_tipo_id=12 OR uxa_ex.usuario_tipo_id=13))"
				+ " LEFT JOIN USUARIO_SELECCION us_ex ON (uxa_ex.usuario_seleccion_id=us_ex.id AND (us_ex.usuario_tipo_id=11 OR us_ex.usuario_tipo_id=12 OR us_ex.usuario_tipo_id=13))"
				+ " LEFT JOIN USUARIO_x_APLICACION_x_NIVEL uxaxn_ex ON us_ex.usuario_x_aplicacion_x_nivel_id=uxaxn_ex.id"
				+ " LEFT JOIN USUARIO u_ex ON uxaxn_ex.usuario_id=u_ex.id"

				+ " LEFT JOIN USUARIO_x_ACTIVIDAD uxa_sup ON (a.id=uxa_sup.actividad_id AND (uxa_sup.asistencia != false OR uxa_sup.asistencia IS NULL) AND uxa_sup.usuario_seleccion_id IS NOT NULL AND (uxa_sup.usuario_tipo_id=9 OR uxa_sup.usuario_tipo_id=10))"
				+ " LEFT JOIN USUARIO_SELECCION us_sup ON (uxa_sup.usuario_seleccion_id=us_sup.id AND (us_sup.usuario_tipo_id=9 OR us_sup.usuario_tipo_id=10))"
				+ " LEFT JOIN USUARIO_x_APLICACION_x_NIVEL uxaxn_sup ON us_sup.usuario_x_aplicacion_x_nivel_id=uxaxn_sup.id"
				+ " LEFT JOIN USUARIO u_sup ON uxaxn_sup.usuario_id=u_sup.id";
		;
		if (usuarioTipo.equals(UsuarioTipo.JEFE_REGIONAL)
				|| usuarioTipo.equals(UsuarioTipo.JEFE_ZONAL)
				|| usuarioTipo.equals(UsuarioTipo.JEFE_CENTRO_OPERACIONES)
				|| usuarioTipo.equals(UsuarioTipo.LOGISTICA_Y_SOPORTE)) {
			query += " JOIN CO_x_ESTABLECIMIENTO coxe ON (e.id=coxe.establecimiento_id  AND axn.id=coxe.aplicacion_x_nivel_id)";
			if (usuarioTipo.equals(UsuarioTipo.JEFE_CENTRO_OPERACIONES)
					|| usuarioTipo.equals(UsuarioTipo.LOGISTICA_Y_SOPORTE)) {
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
			for (String key : filtros.keySet()) {
				if (!filtros.get(key).equals("")) {
					if (!where.equals("")) {
						where += " AND ";
					}
					if (key.equals(PlanificacionService.FKEY_DESDE)) {
						where += " a.fecha_inicio >='"
								+ SecurityFilter.escapeString(filtros.get(key))
								+ " 00:00:00'";
					} else if (key.equals(PlanificacionService.FKEY_HASTA)) {
						where += " a.fecha_inicio <='"
								+ SecurityFilter.escapeString(filtros.get(key))
								+ " 23:59:59'";
					} else if (key.equals(PlanificacionService.FKEY_COMUNA)) {
						where += " e.comuna_id ="
								+ SecurityFilter.escapeString(filtros.get(key));
					} else if (key.equals(PlanificacionService.FKEY_REGION)) {
						where += " p.region_id ="
								+ SecurityFilter.escapeString(filtros.get(key));
					} else if (key.equals(PlanificacionService.FKEY_ESTADOS)) {
						String[] estados = filtros.get(key).split(
								PlanificacionService.SEPARATOR);
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
					}
				}
			}
			if (!where.equals("")) {
				query += " WHERE " + where;
			}
		}
		query += " ORDER BY COMUNA.id,e.id,c.id";
		Query q = s.createSQLQuery(query);
		if (offset != null) {
			q.setFirstResult(offset);
		}
		if (lenght != null) {
			q.setMaxResults(lenght);
		}
		List<Object[]> os = q.list();
		AgendaPreviewDTO apdto = null;
		TipoEstablecimientoDTO tedto = null;
		AgendaItemDTO aidto = null;
		UserDTO udto = null;
		EstadoAgendaDTO eadto = null;
		for (Object[] o : os) {
			apdto = new AgendaPreviewDTO();
			apdto.setCursoId((Integer) o[0]);
			apdto.setCurso((String) o[1]);
			apdto.setRbd(Integer.toString((Integer) o[2]));
			apdto.setEstablecimientoName((String) o[3]);
			tedto = new TipoEstablecimientoDTO();
			tedto.setId((Integer) o[4]);
			tedto.setTipo((String) o[5]);
			apdto.setTipoEstablecimiento(tedto);
			apdto.setRegionName((String) o[6]);
			apdto.setComunaName((String) o[8]);
			aidto = new AgendaItemDTO();
			aidto.setFecha(StringUtils.getDateString((Date) o[9]));
			aidto.setComentario((String) o[10]);
			udto = new UserDTO();
			udto.setId((Integer) o[11]);
			udto.setUsername((String) o[12]);
			udto.setEmail((String) o[13]);
			udto.setNombres((String) o[14]);
			udto.setApellidoPaterno((String) o[15]);
			udto.setApellidoMaterno((String) o[16]);
			aidto.setCreador(udto);
			eadto = new EstadoAgendaDTO();
			eadto.setId((Integer) o[17]);
			eadto.setEstado((String) o[18]);
			aidto.setEstado(eadto);
			apdto.setAgendaItemActual(aidto);
			if (o[19] != null) {
				udto = new UserDTO();
				udto.setId((Integer) o[19]);
				udto.setUsername((String) o[20]);
				udto.setEmail((String) o[21]);
				udto.setNombres((String) o[22]);
				udto.setApellidoPaterno((String) o[23]);
				udto.setApellidoMaterno((String) o[24]);
				apdto.setExaminador(udto);
			}
			if (o[25] != null) {
				udto = new UserDTO();
				udto.setId((Integer) o[25]);
				udto.setUsername((String) o[26]);
				udto.setEmail((String) o[27]);
				udto.setNombres((String) o[28]);
				udto.setApellidoPaterno((String) o[29]);
				udto.setApellidoMaterno((String) o[30]);
				apdto.setSupervisor(udto);
			}
			apdto.setTotalAlumnos((Integer) o[31]);
			apdto.setNombreContacto((String) o[32]);
			apdto.setTelefonoContacto((String) o[33]);
			apdto.setMailContacto((String) o[34]);
			apdtos.add(apdto);
		}

		return apdtos;
	}

	public Integer countAgendasByIdAplicacionANDIdNivelANDIdActividadTipoANDFiltros(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			Integer idUsuario, String usuarioTipo, Map<String, String> filtros) {

		Integer result = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT COUNT(a.id) FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id AND axnxat.actividad_tipo_id="
				+ SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id AND a.actividad_estado_id!=7"
				+ " JOIN CURSO c ON a.curso_id=c.id"
				+ " JOIN ESTABLECIMIENTO e ON c.establecimiento_id=e.id"
				+ " JOIN COMUNA ON e.comuna_id=COMUNA.id"
				+ " JOIN PROVINCIA p ON COMUNA.provincia_id=p.id"
				+ " JOIN REGION r ON p.region_id=r.id";
		if (usuarioTipo.equals(UsuarioTipo.JEFE_REGIONAL)
				|| usuarioTipo.equals(UsuarioTipo.JEFE_ZONAL)
				|| usuarioTipo.equals(UsuarioTipo.JEFE_CENTRO_OPERACIONES)
				|| usuarioTipo.equals(UsuarioTipo.LOGISTICA_Y_SOPORTE)) {
			query += " JOIN CO_x_ESTABLECIMIENTO coxe ON (e.id=coxe.establecimiento_id  AND axn.id=coxe.aplicacion_x_nivel_id)";
			if (usuarioTipo.equals(UsuarioTipo.JEFE_CENTRO_OPERACIONES)
					|| usuarioTipo.equals(UsuarioTipo.LOGISTICA_Y_SOPORTE)) {
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
			Date fecha = null;
			for (String key : filtros.keySet()) {
				if (!filtros.get(key).equals("")) {
					if (!where.equals("")) {
						where += " AND ";
					}
					if (key.equals(PlanificacionService.FKEY_DESDE)) {
						where += " a.fecha_inicio >='"
								+ SecurityFilter.escapeString(filtros.get(key))
								+ " 00:00:00'";
					} else if (key.equals(PlanificacionService.FKEY_HASTA)) {
						where += " a.fecha_inicio <='"
								+ SecurityFilter.escapeString(filtros.get(key))
								+ " 23:59:59'";
					} else if (key.equals(PlanificacionService.FKEY_COMUNA)) {
						where += " e.comuna_id ="
								+ SecurityFilter.escapeString(filtros.get(key));
					} else if (key.equals(PlanificacionService.FKEY_REGION)) {
						where += " p.region_id ="
								+ SecurityFilter.escapeString(filtros.get(key));
					} else if (key.equals(PlanificacionService.FKEY_ESTADOS)) {
						String[] estados = filtros.get(key).split(
								PlanificacionService.SEPARATOR);
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
					}
				}
			}
			if (!where.equals("")) {
				query += " WHERE " + where;
			}
		}
		Query q = s.createSQLQuery(query);
		result = ((BigInteger) q.uniqueResult()).intValue();
		return result;
	}

	public List<String> findAgendasCsvByIdAplicacionANDIdNivelANDFiltros(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			Integer idUsuario, String usuarioTipo, Integer offset,
			Integer lenght, Map<String, String> filtros) {

		List<String> csv = new ArrayList<String>();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT DISTINCT c.id as curso_id,e.id as establecimiento_id,e.nombre as establecimiento_nombre,"
				+ "et.nombre as est_tipo_nombre,"
				+ "r.nombre as region_nombre,COMUNA.id as comuna_id,COMUNA.nombre as comuna_nombre,"
				+ "at.nombre as act_tipo_nombre,a.fecha_inicio,a.comentario,ae.nombre as act_est_nombre, "
				+ "a.contacto_nombre,a.contacto_telefono,a.contacto_email,cc.nombre as contacto_cargo,"
				+ "a.aplicacion_x_nivel_x_actividad_tipo_id,"
				+ "u_ex.nombres as nom_ex,u_ex.apellido_paterno as ap_pat_ex,u_ex.apellido_materno as ap_mat_ex,"
				+ "u_sup.nombres as nom_sup,u_sup.apellido_paterno as ap_pat_sup,u_sup.apellido_materno as ap_mat_sup,"
				+ "a.total_alumnos,apd1.fecha_inicio as fecha_aplicacion,apd1_est.nombre as estado_agendamiento_aplicacion, apd1.comentario as comentario_aplicacion"
				+ " FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.id=axnxat.aplicacion_x_nivel_id"
				// + " AND axn.aplicacion_id="
				// + SecurityFilter.escapeString(idAplicacion)
				// + " AND axn.nivel_id="
				// + SecurityFilter.escapeString(idNivel)
				+ " AND axnxat.actividad_tipo_id=1"
				+ ")"

				+ " JOIN ACTIVIDAD_TIPO at ON axnxat.actividad_tipo_id=at.id"
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id AND a.actividad_estado_id!=7"
				+ " LEFT JOIN CONTACTO_CARGO cc ON a.contacto_cargo_id=cc.id"
				+ " LEFT JOIN ACTIVIDAD_ESTADO ae ON a.actividad_estado_id=ae.id"
				+ " JOIN CURSO c ON a.curso_id=c.id"
				+ " JOIN ESTABLECIMIENTO e ON c.establecimiento_id=e.id"
				+ " LEFT JOIN APLICACION_x_ESTABLECIMIENTO axe ON (e.id=axe.establecimiento_id AND axe.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ ")"
				+ " LEFT JOIN ESTABLECIMIENTO_TIPO et ON axe.establecimiento_tipo_id=et.id"
				+ " JOIN COMUNA ON e.comuna_id=COMUNA.id"
				+ " JOIN PROVINCIA p ON COMUNA.provincia_id=p.id"
				+ " JOIN REGION r ON p.region_id=r.id"
				+ " LEFT JOIN USUARIO u ON a.modificador_id=u.id"

				+ " LEFT JOIN USUARIO_x_ACTIVIDAD uxa_ex ON (a.id=uxa_ex.actividad_id AND (uxa_ex.asistencia != false OR uxa_ex.asistencia IS NULL) AND uxa_ex.usuario_seleccion_id IS NOT NULL AND (uxa_ex.usuario_tipo_id=11 OR uxa_ex.usuario_tipo_id=12 OR uxa_ex.usuario_tipo_id=13))"
				+ " LEFT JOIN USUARIO_SELECCION us_ex ON (uxa_ex.usuario_seleccion_id=us_ex.id AND (us_ex.usuario_tipo_id=11 OR us_ex.usuario_tipo_id=12 OR us_ex.usuario_tipo_id=13))"
				+ " LEFT JOIN USUARIO_x_APLICACION_x_NIVEL uxaxn_ex ON us_ex.usuario_x_aplicacion_x_nivel_id=uxaxn_ex.id"
				+ " LEFT JOIN USUARIO u_ex ON uxaxn_ex.usuario_id=u_ex.id"

				+ " LEFT JOIN USUARIO_x_ACTIVIDAD uxa_sup ON (a.id=uxa_sup.actividad_id AND (uxa_sup.asistencia != false OR uxa_sup.asistencia IS NULL) AND uxa_sup.usuario_seleccion_id IS NOT NULL AND (uxa_sup.usuario_tipo_id=9 OR uxa_sup.usuario_tipo_id=10))"
				+ " LEFT JOIN USUARIO_SELECCION us_sup ON (uxa_sup.usuario_seleccion_id=us_sup.id AND (us_sup.usuario_tipo_id=9 OR us_sup.usuario_tipo_id=10))"
				+ " LEFT JOIN USUARIO_x_APLICACION_x_NIVEL uxaxn_sup ON us_sup.usuario_x_aplicacion_x_nivel_id=uxaxn_sup.id"
				+ " LEFT JOIN USUARIO u_sup ON uxaxn_sup.usuario_id=u_sup.id"

				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO apd1_axnxat ON (axn.id=apd1_axnxat.aplicacion_x_nivel_id"
				// + " AND axn.aplicacion_id="
				// + SecurityFilter.escapeString(idAplicacion)
				// + " AND axn.nivel_id="
				// + SecurityFilter.escapeString(idNivel)
				+ " AND apd1_axnxat.actividad_tipo_id=2"
				+ ")"
				+ " JOIN ACTIVIDAD apd1 ON apd1_axnxat.id=apd1.aplicacion_x_nivel_x_actividad_tipo_id AND a.curso_id=apd1.curso_id"
				+ " JOIN ACTIVIDAD_ESTADO apd1_est ON apd1.actividad_estado_id=apd1_est.id";

		if (usuarioTipo.equals(UsuarioTipo.JEFE_REGIONAL)
				|| usuarioTipo.equals(UsuarioTipo.JEFE_ZONAL)
				|| usuarioTipo.equals(UsuarioTipo.JEFE_CENTRO_OPERACIONES)
				|| usuarioTipo.equals(UsuarioTipo.LOGISTICA_Y_SOPORTE)) {
			query += " JOIN CO_x_ESTABLECIMIENTO coxe ON (e.id=coxe.establecimiento_id  AND axn.id=coxe.aplicacion_x_nivel_id)";
			if (usuarioTipo.equals(UsuarioTipo.JEFE_CENTRO_OPERACIONES)
					|| usuarioTipo.equals(UsuarioTipo.LOGISTICA_Y_SOPORTE)) {
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

		String where = " axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id=" + SecurityFilter.escapeString(idNivel);

		if (filtros != null && !filtros.isEmpty()) {

			for (String key : filtros.keySet()) {
				if (!filtros.get(key).equals("")) {
					if (!where.equals("")) {
						where += " AND ";
					}
					if (key.equals(PlanificacionService.FKEY_DESDE)) {
						if (idActividadTipo == 1) {
							where += " a.fecha_inicio >='";
						} else {
							where += " apd1.fecha_inicio >='";
						}
						where += SecurityFilter.escapeString(filtros.get(key))
								+ " 00:00:00'";
					} else if (key.equals(PlanificacionService.FKEY_HASTA)) {
						if (idActividadTipo == 1) {
							where += " a.fecha_inicio <='";
						} else {
							where += " apd1.fecha_inicio <='";
						}
						where += SecurityFilter.escapeString(filtros.get(key))
								+ " 23:59:59'";
					} else if (key.equals(PlanificacionService.FKEY_COMUNA)) {
						where += " e.comuna_id ="
								+ SecurityFilter.escapeString(filtros.get(key));
					} else if (key.equals(PlanificacionService.FKEY_REGION)) {
						where += " p.region_id ="
								+ SecurityFilter.escapeString(filtros.get(key));
					} else if (key.equals(PlanificacionService.FKEY_ESTADOS)) {
						String[] estados = filtros.get(key).split(
								PlanificacionService.SEPARATOR);
						if (estados.length != 0) {
							where += " (";
							for (int i = 0; i < estados.length; i++) {
								if (idActividadTipo == 1) {
									where += "a.actividad_estado_id=";
								} else {
									where += "apd1.actividad_estado_id=";
								}
								where += SecurityFilter
										.escapeString(estados[i]);
								if (i < estados.length - 1) {
									where += " OR ";
								}
							}
							where += ") ";
						}
					}
				}
			}
		}

		if (!where.equals("")) {
			query += " WHERE " + where;
		}
		query += " ORDER BY COMUNA.id,e.id,c.id,a.aplicacion_x_nivel_x_actividad_tipo_id";
		Query q = s.createSQLQuery(query);
		if (offset != null) {
			q.setFirstResult(offset);
		}
		if (lenght != null) {
			q.setMaxResults(lenght);
		}
		List<Object[]> os = q.list();

		String linea = null;
		String visitaPrevia = null;
		String aplic = null;
		String contacto = null;
		String supervisor = null;
		String examinador = null;
		if (os != null && !os.isEmpty()) {
			csv.add("rbd;establecimiento_nombre;establecimiento_tipo;región;comuna;total_alumnos;"
					+ "fecha_visita_previa;estado_agendamiento_visita_previa;comentario_visita_previa;"
					+ "fecha_aplicación;estado_agendamiento_aplicación;comentario_aplicación;"
					+ "nombre_contacto;cargo_contacto;teléfono_contacto;email_cotacto;examinador;supervisor");
			for (Object[] o : os) {
				linea = Integer.toString((Integer) o[1]) + ";"
						+ ((String) o[2]) + ";" + ((String) o[3]) + ";"
						+ ((String) o[4]) + ";" + ((String) o[6]) + ";"
						+ (Integer) o[22];
				contacto = ";" + ((String) o[11]) + ";" + ((String) o[14])
						+ ";" + ((String) o[12]) + ";" + ((String) o[13]);

				examinador = ";" + ((String) o[16]) + " " + ((String) o[17])
						+ " " + ((String) o[18]);

				supervisor = ";" + ((String) o[19]) + " " + ((String) o[20])
						+ " " + ((String) o[21]);

				visitaPrevia = ";" + ((Date) o[8]) + ";" + ((String) o[10])
						+ ";" + ((String) o[9]);

				aplic = ";" + ((Date) o[23]) + ";" + ((String) o[24]) + ";"
						+ ((String) o[25]);

				linea += visitaPrevia + aplic + contacto + examinador
						+ supervisor;
				csv.add(linea);
			}
		}
		return csv;
	}

	public Integer countAgendasCsvByIdAplicacionANDIdNivelANDFiltros(
			Integer idAplicacion, Integer idNivel, Integer idUsuario,
			String usuarioTipo, Map<String, String> filtros) {

		Integer result = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT COUNT(a.id) FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id"
				+ ")"
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id AND a.actividad_estado_id!=7"
				+ " JOIN CURSO c ON a.curso_id=c.id"
				+ " JOIN ESTABLECIMIENTO e ON c.establecimiento_id=e.id"
				+ " JOIN COMUNA ON e.comuna_id=COMUNA.id"
				+ " JOIN PROVINCIA p ON COMUNA.provincia_id=p.id"
				+ " JOIN REGION r ON p.region_id=r.id";
		if (usuarioTipo.equals(UsuarioTipo.JEFE_REGIONAL)
				|| usuarioTipo.equals(UsuarioTipo.JEFE_ZONAL)
				|| usuarioTipo.equals(UsuarioTipo.JEFE_CENTRO_OPERACIONES)
				|| usuarioTipo.equals(UsuarioTipo.LOGISTICA_Y_SOPORTE)) {
			query += " JOIN CO_x_ESTABLECIMIENTO coxe ON (e.id=coxe.establecimiento_id  AND axn.id=coxe.aplicacion_x_nivel_id)";
			if (usuarioTipo.equals(UsuarioTipo.JEFE_CENTRO_OPERACIONES)
					|| usuarioTipo.equals(UsuarioTipo.LOGISTICA_Y_SOPORTE)) {
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
			Date fecha = null;
			for (String key : filtros.keySet()) {
				if (!filtros.get(key).equals("")) {
					if (!where.equals("")) {
						where += " AND ";
					}
					if (key.equals(PlanificacionService.FKEY_DESDE)) {
						where += " a.fecha_inicio >='"
								+ SecurityFilter.escapeString(filtros.get(key))
								+ " 00:00:00'";
					} else if (key.equals(PlanificacionService.FKEY_HASTA)) {
						where += " a.fecha_inicio <='"
								+ SecurityFilter.escapeString(filtros.get(key))
								+ " 23:59:59'";
					} else if (key.equals(PlanificacionService.FKEY_COMUNA)) {
						where += " e.comuna_id ="
								+ SecurityFilter.escapeString(filtros.get(key));
					} else if (key.equals(PlanificacionService.FKEY_REGION)) {
						where += " p.region_id ="
								+ SecurityFilter.escapeString(filtros.get(key));
					} else if (key.equals(PlanificacionService.FKEY_ESTADOS)) {
						String[] estados = filtros.get(key).split(
								PlanificacionService.SEPARATOR);
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
					}
				}
			}
			if (!where.equals("")) {
				query += " WHERE " + where;
			}
		}
		Query q = s.createSQLQuery(query);
		result = ((BigInteger) q.uniqueResult()).intValue();
		return result;
	}

	public List<String> findAgendasCsvSimceNormalByIdAplicacionANDIdNivelANDFiltros(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			Integer idUsuario, String usuarioTipo, Integer offset,
			Integer lenght, Map<String, String> filtros) {

		List<String> csv = new ArrayList<String>();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT DISTINCT c.id as curso_id,e.id as establecimiento_id,e.nombre as establecimiento_nombre,"
				+ "et.nombre as est_tipo_nombre,"
				+ "r.nombre as region_nombre,COMUNA.id as comuna_id,COMUNA.nombre as comuna_nombre,"
				+ "at.nombre as act_tipo_nombre,a.fecha_inicio,a.comentario,ae.nombre as act_est_nombre, "
				+ "a.contacto_nombre,a.contacto_telefono,a.contacto_email,cc.nombre as contacto_cargo,"
				+ "a.aplicacion_x_nivel_x_actividad_tipo_id,"
				+ "u_ex.nombres as nom_ex,u_ex.apellido_paterno as ap_pat_ex,u_ex.apellido_materno as ap_mat_ex,"
				+ "u_sup.nombres as nom_sup,u_sup.apellido_paterno as ap_pat_sup,u_sup.apellido_materno as ap_mat_sup,"
				+ "a.total_alumnos, c.nombre"
				+ " FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id AND axnxat.actividad_tipo_id="
				+ SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN ACTIVIDAD_TIPO at ON axnxat.actividad_tipo_id=at.id"
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id AND a.actividad_estado_id!=7"
				+ " LEFT JOIN CONTACTO_CARGO cc ON a.contacto_cargo_id=cc.id"
				+ " LEFT JOIN ACTIVIDAD_ESTADO ae ON a.actividad_estado_id=ae.id"
				+ " JOIN CURSO c ON a.curso_id=c.id"
				+ " JOIN ESTABLECIMIENTO e ON c.establecimiento_id=e.id"
				+ " LEFT JOIN APLICACION_x_ESTABLECIMIENTO axe ON (e.id=axe.establecimiento_id AND axe.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ ")"
				+ " LEFT JOIN ESTABLECIMIENTO_TIPO et ON axe.establecimiento_tipo_id=et.id"
				+ " JOIN COMUNA ON e.comuna_id=COMUNA.id"
				+ " JOIN PROVINCIA p ON COMUNA.provincia_id=p.id"
				+ " JOIN REGION r ON p.region_id=r.id"
				+ " LEFT JOIN USUARIO u ON a.modificador_id=u.id"

				+ " LEFT JOIN USUARIO_x_ACTIVIDAD uxa_ex ON (a.id=uxa_ex.actividad_id AND (uxa_ex.asistencia != false OR uxa_ex.asistencia IS NULL) AND uxa_ex.usuario_seleccion_id IS NOT NULL AND (uxa_ex.usuario_tipo_id=11 OR uxa_ex.usuario_tipo_id=12 OR uxa_ex.usuario_tipo_id=13))"
				+ " LEFT JOIN USUARIO_SELECCION us_ex ON (uxa_ex.usuario_seleccion_id=us_ex.id AND (us_ex.usuario_tipo_id=11 OR us_ex.usuario_tipo_id=12 OR us_ex.usuario_tipo_id=13))"
				+ " LEFT JOIN USUARIO_x_APLICACION_x_NIVEL uxaxn_ex ON us_ex.usuario_x_aplicacion_x_nivel_id=uxaxn_ex.id"
				+ " LEFT JOIN USUARIO u_ex ON uxaxn_ex.usuario_id=u_ex.id"

				+ " LEFT JOIN USUARIO_x_ACTIVIDAD uxa_sup ON (a.id=uxa_sup.actividad_id AND (uxa_sup.asistencia != false OR uxa_sup.asistencia IS NULL) AND uxa_sup.usuario_seleccion_id IS NOT NULL AND (uxa_sup.usuario_tipo_id=9 OR uxa_sup.usuario_tipo_id=10))"
				+ " LEFT JOIN USUARIO_SELECCION us_sup ON (uxa_sup.usuario_seleccion_id=us_sup.id AND (us_sup.usuario_tipo_id=9 OR us_sup.usuario_tipo_id=10))"
				+ " LEFT JOIN USUARIO_x_APLICACION_x_NIVEL uxaxn_sup ON us_sup.usuario_x_aplicacion_x_nivel_id=uxaxn_sup.id"
				+ " LEFT JOIN USUARIO u_sup ON uxaxn_sup.usuario_id=u_sup.id";
		;
		if (usuarioTipo.equals(UsuarioTipo.JEFE_REGIONAL)
				|| usuarioTipo.equals(UsuarioTipo.JEFE_ZONAL)
				|| usuarioTipo.equals(UsuarioTipo.JEFE_CENTRO_OPERACIONES)
				|| usuarioTipo.equals(UsuarioTipo.LOGISTICA_Y_SOPORTE)) {
			query += " JOIN CO_x_ESTABLECIMIENTO coxe ON (e.id=coxe.establecimiento_id  AND axn.id=coxe.aplicacion_x_nivel_id)";
			if (usuarioTipo.equals(UsuarioTipo.JEFE_CENTRO_OPERACIONES)
					|| usuarioTipo.equals(UsuarioTipo.LOGISTICA_Y_SOPORTE)) {
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
			for (String key : filtros.keySet()) {
				if (!filtros.get(key).equals("")) {
					if (!where.equals("")) {
						where += " AND ";
					}
					if (key.equals(PlanificacionService.FKEY_DESDE)) {
						where += " a.fecha_inicio >='"
								+ SecurityFilter.escapeString(filtros.get(key))
								+ " 00:00:00'";
					} else if (key.equals(PlanificacionService.FKEY_HASTA)) {
						where += " a.fecha_inicio <='"
								+ SecurityFilter.escapeString(filtros.get(key))
								+ " 23:59:59'";
					} else if (key.equals(PlanificacionService.FKEY_COMUNA)) {
						where += " e.comuna_id ="
								+ SecurityFilter.escapeString(filtros.get(key));
					} else if (key.equals(PlanificacionService.FKEY_REGION)) {
						where += " p.region_id ="
								+ SecurityFilter.escapeString(filtros.get(key));
					} else if (key.equals(PlanificacionService.FKEY_ESTADOS)) {
						String[] estados = filtros.get(key).split(
								PlanificacionService.SEPARATOR);
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
					}
				}
			}
			if (!where.equals("")) {
				query += " WHERE " + where;
			}
		}
		query += " ORDER BY COMUNA.id,e.id,c.id,a.aplicacion_x_nivel_x_actividad_tipo_id";
		Query q = s.createSQLQuery(query);
		if (offset != null) {
			q.setFirstResult(offset);
		}
		if (lenght != null) {
			q.setMaxResults(lenght);
		}
		List<Object[]> os = q.list();
		String linea = null;
		String contacto = null;
		String supervisor = null;
		String examinador = null;
		if (os != null && !os.isEmpty()) {
			csv.add("rbd;establecimiento_nombre;establecimiento_tipo;curso;región;comuna;total_alumnos;"
					+ "tipo_actividad;fecha_actividad;estado_agendamiento;comentario_agendamiento;"
					+ "nombre_contacto;cargo_contacto;teléfono_contacto;email_cotacto;examinador;supervisor");
			for (Object[] o : os) {
				linea = Integer.toString((Integer) o[1]) + ";"
						+ ((String) o[2]) + ";" + ((String) o[3]) + ";"
						+ ((String) o[23]) + ";" + ((String) o[4]) + ";"
						+ ((String) o[6]) + ";" + (Integer) o[22];
				contacto = ";" + ((String) o[11]) + ";" + ((String) o[14])
						+ ";" + ((String) o[12]) + ";" + ((String) o[13]);
				examinador = ";" + ((String) o[16]) + " " + ((String) o[17])
						+ " " + ((String) o[18]);
				supervisor = ";" + ((String) o[19]) + " " + ((String) o[20])
						+ " " + ((String) o[21]);

				linea += ";" + ((String) o[7]) + ";" + ((Date) o[8]) + ";"
						+ ((String) o[10]) + ";" + ((String) o[9]) + contacto
						+ examinador + supervisor;
				csv.add(linea);
			}
		}
		return csv;
	}

	public Integer countAgendasCsvSimceNormalByIdAplicacionANDIdNivelANDFiltros(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			Integer idUsuario, String usuarioTipo, Map<String, String> filtros) {

		Integer result = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT COUNT(a.id) FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id AND axnxat.actividad_tipo_id="
				+ SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id AND a.actividad_estado_id!=7"
				+ " JOIN CURSO c ON a.curso_id=c.id"
				+ " JOIN ESTABLECIMIENTO e ON c.establecimiento_id=e.id"
				+ " JOIN COMUNA ON e.comuna_id=COMUNA.id"
				+ " JOIN PROVINCIA p ON COMUNA.provincia_id=p.id"
				+ " JOIN REGION r ON p.region_id=r.id";
		if (usuarioTipo.equals(UsuarioTipo.JEFE_REGIONAL)
				|| usuarioTipo.equals(UsuarioTipo.JEFE_ZONAL)
				|| usuarioTipo.equals(UsuarioTipo.JEFE_CENTRO_OPERACIONES)
				|| usuarioTipo.equals(UsuarioTipo.LOGISTICA_Y_SOPORTE)) {
			query += " JOIN CO_x_ESTABLECIMIENTO coxe ON (e.id=coxe.establecimiento_id  AND axn.id=coxe.aplicacion_x_nivel_id)";
			if (usuarioTipo.equals(UsuarioTipo.JEFE_CENTRO_OPERACIONES)
					|| usuarioTipo.equals(UsuarioTipo.LOGISTICA_Y_SOPORTE)) {
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
			Date fecha = null;
			for (String key : filtros.keySet()) {
				if (!filtros.get(key).equals("")) {
					if (!where.equals("")) {
						where += " AND ";
					}
					if (key.equals(PlanificacionService.FKEY_DESDE)) {
						where += " a.fecha_inicio >='"
								+ SecurityFilter.escapeString(filtros.get(key))
								+ " 00:00:00'";
					} else if (key.equals(PlanificacionService.FKEY_HASTA)) {
						where += " a.fecha_inicio <='"
								+ SecurityFilter.escapeString(filtros.get(key))
								+ " 23:59:59'";
					} else if (key.equals(PlanificacionService.FKEY_COMUNA)) {
						where += " e.comuna_id ="
								+ SecurityFilter.escapeString(filtros.get(key));
					} else if (key.equals(PlanificacionService.FKEY_REGION)) {
						where += " p.region_id ="
								+ SecurityFilter.escapeString(filtros.get(key));
					} else if (key.equals(PlanificacionService.FKEY_ESTADOS)) {
						String[] estados = filtros.get(key).split(
								PlanificacionService.SEPARATOR);
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
					}
				}
			}
			if (!where.equals("")) {
				query += " WHERE " + where;
			}
		}
		Query q = s.createSQLQuery(query);
		result = ((BigInteger) q.uniqueResult()).intValue();
		return result;
	}

	public List<ActividadPreviewDTO> findActividadesByIdAplicacionANDIdNivelANDIdActividadTipoANDFiltros(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			Integer idUsuario, String usuarioTipo, Integer offset,
			Integer lenght, Map<String, String> filtros, String baseURL) {

		List<ActividadPreviewDTO> apdtos = new ArrayList<ActividadPreviewDTO>();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "";
		if (idAplicacion == 2) {
			query += "WITH pendrive_sinc AS ("
					+ " SELECT axa.actividad_id,axaxd.alumno_x_actividad_id,axaxd.documento_id"
					+ " FROM ALUMNO_x_ACTIVIDAD axa"
					+ " JOIN ALUMNO_x_ACTIVIDAD_x_DOCUMENTO axaxd ON axa.id=axaxd.alumno_x_actividad_id"
					+ " JOIN DOCUMENTO_ESTADO de ON (axaxd.documento_estado_id=de.id AND de.nombre='"
					+ SecurityFilter.escapeString(DocumentoEstado.SINCRONIZADO)
					+ "')"
					+ " JOIN DOCUMENTO d ON axaxd.documento_id=d.id"
					+ " JOIN DOCUMENTO_TIPO dt ON (d.documento_tipo_id=dt.id AND dt.nombre='"
					+ SecurityFilter.escapeString(DocumentoTipo.PRUEBA) + "'))";
		}

		query += " SELECT DISTINCT c.id as curso_id,c.nombre as nombre_curso,e.id as establecimiento_id,e.nombre as establecimiento_nombre,"
				+ "et.id as est_tipo_id,et.nombre as est_tipo_nombre,"
				+ "r.nombre as region_nombre,COMUNA.id as comuna_id,COMUNA.nombre as comuna_nombre,a.total_alumnos,a.total_alumnos_ausentes,a.total_alumnos_presentes,"
				+ "axdt_cuest.total_entregados,axdt_cuest.total_recibidos,arc_form.id as arc_form_id,arc_form.titulo as arc_form_tit,"
				+ "COUNT(axaxd_def.id) as total_defectuosos,bool_or(axi.id IS NOT NULL) as contingencia, bool_or(axi.inhabilita_aplicacion) as limitante,"
				+ "ae.id as act_est_id,ae.nombre as act_est,"
				+ "u_ex.id as ex_id,u_ex.username as user_ex,u_ex.email as email_ex,u_ex.nombres as nom_ex,u_ex.apellido_paterno as ap_pat_ex,u_ex.apellido_materno as ap_mat_ex,"
				+ "u_sup.id as sup_id,u_sup.username as user_sup,u_sup.email as email_sup,u_sup.nombres as nom_sup,u_sup.apellido_paterno as ap_pat_sup,u_sup.apellido_materno as ap_mat_sup,"
				+ "a.contacto_nombre, a.contacto_telefono, a.contacto_email";
		if (idAplicacion == 2) {
			query += ",act_sinc.total_sinc,cuest_sinc.total_cuest_sinc";
		}
		query += " FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id AND axnxat.actividad_tipo_id="
				+ SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id AND a.actividad_estado_id!=7"
				+ " LEFT JOIN ACTIVIDAD_ESTADO ae ON a.actividad_estado_id=ae.id"
				+ " LEFT JOIN ACTIVIDAD_x_INCIDENCIA axi ON a.id=axi.actividad_id"
				+ " LEFT JOIN ALUMNO_x_ACTIVIDAD axa_def ON a.id=axa_def.actividad_id AND axa_def.alumno_id IS NULL"
				+ " LEFT JOIN ALUMNO_x_ACTIVIDAD_x_DOCUMENTO axaxd_def ON axa_def.id=axaxd_def.alumno_x_actividad_id"
				+ " LEFT JOIN ACTIVIDAD_x_DOCUMENTO_TIPO axdt_cuest ON a.id=axdt_cuest.actividad_id"
				+ " LEFT JOIN DOCUMENTO_TIPO dt_cuest ON axdt_cuest.documento_tipo_id=dt_cuest.id AND dt_cuest.nombre='"
				+ SecurityFilter.escapeString(DocumentoTipo.CUESTIONARIO_PADRE)
				+ "'";

		if (idAplicacion == 2) {
			query += " LEFT JOIN (SELECT actividad_id,COUNT(documento_id) AS total_sinc FROM pendrive_sinc "
					+ " GROUP BY actividad_id) act_sinc ON a.id=act_sinc.actividad_id"

					+ " LEFT JOIN (SELECT pendrive_sinc.actividad_id,COUNT(cuest.id) AS total_cuest_sinc FROM pendrive_sinc"
					+ " JOIN ALUMNO_x_ACTIVIDAD_x_DOCUMENTO axaxd ON pendrive_sinc.alumno_x_actividad_id=axaxd.alumno_x_actividad_id AND axaxd.recibido=true"
					+ " JOIN DOCUMENTO cuest ON axaxd.documento_id=cuest.id"
					+ " JOIN DOCUMENTO_TIPO dt ON (cuest.documento_tipo_id=dt.id AND dt.nombre='"
					+ SecurityFilter
							.escapeString(DocumentoTipo.CUESTIONARIO_PADRE)
					+ "')"
					+ " GROUP BY actividad_id) cuest_sinc ON a.id=cuest_sinc.actividad_id";
		}

		/*
		 * +
		 * " LEFT JOIN ACTIVIDAD_x_DOCUMENTO axd_form ON a.id=axd_form.actividad_id"
		 * + " LEFT JOIN DOCUMENTO d_form ON axd_form.documento_id=d_form.id" +
		 * " LEFT JOIN DOCUMENTO_TIPO dt_form ON d_form.documento_tipo_id=dt_form.id AND dt_form.nombre='"
		 * + SecurityFilter
		 * .escapeString(DocumentoTipo.FORMULARIO_CONTROL_DE_APLICACION) + "'" +
		 * " LEFT JOIN ARCHIVO arc_form ON d_form.archivo_id=arc_form.id"
		 */

		query += " LEFT JOIN (SELECT axd_form.actividad_id,MAX(arc_form.id) as archivo_id FROM ACTIVIDAD_x_DOCUMENTO axd_form"
				+ " LEFT JOIN DOCUMENTO d_form ON axd_form.documento_id=d_form.id"
				+ " LEFT JOIN DOCUMENTO_TIPO dt_form ON d_form.documento_tipo_id=dt_form.id AND dt_form.nombre='"
				+ SecurityFilter
						.escapeString(DocumentoTipo.FORMULARIO_CONTROL_DE_APLICACION)
				+ "'"
				+ " LEFT JOIN ARCHIVO arc_form ON d_form.archivo_id=arc_form.id GROUP BY axd_form.actividad_id) axd_form ON a.id=axd_form.actividad_id"
				+ " LEFT JOIN ARCHIVO arc_form ON axd_form.archivo_id=arc_form.id"

				+ " JOIN CURSO c ON a.curso_id=c.id"
				+ " JOIN ESTABLECIMIENTO e ON c.establecimiento_id=e.id"
				+ " LEFT JOIN APLICACION_x_ESTABLECIMIENTO axe ON (e.id=axe.establecimiento_id AND axe.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ ")"
				+ " LEFT JOIN ESTABLECIMIENTO_TIPO et ON axe.establecimiento_tipo_id=et.id"
				+ " JOIN COMUNA ON e.comuna_id=COMUNA.id"
				+ " JOIN PROVINCIA p ON COMUNA.provincia_id=p.id"
				+ " JOIN REGION r ON p.region_id=r.id"

				+ " LEFT JOIN USUARIO_x_ACTIVIDAD uxa_ex ON (a.id=uxa_ex.actividad_id AND (uxa_ex.asistencia != false OR uxa_ex.asistencia IS NULL) AND uxa_ex.usuario_seleccion_id IS NOT NULL AND (uxa_ex.usuario_tipo_id=11 OR uxa_ex.usuario_tipo_id=12 OR uxa_ex.usuario_tipo_id=13))"
				+ " LEFT JOIN USUARIO_SELECCION us_ex ON (uxa_ex.usuario_seleccion_id=us_ex.id AND (us_ex.usuario_tipo_id=11 OR us_ex.usuario_tipo_id=12 OR us_ex.usuario_tipo_id=13))"
				+ " LEFT JOIN USUARIO_x_APLICACION_x_NIVEL uxaxn_ex ON us_ex.usuario_x_aplicacion_x_nivel_id=uxaxn_ex.id"
				+ " LEFT JOIN USUARIO u_ex ON uxaxn_ex.usuario_id=u_ex.id"

				+ " LEFT JOIN USUARIO_x_ACTIVIDAD uxa_sup ON (a.id=uxa_sup.actividad_id AND (uxa_sup.asistencia != false OR uxa_sup.asistencia IS NULL) AND uxa_sup.usuario_seleccion_id IS NOT NULL AND (uxa_sup.usuario_tipo_id=9 OR uxa_sup.usuario_tipo_id=10))"
				+ " LEFT JOIN USUARIO_SELECCION us_sup ON (uxa_sup.usuario_seleccion_id=us_sup.id AND (us_sup.usuario_tipo_id=9 OR us_sup.usuario_tipo_id=10))"
				+ " LEFT JOIN USUARIO_x_APLICACION_x_NIVEL uxaxn_sup ON us_sup.usuario_x_aplicacion_x_nivel_id=uxaxn_sup.id"
				+ " LEFT JOIN USUARIO u_sup ON uxaxn_sup.usuario_id=u_sup.id";

		if (usuarioTipo.equals(UsuarioTipo.JEFE_REGIONAL)
				|| usuarioTipo.equals(UsuarioTipo.JEFE_ZONAL)
				|| usuarioTipo.equals(UsuarioTipo.JEFE_CENTRO_OPERACIONES)
				|| usuarioTipo.equals(UsuarioTipo.LOGISTICA_Y_SOPORTE)) {
			query += " JOIN CO_x_ESTABLECIMIENTO coxe ON (e.id=coxe.establecimiento_id  AND axn.id=coxe.aplicacion_x_nivel_id)";
			if (usuarioTipo.equals(UsuarioTipo.JEFE_CENTRO_OPERACIONES)
					|| usuarioTipo.equals(UsuarioTipo.LOGISTICA_Y_SOPORTE)) {
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
						// } else if (key
						// .equals(ActividadService.FKEY_ACTIVIDADES_CONTINGENCIA))
						// {
						// if (!opciones.equals("")) {
						// opciones += " OR ";
						// }
						// // opciones += " contingencia=true";
						// opciones += " false";
						// } else if (key
						// .equals(ActividadService.FKEY_ACTIVIDADES_CONTINGENCIA_INHABILITANTE))
						// {
						// if (!opciones.equals("")) {
						// opciones += " OR ";
						// }
						// // opciones += " limitante=true";
						// opciones += " false";
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
		query += " GROUP BY c.id,c.nombre,e.id,e.nombre,et.id,et.nombre,"
				+ "r.nombre,COMUNA.id,COMUNA.nombre,a.total_alumnos,a.total_alumnos_ausentes,a.total_alumnos_presentes,"
				+ "axdt_cuest.total_entregados,axdt_cuest.total_recibidos,arc_form.id,arc_form.titulo,ae.id,ae.nombre,"
				+ "u_ex.id,u_ex.username,u_ex.email,u_ex.nombres,u_ex.apellido_paterno,u_ex.apellido_materno,"
				+ "u_sup.id,u_sup.username,u_sup.email,u_sup.nombres,u_sup.apellido_paterno,u_sup.apellido_materno,"
				+ "a.contacto_nombre, a.contacto_telefono, a.contacto_email";
		if (idAplicacion == 2) {
			query += ",act_sinc.total_sinc,cuest_sinc.total_cuest_sinc";
		}
		query += " ORDER BY COMUNA.id,e.id,c.id";
		Query q = s.createSQLQuery(query);
		if (offset != null) {
			q.setFirstResult(offset);
		}
		if (lenght != null) {
			q.setMaxResults(lenght);
		}
		List<Object[]> os = q.list();
		ActividadPreviewDTO apdto = null;
		UserDTO udto = null;
		DocumentoDTO ddto = new DocumentoDTO();
		for (Object[] o : os) {
			apdto = new ActividadPreviewDTO();
			apdto.setCursoId((Integer) o[0]);
			apdto.setCurso((String) o[1]);
			apdto.setRbd(Integer.toString((Integer) o[2]));
			apdto.setNombreEstablecimiento((String) o[3]);
			apdto.setTipoEstablecimiento((String) o[5]);
			apdto.setRegion((String) o[6]);
			apdto.setComuna((String) o[8]);
			apdto.setCuestionariosPadresApoderadosEntregados((o[12] == null) ? 0
					: (Integer) o[12]);
			apdto.setCuestionariosPadresApoderadosRecibidos((o[13] == null) ? 0
					: (Integer) o[13]);
			apdto.setAlumnosTotales((o[9] == null) ? 0 : (Integer) o[9]);
			apdto.setAlumnosEvaluados((o[10] == null) ? 0 : (apdto
					.getAlumnosTotales() - (Integer) o[10]));
			if (idAplicacion == 2) {
				apdto.setAlumnosSincronizados((o[36] == null) ? 0
						: ((BigInteger) o[36]).intValue());
				apdto.setCuestionariosPadresApoderadosRecibidosAplicados((o[37] == null) ? 0
						: ((BigInteger) o[37]).intValue());
			} else {
				apdto.setAlumnosSincronizados((o[11] == null) ? 0
						: (Integer) o[11]);
			}
			if (o[14] != null) {
				ddto = new DocumentoDTO();
				ddto.setId((Integer) o[14]);
				ddto.setName((o[15] == null) ? "" : (String) o[15]);
				ddto.setUrl(Archivo.buildDownloadURL(baseURL, ddto.getId(),
						ddto.getName()));
				apdto.setDocumento(ddto);
			}
			apdto.setMaterialDefectuoso((o[16] == null) ? 0
					: ((BigInteger) o[16]).intValue());
			apdto.setContingencia((o[17] == null) ? false : (Boolean) o[17]);
			apdto.setContingenciaLimitante((o[18] == null) ? false
					: (Boolean) o[18]);
			apdto.setEstadoAgenda((String) o[20]);
			if (o[24] != null) {
				apdto.setNombreExaminador(StringUtils
						.nombreInicialSegundo((String) o[24])
						+ " "
						+ (String) o[25] + " " + (String) o[26]);
			}
			if (o[30] != null) {
				apdto.setNombreSupervisor(StringUtils
						.nombreInicialSegundo((String) o[30])
						+ " "
						+ (String) o[31] + " " + (String) o[32]);
			}
			apdto.setNombreContacto((String) o[33]);
			apdto.setTelefonoContacto((String) o[34]);
			apdto.setMailContacto((String) o[35]);
			apdtos.add(apdto);
		}

		return apdtos;
	}

	public Integer countActividadesByIdAplicacionANDIdNivelANDIdActividadTipoANDFiltros(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			Integer idUsuario, String usuarioTipo, Map<String, String> filtros) {

		Integer result = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT COUNT(a.id) FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id AND axnxat.actividad_tipo_id="
				+ SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id AND a.actividad_estado_id!=7"
				+ " JOIN CURSO c ON a.curso_id=c.id"
				+ " JOIN ESTABLECIMIENTO e ON c.establecimiento_id=e.id"
				+ " JOIN COMUNA ON e.comuna_id=COMUNA.id"
				+ " JOIN PROVINCIA p ON COMUNA.provincia_id=p.id"
				+ " JOIN REGION r ON p.region_id=r.id";
		if (usuarioTipo.equals(UsuarioTipo.JEFE_REGIONAL)
				|| usuarioTipo.equals(UsuarioTipo.JEFE_ZONAL)
				|| usuarioTipo.equals(UsuarioTipo.JEFE_CENTRO_OPERACIONES)
				|| usuarioTipo.equals(UsuarioTipo.LOGISTICA_Y_SOPORTE)) {
			query += " JOIN CO_x_ESTABLECIMIENTO coxe ON (e.id=coxe.establecimiento_id  AND axn.id=coxe.aplicacion_x_nivel_id)";
			if (usuarioTipo.equals(UsuarioTipo.JEFE_CENTRO_OPERACIONES)
					|| usuarioTipo.equals(UsuarioTipo.LOGISTICA_Y_SOPORTE)) {
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
						// } else if (key
						// .equals(ActividadService.FKEY_ACTIVIDADES_CONTINGENCIA))
						// {
						// if (!opciones.equals("")) {
						// opciones += " OR ";
						// }
						// // opciones += " contingencia=true";
						// opciones += " false";
						// } else if (key
						// .equals(ActividadService.FKEY_ACTIVIDADES_CONTINGENCIA_INHABILITANTE))
						// {
						// if (!opciones.equals("")) {
						// opciones += " OR ";
						// }
						// // opciones += " limitante=true";
						// opciones += " false";
					} else if (key
							.equals(ActividadService.FKEY_ACTIVIDADES_MATERIAL_CONTINGENCIA)) {
						if (!opciones.equals("")) {
							opciones += " OR ";
						}
						// No se está comprobando por materiales de contingencia
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

	public Actividad findByIdAplicacionANDIdNivelANDIdCursoANDTipoActividad(
			Integer idAplicacion, Integer idNivel, Integer idCurso,
			String tipoActividad) {

		Actividad a = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT a.* FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id)"
				+ " JOIN ACTIVIDAD_TIPO at ON (axnxat.actividad_tipo_id=at.id AND at.nombre='"
				+ SecurityFilter.escapeString(tipoActividad)
				+ "')"
				+ " JOIN ACTIVIDAD a ON (axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id AND a.curso_id="
				+ SecurityFilter.escapeString(idCurso)
				+ " AND a.actividad_estado_id!=7)";
		Query q = s.createSQLQuery(query).addEntity(Actividad.class);
		a = ((Actividad) q.uniqueResult());
		return a;
	}

	public List<Actividad> findAgendadasParaMañanaByIdAplicacionANDIdNivelANDIdActividadTipo(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo) {

		List<Actividad> as = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "select a.* FROM ACTIVIDAD a "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (a.aplicacion_x_nivel_x_actividad_tipo_id=axnxat.id AND axnxat.actividad_tipo_id="
				+ SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN APLICACION_x_NIVEL axn ON (axnxat.aplicacion_x_nivel_id=axn.id AND axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ ")"
				+ " WHERE a.fecha_inicio > now() and a.fecha_inicio < (now()+interval'1 day') and a.actividad_estado_id > 2   AND a.actividad_estado_id!=7";
		Query q = s.createSQLQuery(query).addEntity(Actividad.class);
		as = q.list();
		return as;
	}

	public List<Actividad> findByIdAplicacionANDIdNivelANDIdCursoANDBiggerThanFechaInicio(
			Integer idAplicacion, Integer idNivel, Integer idCurso,
			Date fechaInicio) {
		List<Actividad> as = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "select a.* FROM ACTIVIDAD a "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON a.aplicacion_x_nivel_x_actividad_tipo_id=axnxat.id"
				+ " JOIN APLICACION_x_NIVEL axn ON (axnxat.aplicacion_x_nivel_id=axn.id AND axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ ")"
				+ " WHERE a.fecha_inicio >  '"
				+ SecurityFilter.escapeString(StringUtils
						.getDateISOString(fechaInicio)) + "' AND a.curso_id="
				+ SecurityFilter.escapeString(idCurso)
				+ " AND a.actividad_estado_id!=7";
		Query q = s.createSQLQuery(query).addEntity(Actividad.class);
		as = q.list();
		return as;
	}
}
