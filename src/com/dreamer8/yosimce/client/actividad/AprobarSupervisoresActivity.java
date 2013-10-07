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
import com.dreamer8.yosimce.shared.dto.EvaluacionSupervisorDTO;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class AprobarSupervisoresActivity extends SimceActivity implements
		AprobarSupervisoresPresenter {

	private final AprobarSupervisoresView view;
	private EventBus eventBus; 
	
	private ArrayList<EvaluacionSupervisorDTO> evaluaciones;
	
	public AprobarSupervisoresActivity(ClientFactory factory, SimcePlace place,HashMap<String, ArrayList<String>> permisos) {
		super(factory, place, permisos);
		this.view = getFactory().getAprobarSupervisoresView();
		this.view.setPresenter(this);
	}

	@Override
	public void init(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view.asWidget());
		this.eventBus = eventBus;
		updateSupervisores(true);
	}
	
	private void updateSupervisores(boolean block){
		if(Utils.hasPermisos(eventBus,getPermisos(), "ActividadService", "getEvaluacionSupervisores")){
			getFactory().getActividadService().getEvaluacionSupervisores2(new SimceCallback<ArrayList<EvaluacionSupervisorDTO>>(eventBus,block) {

				@Override
				public void success(ArrayList<EvaluacionSupervisorDTO> result) {
					
					evaluaciones = result;
					view.setSupervisores(evaluaciones);
					ArrayList<String> sugestions = new ArrayList<String>();
					ArrayList<Integer> supervisores = new ArrayList<Integer>();
					for(EvaluacionSupervisorDTO e:evaluaciones){
						if(!supervisores.contains(e.getSupervisor().getId())){
							supervisores.add(e.getSupervisor().getId());
							sugestions.add(e.getSupervisor().getNombres()+" "+e.getSupervisor().getApellidoPaterno()+" "+e.getSupervisor().getApellidoMaterno());
						}
					}
					view.setSuggestions(sugestions);
					
				}
				@Override
				public void failure(Throwable caught) {
					evaluaciones = new ArrayList<EvaluacionSupervisorDTO>();
					view.setSupervisores(evaluaciones);
				}
			});
		}else{
			evaluaciones = new ArrayList<EvaluacionSupervisorDTO>();
			view.setSupervisores(evaluaciones);
		}
	}
	
	@Override
	public void sinc(EvaluacionSupervisorDTO sup){
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

	@Override
	public void filter(String filter) {
		
		if(filter == null || filter.isEmpty()){
			view.setSupervisores(evaluaciones);
			return;
		}
		
		ArrayList<EvaluacionSupervisorDTO> aux = new ArrayList<EvaluacionSupervisorDTO>();
		String name;
		for(EvaluacionSupervisorDTO e:evaluaciones){
			name = e.getSupervisor().getNombres()+" "+e.getSupervisor().getApellidoPaterno()+" "+e.getSupervisor().getApellidoMaterno();
			if(name.contains(filter)){
				aux.add(e);
			}
		}
		view.setSupervisores(aux);
	}

}
