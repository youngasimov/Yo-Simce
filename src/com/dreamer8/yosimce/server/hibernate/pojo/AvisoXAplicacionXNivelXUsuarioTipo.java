package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 16-08-2013 05:13:17 AM by Hibernate Tools 3.4.0.CR1

/**
 * AvisoXAplicacionXNivelXUsuarioTipo generated by hbm2java
 */
public class AvisoXAplicacionXNivelXUsuarioTipo implements java.io.Serializable {

	private Integer id;
	private AplicacionXNivelXUsuarioTipo aplicacionXNivelXUsuarioTipo;
	private Aviso aviso;

	public AvisoXAplicacionXNivelXUsuarioTipo() {
	}

	public AvisoXAplicacionXNivelXUsuarioTipo(Integer id) {
		this.id = id;
	}

	public AvisoXAplicacionXNivelXUsuarioTipo(Integer id,
			AplicacionXNivelXUsuarioTipo aplicacionXNivelXUsuarioTipo,
			Aviso aviso) {
		this.id = id;
		this.aplicacionXNivelXUsuarioTipo = aplicacionXNivelXUsuarioTipo;
		this.aviso = aviso;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AplicacionXNivelXUsuarioTipo getAplicacionXNivelXUsuarioTipo() {
		return this.aplicacionXNivelXUsuarioTipo;
	}

	public void setAplicacionXNivelXUsuarioTipo(
			AplicacionXNivelXUsuarioTipo aplicacionXNivelXUsuarioTipo) {
		this.aplicacionXNivelXUsuarioTipo = aplicacionXNivelXUsuarioTipo;
	}

	public Aviso getAviso() {
		return this.aviso;
	}

	public void setAviso(Aviso aviso) {
		this.aviso = aviso;
	}

}
