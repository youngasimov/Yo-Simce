package com.dreamer8.yosimce.client.material;

import com.dreamer8.yosimce.client.ErrorEvent;
import com.dreamer8.yosimce.client.MensajeEvent;
import com.dreamer8.yosimce.client.TimeoutException;
import com.dreamer8.yosimce.client.WaitEvent;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.event.shared.EventBus;

public abstract class MaterialCallback<T> implements AsyncCallback<T> {

	private EventBus eventBus;
	private boolean blocking;
	private boolean finish;
	private boolean manageThrows;
	private boolean fromLocaStorage;
	private String key;
	
	public MaterialCallback(EventBus eventbus){
		this(eventbus,true);
	}
	
	public MaterialCallback(EventBus eventbus, boolean blocking){
		this(eventbus,blocking,60000);
	}
	
	public MaterialCallback(EventBus eventbus, boolean blocking, int timeout){
		this(eventbus,blocking,timeout,"",true);
	}
	
	public MaterialCallback(EventBus eventbus, boolean blocking, String key){
		this(eventbus,blocking,60000,key,true);
	}
	
	public MaterialCallback(EventBus eventbus, boolean blocking, int timeout,String key,boolean manageThrows){
		finish = false;
		this.key = key;
		fromLocaStorage = false;
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
		if(finish){
			return;
		}
		if(key == null || key.length()==0){
			actionOnFailure(caught);
			return;
		}
		try{
			fromLocaStorage = true;
			T t = tryFromLocalStorage(key);
			if(t == null){
				actionOnFailure(caught);
			}else{
				eventBus.fireEvent(new MensajeEvent("Los valores se obtuvieron desde una copia local",MensajeEvent.MSG_WARNING,true));
				onSuccess(t);
			}
		}catch(Throwable t){
			actionOnFailure(caught);
		}
		
	}
	
	public String getKey(){
		return key;
	}

	@Override
	public void onSuccess(T result) {
		if(result == null && !fromLocaStorage){
			onFailure(new NullPointerException("El servidor entregó un resultado nulo"));
		}else if(!finish){
			finish = true;
			eventBus.fireEvent(new WaitEvent(false,blocking));
			if(key!=null && key.length()>0){
				saveOnLocalStorage(key,result);
			}
			success(result);
		}
	}
	
	public T tryFromLocalStorage(String key){return null;}
	
	public void saveOnLocalStorage(String key,T result){};
	
	public void failure(Throwable caught){}
	
	public abstract void success(T result);
	
	private void actionOnFailure(Throwable caught){
		if(!finish){
			finish = true;
			eventBus.fireEvent(new WaitEvent(false,blocking));
			if(manageThrows){
				eventBus.fireEvent(new ErrorEvent(caught));
			}
			failure(caught);
		}
	}
}
