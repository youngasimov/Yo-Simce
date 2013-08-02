package com.dreamer8.yosimce.client.planificacion;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.planificacion.ui.PlanificacionView;
import com.dreamer8.yosimce.client.planificacion.ui.PlanificacionView.PlanificacionPresenter;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class PlanificacionActivity extends SimceActivity implements
		PlanificacionPresenter {

	private final PlanificacionView view;
	
	public PlanificacionActivity(ClientFactory factory, PlanificacionPlace place,HashMap<String,ArrayList<String>> permisos){
		super(factory,place,permisos);
		this.view = getFactory().getPlanificacionView();
		this.view.setPresenter(this);
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		super.start(panel, eventBus);
		panel.setWidget(view.asWidget());
	}
}
