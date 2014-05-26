/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import java.math.BigInteger;
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
import com.dreamer8.yosimce.shared.dto.EstadoMaterialItemDTO;
import com.dreamer8.yosimce.shared.dto.LoteDTO;
import com.dreamer8.yosimce.shared.dto.MaterialDTO;

/**
 * @author jorge
 * 
 */
public class MaterialDAO extends AbstractHibernateDAO<Material, Integer> {
	/**
	 * 
	 */
	public MaterialDAO() {
		// TODO Auto-generated constructor stub
	}

	public MaterialDAO(Session s) {
		super();
		setSession(s);
	}

	public List<Material> findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCo(Integer idAplicacion, Integer idNivel, Integer idActividadTipo, Integer idCo) {

		List<Material> ms = null;
		Session s = getSession();
		String query = "SELECT DISTINCT m.* FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id AND axnxat.actividad_tipo_id="
				+ SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id "// AND
																							// (a.actividad_estado_id!=7
																							// OR
																							// a.actividad_estado_id
																							// IS
																							// NULL)"
				+ " JOIN MATERIAL_x_ACTIVIDAD mxa ON a.id=mxa.actividad_id" + " JOIN MATERIAL m ON mxa.material_id=m.id AND m.centro_id="
				+ SecurityFilter.escapeString(idCo);
		Query q = s.createSQLQuery(query).addEntity(Material.class);
		ms = q.list();
		return ms;
	}

	public List<Material> findByIdAplicacionANDIdNivelANDIdActividadTipoANDCodigos(Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			List<String> codigos) {

		List<Material> ms = null;
		Session s = getSession();
		String query = "SELECT DISTINCT m.* FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				// + " AND axn.nivel_id="
				// + SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id "
				// + "AND axnxat.actividad_tipo_id="
				// + SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id "// AND
																							// (a.actividad_estado_id!=7
																							// OR
																							// a.actividad_estado_id
																							// IS
																							// NULL)"
				+ " JOIN CURSO c ON a.curso_id=c.id"
				+ " JOIN ESTABLECIMIENTO e ON c.establecimiento_id=e.id"
				+ " JOIN MATERIAL_x_ACTIVIDAD mxa ON a.id=mxa.actividad_id"
				+ " JOIN MATERIAL m ON mxa.material_id=m.id"
				// + " JOIN CO ON m.centro_id=co.id"
				+ " JOIN MATERIAL_TIPO mt ON m.material_tipo_id=mt.id" + " LEFT JOIN MATERIAL_x_LOTE mxl ON m.id=mxl.material_id"
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
					where += "m.codigo='" + SecurityFilter.escapeString(codigo) + "'";
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

	public Material findByIdAplicacionANDIdNivelANDIdActividadTipoANDCodigo(Integer idAplicacion, Integer idNivel, Integer idActividadTipo, String codigo) {

		Material m = null;
		Session s = getSession();
		String query = "SELECT DISTINCT m.* FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				// + " AND axn.nivel_id="
				// + SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id "
				// + "AND axnxat.actividad_tipo_id="
				// + SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id "// AND
																							// (a.actividad_estado_id!=7
																							// OR
																							// a.actividad_estado_id
																							// IS
																							// NULL)"
				+ " JOIN CURSO c ON a.curso_id=c.id" + " JOIN ESTABLECIMIENTO e ON c.establecimiento_id=e.id"
				+ " JOIN MATERIAL_x_ACTIVIDAD mxa ON a.id=mxa.actividad_id" + " JOIN MATERIAL m ON mxa.material_id=m.id" + " WHERE m.codigo='"
				+ SecurityFilter.escapeString(codigo) + "'";
		Query q = s.createSQLQuery(query).addEntity(Material.class);
		m = ((Material) q.uniqueResult());
		return m;
	}

	public List<MaterialDTO> findDTOSByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCo(Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			Integer idCo) {

		List<MaterialDTO> mdtos = new ArrayList<MaterialDTO>();
		Session s = getSession();
		String query = "SELECT DISTINCT m.id as m_id,m.codigo as m_cod,mt.nombre as m_tipo,"
				+ "m.centro_id as co_id,e.id as rbd,e.nombre as e_nom, c.nombre as c_nom, n.nombre as n_nom,"
				+ "l_dest.nombre as dest_nom, l.id as l_id, l.nombre as l_nom,n.id as n_id, axnxat.actividad_tipo_id,me.nombre"
				+ "  FROM APLICACION_x_NIVEL axn " + " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				// + " AND axn.nivel_id="
				// + SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id "
				// + "AND axnxat.actividad_tipo_id="
				// + SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN NIVEL n ON axn.nivel_id=n.id"
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id "// AND
																							// (a.actividad_estado_id!=7
																							// OR
																							// a.actividad_estado_id
																							// IS
																							// NULL)"
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
			mdto.setTransicion((o[13] == null || MaterialEstado.EN_EL_LUGAR.equals(o[13])) ? "En " : "Hacia ");
			mdtos.add(mdto);
		}
		return mdtos;
	}

