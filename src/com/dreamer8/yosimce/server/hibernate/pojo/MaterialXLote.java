/**
 * 
 */
package com.dreamer8.yosimce.server.hibernate.pojo;

import java.io.Serializable;

/**
 * @author jorge
 * 
 */
public class MaterialXLote implements Serializable {
	private MaterialXLoteId id;

	public MaterialXLote() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the id
	 */
	public MaterialXLoteId getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(MaterialXLoteId id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		MaterialXLote other = (MaterialXLote) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
