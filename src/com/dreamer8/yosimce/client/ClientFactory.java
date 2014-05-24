package com.dreamer8.yosimce.client;

import com.dreamer8.yosimce.client.actividad.ActividadServiceAsync;
import com.dreamer8.yosimce.client.actividad.ui.ActividadesView;
import com.dreamer8.yosimce.client.actividad.ui.AprobarSupervisoresView;
import com.dreamer8.yosimce.client.actividad.ui.AvanceRevisionView;
import com.dreamer8.yosimce.client.actividad.ui.FormActividadView;
import com.dreamer8.yosimce.client.actividad.ui.MaterialDefectuosoView;
import com.dreamer8.yosimce.client.actividad.ui.SincronizacionView;
import com.dreamer8.yosimce.client.administracion.AdministracionServiceAsync;
import com.dreamer8.yosimce.client.administracion.ui.PermisosView;
import com.dreamer8.yosimce.client.administracion.ui.ReportesView;
import com.dreamer8.yosimce.client.general.GeneralServiceAsync;
import com.dreamer8.yosimce.client.general.ui.CentroControlView;
import com.dreamer8.yosimce.client.general.ui.DetalleCursoView;
import com.dreamer8.yosimce.client.general.ui.GraficosView;
import com.dreamer8.yosimce.client.material.MaterialServiceAsync;
import com.dreamer8.yosimce.client.material.ui.BuscadorCodigoView;
import com.dreamer8.yosimce.client.material.ui.CentroOperacionSelectorView;
import com.dreamer8.yosimce.client.material.ui.CentroOperacionView;
import com.dreamer8.yosimce.client.planificacion.PlanificacionServiceAsync;
import com.dreamer8.yosimce.client.planificacion.ui.AgendamientosView;
import com.dreamer8.yosimce.client.planificacion.ui.AgendarVisitaView;
import com.dreamer8.yosimce.client.planificacion.ui.DetalleAgendaView;
import com.dreamer8.yosimce.client.ui.AppView;
import com.dreamer8.yosimce.client.ui.CursoSelectorView;
import com.dreamer8.yosimce.client.ui.HeaderView;
import com.dreamer8.yosimce.client.ui.MenuView;
import com.dreamer8.yosimce.client.ui.SidebarView;
import com.dreamer8.yosimce.client.ui.TipoUsuarioSelectorView;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.web.bindery.event.shared.EventBus;

public interface ClientFactory {

	boolean onTesting();
	EventBus getEventBus();
	PlaceController getPlaceController();
	PlaceHistoryMapper getPlaceHistoryMapper();
	
	LoginServiceAsync getLoginService();
	GeneralServiceAsync getGeneralService();
	PlanificacionServiceAsync getPlanificacionService();
	ActividadServiceAsync getActividadService();
	MaterialServiceAsync getMaterialService();
	AdministracionServiceAsync getAdministracionService();
	
	
	AppView getAppView();
	HeaderView getHeaderView();
	SidebarView getSidebarView();
	TipoUsuarioSelectorView getTipoUsuarioSelectorView();
	
	MenuView getMenuView();
	
	DetalleCursoView getDetalleCursoView();
	CentroControlView getCentroControlView();
	GraficosView getGraficosView();
	
	AgendamientosView getAgendamientosView();
	AgendarVisitaView getAgendarVisitaView();
	DetalleAgendaView getDetalleAgendaView();
	CursoSelectorView getCursoSelectorView();
	
	ActividadesView getActividadesView();
	FormActividadView getFormActividadView();
	SincronizacionView getSincronizacionView();
	MaterialDefectuosoView getMaterialDefectuosoView();
	AprobarSupervisoresView getAprobarSupervisoresView();
	AvanceRevisionView getAvanceRevisionView();
	
	CentroOperacionView getCentroOperacionView();
	BuscadorCodigoView getBuscadorCodigoView();
	CentroOperacionSelectorView getCentroOperacionSelectorView();
	
	PermisosView getPermisosView();
	ReportesView getReportesView();
}
