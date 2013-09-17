package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class EstadoAgendaDTO implements Serializable {

	public static final String SIN_INFORMACION = "Sin Información";
	public static final String POR_CONFIRMAR = "Por Confirmar";
	public static final String CONFIRMADO = "Confirmado";
	public static final String REALIZADA = "Realizada";
	public static final String ANULADA = "Anulada";
	public static final String CONFIRMADO_CON_CAMBIOS = "Confirmado con Cambios";

	private Integer id;
	private String estado;

	public EstadoAgendaDTO() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
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
		EstadoAgendaDTO other = (EstadoAgendaDTO) obj;
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
