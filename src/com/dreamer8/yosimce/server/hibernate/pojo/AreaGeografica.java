package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 16-07-2013 11:03:56 PM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.List;

/**
 * AreaGeografica generated by hbm2java
 */
public class AreaGeografica implements java.io.Serializable {

	private Integer id;
	private String nombre;
	private List<Establecimiento> establecimientos = new ArrayList<Establecimiento>(0);

	public AreaGeografica() {
	}

	public AreaGeografica(Integer id) {
		this.id = id;
	}

	public AreaGeografica(Integer id, String nombre, List<Establecimiento> establecimientos) {
		this.id = id;
		this.nombre = nombre;
		this.establecimientos = establecimientos;
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

	public List<Establecimiento> getEstablecimientos() {
		return this.establecimientos;
	}

	public void setEstablecimientos(List<Establecimiento> establecimientos) {
		this.establecimientos = establecimientos;
	}

}
