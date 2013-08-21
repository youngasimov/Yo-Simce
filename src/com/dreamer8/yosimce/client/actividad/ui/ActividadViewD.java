package com.dreamer8.yosimce.client.actividad.ui;

import com.dreamer8.yosimce.client.actividad.ActividadesPlace;
import com.dreamer8.yosimce.client.actividad.AprobarSupervisoresPlace;
import com.dreamer8.yosimce.client.actividad.FormActividadPlace;
import com.dreamer8.yosimce.client.actividad.MaterialDefectuosoPlace;
import com.dreamer8.yosimce.client.actividad.SincronizacionPlace;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ActividadViewD extends Composite implements ActividadView {

	private static ActividadViewDUiBinder uiBinder = GWT
			.create(ActividadViewDUiBinder.class);

	interface ActividadViewDUiBinder extends UiBinder<Widget, ActividadViewD> {
	}
	
	@UiField Button actividadesViewButton;
	@UiField Button formularioActividadActionButton;
	@UiField Button sincronizacionActionButton;
	@UiField Button materialDefectuosoActionButton;
	@UiField Button aprobarSupervisoresActionButton;

	private ActividadPresenter presenter;
	
	public ActividadViewD() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiHandler("actividadesViewButton")
	void onActividadesClick(ClickEvent event){
		presenter.goTo(new ActividadesPlace());
	}
	
	@UiHandler("formularioActividadActionButton")
	void onFormularioActividadClick(ClickEvent event){
		presenter.goTo(new FormActividadPlace());
	}
	
	@UiHandler("sincronizacionActionButton")
	void onSincronizacionClick(ClickEvent event){
		presenter.goTo(new SincronizacionPlace());
	}
	
	@UiHandler("sincronizacionActionButton")
	void onMaterialDefectuosoClick(ClickEvent event){
		presenter.goTo(new MaterialDefectuosoPlace());
	}
	
	@UiHandler("aprobarSupervisoresActionButton")
	void onAprobarSupervisoresClick(ClickEvent event){
		presenter.goTo(new AprobarSupervisoresPlace());
	}

	@Override
	public void setActividadesVisivility(boolean visible) {
		actividadesViewButton.setVisible(visible);
	}

	@Override
	public void setFormActividadVisivility(boolean visible) {
		formularioActividadActionButton.setVisible(visible);
	}

	@Override
	public void setSincronizacionVisivility(boolean visible) {
		sincronizacionActionButton.setVisible(visible);
	}
	
	@Override
	public void setMaterialDefectuosoVisivility(boolean visible){
		materialDefectuosoActionButton.setVisible(visible);
	}

	@Override
	public void setAprobarSupervisoresVisivility(boolean visible) {
		aprobarSupervisoresActionButton.setVisible(visible);
	}

	@Override
	public void setPresenter(ActividadPresenter presenter) {
		this.presenter = presenter;
	}

}
