package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;

public class EtapaDTO implements Serializable {

	public static final String MINISTERIO = "Ministerio";
	public static final String IMPRENTA = "Imprenta";
	public static final String CENTRO_DE_DISTRIBUCION = "Centro de Distribución";
	public static final String CENTRO_DE_OPERACIONES = "Centro de Operaciones";
	public static final String ESTABLECIMIENTO = "Establecimiento";
	
	private Integer id;
	private String etapa;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEtapa() {
		return etapa;
	}
	public void setEtapa(String etapa) {
		this.etapa = etapa;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((etapa == null) ? 0 : etapa.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		EtapaDTO other = (EtapaDTO) obj;
		if (etapa == null) {
			if (other.etapa != null)
				return false;
		} else if (!etapa.equals(other.etapa))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
