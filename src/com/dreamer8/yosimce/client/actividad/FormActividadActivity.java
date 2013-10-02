package com.dreamer8.yosimce.client.actividad;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.CursoSelector;
import com.dreamer8.yosimce.client.MensajeEvent;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.SimceCallback;
import com.dreamer8.yosimce.client.SimcePlace;
import com.dreamer8.yosimce.client.Utils;
import com.dreamer8.yosimce.client.actividad.ui.FormActividadView;
import com.dreamer8.yosimce.client.actividad.ui.FormActividadView.FormActividadPresenter;
import com.dreamer8.yosimce.shared.dto.ActividadDTO;
import com.dreamer8.yosimce.shared.dto.ContingenciaDTO;
import com.dreamer8.yosimce.shared.dto.DocumentoDTO;
import com.dreamer8.yosimce.shared.dto.EstadoAgendaDTO;
import com.dreamer8.yosimce.shared.dto.EvaluacionUsuarioDTO;
import com.dreamer8.yosimce.shared.dto.TipoContingenciaDTO;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.dreamer8.yosimce.shared.exceptions.ConsistencyException;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class FormActividadActivity extends SimceActivity implements
		FormActividadPresenter {

	private final FormActividadPlace place;
	private final FormActividadView view;
	
	private CursoSelector selector;
	private EventBus eventBus;
	
	private ArrayList<ContingenciaDTO> contingencias;
	private ArrayList<TipoContingenciaDTO> tipos;
	private ArrayList<EstadoAgendaDTO> estados;
	private ActividadDTO a;
	
	private ArrayList<EvaluacionUsuarioDTO> examinadores;
	
	public FormActividadActivity(ClientFactory factory, FormActividadPlace place,HashMap<String, ArrayList<String>> permisos) {
		super(factory, place, permisos);
		this.place = place;
		this.view = getFactory().getFormActividadView();
		this.view.setPresenter(this);
		contingencias = new ArrayList<ContingenciaDTO>();
		tipos = new ArrayList<TipoContingenciaDTO>();
	}
	
	@Override
	public void init(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view.asWidget());
		this.eventBus = eventBus;
		
		view.setSaveVisibility(Utils.hasPermisos(eventBus,getPermisos(),"ActividadService","actualizarActividad"));
		
		selector = new CursoSelector(getFactory(),eventBus);
		clear();
		selector.setOnCursoChangeAction(new Command() {
			
			@Override
			public void execute() {
				FormActividadPlace fap = new FormActividadPlace();
				fap.setIdCurso(selector.getSelectedCurso().getId());
				selector.hide();
				goTo(fap);
			}
		});
		
		view.showSeccionExaminador(Utils.hasPermisos(getPermisos(),"ActividadService","getEvaluacionExaminadores") &&
				Utils.hasPermisos(getPermisos(),"ActividadService","updateEvaluacionExaminadores"));
		
		view.showForm(true);
		
		if(place.getIdCurso()<0){
			selector.setOnCancelAction(new Command() {
				
				@Override
				public void execute() {
					//goTo(new ActividadPlace());
					goTo(new SimcePlace());
				}
			});
			selector.show();
		}else{
			
			if(Utils.hasPermisos(eventBus,getPermisos(),"ActividadService","getTiposContingencia")){
				view.enableAddContingencia(true);
				getFactory().getActividadService().getTiposContingencia(place.getIdCurso(), new SimceCallback<ArrayList<TipoContingenciaDTO>>(eventBus,true) {
	
					@Override
					public void success(ArrayList<TipoContingenciaDTO> result) {
						tipos.clear();
						tipos.addAll(result);
						view.setTiposContingencia(tipos);
					}
				});
			}else{
				view.enableAddContingencia(false);
			}
			if(Utils.hasPermisos(getPermisos(),"ActividadService","getEvaluacionExaminadores")){
				getFactory().getActividadService().getEvaluacionExaminadores(place.getIdCurso(), new SimceCallback<ArrayList<EvaluacionUsuarioDTO>>(eventBus,true) {

					@Override
					public void success(ArrayList<EvaluacionUsuarioDTO> result) {
						examinadores = new ArrayList<EvaluacionUsuarioDTO>();
						for(EvaluacionUsuarioDTO u:result){
							EvaluacionUsuarioDTO aux = new EvaluacionUsuarioDTO();
							aux.setUsuario(u.getUsuario());
							aux.setFormulario(u.getFormulario());
							aux.setGeneral(u.getGeneral());
							aux.setPresentacionPersonal(u.getPresentacionPersonal());
							aux.setPuntualidad(u.getPuntualidad());
							aux.setEstado(EvaluacionUsuarioDTO.ESTADO_REMPLAZADO);
							u.setEstado(EvaluacionUsuarioDTO.ESTADO_TITULAR);
							examinadores.add(aux);
						}
						view.setExaminadores(result);
					}

				});
			}
			if(Utils.hasPermisos(eventBus,getPermisos(),"ActividadService","getActividad")){
				getFactory().getActividadService().getActividad(place.getIdCurso(), new SimceCallback<ActividadDTO>(eventBus,true) {

					@Override
					public void success(ActividadDTO result) {
						a = result;
						setActividad();
					}
					
					@Override
					public void failure(Throwable caught) {
						if(caught instanceof ConsistencyException){
							History.back();
						}
					}
				});
			}
			if(Utils.hasPermisos(eventBus,getPermisos(),"ActividadService","getEstadosActividad")){
				getFactory().getActividadService().getEstadosActividad(new SimceCallback<ArrayList<EstadoAgendaDTO>>(eventBus,true) {

					@Override
					public void success(ArrayList<EstadoAgendaDTO> result) {
						estados = result;
						if(a != null){
							if(!estados.contains(a.getEstadoAplicacion())){
								estados.add(a.getEstadoAplicacion());
							}
							view.setEstados(result);
							view.selectEstado(a.getEstadoAplicacion());
							onEstadoChange(a.getEstadoAplicacion().getId());
						}else{
							view.setEstados(result);
						}
					}
				});
			}
			
		}
		
	}

	@Override
	public void onCambiarCursoClick() {
		selector.show();
	}
	
	@Override
	public void onDocumentoUploaded(String documento) {
		eventBus.fireEvent(new MensajeEvent("El documento "+documento+" se a subido correctamente",MensajeEvent.MSG_OK,true));
		DocumentoDTO d = new DocumentoDTO();
		d.setName(documento);
		d.setUrl("");
		view.setHyperlink(d);
	}

	@Override
	public void guardarFormulario() {
		if(view.isUploading()){
			eventBus.fireEvent(new MensajeEvent("Espere a que se termine la carga del documento antes de guardar el formulario",MensajeEvent.MSG_WARNING,false));
			return;
		}
		
		a.setInicioActividad(view.getInicioActividad());
		a.setInicioPrueba(view.getInicioPrueba());
		a.setTerminoPrueba(view.getTerminoPrueba());
		a.setAlumnosTotal(view.getTotalAlumnos());
		a.setAlumnosAusentes(view.getAlumnosAusentes());
		a.setAlumnosDs(view.getAlumnosDS());
		a.setTotalCuestionarios(view.getCuestionariosTotales());
		a.setCuestionariosEntregados(view.getCuestionariosEntregados());
		a.setCuestionariosRecibidos(view.getCuestionariosRecibidos());
		a.setMaterialContingencia(view.getUsoMaterialContingencia());
		a.setDetalleUsoMaterialContingencia(view.getDetalleUsoMaterialContingencia());
		a.setEvaluacionProcedimientos(view.getEvaluacionGeneral());
		a.setContingencias(contingencias);
		
		if(view.isFileUploaded()){
			DocumentoDTO d = new DocumentoDTO();
			d.setName(view.getUploadFile());
			a.setDocumento(d);
			
		}
		if(Utils.hasPermisos(eventBus,getPermisos(),"ActividadService","actualizarActividad")){
			getFactory().getActividadService().actualizarActividad(a, new SimceCallback<Boolean>(eventBus,false) {
	
				@Override
				public void success(Boolean result) {
					eventBus.fireEvent(new MensajeEvent("La actividad se ha guardado exitosamente",MensajeEvent.MSG_OK,true));
				}
			});
		}
		
		if(Utils.hasPermisos(eventBus,getPermisos(),"ActividadService","updateEvaluacionExaminadores")){
			ArrayList<EvaluacionUsuarioDTO> examinadoresCorregido = view.getExaminadores();
			
			for(EvaluacionUsuarioDTO e:examinadoresCorregido){
				if(e.getEstado()!=EvaluacionUsuarioDTO.ESTADO_REMPLAZADO){
					e.setEstado(EvaluacionUsuarioDTO.ESTADO_REMPLAZANTE);
					for(EvaluacionUsuarioDTO x:examinadores){
						if(e.getUsuario().getId() == x.getUsuario().getId()){
							e.setEstado(EvaluacionUsuarioDTO.ESTADO_TITULAR);
							x.setEstado(EvaluacionUsuarioDTO.ESTADO_TITULAR);
							break;
						}
					}
				}else{
					examinadoresCorregido.remove(e);
				}
			}
			
			for(EvaluacionUsuarioDTO x:examinadores){
				if(x.getEstado() == EvaluacionUsuarioDTO.ESTADO_REMPLAZADO){
					examinadoresCorregido.add(x);
				}
			}
			getFactory().getActividadService().updateEvaluacionExaminadores(place.getIdCurso(),examinadoresCorregido, new SimceCallback<Boolean>(eventBus,false) {
	
				@Override
				public void success(Boolean result) {
					eventBus.fireEvent(new MensajeEvent("La evaluación de los examinadores se ha guardado exitosamente",MensajeEvent.MSG_OK,true));
				}
			});
		}
	}

	@Override
	public void getExaminadoresSuplentes(String search) {

		if(Utils.hasPermisos(eventBus,getPermisos(),"ActividadService","getExaminadores")){
			getFactory().getActividadService().getExaminadores(search, new SimceCallback<ArrayList<UserDTO>>(eventBus,false) {
	
				@Override
				public void success(ArrayList<UserDTO> result) {
					view.setExaminadoresSuplentes(result);
				}
			});
		}
	}
	
	@Override
	public void onAgregarContingencia(ContingenciaDTO contingencia) {
		if(Utils.hasPermisos(eventBus,getPermisos(),"ActividadService","actualizarActividad")){
			tipos.remove(contingencia.getTipoContingencia());
			contingencias.add(contingencia);
			view.setTiposContingencia(tipos);
			view.setContingencias(contingencias);
		}
	}

	@Override
	public void onRemoveContingecia(ContingenciaDTO contingencia) {
		if(Utils.hasPermisos(eventBus,getPermisos(),"ActividadService","actualizarActividad")){
			contingencias.remove(contingencia);
			tipos.add(contingencia.getTipoContingencia());
			view.setTiposContingencia(tipos);
			view.setContingencias(contingencias);
		}
	}
	
	@Override
	public void onEstadoChange(Integer estadoId) {
		EstadoAgendaDTO selected = null;
		for(EstadoAgendaDTO estado:estados){
			if(estado.getId() == estadoId){
				selected = estado;
				break;
			}
		}
		a.setEstadoAplicacion(selected);
		view.showForm(selected.getEstado().equals(EstadoAgendaDTO.REALIZADA));
	}
	
	@Override
	public void onStop() {
		clear();
		super.onStop();
	}
	
	@SuppressWarnings("deprecation")
	private void setActividad(){
		view.setNombreEstablecimiento((a.getNombreEstablecimiento()!=null)?a.getNombreEstablecimiento():"");
		view.setRbd((a.getRbd()!=null)?a.getRbd():"");
		view.setCurso((a.getCurso()!=null)?a.getCurso():"");
		view.setTipoCurso((a.getTipoEstablecimiento()!=null)?a.getTipoEstablecimiento():"");
		view.setRegion((a.getRegion()!=null)?a.getRegion():"");
		view.setComuna((a.getComuna()!=null)?a.getComuna():"");
		if(estados == null && a.getEstadoAplicacion() != null){
			estados = new ArrayList<EstadoAgendaDTO>();
			estados.add(a.getEstadoAplicacion());
		}else if(a.getEstadoAplicacion()!=null && !estados.contains(a.getEstadoAplicacion())){
			estados.add(a.getEstadoAplicacion());
		}else if(estados == null && a.getEstadoAplicacion() == null){
			estados = new ArrayList<EstadoAgendaDTO>();
		}
		view.setEstados(estados);
		if(a.getEstadoAplicacion()!=null){
			view.selectEstado(a.getEstadoAplicacion());
			onEstadoChange(a.getEstadoAplicacion().getId());
		}
		this.contingencias =a.getContingencias();
		if(tipos != null && contingencias != null && !contingencias.isEmpty()){
			for(ContingenciaDTO c: contingencias){
				if(tipos.contains(c.getTipoContingencia())){
					tipos.remove(c.getTipoContingencia());
				}
			}
			view.setTiposContingencia(tipos);
		}
		if(a.getContingencias() == null){
			a.setContingencias( new ArrayList<ContingenciaDTO>());
		}
		view.setContingencias(a.getContingencias());
		Date b = new Date();
		view.setInicioActividad((a.getInicioActividad()!=null)?a.getInicioActividad():new Date(b.getYear(), b.getMonth(), b.getDate(), 8, 0));
		view.setInicioPrueba((a.getInicioPrueba()!=null)?a.getInicioPrueba():new Date(b.getYear(), b.getMonth(), b.getDate(), 8, 0));
		view.setTerminoPrueba((a.getTerminoPrueba()!=null)?a.getTerminoPrueba():new Date(b.getYear(), b.getMonth(), b.getDate(), 8, 0));
		if(a.getAlumnosTotal()!=null){view.setTotalAlumnos(a.getAlumnosTotal());}
		if(a.getAlumnosAusentes()!=null){view.setAlumnosAusentes(a.getAlumnosAusentes());}
		if(a.getAlumnosDs()!=null){view.setAlumnosDS(a.getAlumnosDs());}
		if(a.getTotalCuestionarios()!=null){view.setCuestionariosTotales(a.getTotalCuestionarios());}
		if(a.getCuestionariosEntregados()!=null){view.setCuestionariosEntregados(a.getCuestionariosEntregados());}
		if(a.getCuestionariosRecibidos()!=null){view.setCuestionariosRecibidos(a.getCuestionariosRecibidos());}
		if(a.getMaterialContingencia()!=null){view.setUsoMaterialContingencia(a.getMaterialContingencia());}
		if(a.getDetalleUsoMaterialContingencia()!=null){view.setDetalleUsoMaterialContingencia(a.getDetalleUsoMaterialContingencia());}
		if(a.getEvaluacionProcedimientos()!=null){view.setEvaluacionGeneral(a.getEvaluacionProcedimientos());}
		view.setHyperlink(a.getDocumento());
		
		//***************Simce TIC***********************
		view.setTotalAlumnosEnabled(place.getAplicacionId() != 2);
		view.setAlumnosDSEnabled(place.getAplicacionId() != 2);
		view.showUsoMaterialComplementarioPanel(place.getAplicacionId() != 2);
		
		view.setCuestionariosTotalesEnabled(!(place.getAplicacionId() == 2 && place.getTipoId() == 2));
		view.setCuestionariosEntregadosEnabled(!(place.getAplicacionId() == 2 && place.getTipoId() == 2));
		
		
		//***************Simce TIC***********************
		
		
		onEstadoChange(a.getEstadoAplicacion().getId());
	}
	
	@SuppressWarnings("deprecation")
	private void clear(){
		contingencias.clear();
		Date b = new Date();
		view.setContingencias(contingencias);
		view.setInicioActividad(new Date(b.getYear(), b.getMonth(), b.getDate(), 8, 0));
		view.setInicioPrueba(new Date(b.getYear(), b.getMonth(), b.getDate(), 8, 0));
		view.setTerminoPrueba(new Date(b.getYear(), b.getMonth(), b.getDate(), 8, 0));;
		view.setTotalAlumnos(0);
		view.setAlumnosAusentes(0);
		view.setAlumnosDS(0);
		view.setCuestionariosTotales(0);
		view.setCuestionariosEntregados(0);
		view.setCuestionariosRecibidos(0);
		view.setUsoMaterialContingencia(false);
		view.setDetalleUsoMaterialContingencia("");
		view.setEvaluacionGeneral(0);
		view.setHyperlink(null);
		a = null;
		view.showForm(false);
	}
}
