package com.dreamer8.yosimce.client.planificacion.ui;

import java.util.Date;

import com.dreamer8.yosimce.client.general.DetalleCursoPlace;
import com.dreamer8.yosimce.client.planificacion.AgendarVisitaPlace;
import com.dreamer8.yosimce.client.planificacion.DetalleAgendaPlace;
import com.dreamer8.yosimce.shared.dto.AgendaPreviewDTO;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortList;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.SafeHtmlHeader;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class AgendamientosViewD extends Composite implements AgendamientosView {

	private static AgendamientosViewDUiBinder uiBinder = GWT
			.create(AgendamientosViewDUiBinder.class);

	interface AgendamientosViewDUiBinder extends
			UiBinder<Widget, AgendamientosViewD> {
	}
	
	
	@UiField Button filtrosButton;
	@UiField Button exportarButton;
	@UiField Button modificarAgendaButton;
	@UiField Button detallesButton;
	@UiField Button informacionButton;
	@UiField  HTML establecimientoSeleccionado;
	@UiField(provided = true) DataGrid<AgendaPreviewDTO> dataGrid;
	@UiField(provided = true) SimplePager pager;
	
	
	private AgendamientosPresenter presenter;
	private SingleSelectionModel<AgendaPreviewDTO> selectionModel;
	private AgendaPreviewDTO selectedItem;
	
	private DialogBox filtrosDialogBox;
	private FiltroAgendamientosPanelViewD filtrosPanel;
	
	public AgendamientosViewD() {
		dataGrid = new DataGrid<AgendaPreviewDTO>(AgendaPreviewDTO.KEY_PROVIDER);
		pager = new SimplePager(TextLocation.CENTER, false, false);
		initWidget(uiBinder.createAndBindUi(this));
		filtrosDialogBox = new DialogBox(true, false);
		filtrosPanel = new FiltroAgendamientosPanelViewD();
		filtrosDialogBox.setWidget(filtrosPanel);
		bind();
	}
	
	@UiHandler("filtrosButton")
	void onFiltrosClick(ClickEvent event){
		filtrosDialogBox.showRelativeTo(filtrosButton);
	}
	
	@UiHandler("modificarAgendaButton")
	void onModificarAgendaClick(ClickEvent event){
		AgendarVisitaPlace avp = new AgendarVisitaPlace();
		if(selectedItem !=null)avp.setCursoId(selectedItem.getEstablecimientoId());
		presenter.goTo(avp);
	}
	
	@UiHandler("detallesButton")
	void onDetallesClick(ClickEvent event){
		DetalleAgendaPlace daep = new DetalleAgendaPlace();
		if(selectedItem !=null)daep.setCursoId(selectedItem.getEstablecimientoId());
		presenter.goTo(daep);
	}
	
	@UiHandler("informacionButton")
	void onInformacionClick(ClickEvent event){
		DetalleCursoPlace dcp = new DetalleCursoPlace();
		if(selectedItem !=null)dcp.setCursoId(selectedItem.getEstablecimientoId());
		presenter.goTo(dcp);
	}

	@Override
	public void setPresenter(AgendamientosPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public HasData<AgendaPreviewDTO> getDataDisplay() {
		return dataGrid;
	}

	@Override
	public ColumnSortList getColumnSortList() {
		return dataGrid.getColumnSortList();
	}

	@Override
	public Column<AgendaPreviewDTO,?> getColumn(int index) {
		return dataGrid.getColumn(index);
	}

	@Override
	public int getColumnIndex(Column<AgendaPreviewDTO,?> column) {
		return dataGrid.getColumnIndex(column);
	}
	
	private void bind(){
		dataGrid.setWidth("100%");
		buildGrid();		
		modificarAgendaButton.setVisible(false);
		detallesButton.setVisible(false);
		informacionButton.setVisible(false);
		
		pager.setDisplay(dataGrid);
		
		selectionModel = new SingleSelectionModel<AgendaPreviewDTO>(AgendaPreviewDTO.KEY_PROVIDER);
		
		dataGrid.setSelectionModel(selectionModel,new CellPreviewEvent.Handler<AgendaPreviewDTO>(){

			@Override
			public void onCellPreview(CellPreviewEvent<AgendaPreviewDTO> event) {
				if(!event.getNativeEvent().getType().contains("click")){
					return;
				}
				selectionModel.setSelected(event.getValue(), !selectionModel.isSelected(event.getValue()));
			}});
		
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				establecimientoSeleccionado.setHTML((selectionModel.getSelectedObject()!=null)?
						selectionModel.getSelectedObject().getEstablecimientoName()+" >":"");
				modificarAgendaButton.setVisible(selectionModel.getSelectedObject()!=null);
				detallesButton.setVisible(selectionModel.getSelectedObject()!=null);
				informacionButton.setVisible(selectionModel.getSelectedObject()!=null);
				selectedItem = selectionModel.getSelectedObject();
			}
		});
	}

	
	private void buildGrid(){
		Column<AgendaPreviewDTO, String> rbdColumn =new Column<AgendaPreviewDTO, String>(new TextCell()) {
            @Override
            public String getValue(AgendaPreviewDTO object) {
                return object.getRbd();
            }
        };
        rbdColumn.setSortable(false);
        dataGrid.addColumn(rbdColumn, new SafeHtmlHeader(SafeHtmlUtils.fromSafeConstant("RBD")));
        dataGrid.setColumnWidth(rbdColumn, 60, Unit.PX);
        
        Column<AgendaPreviewDTO, String> establecimientoColumn =new Column<AgendaPreviewDTO, String>(new TextCell()) {
            @Override
            public String getValue(AgendaPreviewDTO object) {
                return object.getEstablecimientoName();
            }
        };
        establecimientoColumn.setSortable(true);
        dataGrid.addColumn(establecimientoColumn, new SafeHtmlHeader(SafeHtmlUtils.fromSafeConstant("Establecimiento")));
        dataGrid.setColumnWidth(establecimientoColumn, 170, Unit.PX);
        
        Column<AgendaPreviewDTO, String> cursoColumn =new Column<AgendaPreviewDTO, String>(new TextCell()) {
            @Override
            public String getValue(AgendaPreviewDTO object) {
                return object.getCurso();
            }
        };
        cursoColumn.setSortable(false);
        dataGrid.addColumn(cursoColumn, new SafeHtmlHeader(SafeHtmlUtils.fromSafeConstant("Curso")));
        dataGrid.setColumnWidth(cursoColumn, 80, Unit.PX);
        
        Column<AgendaPreviewDTO, String> tipoColumn =new Column<AgendaPreviewDTO, String>(new TextCell()) {
            @Override
            public String getValue(AgendaPreviewDTO object) {
                return object.getTipoEstablecimiento().getTipo();
            }
        };
        tipoColumn.setSortable(false);
        dataGrid.addColumn(tipoColumn, new SafeHtmlHeader(SafeHtmlUtils.fromSafeConstant("Tipo")));
        dataGrid.setColumnWidth(tipoColumn, 70, Unit.PX);
        
        Column<AgendaPreviewDTO, String> estadoColumn =new Column<AgendaPreviewDTO, String>(new TextCell()) {
            @Override
            public String getValue(AgendaPreviewDTO object) {
                return object.getAgendaItemActual().getEstado().getEstado();
            }
        };
        estadoColumn.setSortable(false);
        dataGrid.addColumn(estadoColumn, new SafeHtmlHeader(SafeHtmlUtils.fromSafeConstant("Estado actual")));
        dataGrid.setColumnWidth(estadoColumn, 140, Unit.PX);
        
        Column<AgendaPreviewDTO, Date> dateColumn =new Column<AgendaPreviewDTO, Date>(new DateCell(DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_MEDIUM))) {
            @Override
            public Date getValue(AgendaPreviewDTO object) {
                return object.getAgendaItemActual().getFecha();
            }
        };
        dateColumn.setSortable(true);
        dataGrid.addColumn(dateColumn, new SafeHtmlHeader(SafeHtmlUtils.fromSafeConstant("Fecha")));
        dataGrid.setColumnWidth(dateColumn, 160, Unit.PX);
        
        Column<AgendaPreviewDTO, String> regionColumn =new Column<AgendaPreviewDTO, String>(new TextCell()) {
            @Override
            public String getValue(AgendaPreviewDTO object) {
                return object.getRegionName();
            }
        };
        regionColumn.setSortable(false);
        dataGrid.addColumn(regionColumn, new SafeHtmlHeader(SafeHtmlUtils.fromSafeConstant("Región")));
        dataGrid.setColumnWidth(regionColumn, 140, Unit.PX);
        
        Column<AgendaPreviewDTO, String> comunaColumn =new Column<AgendaPreviewDTO, String>(new TextCell()) {
            @Override
            public String getValue(AgendaPreviewDTO object) {
                return object.getComunaName();
            }
        };
        comunaColumn.setSortable(false);
        dataGrid.addColumn(comunaColumn, new SafeHtmlHeader(SafeHtmlUtils.fromSafeConstant("Comuna")));
        dataGrid.setColumnWidth(comunaColumn, 120, Unit.PX);
        
        Column<AgendaPreviewDTO, String> comentarioColumn =new Column<AgendaPreviewDTO, String>(new TextCell()) {
            @Override
            public String getValue(AgendaPreviewDTO object) {
                return (object.getAgendaItemActual().getComentario().length()<60)?object.getAgendaItemActual().getComentario():object.getAgendaItemActual().getComentario().substring(0,56)+"...";
            }
        };
        comentarioColumn.setSortable(false);
        dataGrid.addColumn(comentarioColumn, new SafeHtmlHeader(SafeHtmlUtils.fromSafeConstant("Comentario")));
        dataGrid.setColumnWidth(comentarioColumn, 300, Unit.PX);
	}

	@Override
	public void clearCursoSelection() {
		if(selectedItem !=null){
			selectionModel.setSelected(selectedItem, false);
			selectedItem = null;
		}
		
	}
	
}
