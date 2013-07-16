package com.dreamer8.yosimce.client.planandresult.ui;

import java.util.ArrayList;

import com.dreamer8.yosimce.shared.dto.ActividadTipoDTO;
import com.dreamer8.yosimce.shared.dto.AplicacionDTO;
import com.dreamer8.yosimce.shared.dto.NivelDTO;
import com.google.gwt.user.client.ui.IsWidget;

public interface PlanAndResultHeaderView extends IsWidget {

	void setPresenter(Presenter p);
	
	void setAplicacionList(ArrayList<AplicacionDTO> aplicaciones);
	void setNivelList(ArrayList<NivelDTO> niveles);
	void setTipoList(ArrayList<ActividadTipoDTO> tipos);
	void exportActionVisivility(boolean visible);
	void actividadBoxVisivility(boolean visible);
	void nivelBoxVisivility(boolean visible);
	void tipoBoxVisivility(boolean visible);
	
	public interface Presenter{
		void onAplicacionChange(AplicacionDTO aplicacion);
		void onNivelChange(NivelDTO nivel);
		void onTipoChange(ActividadTipoDTO tipo);
		void onExportClick();
	}
	
	
}
