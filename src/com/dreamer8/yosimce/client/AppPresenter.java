package com.dreamer8.yosimce.client;

import com.dreamer8.yosimce.client.ui.AppView;
import com.dreamer8.yosimce.shared.exceptions.ConsistencyException;
import com.dreamer8.yosimce.shared.exceptions.DBException;
import com.dreamer8.yosimce.shared.exceptions.NoAllowedException;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class AppPresenter implements AppView.AppPresenter {

	private final ClientFactory factory;
	private final AppView view;
	private Place place;
	
	private int blockingEvents;
	private int nonBlockingEvents;
	
	public AppPresenter(ClientFactory factory){
		this.factory = factory;
		this.view = factory.getAppView();
		blockingEvents = 0;
		nonBlockingEvents = 0;
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
		
		factory.getEventBus().addHandler(MensajeEvent.TYPE, new MensajeEvent.MensajeHandler() {
			
			@Override
			public void onMensaje(MensajeEvent event) {
				if(event.getTipo() == MensajeEvent.MSG_OK){
					view.showOkMessage(event.getMensaje(), event.isAutoClose());
				}else if(event.getTipo() == MensajeEvent.MSG_WARNING){
					view.showWarningMessage(event.getMensaje(), event.isAutoClose());
				}else if(event.getTipo() == MensajeEvent.MSG_ERROR){
					view.showErrorMessage(event.getMensaje(), event.isAutoClose());
				}else if(event.getTipo() == MensajeEvent.MSG_PERMISOS){
					view.showPermisoMessage(event.getMensaje(), event.isAutoClose());
				}
			}
		});
		
		factory.getEventBus().addHandler(ErrorEvent.TYPE, new ErrorEvent.ErrorHandler() {
			
			@Override
			public void onError(ErrorEvent event) {
				if(event.getError() instanceof NoAllowedException){
					view.showPermisoMessage(event.getError().getMessage(), true);
				}else if(event.getError() instanceof DBException){
					view.showErrorMessage(event.getError().getMessage(), true);
				}else if(event.getError() instanceof TimeoutException){
					view.showWarningMessage(event.getError().getMessage(), true);
				}else if(event.getError() instanceof ConsistencyException){
					view.showWarningMessage(event.getError().getMessage(), true);
				}
			}
		});
		
		factory.getEventBus().addHandler(WaitEvent.TYPE, new WaitEvent.WaitHandler() {
			
			@Override
			public void onWait(WaitEvent event) {
				if(event.isBlocking()){
					blockingEvents += (event.isWait())?1:-1;
				}else{
					nonBlockingEvents += (event.isWait())?1:-1;
				}
				view.setCirularLoadVisibility(blockingEvents>0);
				view.setBarLoadVisibility(nonBlockingEvents>0);
			}
		});
	}

}
