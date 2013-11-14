package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class EvaluacionSuplenteDTO implements Serializable {

	public EvaluacionSuplenteDTO() {
		// TODO Auto-generated constructor stub
	}

	private UserDTO suplente;
	private String co;
	// private Integer presentacionPersonal;
	// private Integer puntualidad;
	private Integer general;
	private Boolean presente;

	public UserDTO getSuplente() {
		return suplente;
	}

	public void setSuplente(UserDTO suplente) {
		this.suplente = suplente;
	}

	public String getCo() {
		return co;
	}

	public void setCo(String co) {
		this.co = co;
	}

	// public Integer getPresentacionPersonal() {
	// return presentacionPersonal;
	// }
	// public void setPresentacionPersonal(Integer presentacionPersonal) {
	// this.presentacionPersonal = presentacionPersonal;
	// }
	// public Integer getPuntualidad() {
	// return puntualidad;
	// }
	// public void setPuntualidad(Integer puntualidad) {
	// this.puntualidad = puntualidad;
	// }
	public Integer getGeneral() {
		return general;
	}

	public void setGeneral(Integer general) {
		this.general = general;
	}

	public Boolean getPresente() {
		return presente;
	}

	public void setPresente(Boolean presente) {
		this.presente = presente;
	}

}
