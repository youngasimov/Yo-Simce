package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 16-08-2013 05:13:17 AM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Transporte generated by hbm2java
 */
public class Transporte implements java.io.Serializable {

	private Integer id;
	private Usuario usuario;
	private TransporteTipo transporteTipo;
	private Comuna comuna;
	private String patente;
	private Integer ano;
	private String direccion;
	private BigDecimal direccionLatitud;
	private BigDecimal direccionLongitud;
	private String choferNombre;
	private Integer choferCelular;
	private List<TransporteXActividad> transporteXActividads = new ArrayList<TransporteXActividad>(0);
	private List<TransporteXAplicacionXNivel> transporteXAplicacionXNivels = new ArrayList<TransporteXAplicacionXNivel>(0);

	public Transporte() {
	}

	public Transporte(Integer id) {
		this.id = id;
	}

	public Transporte(Integer id, Usuario usuario, TransporteTipo transporteTipo,
			Comuna comuna, String patente, Integer ano, String direccion,
			BigDecimal direccionLatitud, BigDecimal direccionLongitud,
			String choferNombre, Integer choferCelular,
			List<TransporteXActividad> transporteXActividads, List<TransporteXAplicacionXNivel> transporteXAplicacionXNivels) {
		this.id = id;
		this.usuario = usuario;
		this.transporteTipo = transporteTipo;
		this.comuna = comuna;
		this.patente = patente;
		this.ano = ano;
		this.direccion = direccion;
		this.direccionLatitud = direccionLatitud;
		this.direccionLongitud = direccionLongitud;
		this.choferNombre = choferNombre;
		this.choferCelular = choferCelular;
		this.transporteXActividads = transporteXActividads;
		this.transporteXAplicacionXNivels = transporteXAplicacionXNivels;
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

	public TransporteTipo getTransporteTipo() {
		return this.transporteTipo;
	}

	public void setTransporteTipo(TransporteTipo transporteTipo) {
		this.transporteTipo = transporteTipo;
	}

	public Comuna getComuna() {
		return this.comuna;
	}

	public void setComuna(Comuna comuna) {
		this.comuna = comuna;
	}

	public String getPatente() {
		return this.patente;
	}

	public void setPatente(String patente) {
		this.patente = patente;
	}

	public Integer getAno() {
		return this.ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public BigDecimal getDireccionLatitud() {
		return this.direccionLatitud;
	}

	public void setDireccionLatitud(BigDecimal direccionLatitud) {
		this.direccionLatitud = direccionLatitud;
	}

	public BigDecimal getDireccionLongitud() {
		return this.direccionLongitud;
	}

	public void setDireccionLongitud(BigDecimal direccionLongitud) {
		this.direccionLongitud = direccionLongitud;
	}

	public String getChoferNombre() {
		return this.choferNombre;
	}

	public void setChoferNombre(String choferNombre) {
		this.choferNombre = choferNombre;
	}

	public Integer getChoferCelular() {
		return this.choferCelular;
	}

	public void setChoferCelular(Integer choferCelular) {
		this.choferCelular = choferCelular;
	}

	public List<TransporteXActividad> getTransporteXActividads() {
		return this.transporteXActividads;
	}

	public void setTransporteXActividads(List<TransporteXActividad> transporteXActividads) {
		this.transporteXActividads = transporteXActividads;
	}

	public List<TransporteXAplicacionXNivel> getTransporteXAplicacionXNivels() {
		return this.transporteXAplicacionXNivels;
	}

	public void setTransporteXAplicacionXNivels(List<TransporteXAplicacionXNivel> transporteXAplicacionXNivels) {
		this.transporteXAplicacionXNivels = transporteXAplicacionXNivels;
	}

}
