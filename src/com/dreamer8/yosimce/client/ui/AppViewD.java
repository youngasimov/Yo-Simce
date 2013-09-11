package com.dreamer8.yosimce.client.ui;

import com.dreamer8.yosimce.client.ui.resources.SimceResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class AppViewD extends Composite implements AppView {

	private static AppViewDUiBinder uiBinder = GWT
			.create(AppViewDUiBinder.class);

	interface AppViewDUiBinder extends UiBinder<Widget, AppViewD> {
	}
	
	interface Style extends CssResource {
		String showMenu();
	}
	
	@UiField Style style;
	@UiField DockLayoutPanel appPanel;
	@UiField DivElement sidebarContainer;	
	@UiField SimpleLayoutPanel headerPanel;
	@UiField FocusPanel sidebarFocusPanel;
	@UiField SimpleLayoutPanel sidebarPanel;
	@UiField SimpleLayoutPanel contentPanel;
	@UiField Image barload;
	@UiField FocusPanel logout;
	@UiField AnchorElement manual;
	
	private MessageContainer messagesContainer;
	
	private PopupPanel popup;
	
	private PopupPanel messagePopup;
	
	private AppPresenter presenter;
	
	private int messages;

	public AppViewD() {
		initWidget(uiBinder.createAndBindUi(this));
		manual.setHref(Window.Location.getProtocol()+"//"+Window.Location.getHost()+"/manual.pdf");
		sidebarPanel.setVisible(true);
		barload.setVisible(false);
		popup = new PopupPanel(false, true);
		popup.setStyleName("");
		popup.setAnimationEnabled(false);
		popup.setGlassEnabled(true);
		popup.setWidget(new Image(SimceResources.INSTANCE.load()));
		messagesContainer = new MessageContainer();
		messagePopup = new PopupPanel(false, false);
		messagePopup.setStyleName("");
		messagePopup.setAnimationEnabled(false);
		messagePopup.setGlassEnabled(false);
		messagePopup.setAutoHideOnHistoryEventsEnabled(false);
		messagePopup.setWidget(messagesContainer);
		
		messages = 0;
	}
	
	@UiHandler("sidebarFocusPanel")
	void onSidebarMouseOut(MouseOutEvent event){
		presenter.onMouseOutFromPanel();
	}
	
	@UiHandler("logout")
	void onLogoutClick(ClickEvent event){
		presenter.onLogout();
	}
	
	@Override
	public void setPresenter(AppPresenter presenter) {
		this.presenter = presenter;
	}
	
	@Override
	public void setSidebarPanelState(boolean open){
		menu(open);
	}

	@Override
	public SimpleLayoutPanel getHeaderView() {
		return headerPanel;
	}

	@Override
	public SimpleLayoutPanel getSideBarPanel() {
		return sidebarPanel;
	}

	@Override
	public SimpleLayoutPanel getContentPanel() {
		return contentPanel;
	}

	@Override
	public void setBarLoadVisibility(boolean visible) {
		barload.setVisible(visible);
	}

	@Override
	public void setCirularLoadVisibility(boolean visible) {
		if(visible){
			popup.center();
		}else{
			popup.hide();
		}
	}

	@Override
	public void showErrorMessage(String message, boolean autoclose) {
		showMessage(SimceResources.INSTANCE.msgError(), message, autoclose);
	}

	@Override
	public void showWarningMessage(String message, boolean autoclose) {
		showMessage(SimceResources.INSTANCE.msgWarning(), message, autoclose);
	}

	@Override
	public void showOkMessage(String message, boolean autoclose) {
		showMessage(SimceResources.INSTANCE.msgOk(), message, autoclose);
	}

	@Override
	public void showPermisoMessage(String message, boolean autoclose) {
		showMessage(SimceResources.INSTANCE.msgLocked(), message, autoclose);
	}
	
	@Override
	public void openLoginPopup(String mensaje1, String mensaje2) {
		PopupPanel p = new PopupPanel(false, true);
		p.setAnimationEnabled(false);
		p.setAutoHideOnHistoryEventsEnabled(false);
		p.setGlassEnabled(true);
		NotLoggedPanel nlp = new NotLoggedPanel();
		nlp.mensaje1.setHTML(mensaje1);
		nlp.mensaje2.setHTML(mensaje2);
		p.setWidget(nlp);
		p.center();
	}
	
	private void menu(boolean open){
		if(open){
			sidebarContainer.addClassName(style.showMenu());
		}else{
			sidebarContainer.removeClassName(style.showMenu());
		}
		//sidebarPanel.setVisible(true);
	}
	
	private void showMessage(ImageResource i,String message, boolean autoclose){
		messages++;
		MessagePanel mp = new MessagePanel(messages, i, message, messagesContainer);
		mp.setAutoClose(autoclose);
		if(autoclose){
			mp.setOpenTime(6000);
		}
		mp.setCloseCommand(new Command() {
			
			@Override
			public void execute() {
				messages--;
				if(messages == 0){
					messagePopup.hide();
				}
			}
		});
		
		mp.show();
		
		if(messages>0){
			messagePopup.setPopupPosition(Window.getClientWidth()-370, 35);
			messagePopup.show();
		}
	}
}
