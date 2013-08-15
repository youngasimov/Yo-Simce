package com.dreamer8.yosimce.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;

public class HelperLabel extends Composite implements HasText {

	
	
	private Label label;
	private DecoratedPopupPanel simplePopup;
	private String help;
	
	public HelperLabel(){
		label = new Label();
		simplePopup = new DecoratedPopupPanel(true);
		help = "";
		initWidget(label);
		
		simplePopup.setWidth("200px");
		simplePopup.setAnimationEnabled(true);
		simplePopup.setAutoHideEnabled(true);
		simplePopup.setGlassEnabled(false);
		simplePopup.setModal(false);
		label.addStyleName("dreamer-HelperLabel");
		
		label.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				simplePopup.setWidget(new HTML(help));
				simplePopup.showRelativeTo(label);
			}
		});
	}
	
	public void setHelp(String help){
		this.help = help;
	}

	@Override
	public String getText() {
		return label.getText();
	}

	@Override
	public void setText(String text) {
		label.setText(text);
	}
}
