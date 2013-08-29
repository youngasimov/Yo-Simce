package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;

public class SincAlumnoDTO implements Serializable {

	public static final int SINC_SIN_INFORMACION = 0;
	public static final int SINC_EN_PROCESO = 1;
	public static final int SINC_EXITOSA = 2;
	public static final int SINC_ERRONEA = 3;

	private Integer idSincronizacion;
	private String nombres;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String rut;
	private String idPendrive;
	private Boolean sincronizado;
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
	public Boolean getSincronizado() {
		return sincronizado;
	}

	public void setSincronizado(Boolean sincronizado) {
		this.sincronizado = sincronizado;
	}

	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public int getSinc() {
		return sinc;
	}

	public void setSinc(int sinc) {
		this.sinc = sinc;
	}
}
