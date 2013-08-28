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
import com.dreamer8.yosimce.client.material.IngresoMaterialPlace;
import com.dreamer8.yosimce.client.material.MovimientosMaterialPlace;
import com.dreamer8.yosimce.client.material.SalidaMaterialPlace;
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
				}else if(event.getNewPlace() instanceof IngresoMaterialPlace){
					view.setIngresoMaterialActionItemSelected(true);
				}else if(event.getNewPlace() instanceof SalidaMaterialPlace){
					view.setSalidaMaterialActionItemSelected(true);
				}else if(event.getNewPlace() instanceof MovimientosMaterialPlace){
					view.setMovimientosMaterialViewItemSelected(true);
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
		
		view.setGeneralVisivility(permisos.get("GeneralService").contains("getDetalleCurso"));
		view.setDetalleCursoViewItemVisivility(permisos.get("GeneralService").contains("getDetalleCurso") && tipo);
		
		view.setAgendamientoVisivility((permisos.get("PlanificacionService").contains("getPreviewAgendamientos") && permisos.get("PlanificacionService").contains("getTotalPreviewAgendamientos")) ||
				(permisos.get("PlanificacionService").contains("getAgendaCurso") && permisos.get("PlanificacionService").contains("AgendarVisita") && permisos.get("PlanificacionService").contains("getEstadosAgenda")) ||
				permisos.get("PlanificacionService").contains("getAgendaCurso"));
		view.setAgendamientosViewItemVisivility(permisos.get("PlanificacionService").contains("getPreviewAgendamientos") && permisos.get("PlanificacionService").contains("getTotalPreviewAgendamientos") && tipo);
		view.setAgendarVisitaActionItemVisivility(permisos.get("PlanificacionService").contains("getAgendaCurso") && permisos.get("PlanificacionService").contains("AgendarVisita") && permisos.get("PlanificacionService").contains("getEstadosAgenda") && tipo);
		view.setDetalleAgendaViewItemVisivility(permisos.get("PlanificacionService").contains("getAgendaCurso") && tipo);
		
		view.setActividadVisivility((permisos.get("ActividadService").contains("getTotalPreviewActividades") && permisos.get("ActividadService").contains("getPreviewActividades")) ||
				permisos.get("ActividadService").contains("getActividad") ||
				permisos.get("ActividadService").contains("getSincronizacionesCurso") ||
				permisos.get("ActividadService").contains("getEvaluacionSupervisores"));
		view.setActividadesViewItemVisivility(permisos.get("ActividadService").contains("getTotalPreviewActividades") && permisos.get("ActividadService").contains("getPreviewActividades") && tipo);
		view.setFormularioActividadActionItemVisivility(permisos.get("ActividadService").contains("getActividad") && tipo);
		view.setSincronizacionActionItemVisivility(permisos.get("ActividadService").contains("getSincronizacionesCurso") && tipo);
		
		view.setMaterialDefectuosoActionItemVisivility(true);
		
		view.setAprobarSupervisoresActionItemVisivility(permisos.get("ActividadService").contains("getEvaluacionSupervisores") && tipo);
		
		view.setMaterialVisivility(false);
		view.setIngresoMaterialActionItemVisivility(false);
		view.setSalidaMaterialActionItemVisivility(false);
		view.setMovimientosMaterialViewItemVisivility(false);
		
		view.setAdministracionVisivility((permisos.get("AdministracionService").contains("getTiposUsuario") && permisos.get("AdministracionService").contains("getPermisos")) ||
				false ||
				false);
		view.setAdministrarUsuariosActionItemVisivility(false);
		view.setAdministrarEventosActionItemVisivility(false);
		view.setAdministrarPermisosActionItemVisivility(permisos.get("AdministracionService").contains("getTiposUsuario") && permisos.get("AdministracionService").contains("getPermisos"));
	}
}
