package com.dreamer8.yosimce.client.general;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.MensajeEvent;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.SimceCallback;
import com.dreamer8.yosimce.client.general.ui.CentroControlView;
import com.dreamer8.yosimce.client.general.ui.CentroControlView.CentroControlPresenter;
import com.dreamer8.yosimce.client.material.CentroOperacionPlace;
import com.dreamer8.yosimce.shared.dto.CentroOperacionDTO;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.view.client.ProvidesKey;

public class CentroControlActivity extends SimceActivity implements
		CentroControlPresenter {

	public static class CentroOperacionWrap{
		public static final ProvidesKey<CentroOperacionWrap> KEY_PROVIDER = new ProvidesKey<CentroOperacionWrap>() {

			@Override
			public Object getKey(CentroOperacionWrap item) {
				return (item == null) ? null : item.centroOperacion.getId();
			}
		};
		public CentroOperacionDTO centroOperacion;
		public int maxEnCentro;
		public int maxEnEstablecimiento;
		public int maxEnImprenta;
		public int maxEnMinisterio;
		public Hyperlink hyperlink;
		public CentroOperacionPlace place;
	}
	
	private final CentroControlView view;
	private final CentroControlPlace place;
	private EventBus eventBus;
	private Timer updateTimer;
	
	private ArrayList<CentroOperacionWrap> centros;
	private boolean firstLoad;
	
	private int etapa;
	
	public CentroControlActivity(ClientFactory factory, CentroControlPlace place,HashMap<String, ArrayList<String>> permisos) {
		super(factory, place, permisos);
		this.place = place;
		this.view = getFactory().getCentroControlView();
		this.view.setPresenter(this);
		centros = new ArrayList<CentroControlActivity.CentroOperacionWrap>();
	}

	@Override
	public void init(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(this.view.asWidget());
		this.eventBus = eventBus;
		firstLoad = true;
		etapa = 1;
		updateCentros();
	}
	
	
	@Override
	public void actualizar() {
		updateCentros();
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
	public void addToMonitor(ArrayList<CentroOperacionWrap> centros) {
		view.getMonitorDataProvider().getList().addAll(centros);
		updateTables();
		
	}

	@Override
	public void removeFromMonitor(ArrayList<CentroOperacionWrap> centros) {
		view.getMonitorDataProvider().getList().removeAll(centros);
		updateTables();
	}

	@Override
	public void setMonitorEtapaEstablecimiento() {
		etapa = 1;
		updateTables();
	}

	@Override
	public void setMonitorEtapaCentro() {
		etapa = 2;
		updateTables();
	}

	@Override
	public void setMonitorEtapaImprenta() {
		etapa = 3;
		updateTables();
	}

	@Override
	public void setMonitorEtapaMinisterio() {
		etapa = 4;
		updateTables();
	}
	
	private void updateCentros(){
		getFactory().getGeneralService().getCentrosOperacion(new SimceCallback<ArrayList<CentroOperacionDTO>>(eventBus, firstLoad) {

			@Override
			public void success(ArrayList<CentroOperacionDTO> result) {
				boolean finded;
				for(CentroOperacionDTO centro:result){
					finded = false;
					for(CentroOperacionWrap cow:centros){
						if(cow.centroOperacion.getId() == centro.getId()){
							cow.centroOperacion = centro;
							cow.maxEnCentro = (cow.maxEnCentro<centro.getEnCentro())?centro.getEnCentro():cow.maxEnCentro;
							cow.maxEnEstablecimiento = (cow.maxEnEstablecimiento<centro.getEnEstablecimiento())?centro.getEnEstablecimiento():cow.maxEnEstablecimiento;
							cow.maxEnImprenta = (cow.maxEnImprenta<centro.getEnImprenta())?centro.getEnImprenta():cow.maxEnImprenta;
							cow.maxEnMinisterio = (cow.maxEnMinisterio<centro.getEnMinisterio())?centro.getEnMinisterio():cow.maxEnMinisterio;
							finded = true;
							break;
						}
					}
					if(!finded){
						CentroOperacionWrap cow = new CentroOperacionWrap();
						cow.centroOperacion = centro;
						cow.place = new CentroOperacionPlace(place.getAplicacionId(), place.getNivelId(), place.getTipoId());
						cow.place.setCentroId(centro.getId());
						cow.maxEnCentro = centro.getEnCentro();
						cow.maxEnEstablecimiento = centro.getEnEstablecimiento();
						cow.maxEnImprenta = centro.getEnImprenta();
						cow.maxEnMinisterio = centro.getEnMinisterio();
						cow.hyperlink = new Hyperlink("Ir a tracking", getFactory().getPlaceHistoryMapper().getToken(cow.place));
						centros.add(cow);
					}
				}
				firstLoad = false;
				updateTables();
			}
		});
		
	}
	
	private void updateTables(){
		view.getCompleteDataProvider().getList().clear();
		view.getIncompleteDataProvider().getList().clear();
		int total = 0;
		int aux = 0;
		for(CentroOperacionWrap cow:centros){
			total = cow.centroOperacion.getEnCentro()+cow.centroOperacion.getEnEstablecimiento()+cow.centroOperacion.getEnImprenta()+cow.centroOperacion.getEnMinisterio();
			if(etapa == 1){
				aux = cow.maxEnEstablecimiento;
			}else if(etapa == 2){
				aux = cow.maxEnCentro;
			}else if(etapa == 3){
				aux = cow.maxEnImprenta;
			}else{
				aux = cow.maxEnMinisterio;
			}
			
			if(view.getMonitorDataProvider().getList().contains(cow)){
				view.getMonitorDataProvider().getList().set(view.getMonitorDataProvider().getList().indexOf(cow), cow);
			}
			if(view.getAllDataProvider().getList().contains(cow)){
				view.getAllDataProvider().getList().set(view.getAllDataProvider().getList().indexOf(cow), cow);
			}
			if(aux<total){
				view.getIncompleteDataProvider().getList().add(cow);
			}else{
				view.getCompleteDataProvider().getList().add(cow);
			}
			
		}
	}

	

}
