package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;

import com.google.gwt.view.client.ProvidesKey;

public class ActividadPreviewDTO implements Serializable{

	public static final ProvidesKey<ActividadPreviewDTO> KEY_PROVIDER = new ProvidesKey<ActividadPreviewDTO>() {

		@Override
		public Object getKey(ActividadPreviewDTO item) {
			return (item == null) ? null : item.getActividadId();
		}
	};
	
	private Integer actividadId;
	
	private Integer cursoId;
	
	
	public ActividadPreviewDTO(){}

	public Integer getActividadId() {
		return actividadId;
	}

	public void setActividadId(Integer actividadId) {
		this.actividadId = actividadId;
	}

	public Integer getCursoId() {
		return cursoId;
	}

	public void setCursoId(Integer cursoId) {
		this.cursoId = cursoId;
	}
	
	
}
