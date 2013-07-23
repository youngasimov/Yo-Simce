package com.dreamer8.yosimce.client;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class ErrorEvent extends GwtEvent<ErrorEvent.ErrorHandler> {
	
	public static final Type<ErrorHandler> TYPE = new Type<ErrorHandler>();
	
	private Throwable error;
	
	public ErrorEvent(Throwable error) {
		super();
		this.error = error;
	}

	public Throwable getError() {
		return error;
	}

	@Override
	public Type<ErrorHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ErrorEvent.ErrorHandler handler) {
		handler.onError(this);
	}
	
	public interface ErrorHandler extends EventHandler {
		void onError(ErrorEvent event);
	}

}
