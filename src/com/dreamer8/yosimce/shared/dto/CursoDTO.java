package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;

import com.google.gwt.view.client.ProvidesKey;

public class CursoDTO implements Serializable {

	
	public static final ProvidesKey<CursoDTO> KEY_PROVIDER = new ProvidesKey<CursoDTO>() {

		@Override
		public Object getKey(CursoDTO item) {
			return (item == null) ? null : item.getId();
		}
	};
	
	private Integer id;
	
	private String nombre;
	
	private String rbd;
	
	private String nombreEstablecimiento;
	
	private String nivel;
	
	public CursoDTO(){}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombreEstablecimiento() {
		return nombreEstablecimiento;
	}

	public void setNombreEstablecimiento(String nombreEstablecimiento) {
		this.nombreEstablecimiento = nombreEstablecimiento;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public String getRbd() {
		return rbd;
	}

	public void setRbd(String rbd) {
		this.rbd = rbd;
	};
	
	
	
	
}
