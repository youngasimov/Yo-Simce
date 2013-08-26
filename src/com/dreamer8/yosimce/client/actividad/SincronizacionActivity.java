package com.dreamer8.yosimce.client.actividad;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.CursoSelector;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.SimceCallback;
import com.dreamer8.yosimce.client.actividad.ui.SincronizacionView;
import com.dreamer8.yosimce.client.actividad.ui.SincronizacionView.SincronizacionPresenter;
import com.dreamer8.yosimce.shared.dto.CursoDTO;
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
		view.getDataDisplay().setRowCount(0);
		if(place.getIdCurso() < 0){
			selector.setOnCancelAction(new Command() {
				
				@Override
				public void execute() {
					goTo(new ActividadPlace());
				}
			});
			selector.show();
		}else{
			
			if(getPermisos().get("GeneralService").contains("getCurso")){
				getFactory().getGeneralService().getCurso(place.getIdCurso(), new SimceCallback<CursoDTO>(eventBus) {

					@Override
					public void success(CursoDTO result) {
						view.setCurso(result);
					}
				});
			}
			
			if(getPermisos().get("ActividadService").contains("getSincronizacionesCurso")){
				view.setGuardarButtonEnabled(true);
				getFactory().getActividadService().getSincronizacionesCurso(place.getIdCurso(), new SimceCallback<ArrayList<SincAlumnoDTO>>(eventBus) {
	
					@Override
					public void success(ArrayList<SincAlumnoDTO> result) {
						alumnos = result;
						for(SincAlumnoDTO x:alumnos){
							x.setSinc(SincAlumnoDTO.SINC_SIN_INFORMACION);
						}
						view.getDataDisplay().setRowCount(alumnos.size());
						view.getDataDisplay().setRowData(0, alumnos);
						view.setTotalALumnos(alumnos.size());
					}
				});
			}else{
				view.setGuardarButtonEnabled(false);
			}
			
		}
		
		if(!getPermisos().get("ActividadService").contains("updateSincronizacionAlumno")){
			view.setGuardarButtonEnabled(true);
			view.setIdMaterialFieldUpdater(new FieldUpdater<SincAlumnoDTO, String>() {
				
				@Override
				public void update(int index, final SincAlumnoDTO object, String value) {
					object.setIdPendrive(value);
					sinc(object);
				}
			});
			
			view.setEstadoFieldUpdater(new FieldUpdater<SincAlumnoDTO, Boolean>() {
	
				@Override
				public void update(int index, SincAlumnoDTO object, Boolean value) {
					object.setSincronizado(value);
					sinc(object);
				}
			});
			
			view.setComentarioFieldUpdater(new FieldUpdater<SincAlumnoDTO, String>() {
	
				@Override
				public void update(int index, SincAlumnoDTO object, String value) {
					object.setComentario(value);
					sinc(object);
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
		
		if(!getPermisos().get("ActividadService").contains("updateSincronizacionAlumno")){
			return;
		}
		
		alumno.setSinc(SincAlumnoDTO.SINC_EN_PROCESO);
		view.updateTable();
		getFactory().getActividadService().updateSincronizacionAlumno(alumno, new SimceCallback<Boolean>(SincronizacionActivity.this.eventBus) {

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
