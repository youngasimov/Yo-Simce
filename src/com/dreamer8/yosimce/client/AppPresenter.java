package com.dreamer8.yosimce.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.dreamer8.yosimce.client.ui.AppView;
import com.dreamer8.yosimce.shared.exceptions.ConsistencyException;
import com.dreamer8.yosimce.shared.exceptions.DBException;
import com.dreamer8.yosimce.shared.exceptions.NoAllowedException;
import com.dreamer8.yosimce.shared.exceptions.NoLoggedException;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.event.shared.UmbrellaException;
import com.google.gwt.http.client.RequestTimeoutException;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.SerializedTypeViolationException;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.rpc.StatusCodeException;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class AppPresenter implements AppView.AppPresenter {

	private final ClientFactory factory;
	private final AppView view;
	
	private int blockingEvents;
	private int nonBlockingEvents;
	
	private boolean notLogged;
	private boolean menuOpen;
	
	private Logger logger = Logger.getLogger("");
	
	public AppPresenter(ClientFactory factory){
		this.factory = factory;
		this.view = factory.getAppView();
		this.view.setPresenter(this);
		blockingEvents = 0;
		nonBlockingEvents = 0;
		notLogged = false;
		menuOpen = false;
		bind();
	}
	
	
	@Override
	public void setDisplay(AcceptsOneWidget panel) {
		panel.setWidget(view.asWidget());
	}
	
	@Override
	public void onMouseOutFromPanel() {
		menuOpen = false;
		view.setSidebarPanelState(menuOpen);
	}
	
	private void bind(){
		
		GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			
			@Override
			public void onUncaughtException(Throwable e) {
				Throwable unwrapped = unwrap(e);
				errorHandler(unwrapped);
			}
			
			public Throwable unwrap(Throwable e) {   
			    if(e instanceof UmbrellaException) {   
			      UmbrellaException ue = (UmbrellaException) e;  
			      if(ue.getCauses().size() == 1) {   
			        return unwrap(ue.getCauses().iterator().next());  
			      }  
			    }  
			    return e;  
			  }
		});
		
		factory.getEventBus().addHandler(MenuEvent.TYPE, new MenuEvent.MenuHandler() {
			
			@Override
			public void onMenu(MenuEvent event) {
				menuOpen = event.isOpen();
				view.setSidebarPanelState(menuOpen);
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
				errorHandler(event.getError());
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
	
	private void errorHandler(Throwable e){
		
		if(e instanceof IncompatibleRemoteServiceException){
			view.showErrorMessage("La aplicación web esta desactualizada<br />limpie el cache y recargue el sitio", false);
			logger.log(Level.WARNING, e.getLocalizedMessage());
		}else if(e instanceof NullPointerException){
			view.showErrorMessage(e.getLocalizedMessage(), true);
			logger.log(Level.SEVERE, e.getLocalizedMessage());
		}else if(e instanceof InvocationException){
			view.showErrorMessage("La petición al servidor presentó problemas, esto puede deberse a:<br />1)No hay conexión al servidor<br />2)El servidor no está disponible", false);
			logger.log(Level.WARNING, e.getLocalizedMessage());
		}else if(e instanceof SerializedTypeViolationException){
			view.showErrorMessage("Tipo de dato inesperado", true);
			logger.log(Level.SEVERE, e.getLocalizedMessage());
		}else if(e instanceof StatusCodeException){
			view.showErrorMessage("El código del mensaje HTTP es inválido<br />"+e.getLocalizedMessage(), true);
			logger.log(Level.SEVERE, e.getLocalizedMessage());
		}else if(e instanceof NoAllowedException){
			view.showPermisoMessage(e.getLocalizedMessage(), true);
			logger.log(Level.INFO, e.getLocalizedMessage());
		}else if(e instanceof DBException){
			view.showErrorMessage(e.getLocalizedMessage(), false);
			logger.log(Level.WARNING, e.getLocalizedMessage());
		}else if(e instanceof RequestTimeoutException){
			view.showErrorMessage("El tiempo máximo de espera de respuesta se ha excedido", true);
			logger.log(Level.SEVERE, e.getLocalizedMessage());
		}else if(e instanceof ConsistencyException){
			view.showWarningMessage(e.getMessage(), false);
			logger.log(Level.INFO, e.getLocalizedMessage());
		}else if(e instanceof NoLoggedException && !notLogged){
			logger.log(Level.SEVERE, e.getLocalizedMessage());
			notLogged = true;
			view.openLoginPopup();
		}else{
			logger.log(Level.SEVERE, e.getLocalizedMessage());
		}
	}

}
