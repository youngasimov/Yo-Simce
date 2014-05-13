package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;

import com.google.gwt.view.client.ProvidesKey;

public class EstablecimientoDTO implements Serializable{

	public static final ProvidesKey<EstablecimientoDTO> KEY_PROVIDER = new ProvidesKey<EstablecimientoDTO>() {

		@Override
		public Object getKey(EstablecimientoDTO item) {
			return (item == null) ? null : item.getId();
		}
	};
	
	private Integer id;
	private String rbd;
	private String name;
	private String estadoActividad;
	private String estadoAgendamiento;
	private String estadoMaterialSincronizado;
	private String codigo;
	
	
	public EstablecimientoDTO() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEstadoActividad() {
		return estadoActividad;
	}

	public void setEstadoActividad(String estadoActividad) {
		this.estadoActividad = estadoActividad;
	}

	public String getEstadoAgendamiento() {
		return estadoAgendamiento;
	}

	public void setEstadoAgendamiento(String estadoAgendamiento) {
		this.estadoAgendamiento = estadoAgendamiento;
	}

	public String getEstadoMaterialSincronizado() {
		return estadoMaterialSincronizado;
	}

	public void setEstadoMaterialSincronizado(String estadoMaterialSincronizado) {
		this.estadoMaterialSincronizado = estadoMaterialSincronizado;
	}

	public String getRbd() {
		return rbd;
	}

	public void setRbd(String rbd) {
		this.rbd = rbd;
	}
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
}
