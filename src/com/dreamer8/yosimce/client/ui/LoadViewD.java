package com.dreamer8.yosimce.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class LoadViewD extends Composite implements LoadView {

	private static LoadViewDUiBinder uiBinder = GWT
			.create(LoadViewDUiBinder.class);

	interface LoadViewDUiBinder extends UiBinder<Widget, LoadViewD> {
	}

	@UiField HTML message;
	
	public LoadViewD() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setMessage(String message) {
		this.message.setHTML(message);
	}

}
