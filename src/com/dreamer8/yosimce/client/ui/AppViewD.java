package com.dreamer8.yosimce.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class AppViewD extends Composite implements AppView {

	private static AppViewDUiBinder uiBinder = GWT
			.create(AppViewDUiBinder.class);

	interface AppViewDUiBinder extends UiBinder<Widget, AppViewD> {
	}
	
	
	@UiField SimpleLayoutPanel headerPanel;
	@UiField SimpleLayoutPanel sidebarPanel;
	@UiField SimpleLayoutPanel contentPanel;
	
	private AppPresenter presenter;

	public AppViewD() {
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
}
