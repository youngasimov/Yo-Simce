package com.dreamer8.yosimce.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class MessageContainer extends Composite {

	private static MessageContainerUiBinder uiBinder = GWT
			.create(MessageContainerUiBinder.class);

	interface MessageContainerUiBinder extends
			UiBinder<Widget, MessageContainer> {
	}
	
	@UiField FlowPanel panel;

	public MessageContainer() {
		initWidget(uiBinder.createAndBindUi(this));
		panel.getElement().getStyle().setProperty("maxHeight", Window.getClientHeight()-60, Unit.PX);
		Window.addResizeHandler(new ResizeHandler() {
			
			@Override
			public void onResize(ResizeEvent event) {
				panel.getElement().getStyle().setProperty("maxHeight", Window.getClientHeight()-60, Unit.PX);
			}
		});
	}
	
	public void remove(Widget w){
		panel.remove(w);
	}
	
	public void add(Widget w){
		panel.insert(w, 0);
	}

}
