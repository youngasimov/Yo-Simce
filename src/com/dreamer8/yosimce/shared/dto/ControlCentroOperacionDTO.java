package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;

import com.google.gwt.view.client.ProvidesKey;

public class ControlCentroOperacionDTO implements Serializable{

	public static final String ESTADO_EMPTY = "empty";
	public static final String ESTADO_IDA = "ida";
	public static final String ESTADO_VUELTA = "vuelta";
	
	public static final ProvidesKey<ControlCentroOperacionDTO> KEY_PROVIDER = new ProvidesKey<ControlCentroOperacionDTO>() {

		@Override
		public Object getKey(ControlCentroOperacionDTO item) {
			return (item == null) ? null : item.getId();
		}
	};
	
	private int id;
	private String zona;
	private String co;
	private String jefeZona;
	private String jefeCentro;
	private EstadoControlDTO estado;
	
	public ControlCentroOperacionDTO() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public String getCo() {
		return co;
	}

	public void setCo(String co) {
		this.co = co;
	}

	public String getJefeZona() {
		return jefeZona;
	}

	public void setJefeZona(String jefeZona) {
		this.jefeZona = jefeZona;
	}

	public String getJefeCentro() {
		return jefeCentro;
	}

	public void setJefeCentro(String jefeCentro) {
		this.jefeCentro = jefeCentro;
	}

	public EstadoControlDTO getEstado() {
		return estado;
	}

	public void setEstado(EstadoControlDTO estado) {
		this.estado = estado;
	}
}
