/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.Material;
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
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id"
				+ " JOIN MATERIAL_x_ACTIVIDAD mxa ON a.id=mxa.actividad_id"
				+ " JOIN MATERIAL m ON mxa.material_id=m.id AND m.centro_id="
				+ SecurityFilter.escapeString(idCo);
		Query q = s.createSQLQuery(query).addEntity(Material.class);
		ms = q.list();
		return ms;
	}

	public List<MaterialDTO> findDTOSByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCo(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			Integer idCo) {

		List<MaterialDTO> mdtos = new ArrayList<MaterialDTO>();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT DISTINCT m.id as m_id,m.codigo as m_cod,mt.nombre as m_tipo,"
				+ "m.centro_id as co_id,e.id as rbd,e.nombre as e_nom, c.nombre as c_nom, n.nombre as n_nom,"
				+ "l_dest.nombre as dest_nom, l.id as l_id, l.nombre as l_nom"
				+ "  FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				// + " AND axn.nivel_id="
				// + SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id AND axnxat.actividad_tipo_id="
				+ SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN NIVEL n ON axn.nivel_id=n.id"
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id"
				+ " JOIN CURSO c ON a.curso_id=c.id"
				+ " JOIN ESTABLECIMIENTO e ON c.establecimiento_id=e.id"
				+ " JOIN MATERIAL_x_ACTIVIDAD mxa ON a.id=mxa.actividad_id"
				+ " JOIN MATERIAL m ON mxa.material_id=m.id AND m.centro_id="
				+ SecurityFilter.escapeString(idCo)
				// + " JOIN CO ON m.centro_id=co.id"
				+ " JOIN MATERIAL_TIPO mt ON m.material_tipo_id=mt.id"
				+ " LEFT JOIN MATERIAL_x_LOTE mxl ON m.id=mxl.material_id"
				+ " LEFT JOIN lote l ON mxl.lote_id=id"
				+ " LEFT JOIN (SELECT material_id, MAX(fecha) as fecha FROM MATERIAL_HISTORIAL GROUP BY material_id) mh_max ON m.id=mh_max.material_id"
				+ " LEFT JOIN MATERIAL_HISTORIAL mh ON mh_max.material_id=mh.material_id AND mh_max.fecha=mh.fecha"
				+ " LEFT JOIN LUGAR l_dest ON mh.destino_id=l_dest.id";
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
			mdto.setEtapa((String) o[8]);
			if (o[9] != null) {
				ldto = new LoteDTO();
				ldto.setId((Integer) o[9]);
				ldto.setNombre((String) o[10]);
				mdto.setLote(ldto);
			}

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
				+ "l_dest.nombre as dest_nom, l.id as l_id, l.nombre as l_nom"
				+ "  FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				// + " AND axn.nivel_id="
				// + SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id AND axnxat.actividad_tipo_id="
				+ SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN NIVEL n ON axn.nivel_id=n.id"
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id"
				+ " JOIN CURSO c ON a.curso_id=c.id"
				+ " JOIN ESTABLECIMIENTO e ON c.establecimiento_id=e.id"
				+ " JOIN MATERIAL_x_ACTIVIDAD mxa ON a.id=mxa.actividad_id"
				+ " JOIN MATERIAL m ON mxa.material_id=m.id"
				// + " JOIN CO ON m.centro_id=co.id"
				+ " JOIN MATERIAL_TIPO mt ON m.material_tipo_id=mt.id"
				+ " LEFT JOIN MATERIAL_x_LOTE mxl ON m.id=mxl.material_id"
				+ " LEFT JOIN lote l ON mxl.lote_id=id"
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
			mdto.setEtapa((String) o[8]);
			if (o[9] != null) {
				ldto = new LoteDTO();
				ldto.setId((Integer) o[9]);
				ldto.setNombre((String) o[10]);
				mdto.setLote(ldto);
			}

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
				+ "l_dest.nombre as dest_nom, l.id as l_id, l.nombre as l_nom"
				+ "  FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				// + " AND axn.nivel_id="
				// + SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id AND axnxat.actividad_tipo_id="
				+ SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN NIVEL n ON axn.nivel_id=n.id"
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id"
				+ " JOIN CURSO c ON a.curso_id=c.id"
				+ " JOIN ESTABLECIMIENTO e ON c.establecimiento_id=e.id"
				+ " JOIN MATERIAL_x_ACTIVIDAD mxa ON a.id=mxa.actividad_id"
				+ " JOIN MATERIAL m ON mxa.material_id=m.id AND m.codigo='"
				+ SecurityFilter.escapeString(codigo)
				+ "'"
				// + " JOIN CO ON m.centro_id=co.id"
				+ " JOIN MATERIAL_TIPO mt ON m.material_tipo_id=mt.id"
				+ " LEFT JOIN MATERIAL_x_LOTE mxl ON m.id=mxl.material_id"
				+ " LEFT JOIN lote l ON mxl.lote_id=id"
				+ " LEFT JOIN (SELECT material_id, MAX(fecha) as fecha FROM MATERIAL_HISTORIAL GROUP BY material_id) mh_max ON m.id=mh_max.material_id"
				+ " LEFT JOIN MATERIAL_HISTORIAL mh ON mh_max.material_id=mh.material_id AND mh_max.fecha=mh.fecha"
				+ " LEFT JOIN LUGAR l_dest ON mh.destino_id=l_dest.id";
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
			mdto.setEtapa((String) o[8]);
			if (o[9] != null) {
				ldto = new LoteDTO();
				ldto.setId((Integer) o[9]);
				ldto.setNombre((String) o[10]);
				mdto.setLote(ldto);
			}
		}
		return mdto;
	}
}
