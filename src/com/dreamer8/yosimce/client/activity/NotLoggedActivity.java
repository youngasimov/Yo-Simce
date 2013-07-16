package com.dreamer8.yosimce.client.activity;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.ui.NotLoggedView;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class NotLoggedActivity extends AbstractActivity {

	
	private ClientFactory factory;
	private NotLoggedView view;
	
	public NotLoggedActivity(ClientFactory factory){
		this.factory = factory;
		view = factory.getNotLoggedView();
		
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		view.setLink("http://www.yosimce.usm.cl", "Ingresar a YoSimce");
		view.setMessage("No se ha registrado en YoSimce aún");
		panel.setWidget(view.asWidget());
	}

}
