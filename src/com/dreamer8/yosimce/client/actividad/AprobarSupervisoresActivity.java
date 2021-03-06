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
import com.dreamer8.yosimce.shared.dto.EvaluacionSuplenteDTO;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class AprobarSupervisoresActivity extends SimceActivity implements
		AprobarSupervisoresPresenter {

	private final AprobarSupervisoresView view;
	private EventBus eventBus; 
	
	private ArrayList<EvaluacionSupervisorDTO> evaluaciones;
	private ArrayList<EvaluacionSuplenteDTO> evaluacionesSuplentes;
	private int selectedTab;
	
	public AprobarSupervisoresActivity(ClientFactory factory, SimcePlace place,HashMap<String, ArrayList<String>> permisos) {
		super(factory, place, permisos);
		this.view = getFactory().getAprobarSupervisoresView();
		this.view.setPresenter(this);
	}

	@Override
	public void init(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view.asWidget());
		this.eventBus = eventBus;
		selectedTab = view.getTabSelected();
		if(selectedTab==0){
			updateSupervisores(true);
		}else{
			updateSuplentes(true);
		}
	}
	
	@Override
	public void onTabSelected(int tab) {
		selectedTab = tab;
		if(tab == 0){
			updateSupervisores(true);
		}else{
			updateSuplentes(true);
		}
	}
	
	private void updateSupervisores(boolean block){
		if(Utils.hasPermisos(eventBus,getPermisos(), "ActividadService", "getEvaluacionSupervisores")){
			getFactory().getActividadService().getEvaluacionSupervisores(new SimceCallback<ArrayList<EvaluacionSupervisorDTO>>(eventBus,block) {

				@Override
				public void success(ArrayList<EvaluacionSupervisorDTO> result) {
					
					evaluaciones = result;
					view.setSupervisores(evaluaciones);
					ArrayList<String> sugestions = new ArrayList<String>();
					ArrayList<Integer> supervisores = new ArrayList<Integer>();
					boolean even = false;
					int current = -1;
					for(EvaluacionSupervisorDTO e:evaluaciones){
						if(e.getSupervisor().getId() != current){
							current = e.getSupervisor().getId();
							even = !even;
						}
						e.setEven(even);
						e.setSinc(EvaluacionSupervisorDTO.UPDATED);
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
	
	private void updateSuplentes(boolean block){
		if(Utils.hasPermisos(eventBus,getPermisos(), "ActividadService", "getEvaluacionSupervisores")){
			getFactory().getActividadService().getEvaluacionSuplentes(new SimceCallback<ArrayList<EvaluacionSuplenteDTO>>(eventBus,block) {

				@Override
				public void success(ArrayList<EvaluacionSuplenteDTO> result) {
					
					evaluacionesSuplentes = result;
					view.setSuplentes(evaluacionesSuplentes);
					ArrayList<String> sugestions = new ArrayList<String>();
					ArrayList<Integer> suplentes = new ArrayList<Integer>();
					for(EvaluacionSuplenteDTO e:evaluacionesSuplentes){
						e.setSinc(EvaluacionSupervisorDTO.UPDATED);
						if(!suplentes.contains(e.getSuplente().getId())){
							suplentes.add(e.getSuplente().getId());
							sugestions.add(e.getSuplente().getNombres()+" "+e.getSuplente().getApellidoPaterno()+" "+e.getSuplente().getApellidoMaterno());
						}
					}
					view.setSuplenteSuggestions(sugestions);
					
				}
				@Override
				public void failure(Throwable caught) {
					evaluacionesSuplentes = new ArrayList<EvaluacionSuplenteDTO>();
					view.setSuplentes(evaluacionesSuplentes);
				}
			});
		}else{
			evaluacionesSuplentes = new ArrayList<EvaluacionSuplenteDTO>();
			view.setSuplentes(evaluacionesSuplentes);
		}
	}

	@Override
	public void filter(String filter) {
		
		if(filter == null || filter.isEmpty()){
			boolean even = false;
			int current = -1;
			for(EvaluacionSupervisorDTO e:evaluaciones){
				if(e.getSupervisor().getId() != current){
					current = e.getSupervisor().getId();
					even = !even;
				}
				e.setEven(even);
			}
			view.setSupervisores(evaluaciones);
			return;
		}
		
		ArrayList<EvaluacionSupervisorDTO> aux = new ArrayList<EvaluacionSupervisorDTO>();
		String name;
		boolean even = false;
		int current = -1;
		for(EvaluacionSupervisorDTO e:evaluaciones){
			name = e.getSupervisor().getNombres()+" "+e.getSupervisor().getApellidoPaterno()+" "+e.getSupervisor().getApellidoMaterno();
			
			if(((filter.matches("^[0-9]+$") && filter.contains(e.getCo())) ||
					(!filter.matches("^[0-9]+$") && name.contains(filter))) &&
					e.getSupervisor().getId() != current){
				current = e.getSupervisor().getId();
				even = !even;
			}
			e.setEven(even);
			
			if(filter.matches("^[0-9]+$") && filter.contains(e.getCo())){
				aux.add(e);
			}else if(!filter.matches("^[0-9]+$") && name.contains(filter)){
				aux.add(e);
			}
		}
		view.setSupervisores(aux);
	}

	@Override
	public void suplentefilter(String filter) {
		if(filter == null || filter.isEmpty()){
			view.setSuplentes(evaluacionesSuplentes);
			return;
		}
		ArrayList<EvaluacionSuplenteDTO> aux = new ArrayList<EvaluacionSuplenteDTO>();
		String name;
		for(EvaluacionSuplenteDTO e:evaluacionesSuplentes){
			name = e.getSuplente().getNombres()+" "+e.getSuplente().getApellidoPaterno()+" "+e.getSuplente().getApellidoMaterno();
			if(filter.matches("^[0-9]+$") && filter.contains(e.getCo())){
				aux.add(e);
			}else if(!filter.matches("^[0-9]+$") && name.contains(filter)){
				aux.add(e);
			}
		}
		view.setSuplentes(aux);
	}
	
	@Override
	public void sinc(final EvaluacionSupervisorDTO sup){
		if(view.replicarSeleccionByCurso()){
			for(EvaluacionSupervisorDTO ev: evaluaciones){
				if(ev.getSupervisor().getId().equals(sup.getSupervisor().getId())){
					ev.setGeneral(sup.getGeneral());
					ev.setPresentacionPersonal(sup.getPresentacionPersonal());
					ev.setPuntualidad(sup.getPuntualidad());
					ev.setPresente(sup.getPresente());
					baseSinc(ev);
				}
			}
		}else{
			baseSinc(sup);
		}
		
	}

	@Override
	public void sinc(final EvaluacionSuplenteDTO suplente) {
		suplente.setSinc(EvaluacionSuplenteDTO.UPDATING);
		
		view.updateTableRow(suplente);
		if(Utils.hasPermisos(eventBus,getPermisos(), "ActividadService", "updateEvaluacionSupervisor")){
			getFactory().getActividadService().updateEvaluacionSuplente(suplente, new SimceCallback<Boolean>(eventBus,false) {
	
				@Override
				public void success(Boolean result) {
					suplente.setSinc(EvaluacionSuplenteDTO.UPDATED);
					view.updateTableRow(suplente);
				}
				@Override
				public void failure(Throwable caught) {
					suplente.setSinc(EvaluacionSuplenteDTO.ERROR);
					view.updateTableRow(suplente);
				}
			});
		}
	}
	
	private void baseSinc(final EvaluacionSupervisorDTO sup){
		sup.setSinc(EvaluacionSupervisorDTO.UPDATING);
		view.updateTableRow(sup);
		
		if(Utils.hasPermisos(eventBus,getPermisos(), "ActividadService", "updateEvaluacionSupervisor")){
			getFactory().getActividadService().updateEvaluacionSupervisor(sup, new SimceCallback<Boolean>(eventBus,false) {
	
				@Override
				public void success(Boolean result) {
					sup.setSinc(EvaluacionSupervisorDTO.UPDATED);
					view.updateTableRow(sup);
				}
				@Override
				public void failure(Throwable caught) {
					sup.setSinc(EvaluacionSupervisorDTO.ERROR);
					view.updateTableRow(sup);
				}
			});
		}
	}

}
