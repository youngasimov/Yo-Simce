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
	private ArrayList<EmplazamientoDTO> allCos;
	private EmplazamientoDTO co;
	private ArrayList<LoteDTO> lotes;
	private LoteDTO selectedLote;
	private ArrayList<EtapaDTO> etapas;
	private int intentos;
	private String ingresoFile;
	private String despachoFile;
	private EtapaDTO selectedEtapa;
	private EmplazamientoDTO selectedCo;
	private String selectedRetiranteRut;
	private HashMap<String, UserDTO> users;

	public CentroOperacionActivity(ClientFactory factory,
			CentroOperacionPlace place,
			HashMap<String, ArrayList<String>> permisos) {
		super(factory, place, permisos, true);
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
		outdated = new ArrayList<MaterialWrap>();
		users = new HashMap<String, UserDTO>();
		intentos = 0;
		view.setTotalMaterialIngresando(0);
		view.setTotalMaterialEnLote(0);
		view.setTotalMaterialDespachando(0);
		selectedEtapa = null;
		selectedCo = null;
	}

	@Override
	public void init(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view.asWidget());
		this.eventBus = eventBus;

		intentos = 0;
		view.setTotalMaterialIngresando(0);
		view.setTotalMaterialEnLote(0);
		view.setTotalMaterialDespachando(0);
		selectedEtapa = null;
		selectedCo = null;

		view.setMaterialVisivility(Utils.hasPermisos(getPermisos(),
				"MaterialService", "getMateriales"));
		view.setIngresoVisivility(Utils.hasPermisos(getPermisos(),
				"MaterialService", "ingresarMateriales"));
		view.setPredespachoVisivility(Utils.hasPermisos(getPermisos(),
				"MaterialService", "crearOEditarLote"));
		view.setDespachoVisivility(Utils.hasPermisos(getPermisos(),
				"MaterialService", "despacharMateriales"));

		if (Utils.hasPermisos(eventBus, getPermisos(), "MaterialService",
				"getCentrosOperacionAsociados")) {
			getFactory().getMaterialService().getCentrosOperacionAsociados(
					new SimceCallback<ArrayList<EmplazamientoDTO>>(eventBus,
							true) {

						@Override
						public void success(ArrayList<EmplazamientoDTO> result) {
							cosAsociados = result;

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
			getFactory().getMaterialService().getCentrosOperacion(
					new SimceCallback<ArrayList<EmplazamientoDTO>>(eventBus,
							true) {

						@Override
						public void success(ArrayList<EmplazamientoDTO> result) {
							allCos = result;
						}
						
						@Override
						public void failure(Throwable caught) {
							allCos = new ArrayList<EmplazamientoDTO>();
						}

					});
		}else{
			allCos = new ArrayList<EmplazamientoDTO>();
		}

		if (Utils.hasPermisos(getPermisos(), "MaterialService",
				"getEtapas")) {
			getFactory().getMaterialService().getEtapas(
					new SimceCallback<ArrayList<EtapaDTO>>(eventBus, true) {

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
		super.onStop();
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
		if (material.getHistorial() != null) {
			historialDataProvider.setList(material.getHistorial());
		}

		// **********************SOLO TESTING**************************//
		/*
		 * ArrayList<HistorialMaterialItemDTO> historial = new
		 * ArrayList<HistorialMaterialItemDTO>(); UserDTO u = new UserDTO();
		 * u.setNombres("Juan Diego"); u.setApellidoPaterno("Jara");
		 * u.setApellidoMaterno("Lillo"); u.setId(1); u.setRut("18574996-1");
		 * HistorialMaterialItemDTO h = new HistorialMaterialItemDTO();
		 * h.setAutorizante(u); h.setDesde("Ministerio"); h.setHacia("Centro");
		 * h.setFecha(new Date()); historial.add(h);
		 * 
		 * h = new HistorialMaterialItemDTO(); h.setAutorizante(u);
		 * h.setDesde("Centro"); h.setHacia("Establecimiento"); Date d = new
		 * Date(); CalendarUtil.addDaysToDate(d, 10); h.setFecha(d);
		 * historial.add(h);
		 * 
		 * h = new HistorialMaterialItemDTO(); h.setAutorizante(u);
		 * h.setDesde("Establecimiento"); h.setHacia("Centro"); d = new Date();
		 * CalendarUtil.addDaysToDate(d, 10); d.setHours(d.getHours()+4);
		 * h.setFecha(d); historial.add(h);
		 * 
		 * material.setHistorial(historial);
		 * historialDataProvider.setList(material.getHistorial());
		 */

		// **********************SOLO TESTING**************************//

		if (Utils.hasPermisos(eventBus, getPermisos(), "MaterialService",
				"getDetallesMaterial")) {
			getFactory().getMaterialService().getDetallesMaterial(
					material.getMaterial().getId(),
					new SimceCallback<DetallesMaterialDTO>(eventBus, false) {

						@Override
						public void success(DetallesMaterialDTO result) {
							if (result.getHistorial() != null) {
								material.setHistorial(result.getHistorial());
								historialDataProvider.setList(material
										.getHistorial());
							}
						}
					});
		}
	}

	@Override
	public void onMaterialAddedToIngresoStack(String id) {

		view.setFocusOnIngresoCodigoBox(true);

		for (MaterialWrap m : materialDataProvider.getList()) {
			if (m.getMaterial().getCodigo().equals(id)) {
				if (!ingresoDataProvider.getList().contains(m)) {
					ingresoDataProvider.getList().add(m);
					view.setTotalMaterialIngresando(ingresoDataProvider
							.getList().size());
				} else {
					eventBus.fireEvent(new MensajeEvent(
							"El código ingresado ya esta en la lista",
							MensajeEvent.MSG_WARNING, true));
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
		if (selectedLote == null) {
			eventBus.fireEvent(new MensajeEvent(
					"Seleccione un lote al cual ingresar el material y vuelva a intentarlo",
					MensajeEvent.MSG_WARNING, true));
			return;
		}
		for (MaterialWrap m : materialDataProvider.getList()) {
			if (m.getMaterial().getCodigo().equals(id)) {
				if (!predespachoDataProvider.getList().contains(m)) {
					predespachoDataProvider.getList().add(m);
					view.setTotalMaterialEnLote(predespachoDataProvider
							.getList().size());
				} else {
					eventBus.fireEvent(new MensajeEvent(
							"El código ingresado ya esta en la lista",
							MensajeEvent.MSG_WARNING, true));
				}
				return;
			}
		}
		eventBus.fireEvent(new MensajeEvent(
				"El código ingresado no pertenece a un material asociado a su centro",
				MensajeEvent.MSG_WARNING, true));
	}

	@Override
	public void onMaterialAddedToDespachoStack(String id) {
		view.setFocusOnDespachoCodigoBox(true);
		MaterialWrap mat = null;
		for (MaterialWrap m : materialDataProvider.getList()) {
			if (m.getMaterial().getCodigo().equals(id)) {
				mat = m;
				if (!despachoDataProvider.getList().contains(m)) {
					despachoDataProvider.getList().add(m);
				}
				break;
			}
		}

		if (view.getAddByLote() && mat != null
				&& mat.getMaterial().getLote() != null) {
			for (MaterialWrap m : materialDataProvider.getList()) {
				if (m.getMaterial().getLote().getId() == mat.getMaterial()
						.getLote().getId()) {
					if (!despachoDataProvider.getList().contains(m)) {
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

		for (MaterialWrap mw : ingresoDataProvider.getList()) {
			codigos.add(mw.getMaterial().getCodigo());
		}
		String folio = view.getIngresoFolio();
		if (Utils.hasPermisos(eventBus, getPermisos(), "MaterialService",
				"ingresarMateriales")) {
			getFactory().getMaterialService().ingresarMateriales(
					place.getCentroId(), codigos, folio, ingresoFile,
					new SimceCallback<Boolean>(eventBus, true) {

						@Override
						public void success(Boolean result) {
							ingresoDataProvider.getList().clear();
							ingresoFile = null;
							view.setFocusOnIngresoCodigoBox(true);
							view.clearIngresoFolioBox();
							eventBus.fireEvent(new MensajeEvent(
									"El ingreso se realizó exitosamente",
									MensajeEvent.MSG_OK, true));
						}
					});
		}
	}

	@Override
	public void onRealizarDespachoStackActualClick() {
		if (selectedEtapa == null && selectedCo == null) {
			eventBus.fireEvent(new MensajeEvent(
					"Seleccione la etapa hacia donde se dirigen los materiales",
					MensajeEvent.MSG_WARNING, true));
			return;
		} else if (selectedRetiranteRut == null) {
			eventBus.fireEvent(new MensajeEvent(
					"Ingrese el RUT de la persona que retira, o el suyo si la persona que retira no pertenece al proceso",
					MensajeEvent.MSG_WARNING, true));
			return;
		}

		ArrayList<String> codigos = new ArrayList<String>();

		for (MaterialWrap mw : despachoDataProvider.getList()) {
			codigos.add(mw.getMaterial().getCodigo());
		}
		String folio = view.getDespachoFolio();

		if (selectedEtapa == null
				&& selectedCo != null
				&& Utils.hasPermisos(eventBus, getPermisos(),
						"MaterialService", "despacharMateriales")) {
			getFactory().getMaterialService().despacharMateriales(
					place.getCentroId(), selectedCo.getId(),
					selectedRetiranteRut, codigos, folio, despachoFile,
					new SimceCallback<Boolean>(eventBus, true) {

						@Override
						public void success(Boolean result) {
							despachoDataProvider.getList().clear();
							despachoFile = null;
							view.setFocusOnDespachoCodigoBox(true);
							view.clearDespachoFolioBox();
							eventBus.fireEvent(new MensajeEvent(
									"El despacho se realizó exitosamente",
									MensajeEvent.MSG_OK, true));
						}

					});
		} else if (selectedEtapa != null
				&& Utils.hasPermisos(eventBus, getPermisos(),
						"MaterialService", "despacharMateriales")) {
			getFactory().getMaterialService().despacharMateriales(
					place.getCentroId(), selectedEtapa.getId(),
					selectedRetiranteRut, codigos, folio, despachoFile,
					new SimceCallback<Boolean>(eventBus, true) {

						@Override
						public void success(Boolean result) {
							despachoDataProvider.getList().clear();
							despachoFile = null;
							view.setFocusOnDespachoCodigoBox(true);
							view.clearDespachoFolioBox();
							eventBus.fireEvent(new MensajeEvent(
									"El despacho se realizó exitosamente",
									MensajeEvent.MSG_OK, true));
						}
					});
		}
	}

	@Override
	public void onCrearLote(String nuevoLote) {
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
						new SimceCallback<Boolean>(eventBus, true) {

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
		for (MaterialWrap m : materialDataProvider.getList()) {
			if (m.getMaterial().getLote().getId() == selectedLote.getId()) {
				lote.add(m);
			}
		}
		predespachoDataProvider.setList(lote);
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
					new SimceCallback<Integer>(eventBus, true) {

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
		if (etapaId == -1) {
			selectedEtapa = null;
		} else if (etapaId == -2) {
			selectedEtapa = null;
			view.setChangeCoButtonVisivility(true);
			onChangeSelectedStageCo();
		} else {
			for (EtapaDTO etapa : etapas) {
				if (etapa.getId() == etapaId) {
					selectedEtapa = etapa;
					break;
				}
			}
		}
	}

	@Override
	public void onRemoveIngresoItem(MaterialWrap material) {
		ingresoDataProvider.getList().remove(material);
		view.setTotalMaterialIngresando(ingresoDataProvider.getList().size());
	}

	@Override
	public void onRemovePredespachoItem(MaterialWrap material) {
		predespachoDataProvider.getList().remove(material);
		view.setTotalMaterialEnLote(predespachoDataProvider.getList().size());
	}

	@Override
	public void onRemoveDespachoItem(MaterialWrap material) {
		despachoDataProvider.getList().remove(material);
		view.setTotalMaterialDespachando(despachoDataProvider.getList().size());
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

		if (users.containsKey(selectedRetiranteRut)) {
			view.setRetirante(users.get(selectedRetiranteRut));
			eventBus.fireEvent(new MensajeEvent(
					"El retirante pertenece al proceso YoSimce",
					MensajeEvent.MSG_OK, true));
		} else if (Utils.hasPermisos(eventBus, getPermisos(),
				"MaterialService", "getUser")) {
			getFactory().getMaterialService().getUser(rut,
					new SimceCallback<UserDTO>(eventBus, false) {

						@Override
						public void success(UserDTO result) {
							users.put(selectedRetiranteRut, result);
							view.setRetirante(result);
							eventBus.fireEvent(new MensajeEvent(
									"El retirante pertenece al proceso YoSimce",
									MensajeEvent.MSG_OK, true));
						}
					});
		}
	}

	private void initialize() {

		view.setCO(co);

		// **********************SOLO TESTING**************************//
		/*
		 * ArrayList<MaterialDTO> result = new ArrayList<MaterialDTO>(); for(int
		 * i = 1;i<50;i++){ MaterialDTO m = new MaterialDTO();
		 * m.setCodigo("1000000"+i); if(i%5 == 0){ m.setCurso("B"); }else{
		 * m.setCurso("A"); } m.setEstablecimiento("Carmelitas descalsas");
		 * if(i%6 == 0){ m.setEtapa("Ministerio"); }else{ m.setEtapa("Centro");
		 * } m.setIdCentro(place.getCentroId()); m.setId(i);
		 * m.setNivel("4 básico"); m.setRbd("49576"); if(i%13 == 0){
		 * m.setTipo("Contingencia"); }else{ m.setTipo("Caja-Curso-Día"); }
		 * result.add(m); }
		 * 
		 * for(int i = 301;i<390;i++){ MaterialDTO m = new MaterialDTO();
		 * m.setCodigo("1000000"+i); if(i%8 == 0){ m.setCurso("B"); }else{
		 * m.setCurso("A"); }
		 * m.setEstablecimiento("Colegio Altos de los andes"); if(i%3 == 0){
		 * m.setEtapa("Ministerio"); }else{ m.setEtapa("Centro"); }
		 * m.setIdCentro(place.getCentroId()); m.setId(i);
		 * m.setNivel("4 básico"); m.setRbd("8574"); if(i%11 == 0){
		 * m.setTipo("Contingencia"); }else{ m.setTipo("Caja-Curso-Día"); }
		 * result.add(m); } materialDataProvider.setList(wrap(result));
		 * 
		 * view.setMaterialSortHandler(new
		 * ListHandler<MaterialWrap>(materialDataProvider.getList()));
		 * extractLotes();
		 */
		// **********************SOLO TESTING**************************//

		if (Utils.hasPermisos(eventBus, getPermisos(), "MaterialService",
				"getMateriales")) {
			getFactory().getMaterialService().getMateriales(co.getId(),
					new SimceCallback<ArrayList<MaterialDTO>>(eventBus, true) {

						@Override
						public void success(ArrayList<MaterialDTO> result) {
							materialDataProvider.setList(wrap(result));
							view.setMaterialSortHandler(new ListHandler<MaterialWrap>(
									materialDataProvider.getList()));
							extractLotes();
						}
					});
		}
	}

	private void removeLoteOnClient(int loteId) {
		for (LoteDTO l : lotes) {
			if (l.getId() == loteId) {
				lotes.remove(l);
				break;
			}
		}
		for (MaterialWrap mw : materialDataProvider.getList()) {
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
			w.setMaterialUpToDate(true);
			wrap.add(w);
		}
		return wrap;
	}

	private void extractLotes() {
		if (lotes == null) {
			lotes = new ArrayList<LoteDTO>();
		}
		lotes.clear();
		for (MaterialWrap m : materialDataProvider.getList()) {
			if (m.getMaterial().getLote() != null
					&& !lotes.contains(m.getMaterial().getLote())) {
				lotes.add(m.getMaterial().getLote());
			}
		}
		view.setLotes(lotes);
	}

	private void updateMateriales(boolean blockThrows) {
		ArrayList<String> mList = new ArrayList<String>();

		for (MaterialWrap mwAux : outdated) {
			mList.add(mwAux.getMaterial().getCodigo());
		}
		if (Utils.hasPermisos(eventBus, getPermisos(), "MaterialService",
				"getMaterialesByCodigos")) {
			getFactory().getMaterialService().getMaterialesByCodigos(
					mList,
					new SimceCallback<ArrayList<MaterialDTO>>(eventBus, false,
							20000, !blockThrows) {

						@Override
						public void success(ArrayList<MaterialDTO> result) {
							intentos = 0;
							for (MaterialDTO m : result) {
								for (MaterialWrap mw : outdated) {
									if (mw.getMaterial().getCodigo()
											.equals(m.getCodigo())) {
										outdated.remove(mw);
										if (materialDataProvider.getList()
												.contains(mw)) {
											int index = materialDataProvider
													.getList().indexOf(mw);
											materialDataProvider.getList()
													.get(index).setMaterial(m);
											materialDataProvider.getList().set(
													index, mw);
										}
										if (ingresoDataProvider.getList()
												.contains(mw)) {
											int index = ingresoDataProvider
													.getList().indexOf(mw);
											ingresoDataProvider.getList()
													.get(index).setMaterial(m);
											ingresoDataProvider.getList().set(
													index, mw);
										}
										if (predespachoDataProvider.getList()
												.contains(mw)) {
											int index = predespachoDataProvider
													.getList().indexOf(mw);
											predespachoDataProvider.getList()
													.get(index).setMaterial(m);
											predespachoDataProvider.getList()
													.set(index, mw);
										}
										if (despachoDataProvider.getList()
												.contains(mw)) {
											int index = despachoDataProvider
													.getList().indexOf(mw);
											despachoDataProvider.getList()
													.get(index).setMaterial(m);
											despachoDataProvider.getList().set(
													index, mw);
										}
										break;
									}
								}
							}
						}

						@Override
						public void failure(Throwable caught) {

							if (outdated.isEmpty()) {
								return;
							}

							intentos++;
							if (intentos < 5) {
								Timer t = new Timer() {

									@Override
									public void run() {
										updateMateriales(true);
									}
								};
								t.schedule((int) (Math.pow(5, intentos) * 1000));
							} else {
								updateMateriales(false);
								Timer t = new Timer() {

									@Override
									public void run() {
										updateMateriales(false);
									}
								};
								t.schedule((int) (30000));
							}
						}
					});
		}
	}

}
