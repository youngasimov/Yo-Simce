package com.dreamer8.yosimce.client.material.ui;

import com.dreamer8.yosimce.client.material.CentroOperacionPlace;
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

	@UiField Button centroOperacionViewButton;
	
	private MaterialPresenter presenter;
	
	public MaterialViewD() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiHandler("centroOperacionViewButton")
	void onIngresoMaterialActionButtonClick(ClickEvent event){
		presenter.goTo(new CentroOperacionPlace());
	}

	@Override
	public void setCentroOperacionVisivility(boolean visible) {
		centroOperacionViewButton.setVisible(visible);
	}
	

	@Override
	public void setPresenter(MaterialPresenter presenter) {
		this.presenter = presenter;
	}

}
