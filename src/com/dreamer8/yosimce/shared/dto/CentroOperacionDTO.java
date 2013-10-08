package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;


@SuppressWarnings("serial")
public class CentroOperacionDTO implements Serializable {
	
	private Integer id;
	private Integer idRegion;
	private Integer idComuna;
	private Integer idZona;
	private String nombre;
	private Long longitud;
	private Long latitud;
	private String nombreJefeCentro;
	private String telefonoJefeCentro;
	private Integer enCentro;
	private Integer enEstablecimiento;
	private Integer enImprenta;
	private Integer enMinisterio;
	
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

	public Long getLongitud() {
		return longitud;
	}

	public void setLongitud(Long longitud) {
		this.longitud = longitud;
	}

	public Long getLatitud() {
		return latitud;
	}

	public void setLatitud(Long latitud) {
		this.latitud = latitud;
	}

	public Integer getEnCentro() {
		return enCentro;
	}

	public void setEnCentro(Integer enCentro) {
		this.enCentro = enCentro;
	}

	public Integer getEnEstablecimiento() {
		return enEstablecimiento;
	}

	public void setEnEstablecimiento(Integer enEstablecimiento) {
		this.enEstablecimiento = enEstablecimiento;
	}

	public Integer getEnImprenta() {
		return enImprenta;
	}

	public void setEnImprenta(Integer enImprenta) {
		this.enImprenta = enImprenta;
	}

	public Integer getEnMinisterio() {
		return enMinisterio;
	}

	public void setEnMinisterio(Integer enMinisterio) {
		this.enMinisterio = enMinisterio;
	}
}
