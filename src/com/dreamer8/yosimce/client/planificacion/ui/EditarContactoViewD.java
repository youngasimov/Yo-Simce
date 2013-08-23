package com.dreamer8.yosimce.client.planificacion.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EditarContactoViewD extends Composite {

	private static EditarContactoViewDUiBinder uiBinder = GWT
			.create(EditarContactoViewDUiBinder.class);

	interface EditarContactoViewDUiBinder extends
			UiBinder<Widget, EditarContactoViewD> {
	}

	@UiField TextBox nombreBox;
	@UiField TextBox fonoBox;
	@UiField TextBox emailBox;
	@UiField ListBox cargoBox;
	@UiField Button editarButton;
	@UiField Button cancelarButton;
	
	public EditarContactoViewD() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
