package com.dreamer8.yosimce.client.planificacion;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.SimceCallback;
import com.dreamer8.yosimce.client.planificacion.ui.AgendamientosView;
import com.dreamer8.yosimce.client.planificacion.ui.AgendamientosView.AgendamientosPresenter;
import com.dreamer8.yosimce.shared.dto.AgendaPreviewDTO;
import com.dreamer8.yosimce.shared.dto.EstadoAgendaDTO;
import com.dreamer8.yosimce.shared.dto.SectorDTO;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.view.client.Range;

public class AgendamientosActivity extends SimceActivity implements
		AgendamientosPresenter {

	private final AgendamientosPlace place;
	private AgendamientosView view;
	private EventBus eventBus;
	private HashMap<String,String> filtros;
	
	private ArrayList<SectorDTO> regiones;
	private HashMap<Integer,ArrayList<SectorDTO>> comunas;
	private ArrayList<EstadoAgendaDTO> estados;
	
	private boolean estadosReady;
	private boolean regionesReady;
	
	private Range range;
	
	public AgendamientosActivity(ClientFactory factory,AgendamientosPlace place,HashMap<String, ArrayList<String>> permisos) {
		super(factory,place,permisos);
		this.place = place;
		view = factory.getAgendamientosView();
		view.setPresenter(this);
		filtros = new HashMap<String, String>();
		regiones = new ArrayList<SectorDTO>();
		comunas = new HashMap<Integer, ArrayList<SectorDTO>>();
		estados = new ArrayList<EstadoAgendaDTO>();
		estadosReady = false;
		regionesReady = false;
		range = view.getDataDisplay().getVisibleRange();
		view.getDataDisplay().setRowCount(0,true);
	}
	
	@Override
	public void init(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view.asWidget());
		this.eventBus = eventBus;
		regiones.clear();
		comunas.clear();
		estados.clear();
		estadosReady = false;
		regionesReady = false;
		
		
		getFactory().getGeneralService().getRegiones(new SimceCallback<ArrayList<SectorDTO>>(eventBus) {

			@Override
			public void success(ArrayList<SectorDTO> result) {
				regiones.addAll(result);
				view.setRegiones(regiones);
				regionesReady = true;
				if(estadosReady){
					updateFiltros();
				}
			}
		});
		
		getFactory().getPlanificacionService().getEstadosAgenda(new SimceCallback<ArrayList<EstadoAgendaDTO>>(eventBus) {

			@Override
			public void success(ArrayList<EstadoAgendaDTO> result) {
				estados = result;
				view.setEstados(estados);
				if(place.getEstadosSeleccionados().isEmpty()){
					ArrayList<Integer> x = new ArrayList<Integer>();
					for(EstadoAgendaDTO eadto:result){
						x.add(eadto.getId());
					}
					place.setEstadosSeleccionados(x);
				}
				
				estadosReady = true;
				if(regionesReady){
					updateFiltros();
				}
			}
		});
	}
	
	@Override
	public void onExportarClick() {
		
	}
	
	@Override
	public void onCancelarFiltroClick() {
		updateFiltros();
	}
	
	@Override
	public void onRangeChange(Range r) {
		this.range = r;
		getFactory().getPlanificacionService().getPreviewAgendamientos(range.getStart(), range.getLength(), filtros, new SimceCallback<ArrayList<AgendaPreviewDTO>>(eventBus) {

			@Override
			public void success(ArrayList<AgendaPreviewDTO> result) {
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
		if(place.getDesdeTimestamp()!=-1){
			Date d = new Date(place.getDesdeTimestamp());
			filtros.put(PlanificacionService.FKEY_DESDE, d.toString());
			view.setDesde(d);
		}else{
			view.setDesde(null);
		}
		if(place.getHastaTimestamp()!=-1){
			Date d = new Date(place.getHastaTimestamp());
			filtros.put(PlanificacionService.FKEY_HASTA, d.toString());
			view.setHasta(d);
		}else{
			view.setHasta(null);
		}
		if(place.getRegionId()!=-1){
			filtros.put(PlanificacionService.FKEY_REGION, place.getRegionId()+"");
			view.setSelectedRegion(place.getRegionId());
			onRegionChange(place.getRegionId());
		}
		if(place.getComunaId()!=-1){
			filtros.put(PlanificacionService.FKEY_COMUNA, place.getComunaId()+"");
		}
		if(place.getEstadosSeleccionados().size()>0){
			view.setSelectedEstados(place.getEstadosSeleccionados());
			StringBuilder b = new StringBuilder();
			for(Integer id:place.getEstadosSeleccionados()){
				b.append(id);
				b.append(PlanificacionService.SEPARATOR);
			}
			b.deleteCharAt(b.length()-1);
			filtros.put(PlanificacionService.FKEY_ESTADOS, b.toString());
		}
		getFactory().getPlanificacionService().getTotalPreviewAgendamientos(filtros, new SimceCallback<Integer>(eventBus) {

			@Override
			public void success(Integer result) {
				view.getDataDisplay().setRowCount(result,true);
			}
		});
		
		getFactory().getPlanificacionService().getPreviewAgendamientos(range.getStart(), range.getLength(), filtros, new SimceCallback<ArrayList<AgendaPreviewDTO>>(eventBus) {

			@Override
			public void success(ArrayList<AgendaPreviewDTO> result) {
				view.getDataDisplay().setRowData(range.getStart(), result);
			}
		});
		
	}
}
