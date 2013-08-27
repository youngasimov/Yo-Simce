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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((contingencia == null) ? 0 : contingencia.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TipoContingenciaDTO other = (TipoContingenciaDTO) obj;
		if (contingencia == null) {
			if (other.contingencia != null)
				return false;
		} else if (!contingencia.equals(other.contingencia))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
