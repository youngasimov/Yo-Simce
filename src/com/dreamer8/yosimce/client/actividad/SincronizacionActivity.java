package com.dreamer8.yosimce.client.actividad;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.CursoSelector;
import com.dreamer8.yosimce.client.MensajeEvent;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.SimceCallback;
import com.dreamer8.yosimce.client.SimcePlace;
import com.dreamer8.yosimce.client.Utils;
import com.dreamer8.yosimce.client.actividad.ui.SincronizacionView;
import com.dreamer8.yosimce.client.actividad.ui.SincronizacionView.SincronizacionPresenter;
import com.dreamer8.yosimce.shared.dto.CursoDTO;
import com.dreamer8.yosimce.shared.dto.EstadoSincronizacionDTO;
import com.dreamer8.yosimce.shared.dto.SincAlumnoDTO;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class SincronizacionActivity extends SimceActivity implements
		SincronizacionPresenter {
	
	

	private final SincronizacionPlace place;
	private final SincronizacionView view;
	
	private EventBus eventBus;
	private ArrayList<SincAlumnoDTO> alumnos;
	private CursoSelector selector;
	
	private ArrayList<EstadoSincronizacionDTO> estados;
	
	public SincronizacionActivity(ClientFactory factory, SincronizacionPlace place,
			HashMap<String, ArrayList<String>> permisos) {
		super(factory, place, permisos);
		this.place = place;
		this.view = getFactory().getSincronizacionView();
		this.view.setPresenter(this);
	}
	
	@Override
	public void init(AcceptsOneWidget panel, EventBus eventBus) {
		this.eventBus = eventBus;
		panel.setWidget(view.asWidget());
		view.clear();
		
		view.setMaterialDefectusoVisivility(Utils.hasPermisos(getPermisos(),"ActividadService","getMaterialDefectuoso"));
		
		selector = new CursoSelector(getFactory(),eventBus);
		selector.setOnCursoChangeAction(new Command() {
			
			@Override
			public void execute() {
				SincronizacionPlace sp = new SincronizacionPlace();
				sp.setIdCurso(selector.getSelectedCurso().getId());
				selector.hide();
				goTo(sp);
			}
		});
		if(place.getIdCurso() < 0){
			selector.setOnCancelAction(new Command() {
				
				@Override
				public void execute() {
					goTo(new SimcePlace());
				}
			});
			selector.show();
		}else{
			if(Utils.hasPermisos(eventBus,getPermisos(),"GeneralService","getCurso")){
				getFactory().getGeneralService().getCurso(place.getIdCurso(), new SimceCallback<CursoDTO>(eventBus,false) {

					@Override
					public void success(CursoDTO result) {
						view.setCurso(result);
					}
				});
			}
			
			if(Utils.hasPermisos(eventBus,getPermisos(),"ActividadService","getEstadosSincronizacion")){
				getFactory().getActividadService().getEstadosSincronizacion(new SimceCallback<ArrayList<EstadoSincronizacionDTO>>(eventBus,false) {

					@Override
					public void success(
							ArrayList<EstadoSincronizacionDTO> result) {
						estados = result;
						view.setEstadosSincronizacion(estados);
					}
				});
			}
			
			if(Utils.hasPermisos(eventBus,getPermisos(),"ActividadService","getSincronizacionesCurso")){
				getFactory().getActividadService().getSincronizacionesCurso(place.getIdCurso(), new SimceCallback<ArrayList<SincAlumnoDTO>>(eventBus,false) {
	
					@Override
					public void success(ArrayList<SincAlumnoDTO> result) {
						alumnos = result;
						ArrayList<String> suggestions = new ArrayList<String>();
						for(SincAlumnoDTO x:alumnos){
							
							if(x.getComentario() == null){
								x.setComentario("");
							}
							if(x.getIdPendrive() == null){
								x.setIdPendrive("");
							}
							if(x.getEntregoFormulario() == null){
								x.setEntregoFormulario(false);
							}
							if(x.getEstado() == null || x.getEstado().getNombreEstado() == null || x.getEstado().getNombreEstado().isEmpty() || x.getEstado().getNombreEstado().contains(SincronizacionView.SIN_INFO)){
								x.setSinc(SincAlumnoDTO.SINC_SIN_INFORMACION);
							}else{
								x.setSinc(SincAlumnoDTO.SINC_EXITOSA);
							}
							
							suggestions.add(x.getRut()+" - "+x.getNombres()+" "+x.getApellidoPaterno()+" "+x.getApellidoMaterno());
						}
						view.setSuggestions(suggestions);
						view.setAlumnos(alumnos);
					}
					
					@Override
					public void failure(Throwable caught) {
						super.failure(caught);
						view.setAlumnos(new ArrayList<SincAlumnoDTO>());
					}
				});
			}
			
		}
		
		view.setIdMaterialFieldUpdater(new FieldUpdater<SincAlumnoDTO, String>() {
			
			@Override
			public void update(int index, final SincAlumnoDTO object, String value) {
				
				for(SincAlumnoDTO s:alumnos){
					if(s.getIdPendrive()!=null && value!=null && !value.isEmpty() && value.equals(s.getIdPendrive())){
						SincronizacionActivity.this.eventBus.fireEvent(new MensajeEvent("El id del pendrive ya esta registrado a un alumno de este mismo curso",MensajeEvent.MSG_WARNING,true));
						break;
					}
				}
				
				object.setIdPendrive(value);
				if(!object.getEstado().getNombreEstado().contains(SincronizacionView.SIN_INFO)){
					sinc(object);
				}
			}
		});
		
		view.setEstadoFieldUpdater(new FieldUpdater<SincAlumnoDTO, String>() {

			@Override
			public void update(int index, SincAlumnoDTO object, String value) {
				for(EstadoSincronizacionDTO e: estados){
					if(e.getNombreEstado().equals(value)){
						object.setEstado(e);
						if(e.getNombreEstado().equals(SincronizacionView.SIN_INFO)){
							object.setIdPendrive("");
							object.setComentario("");
							object.setEntregoFormulario(false);
						}
						sinc(object);
						return;
					}
				}
				
			}
		});
		
		view.setComentarioFieldUpdater(new FieldUpdater<SincAlumnoDTO, String>() {

			@Override
			public void update(int index, SincAlumnoDTO object, String value) {
				object.setComentario(value);
				if(!object.getEstado().getNombreEstado().contains(SincronizacionView.SIN_INFO)){
					sinc(object);
				}
			}
		});
		
		view.setFormFieldUpdater(new FieldUpdater<SincAlumnoDTO, Boolean>() {

			@Override
			public void update(int index, SincAlumnoDTO object,
					Boolean value) {
				object.setEntregoFormulario(value);
				if(!object.getEstado().getNombreEstado().contains(SincronizacionView.SIN_INFO)){
					sinc(object);
				}
			}
		});
		
		view.setUpdateFieldUpdater(new FieldUpdater<SincAlumnoDTO, String>() {

			@Override
			public void update(int index, SincAlumnoDTO object,String value) {
				if(object.getEstado().getNombreEstado().equals(SincronizacionView.SIN_INFO)){
					object.setIdPendrive("");
					object.setComentario("");
					object.setEntregoFormulario(false);
				}
				sinc(object);
				
			}
		});
	}

	@Override
	public void onCambiarCursoButtonClick() {
		selector.show();
		
	}

	@Override
	public void onAgregarAlumnoButtonClick() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void filter(String filter) {
		if(filter == null || filter.isEmpty()){
			view.setAlumnos(alumnos);
			return;
		}
		String rut = filter.substring(0, 12).trim();
		ArrayList<SincAlumnoDTO> filtrado = new ArrayList<SincAlumnoDTO>();
		for(SincAlumnoDTO s:alumnos){
			if(s.getRut().contains(rut)){
				filtrado.add(s);
			}
		}
		view.setAlumnos(filtrado);
	}
	
	@Override
	public void onStop() {
		view.clear();
		super.onStop();
	}
	
	private void sinc(final SincAlumnoDTO alumno){
		Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				if(Utils.hasPermisos(getPermisos(),"ActividadService","updateSincronizacionAlumno")){
					alumno.setSinc(SincAlumnoDTO.SINC_EN_PROCESO);
					view.updateTableRow(alumno);
					getFactory().getActividadService().updateSincronizacionAlumno(place.getIdCurso(),alumno, new SimceCallback<Boolean>(SincronizacionActivity.this.eventBus,false) {
			
						@Override
						public void success(Boolean result) {
							if(alumno.getEstado().getNombreEstado().equals(SincronizacionView.SIN_INFO)){
								alumno.setSinc(SincAlumnoDTO.SINC_SIN_INFORMACION);
							}else{
								alumno.setSinc(SincAlumnoDTO.SINC_EXITOSA);
							}
							view.updateTableRow(alumno);
						}
						
						@Override
						public void failure(Throwable caught) {
							super.failure(caught);
							alumno.setSinc(SincAlumnoDTO.SINC_ERRONEA);
							view.updateTableRow(alumno);
						}
					});
				}
			}
		});
	}

}
