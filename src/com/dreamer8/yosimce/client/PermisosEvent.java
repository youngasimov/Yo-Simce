package com.dreamer8.yosimce.client;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class PermisosEvent extends GwtEvent<PermisosEvent.PermisosHandler> {

	public static final Type<PermisosHandler> TYPE = new Type<PermisosHandler>();
	
	private HashMap<String,ArrayList<String>> permisos;

	public PermisosEvent(HashMap<String,ArrayList<String>> permisos) {
		super();
		this.permisos = permisos;
	}

	public HashMap<String,ArrayList<String>> getPermisos() {
		return permisos;
	}

	@Override
	public Type<PermisosHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(PermisosHandler handler) {
		handler.onPermisos(this);
	}
	
	public interface PermisosHandler extends EventHandler {
		void onPermisos(PermisosEvent event);
	}

}
