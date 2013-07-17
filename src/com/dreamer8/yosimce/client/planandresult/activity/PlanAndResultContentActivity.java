package com.dreamer8.yosimce.client.planandresult.activity;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.planandresult.ui.PlanAndResultContentView;
import com.dreamer8.yosimce.client.planandresult.ui.PlanAndResultContentView.PlanAndResultContentPresenter;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class PlanAndResultContentActivity extends AbstractActivity implements
		PlanAndResultContentPresenter {

	
	private final ClientFactory factory;
	private final PlanAndResultPlace place;
	private PlanAndResultContentView view;
	private UserDTO user;
	
	public PlanAndResultContentActivity(PlanAndResultPlace place, ClientFactory factory, UserDTO user){
		this.place = place;
		this.factory = factory;
		this.user = user;
		view = factory.getPlanAndResultContentView();
		view.setPresenter(this);
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view.asWidget());
	}

}
