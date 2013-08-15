package com.dreamer8.yosimce.client.actividad.ui;

import com.dreamer8.yosimce.shared.dto.SincronizacionPreviewDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class SincronizacionesViewD extends Composite implements SincronizacionesView {

	private static SincronizacionesViewDUiBinder uiBinder = GWT
			.create(SincronizacionesViewDUiBinder.class);

	interface SincronizacionesViewDUiBinder extends
			UiBinder<Widget, SincronizacionesViewD> {
	}

	@UiField Button filtrosButton;
	@UiField Button exportarButton;
	@UiField Button sincronizacionButton;
	@UiField Button informacionButton;
	@UiField  HTML establecimientoSeleccionado;
	@UiField(provided = true) DataGrid<SincronizacionPreviewDTO> dataGrid;
	@UiField(provided = true) SimplePager pager;
	
	private SincronizacionesPresenter presenter;
	private SingleSelectionModel<SincronizacionPreviewDTO> selectionModel;
	SincronizacionPreviewDTO selectedItem;
	
	public SincronizacionesViewD() {
		dataGrid = new DataGrid<SincronizacionPreviewDTO>(SincronizacionPreviewDTO.KEY_PROVIDER);
		pager = new SimplePager(TextLocation.CENTER, false, false);
		initWidget(uiBinder.createAndBindUi(this));
		bind();
	}

	@Override
	public void setPresenter(SincronizacionesPresenter presenter) {
		this.presenter = presenter;
	}
	
	private void bind(){
		dataGrid.setWidth("100%");
		buildGrid();
		
		pager.setDisplay(dataGrid);
		
		selectionModel = new SingleSelectionModel<SincronizacionPreviewDTO>(SincronizacionPreviewDTO.KEY_PROVIDER);
		
		dataGrid.setSelectionModel(selectionModel, new CellPreviewEvent.Handler<SincronizacionPreviewDTO>() {

			@Override
			public void onCellPreview(
					CellPreviewEvent<SincronizacionPreviewDTO> event) {
				if(!event.getNativeEvent().getType().contains("click")){
					return;
				}
				selectionModel.setSelected(event.getValue(), !selectionModel.isSelected(event.getValue()));
			}
		});
	}
	
	private void buildGrid(){
		
	}

}
