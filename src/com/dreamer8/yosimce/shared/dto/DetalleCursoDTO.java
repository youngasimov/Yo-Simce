package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;

public class DetalleCursoDTO implements Serializable {

	
	private Integer id;
	private String rbd;
	private String establecimiento;
	private String region;
	private String comuna;
	private String curso;
	private String tipoEstablecimiento;
	
	private UserDTO supervisor;
	private UserDTO examinador;
	private UserDTO examinador2;
	
	private String nombreContacto;	
	private String emailContacto;
	private String telefonoContacto;
	
	public DetalleCursoDTO() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRbd() {
		return rbd;
	}

	public void setRbd(String rbd) {
		this.rbd = rbd;
	}

	public String getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(String establecimiento) {
		this.establecimiento = establecimiento;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getComuna() {
		return comuna;
	}

	public void setComuna(String comuna) {
		this.comuna = comuna;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public String getTipoEstablecimiento() {
		return tipoEstablecimiento;
	}

	public void setTipoEstablecimiento(String tipoEstablecimiento) {
		this.tipoEstablecimiento = tipoEstablecimiento;
	}

	public UserDTO getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(UserDTO supervisor) {
		this.supervisor = supervisor;
	}

	public UserDTO getExaminador() {
		return examinador;
	}

	public void setExaminador(UserDTO examinador) {
		this.examinador = examinador;
	}

	public UserDTO getExaminador2() {
		return examinador2;
	}

	public void setExaminador2(UserDTO examinador2) {
		this.examinador2 = examinador2;
	}

	public String getNombreContacto() {
		return nombreContacto;
	}

	public void setNombreContacto(String nombreContacto) {
		this.nombreContacto = nombreContacto;
	}

	public String getEmailContacto() {
		return emailContacto;
	}

	public void setEmailContacto(String emailContacto) {
		this.emailContacto = emailContacto;
	}

	public String getTelefonoContacto() {
		return telefonoContacto;
	}

	public void setTelefonoContacto(String telefonoContacto) {
		this.telefonoContacto = telefonoContacto;
	}
}
