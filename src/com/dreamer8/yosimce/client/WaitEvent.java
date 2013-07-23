package com.dreamer8.yosimce.client;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class WaitEvent extends GwtEvent<WaitEvent.WaitHandler> {

	public static final Type<WaitHandler> TYPE = new Type<WaitHandler>();
	
	private boolean wait;
	
	public WaitEvent(boolean wait) {
		super();
		this.wait = wait;
	}

	public boolean isWait() {
		return wait;
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
