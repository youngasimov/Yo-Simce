package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 01-08-2013 04:51:27 AM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * Curso generated by hbm2java
 */
public class Curso implements java.io.Serializable {

	private int id;
	private Nivel nivel;
	private Establecimiento establecimiento;
	private String nombre;
	private String codigo;
	private boolean emergente;
	private boolean nee;
	private Set actividads = new HashSet(0);

	public Curso() {
	}

	public Curso(int id, boolean emergente, boolean nee) {
		this.id = id;
		this.emergente = emergente;
		this.nee = nee;
	}

	public Curso(int id, Nivel nivel, Establecimiento establecimiento,
			String nombre, String codigo, boolean emergente, boolean nee,
			Set actividads) {
		this.id = id;
		this.nivel = nivel;
		this.establecimiento = establecimiento;
		this.nombre = nombre;
		this.codigo = codigo;
		this.emergente = emergente;
		this.nee = nee;
		this.actividads = actividads;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Nivel getNivel() {
		return this.nivel;
	}

	public void setNivel(Nivel nivel) {
		this.nivel = nivel;
	}

	public Establecimiento getEstablecimiento() {
		return this.establecimiento;
	}

	public void setEstablecimiento(Establecimiento establecimiento) {
		this.establecimiento = establecimiento;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public boolean isEmergente() {
		return this.emergente;
	}

	public void setEmergente(boolean emergente) {
		this.emergente = emergente;
	}

	public boolean isNee() {
		return this.nee;
	}

	public void setNee(boolean nee) {
		this.nee = nee;
	}

	public Set getActividads() {
		return this.actividads;
	}

	public void setActividads(Set actividads) {
		this.actividads = actividads;
	}

}
