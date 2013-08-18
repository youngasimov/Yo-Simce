package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 16-08-2013 05:13:17 AM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Alumno generated by hbm2java
 */
public class Alumno implements java.io.Serializable {

	private Integer id;
	private Sexo sexo;
	private String rut;
	private String nombres;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private Date fechaNacimiento;
	private boolean nee;
	private List<AlumnoXActividad> alumnoXActividads = new ArrayList<AlumnoXActividad>(0);

	public Alumno() {
	}

	public Alumno(Integer id, boolean nee) {
		this.id = id;
		this.nee = nee;
	}

	public Alumno(Integer id, Sexo sexo, String rut, String nombres,
			String apellidoPaterno, String apellidoMaterno,
			Date fechaNacimiento, boolean nee, List<AlumnoXActividad> alumnoXActividads) {
		this.id = id;
		this.sexo = sexo;
		this.rut = rut;
		this.nombres = nombres;
		this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
		this.fechaNacimiento = fechaNacimiento;
		this.nee = nee;
		this.alumnoXActividads = alumnoXActividads;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Sexo getSexo() {
		return this.sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public String getRut() {
		return this.rut;
	}

	public void setRut(String rut) {
		this.rut = rut;
	}

	public String getNombres() {
		return this.nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidoPaterno() {
		return this.apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return this.apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public Date getFechaNacimiento() {
		return this.fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public boolean isNee() {
		return this.nee;
	}

	public void setNee(boolean nee) {
		this.nee = nee;
	}

	public List<AlumnoXActividad> getAlumnoXActividads() {
		return this.alumnoXActividads;
	}

	public void setAlumnoXActividads(List<AlumnoXActividad> alumnoXActividads) {
		this.alumnoXActividads = alumnoXActividads;
	}

}
