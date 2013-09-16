/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.pojo;

import java.io.Serializable;

/**
 * @author jorge
 * 
 */
public class MaterialXActividadId implements Serializable {
	private Integer materialId;
	private Integer actividadId;

	/**
 * 
 */
	public MaterialXActividadId() {
		// TODO Auto-generated constructor stub
	}

	public Integer getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	public Integer getActividadId() {
		return actividadId;
	}

	public void setActividadId(Integer actividadId) {
		this.actividadId = actividadId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((actividadId == null) ? 0 : actividadId.hashCode());
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
		MaterialXActividadId other = (MaterialXActividadId) obj;
		if (actividadId == null) {
			if (other.actividadId != null)
				return false;
		} else if (!actividadId.equals(other.actividadId))
			return false;
		if (materialId == null) {
			if (other.materialId != null)
				return false;
		} else if (!materialId.equals(other.materialId))
			return false;
		return true;
	}

}
