package com.dreamer8.yosimce.client.planificacion.ui;

import com.dreamer8.yosimce.client.planificacion.AgendamientosPlace;
import com.dreamer8.yosimce.client.planificacion.AgendarVisitaPlace;
import com.dreamer8.yosimce.client.planificacion.DetalleAgendaPlace;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class PlanificacionViewD extends Composite implements PlanificacionView {

	private static PlanificacionViewDUiBinder uiBinder = GWT
			.create(PlanificacionViewDUiBinder.class);

	interface PlanificacionViewDUiBinder extends
			UiBinder<Widget, PlanificacionViewD> {
	}
	
	@UiField Button agendamientosViewButton;
	@UiField Button detalleAgendaViewButton;
	@UiField Button agendarVisitaActionButton;
	
	private PlanificacionPresenter presenter;

	public PlanificacionViewD() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiHandler("agendamientosViewButton")
	void onAgendamientosViewButtonClick(ClickEvent event){
		presenter.goTo(new AgendamientosPlace());
	}
	
	@UiHandler("detalleAgendaViewButton")
	void onDetalleAgendaViewButtonClick(ClickEvent event){
		presenter.goTo(new DetalleAgendaPlace());
	}
	
	@UiHandler("agendarVisitaActionButton")
	void onAgendarVisitaActionButtonClick(ClickEvent event){
		presenter.goTo(new AgendarVisitaPlace());
	}

	@Override
	public void setAgendamientosVisivility(boolean visible) {
		agendamientosViewButton.setVisible(visible);
	}

	@Override
	public void setDetalleAgendaVisivility(boolean visible) {
		detalleAgendaViewButton.setVisible(visible);
	}

	@Override
	public void setAgendarVisitaVisivility(boolean visible) {
		agendarVisitaActionButton.setVisible(visible);
	}

	@Override
	public void setPresenter(PlanificacionPresenter presenter) {
		this.presenter = presenter;
	}

}
