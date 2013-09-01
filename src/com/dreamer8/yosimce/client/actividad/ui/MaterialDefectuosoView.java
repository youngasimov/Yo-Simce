package com.dreamer8.yosimce.client.actividad.ui;

import java.util.ArrayList;

import com.dreamer8.yosimce.client.SimcePresenter;
import com.dreamer8.yosimce.shared.dto.CursoDTO;
import com.dreamer8.yosimce.shared.dto.EstadoSincronizacionDTO;
import com.dreamer8.yosimce.shared.dto.MaterialDefectuosoDTO;
import com.google.gwt.user.client.ui.IsWidget;

public interface MaterialDefectuosoView extends IsWidget {

	void setCurso(CursoDTO curso);
	void setEstadosSincronizacion(ArrayList<EstadoSincronizacionDTO> estados);
	void setMaterialDefectuoso(ArrayList<MaterialDefectuosoDTO> material);
	
	void setPresenter(MaterialDefectuosoPresenter presenter);
	
	void setAddMaterialDefectuosoPanelVisivility(boolean visible);
	
	public interface MaterialDefectuosoPresenter extends SimcePresenter{
		void onCambiarCursoClick();
		void onRemoveMaterialDefectuoso(MaterialDefectuosoDTO material);
		void onAddMaterialDefectuoso(MaterialDefectuosoDTO material);
	}
	
}
