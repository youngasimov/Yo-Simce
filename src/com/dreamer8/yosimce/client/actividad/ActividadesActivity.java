package com.dreamer8.yosimce.client.actividad;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.SimceCallback;
import com.dreamer8.yosimce.client.actividad.ui.ActividadesView;
import com.dreamer8.yosimce.client.actividad.ui.ActividadesView.ActividadesPresenter;
import com.dreamer8.yosimce.shared.dto.ActividadPreviewDTO;
import com.dreamer8.yosimce.shared.dto.SectorDTO;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.view.client.Range;

public class ActividadesActivity extends SimceActivity implements
		ActividadesPresenter {

	private final ActividadesPlace place;
	private final ActividadesView view;
	private EventBus eventBus;
	private HashMap<String,String> filtros;
	
	private ArrayList<SectorDTO> regiones;
	private HashMap<Integer,ArrayList<SectorDTO>> comunas;
	
	private Range range;
	
	public ActividadesActivity(ClientFactory factory, ActividadesPlace place,HashMap<String, ArrayList<String>> permisos) {
		super(factory, place, permisos);
		this.place = place;
		view = getFactory().getActividadesView();
		view.setPresenter(this);
		
		filtros = new HashMap<String, String>();
		regiones = new ArrayList<SectorDTO>();
		comunas = new HashMap<Integer, ArrayList<SectorDTO>>();
		view.getDataDisplay().setRowCount(0,true);
	}
	
	@Override
	public void init(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view.asWidget());
		this.eventBus = eventBus;
		regiones.clear();
		comunas.clear();
		range = view.getDataDisplay().getVisibleRange();
		
		getFactory().getGeneralService().getRegiones(new SimceCallback<ArrayList<SectorDTO>>(eventBus) {

			@Override
			public void success(ArrayList<SectorDTO> result) {
				regiones.addAll(result);
				view.setRegiones(regiones);
				updateFiltros();
			}
		});
		
		
		
		
	}

	@Override
	public void onExportarClick() {
		// TODO Auto-generated method stub

	}
	

	@Override
	public void onCancelarFiltroClick() {
		updateFiltros();
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
	public void onRegionChange(final int regionId) {
		if(!comunas.containsKey(regionId)){
			SectorDTO region = null;
			for(SectorDTO r:regiones){
				if(r.getIdSector() == regionId){
					region = r;
					break;
				}
			}
			getFactory().getGeneralService().getComunas(region, new SimceCallback<ArrayList<SectorDTO>>(eventBus) {
	
				@Override
				public void success(ArrayList<SectorDTO> result) {
					comunas.put(regionId, result);
					view.setComunas(result);
					if(place.getComunaId()!=-1){
						view.setSelectedComuna(place.getComunaId());
					}
				}
			});
		}else{
			view.setComunas(comunas.get(regionId));
			if(place.getComunaId()!=-1){
				view.setSelectedComuna(place.getComunaId());
			}
		}
	}
	
	@Override
	public void onStop() {
		super.onStop();
		view.getDataDisplay().setRowCount(0);
		view.clearCursoSelection();
	}
	
	private void updateFiltros(){
		
		filtros.clear();
		filtros.put(ActividadService.FKEY_ACTIVIDADES_NO_INICIADAS, (place.isShowActividadesNoInciadas())?"1":"0");
		view.setActividadesNoIniciadas(place.isShowActividadesNoInciadas());
		filtros.put(ActividadService.FKEY_ACTIVIDADES_TERMINADAS, (place.isShowActividadesTerminadas())?"1":"0");
		view.setActividadesTerminadas(place.isShowActividadesTerminadas());
		filtros.put(ActividadService.FKEY_ACTIVIDADES_CONTINGENCIA, (place.isShowActividadesContingencia())?"1":"0");
		view.setActividadesContingencia(place.isShowActividadesContingencia());
		filtros.put(ActividadService.FKEY_ACTIVIDADES_PROBLEMA, (place.isShowActividadesProblema())?"1":"0");
		view.setActividadesProblema(place.isShowActividadesProblema());
		filtros.put(ActividadService.FKEY_ACTIVIDADES_SINCRONIZADAS, (place.isShowActividadesSincronizadas())?"1":"0");
		view.setActividadesSincronizadas(place.isShowActividadesSincronizadas());
		if(place.getRegionId()!=-1){
			filtros.put(ActividadService.FKEY_REGION, place.getRegionId()+"");
			view.setSelectedRegion(place.getRegionId());
			onRegionChange(place.getRegionId());
		}
		if(place.getComunaId()!=-1){
			filtros.put(ActividadService.FKEY_COMUNA, place.getComunaId()+"");
		}
		
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
		
	}
}
