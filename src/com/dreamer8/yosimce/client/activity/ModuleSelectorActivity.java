package com.dreamer8.yosimce.client.activity;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.planandresult.activity.PlanAndResultPlace;
import com.dreamer8.yosimce.client.ui.ModuleSelectorView;
import com.dreamer8.yosimce.client.ui.ModuleSelectorView.ModuleSelectorPresenter;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class ModuleSelectorActivity extends AbstractActivity implements ModuleSelectorPresenter {

	private ModuleSelectorView view;
	private ClientFactory factory;
	private UserDTO user;
	
	public ModuleSelectorActivity(ClientFactory factory, UserDTO user){
		this.factory = factory;
		this.user = user;
		view = factory.getModuleSelectorView();
		view.setPresenter(this);
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view.asWidget());
	}

	@Override
	public void onPlanAndResultButtonClick() {
		factory.getPlaceController().goTo(new PlanAndResultPlace());
	}
}