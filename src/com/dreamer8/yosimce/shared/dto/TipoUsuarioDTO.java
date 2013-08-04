package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;

public class TipoUsuarioDTO implements Serializable {

	private Integer id;
	private String tipoUsuario;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTipoUsuario() {
		return tipoUsuario;
	}
	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
}
