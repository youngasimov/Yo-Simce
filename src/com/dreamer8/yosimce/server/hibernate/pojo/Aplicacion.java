package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 16-07-2013 11:03:56 PM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.List;

/**
 * Aplicacion generated by hbm2java
 */
public class Aplicacion implements java.io.Serializable {

	private Integer id;
	private String nombre;
	private Integer tiempoDetencionEstablecimiento;
	private List<CentroRegional> centroRegionals = new ArrayList<CentroRegional>(0);
	private List<AplicacionXNivel> aplicacionXNivels = new ArrayList<AplicacionXNivel>(0);
	private List<AplicacionXEstablecimiento> aplicacionXEstablecimientos = new ArrayList<AplicacionXEstablecimiento>(0);
	private List<FaseXAplicacion> faseXAplicacions = new ArrayList<FaseXAplicacion>(0);
	private List<AplicacionXUsuarioTipo> aplicacionXUsuarioTipos = new ArrayList<AplicacionXUsuarioTipo>(0);

	public Aplicacion() {
	}

	public Aplicacion(Integer id) {
		this.id = id;
	}

	public Aplicacion(Integer id, String nombre,
			Integer tiempoDetencionEstablecimiento, List<CentroRegional> centroRegionals,
			List<AplicacionXNivel> aplicacionXNivels, List<AplicacionXEstablecimiento> aplicacionXEstablecimientos,
			List<FaseXAplicacion> faseXAplicacions, List<AplicacionXUsuarioTipo> aplicacionXUsuarioTipos) {
		this.id = id;
		this.nombre = nombre;
		this.tiempoDetencionEstablecimiento = tiempoDetencionEstablecimiento;
		this.centroRegionals = centroRegionals;
		this.aplicacionXNivels = aplicacionXNivels;
		this.aplicacionXEstablecimientos = aplicacionXEstablecimientos;
		this.faseXAplicacions = faseXAplicacions;
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

	public Integer getTiempoDetencionEstablecimiento() {
		return this.tiempoDetencionEstablecimiento;
	}

	public void setTiempoDetencionEstablecimiento(
			Integer tiempoDetencionEstablecimiento) {
		this.tiempoDetencionEstablecimiento = tiempoDetencionEstablecimiento;
	}

	public List<CentroRegional> getCentroRegionals() {
		return this.centroRegionals;
	}

	public void setCentroRegionals(List<CentroRegional> centroRegionals) {
		this.centroRegionals = centroRegionals;
	}

	public List<AplicacionXNivel> getAplicacionXNivels() {
		return this.aplicacionXNivels;
	}

	public void setAplicacionXNivels(List<AplicacionXNivel> aplicacionXNivels) {
		this.aplicacionXNivels = aplicacionXNivels;
	}

	public List<AplicacionXEstablecimiento> getAplicacionXEstablecimientos() {
		return this.aplicacionXEstablecimientos;
	}

	public void setAplicacionXEstablecimientos(List<AplicacionXEstablecimiento> aplicacionXEstablecimientos) {
		this.aplicacionXEstablecimientos = aplicacionXEstablecimientos;
	}

	public List<FaseXAplicacion> getFaseXAplicacions() {
		return this.faseXAplicacions;
	}

	public void setFaseXAplicacions(List<FaseXAplicacion> faseXAplicacions) {
		this.faseXAplicacions = faseXAplicacions;
	}

	public List<AplicacionXUsuarioTipo> getAplicacionXUsuarioTipos() {
		return this.aplicacionXUsuarioTipos;
	}

	public void setAplicacionXUsuarioTipos(List<AplicacionXUsuarioTipo> aplicacionXUsuarioTipos) {
		this.aplicacionXUsuarioTipos = aplicacionXUsuarioTipos;
	}

}
