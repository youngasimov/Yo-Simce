package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;

public class TipoContingenciaDTO implements Serializable {

	private Integer id;
	private String contingencia;
	
	public TipoContingenciaDTO() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContingencia() {
		return contingencia;
	}

	public void setContingencia(String contingencia) {
		this.contingencia = contingencia;
	}
}
