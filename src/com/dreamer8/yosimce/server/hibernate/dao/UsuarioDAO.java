package com.dreamer8.yosimce.server.hibernate.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.Usuario;
import com.dreamer8.yosimce.server.utils.SecurityFilter;
import com.dreamer8.yosimce.server.utils.StringUtils;
import com.dreamer8.yosimce.shared.dto.TipoUsuarioDTO;
import com.dreamer8.yosimce.shared.dto.UserDTO;

public class UsuarioDAO extends AbstractHibernateDAO<Usuario, Integer> {
	public Usuario findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdEstablecimientoANDUsuarioTipo(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			Integer idEstablecimiento, String usuarioTipo) {

		Usuario u = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT u.* FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.id_actividad="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.id_nivel="
				+ SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id AND axnxat.actividad_tipo_id="
				+ SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id"
				+ " JOIN CURSO c ON (a.curso_id=c.id AND c.establecimiento_id="
				+ SecurityFilter.escapeString(idEstablecimiento)
				+ ")"
				+ " JOIN USUARIO_x_ACTIVIDAD uxa ON a.id=uxa.actividad_id"
				+ " JOIN USUARIO_SELECCION us ON uxa.usuario_seleccion_id=us.id"
				+ " JOIN USUARIO_TIPO ut ON (us.usuario_tipo_id=ut.id AND ut.nombre='"
				+ SecurityFilter.escapeString(usuarioTipo)
				+ "')"
				+ " JOIN USUARIO_x_APLICACION_x_NIVEL uxaxn ON us.usuario_x_aplicacion_x_nivel_id=uxaxn.id"
				+ " JOIN USUARIO u ON uxaxn.usuario_id=u.id";
		Query q = s.createSQLQuery(query).addEntity(Usuario.class);
		u = ((Usuario) q.uniqueResult());
		return u;
	}

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
		String query = "SELECT u.id,u.id,u.email,u.nombres,u.apellido_paterno,u.apellido_materno,u.username,ut.id,ut.nombre FROM USUARIO_SELECCION us"
				+ " JOIN USUARIO_TIPO ut ON us.usuario_tipo_id=ut.id"
				+ " JOIN USUARIO_x_APLICACION_x_NIVEL uxaxn ON (us.usuario_x_aplicacion_x_nivel_id=uxaxn.id AND us.usuario_tipo_id > "
				+ SecurityFilter.escapeString(idTipoUsuarioSuperior)
				+ ")"
				+ " JOIN APLICACION_x_NIVEL axn ON (uxaxn.aplicacion_x_nivel.id=axn.id AND axn.id_actividad="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.id_nivel="
				+ SecurityFilter.escapeString(idNivel)
				+ ")"
				+ " JOIN USUARIO u ON uxaxn.usuario_id=u.id"
				+ " WHERE (u.id ILIKE '%"
				+ SecurityFilter.escapeLikeString(StringUtils.stripRut(filtro),
						"~")
				+ "%' ESCAPE '~'"
				+ " OR u.username ILIKE '%"
				+ SecurityFilter.escapeLikeString(filtro, "~")
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
}
