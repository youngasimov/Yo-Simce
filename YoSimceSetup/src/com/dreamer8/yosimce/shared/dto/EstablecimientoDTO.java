package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;

public class EstablecimientoDTO implements Serializable{

	
	private Integer id;
	private String rbd;
	private String name;
	private String estadoActividad;
	private String estadoAgendamiento;
	private String estadoMaterialSincronizado;
	
	public EstablecimientoDTO() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEstadoActividad() {
		return estadoActividad;
	}

	public void setEstadoActividad(String estadoActividad) {
		this.estadoActividad = estadoActividad;
	}

	public String getEstadoAgendamiento() {
		return estadoAgendamiento;
	}

	public void setEstadoAgendamiento(String estadoAgendamiento) {
		this.estadoAgendamiento = estadoAgendamiento;
	}

	public String getEstadoMaterialSincronizado() {
		return estadoMaterialSincronizado;
	}

	public void setEstadoMaterialSincronizado(String estadoMaterialSincronizado) {
		this.estadoMaterialSincronizado = estadoMaterialSincronizado;
	}

	public String getRbd() {
		return rbd;
	}

	public void setRbd(String rbd) {
		this.rbd = rbd;
	}
}
