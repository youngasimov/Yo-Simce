package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;

import com.google.gwt.view.client.ProvidesKey;

public class HistorialMaterialItemDTO implements Serializable {

	public static final ProvidesKey<HistorialMaterialItemDTO> KEY_PROVIDER = new ProvidesKey<HistorialMaterialItemDTO>() {

		@Override
		public Object getKey(HistorialMaterialItemDTO item) {
			return (item == null) ? null : item.getId();
		}
	};
	
	private Integer id;
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}
}
