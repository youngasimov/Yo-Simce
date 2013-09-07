package com.dreamer8.yosimce.client.ui;

import com.dreamer8.yosimce.client.SimcePresenter;
import com.google.gwt.user.client.ui.IsWidget;

public interface MenuView extends IsWidget {
	
	
	void setPresenter(MenuPresenter presenter);
	
	public interface MenuPresenter extends SimcePresenter{
		
	}
}