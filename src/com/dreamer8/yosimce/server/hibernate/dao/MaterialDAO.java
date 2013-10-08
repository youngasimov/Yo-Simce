/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.Lugar;
import com.dreamer8.yosimce.server.hibernate.pojo.Material;
import com.dreamer8.yosimce.server.hibernate.pojo.MaterialEstado;
import com.dreamer8.yosimce.server.hibernate.pojo.UsuarioTipo;
import com.dreamer8.yosimce.server.utils.SecurityFilter;
import com.dreamer8.yosimce.shared.dto.LoteDTO;
import com.dreamer8.yosimce.shared.dto.MaterialDTO;

/**
 * @author jorge
 * 
 */
public class MaterialDAO extends AbstractHibernateDAO<Material, Integer> {

	public List<Material> findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCo(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			Integer idCo) {

		List<Material> ms = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT DISTINCT m.* FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id AND axnxat.actividad_tipo_id="
				+ SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id AND a.actividad_estado_id!=7"
				+ " JOIN MATERIAL_x_ACTIVIDAD mxa ON a.id=mxa.actividad_id"
				+ " JOIN MATERIAL m ON mxa.material_id=m.id AND m.centro_id="
				+ SecurityFilter.escapeString(idCo);
		Query q = s.createSQLQuery(query).addEntity(Material.class);
		ms = q.list();
		return ms;
	}

	public List<Material> findByIdAplicacionANDIdNivelANDIdActividadTipoANDCodigos(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			List<String> codigos) {

		List<Material> ms = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT DISTINCT m.* FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				// + " AND axn.nivel_id="
				// + SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id "
				// + "AND axnxat.actividad_tipo_id="
				// + SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id AND a.actividad_estado_id!=7"
				+ " JOIN CURSO c ON a.curso_id=c.id"
				+ " JOIN ESTABLECIMIENTO e ON c.establecimiento_id=e.id"
				+ " JOIN MATERIAL_x_ACTIVIDAD mxa ON a.id=mxa.actividad_id"
				+ " JOIN MATERIAL m ON mxa.material_id=m.id"
				// + " JOIN CO ON m.centro_id=co.id"
				+ " JOIN MATERIAL_TIPO mt ON m.material_tipo_id=mt.id"
				+ " LEFT JOIN MATERIAL_x_LOTE mxl ON m.id=mxl.material_id"
				+ " LEFT JOIN lote l ON mxl.lote_id=l.id"
				+ " LEFT JOIN (SELECT material_id, MAX(fecha) as fecha FROM MATERIAL_HISTORIAL GROUP BY material_id) mh_max ON m.id=mh_max.material_id"
				+ " LEFT JOIN MATERIAL_HISTORIAL mh ON mh_max.material_id=mh.material_id AND mh_max.fecha=mh.fecha"
				+ " LEFT JOIN LUGAR l_dest ON mh.destino_id=l_dest.id";

		String where = "";
		if (codigos != null && !codigos.isEmpty()) {
			for (String codigo : codigos) {
				if (!where.equals("")) {
					where += " OR ";
				}
				if (codigo != null && !codigo.isEmpty()) {
					where += "m.codigo='" + SecurityFilter.escapeString(codigo)
							+ "'";
				}
			}
			if (!where.equals("")) {
				query += " WHERE " + where;
			}
		}
		Query q = s.createSQLQuery(query).addEntity(Material.class);
		ms = q.list();
		return ms;
	}

	public Material findByIdAplicacionANDIdNivelANDIdActividadTipoANDCodigo(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			String codigo) {

		Material m = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT DISTINCT m.* FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				// + " AND axn.nivel_id="
				// + SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id "
				// + "AND axnxat.actividad_tipo_id="
				// + SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id AND a.actividad_estado_id!=7"
				+ " JOIN CURSO c ON a.curso_id=c.id"
				+ " JOIN ESTABLECIMIENTO e ON c.establecimiento_id=e.id"
				+ " JOIN MATERIAL_x_ACTIVIDAD mxa ON a.id=mxa.actividad_id"
				+ " JOIN MATERIAL m ON mxa.material_id=m.id"
				+ " WHERE m.codigo='" + SecurityFilter.escapeString(codigo)
				+ "'";
		Query q = s.createSQLQuery(query).addEntity(Material.class);
		m = ((Material) q.uniqueResult());
		return m;
	}

