package com.dreamer8.yosimce.client.planificacion.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class AgendamientosViewD extends Composite implements AgendamientosView {

	private static AgendamientosViewDUiBinder uiBinder = GWT
			.create(AgendamientosViewDUiBinder.class);

	interface AgendamientosViewDUiBinder extends
			UiBinder<Widget, AgendamientosViewD> {
	}
	
	
	
	
	private AgendamientosPresenter presenter;
	
	public AgendamientosViewD() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(AgendamientosPresenter presenter) {
		this.presenter = presenter;
	}

}
