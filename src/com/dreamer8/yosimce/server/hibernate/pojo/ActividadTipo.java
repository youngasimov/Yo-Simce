package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 05-08-2013 03:58:39 AM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.List;

import com.dreamer8.yosimce.shared.dto.ActividadTipoDTO;

/**
 * ActividadTipo generated by hbm2java
 */
public class ActividadTipo implements java.io.Serializable {

	private Integer id;
	private String nombre;
	private List<AplicacionXNivelXActividadTipo> aplicacionXNivelXActividadTipos = new ArrayList<AplicacionXNivelXActividadTipo>(0);

	public ActividadTipo() {
	}

	public ActividadTipo(Integer id) {
		this.id = id;
	}

	public ActividadTipo(Integer id, String nombre,
			List<AplicacionXNivelXActividadTipo> aplicacionXNivelXActividadTipos) {
		this.id = id;
		this.nombre = nombre;
		this.aplicacionXNivelXActividadTipos = aplicacionXNivelXActividadTipos;
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

	public List<AplicacionXNivelXActividadTipo> getAplicacionXNivelXActividadTipos() {
		return this.aplicacionXNivelXActividadTipos;
	}

	public void setAplicacionXNivelXActividadTipos(
			List<AplicacionXNivelXActividadTipo> aplicacionXNivelXActividadTipos) {
		this.aplicacionXNivelXActividadTipos = aplicacionXNivelXActividadTipos;
	}

	public ActividadTipoDTO getActividadTipoDTO() {
		ActividadTipoDTO atdto = new ActividadTipoDTO();
		atdto.setId(id);
		atdto.setNombre(nombre);
		return atdto;
	}
}
