package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;

import com.google.gwt.view.client.ProvidesKey;

public class MaterialDefectuosoDTO implements Serializable {

	public static final ProvidesKey<MaterialDefectuosoDTO> KEY_PROVIDER = new ProvidesKey<MaterialDefectuosoDTO>() {

		@Override
		public Object getKey(MaterialDefectuosoDTO item) {
			return (item == null) ? null : item.getIdMaterial();
		}
	};
	
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
