package com.dreamer8.yosimce.client.planandresult.activity;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.planandresult.ui.PlanAndResultSidebarView;
import com.dreamer8.yosimce.client.planandresult.ui.PlanAndResultSidebarView.PlanAndResultSidebarPresenter;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class PlanAndResultSidebarActivity extends AbstractActivity implements PlanAndResultSidebarPresenter {

	
	private final ClientFactory factory;
	private final PlanAndResultPlace place;
	private PlanAndResultSidebarView view;
	private UserDTO user;
	
	public PlanAndResultSidebarActivity(PlanAndResultPlace place, ClientFactory factory, UserDTO user){
		this.place = place;
		this.factory = factory;
		this.user = user;
		view = factory.getPlanAndResultSidebarView();
		view.setPresenter(this);
	}
	
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view.asWidget());
	}

}
