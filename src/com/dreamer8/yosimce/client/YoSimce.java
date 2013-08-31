package com.dreamer8.yosimce.client;

import com.dreamer8.yosimce.client.ui.LoadView;
import com.dreamer8.yosimce.client.ui.LoadViewD;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
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
	private SimplePanel panel;
	private LoginServiceAsync loginService ;
	

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		defaultPlace = new SimcePlace();
		loginService = (LoginServiceAsync)GWT.create(LoginService.class);
		panel = new SimplePanel();
		loadView = new LoadViewD();
		
		panel.setSize("100%", "100%");
		
		
		
		RootPanel.get().addStyleName("app");
		RootPanel.get().add(panel);
		RootPanel.get().setWidgetPosition(panel, 0, 0);
		
		
		panel.setWidget(loadView);
		
		Cookies.removeCookie("a");
		Cookies.removeCookie("n");
		Cookies.removeCookie("t");
		
		loadView.setMessage("Comprobando permisos de usuario...");
		
		String token = Cookies.getCookie(TOKEN_COOKIE);
		
		if(token == null){
			loadView.setMessage("Usuario no registrado");
			user = null;
			loadApp();
		}else{
			loginService.getUser(token, new AsyncCallback<UserDTO>() {
				
				@Override
				public void onSuccess(UserDTO result) {
					user = result;
					loadView.setMessage(result.getNombres()+" "+result.getApellidoPaterno()+",<br />Descargando aplicación...");
					loadApp();
				}
				
				@Override
				public void onFailure(Throwable caught) {
					loadView.setMessage("Ocurrio un problema al comprobar los permisos de usuario<br />Error: "+caught.getMessage());
					user = null;
					Cookies.removeCookie(TOKEN_COOKIE);
					Timer t = new Timer(){

						@Override
						public void run() {
							notLogged();
						}
					};
					t.schedule(3000);
				}
			});
		}
		
	}
	
	private void loadApp(){
		
		if(user == null){
			
			notLogged();
			return;
		}
		
		GWT.runAsync(new RunAsyncCallback() {
			
			@Override
			public void onSuccess() {
				load();
			}
			
			@Override
			public void onFailure(Throwable reason) {
				loadView.setMessage("Ocurrio un problema al descargar los datos de la aplicación<br />Error: "+reason.getMessage());
			}
		});
	}
	
	private void load(){
		
		factory = GWT.create(ClientFactory.class);
		
		AppPresenter app = new AppPresenter(factory);
		app.setDisplay(panel);
		
		HeaderPresenter header = new HeaderPresenter(factory, user);
		header.setDisplay(factory.getAppView().getHeaderView());
		
		SidebarPresenter sidebar = new SidebarPresenter(factory);
		sidebar.setDisplay(factory.getAppView().getSideBarPanel());
		
		
		ContentActivityMapper contentActivityMapper = new ContentActivityMapper(factory);
		ActivityManager contentActivityManager  = new ActivityManager(contentActivityMapper, factory.getEventBus());
		contentActivityManager.setDisplay(factory.getAppView().getContentPanel());
		
		PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(factory.getPlaceHistoryMapper());
		historyHandler.register(factory.getPlaceController(), factory.getEventBus(), defaultPlace);
		historyHandler.handleCurrentHistory();
	}
	
	private void notLogged(){
//		if(Window.Location.getPath().contains("demo")){
//			LoginView login = new LoginView();
//			login.setPresenter(new LoginView.LoginPresenter() {
//				
//				@Override
//				public void onLogin(String username) {
//					loginService.getUserToken(username, new AsyncCallback<String>() {
//	
//						@Override
//						public void onFailure(Throwable caught) {
//							
//						}
//	
//						@Override
//						public void onSuccess(String result) {
//							Cookies.setCookie(TOKEN_COOKIE, result);
//							Window.Location.reload();
//						}
//					});
//				}
//			});
//			panel.setWidget(login);
//		}else{
			Window.open("http://www.yosimce.cl", "_self", "");
//		}
	}
}
