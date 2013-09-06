package com.dreamer8.yosimce.client.administracion.ui;


import com.dreamer8.yosimce.client.ui.resources.SimceResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class AdminEventosViewD extends Composite implements AdminEventosView{

	private static AdminEventosViewDUiBinder uiBinder = GWT
			.create(AdminEventosViewDUiBinder.class);

	interface AdminEventosViewDUiBinder extends
			UiBinder<Widget, AdminEventosViewD> {
	}
	
	
	//private AdminEventosPresenter presenter;
	
	public AdminEventosViewD() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiFactory
	public static SimceResources getResources() {
		return SimceResources.INSTANCE;
	}

	@Override
	public void setPresenter(AdminEventosPresenter presenter) {
		//this.presenter = presenter;
	}

	
}
