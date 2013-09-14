package com.dreamer8.yosimce.client.material;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.MensajeEvent;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.SimceCallback;
import com.dreamer8.yosimce.client.SimcePlace;
import com.dreamer8.yosimce.client.Utils;
import com.dreamer8.yosimce.client.material.ui.CentroOperacionView;
import com.dreamer8.yosimce.client.material.ui.CentroOperacionView.CentroOperacionPresenter;
import com.dreamer8.yosimce.shared.dto.DetallesMaterialDTO;
import com.dreamer8.yosimce.shared.dto.DocumentoDTO;
import com.dreamer8.yosimce.shared.dto.EmplazamientoDTO;
import com.dreamer8.yosimce.shared.dto.EtapaDTO;
import com.dreamer8.yosimce.shared.dto.HistorialMaterialItemDTO;
import com.dreamer8.yosimce.shared.dto.LoteDTO;
import com.dreamer8.yosimce.shared.dto.MaterialDTO;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;
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
	
	private ArrayList<MaterialWrap> outdated;
	
	private ArrayList<EmplazamientoDTO> cosAsociados;
	private EmplazamientoDTO co;
	private ArrayList<LoteDTO> lotes;
	private LoteDTO selectedLote;
	private ArrayList<EtapaDTO> etapas;
	private int clientLotes;
	private int intentos;
	private String ingresoFile;
	private String despachoFile;
	
	public CentroOperacionActivity(ClientFactory factory, CentroOperacionPlace place,
			HashMap<String, ArrayList<String>> permisos) {
		super(factory, place, permisos,true);
		this.place = place;
		this.view = getFactory().getCentroOperacionView();
		this.view.setPresenter(this);
		clientLotes = -2;
		historialDataProvider = new ListDataProvider<HistorialMaterialItemDTO>(HistorialMaterialItemDTO.KEY_PROVIDER);
		historialDataProvider.addDataDisplay(view.getHistorialDataDisplay());
		materialDataProvider = new ListDataProvider<MaterialWrap>(MaterialWrap.KEY_PROVIDER);
		materialDataProvider.addDataDisplay(view.getMaterialDataDisplay());
		ingresoDataProvider = new ListDataProvider<MaterialWrap>(new ArrayList<MaterialWrap>(), MaterialWrap.KEY_PROVIDER);
		ingresoDataProvider.addDataDisplay(view.getIngresoDataDisplay());
		predespachoDataProvider = new ListDataProvider<MaterialWrap>(new ArrayList<MaterialWrap>(),MaterialWrap.KEY_PROVIDER);
		predespachoDataProvider.addDataDisplay(view.getPredespachoDataDisplay());
		despachoDataProvider = new ListDataProvider<MaterialWrap>(new ArrayList<MaterialWrap>(),MaterialWrap.KEY_PROVIDER);
		despachoDataProvider.addDataDisplay(view.getDespachoDataDisplay());
		outdated = new ArrayList<MaterialWrap>();
		intentos = 0;
		view.setTotalMaterialIngresando(0);
		view.setTotalMaterialEnLote(0);
		view.setTotalMaterialDespachando(0);
	}

	@Override
	public void init(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view.asWidget());
		this.eventBus = eventBus;
		
		view.setMaterialVisivility(Utils.hasPermisos(getPermisos(), "MaterialService","getMateriales"));
		view.setIngresoVisivility(Utils.hasPermisos(getPermisos(), "MaterialService","ingresarMateriales"));
		view.setPredespachoVisivility(Utils.hasPermisos(getPermisos(), "MaterialService","crearOEditarLote"));
		view.setDespachoVisivility(Utils.hasPermisos(getPermisos(), "MaterialService","despacharMateriales"));
		
		if(Utils.hasPermisos(eventBus, getPermisos(), "MaterialService","getCentrosOperacionAsociados")){
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
		}else{
			goTo(new SimcePlace());
		}
	}

	@Override
	public void onMaterialTabSelected() {
		
	}
	
	@Override
	public void onIngresoTabSelected() {
		view.setFocusOnIngresoCodigoBox(true);
	}
	
	@Override
	public void onPredespachoTabSelected() {
		view.setFocusOnPredespachoCodigoBox(true);
	}
	
	@Override
	public void onDespachoTabSelected() {
		view.setFocusOnDespachoCodigoBox(true);
	}
	
	@Override
	public void onMaterialSelected(final MaterialWrap material) {
		if(material.getHistorial()!=null){
			historialDataProvider.setList(material.getHistorial());
		}
		
		//**********************SOLO TESTING**************************//
		/*
		ArrayList<HistorialMaterialItemDTO> historial = new ArrayList<HistorialMaterialItemDTO>();
		UserDTO u = new UserDTO();
		u.setNombres("Juan Diego");
		u.setApellidoPaterno("Jara");
		u.setApellidoMaterno("Lillo");
		u.setId(1);
		u.setRut("18574996-1");
		HistorialMaterialItemDTO h = new HistorialMaterialItemDTO();
		h.setAutorizante(u);
		h.setDesde("Ministerio");
		h.setHacia("Centro");
		h.setFecha(new Date());
		historial.add(h);
		
		h = new HistorialMaterialItemDTO();
		h.setAutorizante(u);
		h.setDesde("Centro");
		h.setHacia("Establecimiento");
		Date d = new Date();
		CalendarUtil.addDaysToDate(d, 10);
		h.setFecha(d);
		historial.add(h);
		
		h = new HistorialMaterialItemDTO();
		h.setAutorizante(u);
		h.setDesde("Establecimiento");
		h.setHacia("Centro");
		d = new Date();
		CalendarUtil.addDaysToDate(d, 10);
		d.setHours(d.getHours()+4);
		h.setFecha(d);
		historial.add(h);
		
		material.setHistorial(historial);
		historialDataProvider.setList(material.getHistorial());
		*/
		
		//**********************SOLO TESTING**************************//
		
		if(Utils.hasPermisos(eventBus, getPermisos(), "MaterialService","getDetallesMaterial")){
			getFactory().getMaterialService().getDetallesMaterial(material.getMaterial().getId(), new SimceCallback<DetallesMaterialDTO>(eventBus,false) {
	
				@Override
				public void success(DetallesMaterialDTO result) {
					if(result.getHistorial()!=null){
						material.setHistorial(result.getHistorial());
						historialDataProvider.setList(material.getHistorial());
					}
				}
			});
		}
	}

	@Override
	public void onMaterialAddedToIngresoStack(String id) {
		
		view.setFocusOnIngresoCodigoBox(true);
		
		for(MaterialWrap m:materialDataProvider.getList()){
			if(m.getMaterial().getCodigo().equals(id)){
				if(!ingresoDataProvider.getList().contains(m)){
					ingresoDataProvider.getList().add(m);
					view.setTotalMaterialIngresando(ingresoDataProvider.getList().size());
				}else{
					eventBus.fireEvent(new MensajeEvent("El código ingresado ya esta en la lista",MensajeEvent.MSG_WARNING,true));
				}
				return;
			}
		}
		
		MaterialDTO mat = new MaterialDTO();
		mat.setCodigo(id);
		mat.setCurso("-");
		mat.setEstablecimiento("-------");
		mat.setEtapa("-------");
		mat.setNivel("-------");
		mat.setRbd("-------");
		MaterialWrap w = new MaterialWrap();
		w.setMaterial(mat);
		w.setMaterialUpToDate(false);
		ingresoDataProvider.getList().add(w);
		view.setTotalMaterialIngresando(ingresoDataProvider.getList().size());
		outdated.add(w);
		
		updateMateriales(true);
		
	}

	@Override
	public void onMaterialAddedToPredespachoStack(String id) {
		view.setFocusOnPredespachoCodigoBox(true);
		if(selectedLote== null){
			eventBus.fireEvent(new MensajeEvent("Seleccione un lote al cual ingresar el material y vuelva a intentarlo",MensajeEvent.MSG_WARNING,true));
			return;
		}
		for(MaterialWrap m:materialDataProvider.getList()){
			if(m.getMaterial().getCodigo().equals(id)){
				m.getMaterial().setLote(selectedLote);
				if(!predespachoDataProvider.getList().contains(m)){
					predespachoDataProvider.getList().add(m);
					view.setTotalMaterialEnLote(predespachoDataProvider.getList().size());
				}else{
					eventBus.fireEvent(new MensajeEvent("El código ingresado ya esta en la lista",MensajeEvent.MSG_WARNING,true));
				}
				return;
			}
		}
		eventBus.fireEvent(new MensajeEvent("El código ingresado no pertenece a un material asociado a su centro",MensajeEvent.MSG_WARNING,true));
	}

	@Override
	public void onMaterialAddedToDespachoStack(String id) {
		view.setFocusOnDespachoCodigoBox(true);
		MaterialWrap mat = null;
		for(MaterialWrap m:materialDataProvider.getList()){
			if(m.getMaterial().getCodigo().equals(id)){
				mat = m;
				if(!despachoDataProvider.getList().contains(m)){
					despachoDataProvider.getList().add(m);
				}
				break;
			}
		}
		
		if(view.getAddByLote() && mat!=null && mat.getMaterial().getLote()!=null){
			for(MaterialWrap m:materialDataProvider.getList()){
				if(m.getMaterial().getLote().getId() == mat.getMaterial().getLote().getId()){
					if(!despachoDataProvider.getList().contains(m)){
						despachoDataProvider.getList().add(m);
					}
				}
			}
		}
		view.setTotalMaterialDespachando(despachoDataProvider.getList().size());
		
	}

	@Override
	public void onIngresoDocUploaded(DocumentoDTO doc) {
		ingresoFile = doc.getName();
	}

	@Override
	public void onDespachoDocUploaded(DocumentoDTO doc) {
		despachoFile = doc.getName();
	}

	@Override
	public void onRealizarIngresoStackActualClick() {
		ArrayList<String> codigos = new ArrayList<String>();
		
		for(MaterialWrap mw:ingresoDataProvider.getList()){
			codigos.add(mw.getMaterial().getCodigo());
		}
		String folio = view.getIngresoFolio();
		
		getFactory().getMaterialService().ingresarMateriales(place.getCentroId(), codigos, folio, ingresoFile, new SimceCallback<Boolean>(eventBus,true) {

			@Override
			public void success(Boolean result) {
				ingresoDataProvider.getList().clear();
				ingresoFile = null;
				view.setFocusOnIngresoCodigoBox(true);
				
			}
		});
	}

	@Override
	public void onRealizarDespachoStackActualClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCrearLote(String nuevoLote) {
		LoteDTO l = new LoteDTO();
		l.setNombre(nuevoLote);
		l.setId(clientLotes);
		clientLotes--;
		lotes.add(l);
		view.setLotes(lotes);
		onLoteSelected(-1);
	}

	@Override
	public void onDeleteLote(int loteId) {
		for(LoteDTO l:lotes){
			if(l.getId() == loteId){
				lotes.remove(l);
			}
		}
		view.setLotes(lotes);
		onLoteSelected(-1);
	}

	@Override
	public void onLoteSelected(int loteId) {
		if(loteId == -1){
			predespachoDataProvider.getList().clear();
			view.setTotalMaterialEnLote(0);
			return;
		}
		for(LoteDTO l:lotes){
			if(l.getId() == loteId){
				selectedLote = l;
			}
		}
		if(selectedLote == null){
			return;
		}
		
		ArrayList<MaterialWrap> lote = new ArrayList<MaterialWrap>();
		for(MaterialWrap m:materialDataProvider.getList()){
			if(m.getMaterial().getLote().getId()==selectedLote.getId()){
				lote.add(m);
			}
		}
		predespachoDataProvider.setList(lote);
		view.setTotalMaterialEnLote(lote.size());
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
		getFactory().getMaterialService().getUser(rut, new SimceCallback<UserDTO>(eventBus,false) {

			@Override
			public void success(UserDTO result) {
				view.setRetirante(result);
			}
		});
	}
	
	private void initialize(){
		
		view.setCO(co);
		
		//**********************SOLO TESTING**************************//
		/*
		ArrayList<MaterialDTO> result = new ArrayList<MaterialDTO>();
		for(int i = 1;i<50;i++){
			MaterialDTO m = new MaterialDTO();
			m.setCodigo("1000000"+i);
			if(i%5 == 0){
				m.setCurso("B");
			}else{
				m.setCurso("A");
			}
			m.setEstablecimiento("Carmelitas descalsas");
			if(i%6 == 0){
				m.setEtapa("Ministerio");
			}else{
				m.setEtapa("Centro");
			}
			m.setIdCentro(place.getCentroId());
			m.setId(i);
			m.setNivel("4 básico");
			m.setRbd("49576");
			if(i%13 == 0){
				m.setTipo("Contingencia");
			}else{
				m.setTipo("Caja-Curso-Día");
			}
			result.add(m);
		}
		
		for(int i = 301;i<390;i++){
			MaterialDTO m = new MaterialDTO();
			m.setCodigo("1000000"+i);
			if(i%8 == 0){
				m.setCurso("B");
			}else{
				m.setCurso("A");
			}
			m.setEstablecimiento("Colegio Altos de los andes");
			if(i%3 == 0){
				m.setEtapa("Ministerio");
			}else{
				m.setEtapa("Centro");
			}
			m.setIdCentro(place.getCentroId());
			m.setId(i);
			m.setNivel("4 básico");
			m.setRbd("8574");
			if(i%11 == 0){
				m.setTipo("Contingencia");
			}else{
				m.setTipo("Caja-Curso-Día");
			}
			result.add(m);
		}
		materialDataProvider.setList(wrap(result));

		view.setMaterialSortHandler(new ListHandler<MaterialWrap>(materialDataProvider.getList()));
		extractLotes();
		*/
		//**********************SOLO TESTING**************************//
		
		
		if(Utils.hasPermisos(eventBus, getPermisos(), "MaterialService", "getMateriales")){
			getFactory().getMaterialService().getMateriales(co.getId(), new SimceCallback<ArrayList<MaterialDTO>>(eventBus,true) {
	
				@Override
				public void success(ArrayList<MaterialDTO> result) {
					materialDataProvider.setList(wrap(result));
					view.setMaterialSortHandler(new ListHandler<MaterialWrap>(materialDataProvider.getList()));
					extractLotes();
				}
			});
		}
		
		if(Utils.hasPermisos(eventBus, getPermisos(), "MaterialService", "getEtapas")){
			getFactory().getMaterialService().getEtapas(new SimceCallback<ArrayList<EtapaDTO>>(eventBus,true) {
	
				@Override
				public void success(ArrayList<EtapaDTO> result) {
					etapas = result;
					view.setEtapas(etapas);
				}
			});
		}
	}
	
	private void showCoSelectorPopup(){
		final CentroOperacionSelector cos = new CentroOperacionSelector(getFactory(), cosAsociados);
		cos.setSelectedCommand(new Command() {
			
			@Override
			public void execute() {
				cos.hide();
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
		cos.show();
	}
	
	private ArrayList<MaterialWrap> wrap(ArrayList<MaterialDTO> list){
		ArrayList<MaterialWrap> wrap = new ArrayList<MaterialWrap>();
		LoteDTO l = new LoteDTO();
		l.setNombre("---------");
		l.setId(-1);
		for(MaterialDTO m:list){
			MaterialWrap w = new MaterialWrap();
			w.setMaterial(m);
			w.setMaterialUpToDate(true);
			if(m.getLote()==null){
				w.getMaterial().setLote(l);
			}
			wrap.add(w);
		}
		return wrap;
	}
	
	private void extractLotes(){
		if(lotes == null){
			lotes = new ArrayList<LoteDTO>();
		}
		lotes.clear();
		for(MaterialWrap m:materialDataProvider.getList()){
			if(m.getMaterial().getLote()!=null && !lotes.contains(m.getMaterial().getLote())){
				lotes.add(m.getMaterial().getLote());
			}
		}
		view.setLotes(lotes);
	}
	
	private void updateMateriales(boolean blockThrows){
		ArrayList<String> mList = new ArrayList<String>();
		
		for(MaterialWrap mwAux:outdated){
			mList.add(mwAux.getMaterial().getCodigo());
		}
		if(Utils.hasPermisos(eventBus, getPermisos(), "MaterialService", "getMaterialesByCodigos")){
			getFactory().getMaterialService().getMaterialesByCodigos(mList, new SimceCallback<ArrayList<MaterialDTO>>(eventBus,false,20000,!blockThrows) {
	
				@Override
				public void success(ArrayList<MaterialDTO> result) {
					intentos = 0;
					for(MaterialDTO m:result){
						for(MaterialWrap mw:outdated){
							if(mw.getMaterial().getCodigo().equals(m.getCodigo())){
								outdated.remove(mw);
								if(materialDataProvider.getList().contains(mw)){
									int index = materialDataProvider.getList().indexOf(mw);
									materialDataProvider.getList().get(index).setMaterial(m);
									materialDataProvider.getList().set(index, mw);
								}
								if(ingresoDataProvider.getList().contains(mw)){
									int index = ingresoDataProvider.getList().indexOf(mw);
									ingresoDataProvider.getList().get(index).setMaterial(m);
									ingresoDataProvider.getList().set(index, mw);
								}
								if(predespachoDataProvider.getList().contains(mw)){
									int index = predespachoDataProvider.getList().indexOf(mw);
									predespachoDataProvider.getList().get(index).setMaterial(m);
									predespachoDataProvider.getList().set(index, mw);
								}
								if(despachoDataProvider.getList().contains(mw)){
									int index = despachoDataProvider.getList().indexOf(mw);
									despachoDataProvider.getList().get(index).setMaterial(m);
									despachoDataProvider.getList().set(index, mw);
								}
								break;
							}
						}
					}
				}
				
				@Override
				public void failure(Throwable caught) {
					
					if(outdated.isEmpty()){
						return;
					}
					
					intentos++;
					if(intentos<5){
						Timer t = new Timer(){
		
							@Override
							public void run() {
								updateMateriales(true);
							}
						};
						t.schedule((int)(Math.pow(5,intentos)*1000));
					}else{
						updateMateriales(false);
						Timer t = new Timer(){
							
							@Override
							public void run() {
								updateMateriales(false);
							}
						};
						t.schedule((int)(30000));
					}
				}
			});
		}
	}

	

}
