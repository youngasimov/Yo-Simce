package com.dreamer8.yosimce.client.actividad.ui;

import java.util.ArrayList;

import com.dreamer8.yosimce.client.SimcePresenter;
import com.dreamer8.yosimce.shared.dto.EvaluacionUsuarioDTO;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.user.client.ui.IsWidget;

public interface AprobarSupervisoresView extends IsWidget {
	
	void setPuntualidadFieldUpdater(FieldUpdater<EvaluacionUsuarioDTO,Boolean> updater);
	void setPresentacionFieldUpdater(FieldUpdater<EvaluacionUsuarioDTO,Boolean> updater);
	void setFormularioFieldUpdater(FieldUpdater<EvaluacionUsuarioDTO,Boolean> updater);
	void setGeneralFieldUpdater(FieldUpdater<EvaluacionUsuarioDTO,Boolean> updater);
	
	void setSupervisores(ArrayList<EvaluacionUsuarioDTO> supervisores);
	
	public interface AprobarSupervisoresPresenter extends SimcePresenter{
		
	}
}
