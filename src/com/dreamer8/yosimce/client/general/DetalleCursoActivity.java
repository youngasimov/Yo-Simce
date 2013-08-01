package com.dreamer8.yosimce.client.general;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.general.ui.DetalleCursoView;
import com.dreamer8.yosimce.client.general.ui.DetalleCursoView.DetalleCursoPresenter;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class DetalleCursoActivity extends SimceActivity implements
		DetalleCursoPresenter {

	private final DetalleCursoPlace place;
	private final DetalleCursoView view;
	
	public DetalleCursoActivity(ClientFactory factory, DetalleCursoPlace place, HashMap<String, ArrayList<String>> permisos) {
		super(factory, permisos);
		this.place = place;
		this.view = getFactory().getDetalleCursoView();
		this.view.setPresenter(this);
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		super.start(panel,eventBus);
		panel.setWidget(view.asWidget());
	}

}
