package com.dreamer8.yosimce.client.actividad;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.SimceCallback;
import com.dreamer8.yosimce.client.actividad.ui.ActividadesView;
import com.dreamer8.yosimce.client.actividad.ui.ActividadesView.ActividadesPresenter;
import com.dreamer8.yosimce.shared.dto.ActividadPreviewDTO;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.view.client.Range;

public class ActividadesActivity extends SimceActivity implements
		ActividadesPresenter {

	private final ActividadesPlace place;
	private final ActividadesView view;
	private EventBus eventBus;
	private HashMap<String,String> filtros;
	
	private Range range;
	
	public ActividadesActivity(ClientFactory factory, ActividadesPlace place,HashMap<String, ArrayList<String>> permisos) {
		super(factory, place, permisos);
		this.place = place;
		view = getFactory().getActividadesView();
		view.setPresenter(this);
	}
	
	@Override
	public void init(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view.asWidget());
		this.eventBus = eventBus;
		range = view.getDataDisplay().getVisibleRange();
		getFactory().getActividadService().getTotalPreviewActividades(filtros, new SimceCallback<Integer>(eventBus) {

			@Override
			public void success(Integer result) {
				view.getDataDisplay().setRowCount(result,true);
			}
		});
		
		getFactory().getActividadService().getPreviewActividades(range.getStart(), range.getLength(), filtros, new SimceCallback<ArrayList<ActividadPreviewDTO>>(eventBus) {

			@Override
			public void success(ArrayList<ActividadPreviewDTO> result) {
				view.getDataDisplay().setRowData(range.getStart(), result);
			}
		});
		
		
		
		ArrayList<ActividadPreviewDTO> lap = new ArrayList<ActividadPreviewDTO>(); 
		ActividadPreviewDTO ap = new ActividadPreviewDTO();
		ap.setRbd("11906");
		ap.setNombreEstablecimiento("COLEGIO CARMELITAS DESCALZAS DE ROSARIO");
		ap.setCurso("2A");
		ap.setTipoEstablecimiento("Seleccionado");
		ap.setCuestionariosPadresApoderadosEntregados(25);
		ap.setCuestionariosPadresApoderadosRecibidos(20);
		ap.setAlumnosTotales(27);
		ap.setAlumnosEvaluados(25);
		ap.setAlumnosSincronizados(9);
		ap.setContingencia(true);
		ap.setContingenciaLimitante(false);
		ap.setCursoId(3484);
		ap.setRegion("Valparaiso");
		ap.setComuna("Viña del mar");
		ap.setMaterialDefectuoso(3);
		lap.add(ap);
		
		ap = new ActividadPreviewDTO();
		ap.setRbd("84736");
		ap.setNombreEstablecimiento("COLEGIO SALESIANOS DE ALAMEDA");
		ap.setCurso("2B");
		ap.setTipoEstablecimiento("Seleccionado");
		ap.setCuestionariosPadresApoderadosEntregados(12);
		ap.setCuestionariosPadresApoderadosRecibidos(12);
		ap.setAlumnosTotales(13);
		ap.setAlumnosEvaluados(12);
		ap.setAlumnosSincronizados(12);
		ap.setContingencia(true);
		ap.setContingenciaLimitante(true);
		ap.setCursoId(9473);
		ap.setRegion("Santiago");
		ap.setComuna("Santiago centro");
		ap.setMaterialDefectuoso(0);
		lap.add(ap);
		
		ap = new ActividadPreviewDTO();
		ap.setRbd("8563");
		ap.setNombreEstablecimiento("ESCUELA PARTICULAR SAN DIEGO");
		ap.setCurso("2A");
		ap.setTipoEstablecimiento("Seleccionado");
		ap.setCuestionariosPadresApoderadosEntregados(12);
		ap.setCuestionariosPadresApoderadosRecibidos(12);
		ap.setAlumnosTotales(13);
		ap.setAlumnosEvaluados(12);
		ap.setAlumnosSincronizados(12);
		ap.setContingencia(false);
		ap.setContingenciaLimitante(false);
		ap.setCursoId(846);
		ap.setRegion("Santiago");
		ap.setComuna("Santiago centro");
		ap.setMaterialDefectuoso(0);
		lap.add(ap);
		
		view.getDataDisplay().setRowCount(3);
		view.getDataDisplay().setRowData(0, lap);
		view.getDataDisplay().setVisibleRange(0, 3);
	}

	@Override
	public void onExportarClick() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void onRegionChange(int regionId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCancelarFiltroClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRangeChange(Range r) {
		this.range = r;
		getFactory().getActividadService().getPreviewActividades(range.getStart(), range.getLength(), filtros, new SimceCallback<ArrayList<ActividadPreviewDTO>>(eventBus) {

			@Override
			public void success(ArrayList<ActividadPreviewDTO> result) {
				view.getDataDisplay().setRowData(range.getStart(), result);
			}
		});
	}
	
	@Override
	public void onStop() {
		super.onStop();
		view.getDataDisplay().setRowCount(0);
		view.clearCursoSelection();
	}
}
