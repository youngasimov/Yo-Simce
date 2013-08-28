package com.dreamer8.yosimce.client.actividad.ui;

import java.util.ArrayList;

import com.dreamer8.yosimce.client.SimcePresenter;
import com.dreamer8.yosimce.shared.dto.EstadoSincronizacionDTO;
import com.dreamer8.yosimce.shared.dto.MaterialDefectuosoDTO;
import com.google.gwt.user.client.ui.IsWidget;

public interface MaterialDefectuosoView extends IsWidget {

	void setEstadosSincronizacion(ArrayList<EstadoSincronizacionDTO> estados);
	void setMaterialDefectuoso(ArrayList<MaterialDefectuosoDTO> material);
	
	void setPresenter(MaterialDefectuosoPresenter presenter);
	
	public interface MaterialDefectuosoPresenter extends SimcePresenter{
		
		void onRemoveMaterialDefectuoso(MaterialDefectuosoDTO material);
		void onAddMaterialDefectuoso(MaterialDefectuosoDTO material);
	}
	
}
