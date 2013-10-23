package com.dreamer8.yosimce.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public abstract class ActivityAsyncProxy<A extends SimceActivity> implements Activity {

	private boolean hasAsyncBeenIssued;
	private boolean hasAsyncBeenCancelled;
	private boolean hasAsyncFailed;
	private AcceptsOneWidget display;
	private EventBus eventBus;
	private A instance;
	
	@Override
	public String mayStop() {
		 checkHasAsyncFailed();
	    if(instance != null){
	    	return this.instance.mayStop();
	    }
	    return null;
	}

	@Override
	public void onCancel() {
		if (this.instance != null) {
			this.instance.onCancel();
		}
		    checkHasAsyncFailed();
		    this.hasAsyncBeenCancelled = true;
	}

	@Override
	public void onStop() {
		checkHasAsyncFailed();
	    if(this.instance !=null){
	    	this.instance.onStop();
	    }
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		if (this.instance != null) {
		      this.instance.start(panel, eventBus);
		      return;
		    }
		    checkHasAsyncFailed();
		    assert !this.hasAsyncBeenIssued || this.hasAsyncBeenCancelled;
		    this.display = panel;
		    this.eventBus = eventBus;
		    this.hasAsyncBeenCancelled = false;
		    if (!this.hasAsyncBeenIssued) {
		      this.hasAsyncBeenIssued = true;
		      doAsync(new RunAsyncCallback() {
		 
		        @Override
		        public void onSuccess() {
		          if (!ActivityAsyncProxy.this.hasAsyncBeenCancelled) {
		            assert ActivityAsyncProxy.this.instance == null;
		            ActivityAsyncProxy.this.instance = createInstance();
		            ActivityAsyncProxy.this.instance.start(ActivityAsyncProxy.this.display, ActivityAsyncProxy.this.eventBus);
		          }
		        }
		 
		        @Override
		        public void onFailure(Throwable reason) {
		          ActivityAsyncProxy.this.hasAsyncFailed = true;
		          if (GWT.getUncaughtExceptionHandler() != null) {
		            GWT.getUncaughtExceptionHandler().onUncaughtException(reason);
		          }
		        }
		      });
		    }
	}
	
	/**
	   * Implementors should simply call {@link GWT#runAsync} here, and nothing else.
	   * <p>
	   * This is required to have a different split-point generated for each
	   * {@link ActivityAsyncProxy} sub-class.
	   */
	  protected abstract void doAsync(RunAsyncCallback callback);
	 
	  protected abstract A createInstance();
	 
	  private void checkHasAsyncFailed() {
	    if (this.hasAsyncFailed) {
	      throw new IllegalStateException("runAsync load previously failed");
	    }
	  }

}
