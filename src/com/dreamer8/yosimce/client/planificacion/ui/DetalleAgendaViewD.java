package com.dreamer8.yosimce.client.planificacion.ui;

import com.dreamer8.yosimce.shared.dto.AgendaItemDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.HasData;

public class DetalleAgendaViewD extends Composite implements DetalleAgendaView{

	private static DetalleAgendaViewDUiBinder uiBinder = GWT
			.create(DetalleAgendaViewDUiBinder.class);

	interface DetalleAgendaViewDUiBinder extends
			UiBinder<Widget, DetalleAgendaViewD> {
	}

	@UiField HTML establecimiento;
	@UiField Button informacionButton;
	@UiField Button cambiarButton;
	@UiField(provided=true) CellList<AgendaItemDTO> agendaList;
	
	private DetalleAgendaPresenter presenter;
	private AgendaCell cell;
	
	public DetalleAgendaViewD() {
		cell = new AgendaCell();
		agendaList = new CellList<AgendaItemDTO>(cell);
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("cambiarButton")
	void onCambiarClick(ClickEvent event){
		presenter.onCambiarCursoClick();
	}
	
	@Override
	public void setPresenter(DetalleAgendaPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public HasData<AgendaItemDTO> getDataDisplay() {
		return agendaList;
	}

	@Override
	public void setNombreEstablecimiento(String establecimiento) {
		this.establecimiento.setHTML(establecimiento);
	}

	@Override
	public UIObject getCambiarButton() {
		return cambiarButton;
	}
}