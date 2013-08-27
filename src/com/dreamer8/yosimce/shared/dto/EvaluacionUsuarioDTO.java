package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;

import com.google.gwt.view.client.ProvidesKey;

public class EvaluacionUsuarioDTO implements Serializable {

	
	public static final ProvidesKey<EvaluacionUsuarioDTO> KEY_PROVIDER = new ProvidesKey<EvaluacionUsuarioDTO>() {

		@Override
		public Object getKey(EvaluacionUsuarioDTO item) {
			return (item == null) ? null : item.getUsuario().getId();
		}
	};
	
	private UserDTO usuario;
	
	Integer presentacionPersonal;
	Integer puntualidad;
	Integer formulario;
	Integer general;
	
	public EvaluacionUsuarioDTO() {
		// TODO Auto-generated constructor stub
	}

	public UserDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UserDTO usuario) {
		this.usuario = usuario;
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

	public Integer getFormulario() {
		return formulario;
	}

	public void setFormulario(Integer formulario) {
		this.formulario = formulario;
	}

	public Integer getGeneral() {
		return general;
	}

	public void setGeneral(Integer general) {
		this.general = general;
	}
	
	

}
