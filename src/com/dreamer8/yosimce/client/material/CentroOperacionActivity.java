package com.dreamer8.yosimce.client.material;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.SimcePlace;
import com.dreamer8.yosimce.client.material.ui.CentroOperacionView;
import com.dreamer8.yosimce.client.material.ui.CentroOperacionView.CentroOperacionPresenter;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class CentroOperacionActivity extends SimceActivity implements
		CentroOperacionPresenter {

	private CentroOperacionView view;
	
	public CentroOperacionActivity(ClientFactory factory, SimcePlace place,
			HashMap<String, ArrayList<String>> permisos) {
		super(factory, place, permisos,true);
		this.view = getFactory().getCentroOperacionView();
		this.view.setPresenter(this);
	}

	@Override
	public void init(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view.asWidget());
	}

}
