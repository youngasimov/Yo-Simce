package com.dreamer8.yosimce.client.general.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.dreamer8.yosimce.shared.dto.CentroOperacionDTO;
import com.dreamer8.yosimce.shared.dto.ControlCentroOperacionDTO;
import com.dreamer8.yosimce.shared.dto.SectorDTO;
import com.dreamer8.yosimce.client.ui.HyperlinkCell;
import com.dreamer8.yosimce.client.ui.ImageButton;
import com.dreamer8.yosimce.client.ui.OverMenuBar;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.CompositeCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.HasCell;
import com.google.gwt.cell.client.IconCellDecorator;
import com.google.gwt.cell.client.ImageCell;
import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.builder.shared.TableCellBuilder;
import com.google.gwt.dom.builder.shared.TableRowBuilder;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.maps.client.LoadApi;
import com.google.gwt.maps.client.LoadApi.LoadLibrary;
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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;

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
	    
	    String full();
	    
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
			th.text("Material Contingencia").endTH();
			
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
	//@UiField MenuItem bingoItem;
	@UiField StackLayoutPanel leftPanel;
	@UiField TabLayoutPanel tabs;
	@UiField CheckBox selectAllBox;
	@UiField ListBox regionBox;
	@UiField ListBox zonaBox;
	@UiField ListBox comunaBox;
	@UiField ImageButton selectFiltroButton;
	@UiField(provided=true) DataGrid<CentroOperacionDTO> allTable;
	@UiField(provided=true) CellList<CentroOperacionDTO> monitorList;
	@UiField(provided=true) SimplePager allPager;
	@UiField SimplePanel allMaterialPanel;
	@UiField SimplePanel materialPanel;
	@UiField SimplePanel contingenciaPanel;
	@UiField SimplePanel mapaPanel;
	@UiField HTMLPanel graphPanel;
	@UiField RadioButton imprentaRadioButton;
	@UiField RadioButton centroRadioButton;
	@UiField RadioButton establecimientoRadioButton;
	@UiField RadioButton ministerioRadioButton;
	@UiField ListBox controlZonesBox;
	@UiField SimplePanel controlResumenPanel;
	@UiField(provided=true) DataGrid<ControlCentroOperacionDTO> controlDataGrid;
	
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
	
	//control columns
	private Column<ControlCentroOperacionDTO, String> controlZonaColumn;
	private Column<ControlCentroOperacionDTO, String> controlCoColumn;
	private Column<ControlCentroOperacionDTO, String> controlJefeZnColumn;
	private Column<ControlCentroOperacionDTO, String> controlJefeCoColumn;
	private Column<ControlCentroOperacionDTO, ControlCentroOperacionDTO> controlEstadoColumn;
	
	
	private ListDataProvider<CentroOperacionDTO> allDataProvider;
	private ListDataProvider<CentroOperacionDTO> monitorDataProvider;
	
	private MultiSelectionModel<CentroOperacionDTO> allSelectionModel;
	
	private CentroControlPresenter presenter;
	private BingoPanel bingo;
	private PopupPanel bingoPopup;
	
	private boolean autoRecargaActivated;
	
	private boolean mapApi;
	private boolean chartApi;
	
	private SimceMapWidget map;
	
	private MaterialTimeLineChart allLineChart;
	private MaterialTimeLineChart materialLineChart;
	private MaterialTimeLineChart contingenciaLineChart;
	
	private FlexTable infoWindowTable;
	private Label iwCentro; 
	private Label iwMI;
	private Label iwMC;
	private Label iwME;
	private Label iwMM;
	private Label iwCI;
	private Label iwCC;
	private Label iwCE;
	private Label iwCM;
	private Hyperlink iwh;
	
	private ListDataProvider<ControlCentroOperacionDTO> controlProvider;
	
	
	public CentroControlViewD() {
		controlDataGrid = new DataGrid<ControlCentroOperacionDTO>();
		mapApi = false;
		chartApi = false;
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
		map = new SimceMapWidget();
		map.setMapHandler(new SimceMapWidget.MapHandler() {
			
			@Override
			public void onMapClicked() {
				map.clearInfoWindow();
			}
		});
		map.setMarkerHandler(new SimceMapWidget.MarkerHandler() {

			@Override
			public void onMarkerSelected(int id) {
				presenter.onCentroSelected(id);
			}
			
		});
		allLineChart = new MaterialTimeLineChart();
		materialLineChart = new MaterialTimeLineChart();
		contingenciaLineChart = new MaterialTimeLineChart();
		
		infoWindowTable = new FlexTable();
		iwCentro = new Label();
		iwMI = new Label();
		iwMC = new Label();
		iwME = new Label();
		iwMM = new Label();
		iwCI = new Label();
		iwCC = new Label();
		iwCE = new Label();
		iwCM = new Label();
		iwh = new Hyperlink();
		
		infoWindowTable.setWidget(0, 0, new HTML("Centro:"));
		infoWindowTable.setWidget(0, 1, iwCentro);
		infoWindowTable.setWidget(1, 0, new HTML("En Imprenta:"));
		infoWindowTable.setWidget(1, 1, iwMI);
		infoWindowTable.setWidget(2, 0, new HTML("En Centro:"));
		infoWindowTable.setWidget(2, 1, iwMC);
		infoWindowTable.setWidget(3, 0, new HTML("En Establecimiento:"));
		infoWindowTable.setWidget(3, 1, iwME);
		infoWindowTable.setWidget(4, 0, new HTML("En Ministerio:"));
		infoWindowTable.setWidget(4, 1, iwMM);
		infoWindowTable.setWidget(5, 0, new HTML("Contingecia en Imprenta:"));
		infoWindowTable.setWidget(5, 1, iwCI);
		infoWindowTable.setWidget(6, 0, new HTML("Contingecia en Centro:"));
		infoWindowTable.setWidget(6, 1, iwCC);
		infoWindowTable.setWidget(7, 0, new HTML("Contingecia en Establecimiento:"));
		infoWindowTable.setWidget(7, 1, iwCE);
		infoWindowTable.setWidget(8, 0, new HTML("Contingecia en Ministerio:"));
		infoWindowTable.setWidget(8, 1, iwCM);
		infoWindowTable.setWidget(9, 0, iwh);
		
		initWidget(uiBinder.createAndBindUi(this));
		bingo = new BingoPanel();
		bingoPopup = new PopupPanel(true,true);
		bingoPopup.setGlassEnabled(true);
		bingoPopup.setWidget(bingo);
		mapaPanel.setWidget(map);
		allMaterialPanel.setWidget(allLineChart);
		materialPanel.setWidget(materialLineChart);
		contingenciaPanel.setWidget(contingenciaLineChart);
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
		
		buildControlTable();
		
		sendToMonitorItem.setVisible(false);
		autoRecargaActivated = false;
		menu.setOverItem(menuItem);
		menu.insertSeparator(2);
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
		/*
		bingoItem.setScheduledCommand(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				int w = Window.getClientWidth() - 60;
				int h = Window.getClientHeight() - 120;
				bingo.setWidth(w+"px");
				bingo.setHeight(h+"px");
				bingoPopup.center();
			}
		});
		*/
		Window.addResizeHandler(new ResizeHandler() {
			
			@Override
			public void onResize(ResizeEvent event) {
				allLineChart.redraw();
				materialLineChart.redraw();
				contingenciaLineChart.redraw();
				if(event.getWidth()<1210){
					graphPanel.addStyleName(style.full());
				}else{
					graphPanel.removeStyleName(style.full());
				}
			}
		});
		
		if(Window.getClientWidth()<1210){
			graphPanel.addStyleName(style.full());
		}
		
		controlProvider = new ListDataProvider<ControlCentroOperacionDTO>();
		controlProvider.addDataDisplay(controlDataGrid);
		
		//buildBingo();
	}

	@UiHandler("selectAllBox")
	void onSelectAllBoxChange(ValueChangeEvent<Boolean> event){
		for(CentroOperacionDTO c:allDataProvider.getList()){
			allSelectionModel.setSelected(c, event.getValue());
		}
	}
	
	@UiHandler("imprentaRadioButton")
	void onImprentaEventSelected(ValueChangeEvent<Boolean> event){
		if(event.getValue()){
			presenter.onEventChange(EVENT_IMPRENTA);
		}
	}
	
	@UiHandler("centroRadioButton")
	void onCentroEventSelected(ValueChangeEvent<Boolean> event){
		if(event.getValue()){
			presenter.onEventChange(EVENT_CENTRO);
		}
	}
	
	@UiHandler("establecimientoRadioButton")
	void onEstablecimientoEventSelected(ValueChangeEvent<Boolean> event){
		if(event.getValue()){
			presenter.onEventChange(EVENT_ESTABLECIMIENTO);
		}
	}
	
	@UiHandler("ministerioRadioButton")
	void onMinisterioEventSelected(ValueChangeEvent<Boolean> event){
		if(event.getValue()){
			presenter.onEventChange(EVENT_MINISTERIO);
		}
	}
	
	@UiHandler("selectFiltroButton")
	void onAplicarFiltroButtonClick(ClickEvent event){
		presenter.selectUsingFiltro();
	}
	
	@UiHandler("regionBox")
	void onRegionBoxChange(ChangeEvent event){
		presenter.onRegionChange(Integer.parseInt(regionBox.getValue(regionBox.getSelectedIndex())));
	}
	
	@UiHandler("tabs")
	void onTabSelected(SelectionEvent<Integer> event){
		
	}
	
	@Override
	public void setTab(int tab) {
		tabs.selectTab(tab);
	}
	
	@Override
	public void setEvento(int event) {
		if(event == EVENT_CENTRO){
			centroRadioButton.setValue(true);
		}else if(event == EVENT_ESTABLECIMIENTO){
			establecimientoRadioButton.setValue(true);
		}else if(event == EVENT_IMPRENTA){
			imprentaRadioButton.setValue(true);
		}else if(event == EVENT_MINISTERIO){
			ministerioRadioButton.setValue(true);
		}else{
			imprentaRadioButton.setValue(true);
		}
	}
	
	@Override
	public void clearMarkers() {
		map.clearMarkers();
	}
	
	@Override
	public void showCentroOperacionInfo(CentroOperacionDTO centro) {
		iwCentro.setText(centro.getNombre());
		iwMI.setText(centro.getEnImprenta()+"");
		iwMC.setText(centro.getEnCentro()+"");
		iwME.setText(centro.getEnEstablecimiento()+"");
		iwMM.setText(centro.getEnMinisterio()+"");
		iwCI.setText(centro.getContingenciaEnImprenta()+"");
		iwCC.setText(centro.getContingenciaEnCentro()+"");
		iwCE.setText(centro.getContingenciaEnEstablecimiento()+"");
		iwCM.setText(centro.getContingenciaEnMinisterio()+"");
		iwh.setHTML("ir a tracking");
		iwh.setTargetHistoryToken(presenter.getCentroOperacionToken(centro.getId()));
		map.showInfoWindow(infoWindowTable, centro.getLatitud(), centro.getLongitud());
	}
	
	@Override
	public void setPresenter(CentroControlPresenter presenter) {
		this.presenter = presenter;
	}
	
	@Override
	public boolean isMapApiReady() {
		return mapApi;
	}
	
	@Override
	public boolean isChartApiReady() {
		return chartApi;
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
	
	@Override
	public void setMonitoreados(int monitoreados) {
		leftPanel.setHeaderText(1, "Monitoreados ("+monitoreados+")");
	}
	
	@Override
	public int getSelectedRegion() {
		return Integer.parseInt(regionBox.getValue(regionBox.getSelectedIndex()));
	}
	
	@Override
	public int getSelectedZona() {
		return Integer.parseInt(zonaBox.getValue(zonaBox.getSelectedIndex()));
	}
	
	@Override
	public int getSelectedComuna() {
		return Integer.parseInt(comunaBox.getValue(comunaBox.getSelectedIndex()));
	}
	
	@Override
	public void setSelectedCos(ArrayList<Integer> selected) {
		for(CentroOperacionDTO co:allDataProvider.getList()){
			allSelectionModel.setSelected(co, selected.contains(co.getId()));
		}
	}
	
	@Override
	public void updateGraphs(ArrayList<GraphItem> data) {
		if(data!=null && !data.isEmpty()){
			DataTable dt = DataTable.create();
			DataTable dt2 = DataTable.create();
			DataTable dt3 = DataTable.create();
			dt.addColumn(ColumnType.DATETIME, "Tiempo");
			dt.addColumn(ColumnType.NUMBER, "Imprenta");
			dt.addColumn(ColumnType.NUMBER, "Centro");
			dt.addColumn(ColumnType.NUMBER, "Establecimiento");
			dt.addColumn(ColumnType.NUMBER, "Ministerio");
			dt.addRows(data.size());
			
			dt2.addColumn(ColumnType.DATETIME, "Tiempo");
			dt2.addColumn(ColumnType.NUMBER, "Imprenta");
			dt2.addColumn(ColumnType.NUMBER, "Centro");
			dt2.addColumn(ColumnType.NUMBER, "Establecimiento");
			dt2.addColumn(ColumnType.NUMBER, "Ministerio");
			dt2.addRows(data.size());
			
			dt3.addColumn(ColumnType.DATETIME, "Tiempo");
			dt3.addColumn(ColumnType.NUMBER, "Imprenta");
			dt3.addColumn(ColumnType.NUMBER, "Centro");
			dt3.addColumn(ColumnType.NUMBER, "Establecimiento");
			dt3.addColumn(ColumnType.NUMBER, "Ministerio");
			dt3.addRows(data.size());
			for (int i = 0; i < data.size(); i++) {
				float total =  data.get(i).ci+data.get(i).mi+
						data.get(i).cc+data.get(i).mc+
						data.get(i).ce+data.get(i).me+
						data.get(i).cm+data.get(i).mm;
				dt.setValue(i, 0, data.get(i).d);
				dt.setValue(i, 1, 100*(((float)data.get(i).ci+(float)data.get(i).mi)/total));
				dt.setValue(i, 2, 100*(((float)data.get(i).cc+(float)data.get(i).mc)/total));
				dt.setValue(i, 3, 100*(((float)data.get(i).ce+(float)data.get(i).me)/total));
				dt.setValue(i, 4, 100*(((float)data.get(i).cm+(float)data.get(i).mm)/total));
				
				total =  data.get(i).mi+data.get(i).mc+data.get(i).me+data.get(i).mm;
				dt2.setValue(i, 0, data.get(i).d);
				dt2.setValue(i, 1, 100*((float)data.get(i).mi/total));
				dt2.setValue(i, 2, 100*((float)data.get(i).mc/total));
				dt2.setValue(i, 3, 100*((float)data.get(i).me/total));
				dt2.setValue(i, 4, 100*((float)data.get(i).mm/total));
				
				total =  data.get(i).ci+data.get(i).cc+data.get(i).ce+data.get(i).cm;
				dt3.setValue(i, 0, data.get(i).d);
				dt3.setValue(i, 1, 100*((float)data.get(i).ci/total));
				dt3.setValue(i, 2, 100*((float)data.get(i).cc/total));
				dt3.setValue(i, 3, 100*((float)data.get(i).ce/total));
				dt3.setValue(i, 4, 100*((float)data.get(i).cm/total));
			}
			allLineChart.draw(dt);
			materialLineChart.draw(dt2);
			contingenciaLineChart.draw(dt3);
		}
	}
	
	@Override
	public void updateMarkers(int evento, ArrayList<CentroOperacionDTO> centros) {
		if(mapApi){
			map.updateCentros(evento, centros);
		}
	}
	
	@Override
	public void initApis() {
		if(!mapApi){
			boolean sensor = true;
		    // load all the libs for use in the maps
		    ArrayList<LoadLibrary> loadLibraries = new ArrayList<LoadApi.LoadLibrary>();
		    loadLibraries.add(LoadLibrary.DRAWING);
		    loadLibraries.add(LoadLibrary.PLACES);
		    Runnable onLoad = new Runnable() {
		    	@Override
		    	public void run() {
		    		map.createMapIfIsNull();
		    		mapApi = true;
		    		initChartApi();
		    	}
		    };
		    LoadApi.go(onLoad, loadLibraries,sensor);
		}else{
			map.createMapIfIsNull();
    		mapApi = true;
    		initChartApi();
		}
	}
	
	private void initChartApi(){
		if(!chartApi){
    		ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
    		chartLoader.loadApi(new Runnable() {
    			@Override
    			public void run() {
    				allLineChart.createChartIfIsNull("Estado materiales (Cajas-curso-dia, Complementario y Contingencia)");
    				materialLineChart.createChartIfIsNull("Estado materiales (Cajas-curso-dia y Complementario)");
    				contingenciaLineChart.createChartIfIsNull("Estado materiales (Contingecia)");
    				chartApi = true;
    				presenter.onApisReady();
    			}
    		});
		}else{
			allLineChart.createChartIfIsNull("Estado materiales (Cajas-curso-dia, Complementario y Contingencia)");
			materialLineChart.createChartIfIsNull("Estado materiales (Cajas-curso-dia y Complementario)");
			contingenciaLineChart.createChartIfIsNull("Estado materiales (Contingecia)");
			chartApi = true;
			presenter.onApisReady();
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
		allTable.setColumnWidth(i++, 14, Unit.EM);
		
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
		allTable.setColumnWidth(i++, 15, Unit.EM);
	}
	
	
	private void buildControlTable(){
		controlZonaColumn = new Column<ControlCentroOperacionDTO, String>(new TextCell()) {

			@Override
			public String getValue(ControlCentroOperacionDTO object) {
				return null;
			}
		};
		controlZonaColumn.setSortable(true);
		controlDataGrid.setColumnWidth(controlZonaColumn, "20%");
		
		controlCoColumn = new Column<ControlCentroOperacionDTO, String>(new TextCell()) {

			@Override
			public String getValue(ControlCentroOperacionDTO object) {
				return "";
			}
		};
		controlCoColumn.setSortable(true);
		controlDataGrid.setColumnWidth(controlCoColumn, "4em");
		
		controlJefeZnColumn = new Column<ControlCentroOperacionDTO, String>(new TextCell()) {

			@Override
			public String getValue(ControlCentroOperacionDTO object) {
				// TODO Auto-generated method stub
				return null;
			}
		};
		controlJefeZnColumn.setSortable(false);
		controlDataGrid.setColumnWidth(controlJefeZnColumn, "40%");
		
		controlJefeCoColumn = new Column<ControlCentroOperacionDTO, String>(new TextCell()) {

			@Override
			public String getValue(ControlCentroOperacionDTO object) {
				return null;
			}
		};
		controlJefeCoColumn.setSortable(false);
		controlDataGrid.setColumnWidth(controlJefeCoColumn, "40%");
		
		final List<HasCell> cells = new ArrayList<HasCell>();
		HasCell imageCell = new HasCell() {

			@Override
			public Cell getCell() {
				return new ImageCell();
			}

			@Override
			public FieldUpdater getFieldUpdater() {
				return null;
			}

			@Override
			public Object getValue(Object o) {
				if(o instanceof ControlCentroOperacionDTO){
					ControlCentroOperacionDTO cco = (ControlCentroOperacionDTO)o;
					return "";
				}
				return "";
			}
		};
		
		HasCell actionCell = new HasCell() {

			@Override
			public Cell getCell() {
				return new ActionCell<ControlCentroOperacionDTO>("Estado",new ActionCell.Delegate<ControlCentroOperacionDTO>() {

					@Override
					public void execute(ControlCentroOperacionDTO object) {
						
					}
					
				});
			}

			@Override
			public FieldUpdater getFieldUpdater() {
				return null;
			}

			@Override
			public Object getValue(Object o) {
				return o;
			}
		};
		cells.add(imageCell);
		cells.add(actionCell);
		
		
		
		controlEstadoColumn = new Column<ControlCentroOperacionDTO, ControlCentroOperacionDTO>(new CompositeCell(cells)) {

			@Override
			public ControlCentroOperacionDTO getValue(ControlCentroOperacionDTO object) {
				return object;
			}
			
		};
		controlEstadoColumn.setSortable(true);
		controlDataGrid.setColumnWidth(controlEstadoColumn, "10em");
	}
	
	
	@Override
	public void setBingoRM(HashMap<String, ArrayList<String>> bingo) {
		this.bingo.bingormTable.setWidget(0, 0, new HTML("Región Metropolitana"));
		int max = 0;
		int i=1;
		int j=1;
		for(Entry<String,ArrayList<String>> entry:bingo.entrySet()){
			this.bingo.bingormTable.setWidget(i, 0, new HTML(entry.getKey()));
			if(max<entry.getValue().size()){
				max = entry.getValue().size();
			}
			for(String co:entry.getValue()){
				this.bingo.bingormTable.setWidget(i, j, new HTML(co));
				j++;
			}
			j=1;
			i++;
		}
		this.bingo.bingormTable.getFlexCellFormatter().setColSpan(0, 0, max);
		
	}
	
	@Override
	public void setBingoR8(HashMap<String, ArrayList<String>> bingo) {
		this.bingo.bingor8Table.setWidget(0, 0, new HTML("VIII Région"));
		int max = 0;
		int i=1;
		int j=1;
		for(Entry<String,ArrayList<String>> entry:bingo.entrySet()){
			this.bingo.bingor8Table.setWidget(i, 0, new HTML(entry.getKey()));
			if(max<entry.getValue().size()){
				max = entry.getValue().size();
			}
			for(String co:entry.getValue()){
				this.bingo.bingor8Table.setWidget(i, j, new HTML(co));
				j++;
			}
			j=1;
			i++;
		}
		this.bingo.bingor8Table.getFlexCellFormatter().setColSpan(0, 0, max);
	}

	@Override
	public ListDataProvider<ControlCentroOperacionDTO> getControlDataProvider() {
		return controlProvider;
	}
	
	
}
