package com.dreamer8.yosimce.client.planificacion;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.SimcePlace;
import com.dreamer8.yosimce.client.planificacion.ui.PlanificacionView;
import com.dreamer8.yosimce.client.planificacion.ui.PlanificacionView.PlanificacionPresenter;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class PlanificacionActivity extends SimceActivity implements
		PlanificacionPresenter {

	private final PlanificacionView view;
	private final PlanificacionPlace place;
	
	public PlanificacionActivity(ClientFactory factory, PlanificacionPlace place,HashMap<String,ArrayList<String>> permisos){
		super(factory,permisos);
		this.place = place;
		this.view = getFactory().getPlanificacionView();
		this.view.setPresenter(this);
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view.asWidget());
		updatedPermisos();
	}

	@Override
	public void goTo(SimcePlace place) {
		place.setAplicacionId(this.place.getAplicacionId());
		place.setNivelId(this.place.getNivelId());
		place.setTipoId(this.place.getTipoId());
		getFactory().getPlaceController().goTo(place);
	}
}
