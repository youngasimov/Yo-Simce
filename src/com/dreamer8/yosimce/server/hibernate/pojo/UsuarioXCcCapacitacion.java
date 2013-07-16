package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 16-07-2013 11:03:56 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * UsuarioXCcCapacitacion generated by hbm2java
 */
public class UsuarioXCcCapacitacion implements java.io.Serializable {

	private Integer id;
	private Usuario usuario;
	private CcCapacitacion ccCapacitacion;
	private Boolean asistencia;
	private Date createdAt;
	private Date horaAcceso;
	private Integer puntaje;

	public UsuarioXCcCapacitacion() {
	}

	public UsuarioXCcCapacitacion(Integer id) {
		this.id = id;
	}

	public UsuarioXCcCapacitacion(Integer id, Usuario usuario,
			CcCapacitacion ccCapacitacion, Boolean asistencia, Date createdAt,
			Date horaAcceso, Integer puntaje) {
		this.id = id;
		this.usuario = usuario;
		this.ccCapacitacion = ccCapacitacion;
		this.asistencia = asistencia;
		this.createdAt = createdAt;
		this.horaAcceso = horaAcceso;
		this.puntaje = puntaje;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public CcCapacitacion getCcCapacitacion() {
		return this.ccCapacitacion;
	}

	public void setCcCapacitacion(CcCapacitacion ccCapacitacion) {
		this.ccCapacitacion = ccCapacitacion;
	}

	public Boolean getAsistencia() {
		return this.asistencia;
	}

	public void setAsistencia(Boolean asistencia) {
		this.asistencia = asistencia;
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getHoraAcceso() {
		return this.horaAcceso;
	}

	public void setHoraAcceso(Date horaAcceso) {
		this.horaAcceso = horaAcceso;
	}

	public Integer getPuntaje() {
		return this.puntaje;
	}

	public void setPuntaje(Integer puntaje) {
		this.puntaje = puntaje;
	}

}
