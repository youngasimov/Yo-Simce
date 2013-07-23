package com.dreamer8.yosimce.client.ui;

import java.util.ArrayList;

import com.dreamer8.yosimce.shared.dto.ActividadTipoDTO;
import com.dreamer8.yosimce.shared.dto.AplicacionDTO;
import com.dreamer8.yosimce.shared.dto.NivelDTO;
import com.google.gwt.user.client.ui.IsWidget;

public interface HeaderView extends IsWidget {

	void setPresenter(HeaderPresenter p);
	
	
	void setAplicacionList(ArrayList<AplicacionDTO> aplicaciones);
	void setNivelList(ArrayList<NivelDTO> niveles);
	void setTipoList(ArrayList<ActividadTipoDTO> tipos);
	
	void selectAplicacion(AplicacionDTO aplicacion);
	void selectNivel(NivelDTO nivel);
	void selectTipo(ActividadTipoDTO tipo);
	
	void setAplicacionBoxVisivility(boolean visible);
	void setNivelBoxVisivility(boolean visible);
	void setTipoBoxVisivility(boolean visible);
	
	void setUserName(String user);
	
	void error(boolean visible);
	
	public interface HeaderPresenter extends Presenter{
		void onAplicacionChange(AplicacionDTO aplicacion);
		void onNivelChange(NivelDTO nivel);
		void onTipoChange(ActividadTipoDTO tipo);
	}
	
	
}
