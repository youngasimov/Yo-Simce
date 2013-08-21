package com.dreamer8.yosimce.client.actividad;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.CursoSelector;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.actividad.ui.FormActividadView;
import com.dreamer8.yosimce.client.actividad.ui.FormActividadView.FormActividadPresenter;
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
	
	public FormActividadActivity(ClientFactory factory, FormActividadPlace place,HashMap<String, ArrayList<String>> permisos) {
		super(factory, place, permisos);
		this.place = place;
		this.view = getFactory().getFormActividadView();
		this.view.setPresenter(this);
	}
	
	@Override
	public void init(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view.asWidget());
		this.eventBus = eventBus;
		selector = new CursoSelector(getFactory(),eventBus);
		
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
			
			
			view.setNombreEstablecimiento("COLEGIO CARMELITAS DESCALZAS DE ROSARIO");
			view.setRbd("2425");
			view.setCurso("2A");
			view.setTipoCurso("Seleccionado");
			view.setRegion("Valparaiso");
			view.setComuna("Viña del mar");
			
			UserDTO u = new UserDTO();
			u.setNombres("Juan Daniel");
			u.setApellidoPaterno("Perez");
			u.setApellidoMaterno("Gonzalez");
			u.setRut("11.111.111-1");
			view.setExaminador(u);
			
			
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

}
