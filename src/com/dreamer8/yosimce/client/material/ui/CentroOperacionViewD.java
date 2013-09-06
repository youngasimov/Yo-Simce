package com.dreamer8.yosimce.client.material.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class CentroOperacionViewD extends Composite {

	private static CentroOperacionViewDUiBinder uiBinder = GWT
			.create(CentroOperacionViewDUiBinder.class);

	interface CentroOperacionViewDUiBinder extends
			UiBinder<Widget, CentroOperacionViewD> {
	}

	public CentroOperacionViewD() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
