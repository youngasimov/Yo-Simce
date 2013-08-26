package com.dreamer8.yosimce.client.actividad.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class MaterialDefectuosoViewD extends Composite implements MaterialDefectuosoView {

	private static MaterialDefectuosoViewDUiBinder uiBinder = GWT
			.create(MaterialDefectuosoViewDUiBinder.class);

	interface MaterialDefectuosoViewDUiBinder extends
			UiBinder<Widget, MaterialDefectuosoViewD> {
	}

	
	private MaterialDefectuosoPresenter presenter;
	
	public MaterialDefectuosoViewD() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(MaterialDefectuosoPresenter presenter) {
		this.presenter = presenter;
	}

}
