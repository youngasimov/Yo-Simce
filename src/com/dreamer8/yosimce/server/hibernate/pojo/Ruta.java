package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 05-08-2013 03:58:39 AM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Ruta generated by hbm2java
 */
public class Ruta implements java.io.Serializable {

	private Integer id;
	private TrasladoModo trasladoModo;
	private AplicacionXNivelXActividadTipo aplicacionXNivelXActividadTipo;
	private Color color;
	private Usuario usuario;
	private Co co;
	private Integer ordenSalida;
	private Integer distanciaTotal;
	private Integer duracionEstimada;
	private Date horaSalida;
	private Date updatedAt;
	private List<RutaXEstablecimiento> rutaXEstablecimientos = new ArrayList<RutaXEstablecimiento>(0);

	public Ruta() {
	}

	public Ruta(Integer id) {
		this.id = id;
	}

	public Ruta(Integer id, TrasladoModo trasladoModo,
			AplicacionXNivelXActividadTipo aplicacionXNivelXActividadTipo,
			Color color, Usuario usuario, Co co, Integer ordenSalida,
			Integer distanciaTotal, Integer duracionEstimada, Date horaSalida,
			Date updatedAt, List<RutaXEstablecimiento> rutaXEstablecimientos) {
		this.id = id;
		this.trasladoModo = trasladoModo;
		this.aplicacionXNivelXActividadTipo = aplicacionXNivelXActividadTipo;
		this.color = color;
		this.usuario = usuario;
		this.co = co;
		this.ordenSalida = ordenSalida;
		this.distanciaTotal = distanciaTotal;
		this.duracionEstimada = duracionEstimada;
		this.horaSalida = horaSalida;
		this.updatedAt = updatedAt;
		this.rutaXEstablecimientos = rutaXEstablecimientos;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TrasladoModo getTrasladoModo() {
		return this.trasladoModo;
	}

	public void setTrasladoModo(TrasladoModo trasladoModo) {
		this.trasladoModo = trasladoModo;
	}

	public AplicacionXNivelXActividadTipo getAplicacionXNivelXActividadTipo() {
		return this.aplicacionXNivelXActividadTipo;
	}

	public void setAplicacionXNivelXActividadTipo(
			AplicacionXNivelXActividadTipo aplicacionXNivelXActividadTipo) {
		this.aplicacionXNivelXActividadTipo = aplicacionXNivelXActividadTipo;
	}

	public Color getColor() {
		return this.color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Co getCo() {
		return this.co;
	}

	public void setCo(Co co) {
		this.co = co;
	}

	public Integer getOrdenSalida() {
		return this.ordenSalida;
	}

	public void setOrdenSalida(Integer ordenSalida) {
		this.ordenSalida = ordenSalida;
	}

	public Integer getDistanciaTotal() {
		return this.distanciaTotal;
	}

	public void setDistanciaTotal(Integer distanciaTotal) {
		this.distanciaTotal = distanciaTotal;
	}

	public Integer getDuracionEstimada() {
		return this.duracionEstimada;
	}

	public void setDuracionEstimada(Integer duracionEstimada) {
		this.duracionEstimada = duracionEstimada;
	}

	public Date getHoraSalida() {
		return this.horaSalida;
	}

	public void setHoraSalida(Date horaSalida) {
		this.horaSalida = horaSalida;
	}

	public Date getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<RutaXEstablecimiento> getRutaXEstablecimientos() {
		return this.rutaXEstablecimientos;
	}

	public void setRutaXEstablecimientos(List<RutaXEstablecimiento> rutaXEstablecimientos) {
		this.rutaXEstablecimientos = rutaXEstablecimientos;
	}

}
