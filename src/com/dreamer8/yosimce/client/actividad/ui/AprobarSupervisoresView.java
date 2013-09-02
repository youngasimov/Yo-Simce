package com.dreamer8.yosimce.client.actividad.ui;

import com.dreamer8.yosimce.client.SimcePresenter;
import com.google.gwt.user.client.ui.IsWidget;

public interface AprobarSupervisoresView extends IsWidget {
	
	
	void setPresenter(AprobarSupervisoresPresenter presenter);
	
	public interface AprobarSupervisoresPresenter extends SimcePresenter{
		
	}
}
