package com.dreamer8.yosimce.client.general.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class DetalleCursoViewD extends Composite implements DetalleCursoView{

	private static DetalleCursoViewDUiBinder uiBinder = GWT
			.create(DetalleCursoViewDUiBinder.class);

	interface DetalleCursoViewDUiBinder extends
			UiBinder<Widget, DetalleCursoViewD> {
	}

	private DetalleCursoPresenter presenter;
	
	public DetalleCursoViewD() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(DetalleCursoPresenter presenter) {
		this.presenter = presenter;
	}
}
