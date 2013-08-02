package com.dreamer8.yosimce.client.general;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.CursoSelector;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.general.ui.DetalleCursoView;
import com.dreamer8.yosimce.client.general.ui.DetalleCursoView.DetalleCursoPresenter;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class DetalleCursoActivity extends SimceActivity implements
		DetalleCursoPresenter {

	private final DetalleCursoPlace place;
	private final DetalleCursoView view;
	private final CursoSelector selector;
	
	public DetalleCursoActivity(ClientFactory factory, DetalleCursoPlace place, HashMap<String, ArrayList<String>> permisos) {
		super(factory, place,permisos);
		this.place = place;
		this.view = getFactory().getDetalleCursoView();
		this.view.setPresenter(this);
		selector = new CursoSelector(getFactory());
		selector.setOnEstablecimientoChangeAction(new Command() {
			
			@Override
			public void execute() {
				DetalleCursoPlace dcp = new DetalleCursoPlace(DetalleCursoActivity.this.place.getAplicacionId(),DetalleCursoActivity.this.place.getNivelId(),DetalleCursoActivity.this.place.getTipoId(),DetalleCursoActivity.this.place.getCursoId());
				DetalleCursoActivity.this.getFactory().getPlaceController().goTo(dcp);
			}
		});
	}


	@Override
	public void onCambiarCursoClick() {
		selector.setCancelable(true);
		selector.setGlassEnabled(false);
		selector.showRelativeTo(view.getCambiarButton());
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		super.start(panel,eventBus);
		panel.setWidget(view.asWidget());
		
		if(place.getCursoId()<0){
			selector.setOnCancelAction(new Command() {
				
				@Override
				public void execute() {
					DetalleCursoActivity.this.getFactory().getPlaceController().goTo(new GeneralPlace(DetalleCursoActivity.this.place.getAplicacionId(), DetalleCursoActivity.this.place.getNivelId(), DetalleCursoActivity.this.place.getTipoId()));
				}
			});
			selector.setCancelable(true);
			selector.setGlassEnabled(true);
			selector.show();
		}else{
			view.setIdCurso(place.getCursoId());
		}
	}
	
	@Override
	public void onStop() {
		super.onStop();
		view.clearAll();
	}
}
