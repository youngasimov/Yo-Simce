package com.dreamer8.yosimce.client.actividad.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class SincronizacionesViewD extends Composite implements SincronizacionesView {

	private static SincronizacionesViewDUiBinder uiBinder = GWT
			.create(SincronizacionesViewDUiBinder.class);

	interface SincronizacionesViewDUiBinder extends
			UiBinder<Widget, SincronizacionesViewD> {
	}

	private SincronizacionesPresenter presenter;
	
	public SincronizacionesViewD() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(SincronizacionesPresenter presenter) {
		this.presenter = presenter;
	}

}
