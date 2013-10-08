package com.dreamer8.yosimce.client;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ui.MenuView;
import com.dreamer8.yosimce.client.ui.MenuView.MenuPresenter;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class MenuActivity extends SimceActivity implements MenuPresenter {

	private MenuView view;
	private SimcePlace place;
	
	public MenuActivity(ClientFactory factory, SimcePlace place,
			HashMap<String, ArrayList<String>> permisos) {
		super(factory, place, permisos);
		this.place = place;
		view = factory.getMenuView();
		view.setPresenter(this);
	}

	@Override
	public void init(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view);
		view.setInformacionVisible(place.getAplicacionId() != 2);
	}

}
