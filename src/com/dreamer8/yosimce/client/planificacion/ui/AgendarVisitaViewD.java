package com.dreamer8.yosimce.client.planificacion.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class AgendarVisitaViewD extends Composite implements AgendarVisitaView {

	private static AgendarVisitaViewDUiBinder uiBinder = GWT
			.create(AgendarVisitaViewDUiBinder.class);

	interface AgendarVisitaViewDUiBinder extends
			UiBinder<Widget, AgendarVisitaViewD> {
	}
	
	private AgendarVisitaPresenter presenter;

	public AgendarVisitaViewD() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(AgendarVisitaPresenter presenter) {
		this.presenter = presenter;
	}
}
