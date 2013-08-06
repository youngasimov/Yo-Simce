package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class AgendaDTO implements Serializable {

	private String establecimiento;
	private String rbd;
	private String curso;
	private ArrayList<AgendaItemDTO> items;

	/**
	 * 
	 */
	public AgendaDTO() {
		// TODO Auto-generated constructor stub
	}

	public String getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(String establecimiento) {
		this.establecimiento = establecimiento;
	}

	public String getRbd() {
		return rbd;
	}

	public void setRbd(String rbd) {
		this.rbd = rbd;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public ArrayList<AgendaItemDTO> getItems() {
		return items;
	}

	public void setItems(ArrayList<AgendaItemDTO> items) {
		this.items = items;
	}

}
