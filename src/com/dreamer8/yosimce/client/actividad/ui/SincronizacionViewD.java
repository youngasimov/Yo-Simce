package com.dreamer8.yosimce.client.actividad.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class SincronizacionViewD extends Composite implements SincronizacionView {

	private static SincronizacionViewDUiBinder uiBinder = GWT
			.create(SincronizacionViewDUiBinder.class);

	interface SincronizacionViewDUiBinder extends
			UiBinder<Widget, SincronizacionViewD> {
	}

	private SincronizacionPresenter presenter;
	
	public SincronizacionViewD() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPrenseter(SincronizacionPresenter presenter) {
		this.presenter = presenter;
	}

}
