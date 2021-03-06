package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 16-08-2013 05:13:17 AM by Hibernate Tools 3.4.0.CR1

/**
 * TrasladoModoXTransporteTipo generated by hbm2java
 */
public class TrasladoModoXTransporteTipo implements java.io.Serializable {

	private Integer id;
	private TrasladoModo trasladoModo;
	private TransporteTipo transporteTipo;
	private Integer prioridad;

	public TrasladoModoXTransporteTipo() {
	}

	public TrasladoModoXTransporteTipo(Integer id) {
		this.id = id;
	}

	public TrasladoModoXTransporteTipo(Integer id, TrasladoModo trasladoModo,
			TransporteTipo transporteTipo, Integer prioridad) {
		this.id = id;
		this.trasladoModo = trasladoModo;
		this.transporteTipo = transporteTipo;
		this.prioridad = prioridad;
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

	public TransporteTipo getTransporteTipo() {
		return this.transporteTipo;
	}

	public void setTransporteTipo(TransporteTipo transporteTipo) {
		this.transporteTipo = transporteTipo;
	}

	public Integer getPrioridad() {
		return this.prioridad;
	}

	public void setPrioridad(Integer prioridad) {
		this.prioridad = prioridad;
	}

}
