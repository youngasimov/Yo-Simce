package com.dreamer8.yosimce.client.general;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.general.ui.GeneralView;
import com.dreamer8.yosimce.client.general.ui.GeneralView.GeneralPresenter;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class GeneralActivity extends SimceActivity implements GeneralPresenter {

	
	private final GeneralView view;
	
	public GeneralActivity(ClientFactory factory, GeneralPlace place, HashMap<String,ArrayList<String>> permisos){
		super(factory,place,permisos);
		this.view = getFactory().getGeneralView();
		this.view.setPresenter(this);
	}
	
	@Override
	public void init(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view.asWidget());
		
		view.setDetalleEstablecimientoVisivility(getPermisos().get("GeneralService").contains("getDetalleCurso"));
	}

}
