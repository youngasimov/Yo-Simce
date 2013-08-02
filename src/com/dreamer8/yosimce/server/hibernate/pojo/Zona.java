package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 01-08-2013 04:51:27 AM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * Zona generated by hbm2java
 */
public class Zona implements java.io.Serializable {

	private int id;
	private CentroRegional centroRegional;
	private String nombre;
	private Set jzXZonas = new HashSet(0);
	private Set cos = new HashSet(0);

	public Zona() {
	}

	public Zona(int id) {
		this.id = id;
	}

	public Zona(int id, CentroRegional centroRegional, String nombre,
			Set jzXZonas, Set cos) {
		this.id = id;
		this.centroRegional = centroRegional;
		this.nombre = nombre;
		this.jzXZonas = jzXZonas;
		this.cos = cos;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
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

	public Set getJzXZonas() {
		return this.jzXZonas;
	}

	public void setJzXZonas(Set jzXZonas) {
		this.jzXZonas = jzXZonas;
	}

	public Set getCos() {
		return this.cos;
	}

	public void setCos(Set cos) {
		this.cos = cos;
	}

}
