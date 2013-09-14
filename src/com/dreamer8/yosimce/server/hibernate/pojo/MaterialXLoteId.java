/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.pojo;

import java.io.Serializable;

/**
 * @author jorge
 * 
 */
public class MaterialXLoteId implements Serializable {
	private Integer materialId;
	private Integer loteId;

	public MaterialXLoteId() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the materialId
	 */
	public Integer getMaterialId() {
		return materialId;
	}

	/**
	 * @param materialId
	 *            the materialId to set
	 */
	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	/**
	 * @return the loteId
	 */
	public Integer getLoteId() {
		return loteId;
	}

	/**
	 * @param loteId
	 *            the loteId to set
	 */
	public void setLoteId(Integer loteId) {
		this.loteId = loteId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((loteId == null) ? 0 : loteId.hashCode());
		result = prime * result
				+ ((materialId == null) ? 0 : materialId.hashCode());
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
		MaterialXLoteId other = (MaterialXLoteId) obj;
		if (loteId == null) {
			if (other.loteId != null)
				return false;
		} else if (!loteId.equals(other.loteId))
			return false;
		if (materialId == null) {
			if (other.materialId != null)
				return false;
		} else if (!materialId.equals(other.materialId))
			return false;
		return true;
	}

}
