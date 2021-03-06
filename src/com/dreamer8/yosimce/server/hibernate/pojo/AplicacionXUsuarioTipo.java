package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 16-08-2013 05:13:17 AM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.List;

/**
 * AplicacionXUsuarioTipo generated by hbm2java
 */
public class AplicacionXUsuarioTipo implements java.io.Serializable {

	private Integer id;
	private Aplicacion aplicacion;
	private UsuarioTipo usuarioTipo;
	private Contrato contrato;
	private CarreraEstado carreraEstado;
	private Integer edadMinima;
	private Integer anoIngresoMaximo;
	private List<RequisitoXAplicacionXUsuarioTipo> requisitoXAplicacionXUsuarioTipos = new ArrayList<RequisitoXAplicacionXUsuarioTipo>(0);
	private List<AplicacionXUsuarioTipoXPermiso> aplicacionXUsuarioTipoXPermisos = new ArrayList<AplicacionXUsuarioTipoXPermiso>(0);
	private List<AplicacionXUsuarioTipoXCarrera> aplicacionXUsuarioTipoXCarreras = new ArrayList<AplicacionXUsuarioTipoXCarrera>(0);

	public AplicacionXUsuarioTipo() {
	}

	public AplicacionXUsuarioTipo(Integer id) {
		this.id = id;
	}

	public AplicacionXUsuarioTipo(Integer id, Aplicacion aplicacion,
			UsuarioTipo usuarioTipo, Contrato contrato,
			CarreraEstado carreraEstado, Integer edadMinima,
			Integer anoIngresoMaximo, List<RequisitoXAplicacionXUsuarioTipo> requisitoXAplicacionXUsuarioTipos,
			List<AplicacionXUsuarioTipoXPermiso> aplicacionXUsuarioTipoXPermisos,
			List<AplicacionXUsuarioTipoXCarrera> aplicacionXUsuarioTipoXCarreras) {
		this.id = id;
		this.aplicacion = aplicacion;
		this.usuarioTipo = usuarioTipo;
		this.contrato = contrato;
		this.carreraEstado = carreraEstado;
		this.edadMinima = edadMinima;
		this.anoIngresoMaximo = anoIngresoMaximo;
		this.requisitoXAplicacionXUsuarioTipos = requisitoXAplicacionXUsuarioTipos;
		this.aplicacionXUsuarioTipoXPermisos = aplicacionXUsuarioTipoXPermisos;
		this.aplicacionXUsuarioTipoXCarreras = aplicacionXUsuarioTipoXCarreras;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Aplicacion getAplicacion() {
		return this.aplicacion;
	}

	public void setAplicacion(Aplicacion aplicacion) {
		this.aplicacion = aplicacion;
	}

	public UsuarioTipo getUsuarioTipo() {
		return this.usuarioTipo;
	}

	public void setUsuarioTipo(UsuarioTipo usuarioTipo) {
		this.usuarioTipo = usuarioTipo;
	}

	public Contrato getContrato() {
		return this.contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	public CarreraEstado getCarreraEstado() {
		return this.carreraEstado;
	}

	public void setCarreraEstado(CarreraEstado carreraEstado) {
		this.carreraEstado = carreraEstado;
	}

	public Integer getEdadMinima() {
		return this.edadMinima;
	}

	public void setEdadMinima(Integer edadMinima) {
		this.edadMinima = edadMinima;
	}

	public Integer getAnoIngresoMaximo() {
		return this.anoIngresoMaximo;
	}

	public void setAnoIngresoMaximo(Integer anoIngresoMaximo) {
		this.anoIngresoMaximo = anoIngresoMaximo;
	}

	public List<RequisitoXAplicacionXUsuarioTipo> getRequisitoXAplicacionXUsuarioTipos() {
		return this.requisitoXAplicacionXUsuarioTipos;
	}

	public void setRequisitoXAplicacionXUsuarioTipos(
			List<RequisitoXAplicacionXUsuarioTipo> requisitoXAplicacionXUsuarioTipos) {
		this.requisitoXAplicacionXUsuarioTipos = requisitoXAplicacionXUsuarioTipos;
	}

	public List<AplicacionXUsuarioTipoXPermiso> getAplicacionXUsuarioTipoXPermisos() {
		return this.aplicacionXUsuarioTipoXPermisos;
	}

	public void setAplicacionXUsuarioTipoXPermisos(
			List<AplicacionXUsuarioTipoXPermiso> aplicacionXUsuarioTipoXPermisos) {
		this.aplicacionXUsuarioTipoXPermisos = aplicacionXUsuarioTipoXPermisos;
	}

	public List<AplicacionXUsuarioTipoXCarrera> getAplicacionXUsuarioTipoXCarreras() {
		return this.aplicacionXUsuarioTipoXCarreras;
	}

	public void setAplicacionXUsuarioTipoXCarreras(
			List<AplicacionXUsuarioTipoXCarrera> aplicacionXUsuarioTipoXCarreras) {
		this.aplicacionXUsuarioTipoXCarreras = aplicacionXUsuarioTipoXCarreras;
	}

}
