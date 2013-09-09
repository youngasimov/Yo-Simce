package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;

import com.google.gwt.view.client.ProvidesKey;

public class MaterialDTO implements Serializable {

	public static final ProvidesKey<MaterialDTO> KEY_PROVIDER = new ProvidesKey<MaterialDTO>() {

		@Override
		public Object getKey(MaterialDTO item) {
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
