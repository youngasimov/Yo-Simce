package com.dreamer8.yosimce.client.actividad.ui;

import com.dreamer8.yosimce.client.SimcePresenter;
import com.dreamer8.yosimce.shared.dto.CursoDTO;
import com.dreamer8.yosimce.shared.dto.SincAlumnoDTO;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.view.client.HasData;

public interface SincronizacionView extends IsWidget {

	void setIdMaterialFieldUpdater(FieldUpdater<SincAlumnoDTO, String> updater);
	void setEstadoFieldUpdater(FieldUpdater<SincAlumnoDTO, Boolean> updater);
	void setComentarioFieldUpdater(FieldUpdater<SincAlumnoDTO, String> updater);
	void updateTable();
	HasData<SincAlumnoDTO> getDataDisplay();
	void setTotalALumnos(int total);
	void setCurso(CursoDTO curso);
	void setPresenter(SincronizacionPresenter presenter);
	
	void setGuardarButtonEnabled(boolean enabled);
	
	public interface SincronizacionPresenter extends SimcePresenter{
		
		void onCambiarCursoButtonClick();
		void onAgregarAlumnoButtonClick();
		void onGuardarTodoButtonClick();
		
	}
}
