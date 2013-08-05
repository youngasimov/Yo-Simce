package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 05-08-2013 03:58:39 AM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * GuiaDespacho generated by hbm2java
 */
public class GuiaDespacho implements java.io.Serializable {

	private Integer id;
	private Archivo archivo;
	private String codigo;
	private String comentario;
	private Date fecha;
	private List<MaterialXGuiaDespacho> materialXGuiaDespachos = new ArrayList<MaterialXGuiaDespacho>(0);

	public GuiaDespacho() {
	}

	public GuiaDespacho(Integer id) {
		this.id = id;
	}

	public GuiaDespacho(Integer id, Archivo archivo, String codigo,
			String comentario, Date fecha, List<MaterialXGuiaDespacho> materialXGuiaDespachos) {
		this.id = id;
		this.archivo = archivo;
		this.codigo = codigo;
		this.comentario = comentario;
		this.fecha = fecha;
		this.materialXGuiaDespachos = materialXGuiaDespachos;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Archivo getArchivo() {
		return this.archivo;
	}

	public void setArchivo(Archivo archivo) {
		this.archivo = archivo;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getComentario() {
		return this.comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public List<MaterialXGuiaDespacho> getMaterialXGuiaDespachos() {
		return this.materialXGuiaDespachos;
	}

	public void setMaterialXGuiaDespachos(List<MaterialXGuiaDespacho> materialXGuiaDespachos) {
		this.materialXGuiaDespachos = materialXGuiaDespachos;
	}

}
