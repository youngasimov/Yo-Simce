package com.dreamer8.yosimce.client.ui;

import com.dreamer8.yosimce.client.ClientFactory;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class LoadWidget implements Presenter {

	private ClientFactory factory;
	private LoadView view;
	
	public LoadWidget(ClientFactory factory){
		this.factory = factory;
		this.view = factory.getLoadView();
	}

	@Override
	public void goTo(AcceptsOneWidget panel) {
		panel.setWidget(view.asWidget());
	}
	
}
