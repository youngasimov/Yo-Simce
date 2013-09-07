package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;


public class MaterialDefectuosoDTO implements Serializable {

	
	
	private String idMaterial;
	private EstadoSincronizacionDTO estado;
	
	public MaterialDefectuosoDTO() {
		// TODO Auto-generated constructor stub
	}

	public String getIdMaterial() {
		return idMaterial;
	}

	public void setIdMaterial(String idMaterial) {
		this.idMaterial = idMaterial;
	}

	public EstadoSincronizacionDTO getEstado() {
		return estado;
	}

	public void setEstado(EstadoSincronizacionDTO estado) {
		this.estado = estado;
	}
}
