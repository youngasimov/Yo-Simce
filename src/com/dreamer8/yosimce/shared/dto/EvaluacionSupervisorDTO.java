package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;
import java.util.Date;

import com.google.gwt.view.client.ProvidesKey;

@SuppressWarnings("serial")
public class EvaluacionSupervisorDTO implements Serializable {

	public static final ProvidesKey<EvaluacionSupervisorDTO> KEY_PROVIDER = new ProvidesKey<EvaluacionSupervisorDTO>() {

		@Override
		public Object getKey(EvaluacionSupervisorDTO item) {
			return (item == null) ? null : item.getSupervisor().getId()+"-"+item.getRbd()+"-"+item.getCurso();
		}
	};
	
	
	private UserDTO supervisor;
	
	private Integer presentacionPersonal;
	private Integer puntualidad;
	private Integer general;
	private String rbd;
	private String establecimiento;
	private String curso;
	private Date planificacionActividad;
	
	public EvaluacionSupervisorDTO(){}
	
	public UserDTO getSupervisor() {
		return supervisor;
	}
	public void setSupervisor(UserDTO supervisor) {
		this.supervisor = supervisor;
	}
	public Integer getPresentacionPersonal() {
		return presentacionPersonal;
	}
	public void setPresentacionPersonal(Integer presentacionPersonal) {
		this.presentacionPersonal = presentacionPersonal;
	}
	public Integer getPuntualidad() {
		return puntualidad;
	}
	public void setPuntualidad(Integer puntualidad) {
		this.puntualidad = puntualidad;
	}
	public Integer getGeneral() {
		return general;
	}
	public void setGeneral(Integer general) {
		this.general = general;
	}
	public String getRbd() {
		return rbd;
	}
	public void setRbd(String rbd) {
		this.rbd = rbd;
	}
	public String getEstablecimiento() {
		return establecimiento;
	}
	public void setEstablecimiento(String establecimiento) {
		this.establecimiento = establecimiento;
	}
	public String getCurso() {
		return curso;
	}
	public void setCurso(String curso) {
		this.curso = curso;
	}

	public Date getPlanificacionActividad() {
		return planificacionActividad;
	}

	public void setPlanificacionActividad(Date planificacionActividad) {
		this.planificacionActividad = planificacionActividad;
	}
}