	public List<MaterialDTO> findDTOSByIdAplicacionANDIdNivelANDIdActividadTipoANDCodigos(Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			List<String> codigos) {

		List<MaterialDTO> mdtos = new ArrayList<MaterialDTO>();
		Session s = getSession();
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
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id "// AND
																							// (a.actividad_estado_id!=7
																							// OR
																							// a.actividad_estado_id
																							// IS
																							// NULL)"
				+ " JOIN CURSO c ON a.curso_id=c.id"
				+ " JOIN ESTABLECIMIENTO e ON c.establecimiento_id=e.id"
				+ " JOIN MATERIAL_x_ACTIVIDAD mxa ON a.id=mxa.actividad_id"
				+ " JOIN MATERIAL m ON mxa.material_id=m.id"
				// + " JOIN CO ON m.centro_id=co.id"
				+ " JOIN MATERIAL_TIPO mt ON m.material_tipo_id=mt.id" + " LEFT JOIN MATERIAL_x_LOTE mxl ON m.id=mxl.material_id"
				+ " LEFT JOIN lote l ON mxl.lote_id=l.id"
				+ " LEFT JOIN (SELECT material_id, MAX(fecha) as fecha FROM MATERIAL_HISTORIAL GROUP BY material_id) mh_max ON m.id=mh_max.material_id"
				+ " LEFT JOIN MATERIAL_HISTORIAL mh ON mh_max.material_id=mh.material_id AND mh_max.fecha=mh.fecha"
				+ " LEFT JOIN LUGAR l_dest ON mh.destino_id=l_dest.id" + " LEFT JOIN MATERIAL_ESTADO me ON mh.material_estado_id=me.id";

		String where = "";
		if (codigos != null && !codigos.isEmpty()) {
			for (String codigo : codigos) {
				if (!where.equals("")) {
					where += " OR ";
				}
				if (codigo != null && !codigo.isEmpty()) {
					where += "m.codigo='" + SecurityFilter.escapeString(codigo) + "'";
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
			mdto.setTransicion((o[13] == null || MaterialEstado.EN_EL_LUGAR.equals(o[13])) ? "En " : "Hacia ");
			mdtos.add(mdto);
		}
		return mdtos;
	}

	public List<MaterialDTO> findDTOSByIdAplicacionANDIdNivelANDIdActividadTipoANDIdMateriales(Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			List<Integer> idMateriales) {

		List<MaterialDTO> mdtos = new ArrayList<MaterialDTO>();
		Session s = getSession();
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
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id "// AND
																							// (a.actividad_estado_id!=7
																							// OR
																							// a.actividad_estado_id
																							// IS
																							// NULL)"
				+ " JOIN CURSO c ON a.curso_id=c.id"
				+ " JOIN ESTABLECIMIENTO e ON c.establecimiento_id=e.id"
				+ " JOIN MATERIAL_x_ACTIVIDAD mxa ON a.id=mxa.actividad_id"
				+ " JOIN MATERIAL m ON mxa.material_id=m.id"
				// + " JOIN CO ON m.centro_id=co.id"
				+ " JOIN MATERIAL_TIPO mt ON m.material_tipo_id=mt.id" + " LEFT JOIN MATERIAL_x_LOTE mxl ON m.id=mxl.material_id"
				+ " LEFT JOIN lote l ON mxl.lote_id=l.id"
				+ " LEFT JOIN (SELECT material_id, MAX(fecha) as fecha FROM MATERIAL_HISTORIAL GROUP BY material_id) mh_max ON m.id=mh_max.material_id"
				+ " LEFT JOIN MATERIAL_HISTORIAL mh ON mh_max.material_id=mh.material_id AND mh_max.fecha=mh.fecha"
				+ " LEFT JOIN LUGAR l_dest ON mh.destino_id=l_dest.id" + " LEFT JOIN MATERIAL_ESTADO me ON mh.material_estado_id=me.id";

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
			mdto.setTransicion((o[13] == null || MaterialEstado.EN_EL_LUGAR.equals(o[13])) ? "En " : "Hacia ");
			mdtos.add(mdto);
		}
		return mdtos;
	}

	public MaterialDTO findDTOByIdAplicacionANDIdNivelANDIdActividadTipoANDCodigo(Integer idAplicacion, Integer idNivel, Integer idActividadTipo, String codigo) {
		MaterialDTO mdto = null;
		Session s = getSession();
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
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id "// AND
																							// (a.actividad_estado_id!=7
																							// OR
																							// a.actividad_estado_id
																							// IS
																							// NULL)"
				+ " JOIN CURSO c ON a.curso_id=c.id"
				+ " JOIN ESTABLECIMIENTO e ON c.establecimiento_id=e.id"
				+ " JOIN MATERIAL_x_ACTIVIDAD mxa ON a.id=mxa.actividad_id"
				+ " JOIN MATERIAL m ON mxa.material_id=m.id AND m.codigo='"
				+ SecurityFilter.escapeString(codigo)
				+ "'"
				// + " JOIN CO ON m.centro_id=co.id"
				+ " JOIN MATERIAL_TIPO mt ON m.material_tipo_id=mt.id" + " LEFT JOIN MATERIAL_x_LOTE mxl ON m.id=mxl.material_id"
				+ " LEFT JOIN lote l ON mxl.lote_id=l.id"
				+ " LEFT JOIN (SELECT material_id, MAX(fecha) as fecha FROM MATERIAL_HISTORIAL GROUP BY material_id) mh_max ON m.id=mh_max.material_id"
				+ " LEFT JOIN MATERIAL_HISTORIAL mh ON mh_max.material_id=mh.material_id AND mh_max.fecha=mh.fecha"
				+ " LEFT JOIN LUGAR l_dest ON mh.destino_id=l_dest.id" + " LEFT JOIN MATERIAL_ESTADO me ON mh.material_estado_id=me.id";
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
			mdto.setTransicion((o[13] == null || MaterialEstado.EN_EL_LUGAR.equals(o[13])) ? "En " : "Hacia ");
		}
		return mdto;
	}

