package com.dreamer8.yosimce.client.administracion;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.administracion.ui.AdminEventosView;
import com.dreamer8.yosimce.client.administracion.ui.AdminEventosView.AdminEventosPresenter;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class AdminEventosActivity extends SimceActivity implements
		AdminEventosPresenter {

	private AdminEventosView view;
	
	
	public AdminEventosActivity(ClientFactory factory, AdminEventosPlace place,HashMap<String, ArrayList<String>> permisos) {
		super(factory, place, permisos);
		view = getFactory().getAdminEventosView();
		view.setPresenter(this);
	}
	
	@Override
	public void init(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view.asWidget());
	}

	@Override
	public void onAplicacionSelected(int idAplicacion) {
		
	}

	@Override
	public void onNivelSelected(int idNivel) {
		
	}

	@Override
	public void onTipoSelected(int idTipo) {
		
	}
}
