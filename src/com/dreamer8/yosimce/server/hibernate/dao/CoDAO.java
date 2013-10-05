/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.Co;
import com.dreamer8.yosimce.server.hibernate.pojo.UsuarioTipo;
import com.dreamer8.yosimce.server.utils.SecurityFilter;
import com.dreamer8.yosimce.server.utils.StringUtils;

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
}
