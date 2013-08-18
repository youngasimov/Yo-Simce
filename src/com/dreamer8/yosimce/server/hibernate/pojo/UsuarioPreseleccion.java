package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 16-08-2013 05:13:17 AM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;

/**
 * UsuarioPreseleccion generated by hbm2java
 */
public class UsuarioPreseleccion implements java.io.Serializable {

	private Integer id;
	private UsuarioTipo usuarioTipo;
	private UsuarioXAplicacionXNivel usuarioXAplicacionXNivel;
	private Co co;
	private BigDecimal puntajeTotal;
	private boolean preseleccion;
	private Date fechaPreseleccion;

	public UsuarioPreseleccion() {
	}

	public UsuarioPreseleccion(Integer id, boolean preseleccion) {
		this.id = id;
		this.preseleccion = preseleccion;
	}

	public UsuarioPreseleccion(Integer id, UsuarioTipo usuarioTipo,
			UsuarioXAplicacionXNivel usuarioXAplicacionXNivel, Co co,
			BigDecimal puntajeTotal, boolean preseleccion,
			Date fechaPreseleccion) {
		this.id = id;
		this.usuarioTipo = usuarioTipo;
		this.usuarioXAplicacionXNivel = usuarioXAplicacionXNivel;
		this.co = co;
		this.puntajeTotal = puntajeTotal;
		this.preseleccion = preseleccion;
		this.fechaPreseleccion = fechaPreseleccion;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UsuarioTipo getUsuarioTipo() {
		return this.usuarioTipo;
	}

	public void setUsuarioTipo(UsuarioTipo usuarioTipo) {
		this.usuarioTipo = usuarioTipo;
	}

	public UsuarioXAplicacionXNivel getUsuarioXAplicacionXNivel() {
		return this.usuarioXAplicacionXNivel;
	}

	public void setUsuarioXAplicacionXNivel(
			UsuarioXAplicacionXNivel usuarioXAplicacionXNivel) {
		this.usuarioXAplicacionXNivel = usuarioXAplicacionXNivel;
	}

	public Co getCo() {
		return this.co;
	}

	public void setCo(Co co) {
		this.co = co;
	}

	public BigDecimal getPuntajeTotal() {
		return this.puntajeTotal;
	}

	public void setPuntajeTotal(BigDecimal puntajeTotal) {
		this.puntajeTotal = puntajeTotal;
	}

	public boolean isPreseleccion() {
		return this.preseleccion;
	}

	public void setPreseleccion(boolean preseleccion) {
		this.preseleccion = preseleccion;
	}

	public Date getFechaPreseleccion() {
		return this.fechaPreseleccion;
	}

	public void setFechaPreseleccion(Date fechaPreseleccion) {
		this.fechaPreseleccion = fechaPreseleccion;
	}

}
