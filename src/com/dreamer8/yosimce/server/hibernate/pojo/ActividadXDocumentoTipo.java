package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 16-08-2013 05:13:17 AM by Hibernate Tools 3.4.0.CR1

/**
 * ActividadXDocumentoTipo generated by hbm2java
 */
public class ActividadXDocumentoTipo implements java.io.Serializable {

	private Integer id;
	private DocumentoTipo documentoTipo;
	private Actividad actividad;
	private Integer totalEntregados;
	private Integer totalRecibidos;
	private Integer total;

	public ActividadXDocumentoTipo() {
	}

	public ActividadXDocumentoTipo(Integer id) {
		this.id = id;
	}

	public ActividadXDocumentoTipo(Integer id, DocumentoTipo documentoTipo,
			Actividad actividad, Integer totalEntregados, Integer totalRecibidos) {
		this.id = id;
		this.documentoTipo = documentoTipo;
		this.actividad = actividad;
		this.totalEntregados = totalEntregados;
		this.totalRecibidos = totalRecibidos;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public DocumentoTipo getDocumentoTipo() {
		return this.documentoTipo;
	}

	public void setDocumentoTipo(DocumentoTipo documentoTipo) {
		this.documentoTipo = documentoTipo;
	}

	public Actividad getActividad() {
		return this.actividad;
	}

	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
	}

	public Integer getTotalEntregados() {
		return this.totalEntregados;
	}

	public void setTotalEntregados(Integer totalEntregados) {
		this.totalEntregados = totalEntregados;
	}

	public Integer getTotalRecibidos() {
		return this.totalRecibidos;
	}

	public void setTotalRecibidos(Integer totalRecibidos) {
		this.totalRecibidos = totalRecibidos;
	}

	/**
	 * @return the total
	 */
	public Integer getTotal() {
		return total;
	}

	/**
	 * @param total
	 *            the total to set
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}

}
