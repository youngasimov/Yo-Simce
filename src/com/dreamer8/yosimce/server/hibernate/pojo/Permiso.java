package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 12-07-2013 05:32:10 AM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.List;

/**
 * Permiso generated by hbm2java
 */
public class Permiso implements java.io.Serializable {

	private Integer id;
	private String clase;
	private String metodo;
	private String descripcion;
	private List<AplicacionXUsuarioTipoXPermiso> aplicacionXUsuarioTipoXPermisos = new ArrayList<AplicacionXUsuarioTipoXPermiso>(0);

	public Permiso() {
	}

	public Permiso(Integer id) {
		this.id = id;
	}

	public Permiso(Integer id, String clase, String metodo, String descripcion,
			List<AplicacionXUsuarioTipoXPermiso> aplicacionXUsuarioTipoXPermisos) {
		this.id = id;
		this.clase = clase;
		this.metodo = metodo;
		this.descripcion = descripcion;
		this.aplicacionXUsuarioTipoXPermisos = aplicacionXUsuarioTipoXPermisos;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getClase() {
		return this.clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

	public String getMetodo() {
		return this.metodo;
	}

	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<AplicacionXUsuarioTipoXPermiso> getAplicacionXUsuarioTipoXPermisos() {
		return this.aplicacionXUsuarioTipoXPermisos;
	}

	public void setAplicacionXUsuarioTipoXPermisos(
			List<AplicacionXUsuarioTipoXPermiso> aplicacionXUsuarioTipoXPermisos) {
		this.aplicacionXUsuarioTipoXPermisos = aplicacionXUsuarioTipoXPermisos;
	}

}
