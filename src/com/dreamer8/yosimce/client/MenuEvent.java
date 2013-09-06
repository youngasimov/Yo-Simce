package com.dreamer8.yosimce.client;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class MenuEvent extends GwtEvent<MenuEvent.MenuHandler> {

	public static final Type<MenuHandler> TYPE = new Type<MenuHandler>();
	
	public interface MenuHandler extends EventHandler{
		void onMenu(MenuEvent event);
	}
	
	private boolean open;
	
	public MenuEvent(boolean open){
		this.open = open;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}




	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<MenuHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(MenuHandler handler) {
		handler.onMenu(this);
	}
	
	
	
}
