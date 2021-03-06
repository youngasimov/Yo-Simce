package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 16-08-2013 05:13:17 AM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.List;

/**
 * Test generated by hbm2java
 */
public class Test implements java.io.Serializable {

	private Integer id;
	private String nombre;
	private String slug;
	private Integer Integerentos;
	private Integer duracion;
	private Integer aprobacion;
	private Boolean multipregunta;
	private String instruccion;
	private Integer valorInvierteSentido;
	private List<FaseXAplicacionXTestXNivelXUsuarioTipo> faseXAplicacionXTestXNivelXUsuarioTipos = new ArrayList<FaseXAplicacionXTestXNivelXUsuarioTipo>(0);
	private List<TestPregunta> testPreguntas = new ArrayList<TestPregunta>(0);
	private List<UsuarioXTest> usuarioXTests = new ArrayList<UsuarioXTest>(0);

	public Test() {
	}

	public Test(Integer id) {
		this.id = id;
	}

	public Test(Integer id, String nombre, String slug, Integer Integerentos,
			Integer duracion, Integer aprobacion, Boolean multipregunta,
			String instruccion, Integer valorInvierteSentido,
			List<FaseXAplicacionXTestXNivelXUsuarioTipo> faseXAplicacionXTestXNivelXUsuarioTipos, List<TestPregunta> testPreguntas,
			List<UsuarioXTest> usuarioXTests) {
		this.id = id;
		this.nombre = nombre;
		this.slug = slug;
		this.Integerentos = Integerentos;
		this.duracion = duracion;
		this.aprobacion = aprobacion;
		this.multipregunta = multipregunta;
		this.instruccion = instruccion;
		this.valorInvierteSentido = valorInvierteSentido;
		this.faseXAplicacionXTestXNivelXUsuarioTipos = faseXAplicacionXTestXNivelXUsuarioTipos;
		this.testPreguntas = testPreguntas;
		this.usuarioXTests = usuarioXTests;
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

	public String getSlug() {
		return this.slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public Integer getIntentos() {
		return this.Integerentos;
	}

	public void setIntentos(Integer Integerentos) {
		this.Integerentos = Integerentos;
	}

	public Integer getDuracion() {
		return this.duracion;
	}

	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}

	public Integer getAprobacion() {
		return this.aprobacion;
	}

	public void setAprobacion(Integer aprobacion) {
		this.aprobacion = aprobacion;
	}

	public Boolean getMultipregunta() {
		return this.multipregunta;
	}

	public void setMultipregunta(Boolean multipregunta) {
		this.multipregunta = multipregunta;
	}

	public String getInstruccion() {
		return this.instruccion;
	}

	public void setInstruccion(String instruccion) {
		this.instruccion = instruccion;
	}

	public Integer getValorInvierteSentido() {
		return this.valorInvierteSentido;
	}

	public void setValorInvierteSentido(Integer valorInvierteSentido) {
		this.valorInvierteSentido = valorInvierteSentido;
	}

	public List<FaseXAplicacionXTestXNivelXUsuarioTipo> getFaseXAplicacionXTestXNivelXUsuarioTipos() {
		return this.faseXAplicacionXTestXNivelXUsuarioTipos;
	}

	public void setFaseXAplicacionXTestXNivelXUsuarioTipos(
			List<FaseXAplicacionXTestXNivelXUsuarioTipo> faseXAplicacionXTestXNivelXUsuarioTipos) {
		this.faseXAplicacionXTestXNivelXUsuarioTipos = faseXAplicacionXTestXNivelXUsuarioTipos;
	}

	public List<TestPregunta> getTestPreguntas() {
		return this.testPreguntas;
	}

	public void setTestPreguntas(List<TestPregunta> testPreguntas) {
		this.testPreguntas = testPreguntas;
	}

	public List<UsuarioXTest> getUsuarioXTests() {
		return this.usuarioXTests;
	}

	public void setUsuarioXTests(List<UsuarioXTest> usuarioXTests) {
		this.usuarioXTests = usuarioXTests;
	}

}
