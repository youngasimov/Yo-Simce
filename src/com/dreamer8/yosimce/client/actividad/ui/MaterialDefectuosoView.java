package com.dreamer8.yosimce.client.actividad.ui;

import com.dreamer8.yosimce.client.SimcePresenter;
import com.google.gwt.user.client.ui.IsWidget;

public interface MaterialDefectuosoView extends IsWidget {

	void setPresenter(MaterialDefectuosoPresenter presenter);
	
	public interface MaterialDefectuosoPresenter extends SimcePresenter{
		
	}
	
}
