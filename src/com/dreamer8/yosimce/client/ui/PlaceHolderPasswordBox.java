package com.dreamer8.yosimce.client.ui;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.PasswordTextBox;

public class PlaceHolderPasswordBox extends PasswordTextBox {

	private String placeholder;
	
	public PlaceHolderPasswordBox() {
	}
	
	public PlaceHolderPasswordBox(Element element) {
		super(element);
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
		getElement().setAttribute("placeholder", placeholder);
	}
	
}
