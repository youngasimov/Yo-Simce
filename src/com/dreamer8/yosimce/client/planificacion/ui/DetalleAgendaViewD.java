package com.dreamer8.yosimce.client.planificacion.ui;

import com.dreamer8.yosimce.client.general.DetalleCursoPlace;
import com.dreamer8.yosimce.client.ui.ImageButton;
import com.dreamer8.yosimce.client.ui.ViewUtils;
import com.dreamer8.yosimce.client.ui.resources.SimceResources;
import com.dreamer8.yosimce.shared.dto.AgendaItemDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
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
	@UiField ImageButton cambiarButton;
	@UiField(provided=true) CellList<AgendaItemDTO> agendaList;
	
	private int idCurso;
	private DetalleAgendaPresenter presenter;
	private AgendaCell cell;
	
	public DetalleAgendaViewD() {
		cell = new AgendaCell();
		agendaList = new CellList<AgendaItemDTO>(cell);
		initWidget(uiBinder.createAndBindUi(this));
		idCurso = -1;
	}

	@UiHandler("cambiarButton")
	void onCambiarClick(ClickEvent event){
		presenter.onCambiarCursoClick();
	}
	
	@UiHandler("informacionButton")
	void onInformacionClick(ClickEvent event){
		DetalleCursoPlace dcp = new DetalleCursoPlace();
		dcp.setCursoId(idCurso);
		presenter.goTo(dcp);
	}
	
	@UiFactory
	public static SimceResources getResources() {
		return SimceResources.INSTANCE;
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
		this.establecimiento.setHTML(ViewUtils.limitarString(establecimiento,35));
	}

	@Override
	public UIObject getCambiarButton() {
		return cambiarButton;
	}

	@Override
	public void setIdCurso(int idCurso) {
		this.idCurso = idCurso;
	}
}