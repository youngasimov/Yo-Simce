/**
 * 
 */
package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;

/**
 * @author jorge
 * 
 */
public class ContactoDTO implements Serializable {

	private String contactoNombre;
	private String contactoTelefono;
	private String contactoEmail;
	private CargoDTO cargo;

	/**
	 * 
	 */
	public ContactoDTO() {
		// TODO Auto-generated constructor stub
	}

	public String getContactoNombre() {
		return contactoNombre;
	}

	public void setContactoNombre(String contactoNombre) {
		this.contactoNombre = contactoNombre;
	}

	public String getContactoTelefono() {
		return contactoTelefono;
	}

	public void setContactoTelefono(String contactoTelefono) {
		this.contactoTelefono = contactoTelefono;
	}

	public String getContactoEmail() {
		return contactoEmail;
	}

	public void setContactoEmail(String contactoEmail) {
		this.contactoEmail = contactoEmail;
	}

	public CargoDTO getCargo() {
		return cargo;
	}

	public void setCargo(CargoDTO cargo) {
		this.cargo = cargo;
	}
}
