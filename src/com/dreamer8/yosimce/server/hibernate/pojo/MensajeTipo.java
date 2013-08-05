package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 05-08-2013 03:58:39 AM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.List;

/**
 * MensajeTipo generated by hbm2java
 */
public class MensajeTipo implements java.io.Serializable {

	private Integer id;
	private String nombre;
	private List<Mensaje> mensajes = new ArrayList<Mensaje>(0);

	public MensajeTipo() {
	}

	public MensajeTipo(Integer id) {
		this.id = id;
	}

	public MensajeTipo(Integer id, String nombre, List<Mensaje> mensajes) {
		this.id = id;
		this.nombre = nombre;
		this.mensajes = mensajes;
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

	public List<Mensaje> getMensajes() {
		return this.mensajes;
	}

	public void setMensajes(List<Mensaje> mensajes) {
		this.mensajes = mensajes;
	}

}
