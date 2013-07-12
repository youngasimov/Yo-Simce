package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 12-07-2013 05:32:10 AM by Hibernate Tools 3.4.0.CR1

/**
 * CoXEstablecimientoId generated by hbm2java
 */
public class CoXEstablecimientoId implements java.io.Serializable {

	private Integer coId;
	private Integer establecimientoId;

	public CoXEstablecimientoId() {
	}

	public CoXEstablecimientoId(Integer coId, Integer establecimientoId) {
		this.coId = coId;
		this.establecimientoId = establecimientoId;
	}

	public Integer getCoId() {
		return this.coId;
	}

	public void setCoId(Integer coId) {
		this.coId = coId;
	}

	public Integer getEstablecimientoId() {
		return this.establecimientoId;
	}

	public void setEstablecimientoId(Integer establecimientoId) {
		this.establecimientoId = establecimientoId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof CoXEstablecimientoId))
			return false;
		CoXEstablecimientoId castOther = (CoXEstablecimientoId) other;

		return (this.getCoId() == castOther.getCoId())
				&& (this.getEstablecimientoId() == castOther
						.getEstablecimientoId());
	}

	public int hashCode() {
		Integer result = 17;

		result = 37 * result + this.getCoId();
		result = 37 * result + this.getEstablecimientoId();
		return result;
	}

}
