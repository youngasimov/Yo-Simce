package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;

public class ContingenciaDTO implements Serializable {

	private TipoContingenciaDTO tipoContingencia;
	private String detalleContingencia;
	private Boolean inabilitante;
	
	public ContingenciaDTO() {
		// TODO Auto-generated constructor stub
	}

	public TipoContingenciaDTO getTipoContingencia() {
		return tipoContingencia;
	}

	public void setTipoContingencia(TipoContingenciaDTO tipoContingencia) {
		this.tipoContingencia = tipoContingencia;
	}

	public String getDetalleContingencia() {
		return detalleContingencia;
	}

	public void setDetalleContingencia(String detalleContingencia) {
		this.detalleContingencia = detalleContingencia;
	}

	public Boolean getInabilitante() {
		return inabilitante;
	}

	public void setInabilitante(Boolean inabilitante) {
		this.inabilitante = inabilitante;
	}
}
