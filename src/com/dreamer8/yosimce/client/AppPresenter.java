package com.dreamer8.yosimce.client;

import com.dreamer8.yosimce.client.ui.AppView;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class AppPresenter implements AppView.AppPresenter {

	private final ClientFactory factory;
	private final AppView view;
	private Place place;
	
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
		
		factory.getEventBus().addHandler(PlaceChangeEvent.TYPE, new PlaceChangeEvent.Handler() {
			
			@Override
			public void onPlaceChange(PlaceChangeEvent event) {
				place = event.getNewPlace();
				if(!event.getNewPlace().getClass().equals(SimcePlace.class)){
					view.setSidebarPanelState(false);
				}
			}
		});
		
		factory.getEventBus().addHandler(PermisosEvent.TYPE,new PermisosEvent.PermisosHandler() {
			
			@Override
			public void onPermisos(PermisosEvent event) {
				if(place.getClass().equals(SimcePlace.class)){
					view.setSidebarPanelState(true);
				}
			}
		});
		
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
