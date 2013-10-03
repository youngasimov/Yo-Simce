package com.dreamer8.yosimce.client;

import com.dreamer8.yosimce.client.ui.LoginView;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class YoSimce implements EntryPoint {
	
	public static final String TOKEN_COOKIE = "yosimce";
	public static final String TOKEN_COOKIE_DEMO = "tracking";
	
	
	private LoginActivity loginActivity;
	private SimplePanel panel;
	private LoginServiceAsync loginService ;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		loginService = (LoginServiceAsync)GWT.create(LoginService.class);
		((ServiceDefTarget) loginService).setRpcRequestBuilder(new CustomRpcRequestBuilder(10000));
		panel = new SimplePanel();
		
		panel.setSize("100%", "100%");
		
		RootPanel.get().addStyleName("app");
		RootPanel.get().add(panel);
		RootPanel.get().setWidgetPosition(panel, 0, 0);
		
		loginActivity = new LoginActivity(loginService, new LoginView());
		
		Cookies.removeCookie("a");
		Cookies.removeCookie("n");
		Cookies.removeCookie("t");
		Cookies.removeCookie(LoginService.USUARIO_TIPO_COOKIE_NAME);
		
		loginActivity.start(panel);
	}
}