	public List<MaterialDTO> findDTOSByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCo(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			Integer idCo) {

		List<MaterialDTO> mdtos = new ArrayList<MaterialDTO>();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT DISTINCT m.id as m_id,m.codigo as m_cod,mt.nombre as m_tipo,"
				+ "m.centro_id as co_id,e.id as rbd,e.nombre as e_nom, c.nombre as c_nom, n.nombre as n_nom,"
				+ "l_dest.nombre as dest_nom, l.id as l_id, l.nombre as l_nom,n.id as n_id, axnxat.actividad_tipo_id,me.nombre"
				+ "  FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				// + " AND axn.nivel_id="
				// + SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id "
				// + "AND axnxat.actividad_tipo_id="
				// + SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN NIVEL n ON axn.nivel_id=n.id"
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id AND a.actividad_estado_id!=7"
				+ " JOIN CURSO c ON a.curso_id=c.id"
				+ " JOIN ESTABLECIMIENTO e ON c.establecimiento_id=e.id"
				+ " JOIN MATERIAL_x_ACTIVIDAD mxa ON a.id=mxa.actividad_id"
				+ " JOIN MATERIAL m ON mxa.material_id=m.id"
				// + " AND m.centro_id="
				// + SecurityFilter.escapeString(idCo)
				// + " JOIN CO ON m.centro_id=co.id"
				+ " JOIN MATERIAL_TIPO mt ON m.material_tipo_id=mt.id"
				+ " LEFT JOIN MATERIAL_x_LOTE mxl ON m.id=mxl.material_id"
				+ " LEFT JOIN lote l ON mxl.lote_id=l.id"
				+ " LEFT JOIN (SELECT material_id, MAX(fecha) as fecha FROM MATERIAL_HISTORIAL GROUP BY material_id) mh_max ON m.id=mh_max.material_id"
				+ " LEFT JOIN MATERIAL_HISTORIAL mh ON mh_max.material_id=mh.material_id AND mh_max.fecha=mh.fecha"
				+ " LEFT JOIN LUGAR l_dest ON mh.destino_id=l_dest.id"
				+ " LEFT JOIN MATERIAL_ESTADO me ON mh.material_estado_id=me.id"
				+ " WHERE (m.centro_id="
				+ SecurityFilter.escapeString(idCo)
				+ " AND (mh.centro_id IS NULL OR  mh.centro_id="
				+ SecurityFilter.escapeString(idCo)
				+ ")) OR (m.centro_id !="
				+ SecurityFilter.escapeString(idCo)
				+ " AND mh.centro_id="
				+ SecurityFilter.escapeString(idCo) + ")";
		Query q = s.createSQLQuery(query);
		List<Object[]> os = q.list();
		MaterialDTO mdto = null;
		LoteDTO ldto = null;
		for (Object[] o : os) {
			mdto = new MaterialDTO();
			mdto.setId((Integer) o[0]);
			mdto.setCodigo((String) o[1]);
			mdto.setTipo((String) o[2]);
			mdto.setIdCentro((Integer) o[3]);
			mdto.setRbd(Integer.toString((Integer) o[4]));
			mdto.setEstablecimiento((String) o[5]);
			mdto.setCurso((String) o[6]);
			mdto.setNivel((String) o[7]);
			mdto.setEtapa((o[8] == null) ? Lugar.IMPRENTA : (String) o[8]);
			if (o[9] != null) {
				ldto = new LoteDTO();
				ldto.setId((Integer) o[9]);
				ldto.setNombre((String) o[10]);
				mdto.setLote(ldto);
			}
			mdto.setIdNivel((Integer) o[11]);
			mdto.setIdTipoActividad((Integer) o[12]);
			mdto.setTransicion((o[13] == null || MaterialEstado.EN_EL_LUGAR
					.equals(o[13])) ? "En " : "Hacia ");
			mdtos.add(mdto);
		}
		return mdtos;
	}

