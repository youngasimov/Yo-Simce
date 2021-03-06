package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 16-08-2013 05:13:17 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * RutaXEstablecimiento generated by hbm2java
 */
public class RutaXEstablecimiento implements java.io.Serializable {

	private Integer id;
	private Usuario usuario;
	private Ruta ruta;
	private Establecimiento establecimiento;
	private Integer orden;
	private Integer tramoTiempo;
	private Integer tramoDistancia;
	private Date horaLlegada;
	private Date updatedAt;

	public RutaXEstablecimiento() {
	}

	public RutaXEstablecimiento(Integer id) {
		this.id = id;
	}

	public RutaXEstablecimiento(Integer id, Usuario usuario, Ruta ruta,
			Establecimiento establecimiento, Integer orden,
			Integer tramoTiempo, Integer tramoDistancia, Date horaLlegada,
			Date updatedAt) {
		this.id = id;
		this.usuario = usuario;
		this.ruta = ruta;
		this.establecimiento = establecimiento;
		this.orden = orden;
		this.tramoTiempo = tramoTiempo;
		this.tramoDistancia = tramoDistancia;
		this.horaLlegada = horaLlegada;
		this.updatedAt = updatedAt;
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

	public Ruta getRuta() {
		return this.ruta;
	}

	public void setRuta(Ruta ruta) {
		this.ruta = ruta;
	}

	public Establecimiento getEstablecimiento() {
		return this.establecimiento;
	}

	public void setEstablecimiento(Establecimiento establecimiento) {
		this.establecimiento = establecimiento;
	}

	public Integer getOrden() {
		return this.orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public Integer getTramoTiempo() {
		return this.tramoTiempo;
	}

	public void setTramoTiempo(Integer tramoTiempo) {
		this.tramoTiempo = tramoTiempo;
	}

	public Integer getTramoDistancia() {
		return this.tramoDistancia;
	}

	public void setTramoDistancia(Integer tramoDistancia) {
		this.tramoDistancia = tramoDistancia;
	}

	public Date getHoraLlegada() {
		return this.horaLlegada;
	}

	public void setHoraLlegada(Date horaLlegada) {
		this.horaLlegada = horaLlegada;
	}

	public Date getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

}
