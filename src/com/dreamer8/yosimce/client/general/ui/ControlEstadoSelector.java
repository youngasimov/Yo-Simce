package com.dreamer8.yosimce.client.general.ui;

import com.dreamer8.yosimce.client.general.ui.CentroControlView.CentroControlPresenter;
import com.dreamer8.yosimce.shared.dto.ControlCentroOperacionDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class ControlEstadoSelector extends Composite {

	private static ControlEstadoSelectorUiBinder uiBinder = GWT
			.create(ControlEstadoSelectorUiBinder.class);

	interface ControlEstadoSelectorUiBinder extends
			UiBinder<Widget, ControlEstadoSelector> {
	}
	
	@UiField
	Button waitButton;
	@UiField
	Button idaButton;
	@UiField
	Button vueltaButton;
	@UiField
	Button problemaButton;
	
	private CentroControlPresenter presenter;
	private ControlCentroOperacionDTO centro;
	private PopupPanel container;

	public ControlEstadoSelector() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setPresenter(CentroControlPresenter presenter){
		this.presenter = presenter;
	}
	
	public void setCentro(ControlCentroOperacionDTO centro) {
		this.centro = centro;
	}
	
	public void setContainer(PopupPanel container) {
		this.container = container;
	}
	
	@UiHandler("waitButton")
	public void onWaitButtonClick(ClickEvent event){
		presenter.changeControlStatus(centro, 0);
		container.hide();
	}
	
	@UiHandler("idaButton")
	public void onIdaButtonClick(ClickEvent event){
		presenter.changeControlStatus(centro, 1);
		container.hide();
	}
	
	@UiHandler("vueltaButton")
	public void onVueltaButtonClick(ClickEvent event){
		presenter.changeControlStatus(centro, 2);
		container.hide();
	}
	
	@UiHandler("problemaButton")
	public void onProblemaButtonClick(ClickEvent event){
		presenter.changeControlStatus(centro, 3);
		container.hide();
	}

}
