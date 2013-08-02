package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 01-08-2013 04:51:27 AM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * Requisito generated by hbm2java
 */
public class Requisito implements java.io.Serializable {

	private int id;
	private Escala escala;
	private String nombre;
	private String pregunta;
	private Integer valorMinimo;
	private Integer valorMaximo;
	private Set requisitoXAplicacionXUsuarioTipos = new HashSet(0);
	private Set usuarioXRequisitos = new HashSet(0);

	public Requisito() {
	}

	public Requisito(int id) {
		this.id = id;
	}

	public Requisito(int id, Escala escala, String nombre, String pregunta,
			Integer valorMinimo, Integer valorMaximo,
			Set requisitoXAplicacionXUsuarioTipos, Set usuarioXRequisitos) {
		this.id = id;
		this.escala = escala;
		this.nombre = nombre;
		this.pregunta = pregunta;
		this.valorMinimo = valorMinimo;
		this.valorMaximo = valorMaximo;
		this.requisitoXAplicacionXUsuarioTipos = requisitoXAplicacionXUsuarioTipos;
		this.usuarioXRequisitos = usuarioXRequisitos;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Escala getEscala() {
		return this.escala;
	}

	public void setEscala(Escala escala) {
		this.escala = escala;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPregunta() {
		return this.pregunta;
	}

	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}

	public Integer getValorMinimo() {
		return this.valorMinimo;
	}

	public void setValorMinimo(Integer valorMinimo) {
		this.valorMinimo = valorMinimo;
	}

	public Integer getValorMaximo() {
		return this.valorMaximo;
	}

	public void setValorMaximo(Integer valorMaximo) {
		this.valorMaximo = valorMaximo;
	}

	public Set getRequisitoXAplicacionXUsuarioTipos() {
		return this.requisitoXAplicacionXUsuarioTipos;
	}

	public void setRequisitoXAplicacionXUsuarioTipos(
			Set requisitoXAplicacionXUsuarioTipos) {
		this.requisitoXAplicacionXUsuarioTipos = requisitoXAplicacionXUsuarioTipos;
	}

	public Set getUsuarioXRequisitos() {
		return this.usuarioXRequisitos;
	}

	public void setUsuarioXRequisitos(Set usuarioXRequisitos) {
		this.usuarioXRequisitos = usuarioXRequisitos;
	}

}
