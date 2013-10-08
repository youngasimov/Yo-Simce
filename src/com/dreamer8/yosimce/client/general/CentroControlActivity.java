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
		CentroOperacionDTO centroOperacion;
		int maxEnCentro;
		int maxEnEstablecimiento;
		int maxEnImprenta;
		int maxEnMinisterio;
		CentroOperacionPlace place;
	}
	
	private final CentroControlView view;
	private final CentroControlPlace place;
	private EventBus eventBus;
	private Timer updateTimer;
	
	private ArrayList<CentroOperacionWrap> centros;
	private boolean firstLoad;
	
	
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
						centros.add(cow);
					}
				}
				firstLoad = false;
				updateTables();
			}
		});
		
	}
	
	private void updateTables(){
		
	}

}
