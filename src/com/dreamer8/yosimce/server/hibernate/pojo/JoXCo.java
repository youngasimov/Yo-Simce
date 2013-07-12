package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 12-07-2013 05:32:10 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * JoXCo generated by hbm2java
 */
public class JoXCo implements java.io.Serializable {

	private JoXCoId id;
	private Usuario usuario;
	private Co co;
	private Date fechaActivacion;
	private Date fechaTermino;
	private Boolean activo;

	public JoXCo() {
	}

	public JoXCo(JoXCoId id, Usuario usuario, Co co) {
		this.id = id;
		this.usuario = usuario;
		this.co = co;
	}

	public JoXCo(JoXCoId id, Usuario usuario, Co co, Date fechaActivacion,
			Date fechaTermino, Boolean activo) {
		this.id = id;
		this.usuario = usuario;
		this.co = co;
		this.fechaActivacion = fechaActivacion;
		this.fechaTermino = fechaTermino;
		this.activo = activo;
	}

	public JoXCoId getId() {
		return this.id;
	}

	public void setId(JoXCoId id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Co getCo() {
		return this.co;
	}

	public void setCo(Co co) {
		this.co = co;
	}

	public Date getFechaActivacion() {
		return this.fechaActivacion;
	}

	public void setFechaActivacion(Date fechaActivacion) {
		this.fechaActivacion = fechaActivacion;
	}

	public Date getFechaTermino() {
		return this.fechaTermino;
	}

	public void setFechaTermino(Date fechaTermino) {
		this.fechaTermino = fechaTermino;
	}

	public Boolean getActivo() {
		return this.activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

}
