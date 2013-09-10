package com.dreamer8.yosimce.client.material;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.SimcePlace;
import com.dreamer8.yosimce.client.material.ui.CentroOperacionView;
import com.dreamer8.yosimce.client.material.ui.CentroOperacionView.CentroOperacionPresenter;
import com.dreamer8.yosimce.shared.dto.DocumentoDTO;
import com.dreamer8.yosimce.shared.dto.HistorialMaterialItemDTO;
import com.dreamer8.yosimce.shared.dto.MaterialDTO;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.view.client.ListDataProvider;

public class CentroOperacionActivity extends SimceActivity implements
		CentroOperacionPresenter {

	private final CentroOperacionView view;
	private final CentroOperacionPlace place;
	
	private ListDataProvider<HistorialMaterialItemDTO> historialDataProvider;
	private ListDataProvider<MaterialDTO> materialDataProvider;
	private ListDataProvider<MaterialDTO> ingresoDataProvider;
	private ListDataProvider<MaterialDTO> predespachoDataProvider;
	private ListDataProvider<MaterialDTO> despachoDataProvider;
	
	public CentroOperacionActivity(ClientFactory factory, CentroOperacionPlace place,
			HashMap<String, ArrayList<String>> permisos) {
		super(factory, place, permisos,true);
		this.place = place;
		this.view = getFactory().getCentroOperacionView();
		this.view.setPresenter(this);
		historialDataProvider = new ListDataProvider<HistorialMaterialItemDTO>(HistorialMaterialItemDTO.KEY_PROVIDER);
		historialDataProvider.addDataDisplay(view.getHistorialDataDisplay());
		materialDataProvider = new ListDataProvider<MaterialDTO>(MaterialDTO.KEY_PROVIDER);
		materialDataProvider.addDataDisplay(view.getMaterialDataDisplay());
		ingresoDataProvider = new ListDataProvider<MaterialDTO>(MaterialDTO.KEY_PROVIDER);
		ingresoDataProvider.addDataDisplay(view.getIngresoDataDisplay());
		predespachoDataProvider = new ListDataProvider<MaterialDTO>(MaterialDTO.KEY_PROVIDER);
		predespachoDataProvider.addDataDisplay(view.getPredespachoDataDisplay());
		despachoDataProvider = new ListDataProvider<MaterialDTO>(MaterialDTO.KEY_PROVIDER);
		despachoDataProvider.addDataDisplay(view.getDespachoDataDisplay());
	}

	@Override
	public void init(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view.asWidget());
		
		
		
	}

	@Override
	public void onMaterialSelected(MaterialDTO material) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMaterialAddedToIngresoStack(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMaterialAddedToPredespachoStack(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMaterialAddedToDespachoStack(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onIngresoDocUploaded(DocumentoDTO doc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDespachoDocUploaded(DocumentoDTO doc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRealizarIngresoStackActualClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRealizarDespachoStackActualClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCrearLote(String nuevoLote) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeleteLote(int loteId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoteSelected(int loteId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEtapaChange(int etapaId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRemoveIngresoItem(MaterialDTO material) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRemovePredespachoItem(MaterialDTO material) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRemoveDespachoItem(MaterialDTO material) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onChangeSelectedStageCo() {
		
	}
	
	@Override
	public void onRutRetiranteChange(String rut) {
		// TODO Auto-generated method stub
		
	}

	

}
