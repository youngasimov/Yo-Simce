package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 12-07-2013 05:32:10 AM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.List;

/**
 * CentroRegional generated by hbm2java
 */
public class CentroRegional implements java.io.Serializable {

	private Integer id;
	private Aplicacion aplicacion;
	private String nombre;
	private List<Zona> zonas = new ArrayList<Zona>(0);
	private List<JrXCentroRegional> jrXCentroRegionals = new ArrayList<JrXCentroRegional>(0);

	public CentroRegional() {
	}

	public CentroRegional(Integer id) {
		this.id = id;
	}

	public CentroRegional(Integer id, Aplicacion aplicacion, String nombre,
			List<Zona> zonas, List<JrXCentroRegional> jrXCentroRegionals) {
		this.id = id;
		this.aplicacion = aplicacion;
		this.nombre = nombre;
		this.zonas = zonas;
		this.jrXCentroRegionals = jrXCentroRegionals;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Aplicacion getAplicacion() {
		return this.aplicacion;
	}

	public void setAplicacion(Aplicacion aplicacion) {
		this.aplicacion = aplicacion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Zona> getZonas() {
		return this.zonas;
	}

	public void setZonas(List<Zona> zonas) {
		this.zonas = zonas;
	}

	public List<JrXCentroRegional> getJrXCentroRegionals() {
		return this.jrXCentroRegionals;
	}

	public void setJrXCentroRegionals(List<JrXCentroRegional> jrXCentroRegionals) {
		this.jrXCentroRegionals = jrXCentroRegionals;
	}

}
