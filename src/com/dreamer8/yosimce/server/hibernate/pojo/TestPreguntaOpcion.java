package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 01-08-2013 04:51:27 AM by Hibernate Tools 3.4.0.CR1

/**
 * TestPreguntaOpcion generated by hbm2java
 */
public class TestPreguntaOpcion implements java.io.Serializable {

	private int id;
	private TestPregunta testPregunta;
	private String NOpcion;
	private String nombre;

	public TestPreguntaOpcion() {
	}

	public TestPreguntaOpcion(int id) {
		this.id = id;
	}

	public TestPreguntaOpcion(int id, TestPregunta testPregunta,
			String NOpcion, String nombre) {
		this.id = id;
		this.testPregunta = testPregunta;
		this.NOpcion = NOpcion;
		this.nombre = nombre;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TestPregunta getTestPregunta() {
		return this.testPregunta;
	}

	public void setTestPregunta(TestPregunta testPregunta) {
		this.testPregunta = testPregunta;
	}

	public String getNOpcion() {
		return this.NOpcion;
	}

	public void setNOpcion(String NOpcion) {
		this.NOpcion = NOpcion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