	public List<MaterialDTO> findDTOSByIdAplicacionANDIdNivelANDIdActividadTipoANDCodigos(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			List<String> codigos) {

		List<MaterialDTO> mdtos = new ArrayList<MaterialDTO>();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT DISTINCT m.id as m_id,m.codigo as m_cod,mt.nombre as m_tipo,"
				+ "m.centro_id as co_id,e.id as rbd,e.nombre as e_nom, c.nombre as c_nom, n.nombre as n_nom,"
				+ "l_dest.nombre as dest_nom, l.id as l_id, l.nombre as l_nom,n.id as n_id, axnxat.actividad_tipo_id,me.nombre"
				+ "  FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				// + " AND axn.nivel_id="
				// + SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id "
				// + "AND axnxat.actividad_tipo_id="
				// + SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN NIVEL n ON axn.nivel_id=n.id"
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id AND a.actividad_estado_id!=7"
				+ " JOIN CURSO c ON a.curso_id=c.id"
				+ " JOIN ESTABLECIMIENTO e ON c.establecimiento_id=e.id"
				+ " JOIN MATERIAL_x_ACTIVIDAD mxa ON a.id=mxa.actividad_id"
				+ " JOIN MATERIAL m ON mxa.material_id=m.id"
				// + " JOIN CO ON m.centro_id=co.id"
				+ " JOIN MATERIAL_TIPO mt ON m.material_tipo_id=mt.id"
				+ " LEFT JOIN MATERIAL_x_LOTE mxl ON m.id=mxl.material_id"
				+ " LEFT JOIN lote l ON mxl.lote_id=l.id"
				+ " LEFT JOIN (SELECT material_id, MAX(fecha) as fecha FROM MATERIAL_HISTORIAL GROUP BY material_id) mh_max ON m.id=mh_max.material_id"
				+ " LEFT JOIN MATERIAL_HISTORIAL mh ON mh_max.material_id=mh.material_id AND mh_max.fecha=mh.fecha"
				+ " LEFT JOIN LUGAR l_dest ON mh.destino_id=l_dest.id"
				+ " LEFT JOIN MATERIAL_ESTADO me ON mh.material_estado_id=me.id";

		String where = "";
		if (codigos != null && !codigos.isEmpty()) {
			for (String codigo : codigos) {
				if (!where.equals("")) {
					where += " OR ";
				}
				if (codigo != null && !codigo.isEmpty()) {
					where += "m.codigo='" + SecurityFilter.escapeString(codigo)
							+ "'";
				}
			}
			if (!where.equals("")) {
				query += " WHERE " + where;
			}
		}
		Query q = s.createSQLQuery(query);
		List<Object[]> os = q.list();
		MaterialDTO mdto = null;
		LoteDTO ldto = null;
		for (Object[] o : os) {
			mdto = new MaterialDTO();
			mdto.setId((Integer) o[0]);
			mdto.setCodigo((String) o[1]);
			mdto.setTipo((String) o[2]);
			mdto.setIdCentro((Integer) o[3]);
			mdto.setRbd(Integer.toString((Integer) o[4]));
			mdto.setEstablecimiento((String) o[5]);
			mdto.setCurso((String) o[6]);
			mdto.setNivel((String) o[7]);
			mdto.setEtapa((o[8] == null) ? Lugar.IMPRENTA : (String) o[8]);
			if (o[9] != null) {
				ldto = new LoteDTO();
				ldto.setId((Integer) o[9]);
				ldto.setNombre((String) o[10]);
				mdto.setLote(ldto);
			}
			mdto.setIdNivel((Integer) o[11]);
			mdto.setIdTipoActividad((Integer) o[12]);
			mdto.setTransicion((o[13] == null || MaterialEstado.EN_EL_LUGAR
					.equals(o[13])) ? "En " : "Hacia ");
			mdtos.add(mdto);
		}
		return mdtos;
	}

