package com.dreamer8.yosimce.client;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.general.DetalleEstablecimientoPlace;
import com.dreamer8.yosimce.client.general.GeneralActivity;
import com.dreamer8.yosimce.client.general.GeneralPlace;
import com.dreamer8.yosimce.client.general.HistorialEstablecimientoPlace;
import com.dreamer8.yosimce.client.planificacion.AgendamientosActivity;
import com.dreamer8.yosimce.client.planificacion.AgendamientosPlace;
import com.dreamer8.yosimce.client.planificacion.AgendarVisitaActivity;
import com.dreamer8.yosimce.client.planificacion.AgendarVisitaPlace;
import com.dreamer8.yosimce.client.planificacion.DetalleAgendaEstablecimientoActivity;
import com.dreamer8.yosimce.client.planificacion.DetalleAgendaEstablecimientoPlace;
import com.dreamer8.yosimce.client.planificacion.PlanificacionActivity;
import com.dreamer8.yosimce.client.planificacion.PlanificacionPlace;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class ContentActivityMapper implements ActivityMapper {

	
	private final ClientFactory factory;
	private HashMap<String,ArrayList<String>> permisos;
	
	public ContentActivityMapper(ClientFactory factory){
		this.factory = factory;
		this.factory.getEventBus().addHandler(PermisosEvent.TYPE, new PermisosEvent.PermisosHandler() {
			
			@Override
			public void onPermisos(PermisosEvent event) {
				permisos = event.getPermisos();
			}
		});
	}
	
	@Override
	public Activity getActivity(Place place) {
		
		if(place instanceof GeneralPlace){
			return new GeneralActivity(factory, (GeneralPlace)place, permisos);
		}else if(place instanceof DetalleEstablecimientoPlace){
			
		}else if(place instanceof HistorialEstablecimientoPlace){
		
		}else if(place instanceof PlanificacionPlace){
			return new PlanificacionActivity(factory,(PlanificacionPlace)place, permisos);
		}else if(place instanceof AgendamientosPlace){
			return new AgendamientosActivity(factory, (AgendamientosPlace)place, permisos);
		}else if(place instanceof AgendarVisitaPlace){
			return new AgendarVisitaActivity(factory, (AgendarVisitaPlace)place, permisos);
		}else if(place instanceof DetalleAgendaEstablecimientoPlace){
			return new DetalleAgendaEstablecimientoActivity(factory, (DetalleAgendaEstablecimientoPlace)place, permisos);
		}else if(place instanceof SimcePlace){
			
		}
		return null;
	}

}