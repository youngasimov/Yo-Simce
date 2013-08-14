package com.dreamer8.yosimce.client.material.ui;

import com.dreamer8.yosimce.client.material.IngresoMaterialPlace;
import com.dreamer8.yosimce.client.material.MovimientosMaterialPlace;
import com.dreamer8.yosimce.client.material.SalidaMaterialPlace;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class MaterialViewD extends Composite implements MaterialView {

	private static MaterialViewDUiBinder uiBinder = GWT
			.create(MaterialViewDUiBinder.class);

	interface MaterialViewDUiBinder extends UiBinder<Widget, MaterialViewD> {
	}

	@UiField Button ingresoMaterialActionButton;
	@UiField Button salidaMaterialActionButton;
	@UiField Button movimientoMaterialViewButton;
	
	private MaterialPresenter presenter;
	
	public MaterialViewD() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiHandler("ingresoMaterialActionButton")
	void onIngresoMaterialActionButtonClick(ClickEvent event){
		presenter.goTo(new IngresoMaterialPlace());
	}
	
	@UiHandler("salidaMaterialActionButton")
	void onSalidaMaterialActionButtonClick(ClickEvent event){
		presenter.goTo(new SalidaMaterialPlace());
	}
	
	@UiHandler("movimientoMaterialViewButton")
	void onMovimientoMaterialViewButtonClick(ClickEvent event){
		presenter.goTo(new MovimientosMaterialPlace());
	}

	@Override
	public void setIngresoMaterialVisivility(boolean visible) {
		ingresoMaterialActionButton.setVisible(visible);
	}

	@Override
	public void setSalidaMaterialVisivility(boolean visible) {
		salidaMaterialActionButton.setVisible(visible);
	}

	@Override
	public void setMovimientoMaterialVisivility(boolean visible) {
		movimientoMaterialViewButton.setVisible(visible);
	}

	@Override
	public void setPresenter(MaterialPresenter presenter) {
		this.presenter = presenter;
	}

}
