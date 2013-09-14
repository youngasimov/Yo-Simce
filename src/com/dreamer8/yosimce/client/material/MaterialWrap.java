package com.dreamer8.yosimce.client.material;

import java.util.ArrayList;

import com.dreamer8.yosimce.shared.dto.DocumentoDTO;
import com.dreamer8.yosimce.shared.dto.HistorialMaterialItemDTO;
import com.dreamer8.yosimce.shared.dto.MaterialDTO;
import com.google.gwt.view.client.ProvidesKey;

public class MaterialWrap {

	public static final ProvidesKey<MaterialWrap> KEY_PROVIDER = new ProvidesKey<MaterialWrap>() {

		@Override
		public Object getKey(MaterialWrap item) {
			return (item == null) ? null : item.getMaterial().getCodigo();
		}
	};
	
	private MaterialDTO material;
	private ArrayList<HistorialMaterialItemDTO> historial;
	private ArrayList<DocumentoDTO> documentos;
	
	private boolean materialUpToDate;
	
	
	public MaterialDTO getMaterial() {
		return material;
	}
	public void setMaterial(MaterialDTO material) {
		this.material = material;
	}
	public ArrayList<HistorialMaterialItemDTO> getHistorial() {
		return historial;
	}
	public void setHistorial(ArrayList<HistorialMaterialItemDTO> historial) {
		this.historial = historial;
	}
	public ArrayList<DocumentoDTO> getDocumentos() {
		return documentos;
	}
	public void setDocumentos(ArrayList<DocumentoDTO> documentos) {
		this.documentos = documentos;
	}
	public boolean isMaterialUpToDate() {
		return materialUpToDate;
	}
	public void setMaterialUpToDate(boolean materialUpToDate) {
		this.materialUpToDate = materialUpToDate;
	}
}
