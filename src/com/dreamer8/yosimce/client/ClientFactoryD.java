package com.dreamer8.yosimce.client;

import com.dreamer8.yosimce.client.activity.SimcePlaceHistoryMapper;
import com.dreamer8.yosimce.client.ui.LoadView;
import com.dreamer8.yosimce.client.ui.LoadViewD;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class ClientFactoryD implements ClientFactory {

	private final EventBus eventBus = new SimpleEventBus();
	private final PlaceController placeController = new PlaceController(eventBus);
	private final PlaceHistoryMapper placeHistoryMapper = GWT.create(SimcePlaceHistoryMapper.class);
	
	private final LoadView loadView = new LoadViewD();
	
	@Override
	public EventBus getEventBus() {
		return eventBus;
	}

	@Override
	public PlaceController getPlaceController() {
		return placeController;
	}

	@Override
	public PlaceHistoryMapper getPlaceHistoryMapper() {
		return placeHistoryMapper;
	}

	@Override
	public LoadView getLoadView() {
		return loadView;
	}

}
