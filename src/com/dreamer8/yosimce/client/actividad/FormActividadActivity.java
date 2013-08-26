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
		
		view.showSeccionExaminador(getPermisos().get("ActividadService").contains("getExaminadorProncipal") &&
			getPermisos().get("ActividadService").contains("cambiarExaminadorPrincipal") &&
			getPermisos().get("ActividadService").contains("getExaminadores"));
		
		view.showForm(false);
		
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
			if(getPermisos().get("ActividadService").contains("getExaminadorPrincipal")){
				getFactory().getActividadService().getExaminadorPrincipal(place.getIdCurso(), new SimceCallback<UserDTO>(eventBus) {

					@Override
					public void success(UserDTO result) {
						view.setExaminador(result);
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
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onCambiarExaminador(final UserDTO nuevoExaminador) {
		
		
		if(!getPermisos().get("ActividadService").contains("cambiarExaminadorPrincipal")){
			return;
		}
		getFactory().getActividadService().cambiarExaminadorPrincipal(place.getIdCurso(), nuevoExaminador.getId(), new SimceCallback<Boolean>(eventBus) {

			@Override
			public void success(Boolean result) {
				view.setExaminador(nuevoExaminador);
			}
		});
	}

	@Override
	public void getExaminadoresSuplentes(String search) {

		if(!getPermisos().get("ActividadService").contains("getExaminadores")){
			return;
		}
		getFactory().getActividadService().getExaminadores(search, new SimceCallback<ArrayList<UserDTO>>(eventBus) {

			@Override
			public void success(ArrayList<UserDTO> result) {
				view.setExaminadoresSuplentes(result);
			}
		});
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
	public void onEstadoCompletadoSelected() {
		view.showForm(true);
	}

	@Override
	public void onEstadoAnuladoSelected() {
		 view.showForm(false);
	}
	
	@Override
	public void onStop() {
		clear();
		super.onStop();
	}
	
	private void clear(){
		contingencias.clear();
		view.setContingencias(contingencias);
		view.showForm(false);
	}
}
