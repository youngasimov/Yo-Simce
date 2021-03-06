package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 16-08-2013 05:13:17 AM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.List;

import com.dreamer8.yosimce.shared.dto.AplicacionDTO;

/**
 * Aplicacion generated by hbm2java
 */
public class Aplicacion implements java.io.Serializable {

	private Integer id;
	private String nombre;
	private String slug;
	private Integer tiempoDetencionEstablecimiento;
	private Integer diasPreviosSinMaterial;
	private boolean disponibilidadDiaria;
	private List<CentroRegional> centroRegionals = new ArrayList<CentroRegional>(0);
	private List<AplicacionXNivel> aplicacionXNivels = new ArrayList<AplicacionXNivel>(0);
	private List<Lugar> lugars = new ArrayList<Lugar>(0);
	private List<AplicacionXEstablecimiento> aplicacionXEstablecimientos = new ArrayList<AplicacionXEstablecimiento>(0);
	private List<FaseXAplicacion> faseXAplicacions = new ArrayList<FaseXAplicacion>(0);
	private List<DocumentoTipo> documentoTipos = new ArrayList<DocumentoTipo>(0);
	private List<CcCapacitacion> ccCapacitacions = new ArrayList<CcCapacitacion>(0);
	private List<AplicacionXUsuarioTipo> aplicacionXUsuarioTipos = new ArrayList<AplicacionXUsuarioTipo>(0);
	private List<DocumentoEstado> documentoEstados = new ArrayList<DocumentoEstado>(0);

	public Aplicacion() {
	}

	public Aplicacion(Integer id, String slug, boolean disponibilidadDiaria) {
		this.id = id;
		this.slug = slug;
		this.disponibilidadDiaria = disponibilidadDiaria;
	}

	public Aplicacion(Integer id, String nombre, String slug,
			Integer tiempoDetencionEstablecimiento,
			Integer diasPreviosSinMaterial, boolean disponibilidadDiaria,
			List<CentroRegional> centroRegionals, List<AplicacionXNivel> aplicacionXNivels, List<Lugar> lugars,
			List<AplicacionXEstablecimiento> aplicacionXEstablecimientos, List<FaseXAplicacion> faseXAplicacions,
			List<DocumentoTipo> documentoTipos, List<CcCapacitacion> ccCapacitacions,
			List<AplicacionXUsuarioTipo> aplicacionXUsuarioTipos, List<DocumentoEstado> documentoEstados) {
		this.id = id;
		this.nombre = nombre;
		this.slug = slug;
		this.tiempoDetencionEstablecimiento = tiempoDetencionEstablecimiento;
		this.diasPreviosSinMaterial = diasPreviosSinMaterial;
		this.disponibilidadDiaria = disponibilidadDiaria;
		this.centroRegionals = centroRegionals;
		this.aplicacionXNivels = aplicacionXNivels;
		this.lugars = lugars;
		this.aplicacionXEstablecimientos = aplicacionXEstablecimientos;
		this.faseXAplicacions = faseXAplicacions;
		this.documentoTipos = documentoTipos;
		this.ccCapacitacions = ccCapacitacions;
		this.aplicacionXUsuarioTipos = aplicacionXUsuarioTipos;
		this.documentoEstados = documentoEstados;
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

	public String getSlug() {
		return this.slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public Integer getTiempoDetencionEstablecimiento() {
		return this.tiempoDetencionEstablecimiento;
	}

	public void setTiempoDetencionEstablecimiento(
			Integer tiempoDetencionEstablecimiento) {
		this.tiempoDetencionEstablecimiento = tiempoDetencionEstablecimiento;
	}

	public Integer getDiasPreviosSinMaterial() {
		return this.diasPreviosSinMaterial;
	}

	public void setDiasPreviosSinMaterial(Integer diasPreviosSinMaterial) {
		this.diasPreviosSinMaterial = diasPreviosSinMaterial;
	}

	public boolean isDisponibilidadDiaria() {
		return this.disponibilidadDiaria;
	}

	public void setDisponibilidadDiaria(boolean disponibilidadDiaria) {
		this.disponibilidadDiaria = disponibilidadDiaria;
	}

	public List<CentroRegional> getCentroRegionals() {
		return this.centroRegionals;
	}

	public void setCentroRegionals(List<CentroRegional> centroRegionals) {
		this.centroRegionals = centroRegionals;
	}

	public List<AplicacionXNivel> getAplicacionXNivels() {
		return this.aplicacionXNivels;
	}

	public void setAplicacionXNivels(List<AplicacionXNivel> aplicacionXNivels) {
		this.aplicacionXNivels = aplicacionXNivels;
	}

	public List<Lugar> getLugars() {
		return this.lugars;
	}

	public void setLugars(List<Lugar> lugars) {
		this.lugars = lugars;
	}

	public List<AplicacionXEstablecimiento> getAplicacionXEstablecimientos() {
		return this.aplicacionXEstablecimientos;
	}

	public void setAplicacionXEstablecimientos(List<AplicacionXEstablecimiento> aplicacionXEstablecimientos) {
		this.aplicacionXEstablecimientos = aplicacionXEstablecimientos;
	}

	public List<FaseXAplicacion> getFaseXAplicacions() {
		return this.faseXAplicacions;
	}

	public void setFaseXAplicacions(List<FaseXAplicacion> faseXAplicacions) {
		this.faseXAplicacions = faseXAplicacions;
	}

	public List<DocumentoTipo> getDocumentoTipos() {
		return this.documentoTipos;
	}

	public void setDocumentoTipos(List<DocumentoTipo> documentoTipos) {
		this.documentoTipos = documentoTipos;
	}

	public List<CcCapacitacion> getCcCapacitacions() {
		return this.ccCapacitacions;
	}

	public void setCcCapacitacions(List<CcCapacitacion> ccCapacitacions) {
		this.ccCapacitacions = ccCapacitacions;
	}

	public List<AplicacionXUsuarioTipo> getAplicacionXUsuarioTipos() {
		return this.aplicacionXUsuarioTipos;
	}

	public void setAplicacionXUsuarioTipos(List<AplicacionXUsuarioTipo> aplicacionXUsuarioTipos) {
		this.aplicacionXUsuarioTipos = aplicacionXUsuarioTipos;
	}

	public List<DocumentoEstado> getDocumentoEstados() {
		return this.documentoEstados;
	}

	public void setDocumentoEstados(List<DocumentoEstado> documentoEstados) {
		this.documentoEstados = documentoEstados;
	}

	public AplicacionDTO getAplicacionDTO() {
		AplicacionDTO adto = new AplicacionDTO();
		adto.setId(id);
		adto.setNombre(nombre);
		return adto;
	}

}
