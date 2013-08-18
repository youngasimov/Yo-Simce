package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 16-08-2013 05:13:17 AM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.List;

import com.dreamer8.yosimce.shared.dto.EmplazamientoDTO;

/**
 * Zona generated by hbm2java
 */
public class Zona implements java.io.Serializable {

	private Integer id;
	private CentroRegional centroRegional;
	private String nombre;
	private List<JzXZona> jzXZonas = new ArrayList<JzXZona>(0);
	private List<Co> cos = new ArrayList<Co>(0);

	public Zona() {
	}

	public Zona(Integer id) {
		this.id = id;
	}

	public Zona(Integer id, CentroRegional centroRegional, String nombre,
			List<JzXZona> jzXZonas, List<Co> cos) {
		this.id = id;
		this.centroRegional = centroRegional;
		this.nombre = nombre;
		this.jzXZonas = jzXZonas;
		this.cos = cos;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CentroRegional getCentroRegional() {
		return this.centroRegional;
	}

	public void setCentroRegional(CentroRegional centroRegional) {
		this.centroRegional = centroRegional;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<JzXZona> getJzXZonas() {
		return this.jzXZonas;
	}

	public void setJzXZonas(List<JzXZona> jzXZonas) {
		this.jzXZonas = jzXZonas;
	}

	public List<Co> getCos() {
		return this.cos;
	}

	public void setCos(List<Co> cos) {
		this.cos = cos;
	}

	public EmplazamientoDTO getEmplazamientoDTO() {
		EmplazamientoDTO edto = new EmplazamientoDTO();
		edto.setId(id);
		edto.setNombre(nombre);
		edto.setTipoEmplazamiento(EmplazamientoDTO.ZONA);
		return edto;
	}

}
