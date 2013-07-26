package com.dreamer8.yosimce.client;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.web.bindery.event.shared.HandlerRegistration;

public abstract class SimceActivity extends AbstractActivity {

	private final ClientFactory factory;
	
	private HashMap<String,ArrayList<String>> permisos;
	private HandlerRegistration permisosHandlerRegistration;
	
	public SimceActivity(ClientFactory factory,HashMap<String,ArrayList<String>> permisos){
		this.factory = factory;
		this.permisos = permisos;
		bind();
	}
	
	public void updatedPermisos(){}
	
	public ClientFactory getFactory(){
		return factory;
	}
	
	public HashMap<String,ArrayList<String>> getPermisos(){
		return permisos;
	}
	
	@Override
	public String mayStop() {
		permisosHandlerRegistration.removeHandler();
		return super.mayStop();
	}
	
	
	
	private void bind(){
		permisosHandlerRegistration = this.factory.getEventBus().addHandler(PermisosEvent.TYPE, new PermisosEvent.PermisosHandler() {
			
			@Override
			public void onPermisos(PermisosEvent event) {
				permisos = event.getPermisos();
				updatedPermisos();
			}
		});
	}

}
