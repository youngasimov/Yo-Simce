/**
 * 
 */
package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;

/**
 * @author jorge
 * 
 */
public class NivelDTO implements Serializable {
	private Integer id;
	private String nombre;

	public NivelDTO() {
		// TODO Auto-generated constructor stub
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
