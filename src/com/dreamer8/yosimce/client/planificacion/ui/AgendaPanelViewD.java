package com.dreamer8.yosimce.client.planificacion.ui;

import java.util.Date;

import com.dreamer8.yosimce.client.planificacion.ui.AgendamientosView.AgendamientosPresenter;
import com.dreamer8.yosimce.client.ui.eureka.TimeBox;
import com.dreamer8.yosimce.client.ui.eureka.TimeBox.TIME_PRECISION;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
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
	@UiField Button cancelarButton;
	
	private DateTimeFormat format;
	
	private AgendamientosPresenter presenter;

	public AgendaPanelViewD() {
		timeBox = new TimeBox(new Date(), TIME_PRECISION.MINUTE, false);
		initWidget(uiBinder.createAndBindUi(this));
		format = DateTimeFormat.getFormat(PredefinedFormat.DATE_LONG);
	}
	
	@UiHandler("fechaPicker")
	void onFechaChange(ValueChangeEvent<Date> event){
		/*Date now = new Date();
		if(event.getValue().before(now) ){
			fechaPicker.setValue(now);
			fechaLabel.setText(format.format(now));
			timeBox.setValue(now.getTime());
		}else{*/
			fechaLabel.setText(format.format(event.getValue()));
			timeBox.setValue(event.getValue().getTime());
			presenter.onFechaChange(event.getValue());
		//}
	}
	
	public void setPresenter(AgendamientosPresenter presenter){
		this.presenter = presenter;
	}
}
