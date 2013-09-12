package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 16-08-2013 05:13:17 AM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.List;

/**
 * CarreraEstado generated by hbm2java
 */
public class CarreraEstado implements java.io.Serializable {
	
	public static final String ESTUDIANDO = "Estudiando";
	public static final String EGRESADO = "Egresado";
	public static final String TITULADO = "Titulado";

	private Integer id;
	private String nombre;
	private List<Usuario> usuarios = new ArrayList<Usuario>(0);
	private List<AplicacionXUsuarioTipo> aplicacionXUsuarioTipos = new ArrayList<AplicacionXUsuarioTipo>(0);

	public CarreraEstado() {
	}

	public CarreraEstado(Integer id) {
		this.id = id;
	}

	public CarreraEstado(Integer id, String nombre, List<Usuario> usuarios,
			List<AplicacionXUsuarioTipo> aplicacionXUsuarioTipos) {
		this.id = id;
		this.nombre = nombre;
		this.usuarios = usuarios;
		this.aplicacionXUsuarioTipos = aplicacionXUsuarioTipos;
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

	public List<AplicacionXUsuarioTipo> getAplicacionXUsuarioTipos() {
		return this.aplicacionXUsuarioTipos;
	}

	public void setAplicacionXUsuarioTipos(List<AplicacionXUsuarioTipo> aplicacionXUsuarioTipos) {
		this.aplicacionXUsuarioTipos = aplicacionXUsuarioTipos;
	}

}
