package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 01-08-2013 04:51:27 AM by Hibernate Tools 3.4.0.CR1

/**
 * ArchvicoXAplicacionXNivelXUsuarioTipo generated by hbm2java
 */
public class ArchvicoXAplicacionXNivelXUsuarioTipo implements
		java.io.Serializable {

	private int id;
	private Archivo archivo;
	private AplicacionXNivelXUsuarioTipo aplicacionXNivelXUsuarioTipo;

	public ArchvicoXAplicacionXNivelXUsuarioTipo() {
	}

	public ArchvicoXAplicacionXNivelXUsuarioTipo(int id) {
		this.id = id;
	}

	public ArchvicoXAplicacionXNivelXUsuarioTipo(int id, Archivo archivo,
			AplicacionXNivelXUsuarioTipo aplicacionXNivelXUsuarioTipo) {
		this.id = id;
		this.archivo = archivo;
		this.aplicacionXNivelXUsuarioTipo = aplicacionXNivelXUsuarioTipo;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Archivo getArchivo() {
		return this.archivo;
	}

	public void setArchivo(Archivo archivo) {
		this.archivo = archivo;
	}

	public AplicacionXNivelXUsuarioTipo getAplicacionXNivelXUsuarioTipo() {
		return this.aplicacionXNivelXUsuarioTipo;
	}

	public void setAplicacionXNivelXUsuarioTipo(
			AplicacionXNivelXUsuarioTipo aplicacionXNivelXUsuarioTipo) {
		this.aplicacionXNivelXUsuarioTipo = aplicacionXNivelXUsuarioTipo;
	}

}
