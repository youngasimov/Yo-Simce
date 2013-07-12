package com.dreamer8.yosimce.client.ui.resources;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

public interface SimceResources extends ClientBundle {

	public static final SimceResources INSTANCE = GWT.create(SimceResources.class);
	
	
	public interface StyleResource extends CssResource{
		
	}
	
	@Source("simce.css")
	StyleResource style();
	
	@Source("images/load.gif")
	public ImageResource load();
	
}
