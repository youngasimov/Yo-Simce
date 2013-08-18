/**
 * 
 */
package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * @author jorge
 * 
 */
public class PermisoDTO implements Serializable {
	
		
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

}
