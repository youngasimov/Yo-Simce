package com.dreamer8.yosimce.client.material.ui;

import gwtupload.client.IFileInput.FileInputType;
import gwtupload.client.SingleUploader;

import java.util.Date;

import com.dreamer8.yosimce.client.ui.OverMenuBar;
import com.dreamer8.yosimce.client.ui.PlaceHolderTextBox;
import com.dreamer8.yosimce.shared.dto.HistorialMaterialItemDTO;
import com.dreamer8.yosimce.shared.dto.MaterialDTO;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;

public class CentroOperacionViewD extends Composite implements CentroOperacionView {

	private static CentroOperacionViewDUiBinder uiBinder = GWT
			.create(CentroOperacionViewDUiBinder.class);

	interface CentroOperacionViewDUiBinder extends
			UiBinder<Widget, CentroOperacionViewD> {
	}

	
	@UiField OverMenuBar menu;
	@UiField MenuItem menuItem;
	@UiField MenuItem cosItem;
	@UiField MenuItem filtrosItem;
	@UiField MenuItem exportarItem;
	
	@UiField(provided=true) DataGrid<HistorialMaterialItemDTO> historialGrid;
	@UiField(provided=true) DataGrid<MaterialDTO> materialGrid;
	@UiField(provided=true) DataGrid<MaterialDTO> ingresoGrid;
	@UiField(provided=true) DataGrid<MaterialDTO> predespachoGrid;
	@UiField(provided=true) DataGrid<MaterialDTO> despachoGrid;
	@UiField(provided=true) SingleUploader ingresoUploader;
	@UiField(provided=true) SingleUploader despachoUploader;
	@UiField Label docIngresoLabel;
	@UiField Label totalIngresoLabel;
	@UiField PlaceHolderTextBox ingresoBox;
	
	
	private CentroOperacionPresenter presenter;
	
