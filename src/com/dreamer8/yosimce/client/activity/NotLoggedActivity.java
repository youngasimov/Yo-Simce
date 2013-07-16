package com.dreamer8.yosimce.client.activity;

import com.dreamer8.yosimce.client.ClientFactory;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class NotLoggedActivity extends AbstractActivity {

	
	private ClientFactory factory;
	
	public NotLoggedActivity(ClientFactory factory){
		this.factory = factory;
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		// TODO Auto-generated method stub

	}

}
