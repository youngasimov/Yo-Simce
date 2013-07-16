package com.dreamer8.yosimce.client.planandresult.activity;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.planandresult.ui.PlanAndResultHeaderView;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class PlansAndResultHeaderActivity extends AbstractActivity implements PlanAndResultHeaderView.Presenter{

	private ClientFactory factory;
	private PlanAndResultPlace place;
	
	public PlansAndResultHeaderActivity(PlanAndResultPlace place, ClientFactory factory){
		this.factory = factory;
		this.place = place;
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		// TODO Auto-generated method stub

	}

}
