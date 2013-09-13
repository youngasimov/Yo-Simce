package com.dreamer8.yosimce.client;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.actividad.ActividadesPlace;
import com.dreamer8.yosimce.client.actividad.AprobarSupervisoresPlace;
import com.dreamer8.yosimce.client.actividad.FormActividadPlace;
import com.dreamer8.yosimce.client.actividad.MaterialDefectuosoPlace;
import com.dreamer8.yosimce.client.actividad.SincronizacionPlace;
import com.dreamer8.yosimce.client.administracion.AdminEventosPlace;
import com.dreamer8.yosimce.client.administracion.AdminUsuariosPlace;
import com.dreamer8.yosimce.client.administracion.PermisosPlace;
import com.dreamer8.yosimce.client.general.DetalleCursoPlace;
import com.dreamer8.yosimce.client.material.CentroOperacionPlace;
import com.dreamer8.yosimce.client.planificacion.AgendamientosPlace;
import com.dreamer8.yosimce.client.planificacion.AgendarVisitaPlace;
import com.dreamer8.yosimce.client.planificacion.DetalleAgendaPlace;
import com.dreamer8.yosimce.client.ui.SidebarView;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class SidebarPresenter implements SidebarView.SidebarPresenter {

	private final ClientFactory factory;
	private final SidebarView view;
	
	private SimcePlace place;
	
	private HashMap<String,ArrayList<String>> permisos;
	
	private int tipoActividad;
	
	public SidebarPresenter(ClientFactory factory){
		this.factory = factory;
		this.view = factory.getSidebarView();
		tipoActividad = -1;
		bind();
	}
	
	
	@Override
	public void setDisplay(AcceptsOneWidget panel) {
		view.setGeneralVisivility(false);
		view.setAgendamientoVisivility(false);
		view.setActividadVisivility(false);
		view.setMaterialVisivility(false);
		view.setAdministracionVisivility(false);
		panel.setWidget(view.asWidget());
	}

	@Override
	public void goTo(SimcePlace place) {
		if(this.place!=null){
			place.setAplicacionId(this.place.getAplicacionId());
			place.setNivelId(this.place.getNivelId());
			place.setTipoId(this.place.getTipoId());
			factory.getPlaceController().goTo(place);
		}
	}
	
	private void bind(){
		view.setPresenter(this);
		
		factory.getEventBus().addHandler(MenuEvent.TYPE, new MenuEvent.MenuHandler() {
			
			@Override
			public void onMenu(MenuEvent event) {
				view.setScrollOnTop();
			}
		});
		
		factory.getEventBus().addHandler(PlaceChangeEvent.TYPE, new PlaceChangeEvent.Handler() {
			
			@Override
			public void onPlaceChange(PlaceChangeEvent event) {
				if(event.getNewPlace() instanceof SimcePlace){
					place = (SimcePlace)event.getNewPlace();
				}else{
					view.setGeneralVisivility(false);
					view.setAgendamientoVisivility(false);
					view.setActividadVisivility(false);
					view.setMaterialVisivility(false);
					view.setAdministracionVisivility(false);
				}
				
				if(event.getNewPlace() instanceof DetalleCursoPlace){
					view.setDetalleCursoViewItemSelected(true);
				}else if(event.getNewPlace() instanceof AgendamientosPlace){
					view.setAgendamientosViewItemSelected(true);
				}else if(event.getNewPlace() instanceof AgendarVisitaPlace){
					view.setAgendarVisitaActionItemSelected(true);
				}else if(event.getNewPlace() instanceof DetalleAgendaPlace){
					view.setDetalleAgendaViewItemSelected(true);
				}else if(event.getNewPlace() instanceof ActividadesPlace){
					view.setActividadesViewItemSelected(true);
				}else if(event.getNewPlace() instanceof FormActividadPlace){
					view.setFormularioActividadActionItemSelected(true);
				}else if(event.getNewPlace() instanceof SincronizacionPlace){
					view.setSincronizacionActionItemSelected(true);
				}else if(event.getNewPlace() instanceof MaterialDefectuosoPlace){
					view.setMaterialDefectuosoActionItemSelected(true);
				}else if(event.getNewPlace() instanceof AprobarSupervisoresPlace){
					view.setAprobarSupervisoresActionItemSelected(true);
				}else if(event.getNewPlace() instanceof CentroOperacionPlace){
					view.setCentroOperacionViewItemSelected(true);
				}else if(event.getNewPlace() instanceof AdminUsuariosPlace){
					view.setAdministrarUsuariosActionItemItemSelected(true);
				}else if(event.getNewPlace() instanceof AdminEventosPlace){
					view.setAdministrarEventosActionItemItemSelected(true);
				}else if(event.getNewPlace() instanceof PermisosPlace){
					view.setAdministrarPermisosActionItemItemSelected(true);
				}else{
					view.removeSeleccion();
				}
			}
		});
		factory.getEventBus().addHandler(PermisosEvent.TYPE, new PermisosEvent.PermisosHandler() {
			
			@Override
			public void onPermisos(PermisosEvent event) {
				permisos = event.getPermisos();
				updateView();
				
			}
		});
		
		factory.getEventBus().addHandler(TipoActividadChangeEvent.TYPE, new TipoActividadChangeEvent.TipoActividadChangeHandler() {
			
			@Override
			public void onTipoActividadChange(TipoActividadChangeEvent event) {
				tipoActividad = event.getIdTipo();
				updateView();
			}
		});
	}
	
	private void updateView(){
		
		boolean tipo = tipoActividad>=0;
		
		if(permisos == null || permisos.isEmpty()){
			return;
		}
		
		view.setGeneralVisivility(Utils.hasPermisos(permisos,"GeneralService","getDetalleCurso"));
		view.setDetalleCursoViewItemVisivility(Utils.hasPermisos(permisos,"GeneralService","getDetalleCurso") && tipo);
		
		view.setAgendamientoVisivility((Utils.hasPermisos(permisos,"PlanificacionService","getPreviewAgendamientos") && Utils.hasPermisos(permisos,"PlanificacionService","getTotalPreviewAgendamientos")) ||
				(Utils.hasPermisos(permisos,"PlanificacionService","getAgendaCurso") && Utils.hasPermisos(permisos,"PlanificacionService","AgendarVisita") && Utils.hasPermisos(permisos,"PlanificacionService","getEstadosAgenda")) ||
				Utils.hasPermisos(permisos,"PlanificacionService","getAgendaCurso"));
		view.setAgendamientosViewItemVisivility(Utils.hasPermisos(permisos,"PlanificacionService","getEstadosAgendaFiltro") && Utils.hasPermisos(permisos,"PlanificacionService","getPreviewAgendamientos") && Utils.hasPermisos(permisos,"PlanificacionService","getTotalPreviewAgendamientos") && tipo);
		view.setAgendarVisitaActionItemVisivility(Utils.hasPermisos(permisos,"PlanificacionService","getAgendaCurso") && Utils.hasPermisos(permisos,"PlanificacionService","AgendarVisita") && Utils.hasPermisos(permisos,"PlanificacionService","getEstadosAgenda") && tipo);
		view.setDetalleAgendaViewItemVisivility(Utils.hasPermisos(permisos,"PlanificacionService","getAgendaCurso") && tipo);
		
		view.setActividadVisivility((Utils.hasPermisos(permisos,"ActividadService","getTotalPreviewActividades") && Utils.hasPermisos(permisos,"ActividadService","getPreviewActividades")) ||
				Utils.hasPermisos(permisos,"ActividadService","getActividad") ||
				Utils.hasPermisos(permisos,"ActividadService","getSincronizacionesCurso") ||
				Utils.hasPermisos(permisos,"ActividadService","getEvaluacionSupervisores"));
		view.setActividadesViewItemVisivility(Utils.hasPermisos(permisos,"ActividadService","getTotalPreviewActividades") && Utils.hasPermisos(permisos,"ActividadService","getTotalPreviewActividades") && Utils.hasPermisos(permisos,"ActividadService","getPreviewActividades") && tipo);
		view.setFormularioActividadActionItemVisivility(Utils.hasPermisos(permisos,"ActividadService","getActividad") && tipo);
		view.setSincronizacionActionItemVisivility(Utils.hasPermisos(permisos,"ActividadService","getSincronizacionesCurso") && tipo);
		
		view.setMaterialDefectuosoActionItemVisivility(Utils.hasPermisos(permisos,"ActividadService","getMaterialDefectuoso") && tipo);
		
		view.setAprobarSupervisoresActionItemVisivility(Utils.hasPermisos(permisos,"ActividadService","getEvaluacionSupervisores") && tipo);
		
		view.setMaterialVisivility(true);
		view.setCentroOperacionViewItemVisivility(true);
		
		view.setAdministracionVisivility((Utils.hasPermisos(permisos,"AdministracionService","getTiposUsuario") && Utils.hasPermisos(permisos,"AdministracionService","getPermisos")) ||
				false ||
				false);
		view.setAdministrarUsuariosActionItemVisivility(false);
		view.setAdministrarEventosActionItemVisivility(false);
		view.setAdministrarPermisosActionItemVisivility(Utils.hasPermisos(permisos,"AdministracionService","getTiposUsuario") && Utils.hasPermisos(permisos,"AdministracionService","getPermisos"));
	}
}
