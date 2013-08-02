package com.dreamer8.yosimce.client;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.administracion.AdminActivity;
import com.dreamer8.yosimce.client.administracion.AdminEventosPlace;
import com.dreamer8.yosimce.client.administracion.AdminPlace;
import com.dreamer8.yosimce.client.administracion.AdminUsuariosActivity;
import com.dreamer8.yosimce.client.administracion.AdminUsuariosPlace;
import com.dreamer8.yosimce.client.general.DetalleCursoActivity;
import com.dreamer8.yosimce.client.general.DetalleCursoPlace;
import com.dreamer8.yosimce.client.general.GeneralActivity;
import com.dreamer8.yosimce.client.general.GeneralPlace;
import com.dreamer8.yosimce.client.general.HistorialCursoPlace;
import com.dreamer8.yosimce.client.planificacion.AgendamientosActivity;
import com.dreamer8.yosimce.client.planificacion.AgendamientosPlace;
import com.dreamer8.yosimce.client.planificacion.AgendarVisitaActivity;
import com.dreamer8.yosimce.client.planificacion.AgendarVisitaPlace;
import com.dreamer8.yosimce.client.planificacion.DetalleAgendaActivity;
import com.dreamer8.yosimce.client.planificacion.DetalleAgendaPlace;
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
		}else if(place instanceof DetalleCursoPlace){
			return new DetalleCursoActivity(factory, (DetalleCursoPlace)place, permisos);
		}else if(place instanceof HistorialCursoPlace){
		
		}else if(place instanceof PlanificacionPlace){
			return new PlanificacionActivity(factory,(PlanificacionPlace)place, permisos);
		}else if(place instanceof AgendamientosPlace){
			return new AgendamientosActivity(factory, (AgendamientosPlace)place, permisos);
		}else if(place instanceof AgendarVisitaPlace){
			return new AgendarVisitaActivity(factory, (AgendarVisitaPlace)place, permisos);
		}else if(place instanceof DetalleAgendaPlace){
			return new DetalleAgendaActivity(factory, (DetalleAgendaPlace)place, permisos);
		}
		
		else if(place instanceof AdminPlace){
			return new AdminActivity(factory, (AdminPlace)place, permisos);
		}else if(place instanceof AdminUsuariosPlace){
			return new AdminUsuariosActivity(factory, (AdminUsuariosPlace)place, permisos);
		}else if(place instanceof AdminEventosPlace){
			
		}else if(place instanceof SimcePlace){
			
		}
		return null;
	}

}