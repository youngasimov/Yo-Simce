package com.dreamer8.yosimce.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class LoginView extends Composite {

	private static LoginViewUiBinder uiBinder = GWT
			.create(LoginViewUiBinder.class);

	interface LoginViewUiBinder extends UiBinder<Widget, LoginView> {
	}
	
	public interface LoginPresenter{
		void onLogin(String username);
	}
	
	@UiField TextBox usernameBox;
	@UiField Button loginButton;
	
	private LoginPresenter presenter;

	public LoginView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setPresenter(LoginPresenter presenter){
		this.presenter = presenter;
	}
	
	@UiHandler("loginButton")
	void onLoginClick(ClickEvent event){
		presenter.onLogin(usernameBox.getValue());
	}

}
