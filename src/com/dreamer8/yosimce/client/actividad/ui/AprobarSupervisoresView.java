package com.dreamer8.yosimce.client.actividad.ui;

import java.util.ArrayList;

import com.dreamer8.yosimce.client.SimcePresenter;
import com.dreamer8.yosimce.shared.dto.EvaluacionSupervisorDTO;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.user.client.ui.IsWidget;

public interface AprobarSupervisoresView extends IsWidget {
	
	void setPuntualidadFieldUpdater(FieldUpdater<EvaluacionSupervisorDTO,Boolean> updater);
	void setPresentacionFieldUpdater(FieldUpdater<EvaluacionSupervisorDTO,Boolean> updater);
	void setGeneralFieldUpdater(FieldUpdater<EvaluacionSupervisorDTO,Boolean> updater);
	
	void setSupervisores(ArrayList<EvaluacionSupervisorDTO> supervisores);
	
	void setPresenter(AprobarSupervisoresPresenter presenter);
	
	public interface AprobarSupervisoresPresenter extends SimcePresenter{
		ArrayList<EvaluacionSupervisorDTO> getEvaluacionesByRbd(int supervisorId);
	}
}
