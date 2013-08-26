package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class EstadoAgendaDTO implements Serializable {

	public static final String SIN_INFORMACION = "Sin Información";
	public static final String POR_CONFIRMAR = "Por Confirmar";
	public static final String CONFIRMADO = "Confirmado";
	public static final String REALIZADA = "Realizada";
	public static final String ANULADA = "Anulada";

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

}
