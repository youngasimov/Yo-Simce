package com.dreamer8.yosimce.client.administracion.ui;

import java.util.ArrayList;

import com.dreamer8.yosimce.client.SimcePresenter;
import com.dreamer8.yosimce.shared.dto.ActividadTipoDTO;
import com.dreamer8.yosimce.shared.dto.AplicacionDTO;
import com.dreamer8.yosimce.shared.dto.NivelDTO;
import com.google.gwt.user.client.ui.IsWidget;

public interface AdminEventosView extends IsWidget {

	void setAplicaciones(ArrayList<AplicacionDTO> aplicaciones);
	void setNiveles(ArrayList<NivelDTO> niveles);
	void setTipos(ArrayList<ActividadTipoDTO> tipos);
	
	void setAplicacionesBoxVisivility(boolean visible);
	void setNivelesBoxVisivility(boolean visible);
	void setTiposBoxVisivility(boolean visible);
	
	void setNuevaAplicacionVisibility(boolean visible);
	void setNuevoNivelVisibility(boolean visible);
	void setNuevoTipoVisibility(boolean visible);
	
	void setEditarAplicacionVisivility(boolean visible);
	void setEditarNivelVisivility(boolean visible);
	void setEditarTipoVisivility(boolean visible);
	
	void setPresenter(AdminEventosPresenter presenter);
	
	public interface AdminEventosPresenter extends SimcePresenter{
		void onAplicacionSelected(int idAplicacion);
		void onNivelSelected(int idNivel);
		void onTipoSelected(int idTipo);
	}
}