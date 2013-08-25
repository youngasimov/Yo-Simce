package com.dreamer8.yosimce.client.actividad;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.CursoSelector;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.SimceCallback;
import com.dreamer8.yosimce.client.actividad.ui.FormActividadView;
import com.dreamer8.yosimce.client.actividad.ui.FormActividadView.FormActividadPresenter;
import com.dreamer8.yosimce.shared.dto.ContingenciaDTO;
import com.dreamer8.yosimce.shared.dto.TipoContingenciaDTO;
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
	private ArrayList<TipoContingenciaDTO> tiposDisponibles;
	
	public FormActividadActivity(ClientFactory factory, FormActividadPlace place,HashMap<String, ArrayList<String>> permisos) {
		super(factory, place, permisos);
		this.place = place;
		this.view = getFactory().getFormActividadView();
		this.view.setPresenter(this);
		contingencias = new ArrayList<ContingenciaDTO>();
		tipos = new ArrayList<TipoContingenciaDTO>();
		tiposDisponibles = new ArrayList<TipoContingenciaDTO>();
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
		
		if(place.getIdCurso()<0){
			selector.setOnCancelAction(new Command() {
				
				@Override
				public void execute() {
					goTo(new ActividadPlace());
				}
			});
			selector.show();
		}else{
			/*
			getFactory().getActividadService().getTiposContingencia(place.getIdCurso(), new SimceCallback<ArrayList<TipoContingenciaDTO>>(eventBus) {

				@Override
				public void success(ArrayList<TipoContingenciaDTO> result) {
					tipos.clear();
					tipos.addAll(result);
					tiposDisponibles.clear();
					tiposDisponibles.addAll(tipos);
				}
			});
			*/
			//TipoContingenciaDTO t = new 
			//tipos.add
			
		}
		
	}

	@Override
	public void onCambiarCursoClick() {
		selector.show();
	}

	@Override
	public void onCambiarExaminadorClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void guardarFormulario() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onAgregarContingencia(ContingenciaDTO contingencia) {
		tiposDisponibles.remove(contingencia.getTipoContingencia());
		contingencias.add(contingencia);
		view.setTiposContingencia(tiposDisponibles);
		view.setContingencias(contingencias);
	}

	@Override
	public void onRemoveContingecia(ContingenciaDTO contingencia) {
		contingencias.remove(contingencia);
		tiposDisponibles.add(contingencia.getTipoContingencia());
		view.setTiposContingencia(tiposDisponibles);
		view.setContingencias(contingencias);
	}

	@Override
	public void onEstadoCompletadoSelected() {
		
	}

	@Override
	public void onEstadoAnuladoSelected() {
		 
	}
	
	@Override
	public void onStop() {
		clear();
		super.onStop();
	}
	
	private void clear(){
		
	}
}
