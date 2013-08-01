package com.dreamer8.yosimce.client;

import com.dreamer8.yosimce.client.general.ui.DetalleCursoView;
import com.dreamer8.yosimce.client.general.ui.GeneralView;
import com.dreamer8.yosimce.client.planificacion.PlanificacionServiceAsync;
import com.dreamer8.yosimce.client.planificacion.ui.AgendamientosView;
import com.dreamer8.yosimce.client.planificacion.ui.AgendarVisitaView;
import com.dreamer8.yosimce.client.planificacion.ui.DetalleAgendaView;
import com.dreamer8.yosimce.client.planificacion.ui.PlanificacionView;
import com.dreamer8.yosimce.client.ui.AppView;
import com.dreamer8.yosimce.client.ui.CursoSelectorView;
import com.dreamer8.yosimce.client.ui.LoadView;
import com.dreamer8.yosimce.client.ui.HeaderView;
import com.dreamer8.yosimce.client.ui.SidebarView;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.web.bindery.event.shared.EventBus;

public interface ClientFactory {

	boolean onTesting();
	
	EventBus getEventBus();
	PlaceController getPlaceController();
	PlaceHistoryMapper getPlaceHistoryMapper();
	
	AppView getAppView();
	LoadView getLoadView();
	LoginServiceAsync getLoginService();
	PlanificacionServiceAsync getPlanificacionService();
	HeaderView getHeaderView();
	SidebarView getSidebarView();
	
	GeneralView getGeneralView();
	DetalleCursoView getDetalleCursoView();
	
	PlanificacionView getPlanificacionView();
	AgendamientosView getAgendamientosView();
	AgendarVisitaView getAgendarVisitaView();
	DetalleAgendaView getDetalleAgendaView();
	CursoSelectorView getCursoSelectorView();
	
	
}
