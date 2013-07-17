package com.dreamer8.yosimce.client;

import com.dreamer8.yosimce.client.activity.SimcePlaceHistoryMapper;
import com.dreamer8.yosimce.client.planandresult.ui.PlanAndResultContentView;
import com.dreamer8.yosimce.client.planandresult.ui.PlanAndResultContentViewD;
import com.dreamer8.yosimce.client.planandresult.ui.PlanAndResultHeaderView;
import com.dreamer8.yosimce.client.planandresult.ui.PlanAndResultHeaderViewD;
import com.dreamer8.yosimce.client.planandresult.ui.PlanAndResultSidebarView;
import com.dreamer8.yosimce.client.planandresult.ui.PlanAndResultSidebarViewD;
import com.dreamer8.yosimce.client.ui.AppView;
import com.dreamer8.yosimce.client.ui.AppViewD;
import com.dreamer8.yosimce.client.ui.LoadView;
import com.dreamer8.yosimce.client.ui.LoadViewD;
import com.dreamer8.yosimce.client.ui.ModuleSelectorView;
import com.dreamer8.yosimce.client.ui.ModuleSelectorViewD;
import com.dreamer8.yosimce.client.ui.NotLoggedView;
import com.dreamer8.yosimce.client.ui.NotLoggedViewD;
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
	private final AppView appView = new AppViewD();
	private final NotLoggedView notLoggedView = new NotLoggedViewD();
	private final ModuleSelectorView moduleSelectorView = new ModuleSelectorViewD();
	private final PlanAndResultHeaderView planAndResultHeaderView = new PlanAndResultHeaderViewD();
	private final PlanAndResultSidebarView planAndResultSidebarView = new PlanAndResultSidebarViewD();
	private final PlanAndResultContentView planAndResultContentView = new PlanAndResultContentViewD();
	
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

	@Override
	public AppView getAppView() {
		return appView;
	}

	@Override
	public NotLoggedView getNotLoggedView() {
		return notLoggedView;
	}

	@Override
	public ModuleSelectorView getModuleSelectorView() {
		return moduleSelectorView;
	}

	@Override
	public PlanAndResultHeaderView getPlanAndResultHeaderView() {
		return planAndResultHeaderView;
	}

	@Override
	public PlanAndResultSidebarView getPlanAndResultSidebarView() {
		return planAndResultSidebarView;
	}

	@Override
	public PlanAndResultContentView getPlanAndResultContentView() {
		return planAndResultContentView;
	}

}
