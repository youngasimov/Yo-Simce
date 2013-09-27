package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;
import java.util.Date;


@SuppressWarnings("serial")
public class HistorialMaterialItemDTO implements Serializable {


	private Integer id;
	private Date fecha;
	private String desde;
	private String hacia;
	private Integer idOrigen;
	private Integer idDestino;
	private UserDTO receptor;

	/**
	 * 
	 */
	public HistorialMaterialItemDTO() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getDesde() {
		return desde;
	}

	public void setDesde(String desde) {
		this.desde = desde;
	}

	public String getHacia() {
		return hacia;
	}

	public void setHacia(String hacia) {
		this.hacia = hacia;
	}

	public UserDTO getReceptor() {
		return receptor;
	}

	public void setReceptor(UserDTO receptor) {
		this.receptor = receptor;
	}

	public Integer getIdOrigen() {
		return idOrigen;
	}

	public void setIdOrigen(Integer idOrigen) {
		this.idOrigen = idOrigen;
	}

	public Integer getIdDestino() {
		return idDestino;
	}

	public void setIdDestino(Integer idDestino) {
		this.idDestino = idDestino;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
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
		HistorialMaterialItemDTO other = (HistorialMaterialItemDTO) obj;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
