package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 16-07-2013 11:03:56 PM by Hibernate Tools 3.4.0.CR1

/**
 * TransporteXAplicacionXNivel generated by hbm2java
 */
public class TransporteXAplicacionXNivel implements java.io.Serializable {

	private Integer id;
	private Transporte transporte;
	private AplicacionXNivel aplicacionXNivel;

	public TransporteXAplicacionXNivel() {
	}

	public TransporteXAplicacionXNivel(Integer id) {
		this.id = id;
	}

	public TransporteXAplicacionXNivel(Integer id, Transporte transporte,
			AplicacionXNivel aplicacionXNivel) {
		this.id = id;
		this.transporte = transporte;
		this.aplicacionXNivel = aplicacionXNivel;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Transporte getTransporte() {
		return this.transporte;
	}

	public void setTransporte(Transporte transporte) {
		this.transporte = transporte;
	}

	public AplicacionXNivel getAplicacionXNivel() {
		return this.aplicacionXNivel;
	}

	public void setAplicacionXNivel(AplicacionXNivel aplicacionXNivel) {
		this.aplicacionXNivel = aplicacionXNivel;
	}

}
