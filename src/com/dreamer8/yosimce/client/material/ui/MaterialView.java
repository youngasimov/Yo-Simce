package com.dreamer8.yosimce.client.material.ui;

import com.dreamer8.yosimce.client.SimcePresenter;
import com.google.gwt.user.client.ui.IsWidget;

public interface MaterialView extends IsWidget {

	void setCentroOperacionVisivility(boolean visible);
	
	void setPresenter(MaterialPresenter presenter);
	
	public interface MaterialPresenter extends SimcePresenter{
		
	}
}
