package com.dreamer8.yosimce.client.planificacion.ui;

import java.util.Date;

import com.dreamer8.yosimce.client.general.DetalleCursoPlace;
import com.dreamer8.yosimce.client.ui.eureka.TimeBox;
import com.dreamer8.yosimce.client.ui.eureka.TimeBox.TIME_PRECISION;
import com.dreamer8.yosimce.shared.dto.AgendaItemDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.google.gwt.view.client.HasData;

public class AgendarVisitaViewD extends Composite implements AgendarVisitaView {

	private static AgendarVisitaViewDUiBinder uiBinder = GWT
			.create(AgendarVisitaViewDUiBinder.class);

	interface AgendarVisitaViewDUiBinder extends
			UiBinder<Widget, AgendarVisitaViewD> {
	}
	
	@UiField HTML establecimiento;
	@UiField Button informacionButton;
	@UiField Button cambiarButton;
	@UiField ListBox estadoBox;
	@UiField DatePicker fechaPicker;
	@UiField Label fechaLabel;
	@UiField(provided=true) TimeBox timeBox;
	@UiField TextArea comentarioBox;
	@UiField Button modificarButton;
	@UiField(provided=true) CellList<AgendaItemDTO> agendaList;
	
	
	private int idCurso;
	private AgendarVisitaPresenter presenter;
	private AgendaCell cell;
	private DateTimeFormat format;
	
	public AgendarVisitaViewD() {
		cell = new AgendaCell();
		agendaList = new CellList<AgendaItemDTO>(cell);
		timeBox = new TimeBox(new Date(), TIME_PRECISION.MINUTE, false);
		initWidget(uiBinder.createAndBindUi(this));
		format = DateTimeFormat.getFormat(PredefinedFormat.DATE_LONG);
		idCurso = -1;
	}
	
	@UiHandler("cambiarButton")
	void onCambiarClick(ClickEvent event){
		presenter.onCambiarEstablecimientoClick();
	}
	
	@UiHandler("informacionButton")
	void onInformacionClick(ClickEvent event){
		DetalleCursoPlace dcp = new DetalleCursoPlace();
		dcp.setCursoId(idCurso);
		presenter.goTo(dcp);
	}
	
	@UiHandler("fechaPicker")
	void onFechaChange(ValueChangeEvent<Date> event){
		fechaLabel.setText(format.format(event.getValue()));
	}

	public void setIdCurso(int idCurso){
		this.idCurso = idCurso;
	}
	
	@Override
	public void setPresenter(AgendarVisitaPresenter presenter) {
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
