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
public class ActividadTipoDTO implements Serializable {
	
	public static final ProvidesKey<ActividadTipoDTO> KEY_PROVIDER = new ProvidesKey<ActividadTipoDTO>() {

		@Override
		public Object getKey(ActividadTipoDTO item) {
			return (item == null) ? null : item.getId();
		}
	};
	
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
