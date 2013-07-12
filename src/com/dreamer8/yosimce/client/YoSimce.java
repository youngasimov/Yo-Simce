package com.dreamer8.yosimce.client;

import com.dreamer8.yosimce.client.activity.ContentActivityMapper;
import com.dreamer8.yosimce.client.activity.HeaderActivityMapper;
import com.dreamer8.yosimce.client.activity.NotLoggedPlace;
import com.dreamer8.yosimce.client.activity.SideBarActivityMapper;
import com.dreamer8.yosimce.client.ui.LoadView;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class YoSimce implements EntryPoint {
	
	private static final String TOKEN_COOKIE = "yosimce";
	private static final String MODULE_PLANS_AND_RESULT = "plansandresult";
	
	
	private Place defaultPlace;
	private UserDTO user;
	

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		final ClientFactory factory = GWT.create(ClientFactory.class);
		
		final LoadView loadView = factory.getLoadView();
		
		loadView.setMessage("Comprobando permisos de usuario...");
		
		LoginServiceAsync loginService = (LoginServiceAsync)GWT.create(LoginService.class);
		
		String token = Cookies.getCookie(TOKEN_COOKIE);
		
		if(token == null){
			loadView.setMessage("Usuario no registrado");
			user = null;
			loadApp(factory);
		}else{
			loginService.getUser(token, new AsyncCallback<UserDTO>() {
				
				@Override
				public void onSuccess(UserDTO result) {
					loadView.setMessage(result.getName()+",<br />Descargando aplicación...");
					loadApp(factory);
				}
				
				@Override
				public void onFailure(Throwable caught) {
					loadView.setMessage("Ocurrio un problema al comprobar los permisos de usuario<br />Error: "+caught.getMessage());
					user = null;
					loadApp(factory);
				}
			});
		}
		
	}
	
	private void loadApp(ClientFactory factory){
		
		if(user == null){
			defaultPlace = new NotLoggedPlace();
			String token = History.getToken();
		}
		
		GWT.runAsync(new RunAsyncCallback() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFailure(Throwable reason) {
				// TODO Auto-generated method stub
				
			}
		});
		
		HeaderActivityMapper headerActivityMapper = new HeaderActivityMapper(factory);
		ActivityManager headerActivityManager = new ActivityManager(headerActivityMapper, factory.getEventBus());
		
		SideBarActivityMapper sideBarActivityMapper = new SideBarActivityMapper(factory);
		ActivityManager sideBarActivityManager = new ActivityManager(sideBarActivityMapper, factory.getEventBus());
		
		ContentActivityMapper contentActivityMapper = new ContentActivityMapper(factory);
		ActivityManager contentActivityManager  = new ActivityManager(contentActivityMapper, factory.getEventBus());
	}
}
