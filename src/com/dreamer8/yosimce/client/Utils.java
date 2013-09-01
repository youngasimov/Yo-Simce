package com.dreamer8.yosimce.client;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.event.shared.EventBus;

public class Utils {

	public static boolean hasPermisos(HashMap<String,ArrayList<String>> permisos, String modulo, String permiso){
		return hasPermisos(null,permisos,modulo,permiso);
	}
	
	public static boolean hasPermisos(EventBus eventBus, HashMap<String,ArrayList<String>> permisos, String modulo, String permiso){
		if(permisos.get(modulo).contains(permiso)){
			return true;
		}else if(eventBus != null){
			eventBus.fireEvent(new MensajeEvent("No tiene los permisos suficientes para "+permiso+ " en "+modulo , MensajeEvent.MSG_PERMISOS, true));
			return false;
		}else{
			return false;
		}
	}
	
}
