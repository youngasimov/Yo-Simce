package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SectorDTO implements Serializable, Comparable<SectorDTO> {
	public static final Integer TIPO_REGION = 1;
	public static final Integer TIPO_PROVINCIA = 2;
	public static final Integer TIPO_COMUNA = 3;
	public static final Integer TIPO_ZONA=4;
	private Integer idSector;
	private String sector;
	private Integer tipoSector;

	public SectorDTO() {
		super();
	}

	public Integer getIdSector() {
		return idSector;
	}

	public void setIdSector(Integer idSector) {
		this.idSector = idSector;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public Integer getTipoSector() {
		return tipoSector;
	}

	public void setTipoSector(Integer tipoSector) {
		this.tipoSector = tipoSector;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idSector == null) ? 0 : idSector.hashCode());
		result = prime * result + ((sector == null) ? 0 : sector.hashCode());
		result = prime * result
				+ ((tipoSector == null) ? 0 : tipoSector.hashCode());
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
		SectorDTO other = (SectorDTO) obj;
		if (idSector == null) {
			if (other.idSector != null)
				return false;
		} else if (!idSector.equals(other.idSector))
			return false;
		if (sector == null) {
			if (other.sector != null)
				return false;
		} else if (!sector.equals(other.sector))
			return false;
		if (tipoSector == null) {
			if (other.tipoSector != null)
				return false;
		} else if (!tipoSector.equals(other.tipoSector))
			return false;
		return true;
	}

	@Override
	public int compareTo(SectorDTO o) {
		return idSector.compareTo(o.idSector);
	}
	
	

}
