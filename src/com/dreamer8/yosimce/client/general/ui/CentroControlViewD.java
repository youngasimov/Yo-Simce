package com.dreamer8.yosimce.client.general.ui;

import java.util.ArrayList;

import com.dreamer8.yosimce.client.general.CentroControlActivity;
import com.dreamer8.yosimce.client.general.CentroControlActivity.CentroOperacionWrap;
import com.dreamer8.yosimce.client.ui.HyperlinkCell;
import com.dreamer8.yosimce.client.ui.OverMenuBar;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;

public class CentroControlViewD extends Composite implements CentroControlView {

	private static CentroControlViewDUiBinder uiBinder = GWT
			.create(CentroControlViewDUiBinder.class);

	interface CentroControlViewDUiBinder extends
			UiBinder<Widget, CentroControlViewD> {
	}

	@UiField OverMenuBar menu;
	@UiField MenuItem menuItem;
	@UiField MenuItem actualizarMenu;
	@UiField MenuItem autoCarga2Menu;
	@UiField MenuItem autoCarga5Menu;
	@UiField MenuItem autoCarga10Menu;
	@UiField MenuItem sendToMonitorItem;
	@UiField MenuItem removeFromMonitorItem;
	@UiField MenuItem establecimientoItem;
	@UiField MenuItem centroItem;
	@UiField MenuItem imprentaItem;
	@UiField MenuItem ministerioItem;
	@UiField(provided=true) DataGrid<CentroControlActivity.CentroOperacionWrap> monitorTable;
	@UiField(provided=true) DataGrid<CentroControlActivity.CentroOperacionWrap> incompleteTable;
	@UiField(provided=true) DataGrid<CentroControlActivity.CentroOperacionWrap> completeTable;
	@UiField(provided=true) DataGrid<CentroControlActivity.CentroOperacionWrap> allTable;
	
	private ListDataProvider<CentroControlActivity.CentroOperacionWrap> monitorDataProvider;
	private ListDataProvider<CentroControlActivity.CentroOperacionWrap> incompleteDataProvider;
	private ListDataProvider<CentroControlActivity.CentroOperacionWrap> completeDataProvider;
	private ListDataProvider<CentroControlActivity.CentroOperacionWrap> allDataProvider;
	
	private MultiSelectionModel<CentroControlActivity.CentroOperacionWrap> monitorSelectionModel;
	private MultiSelectionModel<CentroControlActivity.CentroOperacionWrap> allSelectionModel;
	
	private CentroControlPresenter presenter;
	
