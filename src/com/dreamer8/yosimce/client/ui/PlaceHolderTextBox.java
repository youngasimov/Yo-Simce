package com.dreamer8.yosimce.client.ui;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.TextBox;

public class PlaceHolderTextBox extends TextBox {

	private String placeholder;
	
	public PlaceHolderTextBox() {
	}

	public PlaceHolderTextBox(Element element) {
		super(element);
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
		getElement().getStyle().setProperty("placeholder", placeholder);
	}
}
