package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 05-08-2013 03:58:39 AM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.List;

/**
 * AplicacionXNivelXUsuarioTipo generated by hbm2java
 */
public class AplicacionXNivelXUsuarioTipo implements java.io.Serializable {

	private Integer id;
	private UsuarioTipo usuarioTipo;
	private AplicacionXNivel aplicacionXNivel;
	private Integer montoPago;
	private List<AvisoXAplicacionXNivelXUsuarioTipo> avisoXAplicacionXNivelXUsuarioTipos = new ArrayList<AvisoXAplicacionXNivelXUsuarioTipo>(0);
	private List<ArchvicoXAplicacionXNivelXUsuarioTipo> archvicoXAplicacionXNivelXUsuarioTipos = new ArrayList<ArchvicoXAplicacionXNivelXUsuarioTipo>(0);
	private List<AplicacionXNivelXUsuarioTipoXCo> aplicacionXNivelXUsuarioTipoXCos = new ArrayList<AplicacionXNivelXUsuarioTipoXCo>(0);

	public AplicacionXNivelXUsuarioTipo() {
	}

	public AplicacionXNivelXUsuarioTipo(Integer id) {
		this.id = id;
	}

	public AplicacionXNivelXUsuarioTipo(Integer id, UsuarioTipo usuarioTipo,
			AplicacionXNivel aplicacionXNivel, Integer montoPago,
			List<AvisoXAplicacionXNivelXUsuarioTipo> avisoXAplicacionXNivelXUsuarioTipos,
			List<ArchvicoXAplicacionXNivelXUsuarioTipo> archvicoXAplicacionXNivelXUsuarioTipos,
			List<AplicacionXNivelXUsuarioTipoXCo> aplicacionXNivelXUsuarioTipoXCos) {
		this.id = id;
		this.usuarioTipo = usuarioTipo;
		this.aplicacionXNivel = aplicacionXNivel;
		this.montoPago = montoPago;
		this.avisoXAplicacionXNivelXUsuarioTipos = avisoXAplicacionXNivelXUsuarioTipos;
		this.archvicoXAplicacionXNivelXUsuarioTipos = archvicoXAplicacionXNivelXUsuarioTipos;
		this.aplicacionXNivelXUsuarioTipoXCos = aplicacionXNivelXUsuarioTipoXCos;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UsuarioTipo getUsuarioTipo() {
		return this.usuarioTipo;
	}

	public void setUsuarioTipo(UsuarioTipo usuarioTipo) {
		this.usuarioTipo = usuarioTipo;
	}

	public AplicacionXNivel getAplicacionXNivel() {
		return this.aplicacionXNivel;
	}

	public void setAplicacionXNivel(AplicacionXNivel aplicacionXNivel) {
		this.aplicacionXNivel = aplicacionXNivel;
	}

	public Integer getMontoPago() {
		return this.montoPago;
	}

	public void setMontoPago(Integer montoPago) {
		this.montoPago = montoPago;
	}

	public List<AvisoXAplicacionXNivelXUsuarioTipo> getAvisoXAplicacionXNivelXUsuarioTipos() {
		return this.avisoXAplicacionXNivelXUsuarioTipos;
	}

	public void setAvisoXAplicacionXNivelXUsuarioTipos(
			List<AvisoXAplicacionXNivelXUsuarioTipo> avisoXAplicacionXNivelXUsuarioTipos) {
		this.avisoXAplicacionXNivelXUsuarioTipos = avisoXAplicacionXNivelXUsuarioTipos;
	}

	public List<ArchvicoXAplicacionXNivelXUsuarioTipo> getArchvicoXAplicacionXNivelXUsuarioTipos() {
		return this.archvicoXAplicacionXNivelXUsuarioTipos;
	}

	public void setArchvicoXAplicacionXNivelXUsuarioTipos(
			List<ArchvicoXAplicacionXNivelXUsuarioTipo> archvicoXAplicacionXNivelXUsuarioTipos) {
		this.archvicoXAplicacionXNivelXUsuarioTipos = archvicoXAplicacionXNivelXUsuarioTipos;
	}

	public List<AplicacionXNivelXUsuarioTipoXCo> getAplicacionXNivelXUsuarioTipoXCos() {
		return this.aplicacionXNivelXUsuarioTipoXCos;
	}

	public void setAplicacionXNivelXUsuarioTipoXCos(
			List<AplicacionXNivelXUsuarioTipoXCo> aplicacionXNivelXUsuarioTipoXCos) {
		this.aplicacionXNivelXUsuarioTipoXCos = aplicacionXNivelXUsuarioTipoXCos;
	}

}
