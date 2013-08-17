package com.dreamer8.yosimce.client.actividad.ui;


import com.dreamer8.yosimce.client.actividad.DetalleActividadPlace;
import com.dreamer8.yosimce.client.actividad.SincronizacionPlace;
import com.dreamer8.yosimce.client.general.DetalleCursoPlace;
import com.dreamer8.yosimce.shared.dto.ActividadPreviewDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.RangeChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class ActividadesViewD extends Composite implements ActividadesView {

	private static ActividadesViewDUiBinder uiBinder = GWT
			.create(ActividadesViewDUiBinder.class);

	interface ActividadesViewDUiBinder extends
			UiBinder<Widget, ActividadesViewD> {
	}

	@UiField Button filtrosButton;
	@UiField Button exportarButton;
	@UiField Button sincronizacionButton;
	@UiField Button informacionButton;
	@UiField  HTML establecimientoSeleccionado;
	@UiField(provided = true) DataGrid<ActividadPreviewDTO> dataGrid;
	@UiField(provided = true) SimplePager pager;
	
	private SingleSelectionModel<ActividadPreviewDTO> selectionModel;
	private ActividadPreviewDTO selectedItem;
	private ActividadesPresenter presenter;
	
	public ActividadesViewD() {
		dataGrid = new DataGrid<ActividadPreviewDTO>(ActividadPreviewDTO.KEY_PROVIDER);
		pager = new SimplePager(TextLocation.CENTER, false, false);
		initWidget(uiBinder.createAndBindUi(this));
		bind();
	}
	
	@UiHandler("filtrosButton")
	void onFiltrosClick(ClickEvent event){
		
	}
	
	@UiHandler("exportarButton")
	void onExportarClick(ClickEvent event){
		presenter.onExportarClick();
	}
	
	@UiHandler("sincronizacionButton")
	void onDetallesClick(ClickEvent event){
		SincronizacionPlace place = new SincronizacionPlace();
		place.setIdSincronizacion(selectedItem.getCursoId());
		presenter.goTo(place);
	}
	
	@UiHandler("informacionButton")
	void onInformacionClick(ClickEvent event){
		DetalleCursoPlace dcp = new DetalleCursoPlace();
		if(selectedItem !=null)dcp.setCursoId(selectedItem.getCursoId());
		presenter.goTo(dcp);
	}

	@Override
	public HasData<ActividadPreviewDTO> getDataDisplay() {
		return dataGrid;
	}

	@Override
	public Column<ActividadPreviewDTO, ?> getColumn(int index) {
		return dataGrid.getColumn(index);
	}

	@Override
	public int getColumnIndex(Column<ActividadPreviewDTO, ?> column) {
		return dataGrid.getColumnIndex(column);
	}

	@Override
	public void clearCursoSelection() {
		if(selectedItem !=null){
			selectionModel.setSelected(selectedItem, false);
			selectedItem = null;
		}
	}

	@Override
	public void setPresenter(ActividadesPresenter presenter) {
		this.presenter = presenter;
	}
	
	private void bind(){
		dataGrid.setWidth("100%");
		buildGrid();
		sincronizacionButton.setVisible(false);
		informacionButton.setVisible(false);
		
		pager.setDisplay(dataGrid);
		
		selectionModel = new SingleSelectionModel<ActividadPreviewDTO>(ActividadPreviewDTO.KEY_PROVIDER);
		
		dataGrid.setSelectionModel(selectionModel,new CellPreviewEvent.Handler<ActividadPreviewDTO>(){

			@Override
			public void onCellPreview(CellPreviewEvent<ActividadPreviewDTO> event) {
				if(!event.getNativeEvent().getType().contains("click")){
					return;
				}
				selectionModel.setSelected(event.getValue(), !selectionModel.isSelected(event.getValue()));
			}});
		
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				//establecimientoSeleccionado.setHTML((selectionModel.getSelectedObject()!=null)?selectionModel.getSelectedObject().getEstablecimientoName()+" >":"");
				sincronizacionButton.setVisible(selectionModel.getSelectedObject()!=null);
				informacionButton.setVisible(selectionModel.getSelectedObject()!=null);
				selectedItem = selectionModel.getSelectedObject();
			}
		});
		
		dataGrid.addRangeChangeHandler(new RangeChangeEvent.Handler() {
			
			@Override
			public void onRangeChange(RangeChangeEvent event) {
				presenter.onRangeChange(event.getNewRange());
			}
		});
	}

	
	private void buildGrid(){
		
	}
}
