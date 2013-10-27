/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.SuplenteXCo;
import com.dreamer8.yosimce.server.hibernate.pojo.UsuarioTipo;
import com.dreamer8.yosimce.server.utils.SecurityFilter;
import com.dreamer8.yosimce.shared.dto.EvaluacionSuplenteDTO;
import com.dreamer8.yosimce.shared.dto.UserDTO;

/**
 * @author jorge
 * 
 */
public class SuplenteXCoDAO extends AbstractHibernateDAO<SuplenteXCo, Integer> {
	/**
	 * 
	 */
	public SuplenteXCoDAO() {
		// TODO Auto-generated constructor stub
	}

	public SuplenteXCoDAO(Session s) {
		super();
		setSession(s);
	}

	public SuplenteXCo findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdUsuario(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			Integer idUsuario) {

		SuplenteXCo sco = null;
		Session s = getSession();
		String query = "SELECT sco.* FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id AND axnxat.actividad_tipo_id="
				+ SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN SUPLENTE_x_CO sco ON axnxat.id=aplicacion_x_nivel_x_actividad_tipo_id"
				+ " JOIN USUARIO_SELECCION us ON (sco.usuario_seleccion_id=us.id AND us.seleccion=true AND us.renuncia=false)"
				+ " JOIN USUARIO_x_APLICACION_x_NIVEL uxaxn ON (axn.id=uxaxn.aplicacion_x_nivel_id AND us.usuario_x_aplicacion_x_nivel_id=uxaxn.id AND uxaxn.usuario_id="
				+ SecurityFilter.escapeString(idUsuario) + ")";
		Query q = s.createSQLQuery(query).addEntity(SuplenteXCo.class);
		sco = ((SuplenteXCo) q.uniqueResult());
		return sco;
	}

	public List<EvaluacionSuplenteDTO> findEvaluacionesByIdAplicacionANDIdNivelANDIdActividadTipoANDIdUsuarioANDUsuarioTipo(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			Integer idUsuario, String usuarioTipo) {

		List<EvaluacionSuplenteDTO> esdtos = new ArrayList<EvaluacionSuplenteDTO>();
		Session s = getSession();
		String query = "SELECT u.id,u.email,u.nombres,u.apellido_paterno,u.apellido_materno,u.username,co.nombre as centro,sco.evaluacion,sco.asistencia FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id AND axnxat.actividad_tipo_id="
				+ SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN SUPLENTE_x_CO sco ON axnxat.id=aplicacion_x_nivel_x_actividad_tipo_id"
				+ " JOIN USUARIO_SELECCION us ON (sco.usuario_seleccion_id=us.id AND us.seleccion=true AND us.renuncia=false)"
				+ " JOIN USUARIO_x_APLICACION_x_NIVEL uxaxn ON us.usuario_x_aplicacion_x_nivel_id=uxaxn.id"
				+ " JOIN USUARIO u ON uxaxn.usuario_id=u.id"
				+ " JOIN CO co ON sco.co_id=co.id";
		if (usuarioTipo.equals(UsuarioTipo.JEFE_REGIONAL)
				|| usuarioTipo.equals(UsuarioTipo.JEFE_ZONAL)
				|| usuarioTipo.equals(UsuarioTipo.JEFE_CENTRO_OPERACIONES)
				|| usuarioTipo.equals(UsuarioTipo.LOGISTICA_Y_SOPORTE)) {
			if (usuarioTipo.equals(UsuarioTipo.JEFE_CENTRO_OPERACIONES)
					|| usuarioTipo.equals(UsuarioTipo.LOGISTICA_Y_SOPORTE)) {
				query += " JOIN JO_x_CO joxco ON (sco.co_id=joxco.co_id AND joxco.jo_id="
						+ SecurityFilter.escapeString(idUsuario)
						+ ") AND joxco.activo=TRUE";
			} else {

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
		}
		query += " ORDER BY u.id";
		Query q = s.createSQLQuery(query);
		List<Object[]> os = q.list();
		UserDTO udto = null;
		EvaluacionSuplenteDTO esdto = null;
		for (Object[] o : os) {
			esdto = new EvaluacionSuplenteDTO();
			udto = new UserDTO();
			udto.setId((Integer) o[0]);
			udto.setEmail((String) o[1]);
			udto.setNombres((String) o[2]);
			udto.setApellidoPaterno((String) o[3]);
			udto.setApellidoMaterno((String) o[4]);
			udto.setUsername((String) o[5]);
			udto.setRut((String) o[5]);
			esdto.setSuplente(udto);
			esdto.setCo((String) o[6]);
			esdto.setGeneral((Integer) o[7]);
			esdto.setPresente((Boolean) o[8]);
			esdtos.add(esdto);
		}
		return esdtos;
	}
}
