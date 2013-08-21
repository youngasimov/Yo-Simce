package com.dreamer8.yosimce.client.actividad;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.SimceCallback;
import com.dreamer8.yosimce.client.actividad.ui.SincronizacionView;
import com.dreamer8.yosimce.client.actividad.ui.SincronizacionView.SincronizacionPresenter;
import com.dreamer8.yosimce.shared.dto.SincAlumnoDTO;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class SincronizacionActivity extends SimceActivity implements
		SincronizacionPresenter {

	private final SincronizacionPlace place;
	private final SincronizacionView view;
	
	private EventBus eventBus;
	private ArrayList<SincAlumnoDTO> alumnos;
	
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
		
		if(place.getIdSincronizacion() < 0){
			
		}else{
			getFactory().getActividadService().getSincronizacionesCurso(place.getIdSincronizacion(), new SimceCallback<ArrayList<SincAlumnoDTO>>(eventBus) {

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
		}
		
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
	}

	@Override
	public void onCambiarCursoButtonClick() {
		// TODO Auto-generated method stub
		
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
	
	private void sinc(final SincAlumnoDTO alumno){
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
