package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 16-08-2013 05:13:17 AM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * CcModulo generated by hbm2java
 */
public class CcModulo implements java.io.Serializable {

	private Integer id;
	private String nombre;
	private Date horaInicio;
	private Date horaTermino;
	private List<CcCapacitacion> ccCapacitacions = new ArrayList<CcCapacitacion>(0);

	public CcModulo() {
	}

	public CcModulo(Integer id) {
		this.id = id;
	}

	public CcModulo(Integer id, String nombre, Date horaInicio, Date horaTermino,
			List<CcCapacitacion> ccCapacitacions) {
		this.id = id;
		this.nombre = nombre;
		this.horaInicio = horaInicio;
		this.horaTermino = horaTermino;
		this.ccCapacitacions = ccCapacitacions;
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

	public Date getHoraInicio() {
		return this.horaInicio;
	}

	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
	}

	public Date getHoraTermino() {
		return this.horaTermino;
	}

	public void setHoraTermino(Date horaTermino) {
		this.horaTermino = horaTermino;
	}

	public List<CcCapacitacion> getCcCapacitacions() {
		return this.ccCapacitacions;
	}

	public void setCcCapacitacions(List<CcCapacitacion> ccCapacitacions) {
		this.ccCapacitacions = ccCapacitacions;
	}

}
