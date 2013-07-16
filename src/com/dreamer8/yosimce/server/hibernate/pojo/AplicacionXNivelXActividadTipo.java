package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 16-07-2013 11:03:56 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * AplicacionXNivelXActividadTipo generated by hbm2java
 */
public class AplicacionXNivelXActividadTipo implements java.io.Serializable {

	private Integer id;
	private AplicacionXNivel aplicacionXNivel;
	private ActividadTipo actividadTipo;
	private Date fechaInicio;
	private Date fechaTermino;
	private List<Actividad> actividads = new ArrayList<Actividad>(0);
	private List<Ruta> rutas = new ArrayList<Ruta>(0);
	private List<SuplenteXCo> suplenteXCos = new ArrayList<SuplenteXCo>(0);

	public AplicacionXNivelXActividadTipo() {
	}

	public AplicacionXNivelXActividadTipo(Integer id) {
		this.id = id;
	}

	public AplicacionXNivelXActividadTipo(Integer id,
			AplicacionXNivel aplicacionXNivel, ActividadTipo actividadTipo,
			Date fechaInicio, Date fechaTermino, List<Actividad> actividads, List<Ruta> rutas,
			List<SuplenteXCo> suplenteXCos) {
		this.id = id;
		this.aplicacionXNivel = aplicacionXNivel;
		this.actividadTipo = actividadTipo;
		this.fechaInicio = fechaInicio;
		this.fechaTermino = fechaTermino;
		this.actividads = actividads;
		this.rutas = rutas;
		this.suplenteXCos = suplenteXCos;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AplicacionXNivel getAplicacionXNivel() {
		return this.aplicacionXNivel;
	}

	public void setAplicacionXNivel(AplicacionXNivel aplicacionXNivel) {
		this.aplicacionXNivel = aplicacionXNivel;
	}

	public ActividadTipo getActividadTipo() {
		return this.actividadTipo;
	}

	public void setActividadTipo(ActividadTipo actividadTipo) {
		this.actividadTipo = actividadTipo;
	}

	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaTermino() {
		return this.fechaTermino;
	}

	public void setFechaTermino(Date fechaTermino) {
		this.fechaTermino = fechaTermino;
	}

	public List<Actividad> getActividads() {
		return this.actividads;
	}

	public void setActividads(List<Actividad> actividads) {
		this.actividads = actividads;
	}

	public List<Ruta> getRutas() {
		return this.rutas;
	}

	public void setRutas(List<Ruta> rutas) {
		this.rutas = rutas;
	}

	public List<SuplenteXCo> getSuplenteXCos() {
		return this.suplenteXCos;
	}

	public void setSuplenteXCos(List<SuplenteXCo> suplenteXCos) {
		this.suplenteXCos = suplenteXCos;
	}

}
