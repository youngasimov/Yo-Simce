package com.dreamer8.yosimce.client.planificacion;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.planificacion.ui.AgendamientosView;
import com.dreamer8.yosimce.client.planificacion.ui.AgendamientosView.AgendamientosPresenter;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class AgendamientosActivity extends SimceActivity implements
		AgendamientosPresenter {

	private final AgendamientosPlace place;
	private AgendamientosView view;
	
	public AgendamientosActivity(ClientFactory factory,AgendamientosPlace place,HashMap<String, ArrayList<String>> permisos) {
		super(factory,permisos);
		this.place = place;
		
		view.setPresenter(this);
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view.asWidget());
	}

}
