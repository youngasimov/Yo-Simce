package com.dreamer8.yosimce.client.general;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.MensajeEvent;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.SimceCallback;
import com.dreamer8.yosimce.client.general.ui.CentroControlView;
import com.dreamer8.yosimce.client.general.ui.CentroControlView.CentroControlPresenter;
import com.dreamer8.yosimce.client.material.CentroOperacionPlace;
import com.dreamer8.yosimce.shared.dto.CentroOperacionDTO;
import com.dreamer8.yosimce.shared.dto.SectorDTO;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class CentroControlActivity extends SimceActivity implements
		CentroControlPresenter {
	
	private final CentroControlView view;
	private final CentroControlPlace place;
	private CentroOperacionPlace coPlace;
	private EventBus eventBus;
	private Timer updateTimer;
	
	private ArrayList<SectorDTO> regiones;
	private HashMap<Integer,ArrayList<SectorDTO>> comunas;
	private HashMap<Integer,ArrayList<SectorDTO>> zonas;
	private ArrayList<CentroOperacionDTO> centros;
	private ArrayList<Integer> monitoring;
	private HashMap<Date,ArrayList<CentroOperacionDTO>> historial;
	private boolean firstLoad;
	
	public CentroControlActivity(ClientFactory factory, CentroControlPlace place,HashMap<String, ArrayList<String>> permisos) {
		super(factory, place, permisos);
		this.place = place;
		this.coPlace = new CentroOperacionPlace();
		this.view = getFactory().getCentroControlView();
		this.view.setPresenter(this);
		monitoring = new ArrayList<Integer>();
		comunas = new HashMap<Integer, ArrayList<SectorDTO>>();
		zonas = new HashMap<Integer, ArrayList<SectorDTO>>();
		historial = new HashMap<Date, ArrayList<CentroOperacionDTO>>();
	}

	@Override
	public void init(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(this.view.asWidget());
		this.eventBus = eventBus;
		coPlace.setAplicacionId(place.getAplicacionId());
		coPlace.setNivelId(place.getNivelId());
		coPlace.setTipoId(place.getTipoId());
		firstLoad = true;
		monitoring.clear();
		view.setRegiones(new ArrayList<SectorDTO>());
		view.setComunas(new ArrayList<SectorDTO>());
		view.setZonas(new ArrayList<SectorDTO>());
		getFactory().getGeneralService().getRegiones(new SimceCallback<ArrayList<SectorDTO>>(eventBus,true) {

			@Override
			public void success(ArrayList<SectorDTO> result) {
				regiones = result;
				view.setRegiones(regiones);
			}
		});
		
		updateCentros();
	}
	
	
	@Override
	public void actualizar() {
		updateCentros();
	}
	
	@Override
	public void onRegionChange(final int regionId) {
		
		if(regionId == -1){
			view.setComunas(new ArrayList<SectorDTO>());
			view.setZonas(new ArrayList<SectorDTO>());
			return;
		}
		
		SectorDTO region = null;
		for(SectorDTO r:regiones){
			if(r.getIdSector() == regionId){
				region = r;
				break;
			}
		}
		if(region == null){
			view.setComunas(new ArrayList<SectorDTO>());
			view.setZonas(new ArrayList<SectorDTO>());
			return;
		}
		
		if(comunas.containsKey(regionId)){
			view.setComunas(comunas.get(regionId));
		}else{
			getFactory().getGeneralService().getComunas(region, new SimceCallback<ArrayList<SectorDTO>>(eventBus,false) {

				@Override
				public void success(ArrayList<SectorDTO> result) {
					comunas.put(regionId, result);
					view.setComunas(result);
				}
			});
		}
		if(zonas.containsKey(regionId)){
			view.setZonas(zonas.get(regionId));
		}else{
			getFactory().getGeneralService().getZonas(region, new SimceCallback<ArrayList<SectorDTO>>(eventBus,false) {

				@Override
				public void success(ArrayList<SectorDTO> result) {
					zonas.put(regionId, result);
					view.setZonas(result);
				}
			});
		}
	}

	@Override
	public void activarAutoRecarga(boolean activada, int time) {
		if(activada){
			eventBus.fireEvent(new MensajeEvent("Actualizaciones automáticas cada "+(time/1000)+" segundos",MensajeEvent.MSG_OK,true));
			updateTimer = new Timer() {
				
				@Override
				public void run() {
					updateCentros();
				}
			};
			updateTimer.scheduleRepeating(time);
			updateCentros();
		}else{
			eventBus.fireEvent(new MensajeEvent("Actualizaciones automáticas desactivadas",MensajeEvent.MSG_OK,true));
			if(updateTimer!=null){
				updateTimer.cancel();
				updateTimer = null;
			}
		}
	}
	
	@Override
	public void addToMonitor(ArrayList<CentroOperacionDTO> centros) {
		monitoring.clear();
		for(CentroOperacionDTO centro:centros){
			monitoring.add(centro.getId());
		}
		updateRealTime();
	}
	
	@Override
	public void selectUsingFiltro() {
		int r = view.getSelectedRegion();
		int z = view.getSelectedZona();
		int c = view.getSelectedComuna();
		
		ArrayList<Integer> selected = new ArrayList<Integer>();
		for(CentroOperacionDTO co: view.getAllDataProvider().getList()){
			if((c >=0 || z>=0) && (co.getIdComuna() == c || co.getIdZona()==z)){
				selected.add(co.getId());
			}else if(r>=0 && co.getIdRegion() == r){
				selected.add(co.getId());
			}else if(r == -1){
				selected.add(co.getId());
			}
		}
		view.setSelectedCos(selected);
	}
	
	@Override
	public String getCentroOperacionToken(int centroId) {
		coPlace.setCentroId(centroId);
		return getFactory().getPlaceHistoryMapper().getToken(coPlace);
	}
	
	private void updateCentros(){
		getFactory().getGeneralService().getCentrosOperacion(new SimceCallback<ArrayList<CentroOperacionDTO>>(eventBus, firstLoad) {

			@Override
			public void success(ArrayList<CentroOperacionDTO> result) {
				boolean addToMonitor = false;
				if(monitoring.isEmpty()){
					addToMonitor = true;
				}
				historial.put(new Date(), result);
				for(CentroOperacionDTO centro:result){
					centro.setEnCentro((centro.getEnCentro() == null)?0:centro.getEnCentro());
					centro.setEnEstablecimiento((centro.getEnEstablecimiento() == null)?0:centro.getEnEstablecimiento());
					centro.setEnImprenta((centro.getEnImprenta() == null)?0:centro.getEnImprenta());
					centro.setEnMinisterio((centro.getEnMinisterio() == null)?0:centro.getEnMinisterio());
					centro.setContingenciaEnCentro((centro.getContingenciaEnCentro() == null)?0:centro.getContingenciaEnCentro());
					centro.setContingenciaEnEstablecimiento((centro.getContingenciaEnEstablecimiento() == null)?0:centro.getContingenciaEnEstablecimiento());
					centro.setContingenciaEnImprenta((centro.getContingenciaEnImprenta() == null)?0:centro.getContingenciaEnImprenta());
					centro.setContingenciaEnMinisterio((centro.getContingenciaEnMinisterio() == null)?0:centro.getContingenciaEnMinisterio());
					if(addToMonitor){
						monitoring.add(centro.getId());
					}
				}
				centros = result;
				firstLoad = false;
				updateRealTime();
				updateMap();
				updateLineasTiempo();
			}
		});
		
	}
	
	private void updateMap(){
		
	}
	
	private void updateLineasTiempo(){
		ArrayList<Date> keys = new ArrayList<Date>(historial.keySet());
		ArrayList<CentroControlView.GraphItem> data = new ArrayList<CentroControlView.GraphItem>();
		ArrayList<CentroOperacionDTO> centros;
		CentroControlView.GraphItem aux;
		for(Date d:keys){
			centros = historial.get(d);
			aux = new CentroControlView.GraphItem();
			aux.mc = 0;
			aux.me = 0;
			aux.mi = 0;
			aux.mm = 0;
			aux.cc = 0;
			aux.ce = 0;
			aux.ci = 0;
			aux.cm = 0;
			for(CentroOperacionDTO co:centros){
				if(monitoring.contains(co.getId())){
					aux.mc = aux.mc+co.getEnCentro();
					aux.me = aux.me+co.getEnEstablecimiento();
					aux.mi = aux.mi+co.getEnImprenta();
					aux.mm = aux.mm+co.getEnMinisterio();
					aux.cc = aux.cc+co.getContingenciaEnCentro();
					aux.ce = aux.ce+co.getContingenciaEnEstablecimiento();
					aux.ci = aux.ci+co.getContingenciaEnImprenta();
					aux.cm = aux.cm+co.getContingenciaEnMinisterio();
				}
			}
			aux.d = d;
			data.add(aux);
		}
		view.updateGraphs(data);
	}
	
	private void updateRealTime(){
		view.getAllDataProvider().setList(centros);
		ArrayList<CentroOperacionDTO> monitor = new ArrayList<CentroOperacionDTO>(monitoring.size());
		for(CentroOperacionDTO centro:centros){
			if(monitoring.contains(centro.getId())){
				monitor.add(centro);
			}
		}
		view.getMonitoringDataProvider().setList(monitor);
		view.setMonitoreados(monitoring.size());
	}

	

}
