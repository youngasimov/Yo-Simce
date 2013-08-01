package com.dreamer8.yosimce.client.general;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.SimcePlace;
import com.dreamer8.yosimce.client.general.ui.GeneralView;
import com.dreamer8.yosimce.client.general.ui.GeneralView.GeneralPresenter;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class GeneralActivity extends SimceActivity implements GeneralPresenter {

	
	private final GeneralView view;
	private final GeneralPlace place;
	
	public GeneralActivity(ClientFactory factory, GeneralPlace place, HashMap<String,ArrayList<String>> permisos){
		super(factory,permisos);
		this.place = place;
		this.view = getFactory().getGeneralView();
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
