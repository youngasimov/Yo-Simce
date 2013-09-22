package com.dreamer8.yosimce.client;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.HandlerRegistration;

public abstract class SimceActivity extends AbstractActivity implements SimcePresenter{

	private final ClientFactory factory;
	private final SimcePlace place;
	
	private HashMap<String,ArrayList<String>> permisos;
	private HandlerRegistration permisosHandlerRegistration;
	private HandlerRegistration tipoActividadChangeHandlerRegistration;
	
	private boolean init;
	
	private EventBus eventBus;
	
	private int tipoActividadId;
	
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
	public void toggleMenu() {
		eventBus.fireEvent(new MenuEvent(true));
	}
	
	@Override
	public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
		this.eventBus = eventBus;
		tipoActividadId = place.getTipoId();
		String k = Cookies.getCookie("t");
		if(k == null){
			tipoActividadId = -1;
		}
		
		permisosHandlerRegistration = eventBus.addHandler(PermisosEvent.TYPE, new PermisosEvent.PermisosHandler() {
			
			@Override
			public void onPermisos(PermisosEvent event) {
				permisos = event.getPermisos();
				if(permisos!=null && !init && tipoActividadId>=0){
					init = true;
					init(panel,eventBus);
				}
			}
		});
		
		tipoActividadChangeHandlerRegistration = eventBus.addHandler(TipoActividadChangeEvent.TYPE, new TipoActividadChangeEvent.TipoActividadChangeHandler() {
			
			@Override
			public void onTipoActividadChange(TipoActividadChangeEvent event) {
				tipoActividadId = event.getIdTipo();
				if(permisos!=null && !init && tipoActividadId>=0){
					init = true;
					init(panel,eventBus);
				}
			}
		});
		
		
		
		if(!init && tipoActividadId>=0 && permisos != null){
			init = true;
			init(panel,eventBus);
		}
	}
	
	@Override
	public void onStop() {
		super.onStop();
		permisosHandlerRegistration.removeHandler();
		tipoActividadChangeHandlerRegistration.removeHandler();
		init = false;
	}
	
	

}
