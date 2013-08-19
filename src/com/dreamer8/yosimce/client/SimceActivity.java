package com.dreamer8.yosimce.client;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.HandlerRegistration;

public abstract class SimceActivity extends AbstractActivity implements SimcePresenter{

	private final ClientFactory factory;
	private final SimcePlace place;
	
	private HashMap<String,ArrayList<String>> permisos;
	private HandlerRegistration permisosHandlerRegistration;
	
	public SimceActivity(ClientFactory factory, SimcePlace place,HashMap<String,ArrayList<String>> permisos){
		this.factory = factory;
		this.permisos = permisos;
		this.place = place;
	}
	
	public void onPermisosActualizados(){}
	
	public ClientFactory getFactory(){
		return factory;
	}
	
	public HashMap<String,ArrayList<String>> getPermisos(){
		return permisos;
	}
	
	@Override
	public void goTo(SimcePlace place) {
		place.setAplicacionId(this.place.getAplicacionId());
		place.setNivelId(this.place.getNivelId());
		place.setTipoId(this.place.getTipoId());
		factory.getPlaceController().goTo(place);
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		permisosHandlerRegistration = eventBus.addHandler(PermisosEvent.TYPE, new PermisosEvent.PermisosHandler() {
			
			@Override
			public void onPermisos(PermisosEvent event) {
				permisos = event.getPermisos();
				onPermisosActualizados();
			}
		});	
	}
	
	@Override
	public void onStop() {
		super.onStop();
		permisosHandlerRegistration.removeHandler();
	}
	
	

}
