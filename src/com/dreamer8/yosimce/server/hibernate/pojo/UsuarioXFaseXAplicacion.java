package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 16-08-2013 05:13:17 AM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;

/**
 * UsuarioXFaseXAplicacion generated by hbm2java
 */
public class UsuarioXFaseXAplicacion implements java.io.Serializable {

	private Integer id;
	private Usuario usuario;
	private FaseXAplicacion faseXAplicacion;
	private boolean aprobado;
	private Date fechaAprobacion;
	private String comentario;
	private BigDecimal evaluacion;
	private Date updatedAt;

	public UsuarioXFaseXAplicacion() {
	}

	public UsuarioXFaseXAplicacion(Integer id, boolean aprobado) {
		this.id = id;
		this.aprobado = aprobado;
	}

	public UsuarioXFaseXAplicacion(Integer id, Usuario usuario,
			FaseXAplicacion faseXAplicacion, boolean aprobado,
			Date fechaAprobacion, String comentario, BigDecimal evaluacion,
			Date updatedAt) {
		this.id = id;
		this.usuario = usuario;
		this.faseXAplicacion = faseXAplicacion;
		this.aprobado = aprobado;
		this.fechaAprobacion = fechaAprobacion;
		this.comentario = comentario;
		this.evaluacion = evaluacion;
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

	public FaseXAplicacion getFaseXAplicacion() {
		return this.faseXAplicacion;
	}

	public void setFaseXAplicacion(FaseXAplicacion faseXAplicacion) {
		this.faseXAplicacion = faseXAplicacion;
	}

	public boolean isAprobado() {
		return this.aprobado;
	}

	public void setAprobado(boolean aprobado) {
		this.aprobado = aprobado;
	}

	public Date getFechaAprobacion() {
		return this.fechaAprobacion;
	}

	public void setFechaAprobacion(Date fechaAprobacion) {
		this.fechaAprobacion = fechaAprobacion;
	}

	public String getComentario() {
		return this.comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public BigDecimal getEvaluacion() {
		return this.evaluacion;
	}

	public void setEvaluacion(BigDecimal evaluacion) {
		this.evaluacion = evaluacion;
	}

	public Date getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

}
