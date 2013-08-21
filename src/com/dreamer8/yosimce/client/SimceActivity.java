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
	
	private boolean init;
	
	public SimceActivity(ClientFactory factory, SimcePlace place,HashMap<String,ArrayList<String>> permisos){
		this.factory = factory;
		this.permisos = permisos;
		this.place = place;
		init = false;
	}
	
	public abstract void init(AcceptsOneWidget panel,EventBus eventBus);
	
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
	public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
		permisosHandlerRegistration = eventBus.addHandler(PermisosEvent.TYPE, new PermisosEvent.PermisosHandler() {
			
			@Override
			public void onPermisos(PermisosEvent event) {
				permisos = event.getPermisos();
				if(!init){
					init = true;
					init(panel,eventBus);
				}
			}
		});	
		
		if(permisos != null && !init){
			init = true;
			init(panel,eventBus);
		}
	}
	
	@Override
	public void onStop() {
		super.onStop();
		permisosHandlerRegistration.removeHandler();
		init = false;
	}
	
	

}
