package com.dreamer8.yosimce.client.ui;

import com.dreamer8.yosimce.client.ui.resources.SimceResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.LayoutPanel;
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
	@UiField(provided=true) ToggleButton sidebarButton;
	
	private AppPresenter presenter;

	public AppViewD() {
		sidebarButton = new ToggleButton(new Image(SimceResources.INSTANCE.arrowRight()),new Image(SimceResources.INSTANCE.arrowLeft()));
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(AppPresenter presenter) {
		this.presenter = presenter;
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
	
	@UiHandler("sidebarButton")
	public void onSidebarButtonChange(ValueChangeEvent<Boolean> event){
		if(sidebarButton.getValue()){
			appPanel.setWidgetSize(sidebarContainer, 20);
		}else{
			appPanel.setWidgetSize(sidebarContainer, 3);
		}
		sidebarPanel.setVisible(sidebarButton.getValue());
	}
}
