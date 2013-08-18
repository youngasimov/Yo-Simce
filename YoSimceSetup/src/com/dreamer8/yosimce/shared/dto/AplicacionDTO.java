/**
 * 
 */
package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;


/**
 * @author jorge
 * 
 */
public class AplicacionDTO implements Serializable {
	

	private Integer id;
	private String nombre;

	/**
	 * 
	 */
	public AplicacionDTO() {
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
