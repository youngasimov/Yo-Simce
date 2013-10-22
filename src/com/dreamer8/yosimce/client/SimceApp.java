package com.dreamer8.yosimce.client;

import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.SimplePanel;

public class SimceApp {

	private ClientFactory factory;
	private Place defaultPlace;
	private PlaceHistoryHandler historyHandler;
	
	public SimceApp(){
		defaultPlace = new SimcePlace();
		factory = GWT.create(ClientFactory.class);
		
		GATracker.setSessionCookieTimeout(0);
		GATracker.setSiteSpeedSampleRate(5);
		GATracker.trackPageview();
		
		SidebarPresenter sidebar = new SidebarPresenter(factory);
		sidebar.setDisplay(factory.getAppView().getSideBarPanel());
		
		
		ContentActivityMapper contentActivityMapper = new ContentActivityMapper(factory);
		ActivityManager contentActivityManager  = new ActivityManager(contentActivityMapper, factory.getEventBus());
		contentActivityManager.setDisplay(factory.getAppView().getContentPanel());
		
		historyHandler = new PlaceHistoryHandler(factory.getPlaceHistoryMapper());
		historyHandler.register(factory.getPlaceController(), factory.getEventBus(), defaultPlace);
	}
	
	public void start(SimplePanel panel, UserDTO user){
		AppPresenter app = new AppPresenter(factory);
		app.setDisplay(panel);
		HeaderPresenter header = new HeaderPresenter(factory, user);
		header.setDisplay(factory.getAppView().getHeaderView());
		historyHandler.handleCurrentHistory();
	}
	
	
}