	public Map<String, List<Integer>> findRepetidos() {

		Map<String, List<Integer>> ms = new HashMap<String, List<Integer>>();
		Session s = getSession();
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
		Session s = getSession();
		String query = "select m.id,cxe.co_id from material m"
				+ " join material_x_actividad mxa on m.id=mxa.material_id"
				+ " join actividad a on mxa.actividad_id=a.id "// AND
															   // (a.actividad_estado_id!=7
															   // OR
															   // a.actividad_estado_id
															   // IS NULL)"
				+ " join curso c on a.curso_id=c.id" + " join establecimiento e on c.establecimiento_id=e.id"
				+ " join co_x_establecimiento cxe on e.id=cxe.establecimiento_id and c.aplicacion_x_nivel_id=cxe.aplicacion_x_nivel_id"
				+ " where m.centro_id != cxe.co_id" + " order by cxe.co_id asc ";
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
		Session s = getSession();
		String query = "SELECT m.* FROM MATERIAL m " + " WHERE substring(m.codigo from 4 for 1)='" + SecurityFilter.escapeString(dia) + "';";
		Query q = s.createSQLQuery(query).addEntity(Material.class);
		ms = q.list();
		return ms;
	}

	public List<String> findTraspasoDeMaterial(String codigo) {

		List<String> lineas = new ArrayList<String>();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "WITH mh AS "
				+ "	(SELECT mh.fecha,m.codigo,mh.centro_id FROM material m "
				+ "		JOIN (SELECT mh.material_id,MAX(mh.fecha) as fecha FROM material_historial mh WHERE mh.origen_id=2 AND mh.destino_id=4 GROUP BY mh.material_id) mh_max ON m.id=mh_max.material_id "
				+ "		JOIN material_historial mh ON mh_max.material_id=mh.material_id AND mh_max.fecha=mh.fecha)"
				+ " SELECT DISTINCT 'I' as accion, '1' as id_actor_informante, '5' as id_actor_entrega,'' as id_centro_entrega,"
				+ " '1' as id_actor_recibe, co.nombre as id_centro_recibe, mh.codigo as codigo_barra,"
				+ " '' as id_estado_fisico_material,to_char(mh.fecha,  'dd-MM-YYYY HH24:MI') as fecha_hora_transaccion, '' as codigo_guia_de_despacho FROM mh"
				+ " JOIN co ON mh.centro_id=co.id WHERE mh.fecha > (SELECT mh.fecha FROM mh WHERE mh.codigo='" + SecurityFilter.escapeString(codigo)
				+ "')  ORDER BY fecha_hora_transaccion ASC;";
		Query q = s.createSQLQuery(query);
		List<Object[]> os = q.list();
		if (os != null && !os.isEmpty()) {
			lineas.add("accion;id_actor_informante;id_actor_entrega;id_centro_entrega;id_actor_recibe;id_centro_recibe;codigo_barra;id_estado_fisico_material;fecha_hora_transaccion;codigo_guia_de_despacho");
		}
		String linea;
		for (Object[] o : os) {
			linea = "";
			for (int i = 0; i < o.length; i++) {
				linea += (String) o[i];
				if (i < (o.length - 1)) {
					linea += ";";
				}
			}
			lineas.add(linea);
		}
		return lineas;
	}

