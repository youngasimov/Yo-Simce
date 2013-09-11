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
import com.dreamer8.yosimce.server.CustomRemoteServiceServlet;
import com.dreamer8.yosimce.server.hibernate.pojo.Actividad;
import com.dreamer8.yosimce.server.hibernate.pojo.ActividadTipo;
import com.dreamer8.yosimce.server.hibernate.pojo.Archivo;
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
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id";
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
				+ SecurityFilter.escapeString(idEstablecimiento);

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
				+ SecurityFilter.escapeString(idCurso) + ")";

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
				+ "u.email,u.nombres,u.apellido_paterno,u.apellido_materno,ae.id as act_est_id,ae.nombre as act_est_nombre FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id AND axnxat.actividad_tipo_id="
				+ SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id"
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
				+ " LEFT JOIN USUARIO u ON a.modificador_id=u.id";
		;
		if (usuarioTipo.equals(UsuarioTipo.JEFE_REGIONAL)
				|| usuarioTipo.equals(UsuarioTipo.JEFE_ZONAL)
				|| usuarioTipo.equals(UsuarioTipo.JEFE_CENTRO_OPERACIONES)) {
			query += " JOIN CO_x_ESTABLECIMIENTO coxe ON e.id=coxe.establecimiento_id";
			if (usuarioTipo.equals(UsuarioTipo.JEFE_CENTRO_OPERACIONES)) {
				query += " JOIN JO_x_CO joxco ON (coxe.co_id=joxco.co_id AND joxco.jo_id="
						+ SecurityFilter.escapeString(idUsuario)
						+ ") AND joxco.activo=TRUE";
			} else {
				query += " JOIN CO co ON coxe.co_id=co.co_id";
				if (usuarioTipo.equals(UsuarioTipo.JEFE_ZONAL)) {
					query += " JOIN JZ_x_ZONA jzxz ON (co.zona_id=jzxz=zona_id AND jzxz.jz_id="
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
								+ "'";
					} else if (key.equals(PlanificacionService.FKEY_HASTA)) {
						where += " a.fecha_inicio <='"
								+ SecurityFilter.escapeString(filtros.get(key))
								+ "'";
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
			aidto.setFecha((Date) o[9]);
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
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id"
				+ " JOIN CURSO c ON a.curso_id=c.id"
				+ " JOIN ESTABLECIMIENTO e ON c.establecimiento_id=e.id"
				+ " JOIN COMUNA ON e.comuna_id=COMUNA.id"
				+ " JOIN PROVINCIA p ON COMUNA.provincia_id=p.id"
				+ " JOIN REGION r ON p.region_id=r.id";
		if (usuarioTipo.equals(UsuarioTipo.JEFE_REGIONAL)
				|| usuarioTipo.equals(UsuarioTipo.JEFE_ZONAL)
				|| usuarioTipo.equals(UsuarioTipo.JEFE_CENTRO_OPERACIONES)) {
			query += " JOIN CO_x_ESTABLECIMIENTO coxe ON e.id=coxe.establecimiento_id";
			if (usuarioTipo.equals(UsuarioTipo.JEFE_CENTRO_OPERACIONES)) {
				query += " JOIN JO_x_CO joxco ON (coxe.co_id=joxco.co_id AND joxco.jo_id="
						+ SecurityFilter.escapeString(idUsuario)
						+ ") AND joxco.activo=TRUE";
			} else {
				query += " JOIN CO co ON coxe.co_id=co.co_id";
				if (usuarioTipo.equals(UsuarioTipo.JEFE_ZONAL)) {
					query += " JOIN JZ_x_ZONA jzxz ON (co.zona_id=jzxz=zona_id AND jzxz.jz_id="
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
								+ "'";
					} else if (key.equals(PlanificacionService.FKEY_HASTA)) {
						where += " a.fecha_inicio <='"
								+ SecurityFilter.escapeString(filtros.get(key))
								+ "'";
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
			Integer idAplicacion, Integer idNivel, Integer idUsuario,
			String usuarioTipo, Integer offset, Integer lenght,
			Map<String, String> filtros) {

		List<String> csv = new ArrayList<String>();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT DISTINCT c.id as curso_id,e.id as establecimiento_id,e.nombre as establecimiento_nombre,"
				+ "et.nombre as est_tipo_nombre,"
				+ "r.nombre as region_nombre,COMUNA.id as comuna_id,COMUNA.nombre as comuna_nombre,"
				+ "at.nombre as act_tipo_nombre,a.fecha_inicio,a.comentario,ae.nombre as act_est_nombre, "
				+ "a.contacto_nombre,a.contacto_telefono,a.contacto_email,cc.nombre as contacto_cargo,"
				+ "a.aplicacion_x_nivel_x_actividad_tipo_id"
				+ " FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id"
				+ ")"
				+ " JOIN ACTIVIDAD_TIPO at ON axnxat.actividad_tipo_id=at.id"
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id"
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
				+ " LEFT JOIN USUARIO u ON a.modificador_id=u.id";
		;
		if (usuarioTipo.equals(UsuarioTipo.JEFE_REGIONAL)
				|| usuarioTipo.equals(UsuarioTipo.JEFE_ZONAL)
				|| usuarioTipo.equals(UsuarioTipo.JEFE_CENTRO_OPERACIONES)) {
			query += " JOIN CO_x_ESTABLECIMIENTO coxe ON e.id=coxe.establecimiento_id";
			if (usuarioTipo.equals(UsuarioTipo.JEFE_CENTRO_OPERACIONES)) {
				query += " JOIN JO_x_CO joxco ON (coxe.co_id=joxco.co_id AND joxco.jo_id="
						+ SecurityFilter.escapeString(idUsuario)
						+ ") AND joxco.activo=TRUE";
			} else {
				query += " JOIN CO co ON coxe.co_id=co.co_id";
				if (usuarioTipo.equals(UsuarioTipo.JEFE_ZONAL)) {
					query += " JOIN JZ_x_ZONA jzxz ON (co.zona_id=jzxz=zona_id AND jzxz.jz_id="
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
								+ "'";
					} else if (key.equals(PlanificacionService.FKEY_HASTA)) {
						where += " a.fecha_inicio <='"
								+ SecurityFilter.escapeString(filtros.get(key))
								+ "'";
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
		Integer idCursoAnterior = null;
		Integer idCurso = null;
		String linea = null;
		String visitaPrevia = null;
		String aplic = null;
		String contacto = null;
		if (os != null && !os.isEmpty()) {
			csv.add("rbd;establecimiento_nombre;establecimiento_tipo;región;comuna;"
					+ "fecha_visita_previa;estado_agendamiento_visita_previa;comentario_visita_previa;"
					+ "fecha_aplicación;estado_agendamiento_aplicación;comentario_aplicación;"
					+ "nombre_contacto;cargo_contacto;teléfono_contacto;email_cotacto");
			for (Object[] o : os) {
				idCurso = (Integer) o[0];
				if (!idCurso.equals(idCursoAnterior)) {
					idCursoAnterior = idCurso;
					linea = Integer.toString((Integer) o[1]) + ";"
							+ ((String) o[2]) + ";" + ((String) o[3]) + ";"
							+ ((String) o[4]) + ";" + ((String) o[6]);
					contacto = ";" + ((String) o[11]) + ";" + ((String) o[14])
							+ ";" + ((String) o[12]) + ";" + ((String) o[13]);
					visitaPrevia = null;
					aplic = null;
				}
				if (ActividadTipo.VISITA_PREVIA.equals((String) o[7])) {
					visitaPrevia = ";" + ((Date) o[8]) + ";" + ((String) o[10])
							+ ";" + ((String) o[9]);
				} else if (ActividadTipo.APLICACION_DIA_1.equals((String) o[7])) {
					aplic = ";" + ((Date) o[8]) + ";" + ((String) o[10]) + ";"
							+ ((String) o[9]);
				}
				if (visitaPrevia != null && aplic != null) {
					linea += visitaPrevia + aplic + contacto;
					csv.add(linea);
				}
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
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id"
				+ " JOIN CURSO c ON a.curso_id=c.id"
				+ " JOIN ESTABLECIMIENTO e ON c.establecimiento_id=e.id"
				+ " JOIN COMUNA ON e.comuna_id=COMUNA.id"
				+ " JOIN PROVINCIA p ON COMUNA.provincia_id=p.id"
				+ " JOIN REGION r ON p.region_id=r.id";
		if (usuarioTipo.equals(UsuarioTipo.JEFE_REGIONAL)
				|| usuarioTipo.equals(UsuarioTipo.JEFE_ZONAL)
				|| usuarioTipo.equals(UsuarioTipo.JEFE_CENTRO_OPERACIONES)) {
			query += " JOIN CO_x_ESTABLECIMIENTO coxe ON e.id=coxe.establecimiento_id";
			if (usuarioTipo.equals(UsuarioTipo.JEFE_CENTRO_OPERACIONES)) {
				query += " JOIN JO_x_CO joxco ON (coxe.co_id=joxco.co_id AND joxco.jo_id="
						+ SecurityFilter.escapeString(idUsuario)
						+ ") AND joxco.activo=TRUE";
			} else {
				query += " JOIN CO co ON coxe.co_id=co.co_id";
				if (usuarioTipo.equals(UsuarioTipo.JEFE_ZONAL)) {
					query += " JOIN JZ_x_ZONA jzxz ON (co.zona_id=jzxz=zona_id AND jzxz.jz_id="
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
								+ "'";
					} else if (key.equals(PlanificacionService.FKEY_HASTA)) {
						where += " a.fecha_inicio <='"
								+ SecurityFilter.escapeString(filtros.get(key))
								+ "'";
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
		String query = "SELECT DISTINCT c.id as curso_id,c.nombre as nombre_curso,e.id as establecimiento_id,e.nombre as establecimiento_nombre,"
				+ "et.id as est_tipo_id,et.nombre as est_tipo_nombre,"
				+ "r.nombre as region_nombre,COMUNA.id as comuna_id,COMUNA.nombre as comuna_nombre,a.total_alumnos,a.total_alumnos_ausentes,a.total_alumnos_presentes,"
				+ "axdt_cuest.total_entregados,axdt_cuest.total_recibidos,arc_form.id as arc_form_id,arc_form.titulo as arc_form_tit,"
				+ "COUNT(axaxd_def.id) as total_defectuosos,bool_or(axi.id IS NOT NULL) as contingencia, bool_or(axi.inhabilita_aplicacion) as limitante,"
				+ "ae.id as act_est_id,ae.nombre as act_est"
				+ " FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id AND axnxat.actividad_tipo_id="
				+ SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id"
				+ " LEFT JOIN ACTIVIDAD_ESTADO ae ON a.actividad_estado_id=ae.id"
				+ " LEFT JOIN ACTIVIDAD_x_INCIDENCIA axi ON a.id=axi.actividad_id"
				+ " LEFT JOIN ALUMNO_x_ACTIVIDAD axa_def ON a.id=axa_def.actividad_id AND axa_def.alumno_id IS NULL"
				+ " LEFT JOIN ALUMNO_x_ACTIVIDAD_x_DOCUMENTO axaxd_def ON axa_def.id=axaxd_def.alumno_x_actividad_id"
				+ " LEFT JOIN ACTIVIDAD_x_DOCUMENTO_TIPO axdt_cuest ON a.id=axdt_cuest.actividad_id"
				+ " LEFT JOIN DOCUMENTO_TIPO dt_cuest ON axdt_cuest.documento_tipo_id=dt_cuest.id AND dt_cuest.nombre='"
				+ SecurityFilter.escapeString(DocumentoTipo.CUESTIONARIO_PADRE)
				+ "'"
				+ " LEFT JOIN ACTIVIDAD_x_DOCUMENTO axd_form ON a.id=axd_form.actividad_id"
				+ " LEFT JOIN DOCUMENTO d_form ON axd_form.documento_id=d_form.id"
				+ " LEFT JOIN DOCUMENTO_TIPO dt_form ON d_form.documento_tipo_id=dt_form.id AND dt_form.nombre='"
				+ SecurityFilter
						.escapeString(DocumentoTipo.FORMULARIO_CONTROL_DE_APLICACION)
				+ "'"
				+ " LEFT JOIN ARCHIVO arc_form ON d_form.archivo_id=arc_form.id"
				+ " JOIN CURSO c ON a.curso_id=c.id"
				+ " JOIN ESTABLECIMIENTO e ON c.establecimiento_id=e.id"
				+ " LEFT JOIN APLICACION_x_ESTABLECIMIENTO axe ON (e.id=axe.establecimiento_id AND axe.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ ")"
				+ " LEFT JOIN ESTABLECIMIENTO_TIPO et ON axe.establecimiento_tipo_id=et.id"
				+ " JOIN COMUNA ON e.comuna_id=COMUNA.id"
				+ " JOIN PROVINCIA p ON COMUNA.provincia_id=p.id"
				+ " JOIN REGION r ON p.region_id=r.id";
		if (usuarioTipo.equals(UsuarioTipo.JEFE_REGIONAL)
				|| usuarioTipo.equals(UsuarioTipo.JEFE_ZONAL)
				|| usuarioTipo.equals(UsuarioTipo.JEFE_CENTRO_OPERACIONES)) {
			query += " JOIN CO_x_ESTABLECIMIENTO coxe ON e.id=coxe.establecimiento_id";
			if (usuarioTipo.equals(UsuarioTipo.JEFE_CENTRO_OPERACIONES)) {
				query += " JOIN JO_x_CO joxco ON (coxe.co_id=joxco.co_id AND joxco.jo_id="
						+ SecurityFilter.escapeString(idUsuario)
						+ ") AND joxco.activo=TRUE";
			} else {
				query += " JOIN CO co ON coxe.co_id=co.co_id";
				if (usuarioTipo.equals(UsuarioTipo.JEFE_ZONAL)) {
					query += " JOIN JZ_x_ZONA jzxz ON (co.zona_id=jzxz=zona_id AND jzxz.jz_id="
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
					if (key.equals(ActividadService.FKEY_COMUNA)) {
						where += " e.comuna_id ="
								+ SecurityFilter.escapeString(filtros.get(key));
					} else if (key.equals(ActividadService.FKEY_REGION)) {
						where += " p.region_id ="
								+ SecurityFilter.escapeString(filtros.get(key));
					} else if (key.equals(ActividadService.FKEY_ESTADOS)) {
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
						// where += " contingencia=true";
						where += " true";
					} else if (key
							.equals(ActividadService.FKEY_ACTIVIDADES_CONTINGENCIA_INHABILITANTE)) {
						// where += " limitante=true";
						where += " true";
					} else if (key
							.equals(ActividadService.FKEY_ACTIVIDADES_MATERIAL_CONTINGENCIA)) {
						// No se está comprobando por materiales de contingencia
						where += " true";
					} else if (key
							.equals(ActividadService.FKEY_ACTIVIDADES_NO_SINCRONIZADAS)) {
						where += " (a.total_alumnos_presentes IS NULL OR a.total_alumnos_presentes=0)";
					} else if (key
							.equals(ActividadService.FKEY_ACTIVIDADES_PARCIALMENTE_SINCRONIZADAS)) {
						where += " (a.total_alumnos_presentes IS NOT NULL AND a.total_alumnos_presentes!=0)";
					} else if (key
							.equals(ActividadService.FKEY_ACTIVIDADES_SINCRONIZADAS)) {
						where += " (a.total_alumnos_presentes IS NOT NULL AND a.total_alumnos_presentes=a.total_alumnos)";
					}
				}
			}
			if (!where.equals("")) {
				query += " WHERE " + where;
			}
		}
		query += " GROUP BY c.id,c.nombre,e.id,e.nombre,et.id,et.nombre,"
				+ "r.nombre,COMUNA.id,COMUNA.nombre,a.total_alumnos,a.total_alumnos_ausentes,a.total_alumnos_presentes,"
				+ "axdt_cuest.total_entregados,axdt_cuest.total_recibidos,arc_form.id,arc_form.titulo,ae.id,ae.nombre"
				+ " ORDER BY COMUNA.id,e.id,c.id";
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
			apdto.setCuestionariosPadresApoderadosEntregados((Integer) o[12]);
			apdto.setCuestionariosPadresApoderadosRecibidos((Integer) o[13]);
			apdto.setAlumnosTotales((o[9] == null) ? 0 : (Integer) o[9]);
			apdto.setAlumnosEvaluados(apdto.getAlumnosTotales()
					- ((o[10] == null) ? 0 : (Integer) o[10]));
			apdto.setAlumnosSincronizados((o[11] == null) ? 0 : (Integer) o[11]);
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
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id"
				+ " JOIN CURSO c ON a.curso_id=c.id"
				+ " JOIN ESTABLECIMIENTO e ON c.establecimiento_id=e.id"
				+ " JOIN COMUNA ON e.comuna_id=COMUNA.id"
				+ " JOIN PROVINCIA p ON COMUNA.provincia_id=p.id"
				+ " JOIN REGION r ON p.region_id=r.id";
		if (usuarioTipo.equals(UsuarioTipo.JEFE_REGIONAL)
				|| usuarioTipo.equals(UsuarioTipo.JEFE_ZONAL)
				|| usuarioTipo.equals(UsuarioTipo.JEFE_CENTRO_OPERACIONES)) {
			query += " JOIN CO_x_ESTABLECIMIENTO coxe ON e.id=coxe.establecimiento_id";
			if (usuarioTipo.equals(UsuarioTipo.JEFE_CENTRO_OPERACIONES)) {
				query += " JOIN JO_x_CO joxco ON (coxe.co_id=joxco.co_id AND joxco.jo_id="
						+ SecurityFilter.escapeString(idUsuario)
						+ ") AND joxco.activo=TRUE";
			} else {
				query += " JOIN CO co ON coxe.co_id=co.co_id";
				if (usuarioTipo.equals(UsuarioTipo.JEFE_ZONAL)) {
					query += " JOIN JZ_x_ZONA jzxz ON (co.zona_id=jzxz=zona_id AND jzxz.jz_id="
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
					if (key.equals(ActividadService.FKEY_COMUNA)) {
						where += " e.comuna_id ="
								+ SecurityFilter.escapeString(filtros.get(key));
					} else if (key.equals(ActividadService.FKEY_REGION)) {
						where += " p.region_id ="
								+ SecurityFilter.escapeString(filtros.get(key));
					} else if (key.equals(ActividadService.FKEY_ESTADOS)) {
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
						// where += " contingencia=true";
						where += " true";
					} else if (key
							.equals(ActividadService.FKEY_ACTIVIDADES_CONTINGENCIA_INHABILITANTE)) {
						// where += " limitante=true";
						where += " true";
					} else if (key
							.equals(ActividadService.FKEY_ACTIVIDADES_MATERIAL_CONTINGENCIA)) {
						// No se está comprobando por materiales de contingencia
						where += " true";
					} else if (key
							.equals(ActividadService.FKEY_ACTIVIDADES_NO_SINCRONIZADAS)) {
						where += " (a.total_alumnos_presentes IS NULL OR a.total_alumnos_presentes=0)";
					} else if (key
							.equals(ActividadService.FKEY_ACTIVIDADES_PARCIALMENTE_SINCRONIZADAS)) {
						where += " (a.total_alumnos_presentes IS NOT NULL AND a.total_alumnos_presentes!=0)";
					} else if (key
							.equals(ActividadService.FKEY_ACTIVIDADES_SINCRONIZADAS)) {
						where += " (a.total_alumnos_presentes IS NOT NULL AND a.total_alumnos_presentes=a.total_alumnos)";
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
				+ SecurityFilter.escapeString(idCurso) + ")";
		Query q = s.createSQLQuery(query).addEntity(Actividad.class);
		a = ((Actividad) q.uniqueResult());
		return a;
	}
}
