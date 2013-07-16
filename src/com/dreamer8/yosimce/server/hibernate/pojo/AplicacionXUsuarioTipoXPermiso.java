package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 16-07-2013 11:03:56 PM by Hibernate Tools 3.4.0.CR1

/**
 * AplicacionXUsuarioTipoXPermiso generated by hbm2java
 */
public class AplicacionXUsuarioTipoXPermiso implements java.io.Serializable {

	private Integer id;
	private AplicacionXUsuarioTipo aplicacionXUsuarioTipo;
	private Permiso permiso;
	private Boolean acceso;

	public AplicacionXUsuarioTipoXPermiso() {
	}

	public AplicacionXUsuarioTipoXPermiso(Integer id) {
		this.id = id;
	}

	public AplicacionXUsuarioTipoXPermiso(Integer id,
			AplicacionXUsuarioTipo aplicacionXUsuarioTipo, Permiso permiso,
			Boolean acceso) {
		this.id = id;
		this.aplicacionXUsuarioTipo = aplicacionXUsuarioTipo;
		this.permiso = permiso;
		this.acceso = acceso;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AplicacionXUsuarioTipo getAplicacionXUsuarioTipo() {
		return this.aplicacionXUsuarioTipo;
	}

	public void setAplicacionXUsuarioTipo(
			AplicacionXUsuarioTipo aplicacionXUsuarioTipo) {
		this.aplicacionXUsuarioTipo = aplicacionXUsuarioTipo;
	}

	public Permiso getPermiso() {
		return this.permiso;
	}

	public void setPermiso(Permiso permiso) {
		this.permiso = permiso;
	}

	public Boolean getAcceso() {
		return this.acceso;
	}

	public void setAcceso(Boolean acceso) {
		this.acceso = acceso;
	}

}
