package com.dreamer8.yosimce.client;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.dreamer8.yosimce.client.ui.AppView;
import com.dreamer8.yosimce.shared.exceptions.ConsistencyException;
import com.dreamer8.yosimce.shared.exceptions.DBException;
import com.dreamer8.yosimce.shared.exceptions.NoAllowedException;
import com.dreamer8.yosimce.shared.exceptions.NoLoggedException;
import com.google.gwt.http.client.RequestTimeoutException;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
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
	
	private Date update;
	
	private DateTimeFormat format;
	
	private Timer t2;
	
	private Logger logger = Logger.getLogger("");
	
	public AppPresenter(ClientFactory factory){
		this.factory = factory;
		this.view = factory.getAppView();
		this.view.setPresenter(this);
		blockingEvents = 0;
		nonBlockingEvents = 0;
		notLogged = false;
		menuOpen = false;
		format = DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_TIME_MEDIUM);
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
	
	@Override
	public void onLogout() {
		factory.getLoginService().logout(new AsyncCallback<Boolean>() {
			
			@Override
			public void onSuccess(Boolean result) {
				logout();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				logout();
			}
		});
	}
	
	private void logout(){
		Cookies.removeCookie(YoSimce.TOKEN_COOKIE);
		Cookies.removeCookie(YoSimce.TOKEN_COOKIE_DEMO);
		notLogged = true;
		view.openLoginPopup("<br /><br />Su sesión se ha cerrado con éxito<br /><br />",
				"<br /><br />Para volver a ingresar diríjase a YoSimce");
	}
	
	@SuppressWarnings("deprecation")
	private void bind(){
		
		Timer t = new Timer(){

			@Override
			public void run() {
				factory.getLoginService().keepAlive(new AsyncCallback<Integer>() {
					
					@Override
					public void onSuccess(Integer result) {
						
						if(result == LoginService.SESION_ACTIVA){
							update = null;
						}
						
						if(result == LoginService.SESION_INACTIVA && !notLogged){
							notLogged = true;
							update = null;
							view.openLoginPopup("Al parecer no se encuentra logueado o su sesión se cerró inesperadamente.<br /><br />Diríjase al sitio principal de YoSimce e ingrese nuevamente.<br /><br /><br />",
									"Si desea volver al mismo lugar, copie la URL del navegador antes de ir a YoSimce");
						}else if(result == LoginService.PRONTA_ACTUALIZACION && update == null){
							factory.getLoginService().getActualizacionDate(new AsyncCallback<String>(){

								@Override
								public void onFailure(Throwable caught) {
									logger.log(Level.SEVERE, caught.getLocalizedMessage());
								}

								@Override
								public void onSuccess(String result) {
									if(result == null || result.isEmpty()){
										update = null;
									}else{
										update = format.parse(result);
										t2 = new Timer() {
											
											@Override
											public void run() {
												
												if(update == null){
													t2.cancel();
													return;
													
												}
												
												Date d = new Date();
												if(d.compareTo(update)<1){
													int days = update.getDay() - d.getDay();
													int hours = update.getHours() - d.getHours();
													int mins = update. getMinutes() - d.getMinutes();
													factory.getEventBus().fireEvent(new MensajeEvent("Se ha programado una actualización del sistema Tracking para dentro de <br />"+days+" días, <br />"+hours+" horas, <br />"+mins+" minutos,<br /> si presenta problemas, refresque el navegador para actulizar la aplicación",MensajeEvent.MSG_WARNING,true));
												}else{
													factory.getEventBus().fireEvent(new MensajeEvent("Se ha programado una actualización del sistema Tracking que debería estar pronta a realizarse,<br /> si presenta problemas, refresque el navegador para actulizar la aplicación",MensajeEvent.MSG_WARNING,true));
												}
											}
										};
										t2.scheduleRepeating(30000);
										
										Date d = new Date();
										if(d.compareTo(update)<1){
											int days = update.getDay() - d.getDay();
											int hours = update.getHours() - d.getHours();
											int mins = update. getMinutes() - d.getMinutes();
											factory.getEventBus().fireEvent(new MensajeEvent("Se ha programado una actualización del sistema Tracking para dentro de <br />"+days+" días, <br />"+hours+" horas, <br />"+mins+" minutos,<br /> si presenta problemas, refresque el navegador para actulizar la aplicación",MensajeEvent.MSG_WARNING,true));
										}else{
											factory.getEventBus().fireEvent(new MensajeEvent("Se ha programado una actualización del sistema Tracking que debería estar pronta a realizarse,<br /> si presenta problemas, refresque el navegador para actulizar la aplicación",MensajeEvent.MSG_WARNING,true));
										}
									}
								}
							});
						}
					}
					
					@Override
					public void onFailure(Throwable caught) {
						logger.log(Level.SEVERE, caught.getLocalizedMessage());
					}
				});
			}};
		t.scheduleRepeating(300000);
		/*
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
		});*/
		
		
		factory.getEventBus().addHandler(PlaceChangeEvent.TYPE, new PlaceChangeEvent.Handler() {

			@Override
			public void onPlaceChange(PlaceChangeEvent event) {
				menuOpen = false;
				view.setSidebarPanelState(menuOpen);
				
				if(event.getNewPlace() instanceof SimcePlace){
					SimcePlace sp = (SimcePlace)event.getNewPlace();
					if(sp.getAplicacionId()==1){
						view.setManualHref(Window.Location.getProtocol()+"//"+Window.Location.getHost()+"/manual_simce.pdf");
					}else if(sp.getAplicacionId()==2){
						view.setManualHref(Window.Location.getProtocol()+"//"+Window.Location.getHost()+"/manual_simce_tic.pdf");
					}
				}
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
		}else if(e instanceof TimeoutException){
			view.showErrorMessage(e.getLocalizedMessage(), true);
			logger.log(Level.SEVERE, e.getLocalizedMessage());
		}else if(e instanceof ConsistencyException){
			view.showWarningMessage(e.getMessage(), false);
			logger.log(Level.INFO, e.getLocalizedMessage());
		}else if(e instanceof NoLoggedException && !notLogged){
			logger.log(Level.SEVERE, e.getLocalizedMessage());
			notLogged = true;
			view.openLoginPopup("Al parecer no se encuentra logueado o su sesión se cerró inesperadamente.<br /><br />Diríjase al sitio principal de YoSimce e ingrese nuevamente.<br /><br /><br />",
					"Si desea volver al mismo lugar, copie la URL del navegador antes de ir a YoSimce");
		}else{
			logger.log(Level.SEVERE, e.getLocalizedMessage());
		}
	}

}
