package com.dreamer8.yosimce.client.actividad;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.actividad.ui.SincronizacionesView;
import com.dreamer8.yosimce.client.actividad.ui.SincronizacionesView.SincronizacionesPresenter;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class SincronizacionesActivity extends SimceActivity implements
		SincronizacionesPresenter {

	private final SincronizacionesPlace place;
	private final SincronizacionesView view;
	
	public SincronizacionesActivity(ClientFactory factory, SincronizacionesPlace place,HashMap<String, ArrayList<String>> permisos) {
		super(factory, place, permisos);
		this.place = place;
		this.view = getFactory().getSincronizacionesView();
		this.view.setPresenter(this);
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		super.start(panel, eventBus);
		panel.setWidget(view.asWidget());
	}

}
