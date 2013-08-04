package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;

public class EmplazamientoDTO implements Serializable {

	private Integer id;
	private String nombre;
	private TipoEmplazamientoDTO tipoEmplazamiento;

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

	public TipoEmplazamientoDTO getTipoEmplazamiento() {
		return tipoEmplazamiento;
	}

	public void setTipoEmplazamiento(TipoEmplazamientoDTO tipoEmplazamiento) {
		this.tipoEmplazamiento = tipoEmplazamiento;
	}
}
