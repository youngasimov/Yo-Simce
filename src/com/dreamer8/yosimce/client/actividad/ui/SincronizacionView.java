package com.dreamer8.yosimce.client.actividad.ui;

import java.util.ArrayList;

import com.dreamer8.yosimce.client.SimcePresenter;
import com.dreamer8.yosimce.shared.dto.CursoDTO;
import com.dreamer8.yosimce.shared.dto.EstadoSincronizacionDTO;
import com.dreamer8.yosimce.shared.dto.SincAlumnoDTO;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.user.client.ui.IsWidget;

public interface SincronizacionView extends IsWidget {

	void setIdMaterialFieldUpdater(FieldUpdater<SincAlumnoDTO, String> updater);
	void setEstadoFieldUpdater(FieldUpdater<SincAlumnoDTO, String> updater);
	void setFormFieldUpdater(FieldUpdater<SincAlumnoDTO, Boolean> updater);
	void setComentarioFieldUpdater(FieldUpdater<SincAlumnoDTO, String> updater);
	void setEstadosSincronizacion(ArrayList<EstadoSincronizacionDTO> estados);
	void updateTable();
	void setAlumnos(ArrayList<SincAlumnoDTO> alumnos);
	void setTotalALumnos(int total);
	void setCurso(CursoDTO curso);
	void setPresenter(SincronizacionPresenter presenter);
	void clear();
	void setGuardarButtonEnabled(boolean enabled);
	
	void setMaterialDefectusoVisivility(boolean visible);
	
	public interface SincronizacionPresenter extends SimcePresenter{
		
		void onCambiarCursoButtonClick();
		void onAgregarAlumnoButtonClick();
		void onGuardarTodoButtonClick();
		
	}
}
