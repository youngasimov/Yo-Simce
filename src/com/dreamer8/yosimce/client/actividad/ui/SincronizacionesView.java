package com.dreamer8.yosimce.client.actividad.ui;

import com.dreamer8.yosimce.client.SimcePresenter;
import com.google.gwt.user.client.ui.IsWidget;

public interface SincronizacionesView extends IsWidget {

	
	void setPresenter(SincronizacionesPresenter presenter);
	
	public interface SincronizacionesPresenter extends SimcePresenter{
		
	}
}
