package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 16-08-2013 05:13:17 AM by Hibernate Tools 3.4.0.CR1

/**
 * CoXEstablecimiento generated by hbm2java
 */
public class CoXEstablecimiento implements java.io.Serializable {

	private Integer id;
	private Establecimiento establecimiento;
	private Co co;
	private Integer trasladoTiempo;
	private Integer trasladoDistancia;

	public CoXEstablecimiento() {
	}

	public CoXEstablecimiento(Integer id) {
		this.id = id;
	}

	public CoXEstablecimiento(Integer id, Establecimiento establecimiento, Co co,
			Integer trasladoTiempo, Integer trasladoDistancia) {
		this.id = id;
		this.establecimiento = establecimiento;
		this.co = co;
		this.trasladoTiempo = trasladoTiempo;
		this.trasladoDistancia = trasladoDistancia;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Establecimiento getEstablecimiento() {
		return this.establecimiento;
	}

	public void setEstablecimiento(Establecimiento establecimiento) {
		this.establecimiento = establecimiento;
	}

	public Co getCo() {
		return this.co;
	}

	public void setCo(Co co) {
		this.co = co;
	}

	public Integer getTrasladoTiempo() {
		return this.trasladoTiempo;
	}

	public void setTrasladoTiempo(Integer trasladoTiempo) {
		this.trasladoTiempo = trasladoTiempo;
	}

	public Integer getTrasladoDistancia() {
		return this.trasladoDistancia;
	}

	public void setTrasladoDistancia(Integer trasladoDistancia) {
		this.trasladoDistancia = trasladoDistancia;
	}

}
