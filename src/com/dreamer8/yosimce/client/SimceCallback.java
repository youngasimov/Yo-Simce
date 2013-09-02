package com.dreamer8.yosimce.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.event.shared.EventBus;

public abstract class SimceCallback<T> implements AsyncCallback<T> {
	
	private EventBus eventBus;
	private boolean blocking;
	
	public SimceCallback(EventBus eventbus){
		this(eventbus,true);
	}
	
	public SimceCallback(EventBus eventbus, boolean blocking){
		this.eventBus = eventbus;
		this.blocking = blocking;
		eventbus.fireEvent(new WaitEvent(true,blocking));
	}
	
	@Override
	public void onFailure(Throwable caught) {
		eventBus.fireEvent(new WaitEvent(false,blocking));
		eventBus.fireEvent(new ErrorEvent(caught));
		failure(caught);
	}
	
	@Override
	public void onSuccess(T result) {
		if(result == null){
			onFailure(new NullPointerException("El servidor entregó un resultado nulo"));
		}else{
			eventBus.fireEvent(new WaitEvent(false,blocking));
			success(result);
		}
	}
	
	public void failure(Throwable caught){}
	
	public abstract void success(T result); 
	 
}
