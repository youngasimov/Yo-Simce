package com.dreamer8.yosimce.client;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.planificacion.AgendamientosPlace;
import com.dreamer8.yosimce.client.planificacion.AgendarVisitaPlace;
import com.dreamer8.yosimce.client.planificacion.DetalleAgendaEstablecimientoPlace;
import com.dreamer8.yosimce.client.ui.SidebarView;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class SidebarPresenter implements SidebarView.SidebarPresenter {

	private final ClientFactory factory;
	private final SidebarView view;
	
	private SimcePlace place;
	
	private HashMap<String,ArrayList<String>> permisos;
	
	public SidebarPresenter(ClientFactory factory){
		this.factory = factory;
		this.view = factory.getSidebarView();
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
				}else if(event.getNewPlace() instanceof NotLoggedPlace){
					view.setGeneralVisivility(false);
					view.setAgendamientoVisivility(false);
					view.setActividadVisivility(false);
					view.setMaterialVisivility(false);
					view.setAdministracionVisivility(false);
				}
				
				if(event.getNewPlace() instanceof AgendamientosPlace){
					view.setAgendamientosViewItemSelected(true);
				}else if(event.getNewPlace() instanceof AgendarVisitaPlace){
					view.setAgendarVisitaActionItemSelected(true);
				}else if(event.getNewPlace() instanceof DetalleAgendaEstablecimientoPlace){
					view.setDetalleAgendaEstablecimientoViewItemSelected(true);
				}
				
				else{
					view.removeSeleccion();
				}
				
			}
		});
		factory.getEventBus().addHandler(PermisosEvent.TYPE, new PermisosEvent.PermisosHandler() {
			
			@Override
			public void onPermisos(PermisosEvent event) {
				permisos = event.getPermisos();
				
				
				if(factory.onTesting()){
					view.setGeneralVisivility(true);
					view.setAgendamientoVisivility(true);
					view.setActividadVisivility(true);
					view.setMaterialVisivility(true);
					view.setAdministracionVisivility(true);
				}
			}
		});
	}
}
