package com.dreamer8.yosimce.client.administracion.ui;

import java.util.Date;

import com.dreamer8.yosimce.client.ui.eureka.TimeBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class UpdateProgramarViewD extends Composite {

	private static UpdateProgramarViewDUiBinder uiBinder = GWT
			.create(UpdateProgramarViewDUiBinder.class);

	interface UpdateProgramarViewDUiBinder extends
			UiBinder<Widget, UpdateProgramarViewD> {
	}

	@UiField DateBox dateBox;
	@UiField(provided = true) TimeBox timeBox;
	@UiField Button cerrarButton;
	@UiField Button cancelarButton;
	@UiField Button programarButton;
	
	public UpdateProgramarViewD() {
		timeBox = new TimeBox(new Date(), false);
		initWidget(uiBinder.createAndBindUi(this));
		dateBox.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_MEDIUM)));
		dateBox.addValueChangeHandler(new ValueChangeHandler<Date>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				timeBox.setValue(event.getValue().getTime());
			}
		});
	}

}
