package com.dreamer8.yosimce.client.planificacion;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.planificacion.ui.DetalleAgendaEstablecimientoView;
import com.dreamer8.yosimce.client.planificacion.ui.DetalleAgendaEstablecimientoView.DetalleAgendaEstablecimientoPresenter;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class DetalleAgendaEstablecimientoActivity extends SimceActivity
		implements DetalleAgendaEstablecimientoPresenter {

	
	private final DetalleAgendaEstablecimientoPlace place;
	private final DetalleAgendaEstablecimientoView view;
	
	public DetalleAgendaEstablecimientoActivity(ClientFactory factory, DetalleAgendaEstablecimientoPlace place,HashMap<String, ArrayList<String>> permisos) {
		super(factory, permisos);
		this.place = place;
		this.view = factory.getDetalleAgendaEstablecimientoView();
		this.view.setPresenter(this);
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view.asWidget());
	}

}
