/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.pojo.MaterialHistorial;
import com.dreamer8.yosimce.server.hibernate.pojo.MaterialHistorialId;
import com.dreamer8.yosimce.server.utils.SecurityFilter;
import com.dreamer8.yosimce.shared.dto.HistorialMaterialItemDTO;

/**
 * @author jorge
 * 
 */
public class MaterialHistorialDAO extends
		AbstractHibernateDAO<MaterialHistorial, MaterialHistorialId> {

	public List<MaterialHistorial> findByIdMaterial(Integer idMaterial) {

		List<MaterialHistorial> mhs = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT mh.* FROM  MATERIAL_HISTORIAL mh"
				+ " WHERE mh.material_id="
				+ SecurityFilter.escapeString(idMaterial);
		Query q = s.createSQLQuery(query).addEntity(MaterialHistorial.class);
		mhs = q.list();
		return mhs;
	}

	public MaterialHistorial findLastByIdMaterial(Integer idMaterial) {

		MaterialHistorial mh = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT mh.*  FROM MATERIAL_HISTORIAL mh"
				+ " JOIN (SELECT mh.material_id,MAX(mh.fecha) as fecha"
				+ " FROM MATERIAL_HISTORIAL mh"
				+ " WHERE mh.material_id="
				+ SecurityFilter.escapeString(idMaterial)
				+ " GROUP BY mh.material_id) mh_max ON mh.material_id=mh_max.material_id AND mh.fecha=mh_max.fecha";
		Query q = s.createSQLQuery(query).addEntity(MaterialHistorial.class);
		mh = ((MaterialHistorial) q.uniqueResult());
		return mh;
	}

	public List<HistorialMaterialItemDTO> findByIdAplicacionANDIdNivelANDIdActividadTipoANDCodigos(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			List<String> codigos) {

		List<HistorialMaterialItemDTO> mhs = new ArrayList<HistorialMaterialItemDTO>();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String query = "SELECT DISTINCT mh.material_id,mh.origen_id,mh.destino_id FROM APLICACION_x_NIVEL axn "
				+ " JOIN APLICACION_x_NIVEL_x_ACTIVIDAD_TIPO axnxat ON (axn.aplicacion_id="
				+ SecurityFilter.escapeString(idAplicacion)
				// + " AND axn.nivel_id="
				// + SecurityFilter.escapeString(idNivel)
				+ " AND axn.id=axnxat.aplicacion_x_nivel_id "
				// + "AND axnxat.actividad_tipo_id="
				// + SecurityFilter.escapeString(idActividadTipo)
				+ ")"
				+ " JOIN ACTIVIDAD a ON axnxat.id=a.aplicacion_x_nivel_x_actividad_tipo_id"
				+ " JOIN CURSO c ON a.curso_id=c.id"
				+ " JOIN ESTABLECIMIENTO e ON c.establecimiento_id=e.id"
				+ " JOIN MATERIAL_x_ACTIVIDAD mxa ON a.id=mxa.actividad_id"
				+ " JOIN MATERIAL m ON mxa.material_id=m.id"
				// + " JOIN CO ON m.centro_id=co.id"
				+ " LEFT JOIN (SELECT material_id, MAX(fecha) as fecha FROM MATERIAL_HISTORIAL GROUP BY material_id) mh_max ON m.id=mh_max.material_id"
				+ " LEFT JOIN MATERIAL_HISTORIAL mh ON mh_max.material_id=mh.material_id AND mh_max.fecha=mh.fecha";

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
		HistorialMaterialItemDTO hmidto = null;
		for (Object[] o : os) {
			hmidto = new HistorialMaterialItemDTO();
			hmidto.setId((Integer) o[0]);
			hmidto.setIdOrigen((Integer) o[1]);
			hmidto.setIdDestino((Integer) o[2]);
			mhs.add(hmidto);
		}
		return mhs;
	}

}
