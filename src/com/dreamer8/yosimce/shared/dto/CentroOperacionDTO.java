package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CentroOperacionDTO implements Serializable {

	
	private Integer id;
	private Integer idRegion;
	private Integer idComuna;
	private Integer idZona;
	private String nombre;
	private String nombreJefeCentro;
	private String telefonoJefeCentro;
	
	public CentroOperacionDTO() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdRegion() {
		return idRegion;
	}

	public void setIdRegion(Integer idRegion) {
		this.idRegion = idRegion;
	}

	public Integer getIdComuna() {
		return idComuna;
	}

	public void setIdComuna(Integer idComuna) {
		this.idComuna = idComuna;
	}

	public Integer getIdZona() {
		return idZona;
	}

	public void setIdZona(Integer idZona) {
		this.idZona = idZona;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombreJefeCentro() {
		return nombreJefeCentro;
	}

	public void setNombreJefeCentro(String nombreJefeCentro) {
		this.nombreJefeCentro = nombreJefeCentro;
	}

	public String getTelefonoJefeCentro() {
		return telefonoJefeCentro;
	}

	public void setTelefonoJefeCentro(String telefonoJefeCentro) {
		this.telefonoJefeCentro = telefonoJefeCentro;
	}
}
