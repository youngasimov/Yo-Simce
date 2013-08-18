package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;

public class TipoUsuarioDTO implements Serializable {

	private Integer id;
	private String tipoUsuario;
	private String tipoEmplazamientoAsociado;

	public TipoUsuarioDTO() {
		super();
	}

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

	/**
	 * @return the tipoEmplazamientoAsociado
	 */
	public String getTipoEmplazamientoAsociado() {
		return tipoEmplazamientoAsociado;
	}

	/**
	 * @param tipoEmplazamientoAsociado
	 *            the tipoEmplazamientoAsociado to set
	 */
	public void setTipoEmplazamientoAsociado(String tipoEmplazamientoAsociado) {
		this.tipoEmplazamientoAsociado = tipoEmplazamientoAsociado;
	}

}