	public List<String> findTraspasoDeMaterialSalida(String codigo) {

		List<String> lineas = new ArrayList<String>();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "WITH mh AS "
				+ " (SELECT mh.fecha,m.codigo,mh.centro_id FROM material m "
				+ "		JOIN (select mh.material_id,MAX(mh.fecha) as fecha FROM material_historial mh WHERE mh.origen_id=4 AND mh.destino_id=1 GROUP BY mh.material_id) mh_max ON m.id=mh_max.material_id"
				+ "		JOIN material_historial mh ON mh_max.material_id=mh.material_id AND mh_max.fecha=mh.fecha)"
				+ " SELECT DISTINCT 'I' as accion, '1' as id_actor_informante, '1' as id_actor_entrega,co.nombre as id_centro_entrega,"
				+ " '5' as id_actor_recibe,''  as id_centro_recibe, mh.codigo as codigo_barra,"
				+ " '' as id_estado_fisico_material,to_char(mh.fecha,  'dd-MM-YYYY HH24:MI') as fecha_hora_transaccion, '' as codigo_guia_de_despacho FROM mh"
				+ " JOIN co ON mh.centro_id=co.id where mh.fecha >(SELECT mh.fecha FROM mh WHERE mh.codigo='" + SecurityFilter.escapeString(codigo)
				+ "')  ORDER BY fecha_hora_transaccion ASC;";
		Query q = s.createSQLQuery(query);
		List<Object[]> os = q.list();
		if (os != null && !os.isEmpty()) {
			lineas.add("accion;id_actor_informante;id_actor_entrega;id_centro_entrega;id_actor_recibe;id_centro_recibe;codigo_barra;id_estado_fisico_material;fecha_hora_transaccion;codigo_guia_de_despacho");
		}
		String linea;
		for (Object[] o : os) {
			linea = "";
			for (int i = 0; i < o.length; i++) {
				linea += (String) o[i];
				if (i < (o.length - 1)) {
					linea += ";";
				}
			}
			lineas.add(linea);
		}
		return lineas;
	}

