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
	
	
	
}
