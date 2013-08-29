package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;

import com.google.gwt.view.client.ProvidesKey;

public class ActividadPreviewDTO implements Serializable{

	public static final ProvidesKey<ActividadPreviewDTO> KEY_PROVIDER = new ProvidesKey<ActividadPreviewDTO>() {

		@Override
		public Object getKey(ActividadPreviewDTO item) {
			return (item == null) ? null : item.getCursoId();
		}
	};
	
	private Integer cursoId;
	private String nombreEstablecimiento;
	private String estadoAgenda = "Sin información";
	private String rbd;
	private String tipoEstablecimiento;
	private String curso;
	private String region;
	private String comuna;
	private Integer cuestionariosPadresApoderadosEntregados;
	private Integer cuestionariosPadresApoderadosRecibidos;
	private Integer alumnosTotales;
	private Integer alumnosEvaluados;
	private Integer alumnosSincronizados;
	private Integer materialDefectuoso;
	private Boolean contingencia;
	/**
	 * indica si la toma de la prueba esta en riesgo en base a la contingencia detectada
	 * en visita previa
	 */
	private Boolean contingenciaLimitante;
	
	
	public ActividadPreviewDTO(){}


	public Integer getCursoId() {
		return cursoId;
	}

	public void setCursoId(Integer cursoId) {
		this.cursoId = cursoId;
	}


	public String getNombreEstablecimiento() {
		return nombreEstablecimiento;
	}


	public void setNombreEstablecimiento(String nombreEstablecimiento) {
		this.nombreEstablecimiento = nombreEstablecimiento;
	}


	public String getRbd() {
		return rbd;
	}


	public void setRbd(String rbd) {
		this.rbd = rbd;
	}


	public String getTipoEstablecimiento() {
		return tipoEstablecimiento;
	}


	public void setTipoEstablecimiento(String tipoEstablecimiento) {
		this.tipoEstablecimiento = tipoEstablecimiento;
	}


	public String getCurso() {
		return curso;
	}


	public void setCurso(String curso) {
		this.curso = curso;
	}


	public String getRegion() {
		return region;
	}


	public void setRegion(String region) {
		this.region = region;
	}


	public String getComuna() {
		return comuna;
	}


	public void setComuna(String comuna) {
		this.comuna = comuna;
	}


	public Integer getCuestionariosPadresApoderadosEntregados() {
		return cuestionariosPadresApoderadosEntregados;
	}


	public void setCuestionariosPadresApoderadosEntregados(
			Integer cuestionariosPadresApoderadosEntregados) {
		this.cuestionariosPadresApoderadosEntregados = cuestionariosPadresApoderadosEntregados;
	}


	public Integer getCuestionariosPadresApoderadosRecibidos() {
		return cuestionariosPadresApoderadosRecibidos;
	}


	public void setCuestionariosPadresApoderadosRecibidos(
			Integer cuestionariosPadresApoderadosRecibidos) {
		this.cuestionariosPadresApoderadosRecibidos = cuestionariosPadresApoderadosRecibidos;
	}


	public Integer getAlumnosTotales() {
		return alumnosTotales;
	}


	public void setAlumnosTotales(Integer alumnosTotales) {
		this.alumnosTotales = alumnosTotales;
	}


	public Integer getAlumnosEvaluados() {
		return alumnosEvaluados;
	}


	public void setAlumnosEvaluados(Integer alumnosEvaluados) {
		this.alumnosEvaluados = alumnosEvaluados;
	}


	public Integer getAlumnosSincronizados() {
		return alumnosSincronizados;
	}


	public void setAlumnosSincronizados(Integer alumnosSincronizados) {
		this.alumnosSincronizados = alumnosSincronizados;
	}


	public Integer getMaterialDefectuoso() {
		return materialDefectuoso;
	}


	public void setMaterialDefectuoso(Integer materialDefectuoso) {
		this.materialDefectuoso = materialDefectuoso;
	}


	public Boolean getContingencia() {
		return contingencia;
	}


	public void setContingencia(Boolean contingencia) {
		this.contingencia = contingencia;
	}


	public Boolean getContingenciaLimitante() {
		return contingenciaLimitante;
	}


	public void setContingenciaLimitante(Boolean contingenciaLimitante) {
		this.contingenciaLimitante = contingenciaLimitante;
	}


	public String getEstadoAgenda() {
		return estadoAgenda;
	}


	public void setEstadoAgenda(String estadoAgenda) {
		this.estadoAgenda = estadoAgenda;
	}
}
