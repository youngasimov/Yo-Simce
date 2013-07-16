package com.dreamer8.yosimce.client.planandresult.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class PlanAndResultHeaderViewD extends Composite implements PlanAndResultHeaderView  {

	private static PlanAndResultHeaderViewDUiBinder uiBinder = GWT
			.create(PlanAndResultHeaderViewDUiBinder.class);

	interface PlanAndResultHeaderViewDUiBinder extends
			UiBinder<Widget, PlanAndResultHeaderViewD> {
	}

	private Presenter presenter;
	
	public PlanAndResultHeaderViewD() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(Presenter p) {
		this.presenter = p;
	}

}
