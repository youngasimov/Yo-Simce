package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 16-08-2013 05:13:17 AM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.List;

/**
 * Sexo generated by hbm2java
 */
public class Sexo implements java.io.Serializable {
	public static final String MASCULINO = "Masculino";
	public static final String FEMENINO = "Femenino";

	private Integer id;
	private String nombre;
	private List<Alumno> alumnos = new ArrayList<Alumno>(0);
	private List<Usuario> usuarios = new ArrayList<Usuario>(0);

	public Sexo() {
	}

	public Sexo(Integer id) {
		this.id = id;
	}

	public Sexo(Integer id, String nombre, List<Alumno> alumnos,
			List<Usuario> usuarios) {
		this.id = id;
		this.nombre = nombre;
		this.alumnos = alumnos;
		this.usuarios = usuarios;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Alumno> getAlumnos() {
		return this.alumnos;
	}

	public void setAlumnos(List<Alumno> alumnos) {
		this.alumnos = alumnos;
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

}
