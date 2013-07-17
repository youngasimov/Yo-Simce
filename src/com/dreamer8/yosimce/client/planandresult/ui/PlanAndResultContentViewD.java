package com.dreamer8.yosimce.client.planandresult.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class PlanAndResultContentViewD extends Composite implements PlanAndResultContentView {

	private static PlanAndResultContentViewDUiBinder uiBinder = GWT
			.create(PlanAndResultContentViewDUiBinder.class);

	interface PlanAndResultContentViewDUiBinder extends
			UiBinder<Widget, PlanAndResultContentViewD> {
	}

	private PlanAndResultContentPresenter presenter;
	
	public PlanAndResultContentViewD() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(PlanAndResultContentPresenter presenter) {
		this.presenter = presenter;
	}

}