	public List<MaterialDTO> findDTOSByIdAplicacionANDIdNivelANDIdActividadTipoANDIdMateriales(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			List<Integer> idMateriales) {

		List<MaterialDTO> mdtos = new ArrayList<MaterialDTO>();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT DISTINCT m.id as m_id,m.codigo as m_cod,mt.nombre as m_tipo,"
				+ "m.centro_id as co_id,e.id as rbd,e.nombre as e_nom, c.nombre as c_nom, n.nombre as n_nom,"
				+ "l_dest.nombre as dest_nom, l.id as l_id, l.nombre as l_nom,n.id as n_id, axnxat.actividad_tipo_id,me.nombre"
				+ "  FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				// + " AND axn.nivel_id="
				// + SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id "
				// + "AND axnxat.actividad_tipo_id="
				// + SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN NIVEL n ON axn.nivel_id=n.id"
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id AND a.actividad_estado_id!=7"
				+ " JOIN CURSO c ON a.curso_id=c.id"
				+ " JOIN ESTABLECIMIENTO e ON c.establecimiento_id=e.id"
				+ " JOIN MATERIAL_x_ACTIVIDAD mxa ON a.id=mxa.actividad_id"
				+ " JOIN MATERIAL m ON mxa.material_id=m.id"
				// + " JOIN CO ON m.centro_id=co.id"
				+ " JOIN MATERIAL_TIPO mt ON m.material_tipo_id=mt.id"
				+ " LEFT JOIN MATERIAL_x_LOTE mxl ON m.id=mxl.material_id"
				+ " LEFT JOIN lote l ON mxl.lote_id=l.id"
				+ " LEFT JOIN (SELECT material_id, MAX(fecha) as fecha FROM MATERIAL_HISTORIAL GROUP BY material_id) mh_max ON m.id=mh_max.material_id"
				+ " LEFT JOIN MATERIAL_HISTORIAL mh ON mh_max.material_id=mh.material_id AND mh_max.fecha=mh.fecha"
				+ " LEFT JOIN LUGAR l_dest ON mh.destino_id=l_dest.id"
				+ " LEFT JOIN MATERIAL_ESTADO me ON mh.material_estado_id=me.id";

		String where = "";
		if (idMateriales != null && !idMateriales.isEmpty()) {
			for (Integer id : idMateriales) {
				if (!where.equals("")) {
					where += " OR ";
				}
				if (id != null) {
					where += "m.id='" + SecurityFilter.escapeString(id) + "'";
				}
			}
			if (!where.equals("")) {
				query += " WHERE " + where;
			}
		}
		Query q = s.createSQLQuery(query);
		List<Object[]> os = q.list();
		MaterialDTO mdto = null;
		LoteDTO ldto = null;
		for (Object[] o : os) {
			mdto = new MaterialDTO();
			mdto.setId((Integer) o[0]);
			mdto.setCodigo((String) o[1]);
			mdto.setTipo((String) o[2]);
			mdto.setIdCentro((Integer) o[3]);
			mdto.setRbd(Integer.toString((Integer) o[4]));
			mdto.setEstablecimiento((String) o[5]);
			mdto.setCurso((String) o[6]);
			mdto.setNivel((String) o[7]);
			mdto.setEtapa((o[8] == null) ? Lugar.IMPRENTA : (String) o[8]);
			if (o[9] != null) {
				ldto = new LoteDTO();
				ldto.setId((Integer) o[9]);
				ldto.setNombre((String) o[10]);
				mdto.setLote(ldto);
			}
			mdto.setIdNivel((Integer) o[11]);
			mdto.setIdTipoActividad((Integer) o[12]);
			mdto.setTransicion((o[13] == null || MaterialEstado.EN_EL_LUGAR
					.equals(o[13])) ? "En " : "Hacia ");
			mdtos.add(mdto);
		}
		return mdtos;
	}

