package com.dreamer8.yosimce.client.administracion;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.administracion.ui.AdminUsuariosView;
import com.dreamer8.yosimce.client.administracion.ui.AdminUsuariosView.AdminUsuariosPresenter;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class AdminUsuariosActivity extends SimceActivity implements
		AdminUsuariosPresenter {

	
	private AdminUsuariosView view;
	private AdminUsuariosPlace place;
	
	public AdminUsuariosActivity(ClientFactory factory, AdminUsuariosPlace place,
			HashMap<String, ArrayList<String>> permisos) {
		super(factory, place, permisos);
		this.place = place;
		this.view = getFactory().getAdminUsuariosView();
		this.view.setPresenter(this);
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		super.start(panel, eventBus);
		panel.setWidget(view.asWidget());
	}

}
