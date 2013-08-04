package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;

import com.google.gwt.view.client.ProvidesKey;

public class UserDTO extends PersonDTO implements Serializable {

	private static final long serialVersionUID = -891784091994533540L;
	
	public static final ProvidesKey<UserDTO> KEY_PROVIDER = new ProvidesKey<UserDTO>() {

		@Override
		public Object getKey(UserDTO item) {
			return (item == null) ? null : item.getId();
		}
	};
	
	private String username;
	private TipoUsuarioDTO tipo;

	public UserDTO() {
		super();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public TipoUsuarioDTO getTipo() {
		return tipo;
	}

	public void setTipo(TipoUsuarioDTO tipo) {
		this.tipo = tipo;
	}
}
