package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;

public class HistorialCambioItemDTO implements Serializable {

	private UserDTO usuario;
	private String cambio;
	private String fecha;

	/**
	 * 
	 */
	public HistorialCambioItemDTO() {
		super();
	}

	public UserDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UserDTO usuario) {
		this.usuario = usuario;
	}

	public String getCambio() {
		return cambio;
	}

	public void setCambio(String cambio) {
		this.cambio = cambio;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
}
