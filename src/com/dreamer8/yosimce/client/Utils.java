package com.dreamer8.yosimce.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;

public class Utils {
	
	private static final DateTimeFormat format = DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_MEDIUM);

	public static boolean hasPermisos(HashMap<String,ArrayList<String>> permisos, String modulo, String permiso){
		return hasPermisos(null,permisos,modulo,permiso);
	}
	
	public static boolean hasPermisos(EventBus eventBus, HashMap<String,ArrayList<String>> permisos, String modulo, String permiso){
		if(permisos.containsKey(modulo) && permisos.get(modulo).contains(permiso)){
			if(eventBus!=null){
				GATracker.trackEvent("rpc call",modulo, permiso);
			}
			return true;
		}else if(eventBus != null){
			eventBus.fireEvent(new MensajeEvent("No tiene los permisos suficientes para "+permiso+ " en "+modulo , MensajeEvent.MSG_PERMISOS, true));
			return false;
		}else{
			return false;
		}
	}
	
	
	public static String getDateString(Date d){
		if(d == null){
			return "";
		}
		return format.format(d);
	}
	
	public static Date getDate(String sd){
		if(sd == null){
			return null;
		}
		return format.parse(sd);
	}
	
	
	
}
