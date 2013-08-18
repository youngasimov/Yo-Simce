package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;


public class ActividadPreviewDTO implements Serializable{


	
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
