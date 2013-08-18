/**
 * 
 */
package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;

/**
 * @author jorge
 * 
 */
public class ActividadTipoDTO implements Serializable {
	

	private Integer id;
	private String nombre;

	/**
	 * 
	 */
	public ActividadTipoDTO() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
