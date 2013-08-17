package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;

public class EstadoSincronizacionDTO implements Serializable {

	
	private Integer idEstadoSincronizacion;
	private String nombreEstado;
	
	public EstadoSincronizacionDTO() {
	}

	public Integer getIdEstadoSincronizacion() {
		return idEstadoSincronizacion;
	}

	public void setIdEstadoSincronizacion(Integer idEstadoSincronizacion) {
		this.idEstadoSincronizacion = idEstadoSincronizacion;
	}

	public String getNombreEstado() {
		return nombreEstado;
	}

	public void setNombreEstado(String nombreEstado) {
		this.nombreEstado = nombreEstado;
	}
}
