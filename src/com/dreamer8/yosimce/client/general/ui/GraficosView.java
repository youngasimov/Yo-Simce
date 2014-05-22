package com.dreamer8.yosimce.client.general.ui;

import com.dreamer8.yosimce.client.SimcePresenter;
import com.google.gwt.user.client.ui.IsWidget;

public interface GraficosView extends IsWidget {

	void setEstadoMaterialVisivility(boolean visible);
	void setEstadoActividadVisivility(boolean visible);
	
	public interface GraficosPresenter extends SimcePresenter{
		void actualizarEstadoMaterial();
		void actualizarEstadoActividad();
	}
}
