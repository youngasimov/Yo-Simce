package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;

public class PersonDTO implements Serializable {

	private static final long serialVersionUID = 1215490872269957714L;
	
	private String name;
	private String lastname;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
}