	public CentroOperacionViewD() {
		
		historialGrid = new DataGrid<HistorialMaterialItemDTO>(HistorialMaterialItemDTO.KEY_PROVIDER);
		materialGrid = new DataGrid<MaterialDTO>(MaterialDTO.KEY_PROVIDER);
		ingresoGrid = new DataGrid<MaterialDTO>(MaterialDTO.KEY_PROVIDER);
		predespachoGrid = new DataGrid<MaterialDTO>(MaterialDTO.KEY_PROVIDER);
		despachoGrid = new DataGrid<MaterialDTO>(MaterialDTO.KEY_PROVIDER);
		ingresoUploader = new SingleUploader(FileInputType.ANCHOR);
		ingresoUploader.setAutoSubmit(true);
		despachoUploader = new SingleUploader(FileInputType.ANCHOR);
		despachoUploader.setAutoSubmit(true);
		initWidget(uiBinder.createAndBindUi(this));
		menu.insertSeparator(2);
		menu.setOverItem(menuItem);
		menu.setOverCommand(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				presenter.toggleMenu();
			}
		});
		
		buildHistorialTable();
		buildMaterialTable();
		buildIngresoGrid();
		buildPreDespachoTable();
	}

	@Override
	public void setPresenter(CentroOperacionPresenter presenter) {
		this.presenter = presenter;
	}
	
	private void buildHistorialTable(){
		Column<HistorialMaterialItemDTO,Date> dateColumn = new Column<HistorialMaterialItemDTO,Date>(new DateCell()){

			@Override
			public Date getValue(HistorialMaterialItemDTO object) {
				return new Date();
			}
		};
		dateColumn.setSortable(true);
		historialGrid.addColumn(dateColumn,"Fecha");
		
		Column<HistorialMaterialItemDTO,String> desdeColumn = new Column<HistorialMaterialItemDTO,String>(new TextCell()){

			@Override
			public String getValue(HistorialMaterialItemDTO object) {
				return "";
			}
		};
		historialGrid.addColumn(desdeColumn,"Desde");
		
		Column<HistorialMaterialItemDTO,String> haciaColumn = new Column<HistorialMaterialItemDTO,String>(new TextCell()){

			@Override
			public String getValue(HistorialMaterialItemDTO object) {
				return "";
			}
		};
		historialGrid.addColumn(haciaColumn,"Hacia");
		
		Column<HistorialMaterialItemDTO,String> usuarioColumn = new Column<HistorialMaterialItemDTO,String>(new TextCell()){

			@Override
			public String getValue(HistorialMaterialItemDTO object) {
				return "";
			}
		};
		historialGrid.addColumn(usuarioColumn,"Usuario autorizante");
	}
	
	private void buildMaterialTable(){
		Column<MaterialDTO,String> idColumn = new Column<MaterialDTO,String>(new TextCell()){

			@Override
			public String getValue(MaterialDTO object) {
				return "";
			}
		};
		materialGrid.addColumn(idColumn,"Id");
		
		Column<MaterialDTO,String> tipoColumn = new Column<MaterialDTO,String>(new TextCell()){

			@Override
			public String getValue(MaterialDTO object) {
				return "";
			}
		};
		tipoColumn.setSortable(true);
		materialGrid.addColumn(tipoColumn,"Tipo");
		
		Column<MaterialDTO,String> rbdColumn = new Column<MaterialDTO,String>(new TextCell()){

			@Override
			public String getValue(MaterialDTO object) {
				return "";
			}
		};
		rbdColumn.setSortable(true);
		materialGrid.addColumn(rbdColumn,"RBD");
		
		Column<MaterialDTO,String> establecimientoColumn = new Column<MaterialDTO,String>(new TextCell()){

			@Override
			public String getValue(MaterialDTO object) {
				return "";
			}
		};
		materialGrid.addColumn(establecimientoColumn,"Establecimiento");
		
		Column<MaterialDTO,String> nivelColumn = new Column<MaterialDTO,String>(new TextCell()){

			@Override
			public String getValue(MaterialDTO object) {
				return "";
			}
		};
		materialGrid.addColumn(nivelColumn,"Nivel");
		
		Column<MaterialDTO,String> cursoColumn = new Column<MaterialDTO,String>(new TextCell()){

			@Override
			public String getValue(MaterialDTO object) {
				return "";
			}
		};
		materialGrid.addColumn(cursoColumn,"Curso");
		
		Column<MaterialDTO,String> etapaColumn = new Column<MaterialDTO,String>(new TextCell()){

			@Override
			public String getValue(MaterialDTO object) {
				return "";
			}
		};
		etapaColumn.setSortable(true);
		materialGrid.addColumn(etapaColumn,"Etapa");
	}
	
	private void buildIngresoGrid(){
		Column<MaterialDTO,String> idColumn = new Column<MaterialDTO,String>(new TextCell()){

			@Override
			public String getValue(MaterialDTO object) {
				return "";
			}
		};
		ingresoGrid.addColumn(idColumn,"Id");
		
		Column<MaterialDTO,String> tipoColumn = new Column<MaterialDTO,String>(new TextCell()){

			@Override
			public String getValue(MaterialDTO object) {
				return "";
			}
		};
		ingresoGrid.addColumn(tipoColumn,"Tipo");
		
		Column<MaterialDTO,String> rbdColumn = new Column<MaterialDTO,String>(new TextCell()){

			@Override
			public String getValue(MaterialDTO object) {
				return "";
			}
		};
		ingresoGrid.addColumn(rbdColumn,"RBD");
		
		Column<MaterialDTO,String> establecimientoColumn = new Column<MaterialDTO,String>(new TextCell()){

			@Override
			public String getValue(MaterialDTO object) {
				return "";
			}
		};
		ingresoGrid.addColumn(establecimientoColumn,"Establecimiento");
		
		Column<MaterialDTO,String> nivelColumn = new Column<MaterialDTO,String>(new TextCell()){

			@Override
			public String getValue(MaterialDTO object) {
				return "";
			}
		};
		ingresoGrid.addColumn(nivelColumn,"Nivel");
		
		Column<MaterialDTO,String> cursoColumn = new Column<MaterialDTO,String>(new TextCell()){

			@Override
			public String getValue(MaterialDTO object) {
				return "";
			}
		};
		ingresoGrid.addColumn(cursoColumn,"Curso");
	}
	
	private void buildPreDespachoTable(){
		Column<MaterialDTO,String> idColumn = new Column<MaterialDTO,String>(new TextCell()){

			@Override
			public String getValue(MaterialDTO object) {
				return "";
			}
		};
		predespachoGrid.addColumn(idColumn,"Id");
		
		Column<MaterialDTO,String> tipoColumn = new Column<MaterialDTO,String>(new TextCell()){

			@Override
			public String getValue(MaterialDTO object) {
				return "";
			}
		};
		predespachoGrid.addColumn(tipoColumn,"Tipo");
		
		Column<MaterialDTO,String> rbdColumn = new Column<MaterialDTO,String>(new TextCell()){

			@Override
			public String getValue(MaterialDTO object) {
				return "";
			}
		};
		predespachoGrid.addColumn(rbdColumn,"RBD");
		
		Column<MaterialDTO,String> establecimientoColumn = new Column<MaterialDTO,String>(new TextCell()){

			@Override
			public String getValue(MaterialDTO object) {
				return "";
			}
		};
		predespachoGrid.addColumn(establecimientoColumn,"Establecimiento");
		
		Column<MaterialDTO,String> nivelColumn = new Column<MaterialDTO,String>(new TextCell()){

			@Override
			public String getValue(MaterialDTO object) {
				return "";
			}
		};
		predespachoGrid.addColumn(nivelColumn,"Nivel");
		
		Column<MaterialDTO,String> cursoColumn = new Column<MaterialDTO,String>(new TextCell()){

			@Override
			public String getValue(MaterialDTO object) {
				return "";
			}
		};
		predespachoGrid.addColumn(cursoColumn,"Curso");
	}

}
