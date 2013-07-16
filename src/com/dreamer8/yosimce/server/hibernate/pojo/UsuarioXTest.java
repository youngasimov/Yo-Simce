package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 16-07-2013 11:03:56 PM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * UsuarioXTest generated by hbm2java
 */
public class UsuarioXTest implements java.io.Serializable {

	private Integer id;
	private Usuario usuario;
	private Test test;
	private Integer Integerento;
	private Date fechaInicio;
	private Date fechaTermino;
	private Integer buenas;
	private Integer omitidas;
	private Integer malas;
	private BigDecimal evaluacion;
	private List<UsuarioXTestRespuesta> usuarioXTestRespuestas = new ArrayList<UsuarioXTestRespuesta>(0);

	public UsuarioXTest() {
	}

	public UsuarioXTest(Integer id) {
		this.id = id;
	}

	public UsuarioXTest(Integer id, Usuario usuario, Test test, Integer Integerento,
			Date fechaInicio, Date fechaTermino, Integer buenas,
			Integer omitidas, Integer malas, BigDecimal evaluacion,
			List<UsuarioXTestRespuesta> usuarioXTestRespuestas) {
		this.id = id;
		this.usuario = usuario;
		this.test = test;
		this.Integerento = Integerento;
		this.fechaInicio = fechaInicio;
		this.fechaTermino = fechaTermino;
		this.buenas = buenas;
		this.omitidas = omitidas;
		this.malas = malas;
		this.evaluacion = evaluacion;
		this.usuarioXTestRespuestas = usuarioXTestRespuestas;
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

	public Test getTest() {
		return this.test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

	public Integer getIntento() {
		return this.Integerento;
	}

	public void setIntento(Integer Integerento) {
		this.Integerento = Integerento;
	}

	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaTermino() {
		return this.fechaTermino;
	}

	public void setFechaTermino(Date fechaTermino) {
		this.fechaTermino = fechaTermino;
	}

	public Integer getBuenas() {
		return this.buenas;
	}

	public void setBuenas(Integer buenas) {
		this.buenas = buenas;
	}

	public Integer getOmitidas() {
		return this.omitidas;
	}

	public void setOmitidas(Integer omitidas) {
		this.omitidas = omitidas;
	}

	public Integer getMalas() {
		return this.malas;
	}

	public void setMalas(Integer malas) {
		this.malas = malas;
	}

	public BigDecimal getEvaluacion() {
		return this.evaluacion;
	}

	public void setEvaluacion(BigDecimal evaluacion) {
		this.evaluacion = evaluacion;
	}

	public List<UsuarioXTestRespuesta> getUsuarioXTestRespuestas() {
		return this.usuarioXTestRespuestas;
	}

	public void setUsuarioXTestRespuestas(List<UsuarioXTestRespuesta> usuarioXTestRespuestas) {
		this.usuarioXTestRespuestas = usuarioXTestRespuestas;
	}

}
