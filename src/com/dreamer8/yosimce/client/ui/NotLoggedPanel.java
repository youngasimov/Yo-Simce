package com.dreamer8.yosimce.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class NotLoggedPanel extends Composite {

	private static NotLoggedPanelUiBinder uiBinder = GWT
			.create(NotLoggedPanelUiBinder.class);

	interface NotLoggedPanelUiBinder extends UiBinder<Widget, NotLoggedPanel> {
	}
	
	@UiField ImageButton yosimceButton;
	@UiField Button trackingButton;
	@UiField HTML mensaje1;
	@UiField HTML mensaje2;

	public NotLoggedPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiHandler("yosimceButton")
	void onIrAYoSimceClick(ClickEvent event){
		Window.open("http://www.yosimce.cl", "_self", "");
	}
	
	@UiHandler("trackingButton")
	void onIrATrackingClick(ClickEvent event){
		Window.open("http://tracking.yosimce.cl", "_self", "");
	}

}
