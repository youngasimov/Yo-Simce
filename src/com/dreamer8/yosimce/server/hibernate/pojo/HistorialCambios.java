package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 05-08-2013 03:58:39 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * HistorialCambios generated by hbm2java
 */
public class HistorialCambios implements java.io.Serializable {

	private Integer id;
	private Usuario usuario;
	private Date fecha;
	private String tabla;
	private String campo;
	private String valor;

	public HistorialCambios() {
	}

	public HistorialCambios(Integer id) {
		this.id = id;
	}

	public HistorialCambios(Integer id, Usuario usuario, Date fecha, String tabla,
			String campo, String valor) {
		this.id = id;
		this.usuario = usuario;
		this.fecha = fecha;
		this.tabla = tabla;
		this.campo = campo;
		this.valor = valor;
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

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getTabla() {
		return this.tabla;
	}

	public void setTabla(String tabla) {
		this.tabla = tabla;
	}

	public String getCampo() {
		return this.campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public String getValor() {
		return this.valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

}
