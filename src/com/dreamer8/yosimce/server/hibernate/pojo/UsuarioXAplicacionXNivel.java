package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 12-07-2013 05:32:10 AM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.List;

/**
 * UsuarioXAplicacionXNivel generated by hbm2java
 */
public class UsuarioXAplicacionXNivel implements java.io.Serializable {

	private Integer id;
	private Usuario usuario;
	private AplicacionXNivel aplicacionXNivel;
	private List<UsuarioSeleccion> usuarioSeleccions = new ArrayList<UsuarioSeleccion>(0);
	private List<UsuarioPreseleccion> usuarioPreseleccions = new ArrayList<UsuarioPreseleccion>(0);

	public UsuarioXAplicacionXNivel() {
	}

	public UsuarioXAplicacionXNivel(Integer id) {
		this.id = id;
	}

	public UsuarioXAplicacionXNivel(Integer id, Usuario usuario,
			AplicacionXNivel aplicacionXNivel, List<UsuarioSeleccion> usuarioSeleccions,
			List<UsuarioPreseleccion> usuarioPreseleccions) {
		this.id = id;
		this.usuario = usuario;
		this.aplicacionXNivel = aplicacionXNivel;
		this.usuarioSeleccions = usuarioSeleccions;
		this.usuarioPreseleccions = usuarioPreseleccions;
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

	public AplicacionXNivel getAplicacionXNivel() {
		return this.aplicacionXNivel;
	}

	public void setAplicacionXNivel(AplicacionXNivel aplicacionXNivel) {
		this.aplicacionXNivel = aplicacionXNivel;
	}

	public List<UsuarioSeleccion> getUsuarioSeleccions() {
		return this.usuarioSeleccions;
	}

	public void setUsuarioSeleccions(List<UsuarioSeleccion> usuarioSeleccions) {
		this.usuarioSeleccions = usuarioSeleccions;
	}

	public List<UsuarioPreseleccion> getUsuarioPreseleccions() {
		return this.usuarioPreseleccions;
	}

	public void setUsuarioPreseleccions(List<UsuarioPreseleccion> usuarioPreseleccions) {
		this.usuarioPreseleccions = usuarioPreseleccions;
	}

}
