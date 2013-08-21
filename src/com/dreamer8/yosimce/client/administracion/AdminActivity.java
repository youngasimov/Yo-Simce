package com.dreamer8.yosimce.client.administracion;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.SimcePlace;
import com.dreamer8.yosimce.client.administracion.ui.AdminView;
import com.dreamer8.yosimce.client.administracion.ui.AdminView.AdminPresenter;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class AdminActivity extends SimceActivity implements AdminPresenter {

	
	private final AdminView view;
	
	public AdminActivity(ClientFactory factory, SimcePlace place,
			HashMap<String, ArrayList<String>> permisos) {
		super(factory, place, permisos);
		view = getFactory().getAdminView();
		view.setPresenter(this);
	}
	
	@Override
	public void init(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view.asWidget());
	}
}
