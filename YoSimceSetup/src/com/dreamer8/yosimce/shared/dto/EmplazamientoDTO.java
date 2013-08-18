package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;

public class EmplazamientoDTO implements Serializable {

	public static final String CENTRO_OPERACIONAL = "Centro Operacional";
	public static final String ZONA = "Zona";
	public static final String CENTRO_REGIONAL = "Centro Regional";
	public static final String CENTRO_CAPACITACION = "Centro de Capacitación";

	private Integer id;
	private String nombre;
	private String tipoEmplazamiento;

	/**
	 * 
	 */
	public EmplazamientoDTO() {
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

	public String getTipoEmplazamiento() {
		return tipoEmplazamiento;
	}

	public void setTipoEmplazamiento(String tipoEmplazamiento) {
		this.tipoEmplazamiento = tipoEmplazamiento;
	}
}
