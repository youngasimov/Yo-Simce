package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 05-08-2013 03:58:39 AM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.List;

/**
 * CompaniaTelefono generated by hbm2java
 */
public class CompaniaTelefono implements java.io.Serializable {

	private Integer id;
	private String nombre;
	private List<Usuario> usuarios = new ArrayList<Usuario>(0);

	public CompaniaTelefono() {
	}

	public CompaniaTelefono(Integer id) {
		this.id = id;
	}

	public CompaniaTelefono(Integer id, String nombre, List<Usuario> usuarios) {
		this.id = id;
		this.nombre = nombre;
		this.usuarios = usuarios;
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

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

}
