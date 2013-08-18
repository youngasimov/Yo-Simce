package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 16-08-2013 05:13:17 AM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.List;

/**
 * AlumnoEstado generated by hbm2java
 */
public class AlumnoEstado implements java.io.Serializable {

	private Integer id;
	private String nombre;
	private List<AlumnoXActividad> alumnoXActividads = new ArrayList<AlumnoXActividad>(0);

	public AlumnoEstado() {
	}

	public AlumnoEstado(Integer id) {
		this.id = id;
	}

	public AlumnoEstado(Integer id, String nombre, List<AlumnoXActividad> alumnoXActividads) {
		this.id = id;
		this.nombre = nombre;
		this.alumnoXActividads = alumnoXActividads;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<AlumnoXActividad> getAlumnoXActividads() {
		return this.alumnoXActividads;
	}

	public void setAlumnoXActividads(List<AlumnoXActividad> alumnoXActividads) {
		this.alumnoXActividads = alumnoXActividads;
	}

}
