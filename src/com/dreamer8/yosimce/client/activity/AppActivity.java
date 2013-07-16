package com.dreamer8.yosimce.client.activity;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.ui.AppView;
import com.dreamer8.yosimce.client.ui.AppView.AppPresenter;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class AppActivity extends AbstractActivity implements AppPresenter {

	private AppView view;
	private ClientFactory factory;
	private UserDTO user;
	
	public AppActivity(ClientFactory factory, UserDTO user){
		this.factory = factory;
		this.user = user;
		this.view = factory.getAppView();
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(this.view);
	}

}
