package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;

import com.google.gwt.view.client.ProvidesKey;

public class SincAlumnoDTO implements Serializable {

	public static final int SINC_SIN_INFORMACION = 0;
	public static final int SINC_EN_PROCESO = 1;
	public static final int SINC_EXITOSA = 2;
	public static final int SINC_ERRONEA = 3;
	
	public static final ProvidesKey<SincAlumnoDTO> KEY_PROVIDER = new ProvidesKey<SincAlumnoDTO>() {

		@Override
		public Object getKey(SincAlumnoDTO item) {
			return (item == null) ? null : item.getIdSincronizacion();
		}
	};
	
	private Integer idSincronizacion;
	private String nombres;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String rut;
	private String tipoAlumno;
	private String idPendrive;
	private EstadoSincronizacionDTO estado;
	private Boolean entregoFormulario;
	private String comentario;
	
	//para uso dentro de la tabla
	private int sinc;
	
	public SincAlumnoDTO(){}
	
	public Integer getIdSincronizacion() {
		return idSincronizacion;
	}
	public void setIdSincronizacion(Integer idSincronizacion) {
		this.idSincronizacion = idSincronizacion;
	}
	
	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getRut() {
		return rut;
	}
	public void setRut(String rut) {
		this.rut = rut;
	}
	public String getIdPendrive() {
		return idPendrive;
	}
	public void setIdPendrive(String idPendrive) {
		this.idPendrive = idPendrive;
	}

	public EstadoSincronizacionDTO getEstado() {
		return estado;
	}

	public void setEstado(EstadoSincronizacionDTO estado) {
		this.estado = estado;
	}

	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Boolean getEntregoFormulario() {
		return entregoFormulario;
	}

	public void setEntregoFormulario(Boolean entregoFormulario) {
		this.entregoFormulario = entregoFormulario;
	}

	public int getSinc() {
		return sinc;
	}

	public void setSinc(int sinc) {
		this.sinc = sinc;
	}

	public String getTipoAlumno() {
		return tipoAlumno;
	}

	public void setTipoAlumno(String tipoAlumno) {
		this.tipoAlumno = tipoAlumno;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((apellidoMaterno == null) ? 0 : apellidoMaterno.hashCode());
		result = prime * result
				+ ((apellidoPaterno == null) ? 0 : apellidoPaterno.hashCode());
		result = prime * result + ((nombres == null) ? 0 : nombres.hashCode());
		result = prime * result + ((rut == null) ? 0 : rut.hashCode());
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
		SincAlumnoDTO other = (SincAlumnoDTO) obj;
		if (apellidoMaterno == null) {
			if (other.apellidoMaterno != null)
				return false;
		} else if (!apellidoMaterno.equals(other.apellidoMaterno))
			return false;
		if (apellidoPaterno == null) {
			if (other.apellidoPaterno != null)
				return false;
		} else if (!apellidoPaterno.equals(other.apellidoPaterno))
			return false;
		if (nombres == null) {
			if (other.nombres != null)
				return false;
		} else if (!nombres.equals(other.nombres))
			return false;
		if (rut == null) {
			if (other.rut != null)
				return false;
		} else if (!rut.equals(other.rut))
			return false;
		return true;
	}
	
	
}