	public List<String> findUsoMaterialContingenciaByIdNivel(Integer idNivel) {

		List<String> lineas = new ArrayList<String>();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT e.id as rbd, c.nombre as curso," + " at.nombre as actividad,r.nombre as region, com.nombre as comuna,"
				+ "a.material_contingencia as utilizó_material_contingencia,a.detalle_material_contingencia FROM ACTIVIDAD a "
				+ "JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON a.aplicacion_x_nivel_x_actividad_tipo_id=axnxat.id "
				+ "JOIN APLICACION_x_NIVEL axn ON axnxat.aplicacion_x_nivel_id=axn.id AND axn.aplicacion_id=1 AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel) + "JOIN ACTIVIDAD_TIPO at ON axnxat.actividad_tipo_id=at.id " + "JOIN CURSO c ON a.curso_id=c.id "
				+ "JOIN ESTABLECIMIENTO e ON c.establecimiento_id=e.id " + "JOIN COMUNA com ON e.comuna_id=com.id "
				+ "JOIN PROVINCIA p ON com.provincia_id=p.id " + "JOIN REGION r ON p.region_id=r.id ORDER BY e.id,c.nombre,at.id;";
		Query q = s.createSQLQuery(query);
		List<Object[]> os = q.list();
		if (os != null && !os.isEmpty()) {
			lineas.add("rbd;curso;actividad;region;comuna;utilizó_material_contingencia;detalle_material_contingencia");
		}
		String linea;
		for (Object[] o : os) {
			linea = "";
			for (int i = 0; i < o.length; i++) {
				linea += (o[i] != null) ? o[i].toString() : "";
				if (i < (o.length - 1)) {
					linea += ";";
				}
			}
			lineas.add(linea);
		}
		return lineas;
	}

