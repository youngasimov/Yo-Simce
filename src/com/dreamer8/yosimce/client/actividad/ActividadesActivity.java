package com.dreamer8.yosimce.client.actividad;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.MensajeEvent;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.SimceCallback;
import com.dreamer8.yosimce.client.Utils;
import com.dreamer8.yosimce.client.actividad.ui.ActividadesView;
import com.dreamer8.yosimce.client.actividad.ui.ActividadesView.ActividadesPresenter;
import com.dreamer8.yosimce.shared.dto.ActividadPreviewDTO;
import com.dreamer8.yosimce.shared.dto.DocumentoDTO;
import com.dreamer8.yosimce.shared.dto.EstadoAgendaDTO;
import com.dreamer8.yosimce.shared.dto.SectorDTO;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.view.client.Range;

public class ActividadesActivity extends SimceActivity implements
		ActividadesPresenter {

	private final ActividadesPlace place;
	private final ActividadesView view;
	private EventBus eventBus;
	private HashMap<String,String> filtros;
	
	private ArrayList<SectorDTO> regiones;
	private HashMap<Integer,ArrayList<SectorDTO>> comunas;
	private ArrayList<EstadoAgendaDTO> estados;
	private boolean estadosReady;
	private boolean regionesReady;
	
	private int total = 0;
	
	private Range range;
	
	public ActividadesActivity(ClientFactory factory, ActividadesPlace place,HashMap<String, ArrayList<String>> permisos) {
		super(factory, place, permisos);
		this.place = place;
		view = getFactory().getActividadesView();
		view.setPresenter(this);
		
		filtros = new HashMap<String, String>();
		regiones = new ArrayList<SectorDTO>();
		comunas = new HashMap<Integer, ArrayList<SectorDTO>>();
		estados = new ArrayList<EstadoAgendaDTO>();
		estadosReady = false;
		regionesReady = false;
	}
	
	@Override
	public void init(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view.asWidget());
		this.eventBus = eventBus;
		filtros.clear();
		regiones.clear();
		comunas.clear();
		estados.clear();
		estadosReady = false;
		regionesReady = false;
		range = view.getDataDisplay().getVisibleRange();
		view.setExportarActividadesVisivility(Utils.hasPermisos(getPermisos(), "ActividadService", "getDocumentoPreviewActividades"));
		view.setExportarAlumnosVisivility(Utils.hasPermisos(getPermisos(), "ActividadService", "getDocumentoAlumnos"));
		
		view.setSincronizacionVisivility(Utils.hasPermisos(getPermisos(), "ActividadService", "getSincronizacionesCurso"));
		view.setFormularioVisivility(Utils.hasPermisos(getPermisos(), "ActividadService", "getActividad"));
		view.setInformacionVisivility(Utils.hasPermisos(getPermisos(), "GeneralService","getDetalleCurso"));
		
		if(Utils.hasPermisos(eventBus,getPermisos(), "GeneralService", "getRegiones")){
			getFactory().getGeneralService().getRegiones(new SimceCallback<ArrayList<SectorDTO>>(eventBus,false) {
	
				@Override
				public void success(ArrayList<SectorDTO> result) {
					regiones.addAll(result);
					view.setRegiones(regiones);
					regionesReady = true;
					if(estadosReady){
						updateFiltros();
						getActividades();
					}
				}
				
				@Override
				public void failure(Throwable caught) {
					view.getDataDisplay().setRowCount(0);
				}
			});
		}else{
			view.getDataDisplay().setRowCount(0);
		}
		
		if(Utils.hasPermisos(eventBus,getPermisos(), "ActividadService", "getEstadosActividadFiltro")){
			getFactory().getActividadService().getEstadosActividadFiltro(new SimceCallback<ArrayList<EstadoAgendaDTO>>(eventBus,false) {

				@Override
				public void success(ArrayList<EstadoAgendaDTO> result) {
					estados = result;
					view.setEstadosActividad(estados);
					if(place.getEstadosSeleccionados().isEmpty()){
						ArrayList<Integer> x = new ArrayList<Integer>();
						for(EstadoAgendaDTO eadto:result){
							x.add(eadto.getId());
						}
						place.setEstadosSeleccionados(x);
					}
					estadosReady = true;
					if(regionesReady){
						updateFiltros();
						getActividades();
					}
				}
				
				@Override
				public void failure(Throwable caught) {
					view.getDataDisplay().setRowCount(0);
				}
			});
		}else{
			view.getDataDisplay().setRowCount(0);
		}
		
	}

	@Override
	public void onExportarClick() {
		if(Utils.hasPermisos(eventBus,getPermisos(), "ActividadService", "getDocumentoPreviewActividades")){
			getFactory().getActividadService().getDocumentoPreviewActividades(filtros,new SimceCallback<DocumentoDTO>(eventBus,true) {
	
				@Override
				public void success(DocumentoDTO result) {
					Window.open(result.getUrl(), "_blank", "");
				}
			});
		}
	}
	
	@Override
	public void onExportarAlumnosClick() {
		
		if(total > 200 && place.getRegionId() == -1){
			eventBus.fireEvent(new MensajeEvent("La lista seleccionada es muy grande para descargarla completamente, filtre por atributo y vuelva a intentarlo",MensajeEvent.MSG_WARNING,false));
			return;
		}
		
		if(Utils.hasPermisos(eventBus,getPermisos(), "ActividadService", "getDocumentoAlumnos")){
			getFactory().getActividadService().getDocumentoAlumnos(filtros, new SimceCallback<DocumentoDTO>(eventBus,true) {
	
				@Override
				public void success(DocumentoDTO result) {
					Window.open(result.getUrl(), "_blank", "");
				}
			});
		}
	}
	

	@Override
	public void onCancelarFiltroClick() {
		updateFiltros();
	}

	@Override
	public void onRangeChange(Range r) {
		this.range = r;
		if(Utils.hasPermisos(eventBus,getPermisos(), "ActividadService", "getPreviewActividades")){
			getFactory().getActividadService().getPreviewActividades(range.getStart(), range.getLength(), filtros, new SimceCallback<ArrayList<ActividadPreviewDTO>>(eventBus,false) {
	
				@Override
				public void success(ArrayList<ActividadPreviewDTO> result) {
					view.getDataDisplay().setRowData(range.getStart(), result);
				}
			});
		}
	}
	
	@Override
	public void onRegionChange(final int regionId) {
		if(regionId == -1){
			view.setComunas(new ArrayList<SectorDTO>());
			return;
		}
		if(!comunas.containsKey(regionId)){
			SectorDTO region = null;
			for(SectorDTO r:regiones){
				if(r.getIdSector() == regionId){
					region = r;
					break;
				}
			}
			if(Utils.hasPermisos(eventBus,getPermisos(), "GeneralService", "getComunas")){
				getFactory().getGeneralService().getComunas(region, new SimceCallback<ArrayList<SectorDTO>>(eventBus,false) {
		
					@Override
					public void success(ArrayList<SectorDTO> result) {
						comunas.put(regionId, result);
						view.setComunas(result);
						if(place.getComunaId()!=-1){
							view.setSelectedComuna(place.getComunaId());
						}
					}
				});
			}
		}else{
			view.setComunas(comunas.get(regionId));
			if(place.getComunaId()!=-1){
				view.setSelectedComuna(place.getComunaId());
			}
		}
	}
	
	@Override
	public void onStop() {
		super.onStop();
		filtros.clear();
		regiones.clear();
		comunas.clear();
		estados.clear();
		estadosReady = false;
		regionesReady = false;
		view.getDataDisplay().setRowCount(0);
		view.getDataDisplay().setRowData(0, new ArrayList<ActividadPreviewDTO>());
		view.clear();
	}
	
	private void getActividades(){
		if(Utils.hasPermisos(eventBus,getPermisos(), "ActividadService", "getTotalPreviewActividades")){
			getFactory().getActividadService().getTotalPreviewActividades(filtros, new SimceCallback<Integer>(eventBus,false) {
	
				@Override
				public void success(Integer result) {
					total = result;
					view.getDataDisplay().setRowCount(result,true);
				}
			});
		}
		if(Utils.hasPermisos(eventBus,getPermisos(), "ActividadService", "getPreviewActividades")){
			getFactory().getActividadService().getPreviewActividades(range.getStart(), range.getLength(), filtros, new SimceCallback<ArrayList<ActividadPreviewDTO>>(eventBus,false) {
	
				@Override
				public void success(ArrayList<ActividadPreviewDTO> result) {
					view.getDataDisplay().setRowData(range.getStart(), result);
				}
			});
		}
	}
	
	private void updateFiltros(){
		
		filtros.clear();
		
		if(place.isActividadesContintencia()){
			filtros.put(ActividadService.FKEY_ACTIVIDADES_CONTINGENCIA, "1");
		}
		view.setActividadesContingencia(place.isActividadesContintencia());
		
		if(place.isActividadesContintenciaInhabilitante()){
			filtros.put(ActividadService.FKEY_ACTIVIDADES_CONTINGENCIA_INHABILITANTE, "1");
		}
		view.setActividadesContingenciaInhabilitante(place.isActividadesContintenciaInhabilitante());
		
		if(place.isActividadesMaterialContintencia()){
			filtros.put(ActividadService.FKEY_ACTIVIDADES_MATERIAL_CONTINGENCIA,"1");
		}
		view.setActividadesMaterialContingencia(place.isActividadesMaterialContintencia());
		
		if(place.isActividadesSincronizadas()){
			filtros.put(ActividadService.FKEY_ACTIVIDADES_SINCRONIZADAS, "1");
		}
		view.setActividadesSincronizadas(place.isActividadesSincronizadas());
		
		if(place.isActividadesParcialmenteSincronizadas()){
			filtros.put(ActividadService.FKEY_ACTIVIDADES_PARCIALMENTE_SINCRONIZADAS, "1");
		}
		view.setActividadesParcialementeSincronizadas(place.isActividadesParcialmenteSincronizadas());
		
		if(place.isActividadesNoSincronizadas()){
			filtros.put(ActividadService.FKEY_ACTIVIDADES_NO_SINCRONIZADAS, "1");
		}
		view.setActividadesNoSincronizadas(place.isActividadesNoSincronizadas());
		
		if(place.getRegionId()!=-1){
			filtros.put(ActividadService.FKEY_REGION, place.getRegionId()+"");
			view.setSelectedRegion(place.getRegionId());
			onRegionChange(place.getRegionId());
		}
		if(place.getComunaId()!=-1){
			filtros.put(ActividadService.FKEY_COMUNA, place.getComunaId()+"");
		}
		if(place.getEstadosSeleccionados().size()>0){
			view.setSelectedEstados(place.getEstadosSeleccionados());
			StringBuilder b = new StringBuilder();
			for(Integer id:place.getEstadosSeleccionados()){
				b.append(id);
				b.append(ActividadService.SEPARATOR);
			}
			b.deleteCharAt(b.length()-1);
			filtros.put(ActividadService.FKEY_ESTADOS, b.toString());
		}
		
	}
}
