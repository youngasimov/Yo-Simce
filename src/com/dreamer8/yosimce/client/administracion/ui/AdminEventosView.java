package com.dreamer8.yosimce.client.administracion.ui;


import com.dreamer8.yosimce.client.SimcePresenter;
import com.google.gwt.user.client.ui.IsWidget;

public interface AdminEventosView extends IsWidget {

	
	
	void setPresenter(AdminEventosPresenter presenter);
	
	public interface AdminEventosPresenter extends SimcePresenter{
		void onAplicacionSelected(int idAplicacion);
		void onNivelSelected(int idNivel);
		void onTipoSelected(int idTipo);
	}
}