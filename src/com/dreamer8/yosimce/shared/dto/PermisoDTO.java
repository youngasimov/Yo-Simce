/**
 * 
 */
package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.gwt.view.client.ProvidesKey;

/**
 * @author jorge
 * 
 */
public class PermisoDTO implements Serializable {
	
	public static final ProvidesKey<PermisoDTO> KEY_PROVIDER = new ProvidesKey<PermisoDTO>() {

		@Override
		public Object getKey(PermisoDTO item) {
			return (item == null) ? null : item.getIdPermiso();
		}
	};
	
	private Integer idPermiso;
	private String clase;
	private String metodo;
	private ArrayList<Integer> idTiposUsuariosPermitidos;

	public PermisoDTO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the idPermiso
	 */
	public Integer getIdPermiso() {
		return idPermiso;
	}

	/**
	 * @param idPermiso
	 *            the idPermiso to set
	 */
	public void setIdPermiso(Integer idPermiso) {
		this.idPermiso = idPermiso;
	}

	/**
	 * @return the clase
	 */
	public String getClase() {
		return clase;
	}

	/**
	 * @param clase
	 *            the clase to set
	 */
	public void setClase(String clase) {
		this.clase = clase;
	}

	/**
	 * @return the metodo
	 */
	public String getMetodo() {
		return metodo;
	}

	/**
	 * @param metodo
	 *            the metodo to set
	 */
	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}

	/**
	 * @return the idTiposUsuariosPermitidos
	 */
	public ArrayList<Integer> getIdTiposUsuariosPermitidos() {
		return idTiposUsuariosPermitidos;
	}

	/**
	 * @param idTiposUsuariosPermitidos
	 *            the idTiposUsuariosPermitidos to set
	 */
	public void setIdTiposUsuariosPermitidos(
			ArrayList<Integer> idTiposUsuariosPermitidos) {
		this.idTiposUsuariosPermitidos = idTiposUsuariosPermitidos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idPermiso == null) ? 0 : idPermiso.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PermisoDTO other = (PermisoDTO) obj;
		if (idPermiso == null) {
			if (other.idPermiso != null)
				return false;
		} else if (!idPermiso.equals(other.idPermiso))
			return false;
		return true;
	}
}
