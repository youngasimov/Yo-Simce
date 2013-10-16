package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;

import com.google.gwt.view.client.ProvidesKey;

@SuppressWarnings("serial")
public class EvaluacionSuplenteDTO implements Serializable {

	public static final int UPDATED = 0;
	public static final int UPDATING = 1;
	public static final int ERROR = 2;
	
	public static final ProvidesKey<EvaluacionSuplenteDTO> KEY_PROVIDER = new ProvidesKey<EvaluacionSuplenteDTO>() {

		@Override
		public Object getKey(EvaluacionSuplenteDTO item) {
			return (item == null) ? null : item.getSuplente().getId();
		}
	};

	public EvaluacionSuplenteDTO() {
		// TODO Auto-generated constructor stub
	}

	private UserDTO suplente;
	private String co;
	// private Integer presentacionPersonal;
	// private Integer puntualidad;
	private Integer general;
	private Boolean presente;
	
	//para uso en cliente solamente
	private Integer sinc;

	public UserDTO getSuplente() {
		return suplente;
	}

	public void setSuplente(UserDTO suplente) {
		this.suplente = suplente;
	}

	public String getCo() {
		return co;
	}

	public void setCo(String co) {
		this.co = co;
	}

	// public Integer getPresentacionPersonal() {
	// return presentacionPersonal;
	// }
	// public void setPresentacionPersonal(Integer presentacionPersonal) {
	// this.presentacionPersonal = presentacionPersonal;
	// }
	// public Integer getPuntualidad() {
	// return puntualidad;
	// }
	// public void setPuntualidad(Integer puntualidad) {
	// this.puntualidad = puntualidad;
	// }
	public Integer getGeneral() {
		return general;
	}

	public void setGeneral(Integer general) {
		this.general = general;
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
	
	
	
	

}
