package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AgendaPreviewDTO implements Serializable {

	private Integer cursoId;
	private String establecimientoName;
	private String rbd;
	private String codigoPisa;
	private String curso;
	private TipoEstablecimientoDTO tipoEstablecimiento;
	private String regionName;
	private String comunaName;
	private AgendaItemDTO agendaItemActual;
	private UserDTO examinador;
	private UserDTO supervisor;
	private Integer alumnosTotal;
	private String nombreContacto;
	private String telefonoContacto;
	private String mailContacto;

	/**
	 * 
	 */
	public AgendaPreviewDTO() {
		// TODO Auto-generated constructor stub
	}

	public Integer getCursoId() {
		return cursoId;
	}

	public void setCursoId(Integer cursoId) {
		this.cursoId = cursoId;
	}

	public String getEstablecimientoName() {
		return establecimientoName;
	}

	public void setEstablecimientoName(String establecimientoName) {
		this.establecimientoName = establecimientoName;
	}

	public String getRbd() {
		return rbd;
	}

	public void setRbd(String rbd) {
		this.rbd = rbd;
	}

	public TipoEstablecimientoDTO getTipoEstablecimiento() {
		return tipoEstablecimiento;
	}

	public void setTipoEstablecimiento(
			TipoEstablecimientoDTO tipoEstablecimiento) {
		this.tipoEstablecimiento = tipoEstablecimiento;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getComunaName() {
		return comunaName;
	}

	public void setComunaName(String comunaName) {
		this.comunaName = comunaName;
	}

	public AgendaItemDTO getAgendaItemActual() {
		return agendaItemActual;
	}

	public void setAgendaItemActual(AgendaItemDTO agendaItemActual) {
		this.agendaItemActual = agendaItemActual;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public UserDTO getExaminador() {
		return examinador;
	}

	public void setExaminador(UserDTO examinador) {
		this.examinador = examinador;
	}

	public UserDTO getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(UserDTO supervisor) {
		this.supervisor = supervisor;
	}

	public Integer getTotalAlumnos() {
		return alumnosTotal;
	}

	public void setTotalAlumnos(Integer alumnos) {
		this.alumnosTotal = alumnos;
	}

	public String getNombreContacto() {
		return nombreContacto;
	}

	public void setNombreContacto(String nombreContacto) {
		this.nombreContacto = nombreContacto;
	}

	public String getTelefonoContacto() {
		return telefonoContacto;
	}

	public void setTelefonoContacto(String telefonoContacto) {
		this.telefonoContacto = telefonoContacto;
	}

	public String getMailContacto() {
		return mailContacto;
	}

	public void setMailContacto(String mailContacto) {
		this.mailContacto = mailContacto;
	}

	public String getCodigoPisa() {
		return codigoPisa;
	}

	public void setCodigoPisa(String codigoPisa) {
		this.codigoPisa = codigoPisa;
	}

}
