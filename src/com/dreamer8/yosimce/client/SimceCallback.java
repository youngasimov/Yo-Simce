package com.dreamer8.yosimce.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.event.shared.EventBus;

public abstract class SimceCallback<T> implements AsyncCallback<T> {
	
	private EventBus eventBus;
	
	public SimceCallback(EventBus eventbus){
		this.eventBus = eventbus;
		eventbus.fireEvent(new WaitEvent(true));
	}
	
	@Override
	public void onFailure(Throwable caught) {
		eventBus.fireEvent(new WaitEvent(false));
		eventBus.fireEvent(new ErrorEvent(caught));
		failure(caught);
	}
	
	@Override
	public void onSuccess(T result) {
		if(result == null){
			onFailure(new NullPointerException());
		}else{
			eventBus.fireEvent(new WaitEvent(false));
			success(result);
		}
	}
	
	public void failure(Throwable caught){}
	
	public abstract void success(T result); 
	 
}
