package com.dreamer8.yosimce.client.actividad.ui;

import com.dreamer8.yosimce.client.SimcePresenter;
import com.google.gwt.user.client.ui.IsWidget;

public interface AvanceRevisionView extends IsWidget {

	
	void setPresenter(AvanceRevisionPresenter presenter);
	
	public interface AvanceRevisionPresenter extends SimcePresenter{
		
	}
}
