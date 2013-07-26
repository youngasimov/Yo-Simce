package com.dreamer8.yosimce.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class EmptyView extends Composite {

	private static EmptyViewUiBinder uiBinder = GWT
			.create(EmptyViewUiBinder.class);

	interface EmptyViewUiBinder extends UiBinder<Widget, EmptyView> {
	}

	public EmptyView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
