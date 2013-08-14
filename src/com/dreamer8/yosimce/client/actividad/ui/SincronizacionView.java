package com.dreamer8.yosimce.client.actividad.ui;

import com.dreamer8.yosimce.client.SimcePresenter;
import com.google.gwt.user.client.ui.IsWidget;

public interface SincronizacionView extends IsWidget {

	
	void setPrenseter(SincronizacionPresenter presenter);
	
	public interface SincronizacionPresenter extends SimcePresenter{
		
	}
}
