package com.dreamer8.yosimce.client.general.ui;

import com.dreamer8.yosimce.shared.dto.EstadoMaterialItemDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class GraficosViewD extends Composite implements GraficosView{

	private static GraficosViewDUiBinder uiBinder = GWT
			.create(GraficosViewDUiBinder.class);

	interface GraficosViewDUiBinder extends UiBinder<Widget, GraficosViewD> {
	}

	
	@UiField HTMLPanel graphPanel;
	@UiField SimplePanel graficoEstadoMaterialPanel;
	@UiField(provided=true) DataGrid<EstadoMaterialItemDTO> estadoMaterialDataGrid;
	@UiField(provided=true) DataGrid<EstadoMaterialItemDTO> estadoActividadDataGrid;
	
	
	public GraficosViewD() {
		estadoMaterialDataGrid = new DataGrid<EstadoMaterialItemDTO>();
		estadoActividadDataGrid = new DataGrid<EstadoMaterialItemDTO>();
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setEstadoMaterialVisivility(boolean visible) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEstadoActividadVisivility(boolean visible) {
		// TODO Auto-generated method stub
		
	}
}
