package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 12-07-2013 05:32:10 AM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.List;

/**
 * Comuna generated by hbm2java
 */
public class Comuna implements java.io.Serializable {

	private Integer id;
	private Provincia provincia;
	private String nombre;
	private List<Cc> ccs = new ArrayList<Cc>(0);
	private List<Establecimiento> establecimientos = new ArrayList<Establecimiento>(0);
	private List<Co> cos = new ArrayList<Co>(0);
	private List<Usuario> usuarios = new ArrayList<Usuario>(0);
	private List usuarios_1 = new ArrayList(0);
	private List<Transporte> transportes = new ArrayList<Transporte>(0);
	private List ccs_1 = new ArrayList(0);

	public Comuna() {
	}

	public Comuna(Integer id) {
		this.id = id;
	}

	public Comuna(Integer id, Provincia provincia, String nombre, List<Cc> ccs,
			List<Establecimiento> establecimientos, List<Co> cos, List<Usuario> usuarios, List usuarios_1,
			List<Transporte> transportes, List ccs_1) {
		this.id = id;
		this.provincia = provincia;
		this.nombre = nombre;
		this.ccs = ccs;
		this.establecimientos = establecimientos;
		this.cos = cos;
		this.usuarios = usuarios;
		this.usuarios_1 = usuarios_1;
		this.transportes = transportes;
		this.ccs_1 = ccs_1;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Provincia getProvincia() {
		return this.provincia;
	}

	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Cc> getCcs() {
		return this.ccs;
	}

	public void setCcs(List<Cc> ccs) {
		this.ccs = ccs;
	}

	public List<Establecimiento> getEstablecimientos() {
		return this.establecimientos;
	}

	public void setEstablecimientos(List<Establecimiento> establecimientos) {
		this.establecimientos = establecimientos;
	}

	public List<Co> getCos() {
		return this.cos;
	}

	public void setCos(List<Co> cos) {
		this.cos = cos;
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List getUsuarios_1() {
		return this.usuarios_1;
	}

	public void setUsuarios_1(List usuarios_1) {
		this.usuarios_1 = usuarios_1;
	}

	public List<Transporte> getTransportes() {
		return this.transportes;
	}

	public void setTransportes(List<Transporte> transportes) {
		this.transportes = transportes;
	}

	public List getCcs_1() {
		return this.ccs_1;
	}

	public void setCcs_1(List ccs_1) {
		this.ccs_1 = ccs_1;
	}

}