	public List<EstadoMaterialItemDTO> getEstadisticasMaterial(Integer idAplicacion, Integer idNivel, Integer idActividadTipo, String usuarioTipo,
			Integer idUsuario) {

		List<EstadoMaterialItemDTO> emidtos = new ArrayList<EstadoMaterialItemDTO>();
		;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "WITH mat as (SELECT DISTINCT mxa.material_id,mh.destino_id, e.comuna_id,p.region_id FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				+ " AND axn.nivel_id="
				+ SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id AND axnxat.actividad_tipo_id="
				+ SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id"
				+ " JOIN MATERIAL_x_ACTIVIDAD mxa ON a.id=mxa.actividad_id"
				+ " LEFT JOIN (SELECT DISTINCT mh.material_id,MAX(mh.fecha)  as fecha FROM MATERIAL_HISTORIAL mh GROUP BY mh.material_id) mh_max ON mxa.material_id=mh_max.material_id"
				+ " LEFT JOIN MATERIAL_HISTORIAL mh ON mh_max.material_id=mh.material_id AND mh_max.fecha=mh.fecha" + " JOIN CURSO c ON a.curso_id=c.id"
				+ " JOIN ESTABLECIMIENTO e ON c.establecimiento_id=e.id" + " JOIN COMUNA com ON e.comuna_id=com.id"
				+ " JOIN PROVINCIA p ON com.provincia_id=p.id ";
		if (usuarioTipo.equals(UsuarioTipo.JEFE_REGIONAL) || usuarioTipo.equals(UsuarioTipo.JEFE_ZONAL)
				|| usuarioTipo.equals(UsuarioTipo.JEFE_CENTRO_OPERACIONES) || usuarioTipo.equals(UsuarioTipo.LOGISTICA_Y_SOPORTE)) {
			query += " JOIN CO_x_ESTABLECIMIENTO coxe ON (e.id=coxe.establecimiento_id  AND axn.id=coxe.aplicacion_x_nivel_id)";
			if (usuarioTipo.equals(UsuarioTipo.JEFE_CENTRO_OPERACIONES) || usuarioTipo.equals(UsuarioTipo.LOGISTICA_Y_SOPORTE)) {
				query += " JOIN JO_x_CO joxco ON (coxe.co_id=joxco.co_id AND joxco.jo_id=" + SecurityFilter.escapeString(idUsuario) + ") AND joxco.activo=TRUE";
			} else {
				query += " JOIN CO co ON coxe.co_id=co.id";
				if (usuarioTipo.equals(UsuarioTipo.JEFE_ZONAL)) {
					query += " JOIN JZ_x_ZONA jzxz ON (co.zona_id=jzxz.zona_id AND jzxz.jz_id=" + SecurityFilter.escapeString(idUsuario)
							+ ") AND jzxz.activo=TRUE";
				} else {
					query += " JOIN ZONA z ON co.zona_id=z.id"
							+ " JOIN JR_x_CENTRO_REGIONAL jrxcr ON (z.centro_regional_id=jrxcr.centro_regional_id AND jrxcr.jr_id="
							+ SecurityFilter.escapeString(idUsuario) + ") AND jrxcr.activo=TRUE";
				}
			}
		} else if (usuarioTipo.equals(UsuarioTipo.SUPERVISOR) || usuarioTipo.equals(UsuarioTipo.COORDINADOR) || usuarioTipo.equals(UsuarioTipo.EXAMINADOR)
				|| usuarioTipo.equals(UsuarioTipo.EXAMINADOR_NEE) || usuarioTipo.equals(UsuarioTipo.COORDINADOR_COMPUTACION)) {
			query += " JOIN USUARIO_x_ACTIVIDAD uxa ON a.id=uxa.actividad_id" + " JOIN USUARIO_SELECCION us ON uxa.usuario_seleccion_id=us.id"
					+ " JOIN USUARIO_x_APLICACION_x_NIVEL uxaxn ON (us.usuario_x_aplicacion_x_nivel_id=uxaxn.id AND uxaxn.usuario_id="
					+ SecurityFilter.escapeString(idUsuario) + ")";
		}
		query += ")"
				+ " SELECT DISTINCT mat.comuna_id, region_id, total_agencia, total_imprenta, total_centro, total_est, total_captura, total_bodega FROM mat"
				+ " LEFT JOIN (SELECT comuna_id,COUNT(material_id) as total_agencia FROM mat"
				+ " WHERE destino_id=1 GROUP BY comuna_id) ta ON mat.comuna_id=ta.comuna_id"

				+ " LEFT JOIN (SELECT comuna_id,COUNT(material_id) as total_imprenta FROM mat"
				+ " WHERE destino_id=2 OR destino_id IS NULL GROUP BY comuna_id) ti ON mat.comuna_id=ti.comuna_id"

				+ " LEFT JOIN (SELECT comuna_id,COUNT(material_id) as total_centro FROM mat"
				+ " WHERE destino_id=3 GROUP BY comuna_id) tco ON mat.comuna_id=tco.comuna_id"

				+ " LEFT JOIN (SELECT comuna_id,COUNT(material_id) as total_est FROM mat"
				+ " WHERE destino_id=4 GROUP BY comuna_id) te ON mat.comuna_id=te.comuna_id"

				+ " LEFT JOIN (SELECT comuna_id,COUNT(material_id) as total_captura FROM mat"
				+ " WHERE destino_id=5 GROUP BY comuna_id) tc ON mat.comuna_id=tc.comuna_id"

				+ " LEFT JOIN (SELECT comuna_id,COUNT(material_id) as total_bodega FROM mat"
				+ " WHERE destino_id=6 GROUP BY comuna_id) tb ON mat.comuna_id=tb.comuna_id" + " ORDER BY mat.comuna_id, region_id";
		Query q = s.createSQLQuery(query);
		List<Object[]> os = q.list();
		EstadoMaterialItemDTO emidto = null;
		for (Object[] o : os) {
			emidto = new EstadoMaterialItemDTO();
			emidto.setIdComuna((Integer) o[0]);
			emidto.setIdRegion((Integer) o[1]);
			emidto.setTotalAgencia((o[2] == null) ? 0 : ((BigInteger) o[2]).intValue());
			emidto.setTotalImprenta((o[3] == null) ? 0 : ((BigInteger) o[3]).intValue());
			emidto.setTotalCentro((o[4] == null) ? 0 : ((BigInteger) o[4]).intValue());
			emidto.setTotalEstablecimiento((o[5] == null) ? 0 : ((BigInteger) o[5]).intValue());
			emidto.setTotalCaptura((o[6] == null) ? 0 : ((BigInteger) o[6]).intValue());
			emidto.setTotalBodega((o[7] == null) ? 0 : ((BigInteger) o[7]).intValue());
			emidtos.add(emidto);
		}
		return emidtos;
	}
}
