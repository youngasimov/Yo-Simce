package com.dreamer8.yosimce.client.ui;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;

public interface AppView extends IsWidget {

	void setPresenter(AppPresenter presenter);
	SimpleLayoutPanel getHeaderView();
	SimpleLayoutPanel getSideBarPanel();
	SimpleLayoutPanel getContentPanel();
	
	
	public interface AppPresenter{
		
	}
}
