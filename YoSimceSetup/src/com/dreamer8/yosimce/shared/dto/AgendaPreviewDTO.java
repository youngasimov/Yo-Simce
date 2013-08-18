package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AgendaPreviewDTO implements Serializable {



	private Integer establecimientoId;
	private String establecimientoName;
	private String rbd;
	private String curso;
	private TipoEstablecimientoDTO tipoEstablecimiento;
	private String regionName;
	private String comunaName;
	private AgendaItemDTO agendaItemActual;

	/**
	 * 
	 */
	public AgendaPreviewDTO() {
		// TODO Auto-generated constructor stub
	}

	public Integer getEstablecimientoId() {
		return establecimientoId;
	}

	public void setEstablecimientoId(Integer establecimientoId) {
		this.establecimientoId = establecimientoId;
	}

	public String getEstablecimientoName() {
		return establecimientoName;
	}

	public void setEstablecimientoName(String establecimientoName) {
		this.establecimientoName = establecimientoName;
	}

	public String getRbd() {
		return rbd;
	}

	public void setRbd(String rbd) {
		this.rbd = rbd;
	}

	public TipoEstablecimientoDTO getTipoEstablecimiento() {
		return tipoEstablecimiento;
	}

	public void setTipoEstablecimiento(
			TipoEstablecimientoDTO tipoEstablecimiento) {
		this.tipoEstablecimiento = tipoEstablecimiento;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getComunaName() {
		return comunaName;
	}

	public void setComunaName(String comunaName) {
		this.comunaName = comunaName;
	}

	public AgendaItemDTO getAgendaItemActual() {
		return agendaItemActual;
	}

	public void setAgendaItemActual(AgendaItemDTO agendaItemActual) {
		this.agendaItemActual = agendaItemActual;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}
}
