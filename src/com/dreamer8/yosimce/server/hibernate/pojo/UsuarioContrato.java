package com.dreamer8.yosimce.server.hibernate.pojo;

import java.io.Serializable;

/**
 * @author jorge
 * 
 */
public class UsuarioContrato implements Serializable {
	private Integer id;
	private UsuarioSeleccion usuarioSeleccion;
	private Boolean pagado;
	private Boolean pagoEnviado;

	public UsuarioContrato() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UsuarioSeleccion getUsuarioSeleccion() {
		return usuarioSeleccion;
	}

	public void setUsuarioSeleccion(UsuarioSeleccion usuarioSeleccion) {
		this.usuarioSeleccion = usuarioSeleccion;
	}

	public Boolean getPagado() {
		return pagado;
	}

	public void setPagado(Boolean pagado) {
		this.pagado = pagado;
	}

	public Boolean getPagoEnviado() {
		return pagoEnviado;
	}

	public void setPagoEnviado(Boolean pagoEnviado) {
		this.pagoEnviado = pagoEnviado;
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
		UsuarioContrato other = (UsuarioContrato) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
