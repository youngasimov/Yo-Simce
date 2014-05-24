package com.dreamer8.yosimce.client.general;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.SimceCallback;
import com.dreamer8.yosimce.client.general.ui.GraficosView;
import com.dreamer8.yosimce.client.general.ui.GraficosView.GraficosPresenter;
import com.dreamer8.yosimce.shared.dto.EstadoActividadItemDTO;
import com.dreamer8.yosimce.shared.dto.EstadoMaterialItemDTO;
import com.dreamer8.yosimce.shared.dto.SectorDTO;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class GraficosActivity extends SimceActivity implements GraficosPresenter {

	
	private final GraficosView view;
	private EventBus eventBus;
	private ArrayList<SectorDTO> regiones;
	private ArrayList<EstadoMaterialItemDTO> estadoMateriales;
	private ArrayList<EstadoActividadItemDTO> estadoActividades;
	private int selectedRegion;
	private int selectedComuna;
	
	public GraficosActivity(ClientFactory factory, GraficosPlace place,HashMap<String, ArrayList<String>> permisos) {
		super(factory,place,permisos);
		this.view = getFactory().getGraficosView();
	}
	
	@Override
	public void init(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view.asWidget());
		view.setPresenter(this);
		this.eventBus = eventBus;
		selectedRegion = -1;
		selectedComuna = -1;
		
		getFactory().getGeneralService().getRegiones(new SimceCallback<ArrayList<SectorDTO>>(eventBus,true) {

			@Override
			public void success(ArrayList<SectorDTO> result) {
				regiones = result;
				view.setRegiones(result);
				view.setComunas(null);
			}
		});
		
		getFactory().getGeneralService().getEstadoMateriales(new SimceCallback<ArrayList<EstadoMaterialItemDTO>>(eventBus,true) {

			@Override
			public void success(ArrayList<EstadoMaterialItemDTO> result) {
				estadoMateriales = result;
				actualizarEstadoMaterial();
			}
		});
		
		onGranularidadActividadChange(false);
		
	}

	@Override
	public void actualizarEstadoMaterial() {
		if(estadoMateriales == null){
			return;
		}
		
		EstadoMaterialItemDTO item = new EstadoMaterialItemDTO();
		for(EstadoMaterialItemDTO x:estadoMateriales){
			if((selectedRegion == -1 || x.getIdRegion() == selectedRegion) && (selectedComuna == -1 || x.getIdComuna() == selectedComuna )){
				item.setTotalAgencia(item.getTotalAgencia() + x.getTotalAgencia());
				item.setTotalBodega(item.getTotalBodega()+x.getTotalBodega());
				item.setTotalCaptura(item.getTotalCaptura()+x.getTotalCaptura());
				item.setTotalCentro(item.getTotalCentro()+x.getTotalCentro());
				item.setTotalEstablecimiento(item.getTotalEstablecimiento()+x.getTotalEstablecimiento());
				item.setTotalImprenta(item.getTotalImprenta()+x.getTotalImprenta());
			}
		}
		item.setIdRegion(selectedRegion);
		item.setIdComuna(selectedComuna);
		view.setMaterialData(item);
	}

	@Override
	public void actualizarEstadoActividad() {
		if(estadoActividades == null){
			return;
		}
		EstadoActividadItemDTO item = new EstadoActividadItemDTO();
		for(EstadoActividadItemDTO x:estadoActividades){
			if((selectedRegion == -1 || x.getIdRegion() == selectedRegion) && (selectedComuna == -1 || x.getIdComuna() == selectedComuna )){
				item.setTotalAnulada(item.getTotalAnulada() + x.getTotalAnulada());
				item.setTotalConfirmado(item.getTotalConfirmado()+x.getTotalConfirmado());
				item.setTotalConfirmadoConCambios(item.getTotalConfirmadoConCambios()+x.getTotalConfirmadoConCambios());
				item.setTotalPorConfirmar(item.getTotalPorConfirmar()+x.getTotalPorConfirmar());
				item.setTotalRealizada(item.getTotalRealizada()+x.getTotalRealizada());
				item.setTotalSinInformacion(item.getTotalSinInformacion()+x.getTotalSinInformacion());
			}
		}
		item.setIdRegion(selectedRegion);
		item.setIdComuna(selectedComuna);
		view.setActividadData(item);
	}
	
	@Override
	public void onGranularidadActividadChange(boolean toCurso) {
		getFactory().getGeneralService().getEstadoActividades(!toCurso,new SimceCallback<ArrayList<EstadoActividadItemDTO>>(eventBus,true) {

			@Override
			public void success(ArrayList<EstadoActividadItemDTO> result) {
				estadoActividades = result;
				actualizarEstadoActividad();
			}
		});
	}
	
	

	@Override
	public void onRegionChange(int idRegion) {
		selectedRegion = idRegion;
		selectedComuna = -1;
		if(idRegion==-1){
			view.setComunas(null);
			actualizarEstadoMaterial();
			actualizarEstadoActividad();
			return;
		}
		SectorDTO region = null;
		for(SectorDTO s:regiones){
			if(s.getIdSector() == idRegion){
				region = s;
				break;
			}
		}
		
		getFactory().getGeneralService().getComunas(region,new SimceCallback<ArrayList<SectorDTO>>(eventBus,false) {

			@Override
			public void success(ArrayList<SectorDTO> result) {
				view.setComunas(result);
			}
		});
		actualizarEstadoMaterial();
		actualizarEstadoActividad();
	}

	@Override
	public void onComunaChange(int idComuna) {
		selectedComuna = idComuna;
		actualizarEstadoMaterial();
		actualizarEstadoActividad();
	}

}
