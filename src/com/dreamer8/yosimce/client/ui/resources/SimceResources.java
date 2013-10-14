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
	
	@Source("images/logosimce.png")
	public ImageResource logoSimce();
	
	@Source("images/logosimcegrande.jpg")
	public ImageResource logoSimceGrande();
	
	@Source("images/dreamer8.png")
	public ImageResource dreamer();
	
	@Source("images/load.gif")
	public ImageResource load();
	
	@Source("images/barload.gif")
	public ImageResource barLoad();
	
	@Source("images/update.gif")
	public ImageResource update();
	
	@Source("images/arrow_left.png")
	public ImageResource arrowLeft();
	
	@Source("images/arrow_right.png")
	public ImageResource arrowRight();
	
	@Source("images/error.png")
	public ImageResource error();
	
	@Source("images/ok.png")
	public ImageResource ok();
	
	@Source("images/popup.png")
	public ImageResource popup();
	
	@Source("images/gear.png")
	public ImageResource gear();
	
	@Source("images/msg_error.png")
	public ImageResource msgError();
	
	@Source("images/msg_warning.png")
	public ImageResource msgWarning();
	
	@Source("images/msg_ok.png")
	public ImageResource msgOk();
	
	@Source("images/msg_locked.png")
	public ImageResource msgLocked();
	
	@Source("images/close.png")
	public ImageResource close();
	
	@Source("images/pdfdownload.png")
	public ImageResource pdfDownload();
	
	@Source("images/logout.png")
	public ImageResource logout();
	
	@Source("images/star.png")
	public ImageResource star();
	
	@Source("images/filtro.png")
	public ImageResource filtro();
}
