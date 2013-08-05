package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 05-08-2013 03:58:39 AM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.List;

/**
 * IncidenciaTipo generated by hbm2java
 */
public class IncidenciaTipo implements java.io.Serializable {

	private Integer id;
	private String nombre;
	private List<MotivoFalla> motivoFallas = new ArrayList<MotivoFalla>(0);

	public IncidenciaTipo() {
	}

	public IncidenciaTipo(Integer id) {
		this.id = id;
	}

	public IncidenciaTipo(Integer id, String nombre, List<MotivoFalla> motivoFallas) {
		this.id = id;
		this.nombre = nombre;
		this.motivoFallas = motivoFallas;
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

	public List<MotivoFalla> getMotivoFallas() {
		return this.motivoFallas;
	}

	public void setMotivoFallas(List<MotivoFalla> motivoFallas) {
		this.motivoFallas = motivoFallas;
	}

}
