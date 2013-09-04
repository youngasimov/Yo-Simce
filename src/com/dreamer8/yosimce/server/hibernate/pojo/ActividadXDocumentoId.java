package com.dreamer8.yosimce.server.hibernate.pojo;

/**
 * @author jorge
 * 
 */
public class ActividadXDocumentoId implements java.io.Serializable {
	private Integer idActividad;
	private Integer idDocumento;

	public ActividadXDocumentoId() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the idActividad
	 */
	public Integer getIdActividad() {
		return idActividad;
	}

	/**
	 * @param idActividad
	 *            the idActividad to set
	 */
	public void setIdActividad(Integer idActividad) {
		this.idActividad = idActividad;
	}

	/**
	 * @return the idDocumento
	 */
	public Integer getIdDocumento() {
		return idDocumento;
	}

	/**
	 * @param idDocumento
	 *            the idDocumento to set
	 */
	public void setIdDocumento(Integer idDocumento) {
		this.idDocumento = idDocumento;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idActividad == null) ? 0 : idActividad.hashCode());
		result = prime * result
				+ ((idDocumento == null) ? 0 : idDocumento.hashCode());
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
		ActividadXDocumentoId other = (ActividadXDocumentoId) obj;
		if (idActividad == null) {
			if (other.idActividad != null)
				return false;
		} else if (!idActividad.equals(other.idActividad))
			return false;
		if (idDocumento == null) {
			if (other.idDocumento != null)
				return false;
		} else if (!idDocumento.equals(other.idDocumento))
			return false;
		return true;
	}

}
