package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 01-08-2013 04:51:27 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * AlumnoXActividadXDocumento generated by hbm2java
 */
public class AlumnoXActividadXDocumento implements java.io.Serializable {

	private int id;
	private Documento documento;
	private DocumentoEstado documentoEstado;
	private AlumnoXActividad alumnoXActividad;
	private Boolean entregado;
	private Boolean recibido;
	private Date updatedAt;
	private Integer modificadorId;

	public AlumnoXActividadXDocumento() {
	}

	public AlumnoXActividadXDocumento(int id) {
		this.id = id;
	}

	public AlumnoXActividadXDocumento(int id, Documento documento,
			DocumentoEstado documentoEstado, AlumnoXActividad alumnoXActividad,
			Boolean entregado, Boolean recibido, Date updatedAt,
			Integer modificadorId) {
		this.id = id;
		this.documento = documento;
		this.documentoEstado = documentoEstado;
		this.alumnoXActividad = alumnoXActividad;
		this.entregado = entregado;
		this.recibido = recibido;
		this.updatedAt = updatedAt;
		this.modificadorId = modificadorId;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Documento getDocumento() {
		return this.documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public DocumentoEstado getDocumentoEstado() {
		return this.documentoEstado;
	}

	public void setDocumentoEstado(DocumentoEstado documentoEstado) {
		this.documentoEstado = documentoEstado;
	}

	public AlumnoXActividad getAlumnoXActividad() {
		return this.alumnoXActividad;
	}

	public void setAlumnoXActividad(AlumnoXActividad alumnoXActividad) {
		this.alumnoXActividad = alumnoXActividad;
	}

	public Boolean getEntregado() {
		return this.entregado;
	}

	public void setEntregado(Boolean entregado) {
		this.entregado = entregado;
	}

	public Boolean getRecibido() {
		return this.recibido;
	}

	public void setRecibido(Boolean recibido) {
		this.recibido = recibido;
	}

	public Date getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Integer getModificadorId() {
		return this.modificadorId;
	}

	public void setModificadorId(Integer modificadorId) {
		this.modificadorId = modificadorId;
	}

}
