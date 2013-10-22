package com.dreamer8.yosimce.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.dreamer8.yosimce.client.ui.LoginView;
import com.dreamer8.yosimce.client.ui.LoginView.LoginPresenter;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.http.client.RequestTimeoutException;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.ui.SimplePanel;
import com.seanchenxi.gwt.storage.client.StorageExt;

public class LoginActivity implements LoginPresenter {

	
	private LoginView view;
	private LoginServiceAsync service;
	private UserDTO user;
	private Logger logger = Logger.getLogger("");
	//private SimceApp app;
	private SimplePanel panel;
	private boolean start;
	
	public LoginActivity(LoginServiceAsync service, LoginView view){
		this.service = service;
		this.view = view;
		this.view.setPresenter(this);
		start = false;
	}
	
	@Override
	public void onLogin(final String username,final String password) {
		view.showLoad();
		view.setMensaje("Comprobando usuario registrado...");
		Timer t = new Timer() {
			
			@Override
			public void run() {
				service.getTrackingUser(username, password, new AsyncCallback<UserDTO>() {

					@Override
					public void onFailure(Throwable caught) {
						loginError(caught);
					}

					@Override
					public void onSuccess(UserDTO result) {
						user =result;
						start = true;
						loadApp();
						/*if(app!=null){
							app.start(panel, user);
						}*/
					}
				});
			}
		};
		t.schedule(700);
		
	}

	@Override
	public void start(final SimplePanel panel) {
		this.panel = panel;
		this.panel.setWidget(view.asWidget());
		start = false;
		view.showLoad();
		view.setMensaje("Comprobando sesión de usuario...");
		service.getYoSimceUser(new AsyncCallback<UserDTO>() {

			@Override
			public void onSuccess(UserDTO result) {
				user = result;
				view.setMensaje(user.getNombres()+" "+user.getApellidoPaterno()+",<br />Cargando aplicación...");
				start = true;
				loadApp();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				start = false;
				loadApp();
				loginError(null);
			}
		});
	}
	
	private void loadApp(){
		GWT.runAsync(new RunAsyncCallback() {
			
			@Override
			public void onSuccess() {
				if(!start){
					return;
				}
				SimceApp app = new SimceApp();
				app.start(panel,user);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				loginError(caught);
			}
		});
	}
	
	private void loginError(Throwable caught){
		if(caught!=null){
			logger.log(Level.WARNING, caught.getMessage());
		}
		StorageExt storage = StorageExt.getLocalStorage();
		if(storage!=null){
			storage.clear();
		}
		view.showLogin();
		user = null;
		if(caught == null){
			view.setMensaje("");
			view.setUsernameFocus();
		}else if(caught instanceof RequestTimeoutException){
			view.setMensaje("Estamos trabajando al 100%<br />Intente ingresar nuevamente");
			view.setPasswordFocus();
		}else if(caught instanceof IncompatibleRemoteServiceException){
			view.setMensaje("La aplicación web esta desactualizada<br />limpie el cache y recargue el sitio");
			view.setPasswordFocus();
		}else{
			view.setMensaje(caught.getMessage());
			view.setPasswordFocus();
		}
	}

}
