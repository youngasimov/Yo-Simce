package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 12-07-2013 05:32:10 AM by Hibernate Tools 3.4.0.CR1

/**
 * FaseXAplicacionXTestXNivelXTipoUsuario generated by hbm2java
 */
public class FaseXAplicacionXTestXNivelXTipoUsuario implements
		java.io.Serializable {

	private Integer id;
	private FaseXAplicacion faseXAplicacion;
	private UsuarioTipo usuarioTipo;
	private Test test;
	private Nivel nivel;

	public FaseXAplicacionXTestXNivelXTipoUsuario() {
	}

	public FaseXAplicacionXTestXNivelXTipoUsuario(Integer id) {
		this.id = id;
	}

	public FaseXAplicacionXTestXNivelXTipoUsuario(Integer id,
			FaseXAplicacion faseXAplicacion, UsuarioTipo usuarioTipo,
			Test test, Nivel nivel) {
		this.id = id;
		this.faseXAplicacion = faseXAplicacion;
		this.usuarioTipo = usuarioTipo;
		this.test = test;
		this.nivel = nivel;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public FaseXAplicacion getFaseXAplicacion() {
		return this.faseXAplicacion;
	}

	public void setFaseXAplicacion(FaseXAplicacion faseXAplicacion) {
		this.faseXAplicacion = faseXAplicacion;
	}

	public UsuarioTipo getUsuarioTipo() {
		return this.usuarioTipo;
	}

	public void setUsuarioTipo(UsuarioTipo usuarioTipo) {
		this.usuarioTipo = usuarioTipo;
	}

	public Test getTest() {
		return this.test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

	public Nivel getNivel() {
		return this.nivel;
	}

	public void setNivel(Nivel nivel) {
		this.nivel = nivel;
	}

}
