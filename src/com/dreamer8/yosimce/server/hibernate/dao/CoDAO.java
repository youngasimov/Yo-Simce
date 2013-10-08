/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.Co;
import com.dreamer8.yosimce.server.hibernate.pojo.UsuarioTipo;
import com.dreamer8.yosimce.server.utils.SecurityFilter;
import com.dreamer8.yosimce.server.utils.StringUtils;
import com.dreamer8.yosimce.shared.dto.CentroOperacionDTO;

/**
 * @author jorge
 * 
 */
public class CoDAO extends AbstractHibernateDAO<Co, Integer> {

	public List<Co> findByIdAplicacion(Integer idAplicacion) {

		List<Co> cos = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT co.* FROM  CENTRO_REGIONAL cr "
				+ " JOIN ZONA z ON (cr.id=z.centro_regional_id AND cr.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion) + ")"
				+ " JOIN CO co ON z.id=co.zona_id";
		Query q = s.createSQLQuery(query).addEntity(Co.class);
		cos = q.list();
		return cos;
	}

	public List<Co> findByIdAplicacionANDIdUsuarioANDUsuarioTipo(
			Integer idAplicacion, Integer idUsuario, String usuarioTipo) {

		List<Co> cos = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT co.* FROM  CENTRO_REGIONAL cr "
				+ " JOIN ZONA z ON (cr.id=z.centro_regional_id AND cr.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion) + ")"
				+ " JOIN CO co ON z.id=co.zona_id";

		if (usuarioTipo.equals(UsuarioTipo.JEFE_CENTRO_OPERACIONES)
				|| usuarioTipo.equals(UsuarioTipo.LOGISTICA_Y_SOPORTE)) {
			query += " JOIN JO_x_CO joxco ON (co.id=joxco.co_id AND joxco.jo_id="
					+ SecurityFilter.escapeString(idUsuario)
					+ ") AND joxco.activo=TRUE";
		} else if (usuarioTipo.equals(UsuarioTipo.JEFE_ZONAL)) {
			query += " JOIN JZ_x_ZONA jzxz ON (co.zona_id=jzxz.zona_id AND jzxz.jz_id="
					+ SecurityFilter.escapeString(idUsuario)
					+ ") AND jzxz.activo=TRUE";
		} else if (usuarioTipo.equals(UsuarioTipo.JEFE_REGIONAL)) {
			query += " JOIN JR_x_CENTRO_REGIONAL jrxcr ON (z.centro_regional_id=jrxcr.centro_regional_id AND jrxcr.jr_id="
					+ SecurityFilter.escapeString(idUsuario)
					+ ") AND jrxcr.activo=TRUE";
		}

		Query q = s.createSQLQuery(query).addEntity(Co.class);
		cos = q.list();
		return cos;
	}

	public Co findByIdAplicacionANDIdNivelANDIdEstablecimiento(
			Integer idAplicacion, Integer idNivel, Integer idEstablecimiento) {

		Co co = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT co.* FROM APLICACION_x_NIVEL axn "
				+ " JOIN CO_x_ESTABLECIMIENTO cxe ON axn.id=cxe.aplicacion_x_nivel_id AND axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id=" + SecurityFilter.escapeString(idNivel)
				+ " AND cxe.establecimiento_id="
				+ SecurityFilter.escapeString(idEstablecimiento)
				+ " JOIN CO co ON cxe.co_id=co.id";

		Query q = s.createSQLQuery(query).addEntity(Co.class);
		co = ((Co) q.uniqueResult());
		return co;
	}

	public Co findByIdAplicacionANDNombre(Integer idAplicacion, String nombre) {

		Co co = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT co.* FROM  CENTRO_REGIONAL cr "
				+ " JOIN ZONA z ON (cr.id=z.centro_regional_id AND cr.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion) + ")"
				+ " JOIN CO co ON z.id=co.zona_id" + " WHERE co.nombre='"
				+ SecurityFilter.escapeString(nombre) + "'";

		Query q = s.createSQLQuery(query).addEntity(Co.class);
		co = ((Co) q.uniqueResult());
		return co;
	}

	public Co findByIdAplicacionANDIdNivelANDComunaANDRegion(
			Integer idAplicacion, Integer idNivel, String comuna, String region) {

		Co co = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT co.* FROM APLICACION_x_NIVEL axn "
				+ " JOIN CO_x_ESTABLECIMIENTO cxe ON axn.id=cxe.aplicacion_x_nivel_id AND axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id=" + SecurityFilter.escapeString(idNivel)
				+ " JOIN CO co ON cxe.co_id=co.id"
				+ " JOIN COMUNA c ON co.comuna_id=c.id"
				+ " JOIN PROVINCIA p ON c.provincia_id=p.id"
				+ " JOIN REGION r ON p.region_id=r.id"
				+ " WHERE c.nombre ILIKE trim(from '"
				+ SecurityFilter.escapeLikeString(comuna, "~")
				+ "') ESCAPE '~' AND r.nombre ILIKE '%' || trim(from '"
				+ SecurityFilter.escapeLikeString(region, "~")
				+ "') || '%' ESCAPE '~' LIMIT 1";

		Query q = s.createSQLQuery(query).addEntity(Co.class);
		co = ((Co) q.uniqueResult());
		return co;
	}

	public List<CentroOperacionDTO> findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdUsuarioANDUsuarioTipo(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			Integer idUsuario, String usuarioTipo) {

		List<CentroOperacionDTO> codots = new ArrayList<CentroOperacionDTO>();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "WITH mh_max AS("
				+ " SELECT mh.material_id,mh.destino_id,m.centro_id fROM MATERIAL_HISTORIAL mh"
				+ " LEFT JOIN (SELECT mh.material_id, MAX(mh.fecha) fecha FROM MATERIAL_HISTORIAL mh"
				+ " GROUP BY mh.material_id) mh_max ON mh.material_id=mh_max.material_id AND mh.fecha=mh_max.fecha"
				+ " JOIN MATERIAL m ON m.id=mh.material_id"
				+ " JOIN MATERIAL_x_ACTIVIDAD mxa ON m.id=mxa.material_id"
				+ " JOIN ACTIVIDAD a ON mxa.actividad_id=a.id"
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id"
				+ " JOIN APLICACION_x_NIVEL axn ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id AND axnxat.actividad_tipo_id="
				+ SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				
				+ ")"
				+ "SELECT DISTINCT co.id,co.comuna_id,p.region_id,co.zona_id,co.nombre,co.direccion_longitud,co.direccion_latitud,"
				+ " u.nombres,u.apellido_paterno,u.apellido_materno,u.celular,total_en_centro,total_establ,total_imprenta,total_minis"
				+ " FROM CO co "
				+ " LEFT JOIN (SELECT mh_max.centro_id,COUNT(mh_max.material_id) as total_en_centro "
				+ " FROM mh_max WHERE mh_max.destino_id=4 GROUP BY mh_max.centro_id) en_centro ON en_centro.centro_id=co.id"
				+ " LEFT JOIN (SELECT mh_max.centro_id,COUNT(mh_max.material_id) as total_establ "
				+ " FROM mh_max WHERE mh_max.destino_id=5 GROUP BY mh_max.centro_id) en_estab ON en_estab.centro_id=co.id"
				+ " LEFT JOIN (SELECT mh_max.centro_id,COUNT(mh_max.material_id) as total_minis "
				+ " FROM mh_max WHERE mh_max.destino_id=1 GROUP BY mh_max.centro_id) en_minis ON en_minis.centro_id=co.id"
				+ " LEFT JOIN (SELECT mh_max.centro_id,COUNT(mh_max.material_id) as total_imprenta "
				+ " FROM mh_max WHERE mh_max.destino_id=2 OR mh_max.destino_id IS NULL GROUP BY mh_max.centro_id) en_imp ON en_imp.centro_id=co.id"
				+ " JOIN COMUNA com ON co.comuna_id=com.id"
				+ " JOIN PROVINCIA p ON com.provincia_id=p.id"
				+ " JOIN JO_x_CO joxco ON co.id=joxco.co_id"
				+ " JOIN USUARIO u ON joxco.jo_id=u.id";

		if (usuarioTipo.equals(UsuarioTipo.JEFE_REGIONAL)
				|| usuarioTipo.equals(UsuarioTipo.JEFE_ZONAL)) {

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

		query += " WHERE  joxco.activo=TRUE" + " ORDER BY co.nombre ASC";
		Query q = s.createSQLQuery(query);
		List<Object[]> os = q.list();
		CentroOperacionDTO codto = null;
		for (Object[] o : os) {
			codto = new CentroOperacionDTO();
			codto.setId((Integer) o[0]);
			codto.setIdComuna((Integer) o[1]);
			codto.setIdRegion((Integer) o[2]);
			codto.setIdZona((Integer) o[3]);
			codto.setNombre((String) o[4]);
			codto.setLongitud((o[5] != null) ? ((BigDecimal) o[5]).longValue()
					: 0);
			codto.setLatitud((o[6] != null) ? ((BigDecimal) o[6]).longValue()
					: 0);
			if (o[7] != null) {
				codto.setNombreJefeCentro(StringUtils
						.nombreInicialSegundo((String) o[7])
						+ " "
						+ (String) o[8] + " " + (String) o[9]);
			}
			codto.setTelefonoJefeCentro((String) o[10]);
			codto.setEnCentro((o[11] == null) ? 0 : ((BigInteger) o[11])
					.intValue());
			codto.setEnEstablecimiento((o[12] == null) ? 0
					: ((BigInteger) o[12]).intValue());
			codto.setEnImprenta((o[13] == null) ? 0 : ((BigInteger) o[13])
					.intValue());
			codto.setEnMinisterio((o[14] == null) ? 0 : ((BigInteger) o[14])
					.intValue());
			codots.add(codto);
		}
		return codots;
	}
}
