package com.dreamer8.yosimce.client;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.event.shared.EventBus;

public abstract class SimceCallback<T> implements AsyncCallback<T> {
	
	private EventBus eventBus;
	private boolean blocking;
	private Timer t;
	private boolean timeout;
	
	public SimceCallback(EventBus eventbus){
		this(eventbus,true);
	}
	
	public SimceCallback(EventBus eventbus, boolean blocking){
		this(eventbus,blocking,0);
	}
	
	public SimceCallback(EventBus eventbus, boolean blocking, int timeout){
		this.eventBus = eventbus;
		this.blocking = blocking;
		this.timeout = false;
		if(timeout>0){
			t = new Timer() {
				
				@Override
				public void run() {
					if(!SimceCallback.this.timeout){
						onFailure(new TimeoutException());
					}
				}
			};
			t.schedule(timeout);
		}
		eventbus.fireEvent(new WaitEvent(true,blocking));
	}
	
	@Override
	public void onFailure(Throwable caught) {
		if(!timeout){
			SimceCallback.this.timeout = true;
			eventBus.fireEvent(new WaitEvent(false,blocking));
			eventBus.fireEvent(new ErrorEvent(caught));
			failure(caught);
		}
	}
	
	@Override
	public void onSuccess(T result) {
		if(!timeout){
			if(result == null){
				onFailure(new NullPointerException("El servidor entregó un resultado nulo"));
			}else{
				SimceCallback.this.timeout = true;
				eventBus.fireEvent(new WaitEvent(false,blocking));
				success(result);
			}
		}
	}
	
	public void failure(Throwable caught){}
	
	public abstract void success(T result); 
	 
}
