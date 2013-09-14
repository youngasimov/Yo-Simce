package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


@SuppressWarnings("serial")
public class DetallesMaterialDTO implements Serializable {

	
	
	private String NombreCentroOperacion;
	private ArrayList<HistorialMaterialItemDTO> historial;
	
	private HashMap<String,DocumentoDTO> documentos;

	public String getNombreCentroOperacion() {
		return NombreCentroOperacion;
	}

	public void setNombreCentroOperacion(String nombreCentroOperacion) {
		NombreCentroOperacion = nombreCentroOperacion;
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
