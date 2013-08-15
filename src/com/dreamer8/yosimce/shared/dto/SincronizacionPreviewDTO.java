package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;

import com.google.gwt.view.client.ProvidesKey;

public class SincronizacionPreviewDTO implements Serializable {
	
	public static final ProvidesKey<SincronizacionPreviewDTO> KEY_PROVIDER = new ProvidesKey<SincronizacionPreviewDTO>() {

		@Override
		public Object getKey(SincronizacionPreviewDTO item) {
			return (item == null) ? null : item.getIdSincronizacion();
		}
	};
	
	private Integer idSincronizacion;
	
	public SincronizacionPreviewDTO() {
		super();
	}

	public Integer getIdSincronizacion() {
		return idSincronizacion;
	}

	public void setIdSincronizacion(Integer idSincronizacion) {
		this.idSincronizacion = idSincronizacion;
	}
}
