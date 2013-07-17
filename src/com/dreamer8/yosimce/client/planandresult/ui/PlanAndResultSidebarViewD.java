package com.dreamer8.yosimce.client.planandresult.ui;

import com.dreamer8.yosimce.shared.dto.EstablecimientoDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class PlanAndResultSidebarViewD extends Composite implements PlanAndResultSidebarView{

	private static PlanAndResultSidebarViewDUiBinder uiBinder = GWT
			.create(PlanAndResultSidebarViewDUiBinder.class);

	interface PlanAndResultSidebarViewDUiBinder extends
			UiBinder<Widget, PlanAndResultSidebarViewD> {
	}

	@UiField StackLayoutPanel stack;
	@UiField CheckBox selectAllBox;
	@UiField(provided=true) CellList<EstablecimientoDTO> establecimientosCellList;
	
	private PlanAndResultSidebarPresenter presenter;
	private EstablecimientoCell cell;
	
	public PlanAndResultSidebarViewD() {
		cell = new EstablecimientoCell();
		establecimientosCellList = new CellList<EstablecimientoDTO>(cell, EstablecimientoDTO.KEY_PROVIDER);
		
		initWidget(uiBinder.createAndBindUi(this));
		stack.showWidget(1);
	}

	@Override
	public void setPresenter(PlanAndResultSidebarPresenter presenter) {
		this.presenter = presenter;
	}
}
