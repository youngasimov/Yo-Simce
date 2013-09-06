package com.dreamer8.yosimce.client.actividad;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.CursoSelector;
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
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
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
		super(factory, place, permisos,true);
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
					//goTo(new ActividadPlace());
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
				view.setGuardarButtonEnabled(true);
				getFactory().getActividadService().getSincronizacionesCurso(place.getIdCurso(), new SimceCallback<ArrayList<SincAlumnoDTO>>(eventBus,false) {
	
					@Override
					public void success(ArrayList<SincAlumnoDTO> result) {
						alumnos = result;
						for(SincAlumnoDTO x:alumnos){
							x.setSinc(SincAlumnoDTO.SINC_SIN_INFORMACION);
						}
						view.setAlumnos(alumnos);
						view.setTotalALumnos(alumnos.size());
					}
					
					@Override
					public void failure(Throwable caught) {
						super.failure(caught);
						view.setAlumnos(new ArrayList<SincAlumnoDTO>());
						view.setTotalALumnos(0);
					}
				});
			}else{
				view.setGuardarButtonEnabled(false);
			}
			
		}
		
		if(Utils.hasPermisos(getPermisos(),"ActividadService","updateSincronizacionAlumno")){
			view.setGuardarButtonEnabled(true);
			view.setIdMaterialFieldUpdater(new FieldUpdater<SincAlumnoDTO, String>() {
				
				@Override
				public void update(int index, final SincAlumnoDTO object, String value) {
					object.setIdPendrive(value);
					sinc(object);
				}
			});
			
			view.setEstadoFieldUpdater(new FieldUpdater<SincAlumnoDTO, String>() {
	
				@Override
				public void update(int index, SincAlumnoDTO object, String value) {
					for(EstadoSincronizacionDTO e: estados){
						if(e.getNombreEstado().equals(value)){
							object.setEstado(e);
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
					sinc(object);
				}
			});
			
			view.setFormFieldUpdater(new FieldUpdater<SincAlumnoDTO, Boolean>() {

				@Override
				public void update(int index, SincAlumnoDTO object,
						Boolean value) {
					object.setEntregoFormulario(value);
					
				}
			});
		}else{
			view.setGuardarButtonEnabled(false);
		}
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
	public void onGuardarTodoButtonClick() {
		for(SincAlumnoDTO x:alumnos){
			x.setSinc(SincAlumnoDTO.SINC_EN_PROCESO);
		}
		
		view.setGuardarButtonEnabled(false);
		
		Scheduler.get().scheduleIncremental(new RepeatingCommand() {
			int count = 0;
			@Override
			public boolean execute() {
				if(alumnos.size()>count){
					sinc(alumnos.get(count));
					count++;
					return true;
				}else{
					view.setGuardarButtonEnabled(true);
					return false;
				}
			}
		});
	}
	
	@Override
	public void onStop() {
		view.clear();
		super.onStop();
	}
	
	private void sinc(final SincAlumnoDTO alumno){
		
		if(Utils.hasPermisos(getPermisos(),"ActividadService","updateSincronizacionAlumno")){
			alumno.setSinc(SincAlumnoDTO.SINC_EN_PROCESO);
			view.updateTable();
			getFactory().getActividadService().updateSincronizacionAlumno(place.getIdCurso(),alumno, new SimceCallback<Boolean>(SincronizacionActivity.this.eventBus,false) {
	
				@Override
				public void success(Boolean result) {
					alumno.setSinc(SincAlumnoDTO.SINC_SIN_INFORMACION);
					view.updateTable();
				}
				
				@Override
				public void failure(Throwable caught) {
					super.failure(caught);
					alumno.setSinc(SincAlumnoDTO.SINC_ERRONEA);
					view.updateTable();
				}
			});
		}
	}

}
