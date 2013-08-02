package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 01-08-2013 04:51:27 AM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * Documento generated by hbm2java
 */
public class Documento implements java.io.Serializable {

	private int id;
	private DocumentoTipo documentoTipo;
	private Material material;
	private String codigo;
	private boolean contingencia;
	private String rutaDocumento;
	private Set alumnoXActividadXDocumentos = new HashSet(0);
	private Set actividads = new HashSet(0);

	public Documento() {
	}

	public Documento(int id, boolean contingencia) {
		this.id = id;
		this.contingencia = contingencia;
	}

	public Documento(int id, DocumentoTipo documentoTipo, Material material,
			String codigo, boolean contingencia, String rutaDocumento,
			Set alumnoXActividadXDocumentos, Set actividads) {
		this.id = id;
		this.documentoTipo = documentoTipo;
		this.material = material;
		this.codigo = codigo;
		this.contingencia = contingencia;
		this.rutaDocumento = rutaDocumento;
		this.alumnoXActividadXDocumentos = alumnoXActividadXDocumentos;
		this.actividads = actividads;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public DocumentoTipo getDocumentoTipo() {
		return this.documentoTipo;
	}

	public void setDocumentoTipo(DocumentoTipo documentoTipo) {
		this.documentoTipo = documentoTipo;
	}

	public Material getMaterial() {
		return this.material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public boolean isContingencia() {
		return this.contingencia;
	}

	public void setContingencia(boolean contingencia) {
		this.contingencia = contingencia;
	}

	public String getRutaDocumento() {
		return this.rutaDocumento;
	}

	public void setRutaDocumento(String rutaDocumento) {
		this.rutaDocumento = rutaDocumento;
	}

	public Set getAlumnoXActividadXDocumentos() {
		return this.alumnoXActividadXDocumentos;
	}

	public void setAlumnoXActividadXDocumentos(Set alumnoXActividadXDocumentos) {
		this.alumnoXActividadXDocumentos = alumnoXActividadXDocumentos;
	}

	public Set getActividads() {
		return this.actividads;
	}

	public void setActividads(Set actividads) {
		this.actividads = actividads;
	}

}
