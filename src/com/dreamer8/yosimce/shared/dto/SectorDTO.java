package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SectorDTO implements Serializable {
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

}
