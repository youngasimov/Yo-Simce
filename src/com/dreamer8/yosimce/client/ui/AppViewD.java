package com.dreamer8.yosimce.client.ui;

import com.dreamer8.yosimce.client.ui.resources.SimceResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.Widget;

public class AppViewD extends Composite implements AppView {

	private static AppViewDUiBinder uiBinder = GWT
			.create(AppViewDUiBinder.class);

	interface AppViewDUiBinder extends UiBinder<Widget, AppViewD> {
	}
	
	@UiField DockLayoutPanel appPanel;
	@UiField LayoutPanel sidebarContainer;	
	@UiField SimpleLayoutPanel headerPanel;
	@UiField SimpleLayoutPanel sidebarPanel;
	@UiField SimpleLayoutPanel contentPanel;
	@UiField Image barload;
	@UiField(provided=true) ToggleButton sidebarButton;
	
	private MessageContainer messagesContainer;
	
	private PopupPanel popup;
	
	private PopupPanel messagePopup;
	
	private int messages;

	public AppViewD() {
		sidebarButton = new ToggleButton(new Image(SimceResources.INSTANCE.arrowRight()),new Image(SimceResources.INSTANCE.arrowLeft()));
		initWidget(uiBinder.createAndBindUi(this));
		sidebarPanel.setVisible(false);
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
	
	@UiHandler("sidebarButton")
	public void onSidebarButtonChange(ValueChangeEvent<Boolean> event){
		if(sidebarButton.getValue()){
			appPanel.setWidgetSize(sidebarContainer, 20);
		}else{
			appPanel.setWidgetSize(sidebarContainer, 3);
		}
		sidebarPanel.setVisible(sidebarButton.getValue());
	}
	
	@Override
	public void setSidebarPanelState(boolean open){
		sidebarButton.setValue(open, true);
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
	
	private void showMessage(ImageResource i,String message, boolean autoclose){
		messages++;
		MessagePanel mp = new MessagePanel(messages, i, message, messagesContainer);
		mp.setAutoClose(autoclose);
		if(autoclose){
			mp.setOpenTime(4000);
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
