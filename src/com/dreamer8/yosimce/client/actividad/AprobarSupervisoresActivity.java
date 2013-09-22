package com.dreamer8.yosimce.client.actividad;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.SimceCallback;
import com.dreamer8.yosimce.client.SimcePlace;
import com.dreamer8.yosimce.client.Utils;
import com.dreamer8.yosimce.client.actividad.ui.AprobarSupervisoresView;
import com.dreamer8.yosimce.client.actividad.ui.AprobarSupervisoresView.AprobarSupervisoresPresenter;
import com.dreamer8.yosimce.shared.dto.EvaluacionUsuarioDTO;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class AprobarSupervisoresActivity extends SimceActivity implements
		AprobarSupervisoresPresenter {

	private final AprobarSupervisoresView view;
	private EventBus eventBus; 
	
	public AprobarSupervisoresActivity(ClientFactory factory, SimcePlace place,HashMap<String, ArrayList<String>> permisos) {
		super(factory, place, permisos);
		this.view = getFactory().getAprobarSupervisoresView();
		this.view.setPresenter(this);
	}

	@Override
	public void init(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view.asWidget());
		this.eventBus = eventBus;
		
		view.setFormularioFieldUpdater(new FieldUpdater<EvaluacionUsuarioDTO, Boolean>() {
			
			@Override
			public void update(int index, EvaluacionUsuarioDTO object, Boolean value) {
				if(Utils.hasPermisos(AprobarSupervisoresActivity.this.eventBus, getPermisos(), "ActividadService", "updateEvaluacionSupervisor")){
					object.setFormulario((value)?4:0);
					sinc(object);
				}
			}
		});
		
		view.setPresentacionFieldUpdater(new FieldUpdater<EvaluacionUsuarioDTO, Boolean>() {
			
			@Override
			public void update(int index, EvaluacionUsuarioDTO object, Boolean value) {
				if(Utils.hasPermisos(AprobarSupervisoresActivity.this.eventBus, getPermisos(), "ActividadService", "updateEvaluacionSupervisor")){
					object.setPresentacionPersonal((value)?4:0);
					sinc(object);
				}
			}
		});
		
		view.setPuntualidadFieldUpdater(new FieldUpdater<EvaluacionUsuarioDTO, Boolean>() {
			
			@Override
			public void update(int index, EvaluacionUsuarioDTO object, Boolean value) {
				if(Utils.hasPermisos(AprobarSupervisoresActivity.this.eventBus, getPermisos(), "ActividadService", "updateEvaluacionSupervisor")){
					object.setPuntualidad((value)?4:0);
					sinc(object);
				}
			}
		});
		
		view.setGeneralFieldUpdater(new FieldUpdater<EvaluacionUsuarioDTO, Boolean>() {
			
			@Override
			public void update(int index, EvaluacionUsuarioDTO object, Boolean value) {
				if(Utils.hasPermisos(AprobarSupervisoresActivity.this.eventBus, getPermisos(), "ActividadService", "updateEvaluacionSupervisor")){
					object.setGeneral((value)?4:0);
					sinc(object);
				}
			}
		});
		
		updateSupervisores(false);
	}
	
	private void updateSupervisores(boolean block){
		if(Utils.hasPermisos(eventBus,getPermisos(), "ActividadService", "getEvaluacionSupervisores")){
			getFactory().getActividadService().getEvaluacionSupervisores(new SimceCallback<ArrayList<EvaluacionUsuarioDTO>>(eventBus,block) {

				@Override
				public void success(ArrayList<EvaluacionUsuarioDTO> result) {
					view.setSupervisores(result);
				}
				@Override
				public void failure(Throwable caught) {
					view.setSupervisores(new ArrayList<EvaluacionUsuarioDTO>());
				}
			});
		}else{
			view.setSupervisores(new ArrayList<EvaluacionUsuarioDTO>());
		}
	}
	
	private void sinc(EvaluacionUsuarioDTO sup){
		if(Utils.hasPermisos(eventBus,getPermisos(), "ActividadService", "updateEvaluacionSupervisor")){
			getFactory().getActividadService().updateEvaluacionSupervisor(sup, new SimceCallback<Boolean>(eventBus,false) {
	
				@Override
				public void success(Boolean result) {
					
				}
				@Override
				public void failure(Throwable caught) {
					updateSupervisores(true);
				}
			});
		}
	}

}
