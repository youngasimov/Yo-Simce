package com.dreamer8.yosimce.client;

import com.dreamer8.yosimce.client.ui.AppView;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class AppPresenter implements AppView.AppPresenter {

	private final ClientFactory factory;
	private final AppView view;
	
	public AppPresenter(ClientFactory factory){
		this.factory = factory;
		this.view = factory.getAppView();
		bind();
	}
	
	
	@Override
	public void setDisplay(AcceptsOneWidget panel) {
		panel.setWidget(view.asWidget());
	}
	
	private void bind(){
		factory.getEventBus().addHandler(ErrorEvent.TYPE, new ErrorEvent.ErrorHandler() {
			
			@Override
			public void onError(ErrorEvent event) {
				
			}
		});
		
		factory.getEventBus().addHandler(WaitEvent.TYPE, new WaitEvent.WaitHandler() {
			
			@Override
			public void onWait(WaitEvent event) {
				
			}
		});
	}

}
