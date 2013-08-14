package com.dreamer8.yosimce.client.actividad.ui;

import com.dreamer8.yosimce.client.SimcePresenter;
import com.google.gwt.user.client.ui.IsWidget;

public interface FormActividadView extends IsWidget {
	
	
	void setPresenter(FormActividadPresenter presenter);
	
	public interface FormActividadPresenter extends SimcePresenter{
		
	}
}
