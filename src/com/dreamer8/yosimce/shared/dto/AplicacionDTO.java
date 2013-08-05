/**
 * 
 */
package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;

import com.google.gwt.view.client.ProvidesKey;

/**
 * @author jorge
 * 
 */
public class AplicacionDTO implements Serializable {
	
	public static final ProvidesKey<AplicacionDTO> KEY_PROVIDER = new ProvidesKey<AplicacionDTO>() {

		@Override
		public Object getKey(AplicacionDTO item) {
			return (item == null) ? null : item.getId();
		}
	};
	
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
