package com.dreamer8.yosimce.client;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class WaitEvent extends GwtEvent<WaitEvent.WaitHandler> {

	public static final Type<WaitHandler> TYPE = new Type<WaitHandler>();
	
	private boolean wait;
	private boolean blocking;
	
	public WaitEvent(boolean wait, boolean blocking) {
		super();
		this.wait = wait;
		this.blocking = blocking;
	}

	public boolean isWait() {
		return wait;
	}
	
	public boolean isBlocking() {
		return blocking;
	}
	
	

	@Override
	public Type<WaitHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(WaitHandler handler) {
		handler.onWait(this);
	}
	
	public interface WaitHandler extends EventHandler {
		void onWait(WaitEvent event);
	}

}
