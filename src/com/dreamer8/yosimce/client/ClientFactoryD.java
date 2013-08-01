package com.dreamer8.yosimce.client;

import com.dreamer8.yosimce.client.planificacion.PlanificacionService;
import com.dreamer8.yosimce.client.planificacion.PlanificacionServiceAsync;
import com.dreamer8.yosimce.client.planificacion.ui.AgendamientosView;
import com.dreamer8.yosimce.client.planificacion.ui.AgendamientosViewD;
import com.dreamer8.yosimce.client.planificacion.ui.AgendarVisitaView;
import com.dreamer8.yosimce.client.planificacion.ui.AgendarVisitaViewD;
import com.dreamer8.yosimce.client.planificacion.ui.DetalleAgendaEstablecimientoView;
import com.dreamer8.yosimce.client.planificacion.ui.DetalleAgendaEstablecimientoViewD;
import com.dreamer8.yosimce.client.planificacion.ui.PlanificacionView;
import com.dreamer8.yosimce.client.planificacion.ui.PlanificacionViewD;
import com.dreamer8.yosimce.client.ui.AppView;
import com.dreamer8.yosimce.client.ui.AppViewD;
import com.dreamer8.yosimce.client.ui.EstablecimientoSelectorView;
import com.dreamer8.yosimce.client.ui.EstablecimientoSelectorViewD;
import com.dreamer8.yosimce.client.ui.LoadView;
import com.dreamer8.yosimce.client.ui.LoadViewD;
import com.dreamer8.yosimce.client.ui.HeaderViewD;
import com.dreamer8.yosimce.client.ui.HeaderView;
import com.dreamer8.yosimce.client.ui.SidebarView;
import com.dreamer8.yosimce.client.ui.SidebarViewD;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class ClientFactoryD implements ClientFactory {
	
	private static final boolean TESTING = true;

	private final EventBus eventBus = new SimpleEventBus();
	private final PlaceController placeController = new PlaceController(eventBus);
	private final PlaceHistoryMapper placeHistoryMapper = GWT.create(SimcePlaceHistoryMapper.class);
	private final LoginServiceAsync loginService = (LoginServiceAsync)GWT.create(LoginService.class);
	private final PlanificacionServiceAsync planificacionService = (PlanificacionServiceAsync)GWT.create(PlanificacionService.class);
	
	
	private final LoadView loadView = new LoadViewD();
	private final AppView appView = new AppViewD();
	private final HeaderView headerView = new HeaderViewD();
	private final SidebarView sidebarView = new SidebarViewD();
	
	private final PlanificacionView planificacionView = new PlanificacionViewD();
	private final AgendamientosView agendamientosView = new AgendamientosViewD();
	private final AgendarVisitaView agendarVisitaView = new AgendarVisitaViewD();
	private final DetalleAgendaEstablecimientoView detalleAgendaEstablecimientoView = new DetalleAgendaEstablecimientoViewD();
	private final EstablecimientoSelectorView establecimientoSelectorView = new EstablecimientoSelectorViewD();
	
	
	@Override
	public boolean onTesting() {
		return TESTING;
	}
	
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

	@Override
	public SidebarView getSidebarView() {
		return sidebarView;
	}

	@Override
	public PlanificacionView getPlanificacionView() {
		return planificacionView;
	}

	@Override
	public AgendamientosView getAgendamientosView() {
		return agendamientosView;
	}

	@Override
	public AgendarVisitaView getAgendarVisitaView() {
		return agendarVisitaView;
	}

	@Override
	public DetalleAgendaEstablecimientoView getDetalleAgendaEstablecimientoView() {
		return detalleAgendaEstablecimientoView;
	}

	@Override
	public EstablecimientoSelectorView getEstablecimientoSelectorView() {
		return establecimientoSelectorView;
	}
}
