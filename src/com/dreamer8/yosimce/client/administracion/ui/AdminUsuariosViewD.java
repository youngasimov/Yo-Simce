package com.dreamer8.yosimce.client.administracion.ui;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class AdminUsuariosViewD extends Composite implements AdminUsuariosView{

	private static AdminUsuariosViewDUiBinder uiBinder = GWT
			.create(AdminUsuariosViewDUiBinder.class);

	interface AdminUsuariosViewDUiBinder extends
			UiBinder<Widget, AdminUsuariosViewD> {
	}

	@UiField(provided=true) CellList usuariosList;
	
	private AdminUsuariosPresenter presenter;
	
	public AdminUsuariosViewD() {
		usuariosList = new CellList<String>(new TextCell());
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(AdminUsuariosPresenter presenter) {
		this.presenter = presenter;
	}

}
