package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 16-08-2013 05:13:17 AM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.List;

import com.dreamer8.yosimce.shared.dto.TipoEstablecimientoDTO;

/**
 * EstablecimientoTipo generated by hbm2java
 */
public class EstablecimientoTipo implements java.io.Serializable {

	public static final String SELECCIONADO = "Titular";
	public static final String SELECCIONADO_NO_APLICA = "Titular No Aplica";
	public static final String REEMPLAZO_1 = "Reemplazo 1";
	public static final String REEMPLAZO_1_SELECCIONADO = "Reemplazo 1 Seleccionado";
	public static final String REEMPLAZO_2 = "Reemplazo 2";
	public static final String REEMPLAZO_2_SELECCIONADO = "Reemplazo 2 Seleccionado";
	public static final String EMERGENTE = "Emergente";

	private Integer id;
	private String nombre;
	private String icono;
	private List<AplicacionXEstablecimiento> aplicacionXEstablecimientos = new ArrayList<AplicacionXEstablecimiento>(
			0);

	public EstablecimientoTipo() {
	}

	public EstablecimientoTipo(Integer id) {
		this.id = id;
	}

	public EstablecimientoTipo(Integer id, String nombre, String icono,
			List<AplicacionXEstablecimiento> aplicacionXEstablecimientos) {
		this.id = id;
		this.nombre = nombre;
		this.icono = icono;
		this.aplicacionXEstablecimientos = aplicacionXEstablecimientos;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getIcono() {
		return this.icono;
	}

	public void setIcono(String icono) {
		this.icono = icono;
	}

	public List<AplicacionXEstablecimiento> getAplicacionXEstablecimientos() {
		return this.aplicacionXEstablecimientos;
	}

	public void setAplicacionXEstablecimientos(
			List<AplicacionXEstablecimiento> aplicacionXEstablecimientos) {
		this.aplicacionXEstablecimientos = aplicacionXEstablecimientos;
	}

	public TipoEstablecimientoDTO getTipoEstablecimientoDTO() {
		TipoEstablecimientoDTO tedto = new TipoEstablecimientoDTO();
		tedto.setId(id);
		tedto.setTipo(nombre);
		return tedto;
	}

}
