package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 01-08-2013 04:51:27 AM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

import com.dreamer8.yosimce.shared.dto.SectorDTO;

/**
 * Region generated by hbm2java
 */
public class Region implements java.io.Serializable {

	private int id;
	private String nombre;
	private Set provincias = new HashSet(0);

	public Region() {
	}

	public Region(int id) {
		this.id = id;
	}

	public Region(int id, String nombre, Set provincias) {
		this.id = id;
		this.nombre = nombre;
		this.provincias = provincias;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set getProvincias() {
		return this.provincias;
	}

	public void setProvincias(Set provincias) {
		this.provincias = provincias;
	}

	public SectorDTO getSectorDTO() {
		SectorDTO sdto = new SectorDTO();
		sdto.setIdSector(id);
		sdto.setSector(nombre);
		sdto.setTipoSector(SectorDTO.TIPO_REGION);
		return sdto;
	}

}
