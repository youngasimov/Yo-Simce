package com.dreamer8.yosimce.client.planificacion.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class FiltroAgendamientosPanelViewD extends Composite {

	private static FiltroAgendamientosPanelViewDUiBinder uiBinder = GWT
			.create(FiltroAgendamientosPanelViewDUiBinder.class);

	interface FiltroAgendamientosPanelViewDUiBinder extends
			UiBinder<Widget, FiltroAgendamientosPanelViewD> {
	}
	
	
	@UiField CheckBox confirmadoBox;
	@UiField CheckBox porConfirmarBox;
	@UiField CheckBox confirmadoConCambiosBox;
	@UiField CheckBox anuladoBox;
	@UiField DateBox desdeBox;
	@UiField DateBox hastaBox;
	@UiField Button aplicarButton;
	@UiField Button cancelarButton;

	public FiltroAgendamientosPanelViewD() {
		initWidget(uiBinder.createAndBindUi(this));
		desdeBox.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat(PredefinedFormat.DATE_LONG)));
		hastaBox.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat(PredefinedFormat.DATE_LONG)));
		confirmadoBox.setValue(true);
		porConfirmarBox.setValue(true);
		confirmadoConCambiosBox.setValue(true);
		anuladoBox.setValue(true);
	}
}
