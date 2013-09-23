package com.dreamer8.yosimce.client.administracion.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ReportesViewD extends Composite {

	private static ReportesViewDUiBinder uiBinder = GWT
			.create(ReportesViewDUiBinder.class);

	interface ReportesViewDUiBinder extends UiBinder<Widget, ReportesViewD> {
	}

	public ReportesViewD() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
