package com.dreamer8.yosimce.client.ui.resources;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface SimceResources extends ClientBundle {

	public static final SimceResources INSTANCE = GWT.create(SimceResources.class);
	
	@Source("images/logosimce.png")
	public ImageResource logoSimce();
	
	@Source("images/logosimcegrande.png")
	public ImageResource logoSimceGrande();
	
	@Source("images/dreamer8.png")
	public ImageResource dreamer();
	
	@Source("images/load.gif")
	public ImageResource load();
	
	@Source("images/barload.gif")
	public ImageResource barLoad();
	
	@Source("images/update.gif")
	public ImageResource update();
	
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
	
	@Source("images/filtro.png")
	public ImageResource filtro();
	
	@Source("images/round_trip.png")
	public ImageResource trip();
	
	@Source("images/round_trip2.png")
	public ImageResource roundTrip();
	
	@Source("images/accept_page.png")
	public ImageResource acceptPage();
}
