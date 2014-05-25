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
	/**
	 * 
	 */
	public CoDAO() {
		// TODO Auto-generated constructor stub
	}

	public CoDAO(Session s) {
		super();
		setSession(s);
	}

	public List<Co> findByIdAplicacion(Integer idAplicacion) {

		List<Co> cos = null;
		Session s = getSession();
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
		Session s = getSession();
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
		Session s = getSession();
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
		Session s = getSession();
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
		Session s = getSession();
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
		Session s = getSession();
		String query =

		// "WITH mh_max AS("
		// + " SELECT mh.material_id,mh.destino_id,m.centro_id,"
		// +
		// "(c.nombre = 'CONT' OR a.actividad_estado_id=7 OR a.actividad_estado_id IS NULL) as contingencia fROM MATERIAL m"
		// +
		// " LEFT JOIN (SELECT mh.material_id,mh.destino_id FROM MATERIAL_HISTORIAL mh "
		// +
		// " JOIN (SELECT mh.material_id, MAX(mh.fecha) fecha FROM MATERIAL_HISTORIAL mh"
		// +
		// " GROUP BY mh.material_id) mh_max ON mh.material_id=mh_max.material_id AND mh.fecha=mh_max.fecha) mh ON m.id=mh.material_id"
		// + " JOIN MATERIAL_x_ACTIVIDAD mxa ON m.id=mxa.material_id"
		// + " JOIN ACTIVIDAD a ON mxa.actividad_id=a.id"
		// + " JOIN CURSO c ON a.curso_id=c.id"
		// +
		// " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id"
		// + " JOIN APLICACION_x_NIVEL axn ON (axn.aplicacion_id="
		// + SecurityFilter.escapeString(idAplicacion)
		// + " AND axn.nivel_id="
		// + SecurityFilter.escapeString(idNivel)
		// +
		// " AND axn.id=axnxat.aplicacion_x_nivel_id AND axnxat.actividad_tipo_id="
		// + SecurityFilter.escapeString(idActividadTipo)
		// + ")"

		"WITH mat AS("
				+ "SELECT m.id,m.centro_id,"
				+ "(c.nombre = 'CONT' OR a.actividad_estado_id=7 OR a.actividad_estado_id IS NULL) as contingencia FROM MATERIAL m"
				+ " JOIN MATERIAL_x_ACTIVIDAD mxa ON m.id=mxa.material_id"
				+ " JOIN ACTIVIDAD a ON mxa.actividad_id=a.id"
				+ " JOIN CURSO c ON a.curso_id=c.id"
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id"
				+ " JOIN APLICACION_x_NIVEL axn ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id AND axnxat.actividad_tipo_id="
				+ SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ "),"
				+ "mh_max AS("
				+ " SELECT mh.material_id,mh.destino_id,mat.centro_id,mat.contingencia FROM mat"
				+ " JOIN  MATERIAL_HISTORIAL mh ON mat.id=mh.material_id"
				+ " JOIN (SELECT mh.material_id, MAX(mh.fecha) fecha FROM MATERIAL_HISTORIAL mh"
				+ " GROUP BY mh.material_id) mh_max ON mh.material_id=mh_max.material_id AND mh.fecha=mh_max.fecha"
				+ ")"

				+ "SELECT DISTINCT co.id,co.comuna_id,p.region_id,co.zona_id,co.nombre,co.direccion_longitud,co.direccion_latitud,"
				+ " u.nombres,u.apellido_paterno,u.apellido_materno,u.celular,total_en_centro,total_establ,total_mat,total_minis,"
				+ " total_cont_centro,total_cont_establ,total_mat_cont,total_cont_minis"
				+ " FROM CO co "
				+ " JOIN CO_x_ESTABLECIMIENTO cxe ON co.id=cxe.co_id"
				+ " JOIN APLICACION_x_NIVEL axn ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=cxe.aplicacion_x_nivel_id)"

				+ " LEFT JOIN (SELECT mat.centro_id,COUNT(mat.id) as total_mat "
				+ " FROM mat WHERE mat.contingencia=false GROUP BY mat.centro_id) tot ON tot.centro_id=co.id"
				+ " LEFT JOIN (SELECT mh_max.centro_id,COUNT(mh_max.material_id) as total_en_centro "
				+ " FROM mh_max WHERE mh_max.destino_id=3 AND mh_max.contingencia=false GROUP BY mh_max.centro_id) en_centro ON en_centro.centro_id=co.id"
				+ " LEFT JOIN (SELECT mh_max.centro_id,COUNT(mh_max.material_id) as total_establ "
				+ " FROM mh_max WHERE mh_max.destino_id=4 AND mh_max.contingencia=false GROUP BY mh_max.centro_id) en_estab ON en_estab.centro_id=co.id"
				+ " LEFT JOIN (SELECT mh_max.centro_id,COUNT(mh_max.material_id) as total_minis "
				+ " FROM mh_max WHERE mh_max.destino_id=1 AND mh_max.contingencia=false GROUP BY mh_max.centro_id) en_minis ON en_minis.centro_id=co.id"
				// +
				// " LEFT JOIN (SELECT mh_max.centro_id,COUNT(mh_max.material_id) as total_imprenta "
				// +
				// " FROM mh_max WHERE (mh_max.destino_id=2 OR mh_max.destino_id IS NULL) AND mh_max.contingencia=false GROUP BY mh_max.centro_id) en_imp ON en_imp.centro_id=co.id"

				+ " LEFT JOIN (SELECT mat.centro_id,COUNT(mat.id) as total_mat_cont "
				+ " FROM mat WHERE mat.contingencia=true GROUP BY mat.centro_id) tot_cont ON tot_cont.centro_id=co.id"
				+ " LEFT JOIN (SELECT mh_max.centro_id,COUNT(mh_max.material_id) as total_cont_centro "
				+ " FROM mh_max WHERE mh_max.destino_id=3 AND mh_max.contingencia=true GROUP BY mh_max.centro_id) cont_en_centro ON cont_en_centro.centro_id=co.id"
				+ " LEFT JOIN (SELECT mh_max.centro_id,COUNT(mh_max.material_id) as total_cont_establ "
				+ " FROM mh_max WHERE mh_max.destino_id=4 AND mh_max.contingencia=true GROUP BY mh_max.centro_id) cont_en_estab ON cont_en_estab.centro_id=co.id"
				+ " LEFT JOIN (SELECT mh_max.centro_id,COUNT(mh_max.material_id) as total_cont_minis "
				+ " FROM mh_max WHERE mh_max.destino_id=1 AND mh_max.contingencia=true GROUP BY mh_max.centro_id) cont_en_minis ON cont_en_minis.centro_id=co.id"
				// +
				// " LEFT JOIN (SELECT mh_max.centro_id,COUNT(mh_max.material_id) as total_cont_imprenta "
				// +
				// " FROM mh_max WHERE (mh_max.destino_id=2 OR mh_max.destino_id IS NULL) AND mh_max.contingencia=true GROUP BY mh_max.centro_id) cont_en_imp ON cont_en_imp.centro_id=co.id"

				+ " JOIN COMUNA com ON co.comuna_id=com.id"
				+ " JOIN PROVINCIA p ON com.provincia_id=p.id"
				+ " LEFT JOIN JO_x_CO joxco ON co.id=joxco.co_id"
				+ " LEFT JOIN USUARIO u ON joxco.jo_id=u.id";

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

//		query += " WHERE  joxco.activo=TRUE";
//		if (usuarioTipo.equals(UsuarioTipo.JEFE_CENTRO_OPERACIONES)) {
//			query += " AND joxco.jo_id="
//					+ SecurityFilter.escapeString(idUsuario);
//		}
		query += " ORDER BY co.nombre ASC";

		Query q = s.createSQLQuery(query);
		List<Object[]> os = q.list();
		CentroOperacionDTO codto = null;
		Integer total = null;
		for (Object[] o : os) {
			codto = new CentroOperacionDTO();
			codto.setId((Integer) o[0]);
			codto.setIdComuna((Integer) o[1]);
			codto.setIdRegion((Integer) o[2]);
			codto.setIdZona((Integer) o[3]);
			codto.setNombre((String) o[4]);
			codto.setLongitud((o[5] != null) ? ((BigDecimal) o[5])
					.doubleValue() : 0);
			codto.setLatitud((o[6] != null) ? ((BigDecimal) o[6]).doubleValue()
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
			codto.setEnMinisterio((o[14] == null) ? 0 : ((BigInteger) o[14])
					.intValue());
			total = (o[13] == null) ? 0 : ((BigInteger) o[13]).intValue();
			total = total - codto.getEnCentro() - codto.getEnEstablecimiento()
					- codto.getEnMinisterio();
			codto.setEnImprenta(total);

			codto.setContingenciaEnCentro((o[15] == null) ? 0
					: ((BigInteger) o[15]).intValue());
			codto.setContingenciaEnEstablecimiento((o[16] == null) ? 0
					: ((BigInteger) o[16]).intValue());
			codto.setContingenciaEnMinisterio((o[18] == null) ? 0
					: ((BigInteger) o[18]).intValue());
			total = (o[17] == null) ? 0 : ((BigInteger) o[17]).intValue();
			total = total - codto.getContingenciaEnCentro()
					- codto.getContingenciaEnEstablecimiento()
					- codto.getContingenciaEnMinisterio();
			codto.setContingenciaEnImprenta(total);
			codots.add(codto);
		}
		return codots;
	}
}
