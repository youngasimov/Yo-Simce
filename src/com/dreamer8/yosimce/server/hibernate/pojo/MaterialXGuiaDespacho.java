package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 01-08-2013 04:51:27 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * MaterialXGuiaDespacho generated by hbm2java
 */
public class MaterialXGuiaDespacho implements java.io.Serializable {

	private int id;
	private Material material;
	private GuiaDespacho guiaDespacho;
	private Date fecha;

	public MaterialXGuiaDespacho() {
	}

	public MaterialXGuiaDespacho(int id) {
		this.id = id;
	}

	public MaterialXGuiaDespacho(int id, Material material,
			GuiaDespacho guiaDespacho, Date fecha) {
		this.id = id;
		this.material = material;
		this.guiaDespacho = guiaDespacho;
		this.fecha = fecha;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Material getMaterial() {
		return this.material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public GuiaDespacho getGuiaDespacho() {
		return this.guiaDespacho;
	}

	public void setGuiaDespacho(GuiaDespacho guiaDespacho) {
		this.guiaDespacho = guiaDespacho;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

}