package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;
import java.util.ArrayList;

public class DetalleCursoDTO implements Serializable {

	private Integer id;
	private String rbd;
	private String establecimiento;
	private String region;
	private String comuna;
	private String curso;
	private String tipoEstablecimiento;
	private String direccion;
	private String centro;

	private UserDTO supervisor;
	private ArrayList<UserDTO> examinadores;

	private String cargoContacto;
	private String nombreContacto;
	private String emailContacto;
	private String telefonoContacto;

	private String nombreDirector;
	private String emailDirector;
	private String telefonoDirector;

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

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCentro() {
		return centro;
	}

	public void setCentro(String centro) {
		this.centro = centro;
	}

	public UserDTO getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(UserDTO supervisor) {
		this.supervisor = supervisor;
	}

	public String getCargoContacto() {
		return cargoContacto;
	}

	public void setCargoContacto(String cargoContacto) {
		this.cargoContacto = cargoContacto;
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

	public String getNombreDirector() {
		return nombreDirector;
	}

	public void setNombreDirector(String nombreDirector) {
		this.nombreDirector = nombreDirector;
	}

	public String getEmailDirector() {
		return emailDirector;
	}

	public void setEmailDirector(String emailDirector) {
		this.emailDirector = emailDirector;
	}

	public String getTelefonoDirector() {
		return telefonoDirector;
	}

	public void setTelefonoDirector(String telefonoDirector) {
		this.telefonoDirector = telefonoDirector;
	}

	/**
	 * @return the examinadores
	 */
	public ArrayList<UserDTO> getExaminadores() {
		return examinadores;
	}

	/**
	 * @param examinadores
	 *            the examinadores to set
	 */
	public void setExaminadores(ArrayList<UserDTO> examinadores) {
		this.examinadores = examinadores;
	}

}
