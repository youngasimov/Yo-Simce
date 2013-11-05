package com.dreamer8.yosimce.client.general.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

public class BingoPanel extends Composite {

	private static BingoPanelUiBinder uiBinder = GWT
			.create(BingoPanelUiBinder.class);

	interface BingoPanelUiBinder extends UiBinder<Widget, BingoPanel> {
	}

	
	@UiField FlexTable bingormTable;
	@UiField FlexTable bingor8Table;
	
	public BingoPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
