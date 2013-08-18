package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 16-08-2013 05:13:17 AM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.List;

/**
 * EstablecimientoTipo generated by hbm2java
 */
public class EstablecimientoTipo implements java.io.Serializable {

	private Integer id;
	private String nombre;
	private String icono;
	private List<AplicacionXEstablecimiento> aplicacionXEstablecimientos = new ArrayList<AplicacionXEstablecimiento>(0);

	public EstablecimientoTipo() {
	}

	public EstablecimientoTipo(Integer id) {
		this.id = id;
	}

	public EstablecimientoTipo(Integer id, String nombre, String icono,
			List<AplicacionXEstablecimiento> aplicacionXEstablecimientos) {
		this.id = id;
		this.nombre = nombre;
		this.icono = icono;
		this.aplicacionXEstablecimientos = aplicacionXEstablecimientos;
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

	public String getIcono() {
		return this.icono;
	}

	public void setIcono(String icono) {
		this.icono = icono;
	}

	public List<AplicacionXEstablecimiento> getAplicacionXEstablecimientos() {
		return this.aplicacionXEstablecimientos;
	}

	public void setAplicacionXEstablecimientos(List<AplicacionXEstablecimiento> aplicacionXEstablecimientos) {
		this.aplicacionXEstablecimientos = aplicacionXEstablecimientos;
	}

}
