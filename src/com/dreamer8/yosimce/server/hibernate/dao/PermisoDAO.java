/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import java.util.List;

import com.dreamer8.yosimce.server.hibernate.pojo.Permiso;

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
		// TODO Auto-generated method stub
		return null;
	}

}
