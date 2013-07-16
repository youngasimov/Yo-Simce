package com.dreamer8.yosimce.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class NotLoggedViewD extends Composite implements NotLoggedView{

	private static NotLoggedViewDUiBinder uiBinder = GWT
			.create(NotLoggedViewDUiBinder.class);

	interface NotLoggedViewDUiBinder extends UiBinder<Widget, NotLoggedViewD> {
	}
	
	@UiField Label message;
	@UiField Anchor link;
	
	
	public NotLoggedViewD() {
		initWidget(uiBinder.createAndBindUi(this));
	}


	@Override
	public void setMessage(String message) {
		this.message.setText(message);
	}


	@Override
	public void setLink(String url, String message) {
		this.link.setHref(url);
		link.setText("Ingrersar a YoSimce");
		
	}

}
