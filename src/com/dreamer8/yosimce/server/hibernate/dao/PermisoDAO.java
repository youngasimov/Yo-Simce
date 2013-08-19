/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.Permiso;
import com.dreamer8.yosimce.server.utils.SecurityFilter;
import com.dreamer8.yosimce.shared.dto.PermisoDTO;

/**
 * @author jorge
 * 
 */
public class PermisoDAO extends AbstractHibernateDAO<Permiso, Integer> {

	/**
	 * @param id
	 * @return
	 */
	public List<Permiso> findByIdAplicacionANDIdNivelANDIdUsuario(
			Integer idAplicacion, Integer idNivel, Integer idUsuario) {

		List<Permiso> ps = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT p.* FROM USUARIO_x_APLICACION_x_NIVEL uxaxn"
				+ " JOIN APLICACION_x_NIVEL axn ON (uxaxn.aplicacion_x_nivel_id=axn.id AND axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ ")"
				+ " JOIN USUARIO_SELECCION us ON us.usuario_x_aplicacion_x_nivel_id=uxaxn.id"
				+ " JOIN APLICACION_x_USUARIO_TIPO axut ON (us.usuario_tipo_id=axut.usuario_tipo_id AND axut.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ ")"
				+ " JOIN APLICACION_x_USUARIO_TIPO_x_PERMISO axutxp ON (axutxp.aplicacion_x_usuario_tipo_id=axut.id AND axutxp.acceso=TRUE)"
				+ " JOIN PERMISO p ON axutxp.permiso_id=p.id"
				+ " WHERE uxaxn.usuario_id="
				+ SecurityFilter.escapeString(idUsuario);
		Query q = s.createSQLQuery(query).addEntity(Permiso.class);
		ps = q.list();
		return ps;
	}

	public List<PermisoDTO> findByIdAplicacion(Integer idAplicacion) {

		List<PermisoDTO> pdtos = new ArrayList<PermisoDTO>();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT p.id,p.clase,p.metodo,axut.usuario_tipo_id FROM APLICACION_x_USUARIO_TIPO axut"
				+ " JOIN APLICACION_x_USUARIO_TIPO_x_PERMISO axutxp ON (axutxp.aplicacion_x_usuario_tipo_id=axut.id AND axut.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axutxp.acceso=TRUE)"
				+ " JOIN PERMISO p ON axutxp.permiso_id=p.id"
				+ " ORDER BY p.id,axut.usuario_tipo_id";
		Query q = s.createSQLQuery(query);
		List<Object[]> os = q.list();
		PermisoDTO pdto = null;
		Integer prevId = -1;
		Integer currId = null;
		ArrayList<Integer> tipos = null;
		for (Object[] o : os) {
			currId = (Integer) o[0];
			if (currId != prevId) {
				prevId = currId;
				pdto = new PermisoDTO();
				pdto.setIdPermiso(currId);
				pdto.setClase((String) o[1]);
				pdto.setMetodo((String) o[2]);
				tipos = new ArrayList<Integer>();
				pdto.setIdTiposUsuariosPermitidos(tipos);
				pdtos.add(pdto);
			}
			tipos.add((Integer) o[3]);
		}
		return pdtos;
	}
}
