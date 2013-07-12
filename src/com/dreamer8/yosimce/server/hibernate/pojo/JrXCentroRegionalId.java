package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 12-07-2013 05:32:10 AM by Hibernate Tools 3.4.0.CR1

/**
 * JrXCentroRegionalId generated by hbm2java
 */
public class JrXCentroRegionalId implements java.io.Serializable {

	private Integer jrId;
	private Integer centroRegionalId;

	public JrXCentroRegionalId() {
	}

	public JrXCentroRegionalId(Integer jrId, Integer centroRegionalId) {
		this.jrId = jrId;
		this.centroRegionalId = centroRegionalId;
	}

	public Integer getJrId() {
		return this.jrId;
	}

	public void setJrId(Integer jrId) {
		this.jrId = jrId;
	}

	public Integer getCentroRegionalId() {
		return this.centroRegionalId;
	}

	public void setCentroRegionalId(Integer centroRegionalId) {
		this.centroRegionalId = centroRegionalId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof JrXCentroRegionalId))
			return false;
		JrXCentroRegionalId castOther = (JrXCentroRegionalId) other;

		return (this.getJrId() == castOther.getJrId())
				&& (this.getCentroRegionalId() == castOther
						.getCentroRegionalId());
	}

	public int hashCode() {
		Integer result = 17;

		result = 37 * result + this.getJrId();
		result = 37 * result + this.getCentroRegionalId();
		return result;
	}

}
