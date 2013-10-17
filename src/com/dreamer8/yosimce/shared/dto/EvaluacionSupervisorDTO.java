package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;

import com.google.gwt.view.client.ProvidesKey;

@SuppressWarnings("serial")
public class EvaluacionSupervisorDTO implements Serializable {

	public static final int UPDATED = 0;
	public static final int UPDATING = 1;
	public static final int ERROR = 2;
	
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
	private String planificacionActividad;
	private String co;
	private Boolean presente;
	
	//para uso en cliente solamente
	private Integer sinc;
	private boolean even;
	
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

	public String getPlanificacionActividad() {
		return planificacionActividad;
	}

	public void setPlanificacionActividad(String planificacionActividad) {
		this.planificacionActividad = planificacionActividad;
	}

	public Boolean getPresente() {
		return presente;
	}

	public void setPresente(Boolean presente) {
		this.presente = presente;
	}
	
	public Integer getSinc() {
		return sinc;
	}

	public void setSinc(Integer sinc) {
		this.sinc = sinc;
	}

	public String getCo() {
		return co;
	}

	public void setCo(String co) {
		this.co = co;
	}

	public boolean isEven() {
		return even;
	}

	public void setEven(boolean even) {
		this.even = even;
	}
}
