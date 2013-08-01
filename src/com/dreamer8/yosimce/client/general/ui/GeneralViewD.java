package com.dreamer8.yosimce.client.general.ui;

import com.dreamer8.yosimce.client.general.DetalleEstablecimientoPlace;
import com.dreamer8.yosimce.client.general.HistorialEstablecimientoPlace;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class GeneralViewD extends Composite implements GeneralView {

	private static GeneralViewDUiBinder uiBinder = GWT
			.create(GeneralViewDUiBinder.class);

	interface GeneralViewDUiBinder extends UiBinder<Widget, GeneralViewD> {
	}

	@UiField Button detalleEstablecimientoViewButton;
	@UiField Button historialCambiosEstablecimientoViewButton;
	
	private GeneralPresenter presenter;
	
	public GeneralViewD() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiHandler("detalleEstablecimientoViewButton")
	void onDetalleEstablecimientoViewButtonClick(ClickEvent event){
		presenter.goTo(new DetalleEstablecimientoPlace());
	}
	
	@UiHandler("historialCambiosEstablecimientoViewButton")
	void onHistorialCambiosEstablecimientoViewButtonClick(ClickEvent event){
		presenter.goTo(new HistorialEstablecimientoPlace());
	}

	@Override
	public void setDetalleEstablecimientoVisivility(boolean visible) {
		detalleEstablecimientoViewButton.setVisible(visible);
	}

	@Override
	public void setHistorialEstablecimientoVisivility(boolean visible) {
		historialCambiosEstablecimientoViewButton.setVisible(visible);
	}

	@Override
	public void setPresenter(GeneralPresenter presenter) {
		this.presenter = presenter;
		
	}
}
