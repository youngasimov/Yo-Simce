package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;

public class CargoDTO implements Serializable {

	private Integer id;
	private String cargo;
	
	public CargoDTO() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
}