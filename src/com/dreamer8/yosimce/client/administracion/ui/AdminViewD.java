package com.dreamer8.yosimce.client.administracion.ui;

import com.dreamer8.yosimce.client.administracion.AdminEventosPlace;
import com.dreamer8.yosimce.client.administracion.AdminUsuariosPlace;
import com.dreamer8.yosimce.client.administracion.PermisosPlace;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class AdminViewD extends Composite implements AdminView {

	private static AdminViewDUiBinder uiBinder = GWT
			.create(AdminViewDUiBinder.class);

	interface AdminViewDUiBinder extends UiBinder<Widget, AdminViewD> {
	}

	@UiField Button adminUsersViewButton;
	@UiField Button adminEventsViewButton;
	@UiField Button adminPermisosViewButton;
	
	private AdminPresenter presenter;
	
	public AdminViewD() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiHandler("adminUsersViewButton")
	void onAdminUsersViewButtonClick(ClickEvent event){
		presenter.goTo(new AdminUsuariosPlace());
	}
	
	@UiHandler("adminEventsViewButton")
	void onAdminEventsViewButtonClick(ClickEvent event){
		presenter.goTo(new AdminEventosPlace());
	}
	
	@UiHandler("adminPermisosViewButton")
	void onAdminPermisosViewButtonClick(ClickEvent event){
		presenter.goTo(new PermisosPlace());
	}

	@Override
	public void setAdminUsersVisivility(boolean visible) {
		adminUsersViewButton.setVisible(visible);
	}

	@Override
	public void setAdminEventsVisivility(boolean visible) {
		adminEventsViewButton.setVisible(visible);
	}

	@Override
	public void setPresenter(AdminPresenter presenter) {
		this.presenter = presenter;
	}

}
