package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("serial")
public class DetallesMaterialDTO implements Serializable {

	private EmplazamientoDTO centroOperacionAsignado;
	private EmplazamientoDTO centroOperacionIngresado;
	private ArrayList<HistorialMaterialItemDTO> historial;
	// String corresponde al folio de la guía de despacho
	private HashMap<String, DocumentoDTO> documentos;
	
	/**
	 * 
	 */
	public DetallesMaterialDTO() {
		// TODO Auto-generated constructor stub
	}

	public EmplazamientoDTO getCentroOperacionAsignado() {
		return centroOperacionAsignado;
	}



	public void setCentroOperacionAsignado(EmplazamientoDTO centroOperacionAsignado) {
		this.centroOperacionAsignado = centroOperacionAsignado;
	}



	public EmplazamientoDTO getCentroOperacionIngresado() {
		return centroOperacionIngresado;
	}



	public void setCentroOperacionIngresado(
			EmplazamientoDTO centroOperacionIngresado) {
		this.centroOperacionIngresado = centroOperacionIngresado;
	}



	public ArrayList<HistorialMaterialItemDTO> getHistorial() {
		return historial;
	}

	public void setHistorial(ArrayList<HistorialMaterialItemDTO> historial) {
		this.historial = historial;
	}

	public HashMap<String, DocumentoDTO> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(HashMap<String, DocumentoDTO> documentos) {
		this.documentos = documentos;
	}
}
