package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;

public class EstadoMaterialItemDTO implements Serializable {

	private Integer idRegion;
	private Integer idComuna;
	private Integer idZona;
	private Integer totalAgencia;
	private Integer totalImprenta;
	private Integer totalCentro;
	private Integer totalEstablecimiento;
	private Integer totalCaptura;
	private Integer totalBodega;

	public EstadoMaterialItemDTO() {
		totalImprenta = 0;
		totalCentro = 0;
		totalEstablecimiento = 0;
		totalCaptura = 0;
		totalBodega = 0;
		totalAgencia = 0;
	}

	public Integer getTotalAgencia() {
		return totalAgencia;
	}

	public void setTotalAgencia(Integer totalAgencia) {
		this.totalAgencia = totalAgencia;
	}

	public Integer getTotalImprenta() {
		return totalImprenta;
	}

	public void setTotalImprenta(Integer totalImprenta) {
		this.totalImprenta = totalImprenta;
	}

	public Integer getTotalCentro() {
		return totalCentro;
	}

	public void setTotalCentro(Integer totalCentro) {
		this.totalCentro = totalCentro;
	}

	public Integer getTotalEstablecimiento() {
		return totalEstablecimiento;
	}

	public void setTotalEstablecimiento(Integer totalEstablecimiento) {
		this.totalEstablecimiento = totalEstablecimiento;
	}

	public Integer getTotalCaptura() {
		return totalCaptura;
	}

	public void setTotalCaptura(Integer totalCaptura) {
		this.totalCaptura = totalCaptura;
	}

	public Integer getTotalBodega() {
		return totalBodega;
	}

	public void setTotalBodega(Integer totalBodega) {
		this.totalBodega = totalBodega;
	}

	public Integer getIdRegion() {
		return idRegion;
	}

	public void setIdRegion(Integer idRegion) {
		this.idRegion = idRegion;
	}

	public Integer getIdComuna() {
		return idComuna;
	}

	public void setIdComuna(Integer idComuna) {
		this.idComuna = idComuna;
	}

	public Integer getIdZona() {
		return idZona;
	}

	public void setIdZona(Integer idZona) {
		this.idZona = idZona;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idComuna == null) ? 0 : idComuna.hashCode());
		result = prime * result + ((idRegion == null) ? 0 : idRegion.hashCode());
		result = prime * result + ((idZona == null) ? 0 : idZona.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EstadoMaterialItemDTO other = (EstadoMaterialItemDTO) obj;
		if (idComuna == null) {
			if (other.idComuna != null)
				return false;
		} else if (!idComuna.equals(other.idComuna))
			return false;
		if (idRegion == null) {
			if (other.idRegion != null)
				return false;
		} else if (!idRegion.equals(other.idRegion))
			return false;
		if (idZona == null) {
			if (other.idZona != null)
				return false;
		} else if (!idZona.equals(other.idZona))
			return false;
		return true;
	}

}
