package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;

import com.google.gwt.view.client.ProvidesKey;

public class ItemReporteMaterialDTO implements Serializable {

	public static final ProvidesKey<ItemReporteMaterialDTO> KEY_PROVIDER = new ProvidesKey<ItemReporteMaterialDTO>() {

		@Override
		public Object getKey(ItemReporteMaterialDTO item) {
			return (item == null) ? null : item.getIdMaterial();
		}
	};
	
	
	private Integer idMaterial;
	private Integer idCentro;
	private Integer idCurso;
	private Integer idTipo;
	private Integer idEstablecimiento;
	private Integer idEtapa;
	private String codigo;
	
	public ItemReporteMaterialDTO(){
		
	}
	
	public Integer getIdMaterial() {
		return idMaterial;
	}
	public void setIdMaterial(Integer idMaterial) {
		this.idMaterial = idMaterial;
	}
	public Integer getIdCentro() {
		return idCentro;
	}
	public void setIdCentro(Integer idCentro) {
		this.idCentro = idCentro;
	}
	public Integer getIdCurso() {
		return idCurso;
	}
	public void setIdCurso(Integer idCurso) {
		this.idCurso = idCurso;
	}
	public Integer getIdTipo() {
		return idTipo;
	}
	public void setIdTipo(Integer idTipo) {
		this.idTipo = idTipo;
	}
	public Integer getIdEstablecimiento() {
		return idEstablecimiento;
	}
	public void setIdEstablecimiento(Integer idEstablecimiento) {
		this.idEstablecimiento = idEstablecimiento;
	}
	public Integer getIdEtapa() {
		return idEtapa;
	}
	public void setIdEtapa(Integer idEtapa) {
		this.idEtapa = idEtapa;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
}
