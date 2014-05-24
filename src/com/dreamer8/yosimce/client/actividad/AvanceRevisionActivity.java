package com.dreamer8.yosimce.client.actividad;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.actividad.ui.AvanceRevisionView;
import com.dreamer8.yosimce.client.actividad.ui.AvanceRevisionView.AvanceRevisionPresenter;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class AvanceRevisionActivity extends SimceActivity implements
		AvanceRevisionPresenter {

	
	private final AvanceRevisionView view;
	private final AvanceRevisionPlace place;
	
	
	public AvanceRevisionActivity(ClientFactory factory, AvanceRevisionPlace place,HashMap<String, ArrayList<String>> permisos) {
		super(factory, place, permisos);
		this.place = place;
		this.view = getFactory().getAvanceRevisionView();
	}

	@Override
	public void init(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view.asWidget());
		this.view.setPresenter(this);
	}

}
