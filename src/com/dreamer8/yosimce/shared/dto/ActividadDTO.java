package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class ActividadDTO implements Serializable {

	private String nombreEstablecimiento;
	private String rbd;
	private Integer idCurso;
	private String curso;
	private String tipoEstablecimiento;
	private String region;
	private String comuna;
	private EstadoAgendaDTO estadoAplicacion;
	private ArrayList<ContingenciaDTO> contingencias;
	private Date inicioActividad;
	private Date inicioPrueba;
	private Date terminoPrueba;
	private Integer alumnosTotal;
	private Integer alumnosAusentes;
	private Integer alumnosDs;
	private Integer totalCuestionarios;
	private Integer cuestionariosEntregados;
	private Integer cuestionariosRecibidos;
	private Boolean materialContingencia;
	private String detalleUsoMaterialContingencia;
	private Integer evaluacionProcedimientos;
	private DocumentoDTO documento;

	public ActividadDTO() {
		// TODO Auto-generated constructor stub
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

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public String getTipoEstablecimiento() {
		return tipoEstablecimiento;
	}

	public void setTipoEstablecimiento(String tipoEstablecimiento) {
		this.tipoEstablecimiento = tipoEstablecimiento;
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

	public EstadoAgendaDTO getEstadoAplicacion() {
		return estadoAplicacion;
	}

	public void setEstadoAplicacion(EstadoAgendaDTO estadoAplicacion) {
		this.estadoAplicacion = estadoAplicacion;
	}

	public ArrayList<ContingenciaDTO> getContingencias() {
		return contingencias;
	}

	public void setContingencias(ArrayList<ContingenciaDTO> contingencias) {
		this.contingencias = contingencias;
	}

	public Date getInicioActividad() {
		return inicioActividad;
	}

	public void setInicioActividad(Date inicioActividad) {
		this.inicioActividad = inicioActividad;
	}

	public Date getInicioPrueba() {
		return inicioPrueba;
	}

	public void setInicioPrueba(Date inicioPrueba) {
		this.inicioPrueba = inicioPrueba;
	}

	public Date getTerminoPrueba() {
		return terminoPrueba;
	}

	public void setTerminoPrueba(Date terminoPrueba) {
		this.terminoPrueba = terminoPrueba;
	}

	public Integer getAlumnosTotal() {
		return alumnosTotal;
	}

	public void setAlumnosTotal(Integer alumnosTotal) {
		this.alumnosTotal = alumnosTotal;
	}

	public Integer getAlumnosAusentes() {
		return alumnosAusentes;
	}

	public void setAlumnosAusentes(Integer alumnosAusentes) {
		this.alumnosAusentes = alumnosAusentes;
	}

	public Integer getAlumnosDs() {
		return alumnosDs;
	}

	public void setAlumnosDs(Integer alumnosDs) {
		this.alumnosDs = alumnosDs;
	}

	public Integer getTotalCuestionarios() {
		return totalCuestionarios;
	}

	public void setTotalCuestionarios(Integer totalCuestionarios) {
		this.totalCuestionarios = totalCuestionarios;
	}

	public Integer getCuestionariosEntregados() {
		return cuestionariosEntregados;
	}

	public void setCuestionariosEntregados(Integer cuestionariosEntregados) {
		this.cuestionariosEntregados = cuestionariosEntregados;
	}

	public Integer getCuestionariosRecibidos() {
		return cuestionariosRecibidos;
	}

	public void setCuestionariosRecibidos(Integer cuestionariosRecibidos) {
		this.cuestionariosRecibidos = cuestionariosRecibidos;
	}

	public Boolean getMaterialContingencia() {
		return materialContingencia;
	}

	public void setMaterialContingencia(Boolean materialContingencia) {
		this.materialContingencia = materialContingencia;
	}

	public String getDetalleUsoMaterialContingencia() {
		return detalleUsoMaterialContingencia;
	}

	public void setDetalleUsoMaterialContingencia(
			String detalleUsoMaterialContingencia) {
		this.detalleUsoMaterialContingencia = detalleUsoMaterialContingencia;
	}

	public Integer getEvaluacionProcedimientos() {
		return evaluacionProcedimientos;
	}

	public void setEvaluacionProcedimientos(Integer evaluacionProcedimientos) {
		this.evaluacionProcedimientos = evaluacionProcedimientos;
	}

	public DocumentoDTO getDocumento() {
		return documento;
	}

	public void setDocumento(DocumentoDTO documento) {
		this.documento = documento;
	}

	/**
	 * @return the idCurso
	 */
	public Integer getIdCurso() {
		return idCurso;
	}

	/**
	 * @param idCurso
	 *            the idCurso to set
	 */
	public void setIdCurso(Integer idCurso) {
		this.idCurso = idCurso;
	}

}
