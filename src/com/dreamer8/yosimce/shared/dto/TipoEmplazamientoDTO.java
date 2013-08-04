package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;

public class TipoEmplazamientoDTO implements Serializable {

	private Integer id;
	private String tipoEmplazamiento;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTipoEmplazamiento() {
		return tipoEmplazamiento;
	}
	public void setTipoEmplazamiento(String tipoEmplazamiento) {
		this.tipoEmplazamiento = tipoEmplazamiento;
	}
}
