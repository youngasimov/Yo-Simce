package com.dreamer8.yosimce.client;

import com.dreamer8.yosimce.client.planificacion.AgendaPlace;
import com.dreamer8.yosimce.client.planificacion.AgendarPlace;
import com.dreamer8.yosimce.client.planificacion.DetalleAgendaEstablecimientoPlace;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class ContentActivityMapper implements ActivityMapper {

	
	private ClientFactory factory;
	
	public ContentActivityMapper(ClientFactory factory){
		this.factory = factory;
	}
	
	@Override
	public Activity getActivity(Place place) {
		
		if(place instanceof AgendaPlace){
			
		}else if(place instanceof AgendarPlace){
			
		}else if(place instanceof DetalleAgendaEstablecimientoPlace){
			
		}else if(place instanceof SimcePlace){
			
		}
		return null;
	}

}