package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class AgendaItemDTO implements Serializable {

	
	private Date fecha;
	private String comentario;
	private UserDTO creador;
	private EstadoAgendaDTO estado;
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public EstadoAgendaDTO getEstado() {
		return estado;
	}
	public void setEstado(EstadoAgendaDTO estado) {
		this.estado = estado;
	}
	public UserDTO getCreador() {
		return creador;
	}
	public void setCreador(UserDTO creador) {
		this.creador = creador;
	}
}
