package com.dreamer8.yosimce.server.hibernate.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.Usuario;
import com.dreamer8.yosimce.server.hibernate.pojo.UsuarioTipo;
import com.dreamer8.yosimce.server.utils.SecurityFilter;
import com.dreamer8.yosimce.server.utils.StringUtils;
import com.dreamer8.yosimce.shared.dto.TipoUsuarioDTO;
import com.dreamer8.yosimce.shared.dto.UserDTO;

public class UsuarioDAO extends AbstractHibernateDAO<Usuario, Integer> {

	/**
	 * @param idAplicacion
	 * @param id
	 * @param offset
	 * @param length
	 * @param filtro
	 * @return
	 */
	public List<UserDTO> findByIdAplicacionANDIdNivelANDIdTipoUsuarioSuperiorANDFiltro(
			Integer idAplicacion, Integer idNivel,
			Integer idTipoUsuarioSuperior, Integer offset, Integer length,
			String filtro) {

		List<UserDTO> udtos = new ArrayList<UserDTO>();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT u.id,u.email,u.nombres,u.apellido_paterno,u.apellido_materno,u.username,ut.id,ut.nombre FROM USUARIO_SELECCION us"
				+ " JOIN USUARIO_TIPO ut ON us.usuario_tipo_id=ut.id"
				+ " JOIN USUARIO_x_APLICACION_x_NIVEL uxaxn ON (us.usuario_x_aplicacion_x_nivel_id=uxaxn.id AND us.usuario_tipo_id > "
				+ SecurityFilter.escapeString(idTipoUsuarioSuperior)
				+ ")"
				+ " JOIN APLICACION_x_NIVEL axn ON (uxaxn.aplicacion_x_nivel_id=axn.id AND axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ ")"
				+ " JOIN USUARIO u ON uxaxn.usuario_id=u.id"
				+ " WHERE (u.username ILIKE '%"
				+ SecurityFilter.escapeLikeString(
						StringUtils.formatRut(filtro, true), "~")
				+ "%' ESCAPE '~'"
				+ " OR u.username ILIKE '%"
				+ SecurityFilter.escapeLikeString(
						StringUtils.formatRut(filtro, false), "~")
				+ "%' ESCAPE '~'"
				+ " OR u.nombres || ' ' || u.apellido_paterno || ' ' || u.apellido_materno ILIKE '%"
				+ SecurityFilter.escapeLikeString(filtro, "~")
				+ "%' ESCAPE '~')";
		Query q = s.createSQLQuery(query);
		q.setMaxResults(length);
		q.setFirstResult(offset);
		List<Object[]> os = q.list();
		UserDTO udto = null;
		TipoUsuarioDTO tudto = null;
		for (Object[] o : os) {
			udto = new UserDTO();
			udto.setId((Integer) o[0]);
			udto.setEmail((String) o[1]);
			udto.setNombres((String) o[2]);
			udto.setApellidoPaterno((String) o[3]);
			udto.setApellidoMaterno((String) o[4]);
			udto.setUsername((String) o[5]);
			tudto = new TipoUsuarioDTO();
			tudto.setId((Integer) o[6]);
			tudto.setTipoUsuario((String) o[7]);
			udto.setTipo(tudto);
			udtos.add(udto);
		}
		return udtos;
	}

	public List<UserDTO> findExaminadoresByIdAplicacionANDIdNivelANDFiltro(
			Integer idAplicacion, Integer idNivel, Integer offset,
			Integer length, String filtro) {

		List<UserDTO> udtos = new ArrayList<UserDTO>();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT u.id,u.email,u.nombres,u.apellido_paterno,u.apellido_materno,u.username,ut.id,ut.nombre FROM USUARIO_SELECCION us"
				+ " JOIN USUARIO_TIPO ut ON (us.usuario_tipo_id=ut.id AND (ut.nombre='"
				+ SecurityFilter.escapeString(UsuarioTipo.EXAMINADOR)
				+ "' OR ut.nombre='"
				+ SecurityFilter.escapeString(UsuarioTipo.EXAMINADOR_NEE)
				+ "' OR ut.nombre='"
				+ SecurityFilter.escapeString(UsuarioTipo.EXAMINADOR_SUPLENTE)
				+ "'))"
				+ " JOIN USUARIO_x_APLICACION_x_NIVEL uxaxn ON us.usuario_x_aplicacion_x_nivel_id=uxaxn.id"
				+ " JOIN APLICACION_x_NIVEL axn ON (uxaxn.aplicacion_x_nivel_id=axn.id AND axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ ")"
				+ " JOIN USUARIO u ON uxaxn.usuario_id=u.id"
				+ " WHERE (u.username ILIKE '%"
				+ SecurityFilter.escapeLikeString(
						StringUtils.formatRut(filtro, true), "~")
				+ "%' ESCAPE '~'"
				+ " OR u.username ILIKE '%"
				+ SecurityFilter.escapeLikeString(
						StringUtils.formatRut(filtro, false), "~")
				+ "%' ESCAPE '~'"
				+ " OR u.nombres || ' ' || u.apellido_paterno || ' ' || u.apellido_materno ILIKE '%"
				+ SecurityFilter.escapeLikeString(filtro, "~")
				+ "%' ESCAPE '~')";
		Query q = s.createSQLQuery(query);
		q.setMaxResults(length);
		q.setFirstResult(offset);
		List<Object[]> os = q.list();
		UserDTO udto = null;
		TipoUsuarioDTO tudto = null;
		for (Object[] o : os) {
			udto = new UserDTO();
			udto.setId((Integer) o[0]);
			udto.setEmail((String) o[1]);
			udto.setNombres((String) o[2]);
			udto.setApellidoPaterno((String) o[3]);
			udto.setApellidoMaterno((String) o[4]);
			udto.setUsername((String) o[5]);
			tudto = new TipoUsuarioDTO();
			tudto.setId((Integer) o[6]);
			tudto.setTipoUsuario((String) o[7]);
			udto.setTipo(tudto);
			udtos.add(udto);
		}
		return udtos;
	}

	public List<UserDTO> findByIdAplicacionANDIdNivelANDFiltro(
			Integer idAplicacion, Integer idNivel, Integer offset,
			Integer length, String filtro) {

		List<UserDTO> udtos = new ArrayList<UserDTO>();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT u.id,u.email,u.nombres,u.apellido_paterno,u.apellido_materno,u.username,ut.id,ut.nombre FROM USUARIO_SELECCION us"
				+ " JOIN USUARIO_TIPO ut ON us.usuario_tipo_id=ut.id"
				+ " JOIN USUAInteger RIO_x_APLICACION_x_NIVEL uxaxn ON us.usuario_x_aplicacion_x_nivel_id=uxaxn.id"
				+ " JOIN APLICACION_x_NIVEL axn ON (uxaxn.aplicacion_x_nivel_id=axn.id AND axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ ")"
				+ " JOIN USUARIO u ON uxaxn.usuario_id=u.id"
				+ " WHERE (u.username ILIKE '%"
				+ SecurityFilter.escapeLikeString(
						StringUtils.formatRut(filtro, true), "~")
				+ "%' ESCAPE '~'"
				+ " OR u.username ILIKE '%"
				+ SecurityFilter.escapeLikeString(
						StringUtils.formatRut(filtro, false), "~")
				+ "%' ESCAPE '~'"
				+ " OR u.nombres || ' ' || u.apellido_paterno || ' ' || u.apellido_materno ILIKE '%"
				+ SecurityFilter.escapeLikeString(filtro, "~")
				+ "%' ESCAPE '~')";
		Query q = s.createSQLQuery(query);
		q.setMaxResults(length);
		q.setFirstResult(offset);
		List<Object[]> os = q.list();
		UserDTO udto = null;
		TipoUsuarioDTO tudto = null;
		for (Object[] o : os) {
			udto = new UserDTO();
			udto.setId((Integer) o[0]);
			udto.setEmail((String) o[1]);
			udto.setNombres((String) o[2]);
			udto.setApellidoPaterno((String) o[3]);
			udto.setApellidoMaterno((String) o[4]);
			udto.setUsername((String) o[5]);
			tudto = new TipoUsuarioDTO();
			tudto.setId((Integer) o[6]);
			tudto.setTipoUsuario((String) o[7]);
			udto.setTipo(tudto);
			udtos.add(udto);
		}
		return udtos;
	}

	public Usuario findbyUsername(String username) {

		Usuario u = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT u.* FROM USUARIO u" + " WHERE u.username='"
				+ SecurityFilter.escapeString(username) + "'";
		Query q = s.createSQLQuery(query).addEntity(Usuario.class);
		u = ((Usuario) q.uniqueResult());
		return u;
	}

	public List<Usuario> findExaminadoresByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCurso(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			Integer idCurso) {
		List<Usuario> us = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT DISTINCT u.* FROM APLICACION_x_NIVEL axn "
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
				+ " JOIN USUARIO_x_ACTIVIDAD uxa ON a.id=uxa.actividad_id"
				+ " JOIN USUARIO_SELECCION us ON uxa.usuario_seleccion_id=us.id"
				+ " JOIN USUARIO_TIPO ut ON us.usuario_tipo_id=ut.id"
				+ " JOIN USUARIO_x_APLICACION_x_NIVEL uxaxn ON us.usuario_x_aplicacion_x_nivel_id=uxaxn.id"
				+ " JOIN USUARIO u ON uxaxn.usuario_id=u.id"
				+ " WHERE (uxa.asistencia IS NULL OR uxa.asistencia=true) AND (ut.nombre='"
				+ SecurityFilter.escapeString(UsuarioTipo.EXAMINADOR)
				+ "' OR ut.nombre='"
				+ SecurityFilter.escapeString(UsuarioTipo.EXAMINADOR_NEE)
				+ "' OR ut.nombre='"
				+ SecurityFilter.escapeString(UsuarioTipo.EXAMINADOR_SUPLENTE)
				+ "')";

		Query q = s.createSQLQuery(query).addEntity(Usuario.class);
		us = q.list();
		return us;
	}

	public Usuario findSupervisorByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCurso(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			Integer idCurso) {
		Usuario u = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT DISTINCT u.* FROM APLICACION_x_NIVEL axn "
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
				+ " JOIN USUARIO_x_ACTIVIDAD uxa ON a.id=uxa.actividad_id"
				+ " JOIN USUARIO_SELECCION us ON uxa.usuario_seleccion_id=us.id"
				+ " JOIN USUARIO_TIPO ut ON us.usuario_tipo_id=ut.id"
				+ " JOIN USUARIO_x_APLICACION_x_NIVEL uxaxn ON us.usuario_x_aplicacion_x_nivel_id=uxaxn.id"
				+ " JOIN USUARIO u ON uxaxn.usuario_id=u.id"
				+ " WHERE (uxa.asistencia IS NULL OR uxa.asistencia=true) AND (ut.nombre='"
				+ SecurityFilter.escapeString(UsuarioTipo.SUPERVISOR)
				+ "' OR ut.nombre='"
				+ SecurityFilter.escapeString(UsuarioTipo.SUPERVISOR_CON_AUTO)
				+ "')";

		Query q = s.createSQLQuery(query).addEntity(Usuario.class);
		u = (Usuario) q.uniqueResult();
		return u;
	}

	public Usuario findJOByIdAplicacionANDIdNivelANDIdActividad(
			Integer idAplicacion, Integer idNivel, Integer idActividad) {

		Usuario u = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT u.* FROM ACTIVIDAD a"
				+ " JOIN CURSO c ON a.curso_id=c.id AND a.id="
				+ SecurityFilter.escapeString(idActividad)
				+ " JOIN ESTABLECIMIENTO e ON c.establecimiento_id=e.id"
				+ " JOIN CO_x_ESTABLECIMIENTO coxe ON e.id=coxe.establecimiento_id"
				+ " JOIN APLICACION_x_NIVEL axn ON coxe.aplicacion_x_nivel_id=axn.id AND axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ " JOIN JO_x_CO jxc ON coxe.co_id=jxc.co_id AND jxc.activo = true"
				+ " JOIN USUARIO u ON jxc.jo_id=u.id  limit 1";
		Query q = s.createSQLQuery(query).addEntity(Usuario.class);
		u = ((Usuario) q.uniqueResult());
		return u;
	}
}
