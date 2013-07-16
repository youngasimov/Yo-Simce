package com.dreamer8.yosimce.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ModuleSelectorViewD extends Composite implements ModuleSelectorView{

	private static ModuleSelectorViewDUiBinder uiBinder = GWT
			.create(ModuleSelectorViewDUiBinder.class);

	interface ModuleSelectorViewDUiBinder extends
			UiBinder<Widget, ModuleSelectorViewD> {
	}

	@UiField Button planAndResultButton;
	
	private ModuleSelectorPresenter presenter;

	public ModuleSelectorViewD(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		planAndResultButton.setText("Modulo de planificación y resultados");
	}

	@UiHandler("planAndResultButton")
	void onClick(ClickEvent e) {
		presenter.onPlanAndResultButtonClick();
	}

	@Override
	public void setPresenter(ModuleSelectorPresenter presenter) {
		this.presenter = presenter;
	}

}
