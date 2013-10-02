package com.dreamer8.yosimce.client.general;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.general.ui.CentroControlView;
import com.dreamer8.yosimce.client.general.ui.CentroControlView.CentroControlPresenter;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class CentroControlActivity extends SimceActivity implements
		CentroControlPresenter {

	private final CentroControlView view;
	
	public CentroControlActivity(ClientFactory factory, CentroControlPlace place,HashMap<String, ArrayList<String>> permisos) {
		super(factory, place, permisos);
		this.view = getFactory().getCentroControlView();
		this.view.setPresenter(this);
	}

	@Override
	public void init(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(this.view.asWidget());
	}

}
