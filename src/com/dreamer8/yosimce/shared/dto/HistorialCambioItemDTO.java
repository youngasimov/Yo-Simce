package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;
import java.util.Date;

public class HistorialCambioItemDTO implements Serializable {

	private UserDTO usuario;
	private String cambio;
	private Date fecha;
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
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}