	public MaterialDTO findDTOByIdAplicacionANDIdNivelANDIdActividadTipoANDCodigo(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			String codigo) {
		MaterialDTO mdto = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT DISTINCT m.id as m_id,m.codigo as m_cod,mt.nombre as m_tipo,"
				+ "m.centro_id as co_id,e.id as rbd,e.nombre as e_nom, c.nombre as c_nom, n.nombre as n_nom,"
				+ "l_dest.nombre as dest_nom, l.id as l_id, l.nombre as l_nom,n.id as n_id, axnxat.actividad_tipo_id,me.nombre"
				+ "  FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				// + " AND axn.nivel_id="
				// + SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id "
				// + "AND axnxat.actividad_tipo_id="
				// + SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN NIVEL n ON axn.nivel_id=n.id"
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id AND a.actividad_estado_id!=7"
				+ " JOIN CURSO c ON a.curso_id=c.id"
				+ " JOIN ESTABLECIMIENTO e ON c.establecimiento_id=e.id"
				+ " JOIN MATERIAL_x_ACTIVIDAD mxa ON a.id=mxa.actividad_id"
				+ " JOIN MATERIAL m ON mxa.material_id=m.id AND m.codigo='"
				+ SecurityFilter.escapeString(codigo)
				+ "'"
				// + " JOIN CO ON m.centro_id=co.id"
				+ " JOIN MATERIAL_TIPO mt ON m.material_tipo_id=mt.id"
				+ " LEFT JOIN MATERIAL_x_LOTE mxl ON m.id=mxl.material_id"
				+ " LEFT JOIN lote l ON mxl.lote_id=l.id"
				+ " LEFT JOIN (SELECT material_id, MAX(fecha) as fecha FROM MATERIAL_HISTORIAL GROUP BY material_id) mh_max ON m.id=mh_max.material_id"
				+ " LEFT JOIN MATERIAL_HISTORIAL mh ON mh_max.material_id=mh.material_id AND mh_max.fecha=mh.fecha"
				+ " LEFT JOIN LUGAR l_dest ON mh.destino_id=l_dest.id"
				+ " LEFT JOIN MATERIAL_ESTADO me ON mh.material_estado_id=me.id";
		Query q = s.createSQLQuery(query);
		List<Object[]> os = q.list();

		LoteDTO ldto = null;
		for (Object[] o : os) {
			mdto = new MaterialDTO();
			mdto.setId((Integer) o[0]);
			mdto.setCodigo((String) o[1]);
			mdto.setTipo((String) o[2]);
			mdto.setIdCentro((Integer) o[3]);
			mdto.setRbd(Integer.toString((Integer) o[4]));
			mdto.setEstablecimiento((String) o[5]);
			mdto.setCurso((String) o[6]);
			mdto.setNivel((String) o[7]);
			mdto.setEtapa((o[8] == null) ? Lugar.IMPRENTA : (String) o[8]);
			if (o[9] != null) {
				ldto = new LoteDTO();
				ldto.setId((Integer) o[9]);
				ldto.setNombre((String) o[10]);
				mdto.setLote(ldto);
			}
			mdto.setIdNivel((Integer) o[11]);
			mdto.setIdTipoActividad((Integer) o[12]);
			mdto.setTransicion((o[13] == null || MaterialEstado.EN_EL_LUGAR
					.equals(o[13])) ? "En " : "Hacia ");
		}
		return mdto;
	}

	public Map<String, List<Integer>> findRepetidos() {

		Map<String, List<Integer>> ms = new HashMap<String, List<Integer>>();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "select m.codigo,m.id as m_id,m1.id as m1_id from material m join material m1 on m.codigo=m1.codigo where m.id != m1.id;";
		Query q = s.createSQLQuery(query);
		List<Integer> ids = null;
		List<Object[]> os = q.list();
		for (Object[] o : os) {
			if (!ms.containsKey((String) o[0])) {
				ids = new ArrayList<Integer>();
				ids.add((Integer) o[1]);
				ids.add((Integer) o[2]);
				ms.put((String) o[0], ids);
			}
		}
		return ms;
	}

	public Map<Integer, Integer> findEnCentroEquivocado() {

		Map<Integer, Integer> ms = new HashMap<Integer, Integer>();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "select m.id,cxe.co_id from material m"
				+ " join material_x_actividad mxa on m.id=mxa.material_id"
				+ " join actividad a on mxa.actividad_id=a.id AND a.actividad_estado_id!=7"
				+ " join curso c on a.curso_id=c.id"
				+ " join establecimiento e on c.establecimiento_id=e.id"
				+ " join co_x_establecimiento cxe on e.id=cxe.establecimiento_id and c.aplicacion_x_nivel_id=cxe.aplicacion_x_nivel_id"
				+ " where m.centro_id != cxe.co_id"
				+ " order by cxe.co_id asc ";
		Query q = s.createSQLQuery(query);
		List<Object[]> os = q.list();
		for (Object[] o : os) {
			if (!ms.containsKey((Integer) o[0])) {
				ms.put((Integer) o[0], (Integer) o[1]);
			}
		}
		return ms;
	}

	public List<Material> findByDia(Integer dia) {
		List<Material> ms = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT m.* FROM MATERIAL m "
				+ " WHERE substring(m.codigo from 4 for 1)='"
				+ SecurityFilter.escapeString(dia) + "';";
		Query q = s.createSQLQuery(query).addEntity(Material.class);
		ms = q.list();
		return ms;
	}
}
