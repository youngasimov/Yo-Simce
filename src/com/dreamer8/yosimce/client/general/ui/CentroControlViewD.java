package com.dreamer8.yosimce.client.general.ui;

import java.util.ArrayList;

import com.dreamer8.yosimce.shared.dto.CentroOperacionDTO;
import com.dreamer8.yosimce.shared.dto.SectorDTO;
import com.dreamer8.yosimce.client.ui.HyperlinkCell;
import com.dreamer8.yosimce.client.ui.OverMenuBar;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.builder.shared.TableCellBuilder;
import com.google.gwt.dom.builder.shared.TableRowBuilder;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.AbstractHeaderOrFooterBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortList;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextHeader;
import com.google.gwt.user.cellview.client.AbstractCellTable.Style;
import com.google.gwt.user.cellview.client.ColumnSortList.ColumnSortInfo;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.StackLayoutPanel;
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
	
	interface Styles extends CssResource {
	    /**
	     * Indents cells in child rows.
	     */
	    String childCell();

	    /**
	     * Applies to group headers.
	     */
	    String groupHeaderCell();
	  }
	
	private class CoCell extends AbstractCell<CentroOperacionDTO>{

		@Override
		public void render(com.google.gwt.cell.client.Cell.Context context,
				CentroOperacionDTO value, SafeHtmlBuilder sb) {
			int sum = value.getEnCentro()+value.getEnEstablecimiento()+
					value.getEnImprenta()+value.getEnMinisterio()+
					value.getContingenciaEnCentro()+value.getContingenciaEnEstablecimiento()+
					value.getContingenciaEnImprenta()+value.getContingenciaEnMinisterio();
			String data = value.getNombre()+" (material:"+sum+")";
			sb.appendEscaped(data);
		}
		
	}
	
	/**
	 * Renders custom table headers. The top header row includes the groups
	 * "Name" and "Information", each of which spans multiple columns. The
	 * second row of the headers includes the contacts' first and last names
	 * grouped under the "Name" category. The second row also includes the age,
	 * category, and address of the contacts grouped under the "Information"
	 * category.
	 */
	private class CustomHeaderBuilder extends
			AbstractHeaderOrFooterBuilder<CentroOperacionDTO> {

		private Header<String> checkHeader = new TextHeader("");
		private Header<String> centroHeader = new TextHeader("Centro");
		private Header<String> miHeader = new TextHeader("MI");
		private Header<String> mcHeader = new TextHeader("MC");
		private Header<String> meHeader = new TextHeader("ME");
		private Header<String> mmHeader = new TextHeader("MM");
		private Header<String> ciHeader = new TextHeader("CI");
		private Header<String> ccHeader = new TextHeader("CC");
		private Header<String> ceHeader = new TextHeader("CE");
		private Header<String> cmHeader = new TextHeader("CM");
		private Header<String> trackingHeader = new TextHeader("");

		public CustomHeaderBuilder() {
			super(allTable, false);
			setSortIconStartOfLine(false);
		}

		@Override
		protected boolean buildHeaderOrFooterImpl() {

			TableRowBuilder tr = startRow();

			tr.startTH().colSpan(2).endTH();
			
			/*
			 * Name group header. Associated with the last name column, so
			 * clicking on the group header sorts by last name.
			 */
			TableCellBuilder th = tr.startTH().colSpan(4).className(style.groupHeaderCell());
			th.style().trustedProperty("border-right", "10px solid white").endStyle();
			th.text("Material Actividad").endTH();

			th = tr.startTH().colSpan(4).className(style.groupHeaderCell());
			th.style().trustedProperty("border-right", "10px solid white").endStyle();
			th.text("Material Complementario").endTH();
			
			tr.startTH().endTH();

			// Get information about the sorted column.
			ColumnSortList sortList = allTable.getColumnSortList();
			ColumnSortInfo sortedInfo = (sortList.size() == 0) ? null
					: sortList.get(0);
			Column<?, ?> sortedColumn = (sortedInfo == null) ? null
					: sortedInfo.getColumn();
			boolean isSortAscending = (sortedInfo == null) ? false : sortedInfo
					.isAscending();

			// Add column headers.
			tr = startRow();
			buildHeader(tr, checkHeader, checkColumn, sortedColumn,isSortAscending, false, false);
			buildHeader(tr, centroHeader, centroColumn, sortedColumn,isSortAscending, false, false);
			buildHeader(tr, miHeader, enImprentaColumn, sortedColumn,isSortAscending, false, false);
			buildHeader(tr, mcHeader, enCentroColumn, sortedColumn,isSortAscending, false, false);
			buildHeader(tr, meHeader, enEstablecimientoColumn, sortedColumn,isSortAscending, false, false);
			buildHeader(tr, mmHeader, enMinisterioColumn, sortedColumn,isSortAscending, false, false);
			
			buildHeader(tr, ciHeader, contingeciaImprentaColumn, sortedColumn,isSortAscending, false, false);
			buildHeader(tr, ccHeader, contingeciaCentroColumn, sortedColumn,isSortAscending, false, false);
			buildHeader(tr, ceHeader, contingeciaEstablecimientoColumn, sortedColumn,isSortAscending, false, false);
			buildHeader(tr, cmHeader, contingeciaMinisterioColumn, sortedColumn,isSortAscending, false, false);
			buildHeader(tr, trackingHeader, trackingColumn, sortedColumn,isSortAscending, false, false);
			tr.endTR();

			return true;
		}

		/**
		 * Renders the header of one column, with the given options.
		 * 
		 * @param out
		 *            the table row to build into
		 * @param header
		 *            the {@link Header} to render
		 * @param column
		 *            the column to associate with the header
		 * @param sortedColumn
		 *            the column that is currently sorted
		 * @param isSortAscending
		 *            true if the sorted column is in ascending order
		 * @param isFirst
		 *            true if this the first column
		 * @param isLast
		 *            true if this the last column
		 */
		private void buildHeader(TableRowBuilder out, Header<?> header,
				Column<CentroOperacionDTO, ?> column,
				Column<?, ?> sortedColumn, boolean isSortAscending,
				boolean isFirst, boolean isLast) {
			// Choose the classes to include with the element.
			Style style = allTable.getResources().style();
			boolean isSorted = (sortedColumn == column);
			StringBuilder classesBuilder = new StringBuilder(style.header());
			if (isFirst) {
				classesBuilder.append(" " + style.firstColumnHeader());
			}
			if (isLast) {
				classesBuilder.append(" " + style.lastColumnHeader());
			}
			if (column.isSortable()) {
				classesBuilder.append(" " + style.sortableHeader());
			}
			if (isSorted) {
				classesBuilder.append(" "
						+ (isSortAscending ? style.sortedHeaderAscending()
								: style.sortedHeaderDescending()));
			}

			// Create the table cell.
			TableCellBuilder th = out.startTH().className(
					classesBuilder.toString());

			// Associate the cell with the column to enable sorting of the
			// column.
			enableColumnHandlers(th, column);

			// Render the header.
			Context context = new Context(0, 2, header.getKey());
			renderSortableHeader(th, context, header, isSorted, isSortAscending);

			// End the table cell.
			th.endTH();
		}
	}

	@UiField
	Styles style;
	@UiField OverMenuBar menu;
	@UiField MenuItem menuItem;
	@UiField MenuItem actualizarMenu;
	@UiField MenuItem autoCargaMenu;
	@UiField MenuItem autoCarga2Menu;
	@UiField MenuItem autoCarga5Menu;
	@UiField MenuItem autoCarga10Menu;
	@UiField MenuItem sendToMonitorItem;
	@UiField MenuItem removeFromMonitorItem;
	@UiField StackLayoutPanel leftPanel;
	@UiField CheckBox selectAllBox;
	@UiField ListBox regionBox;
	@UiField ListBox zonaBox;
	@UiField ListBox comunaBox;
	@UiField(provided=true) DataGrid<CentroOperacionDTO> allTable;
	@UiField(provided=true) CellList<CentroOperacionDTO> monitorList;
	@UiField(provided=true) SimplePager allPager;
	
	private Column<CentroOperacionDTO, Boolean> checkColumn;
	private Column<CentroOperacionDTO, CentroOperacionDTO> centroColumn;
	private Column<CentroOperacionDTO, Number> enImprentaColumn;
	private Column<CentroOperacionDTO, Number> enCentroColumn;
	private Column<CentroOperacionDTO, Number> enEstablecimientoColumn;
	private Column<CentroOperacionDTO, Number> enMinisterioColumn;
	private Column<CentroOperacionDTO, Number> contingeciaImprentaColumn;
	private Column<CentroOperacionDTO, Number> contingeciaCentroColumn;
	private Column<CentroOperacionDTO, Number> contingeciaEstablecimientoColumn;
	private Column<CentroOperacionDTO, Number> contingeciaMinisterioColumn;
	private Column<CentroOperacionDTO, Hyperlink> trackingColumn;
	
	private ListDataProvider<CentroOperacionDTO> allDataProvider;
	private ListDataProvider<CentroOperacionDTO> monitorDataProvider;
	
	private MultiSelectionModel<CentroOperacionDTO> allSelectionModel;
	
	private CentroControlPresenter presenter;
	
	private boolean autoRecargaActivated;
	
	
	public CentroControlViewD() {
		allTable = new DataGrid<CentroOperacionDTO>(CentroOperacionDTO.KEY_PROVIDER);
		monitorList = new CellList<CentroOperacionDTO>(new CoCell());
		monitorList.setPageSize(500);
		allTable.setPageSize(100);
		allTable.setWidth("100%");
		allTable.setHeight("100%");
		allPager = new SimplePager(TextLocation.CENTER, false, false);
		allDataProvider = new ListDataProvider<CentroOperacionDTO>();
		monitorDataProvider = new ListDataProvider<CentroOperacionDTO>();
		allSelectionModel = new MultiSelectionModel<CentroOperacionDTO>();
		allDataProvider.addDataDisplay(allTable);
		monitorDataProvider.addDataDisplay(monitorList);
		allTable.setSelectionModel(allSelectionModel,DefaultSelectionEventManager
		        .<CentroOperacionDTO> createCheckboxManager());
		allSelectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				selectAllBox.setValue(allSelectionModel.getSelectedSet().size()==allDataProvider.getList().size(),false);
				sendToMonitorItem.setVisible(!allSelectionModel.getSelectedSet().isEmpty());
			}
		});
		
		initWidget(uiBinder.createAndBindUi(this));
		
		buildAllTable();
		
		allTable.addColumn(checkColumn);
		allTable.addColumn(centroColumn);
		allTable.addColumn(enImprentaColumn);
		allTable.addColumn(enCentroColumn);
		allTable.addColumn(enEstablecimientoColumn);
		allTable.addColumn(enMinisterioColumn);
		allTable.addColumn(contingeciaImprentaColumn);
		allTable.addColumn(contingeciaCentroColumn);
		allTable.addColumn(contingeciaEstablecimientoColumn);
		allTable.addColumn(contingeciaMinisterioColumn);
		allTable.addColumn(trackingColumn);
		
		allTable.setHeaderBuilder(new CustomHeaderBuilder());
		allPager.setDisplay(allTable);
		
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
		autoCargaMenu.setScheduledCommand(new Scheduler.ScheduledCommand(){

			@Override
			public void execute() {
				autoRecargaActivated = true;
				presenter.activarAutoRecarga(true, 60000);
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
		
		
		sendToMonitorItem.setScheduledCommand(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				ArrayList<CentroOperacionDTO> aux = new ArrayList<CentroOperacionDTO>(allSelectionModel.getSelectedSet());
				for(CentroOperacionDTO cow:allDataProvider.getList()){
					if(allSelectionModel.isSelected(cow)){
						allSelectionModel.setSelected(cow, false);
					}
				}
				leftPanel.showWidget(1);
				presenter.addToMonitor(aux);
			}
		});
		removeFromMonitorItem.setScheduledCommand(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				
			}
		});
	}

	@UiHandler("selectAllBox")
	void onSelectAllBoxChange(ValueChangeEvent<Boolean> event){
		for(CentroOperacionDTO c:allDataProvider.getList()){
			allSelectionModel.setSelected(c, event.getValue());
		}
	}
	
	@UiHandler("regionBox")
	void onRegionBoxChange(ChangeEvent event){
		presenter.onRegionChange(Integer.parseInt(regionBox.getValue(regionBox.getSelectedIndex())));
	}
	
	@Override
	public void setPresenter(CentroControlPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public ListDataProvider<CentroOperacionDTO> getAllDataProvider() {
		return allDataProvider;
	}
	
	@Override
	public ListDataProvider<CentroOperacionDTO> getMonitoringDataProvider() {
		return monitorDataProvider;
	}
	
	@Override
	public void setRegiones(ArrayList<SectorDTO> regiones) {
		regionBox.clear();
		regionBox.addItem("Todas","-1");
		for(SectorDTO s:regiones){
			regionBox.addItem(s.getSector(),s.getIdSector()+"");
		}
	}
	
	@Override
	public void setComunas(ArrayList<SectorDTO> comunas) {
		comunaBox.clear();
		comunaBox.addItem("Todas","-1");
		for(SectorDTO s:comunas){
			comunaBox.addItem(s.getSector(),s.getIdSector()+"");
		}
	}
	
	@Override
	public void setZonas(ArrayList<SectorDTO> zonas) {
		zonaBox.clear();
		zonaBox.addItem("Todas","-1");
		for(SectorDTO s:zonas){
			zonaBox.addItem(s.getSector(),s.getIdSector()+"");
		}
	}
	
	private void buildAllTable(){
		int i=0;
		checkColumn = new Column<CentroOperacionDTO, Boolean>(new CheckboxCell(true, false)) {
			@Override
			public Boolean getValue(CentroOperacionDTO object) {
				return allSelectionModel.isSelected(object);
			}
		};
		allTable.setColumnWidth(i++, 4, Unit.EM);
		
		centroColumn = new Column<CentroOperacionDTO, CentroOperacionDTO>(new CoCell()) {

			@Override
			public CentroOperacionDTO getValue(CentroOperacionDTO o) {
				return o;
			}
		};
		centroColumn.setSortable(false);
		allTable.setColumnWidth(i++, 30, Unit.PCT);
		
		enImprentaColumn = new Column<CentroOperacionDTO, Number>(new NumberCell()) {

			@Override
			public Number getValue(CentroOperacionDTO o) {
				return o.getEnImprenta();
			}
		};
		enImprentaColumn.setSortable(false);
		allTable.setColumnWidth(i++, 6, Unit.EM);
		
		enCentroColumn = new Column<CentroOperacionDTO, Number>(new NumberCell()) {

			@Override
			public Number getValue(CentroOperacionDTO o) {
				return o.getEnCentro();
			}
		};
		enCentroColumn.setSortable(false);
		allTable.setColumnWidth(i++, 6, Unit.EM);
		
		enEstablecimientoColumn = new Column<CentroOperacionDTO, Number>(new NumberCell()) {

			@Override
			public Number getValue(CentroOperacionDTO o) {
				return o.getEnEstablecimiento();
			}
		};
		enEstablecimientoColumn.setSortable(false);
		allTable.setColumnWidth(i++, 6, Unit.EM);
		
		enMinisterioColumn = new Column<CentroOperacionDTO, Number>(new NumberCell()) {

			@Override
			public Number getValue(CentroOperacionDTO o) {
				return o.getEnMinisterio();
			}
		};
		enMinisterioColumn.setSortable(false);
		allTable.setColumnWidth(i++, 6, Unit.EM);
		
		contingeciaImprentaColumn = new Column<CentroOperacionDTO, Number>(new NumberCell()) {

			@Override
			public Number getValue(CentroOperacionDTO o) {
				return o.getContingenciaEnImprenta();
			}
		};
		contingeciaImprentaColumn.setSortable(false);
		allTable.setColumnWidth(i++, 6, Unit.EM);
		
		contingeciaCentroColumn = new Column<CentroOperacionDTO, Number>(new NumberCell()) {

			@Override
			public Number getValue(CentroOperacionDTO o) {
				return o.getContingenciaEnCentro();
			}
		};
		contingeciaCentroColumn.setSortable(false);
		allTable.setColumnWidth(i++, 6, Unit.EM);
		
		contingeciaEstablecimientoColumn = new Column<CentroOperacionDTO, Number>(new NumberCell()) {

			@Override
			public Number getValue(CentroOperacionDTO o) {
				return o.getContingenciaEnEstablecimiento();
			}
		};
		contingeciaEstablecimientoColumn.setSortable(false);
		allTable.setColumnWidth(i++, 6, Unit.EM);
		
		contingeciaMinisterioColumn = new Column<CentroOperacionDTO, Number>(new NumberCell()) {

			@Override
			public Number getValue(CentroOperacionDTO o) {
				return o.getContingenciaEnMinisterio();
			}
		};
		contingeciaMinisterioColumn.setSortable(false);
		allTable.setColumnWidth(i++, 6, Unit.EM);
		
		trackingColumn = new Column<CentroOperacionDTO, Hyperlink>(new HyperlinkCell()) {

			@Override
			public Hyperlink getValue(CentroOperacionDTO o) {
				return new Hyperlink("Tracking", presenter.getCentroOperacionToken(o.getId()));
			}
		};
		trackingColumn.setSortable(false);
		allTable.setColumnWidth(i++, 70, Unit.PCT);
	}
}
