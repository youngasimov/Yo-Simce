package com.dreamer8.yosimce.client.actividad;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.CursoSelector;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.actividad.ui.MaterialDefectuosoView;
import com.dreamer8.yosimce.client.actividad.ui.MaterialDefectuosoView.MaterialDefectuosoPresenter;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class MaterialDefectuosoActivity extends SimceActivity implements
		MaterialDefectuosoPresenter {

	private final MaterialDefectuosoPlace place;
	private final MaterialDefectuosoView view;
	
	private EventBus eventBus;
	private CursoSelector selector;
	
	public MaterialDefectuosoActivity(ClientFactory factory, MaterialDefectuosoPlace place,
			HashMap<String, ArrayList<String>> permisos) {
		super(factory, place, permisos);
		this.place = place;
		this.view = getFactory().getMaterialDefectuosoView();
		this.view.setPresenter(this);
	}

	@Override
	public void init(AcceptsOneWidget panel, EventBus eventBus) {
		this.eventBus = eventBus;
		panel.setWidget(view.asWidget());
		
		selector = new CursoSelector(getFactory(),eventBus);
		selector.setOnCursoChangeAction(new Command() {
			
			@Override
			public void execute() {
				MaterialDefectuosoPlace sp = new MaterialDefectuosoPlace();
				sp.setIdCurso(selector.getSelectedCurso().getId());
				selector.hide();
				goTo(sp);
			}
		});
		
		if(place.getIdCurso() < 0){
			selector.setOnCancelAction(new Command() {
				
				@Override
				public void execute() {
					goTo(new ActividadPlace());
				}
			});
			selector.show();
		}else{
			
		}
	}

}
