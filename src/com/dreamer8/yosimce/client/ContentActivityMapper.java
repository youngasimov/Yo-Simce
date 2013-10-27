package com.dreamer8.yosimce.client;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.actividad.ActividadesActivity;
import com.dreamer8.yosimce.client.actividad.ActividadesPlace;
import com.dreamer8.yosimce.client.actividad.AprobarSupervisoresActivity;
import com.dreamer8.yosimce.client.actividad.AprobarSupervisoresPlace;
import com.dreamer8.yosimce.client.actividad.FormActividadActivity;
import com.dreamer8.yosimce.client.actividad.FormActividadPlace;
import com.dreamer8.yosimce.client.actividad.MaterialDefectuosoActivity;
import com.dreamer8.yosimce.client.actividad.MaterialDefectuosoPlace;
import com.dreamer8.yosimce.client.actividad.SincronizacionActivity;
import com.dreamer8.yosimce.client.actividad.SincronizacionPlace;
import com.dreamer8.yosimce.client.administracion.PermisosActivity;
import com.dreamer8.yosimce.client.administracion.PermisosPlace;
import com.dreamer8.yosimce.client.administracion.ReportesActivity;
import com.dreamer8.yosimce.client.administracion.ReportesPlace;
import com.dreamer8.yosimce.client.general.CentroControlActivity;
import com.dreamer8.yosimce.client.general.CentroControlPlace;
import com.dreamer8.yosimce.client.general.DetalleCursoActivity;
import com.dreamer8.yosimce.client.general.DetalleCursoPlace;
import com.dreamer8.yosimce.client.material.BuscadorCodigoActivity;
import com.dreamer8.yosimce.client.material.BuscadorCodigoPlace;
import com.dreamer8.yosimce.client.material.CentroOperacionActivity;
import com.dreamer8.yosimce.client.material.CentroOperacionPlace;
import com.dreamer8.yosimce.client.planificacion.AgendamientosActivity;
import com.dreamer8.yosimce.client.planificacion.AgendamientosPlace;
import com.dreamer8.yosimce.client.planificacion.AgendarVisitaActivity;
import com.dreamer8.yosimce.client.planificacion.AgendarVisitaPlace;
import com.dreamer8.yosimce.client.planificacion.DetalleAgendaActivity;
import com.dreamer8.yosimce.client.planificacion.DetalleAgendaPlace;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
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
	public Activity getActivity(final Place place) {
		if(place instanceof CentroControlPlace){
			return new ActivityAsyncProxy() {

				@Override
				protected void doAsync(RunAsyncCallback callback) {
					GWT.runAsync(callback);
				}

				@Override
				protected SimceActivity createInstance() {
					return new CentroControlActivity(factory, (CentroControlPlace)place, permisos);
				}
			};
		}else if(place instanceof DetalleCursoPlace){
			return new ActivityAsyncProxy() {

				@Override
				protected void doAsync(RunAsyncCallback callback) {
					GWT.runAsync(callback);
				}

				@Override
				protected SimceActivity createInstance() {
					return new DetalleCursoActivity(factory, (DetalleCursoPlace)place, permisos);
				}
			};
		}else if(place instanceof AgendamientosPlace){
			return new ActivityAsyncProxy() {

				@Override
				protected void doAsync(RunAsyncCallback callback) {
					GWT.runAsync(callback);
				}

				@Override
				protected SimceActivity createInstance() {
					return new AgendamientosActivity(factory, (AgendamientosPlace)place, permisos);
				}
			};
		}else if(place instanceof AgendarVisitaPlace){
			return new ActivityAsyncProxy() {

				@Override
				protected void doAsync(RunAsyncCallback callback) {
					GWT.runAsync(callback);
				}

				@Override
				protected SimceActivity createInstance() {
					return new AgendarVisitaActivity(factory, (AgendarVisitaPlace)place, permisos);
				}
			};
		}else if(place instanceof DetalleAgendaPlace){
			return new ActivityAsyncProxy() {

				@Override
				protected void doAsync(RunAsyncCallback callback) {
					GWT.runAsync(callback);
				}

				@Override
				protected SimceActivity createInstance() {
					return new DetalleAgendaActivity(factory, (DetalleAgendaPlace)place, permisos);
				}
			};
		}else if(place instanceof ActividadesPlace){
			return new ActivityAsyncProxy() {

				@Override
				protected void doAsync(RunAsyncCallback callback) {
					GWT.runAsync(callback);
				}

				@Override
				protected SimceActivity createInstance() {
					return new ActividadesActivity(factory, (ActividadesPlace)place, permisos);
				}
			};
		}else if(place instanceof FormActividadPlace){
			return new ActivityAsyncProxy() {

				@Override
				protected void doAsync(RunAsyncCallback callback) {
					GWT.runAsync(callback);
				}

				@Override
				protected SimceActivity createInstance() {
					return new FormActividadActivity(factory, (FormActividadPlace)place, permisos);
				}
			};
			
		}else if(place instanceof SincronizacionPlace){
			return new ActivityAsyncProxy() {

				@Override
				protected void doAsync(RunAsyncCallback callback) {
					GWT.runAsync(callback);
				}

				@Override
				protected SimceActivity createInstance() {
					return new SincronizacionActivity(factory, (SincronizacionPlace)place, permisos);
				}
			};
			
		}else if(place instanceof MaterialDefectuosoPlace){
			return new ActivityAsyncProxy() {

				@Override
				protected void doAsync(RunAsyncCallback callback) {
					GWT.runAsync(callback);
				}

				@Override
				protected SimceActivity createInstance() {
					return new MaterialDefectuosoActivity(factory, (MaterialDefectuosoPlace)place, permisos);
				}
			};
		}else if(place instanceof AprobarSupervisoresPlace){
			return new ActivityAsyncProxy() {

				@Override
				protected void doAsync(RunAsyncCallback callback) {
					GWT.runAsync(callback);
				}

				@Override
				protected SimceActivity createInstance() {
					return new AprobarSupervisoresActivity(factory, (AprobarSupervisoresPlace)place, permisos);
				}
			};
		}else if(place instanceof CentroOperacionPlace){
			return new ActivityAsyncProxy() {

				@Override
				protected void doAsync(RunAsyncCallback callback) {
					GWT.runAsync(callback);
				}

				@Override
				protected SimceActivity createInstance() {
					return new CentroOperacionActivity(factory, (CentroOperacionPlace)place, permisos);
				}
			};
		}else if(place instanceof BuscadorCodigoPlace){
			return new ActivityAsyncProxy() {

				@Override
				protected void doAsync(RunAsyncCallback callback) {
					GWT.runAsync(callback);
				}

				@Override
				protected SimceActivity createInstance() {
					return new BuscadorCodigoActivity(factory, (BuscadorCodigoPlace)place, permisos);
				}
			};
		}else if(place instanceof PermisosPlace){
			return new ActivityAsyncProxy() {

				@Override
				protected void doAsync(RunAsyncCallback callback) {
					GWT.runAsync(callback);
				}

				@Override
				protected PermisosActivity createInstance() {
					return new PermisosActivity(factory, (PermisosPlace)place, permisos);
				}
			};
		}else if(place instanceof ReportesPlace){
			return new ActivityAsyncProxy() {

				@Override
				protected void doAsync(RunAsyncCallback callback) {
					GWT.runAsync(callback);
				}

				@Override
				protected SimceActivity createInstance() {
					return new ReportesActivity(factory, (ReportesPlace)place, permisos);
				}
			};
		}else if(place instanceof SimcePlace){
			return new MenuActivity(factory, (SimcePlace)place, permisos);
		}
		return null;
	}

}