package com.dreamer8.yosimce.client;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class SoundNotificationEvent extends GwtEvent<SoundNotificationEvent.SoundNotificationHandler> {

	public static final Type<SoundNotificationHandler> TYPE = new Type<SoundNotificationHandler>();
	public static final int NOTIFICACION = 1;
	public static final int ERROR = 2;
	
	interface SoundNotificationHandler extends EventHandler{
		void onSoundNotification(SoundNotificationEvent event);
	}
	
	private int tipo;
	
	public SoundNotificationEvent(int tipo){
		this.tipo = tipo;
	}

	public int getTipo() {
		return tipo;
	}

	@Override
	public Type<SoundNotificationHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SoundNotificationHandler handler) {
		handler.onSoundNotification(this);
	}
	
}
