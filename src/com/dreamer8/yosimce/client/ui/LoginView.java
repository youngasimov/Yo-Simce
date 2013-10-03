package com.dreamer8.yosimce.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class LoginView extends Composite {

	private static LoginViewUiBinder uiBinder = GWT
			.create(LoginViewUiBinder.class);

	interface LoginViewUiBinder extends UiBinder<Widget, LoginView> {
	}
	
	interface Style extends CssResource {
		String showLogin();
	}
	
	public interface LoginPresenter{
		void onLogin(String username, String password);
		void start(SimplePanel panel);
	}
	
	
	@UiField Style style;
	@UiField DivElement div;
	@UiField PlaceHolderTextBox usernameBox;
	@UiField PlaceHolderPasswordBox passwordBox;
	@UiField HTML message;
	@UiField HTML message2;
	@UiField Button loginButton;
	
	private LoginPresenter presenter;

	public LoginView() {
		initWidget(uiBinder.createAndBindUi(this));
		usernameBox.setWidth("190px");
		passwordBox.setWidth("190px");
	}
	
	public void setPresenter(LoginPresenter presenter){
		this.presenter = presenter;
	}
	
	@UiHandler("loginButton")
	void onLoginClick(ClickEvent event){
		presenter.onLogin(usernameBox.getValue(), passwordBox.getValue());
	}
	
	public void showLogin(){
		div.addClassName(style.showLogin());
		passwordBox.setValue("");
	}
	
	public void showLoad(){
		div.removeClassName(style.showLogin());
	}
	
	public void setMensaje(String mensaje){
		message.setHTML(mensaje);
		message2.setHTML(mensaje);
	}

}
