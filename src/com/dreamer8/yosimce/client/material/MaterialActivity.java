package com.dreamer8.yosimce.client.material;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.material.ui.MaterialView;
import com.dreamer8.yosimce.client.material.ui.MaterialView.MaterialPresenter;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class MaterialActivity extends SimceActivity implements
		MaterialPresenter {

	private final MaterialView view;
	
	public MaterialActivity(ClientFactory factory, MaterialPlace place,
			HashMap<String, ArrayList<String>> permisos) {
		super(factory, place, permisos);
		this.view = getFactory().getMaterialView();
		this.view.setPresenter(this);
	}
	
	@Override
	public void init(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view.asWidget());
	}

}
