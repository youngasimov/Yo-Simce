package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;

public class EstadoActividadItemDTO implements Serializable {

	private Integer idRegion;
	private Integer idComuna;
	private Integer idZona;
	private Double totalSinInformacion;
	private Double totalAnulada;
	private Double totalPorConfirmar;
	private Double totalConfirmado;
	private Double totalConfirmadoConCambios;
	private Double totalRealizada;

	public EstadoActividadItemDTO() {
		totalSinInformacion = 0.0;
		totalAnulada = 0.0;
		totalPorConfirmar = 0.0;
		totalConfirmado = 0.0;
		totalConfirmadoConCambios = 0.0;
		totalRealizada = 0.0;
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

	public Double getTotalSinInformacion() {
		return totalSinInformacion;
	}

	public void setTotalSinInformacion(Double totalSinInformacion) {
		this.totalSinInformacion = totalSinInformacion;
	}

	public Double getTotalAnulada() {
		return totalAnulada;
	}

	public void setTotalAnulada(Double totalAnulada) {
		this.totalAnulada = totalAnulada;
	}

	public Double getTotalPorConfirmar() {
		return totalPorConfirmar;
	}

	public void setTotalPorConfirmar(Double totalPorConfirmar) {
		this.totalPorConfirmar = totalPorConfirmar;
	}

	public Double getTotalConfirmado() {
		return totalConfirmado;
	}

	public void setTotalConfirmado(Double totalConfirmado) {
		this.totalConfirmado = totalConfirmado;
	}

	public Double getTotalConfirmadoConCambios() {
		return totalConfirmadoConCambios;
	}

	public void setTotalConfirmadoConCambios(Double totalConfirmadoConCambios) {
		this.totalConfirmadoConCambios = totalConfirmadoConCambios;
	}

	public Double getTotalRealizada() {
		return totalRealizada;
	}

	public void setTotalRealizada(Double totalRealizada) {
		this.totalRealizada = totalRealizada;
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
		EstadoActividadItemDTO other = (EstadoActividadItemDTO) obj;
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
