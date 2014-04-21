package com.dreamer8.yosimce.client.planificacion.ui;

import java.util.Date;

import com.dreamer8.yosimce.client.ui.eureka.TimeBox;
import com.dreamer8.yosimce.client.ui.eureka.TimeBox.TIME_PRECISION;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DatePicker;

public class AgendaPanelViewD extends Composite {

	private static AgendaPanelViewDUiBinder uiBinder = GWT
			.create(AgendaPanelViewDUiBinder.class);

	interface AgendaPanelViewDUiBinder extends
			UiBinder<Widget, AgendaPanelViewD> {
	}
	
	@UiField ListBox estadoBox;
	@UiField Label fechaLabel;
	@UiField DatePicker fechaPicker;
	@UiField(provided = true) TimeBox timeBox;
	@UiField TextArea comentarioBox;
	@UiField Button agendarButton;

	public AgendaPanelViewD() {
		timeBox = new TimeBox(new Date(), TIME_PRECISION.MINUTE, false);
		initWidget(uiBinder.createAndBindUi(this));
	}
}
