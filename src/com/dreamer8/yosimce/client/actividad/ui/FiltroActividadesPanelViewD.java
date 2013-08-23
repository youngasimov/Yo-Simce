package com.dreamer8.yosimce.client.actividad.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class FiltroActividadesPanelViewD extends Composite {

	private static FiltroActividadesPanelViewDUiBinder uiBinder = GWT
			.create(FiltroActividadesPanelViewDUiBinder.class);

	interface FiltroActividadesPanelViewDUiBinder extends
			UiBinder<Widget, FiltroActividadesPanelViewD> {
	}
	
	@UiField CheckBox noIniciadasBox;
	@UiField CheckBox terminadasBox;
	@UiField CheckBox contingenciaBox;
	@UiField CheckBox sincronizadasBox;
	@UiField CheckBox problemasBox;
	@UiField ListBox regionBox;
	@UiField ListBox comunaBox;
	@UiField Button aplicarButton;
	@UiField Button cancelarButton;

	public FiltroActividadesPanelViewD() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}