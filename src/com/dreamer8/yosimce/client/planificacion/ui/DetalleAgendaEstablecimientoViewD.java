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

public class DetalleAgendaEstablecimientoViewD extends Composite implements DetalleAgendaEstablecimientoView{

	private static DetalleAgendaEstablecimientoViewDUiBinder uiBinder = GWT
			.create(DetalleAgendaEstablecimientoViewDUiBinder.class);

	interface DetalleAgendaEstablecimientoViewDUiBinder extends
			UiBinder<Widget, DetalleAgendaEstablecimientoViewD> {
	}

	
	private DetalleAgendaEstablecimientoPresenter presenter;
	
	public DetalleAgendaEstablecimientoViewD() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(DetalleAgendaEstablecimientoPresenter presenter) {
		this.presenter = presenter;
	}
}