package com.dreamer8.yosimce.client.actividad.ui;

import java.util.ArrayList;

import com.dreamer8.yosimce.client.SimcePresenter;
import com.dreamer8.yosimce.shared.dto.EvaluacionSupervisorDTO;
import com.dreamer8.yosimce.shared.dto.EvaluacionSuplenteDTO;
import com.google.gwt.user.client.ui.IsWidget;

public interface AprobarSupervisoresView extends IsWidget {
	
	void setSupervisores(ArrayList<EvaluacionSupervisorDTO> supervisores);
	void setSuplentes(ArrayList<EvaluacionSuplenteDTO> suplentes);
	
	void setSuggestions(ArrayList<String> suggestions);
	
	void setSuplenteSuggestions(ArrayList<String> suggestions);
	
	void setPresenter(AprobarSupervisoresPresenter presenter);
	
	public interface AprobarSupervisoresPresenter extends SimcePresenter{
		void filter(String filter);
		void suplentefilter(String filter);
		void sinc(EvaluacionSupervisorDTO supervisor);
		void sinc(EvaluacionSuplenteDTO suplente);
	}
}
