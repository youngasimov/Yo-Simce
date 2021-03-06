package com.dreamer8.yosimce.client;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.event.shared.EventBus;

public abstract class SimceCallback<T> implements AsyncCallback<T> {
	
	private EventBus eventBus;
	private boolean blocking;
	private boolean finish;
	private boolean manageThrows;
	
	public SimceCallback(EventBus eventbus){
		this(eventbus,true);
	}
	
	public SimceCallback(EventBus eventbus, boolean blocking){
		this(eventbus,blocking,35000);
	}
	
	public SimceCallback(EventBus eventbus, boolean blocking, int timeout){
		this(eventbus,blocking,timeout,true);
	}
	
	public SimceCallback(EventBus eventbus, boolean blocking, int timeout,boolean manageThrows){
		finish = false;
		this.eventBus = eventbus;
		this.blocking = blocking;
		this.manageThrows = manageThrows;
		if(timeout>0){
			Timer t = new Timer() {
				
				@Override
				public void run() {
					if(!finish){
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
		if(!finish){
			finish = true;
			eventBus.fireEvent(new WaitEvent(false,blocking));
			if(manageThrows){
				eventBus.fireEvent(new ErrorEvent(caught));
			}
			failure(caught);
		}
	}
	
	@Override
	public void onSuccess(T result) {
		if(result == null){
			onFailure(new NullPointerException("El servidor entregó un resultado nulo"));
		}else if(!finish){
			finish = true;
			eventBus.fireEvent(new WaitEvent(false,blocking));
			success(result);
		}
	}
	
	public void failure(Throwable caught){}
	
	public abstract void success(T result); 
	 
}
