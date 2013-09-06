package com.dreamer8.yosimce.client.material.ui;

import com.dreamer8.yosimce.client.SimcePresenter;
import com.google.gwt.user.client.ui.IsWidget;

public interface CentroOperacionView extends IsWidget {

	
	void setPresenter(CentroOperacionPresenter presenter);
	public interface CentroOperacionPresenter extends SimcePresenter{
		
	}
}
