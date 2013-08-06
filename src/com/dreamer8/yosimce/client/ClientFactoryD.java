package com.dreamer8.yosimce.client;

import com.dreamer8.yosimce.client.administracion.AdministracionService;
import com.dreamer8.yosimce.client.administracion.AdministracionServiceAsync;
import com.dreamer8.yosimce.client.administracion.ui.AdminEventosView;
import com.dreamer8.yosimce.client.administracion.ui.AdminEventosViewD;
import com.dreamer8.yosimce.client.administracion.ui.AdminUsuariosView;
import com.dreamer8.yosimce.client.administracion.ui.AdminUsuariosViewD;
import com.dreamer8.yosimce.client.administracion.ui.AdminView;
import com.dreamer8.yosimce.client.administracion.ui.AdminViewD;
import com.dreamer8.yosimce.client.general.ui.DetalleCursoView;
import com.dreamer8.yosimce.client.general.ui.DetalleCursoViewD;
import com.dreamer8.yosimce.client.general.ui.GeneralView;
import com.dreamer8.yosimce.client.general.ui.GeneralViewD;
import com.dreamer8.yosimce.client.planificacion.PlanificacionService;
import com.dreamer8.yosimce.client.planificacion.PlanificacionServiceAsync;
import com.dreamer8.yosimce.client.planificacion.ui.AgendamientosView;
import com.dreamer8.yosimce.client.planificacion.ui.AgendamientosViewD;
import com.dreamer8.yosimce.client.planificacion.ui.AgendarVisitaView;
import com.dreamer8.yosimce.client.planificacion.ui.AgendarVisitaViewD;
import com.dreamer8.yosimce.client.planificacion.ui.DetalleAgendaView;
import com.dreamer8.yosimce.client.planificacion.ui.DetalleAgendaViewD;
import com.dreamer8.yosimce.client.planificacion.ui.PlanificacionView;
import com.dreamer8.yosimce.client.planificacion.ui.PlanificacionViewD;
import com.dreamer8.yosimce.client.ui.AppView;
import com.dreamer8.yosimce.client.ui.AppViewD;
import com.dreamer8.yosimce.client.ui.CursoSelectorView;
import com.dreamer8.yosimce.client.ui.CursoSelectorViewD;
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
	private final AdministracionServiceAsync administracionService = (AdministracionServiceAsync)GWT.create(AdministracionService.class);
	
	
	private final LoadView loadView = new LoadViewD();
	private final AppView appView = new AppViewD();
	private final HeaderView headerView = new HeaderViewD();
	private final SidebarView sidebarView = new SidebarViewD();
	
	private final GeneralView generalView = new GeneralViewD();
	private final DetalleCursoView detalleCursoView = new DetalleCursoViewD();
	private final PlanificacionView planificacionView = new PlanificacionViewD();
	private final AgendamientosView agendamientosView = new AgendamientosViewD();
	private final AgendarVisitaView agendarVisitaView = new AgendarVisitaViewD();
	private final DetalleAgendaView detalleAgendaView = new DetalleAgendaViewD();
	private final CursoSelectorView cursoSelectorView = new CursoSelectorViewD();
	
	private final AdminView adminView = new AdminViewD();
	private final AdminUsuariosView adminUsuariosView = new AdminUsuariosViewD();
	private final AdminEventosView adminEventosView = new AdminEventosViewD();
	
	
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
	public AdministracionServiceAsync getAdministracionService() {
		return administracionService;
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
	public GeneralView getGeneralView() {
		return generalView;
	}
	
	@Override
	public DetalleCursoView getDetalleCursoView(){
		return detalleCursoView;
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
	public DetalleAgendaView getDetalleAgendaView() {
		return detalleAgendaView;
	}

	@Override
	public CursoSelectorView getCursoSelectorView() {
		return cursoSelectorView;
	}

	@Override
	public AdminView getAdminView() {
		return adminView;
	}

	@Override
	public AdminUsuariosView getAdminUsuariosView() {
		return adminUsuariosView;
	}

	@Override
	public AdminEventosView getAdminEventosView() {
		return adminEventosView;
	}
}
