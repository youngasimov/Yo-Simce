package com.dreamer8.yosimce.server.hibernate.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.Usuario;
import com.dreamer8.yosimce.server.utils.SecurityFilter;

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
				+ " AND axn.id=axna.aplicacion_x_nivel_id AND axnxat.actividad_tipo_id="
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
}
