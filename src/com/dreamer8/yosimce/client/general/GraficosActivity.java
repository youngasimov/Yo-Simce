package com.dreamer8.yosimce.client.general;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.general.ui.GraficosView;
import com.dreamer8.yosimce.client.general.ui.GraficosView.GraficosPresenter;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class GraficosActivity extends SimceActivity implements GraficosPresenter {

	
	private final GraficosView view;
	private final GraficosPlace place;
	private EventBus eventBus;
	
	public GraficosActivity(ClientFactory factory, GraficosPlace place,HashMap<String, ArrayList<String>> permisos) {
		super(factory,place,permisos);
		this.place = place;
		this.view = getFactory().getGraficosView();
	}

	@Override
	public void actualizarEstadoMaterial() {
		// TODO Auto-generated method stub

	}

	@Override
	public void actualizarEstadoActividad() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view.asWidget());
		this.eventBus = eventBus;
	}

}
