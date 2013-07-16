package com.dreamer8.yosimce.client;

import com.dreamer8.yosimce.client.ui.AppView;
import com.dreamer8.yosimce.client.ui.LoadView;
import com.dreamer8.yosimce.client.ui.NotLoggedView;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.web.bindery.event.shared.EventBus;

public interface ClientFactory {

	EventBus getEventBus();
	PlaceController getPlaceController();
	PlaceHistoryMapper getPlaceHistoryMapper();
	
	AppView getAppView();
	LoadView getLoadView();
	NotLoggedView getNotLoggedView();
	
}
