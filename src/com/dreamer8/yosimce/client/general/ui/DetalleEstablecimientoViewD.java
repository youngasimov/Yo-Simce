package com.dreamer8.yosimce.client.general.ui;

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

public class DetalleEstablecimientoViewD extends Composite implements DetalleEstablecimientoView{

	private static DetalleEstablecimientoViewDUiBinder uiBinder = GWT
			.create(DetalleEstablecimientoViewDUiBinder.class);

	interface DetalleEstablecimientoViewDUiBinder extends
			UiBinder<Widget, DetalleEstablecimientoViewD> {
	}

	private DetalleEstablecimientoPresenter presenter;
	
	public DetalleEstablecimientoViewD() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(DetalleEstablecimientoPresenter presenter) {
		this.presenter = presenter;
	}
}
