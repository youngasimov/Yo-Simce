package com.dreamer8.yosimce.client.material;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.MensajeEvent;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.SimceCallback;
import com.dreamer8.yosimce.client.SimcePlace;
import com.dreamer8.yosimce.client.material.ui.CentroOperacionView;
import com.dreamer8.yosimce.client.material.ui.CentroOperacionView.CentroOperacionPresenter;
import com.dreamer8.yosimce.shared.dto.DocumentoDTO;
import com.dreamer8.yosimce.shared.dto.EmplazamientoDTO;
import com.dreamer8.yosimce.shared.dto.EtapaDTO;
import com.dreamer8.yosimce.shared.dto.HistorialMaterialItemDTO;
import com.dreamer8.yosimce.shared.dto.LoteDTO;
import com.dreamer8.yosimce.shared.dto.MaterialDTO;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.view.client.ListDataProvider;

public class CentroOperacionActivity extends SimceActivity implements
		CentroOperacionPresenter {

	private final CentroOperacionView view;
	private final CentroOperacionPlace place;
	private EventBus eventBus;
	
	private ListDataProvider<HistorialMaterialItemDTO> historialDataProvider;
	private ListDataProvider<MaterialWrap> materialDataProvider;
	private ListDataProvider<MaterialWrap> ingresoDataProvider;
	private ListDataProvider<MaterialWrap> predespachoDataProvider;
	private ListDataProvider<MaterialWrap> despachoDataProvider;
	
	private ArrayList<MaterialWrap> wrap;
	private ArrayList<EmplazamientoDTO> cosAsociados;
	private EmplazamientoDTO co;
	private ArrayList<LoteDTO> lotes;
	private ArrayList<EtapaDTO> etapas;
	
	public CentroOperacionActivity(ClientFactory factory, CentroOperacionPlace place,
			HashMap<String, ArrayList<String>> permisos) {
		super(factory, place, permisos,true);
		this.place = place;
		this.view = getFactory().getCentroOperacionView();
		this.view.setPresenter(this);
		historialDataProvider = new ListDataProvider<HistorialMaterialItemDTO>(HistorialMaterialItemDTO.KEY_PROVIDER);
		historialDataProvider.addDataDisplay(view.getHistorialDataDisplay());
		materialDataProvider = new ListDataProvider<MaterialWrap>(MaterialWrap.KEY_PROVIDER);
		materialDataProvider.addDataDisplay(view.getMaterialDataDisplay());
		ingresoDataProvider = new ListDataProvider<MaterialWrap>(MaterialWrap.KEY_PROVIDER);
		ingresoDataProvider.addDataDisplay(view.getIngresoDataDisplay());
		predespachoDataProvider = new ListDataProvider<MaterialWrap>(MaterialWrap.KEY_PROVIDER);
		predespachoDataProvider.addDataDisplay(view.getPredespachoDataDisplay());
		despachoDataProvider = new ListDataProvider<MaterialWrap>(MaterialWrap.KEY_PROVIDER);
		despachoDataProvider.addDataDisplay(view.getDespachoDataDisplay());
	}

	@Override
	public void init(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view.asWidget());
		this.eventBus = eventBus;
		
		getFactory().getMaterialService().getCentrosOperacionAsociados(new SimceCallback<ArrayList<EmplazamientoDTO>>(eventBus,true) {

			@Override
			public void success(ArrayList<EmplazamientoDTO> result) {
				cosAsociados = result;
				
				if(cosAsociados.isEmpty()){
					CentroOperacionActivity.this.eventBus.fireEvent(new MensajeEvent("No tiene ningun centro de operación asociado",MensajeEvent.MSG_WARNING,true));
					goTo(new SimcePlace());
				}else if(cosAsociados.size() == 1){
					if(place.getCentroId() == cosAsociados.get(0).getId()){
						co = cosAsociados.get(0);
						initialize();
					}else{
						CentroOperacionPlace p = new CentroOperacionPlace();
						p.setCentroId(cosAsociados.get(0).getId());
						goTo(p);
					}
				}else{
					EmplazamientoDTO selected = null;
					for(EmplazamientoDTO empl:cosAsociados){
						if(empl.getId() == place.getCentroId()){
							selected = empl;
							break;
						}
					}
					if(selected != null){
						co =selected;
						initialize();
					}else{
						showCoSelectorPopup();
					}
				}
			}
			
			@Override
			public void failure(Throwable caught) {
				goTo(new SimcePlace());
			}
			
		});
	}

	@Override
	public void onMaterialSelected(final MaterialWrap material) {
		if(material.getHistorial()!=null){
			historialDataProvider.setList(material.getHistorial());
		}
		getFactory().getMaterialService().getHistorialMaterial(material.getMaterial().getId(), new SimceCallback<ArrayList<HistorialMaterialItemDTO>>(eventBus,false) {

			@Override
			public void success(ArrayList<HistorialMaterialItemDTO> result) {
				material.setHistorial(result);
				historialDataProvider.setList(material.getHistorial());
			}
			
			@Override
			public void failure(Throwable caught) {
				material.setHistorialUpToDate(false);
			}
		});
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
	public void onRemoveIngresoItem(MaterialWrap material) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRemovePredespachoItem(MaterialWrap material) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRemoveDespachoItem(MaterialWrap material) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onChangeSelectedStageCo() {
		
	}
	
	@Override
	public void onRutRetiranteChange(String rut) {
		// TODO Auto-generated method stub
		
	}
	
	private void initialize(){
		
		view.setCO(co);
		
		getFactory().getMaterialService().getMateriales(co.getId(), new SimceCallback<ArrayList<MaterialDTO>>(eventBus,true) {

			@Override
			public void success(ArrayList<MaterialDTO> result) {
				wrap(result);
				materialDataProvider.setList(wrap);
				view.setMaterialSortHandler(new ListHandler<MaterialWrap>(materialDataProvider.getList()));
				extractLotes();
			}
		});
		
		getFactory().getMaterialService().getEtapas(new SimceCallback<ArrayList<EtapaDTO>>(eventBus,true) {

			@Override
			public void success(ArrayList<EtapaDTO> result) {
				etapas = result;
				view.setEtapas(result);
			}
		});
		
	}
	
	private void showCoSelectorPopup(){
		final CentroOperacionSelector cos = new CentroOperacionSelector(getFactory(), cosAsociados);
		cos.setSelectedCommand(new Command() {
			
			@Override
			public void execute() {
				CentroOperacionPlace p = new CentroOperacionPlace();
				p.setCentroId(cos.getSelectedEmplazamiento().getId());
				goTo(p);
			}
		});
		cos.setAutoHideCommand(new Command() {
			
			@Override
			public void execute() {
				SimcePlace p = new SimcePlace();
				goTo(p);
			}
		});
	}
	
	private void wrap(ArrayList<MaterialDTO> list){
		wrap = new ArrayList<MaterialWrap>();
		for(MaterialDTO m:list){
			MaterialWrap w = new MaterialWrap();
			w.setMaterial(m);
			w.setMaterialUpToDate(true);
			w.setHistorialUpToDate(false);
			w.setDocumentosUpToDate(true);
			wrap.add(w);
		}
	}
	
	private void extractLotes(){
		if(lotes == null){
			lotes = new ArrayList<LoteDTO>();
		}
		lotes.clear();
		for(MaterialWrap m:wrap){
			if(m.getMaterial().getLote()!=null && !lotes.contains(m.getMaterial().getLote())){
				lotes.add(m.getMaterial().getLote());
			}
		}
		view.setLotes(lotes);
	}

	

}
