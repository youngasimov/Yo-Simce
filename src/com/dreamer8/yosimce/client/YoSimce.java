package com.dreamer8.yosimce.client;

import com.dreamer8.yosimce.client.activity.ContentActivityMapper;
import com.dreamer8.yosimce.client.activity.HeaderActivityMapper;
import com.dreamer8.yosimce.client.activity.ModuleSelectorPlace;
import com.dreamer8.yosimce.client.activity.NotLoggedPlace;
import com.dreamer8.yosimce.client.activity.SideBarActivityMapper;
import com.dreamer8.yosimce.client.activity.SimceActivityMapper;
import com.dreamer8.yosimce.client.ui.LoadView;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class YoSimce implements EntryPoint {
	
	private static final String TOKEN_COOKIE = "yosimce";
	
	
	private Place defaultPlace;
	private UserDTO user;
	private ClientFactory factory;
	private LoadView loadView;
	private SimplePanel panel = new SimplePanel();
	

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		factory = GWT.create(ClientFactory.class);
		
		loadView = factory.getLoadView();
		RootPanel.get().add(panel);
		panel.setWidget(loadView);
		
		loadView.setMessage("Comprobando permisos de usuario...");
		
		LoginServiceAsync loginService = (LoginServiceAsync)GWT.create(LoginService.class);
		
		String token = Cookies.getCookie(TOKEN_COOKIE);
		
		if(token == null){
			loadView.setMessage("Usuario no registrado");
			user = null;
			loadApp();
		}else{
			loginService.getUser(token, new AsyncCallback<UserDTO>() {
				
				@Override
				public void onSuccess(UserDTO result) {
					loadView.setMessage(result.getName()+",<br />Descargando aplicación...");
					loadApp();
				}
				
				@Override
				public void onFailure(Throwable caught) {
					loadView.setMessage("Ocurrio un problema al comprobar los permisos de usuario<br />Error: "+caught.getMessage());
					user = null;
					loadApp();
				}
			});
		}
		
	}
	
	private void loadApp(){
		
		if(user == null){
			defaultPlace = new NotLoggedPlace();
		}else{
			defaultPlace = new ModuleSelectorPlace();
		}
		
		GWT.runAsync(new RunAsyncCallback() {
			
			@Override
			public void onSuccess() {
				load();
			}
			
			@Override
			public void onFailure(Throwable reason) {
				loadView.setMessage("Ocurrio un problema al descargar los datos de la aplicación<br />Error: "+reason.getMessage());
				user = null;
			}
		});
	}
	
	private void load(){
		
		
		
		SimceActivityMapper simceActivityMapper = new SimceActivityMapper(factory, user);
		ActivityManager simceActivityManager = new ActivityManager(simceActivityMapper,factory.getEventBus());
		simceActivityManager.setDisplay(panel);
		
		HeaderActivityMapper headerActivityMapper = new HeaderActivityMapper(factory, user);
		ActivityManager headerActivityManager = new ActivityManager(headerActivityMapper, factory.getEventBus());
		headerActivityManager.setDisplay(factory.getAppView().getHeaderView());
		
		SideBarActivityMapper sideBarActivityMapper = new SideBarActivityMapper(factory, user);
		ActivityManager sideBarActivityManager = new ActivityManager(sideBarActivityMapper, factory.getEventBus());
		sideBarActivityManager.setDisplay(factory.getAppView().getSideBarPanel());
		
		ContentActivityMapper contentActivityMapper = new ContentActivityMapper(factory, user);
		ActivityManager contentActivityManager  = new ActivityManager(contentActivityMapper, factory.getEventBus());
		contentActivityManager.setDisplay(factory.getAppView().getContentPanel());
		
		PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(factory.getPlaceHistoryMapper());
		historyHandler.register(factory.getPlaceController(), factory.getEventBus(), defaultPlace);
		historyHandler.handleCurrentHistory();
	}
}
