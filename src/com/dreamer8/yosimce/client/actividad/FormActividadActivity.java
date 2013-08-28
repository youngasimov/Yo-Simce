package com.dreamer8.yosimce.client.actividad;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.CursoSelector;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.SimceCallback;
import com.dreamer8.yosimce.client.actividad.ui.FormActividadView;
import com.dreamer8.yosimce.client.actividad.ui.FormActividadView.FormActividadPresenter;
import com.dreamer8.yosimce.shared.dto.ActividadDTO;
import com.dreamer8.yosimce.shared.dto.ContingenciaDTO;
import com.dreamer8.yosimce.shared.dto.EstadoAgendaDTO;
import com.dreamer8.yosimce.shared.dto.EvaluacionUsuarioDTO;
import com.dreamer8.yosimce.shared.dto.TipoContingenciaDTO;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Command;
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
		
		view.showSeccionExaminador(getPermisos().get("ActividadService").contains("getEvaluacionExaminadores") &&
			getPermisos().get("ActividadService").contains("updateEvaluacionExaminadores"));
		
		view.showForm(true);
		
		if(place.getIdCurso()<0){
			selector.setOnCancelAction(new Command() {
				
				@Override
				public void execute() {
					goTo(new ActividadPlace());
				}
			});
			selector.show();
		}else{
			
			if(!getPermisos().get("ActividadService").contains("getTiposContingencia")){
				view.enableAddContingencia(false);
			}else{
				view.enableAddContingencia(true);
				getFactory().getActividadService().getTiposContingencia(place.getIdCurso(), new SimceCallback<ArrayList<TipoContingenciaDTO>>(eventBus) {
	
					@Override
					public void success(ArrayList<TipoContingenciaDTO> result) {
						tipos.clear();
						tipos.addAll(result);
						view.setTiposContingencia(tipos);
					}
				});
			}
			if(getPermisos().get("ActividadService").contains("getEvaluacionExaminadores")){
				getFactory().getActividadService().getEvaluacionExaminadores(place.getIdCurso(), new SimceCallback<ArrayList<EvaluacionUsuarioDTO>>(eventBus) {

					@Override
					public void success(ArrayList<EvaluacionUsuarioDTO> result) {
						view.setExaminadores(result);
					}

				});
			}
			if(getPermisos().get("ActividadService").contains("getActividad")){
				getFactory().getActividadService().getActividad(place.getIdCurso(), new SimceCallback<ActividadDTO>(eventBus) {

					@Override
					public void success(ActividadDTO result) {
						a = result;
						setActividad();
					}
				});
			}
			if(getPermisos().get("ActividadService").contains("getEstadosActividad")){
				getFactory().getActividadService().getEstadosActividad(new SimceCallback<ArrayList<EstadoAgendaDTO>>(eventBus) {

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
	public void guardarFormulario() {
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
		
		if(getPermisos().get("ActividadService").contains("actualizarActividad")){
			getFactory().getActividadService().actualizarActividad(a, new SimceCallback<Boolean>(eventBus) {
	
				@Override
				public void success(Boolean result) {
					
				}
			});
		}
		getFactory().getActividadService().updateEvaluacionExaminadores(place.getIdCurso(),view.getExaminadores(), new SimceCallback<Boolean>(eventBus) {

			@Override
			public void success(Boolean result) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	public void getExaminadoresSuplentes(String search) {

		if(getPermisos().get("ActividadService").contains("getExaminadores")){
			getFactory().getActividadService().getExaminadores(search, new SimceCallback<ArrayList<UserDTO>>(eventBus) {
	
				@Override
				public void success(ArrayList<UserDTO> result) {
					view.setExaminadoresSuplentes(result);
				}
			});
		}
	}
	
	@Override
	public void onAgregarContingencia(ContingenciaDTO contingencia) {
		if(!getPermisos().get("ActividadService").contains("getTiposContingencia")){
			return;
		}
		tipos.remove(contingencia.getTipoContingencia());
		contingencias.add(contingencia);
		view.setTiposContingencia(tipos);
		view.setContingencias(contingencias);
	}

	@Override
	public void onRemoveContingecia(ContingenciaDTO contingencia) {
		if(!getPermisos().get("ActividadService").contains("getTiposContingencia")){
			return;
		}
		contingencias.remove(contingencia);
		tipos.add(contingencia.getTipoContingencia());
		view.setTiposContingencia(tipos);
		view.setContingencias(contingencias);
	}
	
	@Override
	public void onUploadFile(String file) {
		a.setFile(file);
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
		view.showForm(selected.getEstado().contains(EstadoAgendaDTO.REALIZADA));
	}
	
	@Override
	public void onStop() {
		clear();
		super.onStop();
	}
	
	private void setActividad(){
		view.setNombreEstablecimiento(a.getNombreEstablecimiento());
		view.setRbd(a.getRbd());
		view.setCurso(a.getCurso());
		view.setTipoCurso(a.getTipoEstablecimiento());
		view.setRegion(a.getRegion());
		view.setComuna(a.getComuna());
		if(estados == null){
			estados = new ArrayList<EstadoAgendaDTO>();
			estados.add(a.getEstadoAplicacion());
		}else if(!estados.contains(a.getEstadoAplicacion())){
			estados.add(a.getEstadoAplicacion());
		}
		view.selectEstado(a.getEstadoAplicacion());
		
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
		view.setInicioActividad(a.getInicioActividad());
		view.setInicioPrueba(a.getInicioPrueba());
		view.setTerminoPrueba(a.getTerminoPrueba());
		view.setTotalAlumnos(a.getAlumnosTotal());
		view.setAlumnosAusentes(a.getAlumnosAusentes());
		view.setAlumnosDS(a.getAlumnosDs());
		view.setCuestionariosTotales(a.getTotalCuestionarios());
		view.setCuestionariosEntregados(a.getCuestionariosEntregados());
		view.setCuestionariosRecibidos(a.getCuestionariosRecibidos());
		view.setUsoMaterialContingencia(a.getMaterialContingencia());
		view.setDetalleUsoMaterialContingencia(a.getDetalleUsoMaterialContingencia());
		view.setEvaluacionGeneral(a.getEvaluacionProcedimientos());
		onEstadoChange(a.getEstadoAplicacion().getId());
	}
	
	private void clear(){
		contingencias.clear();
		view.setContingencias(contingencias);
		view.showForm(false);
	}
}
