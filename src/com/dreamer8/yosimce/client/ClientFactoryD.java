package com.dreamer8.yosimce.client;

import com.dreamer8.yosimce.client.planificacion.PlanificacionService;
import com.dreamer8.yosimce.client.planificacion.PlanificacionServiceAsync;
import com.dreamer8.yosimce.client.ui.AppView;
import com.dreamer8.yosimce.client.ui.AppViewD;
import com.dreamer8.yosimce.client.ui.LoadView;
import com.dreamer8.yosimce.client.ui.LoadViewD;
import com.dreamer8.yosimce.client.ui.HeaderViewD;
import com.dreamer8.yosimce.client.ui.HeaderView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class ClientFactoryD implements ClientFactory {

	private final EventBus eventBus = new SimpleEventBus();
	private final PlaceController placeController = new PlaceController(eventBus);
	private final PlaceHistoryMapper placeHistoryMapper = GWT.create(SimcePlaceHistoryMapper.class);
	private final LoginServiceAsync loginService = (LoginServiceAsync)GWT.create(LoginService.class);
	private final PlanificacionServiceAsync planificacionService = (PlanificacionServiceAsync)GWT.create(PlanificacionService.class);
	
	
	private final LoadView loadView = new LoadViewD();
	private final AppView appView = new AppViewD();
	private final HeaderView headerView = new HeaderViewD();
	
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
	public LoginServiceAsync getLoginService() {
		return loginService;
	}

	@Override
	public PlanificacionServiceAsync getPlanificacionService() {
		return planificacionService;
	}

	@Override
	public LoadView getLoadView() {
		return loadView;
	}

	@Override
	public AppView getAppView() {
		return appView;
	}

	@Override
	public HeaderView getHeaderView() {
		return headerView;
	}
}
