package com.dreamer8.yosimce.client.actividad;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.SimcePlace;
import com.dreamer8.yosimce.client.actividad.ui.AprobarSupervisoresView;
import com.dreamer8.yosimce.client.actividad.ui.AprobarSupervisoresView.AprobarSupervisoresPresenter;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class AprobarSupervisoresActivity extends SimceActivity implements
		AprobarSupervisoresPresenter {

	private final AprobarSupervisoresView view;
	private EventBus eventBus; 
	
	public AprobarSupervisoresActivity(ClientFactory factory, SimcePlace place,HashMap<String, ArrayList<String>> permisos) {
		super(factory, place, permisos,true);
		this.view = getFactory().getAprobarSupervisoresView();
	}

	@Override
	public void init(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view.asWidget());
		this.eventBus = eventBus;
	}

}
