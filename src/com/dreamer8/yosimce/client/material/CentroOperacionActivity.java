package com.dreamer8.yosimce.client.material;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.MensajeEvent;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.SimceCallback;
import com.dreamer8.yosimce.client.SimcePlace;
import com.dreamer8.yosimce.client.SoundNotificationEvent;
import com.dreamer8.yosimce.client.Utils;
import com.dreamer8.yosimce.client.YoSimce;
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
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.MenuBar;
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

	private ArrayList<MaterialWrap> materiales;
	private ArrayList<MaterialWrap> materialesVisibles;
	private ArrayList<MaterialWrap> materialesNuevos;
	private ArrayList<EmplazamientoDTO> cosAsociados;
	private ArrayList<EmplazamientoDTO> allCos;
	private EmplazamientoDTO co;
	private ArrayList<LoteDTO> lotes;
	private LoteDTO selectedLote;
	private ArrayList<EtapaDTO> etapas;
	private String ingresoFile;
	private String despachoFile;
	private EtapaDTO selectedEtapa;
	private EmplazamientoDTO selectedCo;
	private String selectedRetiranteRut;
	private LocalMaterialService localService;
	private String userKey;
	
	private Comparator<MaterialWrap> comparator = new Comparator<MaterialWrap>() {

		@Override
		public int compare(MaterialWrap o1, MaterialWrap o2) {
			if(o1.getMaterial().getNivel()!=null && !o1.getMaterial().getNivel().equals(o2.getMaterial().getNivel())){
				return o1.getMaterial().getNivel().compareToIgnoreCase(o2.getMaterial().getNivel());
			}else if(o1.getMaterial().getTipo()!=null && !o1.getMaterial().getTipo().equals(o2.getMaterial().getTipo())){
				return o1.getMaterial().getTipo().compareToIgnoreCase(o2.getMaterial().getTipo());
			}else if(o1.getMaterial().getEstablecimiento()!=null && !o1.getMaterial().getEstablecimiento().equals(o2.getMaterial().getEstablecimiento())){
				return o1.getMaterial().getEstablecimiento().compareToIgnoreCase(o2.getMaterial().getEstablecimiento());
			}else{
				return o1.getMaterial().getCodigo().compareToIgnoreCase(o2.getMaterial().getCodigo());
			}
		}
	};
	

	public CentroOperacionActivity(ClientFactory factory,
			CentroOperacionPlace place,
			HashMap<String, ArrayList<String>> permisos) {
		super(factory, place, permisos);
		this.place = place;
		this.view = getFactory().getCentroOperacionView();
		this.view.setPresenter(this);
		historialDataProvider = new ListDataProvider<HistorialMaterialItemDTO>(
				HistorialMaterialItemDTO.KEY_PROVIDER);
		historialDataProvider.addDataDisplay(view.getHistorialDataDisplay());
		materialDataProvider = new ListDataProvider<MaterialWrap>(
				MaterialWrap.KEY_PROVIDER);
		materialDataProvider.addDataDisplay(view.getMaterialDataDisplay());
		ingresoDataProvider = new ListDataProvider<MaterialWrap>(
				new ArrayList<MaterialWrap>(), MaterialWrap.KEY_PROVIDER);
		ingresoDataProvider.addDataDisplay(view.getIngresoDataDisplay());
		predespachoDataProvider = new ListDataProvider<MaterialWrap>(
				new ArrayList<MaterialWrap>(), MaterialWrap.KEY_PROVIDER);
		predespachoDataProvider
				.addDataDisplay(view.getPredespachoDataDisplay());
		despachoDataProvider = new ListDataProvider<MaterialWrap>(
				new ArrayList<MaterialWrap>(), MaterialWrap.KEY_PROVIDER);
		despachoDataProvider.addDataDisplay(view.getDespachoDataDisplay());
		materiales = new ArrayList<MaterialWrap>();
		materialesVisibles = new ArrayList<MaterialWrap>();
		materialesNuevos = new ArrayList<MaterialWrap>();
		view.setTotalMaterialIngresando(0);
		view.setTotalMaterialEnLote(0);
		view.setTotalMaterialDespachando(0);
		selectedEtapa = null;
		selectedCo = null;
		
		view.setManualOperacionVisible(place.getAplicacionId()==1);
	}

	@Override
	public void init(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view.asWidget());
		this.eventBus = eventBus;
		localService = new LocalMaterialService(eventBus);
		view.setTotalMaterialIngresando(0);
		view.setTotalMaterialEnLote(0);
		view.setTotalMaterialDespachando(0);
		selectedEtapa = null;
		selectedCo = null;
		
		userKey= Cookies.getCookie(YoSimce.TOKEN_COOKIE);
		if(userKey == null || userKey.length() == 0){
			userKey = Cookies.getCookie(YoSimce.TOKEN_COOKIE_DEMO);
		}
		if(userKey==null){
			userKey = "";
		}else{
			userKey = userKey+Cookies.getCookie("a")+"-"+Cookies.getCookie("n")+"-"+Cookies.getCookie("t");
		}
		
		
		view.setMaterialVisivility(Utils.hasPermisos(getPermisos(),
				"MaterialService", "getMateriales"));
		view.setIngresoVisivility(Utils.hasPermisos(getPermisos(),
				"MaterialService", "ingresarMateriales"));
		view.setPredespachoVisivility(Utils.hasPermisos(getPermisos(),
				"MaterialService", "crearOEditarLote"));
		view.setDespachoVisivility(Utils.hasPermisos(getPermisos(),
				"MaterialService", "despacharMateriales"));

		view.setAddByLote(true);
		
		if (Utils.hasPermisos(eventBus, getPermisos(), "MaterialService","getCentrosOperacionAsociados")) {
			getFactory().getMaterialService().getCentrosOperacionAsociados(new MaterialCallback<ArrayList<EmplazamientoDTO>>(eventBus,true,userKey) {
				@Override
				public void saveOnLocalStorage(String key,ArrayList<EmplazamientoDTO> value) {
					localService.setCentrosOperacionAsociados(key, value);		
				};
				
				@Override
				public ArrayList<EmplazamientoDTO> tryFromLocalStorage(
						String key) {
					return localService.getCentrosOperacionAsociados(key);
				}
				
				@Override
				public void success(ArrayList<EmplazamientoDTO> result) {
					setEmplazamientosAsociados(result);
				}

				@Override
				public void failure(Throwable caught) {
					goTo(new SimcePlace());
				}

			});
		} else {
			goTo(new SimcePlace());
		}

		if (Utils.hasPermisos(getPermisos(), "MaterialService",
				"getCentrosOperacion")) {
			
			allCos = localService.getCentrosOperacion(userKey);
			if(allCos == null || allCos.isEmpty()){
				getFactory().getMaterialService().getCentrosOperacion(
						new MaterialCallback<ArrayList<EmplazamientoDTO>>(eventBus,true,userKey) {
							
							@Override
							public void saveOnLocalStorage(String key, ArrayList<EmplazamientoDTO> result) {
								localService.setCentrosOperacion(key, result);
							};
							
							@Override
							public void success(ArrayList<EmplazamientoDTO> result) {
								allCos = result;
							}
							
							@Override
							public void failure(Throwable caught) {
								allCos = new ArrayList<EmplazamientoDTO>();
							}
	
						});
			}
		}else{
			allCos = new ArrayList<EmplazamientoDTO>();
		}
		
		if (Utils.hasPermisos(getPermisos(), "MaterialService",
				"getEtapas")) {
			etapas = localService.getEtapas(userKey);
			if(etapas!=null){
				view.setEtapas(etapas);
			}
			else{
				getFactory().getMaterialService().getEtapas(
						new MaterialCallback<ArrayList<EtapaDTO>>(eventBus, true,userKey) {
	
							
							@Override
							public void saveOnLocalStorage(String key,
									ArrayList<EtapaDTO> result) {
								localService.setEtapas(key, result);
							}
							
							@Override
							public void success(ArrayList<EtapaDTO> result) {
								etapas = result;
								view.setEtapas(etapas);
							}
							
							@Override
							public void failure(Throwable caught) {
								etapas = new ArrayList<EtapaDTO>();
							}
						});
			}
		}else{
			etapas = new ArrayList<EtapaDTO>();
		}
	}

	@Override
	public void onStop() {
		historialDataProvider.getList().clear();
		materialDataProvider.getList().clear();
		ingresoDataProvider.getList().clear();
		predespachoDataProvider.getList().clear();
		despachoDataProvider.getList().clear();
		view.clearDespachoFolioBox();
		view.clearIngresoFolioBox();
		view.clearNuevoLoteBox();
		view.clearRutRetiranteBox();
		view.setAddByLote(false);
		view.setChangeCoButtonVisivility(false);
		view.setCO(null);
		view.setDespachoDocumento(null);
		view.setIngresoDocumento(null);
		view.setDespachoVisivility(false);
		view.setIngresoVisivility(false);
		view.setMaterialVisivility(false);
		view.setPredespachoVisivility(false);
		view.setLotes(null);
		view.setRetirante(null);
		view.setSelectedCo(null);
		view.setTotalMaterialIngresando(0);
		view.setTotalMaterialEnLote(0);
		view.setTotalMaterialDespachando(0);
		selectedEtapa = null;
		selectedCo = null;
		view.getFiltroMenu().setSubMenu(null);
		super.onStop();
	}

	@Override
	public void onExportarClick() {
		if(Utils.hasPermisos(eventBus, getPermisos(), "MaterialService", "exportar")){
			ArrayList<Integer> mat = new ArrayList<Integer>();
			for(MaterialWrap mw : materialDataProvider.getList() ){
				mat.add(mw.getMaterial().getId());
			}
			getFactory().getMaterialService().exportar(mat, new SimceCallback<DocumentoDTO>(eventBus,true,0) {
	
				@Override
				public void success(DocumentoDTO result) {
					Window.open(result.getUrl(), "_blank", "");
				}
				
			});
		}
	}
	
	@Override
	public void onManualOperacionClick() {
		if(place.getAplicacionId()==1){
			Window.open(Window.Location.getProtocol()+"//"+Window.Location.getHost()+"/manual_operativo_co.pdf", "_blank", "");
		}else{
			eventBus.fireEvent(new MensajeEvent("Esta aplicación no tiene un manual operativo"));
		}
	}
	
	@Override
	public void onMaterialTabSelected() {
		view.setMaterialSortHandler(new ListHandler<MaterialWrap>(
				materialDataProvider.getList()));
	}

	@Override
	public void onIngresoTabSelected() {
		view.setFocusOnIngresoCodigoBox(true);
		
		ArrayList<String> codigos = localService.getLastIngreso(userKey+"-"+place.getCentroId());
		if(codigos!=null && !codigos.isEmpty()){
			ArrayList<MaterialWrap> ms = new ArrayList<MaterialWrap>();
			for(MaterialWrap mw:materiales){
				if(codigos.contains(mw.getMaterial().getCodigo())){
					ms.add(mw);
				}
			}
			ingresoDataProvider.setList(ms);
			view.setTotalMaterialIngresando(ingresoDataProvider
					.getList().size());
			view.setIngresoSortHandler(new ListHandler<MaterialWrap>(
					ingresoDataProvider.getList()));
			eventBus.fireEvent(new MensajeEvent("Materiales cargados desde copia local",MensajeEvent.MSG_WARNING,true));
		}
	}

	@Override
	public void onPredespachoTabSelected() {
		view.setFocusOnPredespachoCodigoBox(true);
	}

	@Override
	public void onDespachoTabSelected() {
		view.setFocusOnDespachoCodigoBox(true);
		ArrayList<String> codigos = localService.getLastDespacho(userKey+"-"+place.getCentroId());
		if(codigos!=null && !codigos.isEmpty()){
			ArrayList<MaterialWrap> ms = new ArrayList<MaterialWrap>();
			for(MaterialWrap mw:materialesVisibles){
				if(codigos.contains(mw.getMaterial().getCodigo())){
					ms.add(mw);
				}
			}
			despachoDataProvider.setList(ms);
			view.setTotalMaterialDespachando(despachoDataProvider.getList().size());
			view.setDespachoSortHandler(new ListHandler<MaterialWrap>(
					despachoDataProvider.getList()));
			eventBus.fireEvent(new MensajeEvent("Materiales cargados desde copia local",MensajeEvent.MSG_WARNING,true));
		}
	}

	@Override
	public void onMaterialSelected(final MaterialWrap material) {
		if (material.getDetalles() != null && material.getDetalles().getHistorial()!=null) {
			historialDataProvider.setList(material.getDetalles().getHistorial());
		}
		
		view.setDetallesMaterial(material.getMaterial(), material.getDetalles());

		if (Utils.hasPermisos(eventBus, getPermisos(), "MaterialService",
				"getDetallesMaterial")) {
			getFactory().getMaterialService().getDetallesMaterial(
					material.getMaterial().getId(),
					new SimceCallback<DetallesMaterialDTO>(eventBus, false) {

						@Override
						public void success(DetallesMaterialDTO result) {
							material.setDetalles(result);
							view.setDetallesMaterial(material.getMaterial(), material.getDetalles());
							if (result.getHistorial() != null) {
								historialDataProvider.setList(material.getDetalles().getHistorial());
							}
						}
					});
		}
	}

	@Override
	public void actualizarMaterial(MaterialWrap material) {
		if(!material.isUpdating()){
			updateMaterial(material);
		}
	}
	
	@Override
	public void onMaterialAddedToIngresoStack(String id) {

		if(id.contains(",")){
			String[] ids = id.split(",");
			for(String x:ids){
				onMaterialAddedToIngresoStack(x);
			}
			return;
		}
		
		id = id.trim();
		
		view.setFocusOnIngresoCodigoBox(true);

		for (MaterialWrap m : materiales) {
			if (m.getMaterial().getCodigo().equals(id) && m.getMaterial().getEtapa()!=null && !m.getMaterial().getEtapa().equals(EtapaDTO.CENTRO_DE_OPERACIONES)) {
				if (!ingresoDataProvider.getList().contains(m)) {
					ingresoDataProvider.getList().add(m);
					view.setTotalMaterialIngresando(ingresoDataProvider
							.getList().size());
					view.setIngresoSortHandler(new ListHandler<MaterialWrap>(
							ingresoDataProvider.getList()));
					if(m.getMaterial().getIdCentro() == place.getCentroId()){
						eventBus.fireEvent(new SoundNotificationEvent(SoundNotificationEvent.NOTIFICACION));
					}else{
						eventBus.fireEvent(new SoundNotificationEvent(SoundNotificationEvent.ERROR));
					}
				} else {
					eventBus.fireEvent(new MensajeEvent(
							"El código ingresado ya esta en la lista",
							MensajeEvent.MSG_WARNING, true));
					eventBus.fireEvent(new SoundNotificationEvent(SoundNotificationEvent.ERROR));
				}
				return;
			}else if(m.getMaterial().getCodigo().equals(id) && (m.getMaterial().getEtapa()==null || m.getMaterial().getEtapa().equals(EtapaDTO.CENTRO_DE_OPERACIONES))){
				eventBus.fireEvent(new MensajeEvent(
						"El código ingresado pertenece a un material que ya esta marcado como en el centro de operaciones",
						MensajeEvent.MSG_WARNING, true));
				eventBus.fireEvent(new SoundNotificationEvent(SoundNotificationEvent.ERROR));
				return;
			}
		}

		for(MaterialWrap mw:ingresoDataProvider.getList()){
			if(mw.getMaterial().getCodigo().equals(id)){
				eventBus.fireEvent(new MensajeEvent(
						"El código ingresado ya esta en la lista",
						MensajeEvent.MSG_WARNING, true));
				eventBus.fireEvent(new SoundNotificationEvent(SoundNotificationEvent.ERROR));
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
		w.setUpdating(false);
		ingresoDataProvider.getList().add(w);
		view.setTotalMaterialIngresando(ingresoDataProvider.getList().size());
		materialesNuevos.add(w);
		updateMaterial(w);
	}

	@Override
	public void onMaterialAddedToPredespachoStack(String id) {
		id = id.trim();
		view.setFocusOnPredespachoCodigoBox(true);
		if (selectedLote == null) {
			eventBus.fireEvent(new MensajeEvent(
					"Seleccione un lote al cual ingresar el material y vuelva a intentarlo",
					MensajeEvent.MSG_WARNING, true));
			eventBus.fireEvent(new SoundNotificationEvent(SoundNotificationEvent.ERROR));
			return;
		}
		for (MaterialWrap m : materiales) {
			if (m.getMaterial().getCodigo().equals(id) && m.getMaterial().getEtapa()!=null && m.getMaterial().getEtapa().equals(EtapaDTO.CENTRO_DE_OPERACIONES)) {
				if(!materialesVisibles.contains(m)){
					eventBus.fireEvent(new MensajeEvent(
							"Este material no esta asignado al nivel o tipo de actividad seleccionado",
							MensajeEvent.MSG_WARNING, true));
					eventBus.fireEvent(new SoundNotificationEvent(SoundNotificationEvent.ERROR));
				}else if(predespachoDataProvider.getList().contains(m)){
					eventBus.fireEvent(new MensajeEvent(
							"El código ingresado ya esta en la lista",
							MensajeEvent.MSG_WARNING, true));
					eventBus.fireEvent(new SoundNotificationEvent(SoundNotificationEvent.ERROR));
				}else {
					predespachoDataProvider.getList().add(m);
					view.setTotalMaterialEnLote(predespachoDataProvider
							.getList().size());
					view.setPredespachoSortHandler(new ListHandler<MaterialWrap>(
							predespachoDataProvider.getList()));
					eventBus.fireEvent(new SoundNotificationEvent(SoundNotificationEvent.NOTIFICACION));
				} 
				return;
			}
		}
		eventBus.fireEvent(new MensajeEvent(
				"El código ingresado no pertenece a un material que esté marcado como en el centro de operaciones o no esta asociado a este asociado este centro",
				MensajeEvent.MSG_WARNING, true));
		eventBus.fireEvent(new SoundNotificationEvent(SoundNotificationEvent.ERROR));
	}

	@Override
	public void onMaterialAddedToDespachoStack(String id) {
		
		if(id.contains(",")){
			String[] ids = id.split(",");
			for(String x:ids){
				onMaterialAddedToDespachoStack(x);
			}
			return;
		}
		
		id = id.trim();
		
		view.setFocusOnDespachoCodigoBox(true);
		MaterialWrap mat = null;
		for (MaterialWrap m : materiales) {
			if (m.getMaterial().getCodigo().equals(id) && m.getMaterial().getEtapa()!=null && m.getMaterial().getEtapa().equals(EtapaDTO.CENTRO_DE_OPERACIONES)) {
				
				if(!materialesVisibles.contains(m)){
					eventBus.fireEvent(new MensajeEvent(
							"Este material no esta asignado al nivel o tipo de actividad seleccionado",
							MensajeEvent.MSG_WARNING, true));
					eventBus.fireEvent(new SoundNotificationEvent(SoundNotificationEvent.ERROR));
					return;
				}else{
					mat = m;
					if (!despachoDataProvider.getList().contains(m)) {
						despachoDataProvider.getList().add(m);
						eventBus.fireEvent(new SoundNotificationEvent(SoundNotificationEvent.NOTIFICACION));
					}
				}
				break;
			}
		}
		
		if(mat == null){
			eventBus.fireEvent(new MensajeEvent(
					"Este material no esta registrado en el centro de operaciones",
					MensajeEvent.MSG_WARNING, true));
			eventBus.fireEvent(new SoundNotificationEvent(SoundNotificationEvent.ERROR));
			return;
		}

		if (view.getAddByLote() && mat != null
				&& mat.getMaterial().getLote() != null) {
			for (MaterialWrap m : materialesVisibles) {
				if (m.getMaterial().getLote()!= null && m.getMaterial().getLote().getId() == mat.getMaterial()
						.getLote().getId()) {
					if (!despachoDataProvider.getList().contains(m)) {
						despachoDataProvider.getList().add(m);
					}
				}
			}
		}
		
		if(mat == null){
			eventBus.fireEvent(new MensajeEvent(
					"El código ingresado no pertenece a un material que esté marcado como en el centro de operaciones o no esta asociado a este centro",
					MensajeEvent.MSG_WARNING, true));
		}else{
			view.setDespachoSortHandler(new ListHandler<MaterialWrap>(
					despachoDataProvider.getList()));
			view.setTotalMaterialDespachando(despachoDataProvider.getList().size());
		}

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
		final ArrayList<String> codigos = new ArrayList<String>();

		for (MaterialWrap mw : ingresoDataProvider.getList()) {
			codigos.add(mw.getMaterial().getCodigo());
		}
		String folio = view.getIngresoFolio();
		if (Utils.hasPermisos(eventBus, getPermisos(), "MaterialService",
				"ingresarMateriales")) {
			
			localService.setIngreso(userKey+"-"+place.getCentroId(), codigos);
			
			getFactory().getMaterialService().ingresarMateriales(place.getCentroId(), codigos, folio, ingresoFile,new MaterialCallback<Boolean>(eventBus, true,userKey+"-"+place.getCentroId()) {
					
				@Override
				public void success(Boolean result) {
					localService.removeLastIngreso(getKey());
					materiales.addAll(materialesNuevos);
					materialesNuevos.clear();
					ingresoDataProvider.getList().clear();
					ingresoFile = null;
					view.setFocusOnIngresoCodigoBox(true);
					view.clearIngresoFolioBox();
					eventBus.fireEvent(new MensajeEvent(
							"El ingreso se realizó exitosamente",
							MensajeEvent.MSG_OK, true));
					setOrUpdateMaterialesList();
					updateMateriales(codigos);
					
				}
				
				@Override
				public void failure(Throwable caught) {
					eventBus.fireEvent(new MensajeEvent("Los materiales en la lista de ingreso se guardaron localmente." +
							"<br />1)Compruebe su conexión a internet."+
							"<br />2)Compruebe que puede realizar otras acciones en el sistema (intente ingresando nuevamente por el menú lateral para forzar a cargar los materiales del centro)"+
							"<br />3) Si todo lo anterior funciona normalmente, consulte a un encargado para solucionar el problema"));
				}
			});
		}
	}

	@Override
	public void onRealizarDespachoStackActualClick() {
		
		
		
		if (selectedEtapa == null) {
			eventBus.fireEvent(new MensajeEvent(
					"Seleccione la etapa hacia donde se dirigen los materiales",
					MensajeEvent.MSG_WARNING, true));
			return;
		} else if (selectedRetiranteRut == null || selectedRetiranteRut.isEmpty()) {
			eventBus.fireEvent(new MensajeEvent(
					"Ingrese el RUT de la persona que retira, o el suyo si la persona que retira no pertenece al proceso",
					MensajeEvent.MSG_WARNING, true));
			return;
		}else if (selectedEtapa.getEtapa().equals(EtapaDTO.CENTRO_DE_OPERACIONES) && selectedCo == null) {
			eventBus.fireEvent(new MensajeEvent(
					"Seleccione el Centro de operación destino",
					MensajeEvent.MSG_WARNING, true));
			return;
		}
		

		final ArrayList<String> codigos = new ArrayList<String>();

		for (MaterialWrap mw : despachoDataProvider.getList()) {
			codigos.add(mw.getMaterial().getCodigo());
		}
		String folio = view.getDespachoFolio();
		if (selectedEtapa != null
				&& selectedEtapa.getEtapa().equals(EtapaDTO.CENTRO_DE_OPERACIONES)
				&& selectedCo != null
				&& Utils.hasPermisos(eventBus, getPermisos(),
						"MaterialService", "despacharMateriales")) {
			
			localService.setDespacho(userKey+"-"+place.getCentroId(), codigos);
			
			getFactory().getMaterialService().despacharMateriales(
					place.getCentroId(), selectedCo.getId(),
					selectedRetiranteRut, codigos, folio, despachoFile,
					new MaterialCallback<Boolean>(eventBus, true, userKey+"-"+place.getCentroId()) {

						@Override
						public void success(Boolean result) {
							localService.removeLastDespacho(getKey());
							despachoDataProvider.getList().clear();
							despachoFile = null;
							view.setFocusOnDespachoCodigoBox(true);
							view.clearDespachoFolioBox();
							view.clearRutRetiranteBox();
							view.setRetirante(null);
							selectedRetiranteRut = null;
							updateMateriales(codigos);
							extractLotes();
							eventBus.fireEvent(new MensajeEvent(
									"El despacho se realizó exitosamente",
									MensajeEvent.MSG_OK, true));
						}
						
						@Override
						public void failure(Throwable caught) {
							eventBus.fireEvent(new MensajeEvent("Los materiales en la lista de despacho se guardaron localmente." +
									"<br />1)Compruebe su conexión a internet."+
									"<br />2)Compruebe que puede realizar otras acciones en el sistema (intente ingresando nuevamente por el menú lateral para forzar a cargar los materiales del centro)"+
									"<br />3) Si todo lo anterior funciona normalmente, consulte a un encargado para solucionar el problema"));
						}

					});
		} else if (selectedEtapa != null
				&& Utils.hasPermisos(eventBus, getPermisos(),
						"MaterialService", "despacharMateriales")) {
			localService.setDespacho(userKey+"-"+place.getCentroId(), codigos);
			getFactory().getMaterialService().despacharMateriales(
					place.getCentroId(), selectedEtapa,
					selectedRetiranteRut, codigos, folio, despachoFile,
					new MaterialCallback<Boolean>(eventBus, true,userKey+"-"+place.getCentroId()) {

						@Override
						public void success(Boolean result) {
							localService.removeLastDespacho(getKey());
							despachoDataProvider.getList().clear();
							despachoFile = null;
							view.setFocusOnDespachoCodigoBox(true);
							view.clearDespachoFolioBox();
							updateMateriales(codigos);
							eventBus.fireEvent(new MensajeEvent(
									"El despacho se realizó exitosamente",
									MensajeEvent.MSG_OK, true));
						}
						
						@Override
						public void failure(Throwable caught) {
							eventBus.fireEvent(new MensajeEvent("Los materiales en la lista de despacho se guardaron localmente." +
									"<br />1)Compruebe su conexión a internet."+
									"<br />2)Compruebe que puede realizar otras acciones en el sistema (intente ingresando nuevamente por el menú lateral para forzar a cargar los materiales del centro)"+
									"<br />3) Si todo lo anterior funciona normalmente, consulte a un encargado para solucionar el problema"));
						}
						
					});
		}
	}

	@Override
	public void onCrearLote(String nuevoLote) {
		view.clearNuevoLoteBox();
		for(LoteDTO l:lotes){
			if(l.getId() == -2){
				eventBus.fireEvent(new MensajeEvent(
						"Hay un lote anterior que no se a guardado en el servidor, Guardelo antes de crear otro lote",
						MensajeEvent.MSG_WARNING, true));
				view.selectLote(-2);
				onLoteSelected(-2);
				return;
			}
		}
		LoteDTO l = new LoteDTO();
		l.setNombre(nuevoLote);
		l.setId(-2);
		lotes.add(l);
		view.setLotes(lotes);
		view.selectLote(-2);
		onLoteSelected(-2);
	}

	@Override
	public void onDeleteLote(final int loteId) {

		if (loteId == -1) {
			return;
		}

		if (loteId == -2) {
			removeLoteOnClient(loteId);
		} else {
			if (loteId >= 0
					&& Utils.hasPermisos(eventBus, getPermisos(),
							"MaterialService", "eliminarLote")) {
				getFactory().getMaterialService().eliminarLote(
						place.getCentroId(), loteId,
						new SimceCallback<Boolean>(eventBus, true,60000) {

							@Override
							public void success(Boolean result) {
								removeLoteOnClient(loteId);
							}
						});
			}
		}
	}

	@Override
	public void onLoteSelected(int loteId) {
		if (loteId == -1) {
			predespachoDataProvider.getList().clear();
			view.setTotalMaterialEnLote(0);
			return;
		}
		for (LoteDTO l : lotes) {
			if (l.getId() == loteId) {
				selectedLote = l;
			}
		}
		if (selectedLote == null) {
			return;
		}

		ArrayList<MaterialWrap> lote = new ArrayList<MaterialWrap>();
		for (MaterialWrap m : materialesVisibles) {
			if (m.getMaterial().getLote() != null && m.getMaterial().getLote().getId() == selectedLote.getId()) {
				lote.add(m);
			}
		}
		predespachoDataProvider.setList(lote);
		view.setPredespachoSortHandler(new ListHandler<MaterialWrap>(
				predespachoDataProvider.getList()));
		view.setTotalMaterialEnLote(lote.size());
	}

	@Override
	public void guardarLote() {
		if (selectedLote == null) {
			eventBus.fireEvent(new MensajeEvent(
					"No hay ningun lote seleccionado",
					MensajeEvent.MSG_WARNING, true));
			return;
		}
		if (predespachoDataProvider.getList().isEmpty()) {
			eventBus.fireEvent(new MensajeEvent(
					"No ha ingresado ningun material, si desea desagrupar todos los materiales, elimine el lote seleccionando 'Eliminar'",
					MensajeEvent.MSG_WARNING, true));
			return;
		}

		ArrayList<Integer> mList = new ArrayList<Integer>();
		for (MaterialWrap mw : predespachoDataProvider.getList()) {
			mList.add(mw.getMaterial().getId());
		}
		if (Utils.hasPermisos(getPermisos(), "MaterialService",
				"crearOEditarLote")) {
			getFactory().getMaterialService().crearOEditarLote(
					place.getCentroId(), mList, selectedLote,
					new SimceCallback<Integer>(eventBus, true,60000) {

						@Override
						public void success(Integer result) {
							selectedLote.setId(result);
							for (MaterialWrap mw : predespachoDataProvider
									.getList()) {
								mw.getMaterial().setLote(selectedLote);
							}
							view.setLotes(lotes);
							view.selectLote(result);
							eventBus.fireEvent(new MensajeEvent(
									"El lote se a guardado exitosamente",
									MensajeEvent.MSG_OK, true));
						}
					});
		}
	}

	@Override
	public void onEtapaChange(int etapaId) {
		view.setChangeCoButtonVisivility(false);
		selectedCo = null;
		view.setSelectedCo(null);
		if (etapaId == -1) {
			selectedEtapa = null;
		}else {
			for (EtapaDTO etapa : etapas) {
				if (etapa.getId() == etapaId) {
					selectedEtapa = etapa;
					break;
				}
			}
		}
		if(selectedEtapa !=null && selectedEtapa.getEtapa().equals(EtapaDTO.CENTRO_DE_OPERACIONES)){
			//selectedEtapa = null;
			view.setChangeCoButtonVisivility(true);
			onChangeSelectedStageCo();
		}
	}

	@Override
	public void onRemoveIngresoItem(MaterialWrap material) {
		ingresoDataProvider.getList().remove(material);
		materialesNuevos.remove(material);
		view.setTotalMaterialIngresando(ingresoDataProvider.getList().size());
		view.setIngresoSortHandler(new ListHandler<MaterialWrap>(
				ingresoDataProvider.getList()));
		view.setFocusOnIngresoCodigoBox(true);
	}

	@Override
	public void onRemovePredespachoItem(MaterialWrap material) {
		predespachoDataProvider.getList().remove(material);
		view.setTotalMaterialEnLote(predespachoDataProvider.getList().size());
		view.setPredespachoSortHandler(new ListHandler<MaterialWrap>(
				predespachoDataProvider.getList()));
		view.setFocusOnPredespachoCodigoBox(true);
	}

	@Override
	public void onRemoveDespachoItem(MaterialWrap material) {
		despachoDataProvider.getList().remove(material);
		view.setTotalMaterialDespachando(despachoDataProvider.getList().size());
		view.setDespachoSortHandler(new ListHandler<MaterialWrap>(
				despachoDataProvider.getList()));
		view.setFocusOnDespachoCodigoBox(true);
	}

	@Override
	public void onChangeSelectedStageCo() {
		final CentroOperacionSelector cos = new CentroOperacionSelector(
				getFactory(), allCos);
		cos.setSelectedCommand(new Command() {

			@Override
			public void execute() {
				selectedCo = cos.getSelectedEmplazamiento();
				view.setSelectedCo(selectedCo);
				cos.hide();
			}
		});
		cos.show();
	}

	@Override
	public void onRutRetiranteChange(String rut) {
		if (rut == null || rut.length() == 0) {
			selectedRetiranteRut = null;
			return;
		}
		selectedRetiranteRut = rut;
		
		selectedRetiranteRut = selectedRetiranteRut.replace('k', 'K');
		
		if (Utils.hasPermisos(eventBus, getPermisos(),
				"MaterialService", "getUser")) {
			UserDTO u = localService.getRetirante(userKey+"-"+rut);
			if(u!=null){
				view.setRetirante(u);
				eventBus.fireEvent(new MensajeEvent(
						"El receptor pertenece al proceso YoSimce",
						MensajeEvent.MSG_OK, true));
			}else{
				getFactory().getMaterialService().getUser(rut,
						new MaterialCallback<UserDTO>(eventBus, false,userKey+"-"+rut) {
	
							@Override
							public void success(UserDTO result) {
								localService.setRetirante(getKey(), result);
								view.setRetirante(result);
								eventBus.fireEvent(new MensajeEvent(
										"El receptor pertenece al proceso YoSimce",
										MensajeEvent.MSG_OK, true));
							}
						});
			}
		}
	}

	private void initialize() {
		view.setCO(co);

		if (Utils.hasPermisos(eventBus, getPermisos(), "MaterialService",
				"getMateriales")) {
			getFactory().getMaterialService().getMateriales(co.getId(),new MaterialCallback<ArrayList<MaterialDTO>>(eventBus, true,userKey+"-"+co.getId()) {
						
				@Override
				public void saveOnLocalStorage(String key,
						ArrayList<MaterialDTO> result) {
					localService.setMateriales(key, result);
				}
				
				@Override
				public ArrayList<MaterialDTO> tryFromLocalStorage(String key) {
					return localService.getMateriales(key);
				}
				
				@Override
				public void success(ArrayList<MaterialDTO> result) {
					materiales = wrap(result);
					setOrUpdateMaterialesList();
					extractLotes();
				}
			});
		}
	}
	
	private void setEmplazamientosAsociados(ArrayList<EmplazamientoDTO> emplazamientos){
		cosAsociados = emplazamientos;
		if (cosAsociados.isEmpty()) {
			CentroOperacionActivity.this.eventBus
					.fireEvent(new MensajeEvent(
							"No tiene ningun centro de operación asociado",
							MensajeEvent.MSG_WARNING, true));
			goTo(new SimcePlace());
		} else if (cosAsociados.size() == 1) {
			if (place.getCentroId() == cosAsociados.get(0)
					.getId()) {
				co = cosAsociados.get(0);
				initialize();
			} else {
				CentroOperacionPlace p = new CentroOperacionPlace();
				p.setCentroId(cosAsociados.get(0).getId());
				goTo(p);
			}
		} else {
			EmplazamientoDTO selected = null;
			for (EmplazamientoDTO empl : cosAsociados) {
				if (empl.getId() == place.getCentroId()) {
					selected = empl;
					break;
				}
			}
			if (selected != null) {
				co = selected;
				initialize();
			} else {
				showCoSelectorPopup();
			}
		}
	}
	
	private void removeLoteOnClient(int loteId) {
		for (LoteDTO l : lotes) {
			if (l.getId() == loteId) {
				lotes.remove(l);
				break;
			}
		}
		for (MaterialWrap mw : materialesVisibles) {
			if (mw.getMaterial().getLote() != null
					&& mw.getMaterial().getLote().getId() == loteId) {
				mw.getMaterial().setLote(null);
			}
		}

		view.setLotes(lotes);
		view.selectLote(-1);
		onLoteSelected(-1);
		eventBus.fireEvent(new MensajeEvent(
				"El lote se a eliminado exitosamente", MensajeEvent.MSG_OK,
				true));
	}

	private void showCoSelectorPopup() {
		final CentroOperacionSelector cos = new CentroOperacionSelector(
				getFactory(), cosAsociados);
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

	private ArrayList<MaterialWrap> wrap(ArrayList<MaterialDTO> list) {
		ArrayList<MaterialWrap> wrap = new ArrayList<MaterialWrap>();
		for (MaterialDTO m : list) {
			MaterialWrap w = new MaterialWrap();
			w.setMaterial(m);
			w.setUpdating(false);
			wrap.add(w);
		}
		return wrap;
	}

	private void extractLotes() {
		if (lotes == null) {
			lotes = new ArrayList<LoteDTO>();
		}
		lotes.clear();
		for (MaterialWrap m : materialesVisibles) {
			if (m.getMaterial().getLote() != null
					&& !lotes.contains(m.getMaterial().getLote())) {
				lotes.add(m.getMaterial().getLote());
			}
		}
		view.setLotes(lotes);
	}

	private void updateMateriales(ArrayList<String> codigos){
		if (Utils.hasPermisos(eventBus, getPermisos(), "MaterialService",
				"getMaterialesByCodigos")) {
			
			for(MaterialWrap mw:materiales){
				if(codigos.contains(mw.getMaterial().getCodigo())){
					mw.setUpdating(true);
					if(materiales.contains(mw)){
						materiales.get(materiales.indexOf(mw)).setUpdating(true);
					}
					if (materialDataProvider.getList()
							.contains(mw)) {
						int index = materialDataProvider
								.getList().indexOf(mw);
						materialDataProvider.getList()
								.get(index).setUpdating(true);
						materialDataProvider.getList().set(
								index, mw);
					}
					if (ingresoDataProvider.getList()
							.contains(mw)) {
						int index = ingresoDataProvider
								.getList().indexOf(mw);
						ingresoDataProvider.getList()
								.get(index).setUpdating(true);
						ingresoDataProvider.getList().set(
								index, mw);
					}
					if (predespachoDataProvider.getList()
							.contains(mw)) {
						int index = predespachoDataProvider
								.getList().indexOf(mw);
						predespachoDataProvider.getList()
								.get(index).setUpdating(true);
						predespachoDataProvider.getList()
								.set(index, mw);
					}
					if (despachoDataProvider.getList()
							.contains(mw)) {
						int index = despachoDataProvider
								.getList().indexOf(mw);
						despachoDataProvider.getList()
								.get(index).setUpdating(true);
						despachoDataProvider.getList().set(
								index, mw);
					}
				}
			}
			
			getFactory().getMaterialService().getMaterialesByCodigos(codigos,new SimceCallback<ArrayList<MaterialDTO>>(eventBus, false) {

				@Override
				public void success(ArrayList<MaterialDTO> result) {
					for (MaterialDTO m : result) {
						for(MaterialWrap mw:materiales){
							if (mw.getMaterial().getCodigo().equals(m.getCodigo())) {
								mw.setUpdating(false);
								if(materiales.contains(mw)){
									materiales.get(materiales.indexOf(mw)).setMaterial(m);
									materiales.get(materiales.indexOf(mw)).setUpdating(false);
								}
								if (materialDataProvider.getList().contains(mw)) {
									int index = materialDataProvider.getList().indexOf(mw);
									materialDataProvider.getList().get(index).setMaterial(m);
									materialDataProvider.getList().get(index).setUpdating(false);
									materialDataProvider.getList().set(index, mw);
								}
								if (ingresoDataProvider.getList()
										.contains(mw)) {
									int index = ingresoDataProvider
											.getList().indexOf(mw);
									ingresoDataProvider.getList().get(index).setMaterial(m);
									ingresoDataProvider.getList().get(index).setUpdating(false);
									ingresoDataProvider.getList().set(
											index, mw);
								}
								if (predespachoDataProvider.getList()
										.contains(mw)) {
									int index = predespachoDataProvider
											.getList().indexOf(mw);
									predespachoDataProvider.getList().get(index).setMaterial(m);
									predespachoDataProvider.getList().get(index).setUpdating(false);
									predespachoDataProvider.getList()
											.set(index, mw);
								}
								if (despachoDataProvider.getList()
										.contains(mw)) {
									int index = despachoDataProvider
											.getList().indexOf(mw);
									despachoDataProvider.getList().get(index).setMaterial(m);
									despachoDataProvider.getList().get(index).setUpdating(false);
									despachoDataProvider.getList().set(index, mw);
								}
								break;
							}
						}
					}
					view.setMaterialSortHandler(new ListHandler<MaterialWrap>(materialDataProvider.getList()));
					view.setIngresoSortHandler(new ListHandler<MaterialWrap>(ingresoDataProvider.getList()));
					view.setPredespachoSortHandler(new ListHandler<MaterialWrap>(predespachoDataProvider.getList()));
					view.setDespachoSortHandler(new ListHandler<MaterialWrap>(despachoDataProvider.getList()));
					buildFiltro();
				}

				@Override
				public void failure(Throwable caught) {
					for(MaterialWrap mw:materiales){
						mw.setUpdating(false);
						if(materiales.contains(mw)){
							materiales.get(materiales.indexOf(mw)).setUpdating(false);
						}
						if (materialDataProvider.getList()
								.contains(mw)) {
							int index = materialDataProvider
									.getList().indexOf(mw);
							materialDataProvider.getList()
									.get(index).setUpdating(false);
							materialDataProvider.getList().set(
									index, mw);
						}
						if (ingresoDataProvider.getList()
								.contains(mw)) {
							int index = ingresoDataProvider
									.getList().indexOf(mw);
							ingresoDataProvider.getList()
									.get(index).setUpdating(false);
							ingresoDataProvider.getList().set(
									index, mw);
						}
						if (predespachoDataProvider.getList()
								.contains(mw)) {
							int index = predespachoDataProvider
									.getList().indexOf(mw);
							predespachoDataProvider.getList()
									.get(index).setUpdating(false);
							predespachoDataProvider.getList()
									.set(index, mw);
						}
						if (despachoDataProvider.getList()
								.contains(mw)) {
							int index = despachoDataProvider
									.getList().indexOf(mw);
							despachoDataProvider.getList()
									.get(index).setUpdating(false);
							despachoDataProvider.getList().set(
									index, mw);
						}
					}
				}
			});
			
		}
	}
	
	private void updateMaterial(final MaterialWrap mw) {
		
		if (Utils.hasPermisos(eventBus, getPermisos(), "MaterialService",
				"getMaterialesByCodigos")) {
			
			ArrayList<String> mList = new ArrayList<String>();
			mList.add(mw.getMaterial().getCodigo());
			mw.setUpdating(true);
			if(materiales.contains(mw)){
				materiales.get(materiales.indexOf(mw)).setUpdating(true);
			}
			if (materialDataProvider.getList()
					.contains(mw)) {
				int index = materialDataProvider
						.getList().indexOf(mw);
				materialDataProvider.getList()
						.get(index).setUpdating(true);
				materialDataProvider.getList().set(
						index, mw);
			}
			if (ingresoDataProvider.getList()
					.contains(mw)) {
				int index = ingresoDataProvider
						.getList().indexOf(mw);
				ingresoDataProvider.getList()
						.get(index).setUpdating(true);
				ingresoDataProvider.getList().set(
						index, mw);
			}
			if (predespachoDataProvider.getList()
					.contains(mw)) {
				int index = predespachoDataProvider
						.getList().indexOf(mw);
				predespachoDataProvider.getList()
						.get(index).setUpdating(true);
				predespachoDataProvider.getList()
						.set(index, mw);
			}
			if (despachoDataProvider.getList()
					.contains(mw)) {
				int index = despachoDataProvider
						.getList().indexOf(mw);
				despachoDataProvider.getList()
						.get(index).setUpdating(true);
				despachoDataProvider.getList().set(
						index, mw);
			}
			
			getFactory().getMaterialService().getMaterialesByCodigos(mList,new SimceCallback<ArrayList<MaterialDTO>>(eventBus, false) {

						@Override
						public void success(ArrayList<MaterialDTO> result) {
							for (MaterialDTO m : result) {
									if (mw.getMaterial().getCodigo().equals(m.getCodigo())) {
										mw.setUpdating(false);
										if(materialesNuevos.contains(mw)){
											materialesNuevos.get(materialesNuevos.indexOf(mw)).setMaterial(m);
											materialesNuevos.get(materialesNuevos.indexOf(mw)).setUpdating(false);
										}
										if(materiales.contains(mw)){
											materiales.get(materiales.indexOf(mw)).setMaterial(m);
											materiales.get(materiales.indexOf(mw)).setUpdating(false);
										}
										if (materialDataProvider.getList()
												.contains(mw)) {
											int index = materialDataProvider
													.getList().indexOf(mw);
											materialDataProvider.getList().get(index).setMaterial(m);
											materialDataProvider.getList().get(index).setUpdating(false);
											materialDataProvider.getList().set(
													index, mw);
										}
										if (ingresoDataProvider.getList()
												.contains(mw)) {
											int index = ingresoDataProvider
													.getList().indexOf(mw);
											ingresoDataProvider.getList().get(index).setMaterial(m);
											ingresoDataProvider.getList().get(index).setUpdating(false);
											ingresoDataProvider.getList().set(
													index, mw);
										}
										if (predespachoDataProvider.getList()
												.contains(mw)) {
											int index = predespachoDataProvider
													.getList().indexOf(mw);
											predespachoDataProvider.getList().get(index).setMaterial(m);
											predespachoDataProvider.getList().get(index).setUpdating(false);
											predespachoDataProvider.getList()
													.set(index, mw);
										}
										if (despachoDataProvider.getList()
												.contains(mw)) {
											int index = despachoDataProvider
													.getList().indexOf(mw);
											despachoDataProvider.getList().get(index).setMaterial(m);
											despachoDataProvider.getList().get(index).setUpdating(false);
											despachoDataProvider.getList().set(index, mw);
										}
										break;
									}
								}
						}

						@Override
						public void failure(Throwable caught) {
							eventBus.fireEvent(new SoundNotificationEvent(SoundNotificationEvent.ERROR));
							mw.setUpdating(false);
							if(materiales.contains(mw)){
								materiales.get(materiales.indexOf(mw)).setUpdating(false);
							}
							if (materialDataProvider.getList()
									.contains(mw)) {
								int index = materialDataProvider
										.getList().indexOf(mw);
								materialDataProvider.getList()
										.get(index).setUpdating(false);
								materialDataProvider.getList().set(
										index, mw);
							}
							if (ingresoDataProvider.getList()
									.contains(mw)) {
								int index = ingresoDataProvider
										.getList().indexOf(mw);
								ingresoDataProvider.getList()
										.get(index).setUpdating(false);
								ingresoDataProvider.getList().set(
										index, mw);
							}
							if (predespachoDataProvider.getList()
									.contains(mw)) {
								int index = predespachoDataProvider
										.getList().indexOf(mw);
								predespachoDataProvider.getList()
										.get(index).setUpdating(false);
								predespachoDataProvider.getList()
										.set(index, mw);
							}
							if (despachoDataProvider.getList()
									.contains(mw)) {
								int index = despachoDataProvider
										.getList().indexOf(mw);
								despachoDataProvider.getList()
										.get(index).setUpdating(false);
								despachoDataProvider.getList().set(
										index, mw);
							}
						}

					});
		}
	}

	private void executeFiltro(String key, String value){
		ArrayList<MaterialWrap> filtro = new ArrayList<MaterialWrap>();
		for(MaterialWrap mw:materialesVisibles){
			if(key.equals("Tipos de material") && mw.getMaterial().getTipo()!=null && mw.getMaterial().getTipo().equals(value)){
				filtro.add(mw);
			}/*else if(key.equals("Niveles") && mw.getMaterial().getNivel()!=null && mw.getMaterial().getNivel().equals(value)){
				filtro.add(mw);
			}*/else if(key.equals("Etapas") && mw.getMaterial().getEtapa()!=null && mw.getMaterial().getEtapa().equals(value)){
				filtro.add(mw);
			}
		}
		
		Collections.sort(filtro, comparator);
		
		materialDataProvider.setList(filtro);
		view.setMaterialSortHandler(new ListHandler<MaterialWrap>(materialDataProvider.getList()));
	}
	
	private void setOrUpdateMaterialesList(){
		materialesVisibles.clear();
		
		//********SIMCE TIC************
		if(place.getAplicacionId() == 2){
			materialesVisibles.addAll(materiales);
		}
		//********SIMCE TIC************
		else{
			for(MaterialWrap mw:materiales){
				if((mw.getMaterial().getIdNivel() == null || mw.getMaterial().getIdNivel() == place.getNivelId()) &&
						(mw.getMaterial().getIdTipoActividad() == null || mw.getMaterial().getIdTipoActividad() == place.getTipoId())){
					materialesVisibles.add(mw);
				}
			}
		}
		
		Collections.sort(materialesVisibles, comparator);
		
		materialDataProvider.setList(materialesVisibles);
		view.setMaterialSortHandler(new ListHandler<MaterialWrap>(materialDataProvider.getList()));
		extractLotes();
		buildFiltro();
	}
	
	private void buildFiltro(){
		ArrayList<String> tipos = new ArrayList<String>();
		//ArrayList<String> niveles = new ArrayList<String>();
		ArrayList<String> etapas = new ArrayList<String>();
		for(MaterialWrap mw:materialesVisibles){
			if(mw.getMaterial().getTipo()!=null && !tipos.contains(mw.getMaterial().getTipo())){
				tipos.add(mw.getMaterial().getTipo());
			}
			/*if(mw.getMaterial().getNivel()!=null && !niveles.contains(mw.getMaterial().getNivel())){
				niveles.add(mw.getMaterial().getNivel());
			}*/
			if(mw.getMaterial().getEtapa()!=null && !etapas.contains(mw.getMaterial().getEtapa())){
				etapas.add(mw.getMaterial().getEtapa());
			}
		}
		
		MenuBar submenu = new MenuBar(true);
		view.getFiltroMenu().setSubMenu(submenu);
		if(tipos.size()>1){
			MenuBar tiposMenuBar = new MenuBar(true);
			for(final String tipo:tipos){
				tiposMenuBar.addItem(tipo, new Scheduler.ScheduledCommand() {
					
					@Override
					public void execute() {
						executeFiltro("Tipos de material", tipo);
					}
				});
			}
			submenu.addItem("Tipos de material", tiposMenuBar);
		}
		/*if(niveles.size()>1){
			MenuBar nivelesMenuBar = new MenuBar(true);
			for(final String nivel:niveles){
				nivelesMenuBar.addItem(nivel, new Scheduler.ScheduledCommand() {
					
					@Override
					public void execute() {
						executeFiltro("Niveles", nivel);
					}
				});
			}
			submenu.addItem("Niveles", nivelesMenuBar);
		}*/
		if(etapas.size()>1){
			MenuBar etapasMenuBar = new MenuBar(true);
			for(final String etapa:etapas){
				etapasMenuBar.addItem(etapa, new Scheduler.ScheduledCommand() {
					
					@Override
					public void execute() {
						executeFiltro("Etapas", etapa);
					}
				});
			}
			submenu.addItem("Etapas", etapasMenuBar);
		}
		submenu.addSeparator();
		submenu.addItem("Limpiar", new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				setOrUpdateMaterialesList();
			}
		});
		
	}
}
