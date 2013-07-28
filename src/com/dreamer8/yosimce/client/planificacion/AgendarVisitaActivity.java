package com.dreamer8.yosimce.client.planificacion;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.planificacion.ui.AgendarVisitaView;
import com.dreamer8.yosimce.client.planificacion.ui.AgendarVisitaView.AgendarVisitaPresenter;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class AgendarVisitaActivity extends SimceActivity implements
		AgendarVisitaPresenter {
	
	private static AgendarVisitaView view;
	private static AgendarVisitaPlace place;
	
	public AgendarVisitaActivity(ClientFactory factory,AgendarVisitaPlace place, HashMap<String, ArrayList<String>> permisos) {
		super(factory, permisos);
		this.place = place;
		this.view = factory.getAgendarVisitaView();
		view.setPresenter(this);
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view.asWidget());
	}

}