	private boolean autoRecargaActivated;
	
	
	public CentroControlViewD() {
		monitorTable = new DataGrid<CentroControlActivity.CentroOperacionWrap>(CentroControlActivity.CentroOperacionWrap.KEY_PROVIDER);
		incompleteTable = new DataGrid<CentroControlActivity.CentroOperacionWrap>(CentroControlActivity.CentroOperacionWrap.KEY_PROVIDER);
		completeTable = new DataGrid<CentroControlActivity.CentroOperacionWrap>(CentroControlActivity.CentroOperacionWrap.KEY_PROVIDER);
		allTable = new DataGrid<CentroControlActivity.CentroOperacionWrap>(CentroControlActivity.CentroOperacionWrap.KEY_PROVIDER);
		
		monitorTable.setPageSize(500);
		incompleteTable.setPageSize(500);
		completeTable.setPageSize(500);
		allTable.setPageSize(500);
		
		monitorDataProvider = new ListDataProvider<CentroControlActivity.CentroOperacionWrap>();
		incompleteDataProvider = new ListDataProvider<CentroControlActivity.CentroOperacionWrap>();
		completeDataProvider = new ListDataProvider<CentroControlActivity.CentroOperacionWrap>();
		allDataProvider = new ListDataProvider<CentroControlActivity.CentroOperacionWrap>();
		
		monitorSelectionModel = new MultiSelectionModel<CentroControlActivity.CentroOperacionWrap>();
		allSelectionModel = new MultiSelectionModel<CentroControlActivity.CentroOperacionWrap>();
		
		monitorDataProvider.addDataDisplay(monitorTable);
		monitorTable.setSelectionModel(monitorSelectionModel,DefaultSelectionEventManager
		        .<CentroControlActivity.CentroOperacionWrap> createCheckboxManager());
		
		allDataProvider.addDataDisplay(allTable);
		allTable.setSelectionModel(allSelectionModel,DefaultSelectionEventManager
		        .<CentroControlActivity.CentroOperacionWrap> createCheckboxManager());
		
		incompleteDataProvider.addDataDisplay(incompleteTable);
		completeDataProvider.addDataDisplay(completeTable);
		
		allSelectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				sendToMonitorItem.setVisible(!allSelectionModel.getSelectedSet().isEmpty());
			}
		});
		
		monitorSelectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				removeFromMonitorItem.setVisible(!monitorSelectionModel.getSelectedSet().isEmpty());
			}
		});
		
		buildAllTable();
		buildMonitorTable();
		buildTable(completeTable);
		buildTable(incompleteTable);
		initWidget(uiBinder.createAndBindUi(this));
		sendToMonitorItem.setVisible(false);
		removeFromMonitorItem.setVisible(false);
		autoRecargaActivated = false;
		menu.setOverItem(menuItem);
		menu.insertSeparator(3);
		menu.setOverCommand(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				presenter.toggleMenu();
			}
		});
		actualizarMenu.setScheduledCommand(new Scheduler.ScheduledCommand(){

			@Override
			public void execute() {
				if(autoRecargaActivated){
					presenter.activarAutoRecarga(false, 0);
					autoRecargaActivated = false;
				}
				presenter.actualizar();
			}
		});
		autoCarga2Menu.setScheduledCommand(new Scheduler.ScheduledCommand(){

			@Override
			public void execute() {
				autoRecargaActivated = true;
				presenter.activarAutoRecarga(true, 120000);
			}
		});
		autoCarga5Menu.setScheduledCommand(new Scheduler.ScheduledCommand(){

			@Override
			public void execute() {
				autoRecargaActivated = true;
				presenter.activarAutoRecarga(true, 300000);
			}
		});
		autoCarga10Menu.setScheduledCommand(new Scheduler.ScheduledCommand(){

			@Override
			public void execute() {
				autoRecargaActivated = true;
				presenter.activarAutoRecarga(true, 600000);
			}
		});
		
		establecimientoItem.setScheduledCommand(new Scheduler.ScheduledCommand(){

			@Override
			public void execute() {
				presenter.setMonitorEtapaEstablecimiento();
			}
		});
		
		centroItem.setScheduledCommand(new Scheduler.ScheduledCommand(){

			@Override
			public void execute() {
				presenter.setMonitorEtapaCentro();
			}
		});
		
		imprentaItem.setScheduledCommand(new Scheduler.ScheduledCommand(){

			@Override
			public void execute() {
				presenter.setMonitorEtapaImprenta();
			}
		});
		
		ministerioItem.setScheduledCommand(new Scheduler.ScheduledCommand(){

			@Override
			public void execute() {
				presenter.setMonitorEtapaMinisterio();
			}
		});
		
		sendToMonitorItem.setScheduledCommand(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				ArrayList<CentroOperacionWrap> aux = new ArrayList<CentroControlActivity.CentroOperacionWrap>(allSelectionModel.getSelectedSet());
				for(CentroOperacionWrap cow:allDataProvider.getList()){
					if(allSelectionModel.isSelected(cow)){
						allSelectionModel.setSelected(cow, false);
					}
				}
				presenter.addToMonitor(aux);
			}
		});
		removeFromMonitorItem.setScheduledCommand(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				ArrayList<CentroOperacionWrap> aux = new ArrayList<CentroControlActivity.CentroOperacionWrap>(monitorSelectionModel.getSelectedSet());
				for(CentroOperacionWrap cow:monitorDataProvider.getList()){
					if(monitorSelectionModel.isSelected(cow)){
						monitorSelectionModel.setSelected(cow, false);
					}
				}
				presenter.removeFromMonitor(aux);
			}
		});
	}

	@Override
	public void setPresenter(CentroControlPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public ListDataProvider<CentroOperacionWrap> getMonitorDataProvider() {
		return monitorDataProvider;
	}

	@Override
	public ListDataProvider<CentroOperacionWrap> getAllDataProvider() {
		return allDataProvider;
	}

	@Override
	public ListDataProvider<CentroOperacionWrap> getCompleteDataProvider() {
		return completeDataProvider;
	}

	@Override
	public ListDataProvider<CentroOperacionWrap> getIncompleteDataProvider() {
		return incompleteDataProvider;
	}
	
	private void buildAllTable(){
		
		Column<CentroOperacionWrap, Boolean> checkColumn = new Column<CentroOperacionWrap, Boolean>(new CheckboxCell(true, false)) {
			@Override
			public Boolean getValue(CentroOperacionWrap object) {
				return allSelectionModel.isSelected(object);
			}
		};
		allTable.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		allTable.setColumnWidth(checkColumn, 40, Unit.PX);
		
		Column<CentroOperacionWrap, String> centroColumn = new Column<CentroOperacionWrap, String>(new TextCell()) {

			@Override
			public String getValue(CentroOperacionWrap o) {
				return o.centroOperacion.getNombre();
			}
		};
		centroColumn.setSortable(false);
		allTable.addColumn(centroColumn, SafeHtmlUtils.fromSafeConstant("Centro"));
		allTable.setColumnWidth(centroColumn, 100, Unit.PX);
		
		Column<CentroOperacionWrap, Number> enCentroColumn = new Column<CentroOperacionWrap, Number>(new NumberCell()) {

			@Override
			public Number getValue(CentroOperacionWrap o) {
				return o.centroOperacion.getEnCentro();
			}
		};
		enCentroColumn.setSortable(false);
		allTable.addColumn(enCentroColumn, SafeHtmlUtils.fromSafeConstant("En centro"));
		allTable.setColumnWidth(enCentroColumn, 90, Unit.PX);
		
		Column<CentroOperacionWrap, Number> enEstablecimientoColumn = new Column<CentroOperacionWrap, Number>(new NumberCell()) {

			@Override
			public Number getValue(CentroOperacionWrap o) {
				return o.centroOperacion.getEnEstablecimiento();
			}
		};
		enEstablecimientoColumn.setSortable(false);
		allTable.addColumn(enEstablecimientoColumn, SafeHtmlUtils.fromSafeConstant("En establecimiento"));
		allTable.setColumnWidth(enEstablecimientoColumn, 90, Unit.PX);
		
		Column<CentroOperacionWrap, Number> enImprentaColumn = new Column<CentroOperacionWrap, Number>(new NumberCell()) {

			@Override
			public Number getValue(CentroOperacionWrap o) {
				return o.centroOperacion.getEnImprenta();
			}
		};
		enImprentaColumn.setSortable(false);
		allTable.addColumn(enImprentaColumn, SafeHtmlUtils.fromSafeConstant("En imprenta"));
		allTable.setColumnWidth(enImprentaColumn, 90, Unit.PX);
		
		Column<CentroOperacionWrap, Number> enMinisterioColumn = new Column<CentroOperacionWrap, Number>(new NumberCell()) {

			@Override
			public Number getValue(CentroOperacionWrap o) {
				return o.centroOperacion.getEnMinisterio();
			}
		};
		enMinisterioColumn.setSortable(false);
		allTable.addColumn(enMinisterioColumn, SafeHtmlUtils.fromSafeConstant("En ministerio"));
		allTable.setColumnWidth(enMinisterioColumn, 90, Unit.PX);
	}

	private void buildMonitorTable(){
		
		Column<CentroOperacionWrap, Boolean> checkColumn = new Column<CentroOperacionWrap, Boolean>(new CheckboxCell(true, false)) {
			@Override
			public Boolean getValue(CentroOperacionWrap object) {
				return monitorSelectionModel.isSelected(object);
			}
		};
		monitorTable.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		monitorTable.setColumnWidth(checkColumn, 40, Unit.PX);
		
		Column<CentroOperacionWrap, Hyperlink> trackingColumn = new Column<CentroOperacionWrap, Hyperlink>(new HyperlinkCell()) {

			@Override
			public Hyperlink getValue(CentroOperacionWrap o) {
				return o.hyperlink;
			}
		};
		trackingColumn.setSortable(false);
		monitorTable.addColumn(trackingColumn);
		monitorTable.setColumnWidth(trackingColumn, 100, Unit.PX);
		
		Column<CentroOperacionWrap, String> centroColumn = new Column<CentroOperacionWrap, String>(new TextCell()) {

			@Override
			public String getValue(CentroOperacionWrap o) {
				return o.centroOperacion.getNombre();
			}
		};
		centroColumn.setSortable(false);
		monitorTable.addColumn(centroColumn, SafeHtmlUtils.fromSafeConstant("Centro"));
		monitorTable.setColumnWidth(centroColumn, 100, Unit.PX);
		
		Column<CentroOperacionWrap, Number> enCentroColumn = new Column<CentroOperacionWrap, Number>(new NumberCell()) {

			@Override
			public Number getValue(CentroOperacionWrap o) {
				return o.centroOperacion.getEnCentro();
			}
		};
		enCentroColumn.setSortable(false);
		monitorTable.addColumn(enCentroColumn, SafeHtmlUtils.fromSafeConstant("En centro"));
		monitorTable.setColumnWidth(enCentroColumn, 90, Unit.PX);
		
		Column<CentroOperacionWrap, Number> enEstablecimientoColumn = new Column<CentroOperacionWrap, Number>(new NumberCell()) {

			@Override
			public Number getValue(CentroOperacionWrap o) {
				return o.centroOperacion.getEnEstablecimiento();
			}
		};
		enEstablecimientoColumn.setSortable(false);
		monitorTable.addColumn(enEstablecimientoColumn, SafeHtmlUtils.fromSafeConstant("En establecimiento"));
		monitorTable.setColumnWidth(enEstablecimientoColumn, 90, Unit.PX);
		
		Column<CentroOperacionWrap, Number> enImprentaColumn = new Column<CentroOperacionWrap, Number>(new NumberCell()) {

			@Override
			public Number getValue(CentroOperacionWrap o) {
				return o.centroOperacion.getEnImprenta();
			}
		};
		enImprentaColumn.setSortable(false);
		monitorTable.addColumn(enImprentaColumn, SafeHtmlUtils.fromSafeConstant("En imprenta"));
		monitorTable.setColumnWidth(enImprentaColumn, 90, Unit.PX);
		
		Column<CentroOperacionWrap, Number> enMinisterioColumn = new Column<CentroOperacionWrap, Number>(new NumberCell()) {

			@Override
			public Number getValue(CentroOperacionWrap o) {
				return o.centroOperacion.getEnMinisterio();
			}
		};
		enMinisterioColumn.setSortable(false);
		monitorTable.addColumn(enMinisterioColumn, SafeHtmlUtils.fromSafeConstant("En ministerio"));
		monitorTable.setColumnWidth(enMinisterioColumn, 90, Unit.PX);
		
		Column<CentroOperacionWrap, Number> enCentroFixedColumn = new Column<CentroOperacionWrap, Number>(new NumberCell()) {

			@Override
			public Number getValue(CentroOperacionWrap o) {
				return o.maxEnCentro;
			}
		};
		enCentroFixedColumn.setSortable(false);
		monitorTable.addColumn(enCentroFixedColumn, SafeHtmlUtils.fromSafeConstant("En centro"));
		monitorTable.setColumnWidth(enCentroFixedColumn, 90, Unit.PX);
		
		Column<CentroOperacionWrap, Number> enEstablecimientoFixedColumn = new Column<CentroOperacionWrap, Number>(new NumberCell()) {

			@Override
			public Number getValue(CentroOperacionWrap o) {
				return o.maxEnEstablecimiento;
			}
		};
		enEstablecimientoFixedColumn.setSortable(false);
		monitorTable.addColumn(enEstablecimientoFixedColumn, SafeHtmlUtils.fromSafeConstant("En establecimiento"));
		monitorTable.setColumnWidth(enEstablecimientoFixedColumn, 90, Unit.PX);
		
		Column<CentroOperacionWrap, Number> enImprentaFixedColumn = new Column<CentroOperacionWrap, Number>(new NumberCell()) {

			@Override
			public Number getValue(CentroOperacionWrap o) {
				return o.maxEnImprenta;
			}
		};
		enImprentaFixedColumn.setSortable(false);
		monitorTable.addColumn(enImprentaFixedColumn, SafeHtmlUtils.fromSafeConstant("En imprenta"));
		monitorTable.setColumnWidth(enImprentaFixedColumn, 90, Unit.PX);
		
		Column<CentroOperacionWrap, Number> enMinisterioFixedColumn = new Column<CentroOperacionWrap, Number>(new NumberCell()) {

			@Override
			public Number getValue(CentroOperacionWrap o) {
				return o.maxEnMinisterio;
			}
		};
		enMinisterioFixedColumn.setSortable(false);
		monitorTable.addColumn(enMinisterioFixedColumn, SafeHtmlUtils.fromSafeConstant("En ministerio"));
		monitorTable.setColumnWidth(enMinisterioFixedColumn, 90, Unit.PX);
	}
	
	private void buildTable(DataGrid<CentroOperacionWrap> table){
		Column<CentroOperacionWrap, String> centroColumn = new Column<CentroOperacionWrap, String>(new TextCell()) {

			@Override
			public String getValue(CentroOperacionWrap o) {
				return o.centroOperacion.getNombre();
			}
		};
		centroColumn.setSortable(false);
		table.addColumn(centroColumn, SafeHtmlUtils.fromSafeConstant("Centro"));
		table.setColumnWidth(centroColumn, 100, Unit.PX);
		
		Column<CentroOperacionWrap, Number> enCentroColumn = new Column<CentroOperacionWrap, Number>(new NumberCell()) {

			@Override
			public Number getValue(CentroOperacionWrap o) {
				return o.maxEnCentro;
			}
		};
		enCentroColumn.setSortable(false);
		table.addColumn(enCentroColumn, SafeHtmlUtils.fromSafeConstant("En centro"));
		table.setColumnWidth(enCentroColumn, 90, Unit.PX);
		
		Column<CentroOperacionWrap, Number> enEstablecimientoColumn = new Column<CentroOperacionWrap, Number>(new NumberCell()) {

			@Override
			public Number getValue(CentroOperacionWrap o) {
				return o.maxEnEstablecimiento;
			}
		};
		enEstablecimientoColumn.setSortable(false);
		table.addColumn(enEstablecimientoColumn, SafeHtmlUtils.fromSafeConstant("En establecimiento"));
		table.setColumnWidth(enEstablecimientoColumn, 90, Unit.PX);
		
		Column<CentroOperacionWrap, Number> enImprentaColumn = new Column<CentroOperacionWrap, Number>(new NumberCell()) {

			@Override
			public Number getValue(CentroOperacionWrap o) {
				return o.maxEnImprenta;
			}
		};
		enImprentaColumn.setSortable(false);
		table.addColumn(enImprentaColumn, SafeHtmlUtils.fromSafeConstant("En imprenta"));
		table.setColumnWidth(enImprentaColumn, 90, Unit.PX);
		
		Column<CentroOperacionWrap, Number> enMinisterioColumn = new Column<CentroOperacionWrap, Number>(new NumberCell()) {

			@Override
			public Number getValue(CentroOperacionWrap o) {
				return o.maxEnMinisterio;
			}
		};
		enMinisterioColumn.setSortable(false);
		table.addColumn(enMinisterioColumn, SafeHtmlUtils.fromSafeConstant("En ministerio"));
		table.setColumnWidth(enMinisterioColumn, 90, Unit.PX);
	}
}
