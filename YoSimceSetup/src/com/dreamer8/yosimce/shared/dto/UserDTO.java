package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;

public class UserDTO extends PersonDTO implements Serializable {

	private String username;
	private TipoUsuarioDTO tipo;
	private String telefono;

	public UserDTO() {
		super();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public TipoUsuarioDTO getTipo() {
		return tipo;
	}

	public void setTipo(TipoUsuarioDTO tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono
	 *            the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

}
