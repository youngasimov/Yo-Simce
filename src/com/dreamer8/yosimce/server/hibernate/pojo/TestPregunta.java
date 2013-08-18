package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 16-08-2013 05:13:17 AM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.List;

/**
 * TestPregunta generated by hbm2java
 */
public class TestPregunta implements java.io.Serializable {

	private Integer id;
	private Test test;
	private TestCompetencia testCompetencia;
	private Integer NPregunta;
	private String nombre;
	private Character alternativaCorrecta;
	private Boolean sentido;
	private String imagen;
	private List<TestPreguntaOpcion> testPreguntaOpcions = new ArrayList<TestPreguntaOpcion>(0);
	private List<TestPreguntaAlternativa> testPreguntaAlternativas = new ArrayList<TestPreguntaAlternativa>(0);
	private List<UsuarioXTestRespuesta> usuarioXTestRespuestas = new ArrayList<UsuarioXTestRespuesta>(0);

	public TestPregunta() {
	}

	public TestPregunta(Integer id) {
		this.id = id;
	}

	public TestPregunta(Integer id, Test test, TestCompetencia testCompetencia,
			Integer NPregunta, String nombre, Character alternativaCorrecta,
			Boolean sentido, String imagen, List<TestPreguntaOpcion> testPreguntaOpcions,
			List<TestPreguntaAlternativa> testPreguntaAlternativas, List<UsuarioXTestRespuesta> usuarioXTestRespuestas) {
		this.id = id;
		this.test = test;
		this.testCompetencia = testCompetencia;
		this.NPregunta = NPregunta;
		this.nombre = nombre;
		this.alternativaCorrecta = alternativaCorrecta;
		this.sentido = sentido;
		this.imagen = imagen;
		this.testPreguntaOpcions = testPreguntaOpcions;
		this.testPreguntaAlternativas = testPreguntaAlternativas;
		this.usuarioXTestRespuestas = usuarioXTestRespuestas;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Test getTest() {
		return this.test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

	public TestCompetencia getTestCompetencia() {
		return this.testCompetencia;
	}

	public void setTestCompetencia(TestCompetencia testCompetencia) {
		this.testCompetencia = testCompetencia;
	}

	public Integer getNPregunta() {
		return this.NPregunta;
	}

	public void setNPregunta(Integer NPregunta) {
		this.NPregunta = NPregunta;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Character getAlternativaCorrecta() {
		return this.alternativaCorrecta;
	}

	public void setAlternativaCorrecta(Character alternativaCorrecta) {
		this.alternativaCorrecta = alternativaCorrecta;
	}

	public Boolean getSentido() {
		return this.sentido;
	}

	public void setSentido(Boolean sentido) {
		this.sentido = sentido;
	}

	public String getImagen() {
		return this.imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public List<TestPreguntaOpcion> getTestPreguntaOpcions() {
		return this.testPreguntaOpcions;
	}

	public void setTestPreguntaOpcions(List<TestPreguntaOpcion> testPreguntaOpcions) {
		this.testPreguntaOpcions = testPreguntaOpcions;
	}

	public List<TestPreguntaAlternativa> getTestPreguntaAlternativas() {
		return this.testPreguntaAlternativas;
	}

	public void setTestPreguntaAlternativas(List<TestPreguntaAlternativa> testPreguntaAlternativas) {
		this.testPreguntaAlternativas = testPreguntaAlternativas;
	}

	public List<UsuarioXTestRespuesta> getUsuarioXTestRespuestas() {
		return this.usuarioXTestRespuestas;
	}

	public void setUsuarioXTestRespuestas(List<UsuarioXTestRespuesta> usuarioXTestRespuestas) {
		this.usuarioXTestRespuestas = usuarioXTestRespuestas;
	}

}
