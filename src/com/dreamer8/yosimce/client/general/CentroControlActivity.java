package com.dreamer8.yosimce.client.general;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.MensajeEvent;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.SimceCallback;
import com.dreamer8.yosimce.client.YoSimce;
import com.dreamer8.yosimce.client.general.ui.CentroControlView;
import com.dreamer8.yosimce.client.general.ui.CentroControlView.CentroControlPresenter;
import com.dreamer8.yosimce.client.material.CentroOperacionPlace;
import com.dreamer8.yosimce.shared.dto.CentroOperacionDTO;
import com.dreamer8.yosimce.shared.dto.ControlCentroOperacionDTO;
import com.dreamer8.yosimce.shared.dto.SectorDTO;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class CentroControlActivity extends SimceActivity implements
		CentroControlPresenter {
	
	private final CentroControlView view;
	private final CentroControlPlace place;
	private CentroOperacionPlace coPlace;
	private EventBus eventBus;
	private Timer updateTimer;
	
	private LocalCentroControlService localService;
	private ArrayList<SectorDTO> regiones;
	private HashMap<Integer,ArrayList<SectorDTO>> comunas;
	private HashMap<Integer,ArrayList<SectorDTO>> zonas;
	private ArrayList<CentroOperacionDTO> centros;
	private ArrayList<Integer> monitoring;
	private HashMap<Date,ArrayList<CentroOperacionDTO>> historial;
	ArrayList<CentroControlView.GraphItem> data;
	private boolean firstLoad;
	private int evento;
	private String userKey;
	private boolean bingoReady;

	private ArrayList<String> zonasControl;
	
	public CentroControlActivity(ClientFactory factory, CentroControlPlace place,HashMap<String, ArrayList<String>> permisos) {
		super(factory, place, permisos);
		this.place = place;
		this.coPlace = new CentroOperacionPlace();
		this.view = getFactory().getCentroControlView();
		this.view.setPresenter(this);
		monitoring = new ArrayList<Integer>();
		comunas = new HashMap<Integer, ArrayList<SectorDTO>>();
		zonas = new HashMap<Integer, ArrayList<SectorDTO>>();
		evento  = CentroControlView.EVENT_IMPRENTA;
		userKey= Cookies.getCookie(YoSimce.TOKEN_COOKIE);
		if(userKey == null || userKey.length() == 0){
			userKey = Cookies.getCookie(YoSimce.TOKEN_COOKIE_DEMO);
		}
		if(userKey==null){
			userKey = "";
		}else{
			userKey = userKey+Cookies.getCookie("a")+"-"+Cookies.getCookie("n")+"-"+Cookies.getCookie("t");
		}
	}

	@Override
	public void init(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(this.view.asWidget());
		this.eventBus = eventBus;
		localService = new LocalCentroControlService(eventBus);
		coPlace.setAplicacionId(place.getAplicacionId());
		coPlace.setNivelId(place.getNivelId());
		coPlace.setTipoId(place.getTipoId());
		firstLoad = true;
		bingoReady = false;
		monitoring = localService.getMonitoreados(userKey);
		if(monitoring == null){
			monitoring = new ArrayList<Integer>();
		}
		view.setRegiones(new ArrayList<SectorDTO>());
		view.setComunas(new ArrayList<SectorDTO>());
		view.setZonas(new ArrayList<SectorDTO>());
		view.initApis();
		
		Integer i = localService.getEvento(userKey);
		if(i==null){
			evento  = CentroControlView.EVENT_IMPRENTA;
		}else{
			evento = i;
		}
		
		historial  = localService.getHistorial(userKey);
		if(historial == null){
			historial = new HashMap<Date, ArrayList<CentroOperacionDTO>>();
		}
		ArrayList<Date> remove = new ArrayList<Date>();
		ArrayList<Date> keys = new ArrayList<Date>(historial.keySet());
		Collections.sort(keys);
		int i2 = 0;
		for(Date d:keys){
			if(i2<keys.size()-30){
				remove.add(d);
			}
			i2++;
		}
		for(Date d:remove){
			historial.remove(d);
		}
		
		getFactory().getGeneralService().getRegiones(new SimceCallback<ArrayList<SectorDTO>>(eventBus,true) {

			@Override
			public void success(ArrayList<SectorDTO> result) {
				regiones = result;
				view.setRegiones(regiones);
				if(!bingoReady && centros!=null && !centros.isEmpty()){
					buildBingo();
				}
			}
		});
		view.setEvento(evento);
		updateCentros();
		
		
		/*
		getFactory().getGeneralService().getCentrosOperacionParaControl(new SimceCallback<ArrayList<ControlCentroOperacionDTO>>(eventBus,true) {

			@Override
			public void success(ArrayList<ControlCentroOperacionDTO> result) {
				zonasControl = new ArrayList<String>();
				for(ControlCentroOperacionDTO centro:result){
					if(centro.getZona() != null && !zonasControl.contains(centro.getZona())){
						zonasControl.add(centro.getZona());
					}
				}
				
				Collections.sort(result, new Comparator<ControlCentroOperacionDTO>() {

					@Override
					public int compare(ControlCentroOperacionDTO o1,
							ControlCentroOperacionDTO o2) {
						if(o1.getZona().equals(o2.getZona())){
							return o1.getCo().compareTo(o2.getCo());
						}else{
							return o1.getZona().compareTo(o2.getZona());
						}
					}
				});
				
				view.getControlDataProvider().setList(result);
				
			}
			
		});*/
	}
	
	@Override
	public void onStop() {
		if(updateTimer!=null){
			updateTimer.cancel();
		}
		updateTimer = null;
		super.onStop();
	}
	
	@Override
	public void onApisReady() {
		if(data!=null && !data.isEmpty()){
			view.updateGraphs(data);
		}
		updateMap();
	}
	
	@Override
	public void onEventChange(int event) {
		evento = event;
		localService.setEvento(userKey, evento);
		updateMap();
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
		localService.setMonitoreados(userKey,monitoring);
		updateRealTime();
		view.clearMarkers();
		updateMap();
		view.setTab(0);
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
			}else if(r>=0 && c==-1 && z == -1 && co.getIdRegion() == r){
				selected.add(co.getId());
			}else if(r == -1){
				selected.add(co.getId());
			}
		}
		view.setSelectedCos(selected);
		view.setTab(1);
	}
	
	@Override
	public String getCentroOperacionToken(int centroId) {
		coPlace.setCentroId(centroId);
		return getFactory().getPlaceHistoryMapper().getToken(coPlace);
	}
	
	@Override
	public void onCentroSelected(int id) {
		for(CentroOperacionDTO c:centros){
			if(c.getId() == id){
				view.showCentroOperacionInfo(c);
				return;
			}
		}
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
				localService.setHistorial(userKey, historial);
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
				if(!bingoReady && regiones!=null && !regiones.isEmpty()){
					buildBingo();
				}
			}
		});
		
	}
	
	private void updateMap(){
		if(monitoring != null && !monitoring.isEmpty()){
			ArrayList<CentroOperacionDTO> cs = new ArrayList<CentroOperacionDTO>();
			for(CentroOperacionDTO c:centros){
				if(monitoring.contains(c.getId())){
					cs.add(c);
				}
			}
			view.updateMarkers(evento, cs);
		}
	}
	
	private void updateLineasTiempo(){
		
		ArrayList<Date> remove = new ArrayList<Date>();
		ArrayList<Date> keys = new ArrayList<Date>(historial.keySet());
		Collections.sort(keys);
		int i2 = 0;
		for(Date d:keys){
			if(i2<keys.size()-30){
				remove.add(d);
			}
			i2++;
		}
		for(Date d:remove){
			historial.remove(d);
		}
		
		keys = new ArrayList<Date>(historial.keySet());
		if(data==null){
			data = new ArrayList<CentroControlView.GraphItem>();
		}else{
			data.clear();
		}
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
		if(view.isChartApiReady()){
			view.updateGraphs(data);
		}
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
	
	private void buildBingo(){
		bingoReady = true;
		for(SectorDTO region:regiones){
			if(region.getIdSector() == 13){
				getFactory().getGeneralService().getZonas(region, new SimceCallback<ArrayList<SectorDTO>>(eventBus,false) {

					@Override
					public void success(ArrayList<SectorDTO> result) {
						ArrayList<String> cos = new ArrayList<String>();
						HashMap<String, ArrayList<String>> bingo = new HashMap<String, ArrayList<String>>();
						Collections.sort(result);
						for(SectorDTO zona:result){
							for(CentroOperacionDTO co:centros){
								if(zona.getIdSector() == co.getIdZona()){
									cos.add(co.getNombre().substring(2));
								}
							}
							Collections.sort(cos);
							bingo.put(zona.getSector(), cos);
							cos = new ArrayList<String>();
						}
						view.setBingoRM(bingo);
					}
				});
			}
			if(region.getIdSector() == 8){
				getFactory().getGeneralService().getZonas(region, new SimceCallback<ArrayList<SectorDTO>>(eventBus,false) {

					@Override
					public void success(ArrayList<SectorDTO> result) {
						ArrayList<String> cos = new ArrayList<String>();
						HashMap<String, ArrayList<String>> bingo = new HashMap<String, ArrayList<String>>();
						Collections.sort(result);
						for(SectorDTO zona:result){
							for(CentroOperacionDTO co:centros){
								if(zona.getIdSector() == co.getIdZona()){
									cos.add(co.getNombre().substring(2));
								}
							}
							Collections.sort(cos);
							bingo.put(zona.getSector(), cos);
							cos = new ArrayList<String>();
						}
						view.setBingoR8(bingo);
					}
				});
			}
		}
		
	}

	@Override
	public void changeControlStatus(ControlCentroOperacionDTO centro, int estadoId) {
		
	}

	

}
