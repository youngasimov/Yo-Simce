package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 16-08-2013 05:13:17 AM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.List;

/**
 * Universidad generated by hbm2java
 */
public class Universidad implements java.io.Serializable {

	private Integer id;
	private InstitucionTipo institucionTipo;
	private String nombre;
	private List<Carrera> carreras = new ArrayList<Carrera>(0);

	public Universidad() {
	}

	public Universidad(Integer id) {
		this.id = id;
	}

	public Universidad(Integer id, InstitucionTipo institucionTipo, String nombre,
			List<Carrera> carreras) {
		this.id = id;
		this.institucionTipo = institucionTipo;
		this.nombre = nombre;
		this.carreras = carreras;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public InstitucionTipo getInstitucionTipo() {
		return this.institucionTipo;
	}

	public void setInstitucionTipo(InstitucionTipo institucionTipo) {
		this.institucionTipo = institucionTipo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Carrera> getCarreras() {
		return this.carreras;
	}

	public void setCarreras(List<Carrera> carreras) {
		this.carreras = carreras;
	}

}
