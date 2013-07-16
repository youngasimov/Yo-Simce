package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;

public class UserDTO extends PersonDTO implements Serializable {

	private static final long serialVersionUID = -891784091994533540L;
	private String username;

	public UserDTO() {
		super();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
