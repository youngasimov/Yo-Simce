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
public class NivelDTO implements Serializable {
	
	public static final ProvidesKey<NivelDTO> KEY_PROVIDER = new ProvidesKey<NivelDTO>() {

		@Override
		public Object getKey(NivelDTO item) {
			return (item == null) ? null : item.getId();
		}
	};
	
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
