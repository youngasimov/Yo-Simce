package com.dreamer8.yosimce.client.planificacion;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.SimceCallback;
import com.dreamer8.yosimce.client.Utils;
import com.dreamer8.yosimce.client.planificacion.ui.AgendamientosView;
import com.dreamer8.yosimce.client.planificacion.ui.AgendamientosView.AgendamientosPresenter;
import com.dreamer8.yosimce.shared.dto.ActividadTipoDTO;
import com.dreamer8.yosimce.shared.dto.AgendaPreviewDTO;
import com.dreamer8.yosimce.shared.dto.ContactoDTO;
import com.dreamer8.yosimce.shared.dto.DetalleCursoDTO;
import com.dreamer8.yosimce.shared.dto.DocumentoDTO;
import com.dreamer8.yosimce.shared.dto.EstadoAgendaDTO;
import com.dreamer8.yosimce.shared.dto.SectorDTO;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.view.client.Range;

public class AgendamientosActivity extends SimceActivity implements
		AgendamientosPresenter {

	private final AgendamientosPlace place;
	private AgendamientosView view;
	private EventBus eventBus;
	private HashMap<String,String> filtros;
	
	private ArrayList<SectorDTO> regiones;
	private HashMap<Integer,ArrayList<SectorDTO>> comunas;
	private ArrayList<EstadoAgendaDTO> estados;
	
	private boolean estadosReady;
	private boolean regionesReady;
	
	
	private Range range;
	
	public AgendamientosActivity(ClientFactory factory,AgendamientosPlace place,HashMap<String, ArrayList<String>> permisos) {
		super(factory,place,permisos);
		this.place = place;
		view = factory.getAgendamientosView();
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
		
		view.setNombreEstablecimiento("");
		view.setRbd("");
		view.setRegion("");
		view.setComuna("");
		view.setCurso("");
		view.setTipo("");
		view.setCentroOperacion("");
		view.setSupervisor(null);
		view.setDirector("");
		view.setExaminadores(new ArrayList<UserDTO>());
		view.setEmailDirector("");
		view.setTelefonoDirector("");
		view.setContacto("");
		view.setCargoContacto("");
		view.setEmailContacto("");
		view.setTelefonoContacto("");
		view.setAddress("");
		filtros.clear();
		regiones.clear();
		comunas.clear();
		estados.clear();
		estadosReady = false;
		regionesReady = false;
		range = view.getDataDisplay().getVisibleRange();
		
		view.setExportarVisivility(Utils.hasPermisos(getPermisos(),"PlanificacionService","getDocumentoPreviewAgendamientos"));
		view.setModificarAgendaVisivility(
				Utils.hasPermisos(getPermisos(),"PlanificacionService","getAgendaCurso") &&
				Utils.hasPermisos(getPermisos(),"PlanificacionService","AgendarVisita") &&
				Utils.hasPermisos(getPermisos(),"PlanificacionService","getEstadosAgenda"));
		view.setDetallesAgendaVisivility(Utils.hasPermisos(getPermisos(),"PlanificacionService","getAgendaCurso"));
		view.setInformacionGeneralVisivility(Utils.hasPermisos(getPermisos(),"GeneralService","getDetalleCurso"));
		
		getFactory().getLoginService().getActividadTipos(new SimceCallback<ArrayList<ActividadTipoDTO>>(eventBus,false) {

			@Override
			public void success(ArrayList<ActividadTipoDTO> result) {
				view.setTiposActividad(result);
			}
		});
		
		if(Utils.hasPermisos(eventBus,getPermisos(),"GeneralService","getRegiones")){
			getFactory().getGeneralService().getRegiones(new SimceCallback<ArrayList<SectorDTO>>(eventBus,false) {
	
				@Override
				public void success(ArrayList<SectorDTO> result) {
					regiones.addAll(result);
					view.setRegiones(regiones);
					regionesReady = true;
					if(estadosReady){
						updateFiltros();
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
		
		if(Utils.hasPermisos(eventBus,getPermisos(),"PlanificacionService","getEstadosAgendaFiltro")){
			getFactory().getPlanificacionService().getEstadosAgendaFiltro(new SimceCallback<ArrayList<EstadoAgendaDTO>>(eventBus,false) {
	
				@Override
				public void success(ArrayList<EstadoAgendaDTO> result) {
					estados = result;
					view.setEstados(estados);
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
		if(Utils.hasPermisos(eventBus,getPermisos(),"PlanificacionService","getDocumentoPreviewAgendamientos")){
			getFactory().getPlanificacionService().getDocumentoPreviewAgendamientos(filtros, new SimceCallback<DocumentoDTO>(eventBus,true,0) {
	
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
		if(Utils.hasPermisos(eventBus,getPermisos(),"PlanificacionService","getPreviewAgendamientos")){
			getFactory().getPlanificacionService().getPreviewAgendamientos(range.getStart(), range.getLength(), filtros, new SimceCallback<ArrayList<AgendaPreviewDTO>>(eventBus,false,60000) {
	
				@Override
				public void success(ArrayList<AgendaPreviewDTO> result) {
					view.getDataDisplay().setRowData(range.getStart(), result);
				}
				
				@Override
				public void failure(Throwable caught) {
					view.getDataDisplay().setRowCount(0);
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
			if(Utils.hasPermisos(eventBus,getPermisos(),"GeneralService","getComunas")){
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
		view.getDataDisplay().setRowData(0, new ArrayList<AgendaPreviewDTO>());
		view.clear();
	}
	
	private void updateFiltros(){
		DateTimeFormat df = DateTimeFormat.getFormat("yyyy-MM-dd");
		filtros.clear();
		if(place.getDesdeTimestamp()!=-1){
			Date d = new Date(place.getDesdeTimestamp());
			filtros.put(PlanificacionService.FKEY_DESDE, df.format(d));
			//Window.alert(df.format(d));
			view.setDesde(d);
		}else{
			view.setDesde(null);
		}
		if(place.getHastaTimestamp()!=-1){
			Date d = new Date(place.getHastaTimestamp());
			filtros.put(PlanificacionService.FKEY_HASTA, df.format(d));
			view.setHasta(d);
		}else{
			view.setHasta(null);
		}
		if(place.getRegionId()!=-1){
			filtros.put(PlanificacionService.FKEY_REGION, place.getRegionId()+"");
			view.setSelectedRegion(place.getRegionId());
			onRegionChange(place.getRegionId());
		}
		if(place.getComunaId()!=-1){
			filtros.put(PlanificacionService.FKEY_COMUNA, place.getComunaId()+"");
		}
		if(place.getEstadosSeleccionados().size()>0){
			view.setSelectedEstados(place.getEstadosSeleccionados());
			StringBuilder b = new StringBuilder();
			for(Integer id:place.getEstadosSeleccionados()){
				b.append(id);
				b.append(PlanificacionService.SEPARATOR);
			}
			b.deleteCharAt(b.length()-1);
			filtros.put(PlanificacionService.FKEY_ESTADOS, b.toString());
		}
		if(Utils.hasPermisos(eventBus,getPermisos(),"PlanificacionService","getTotalPreviewAgendamientos")){
			getFactory().getPlanificacionService().getTotalPreviewAgendamientos(filtros, new SimceCallback<Integer>(eventBus,false) {
	
				@Override
				public void success(Integer result) {
					view.getDataDisplay().setRowCount(result,true);
				}
			});
		}
		if(Utils.hasPermisos(eventBus,getPermisos(),"PlanificacionService","getPreviewAgendamientos")){
			getFactory().getPlanificacionService().getPreviewAgendamientos(range.getStart(), range.getLength(), filtros, new SimceCallback<ArrayList<AgendaPreviewDTO>>(eventBus,false) {
	
				@Override
				public void success(ArrayList<AgendaPreviewDTO> result) {
					view.getDataDisplay().setRowData(range.getStart(), result);
				}
			});
		}
	}

	@Override
	public void onModificarAgendaClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEditarContacto(ContactoDTO contacto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEditarDirector(ContactoDTO contacto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFechaChange(Date d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCursoClick(AgendaPreviewDTO agendaPreview) {
		if(getPermisos().get("GeneralService").contains("getDetalleCurso")){
			getFactory().getGeneralService().getDetalleCurso(agendaPreview.getCursoId(), view.getSelectedTipoActividad(), new SimceCallback<DetalleCursoDTO>(eventBus) {

				@Override
				public void success(DetalleCursoDTO r) {
					view.setNombreEstablecimiento(r.getEstablecimiento());
					view.setRbd(r.getRbd());
					view.setRegion(r.getRegion());
					view.setComuna(r.getComuna());
					view.setCurso(r.getCurso());
					view.setTipo(r.getTipoEstablecimiento());
					view.setCentroOperacion(r.getCentro());
					if(r.getSupervisor()!=null){
						view.setSupervisor(r.getSupervisor());
					}
					if (r.getExaminadores() != null && !r.getExaminadores().isEmpty()) {
						view.setExaminadores(r.getExaminadores());
					}
					
					view.setDirector(r.getNombreContacto());
					view.setEmailDirector(r.getEmailDirector());
					view.setTelefonoDirector(r.getTelefonoContacto());
					view.setContacto(r.getNombreContacto());
					view.setCargoContacto(r.getCargoContacto());
					view.setEmailContacto(r.getEmailContacto());
					view.setTelefonoContacto(r.getTelefonoContacto());
					view.setAddress(r.getDireccion());
				}
				
			});
		}
	}
}
