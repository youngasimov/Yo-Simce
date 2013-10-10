package com.dreamer8.yosimce.client.actividad.ui;

import java.util.ArrayList;

import com.dreamer8.yosimce.client.ui.OverMenuBar;
import com.dreamer8.yosimce.client.ui.ViewUtils;
import com.dreamer8.yosimce.shared.dto.EvaluacionSupervisorDTO;
import com.dreamer8.yosimce.shared.dto.EvaluacionSuplenteDTO;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.builder.shared.TableCellBuilder;
import com.google.gwt.dom.builder.shared.TableRowBuilder;
import com.google.gwt.dom.client.Style.OutlineStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.AbstractCellTableBuilder;
import com.google.gwt.user.cellview.client.AbstractHeaderOrFooterBuilder;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortList;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.HasKeyboardPagingPolicy.KeyboardPagingPolicy;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.cellview.client.TextHeader;
import com.google.gwt.user.cellview.client.AbstractCellTable.Style;
import com.google.gwt.user.cellview.client.ColumnSortList.ColumnSortInfo;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionModel;

public class AprobarSupervisoresViewD extends Composite implements
		AprobarSupervisoresView {

	private static AprobarSupervisoresViewDUiBinder uiBinder = GWT
			.create(AprobarSupervisoresViewDUiBinder.class);

	interface AprobarSupervisoresViewDUiBinder extends
			UiBinder<Widget, AprobarSupervisoresViewD> {
	}
	
	interface Resources extends ClientBundle {
	    @Source("aprobarsupervisores.css")
	    AsStyle style();
	}

	interface AsStyle extends CssResource {
		String evenSupervisorRowStyle();
		String supervisorRowStyle();
		String evenSupervisorRowStyle2();
		String supervisorRowStyle2();
		String selected();
		String groupHeaderCell();
	}

	private class CustomHeaderBuilder extends
			AbstractHeaderOrFooterBuilder<EvaluacionSupervisorDTO> {

		private Header<String> rutHeader = new TextHeader("RUT");
		private Header<String> nombreHeader = new TextHeader("Nombre");
		private Header<String> rbdHeader = new TextHeader("RBD");
		private Header<String> establecimientoHeader = new TextHeader(
				"Establecimiento");
		private Header<String> cursoHeader = new TextHeader("Curso");
		private Header<String> puHeader = new TextHeader("Puntualidad");
		private Header<String> ppHeader = new TextHeader(
				"Presentación personal");
		private Header<String> geHeader = new TextHeader("Asistió");

		public CustomHeaderBuilder() {
			super(dataGrid, false);
			setSortIconStartOfLine(false);
		}

		@Override
		protected boolean buildHeaderOrFooterImpl() {
			TableRowBuilder tr = startRow();

			/*
			 * Name group header. Associated with the last name column, so
			 * clicking on the group header sorts by last name.
			 */
			TableCellBuilder th = tr.startTH().colSpan(2)
					.className(resources.style().groupHeaderCell());
			th.style().trustedProperty("border-right", "10px solid white")
					.endStyle();
			th.text("Supervisor").endTH();

			th = tr.startTH().colSpan(3).className(resources.style().groupHeaderCell());
			th.style().trustedProperty("border-right", "10px solid white")
					.endStyle();
			th.text("Establecimiento").endTH();

			th = tr.startTH().colSpan(3).className(resources.style().groupHeaderCell());
			th.style().trustedProperty("border-right", "10px solid white")
					.endStyle();
			th.text("Evaluación").endTH();

			// Get information about the sorted column.
			ColumnSortList sortList = dataGrid.getColumnSortList();
			ColumnSortInfo sortedInfo = (sortList.size() == 0) ? null
					: sortList.get(0);
			Column<?, ?> sortedColumn = (sortedInfo == null) ? null
					: sortedInfo.getColumn();
			boolean isSortAscending = (sortedInfo == null) ? false : sortedInfo
					.isAscending();

			// Add column headers.
			tr = startRow();
			buildHeader(tr, rutHeader, rutColumn, sortedColumn,
					isSortAscending, true, false);
			buildHeader(tr, nombreHeader, nombreColumn, sortedColumn,
					isSortAscending, false, false);
			buildHeader(tr, rbdHeader, rbdColumn, sortedColumn,
					isSortAscending, false, false);
			buildHeader(tr, establecimientoHeader, establecimientoColumn,
					sortedColumn, isSortAscending, false, false);
			buildHeader(tr, cursoHeader, cursoColumn, sortedColumn,
					isSortAscending, false, false);
			buildHeader(tr, puHeader, puntualidadColumn, sortedColumn,
					isSortAscending, false, false);
			buildHeader(tr, ppHeader, presentacionColumn, sortedColumn,
					isSortAscending, false, false);
			buildHeader(tr, geHeader, generalColumn, sortedColumn,
					isSortAscending, false, true);
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
				Column<EvaluacionSupervisorDTO, ?> column,
				Column<?, ?> sortedColumn, boolean isSortAscending,
				boolean isFirst, boolean isLast) {
			// Choose the classes to include with the element.
			Style style = dataGrid.getResources().style();
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

	private class CustomTableBuilder extends
			AbstractCellTableBuilder<EvaluacionSupervisorDTO> {

		private final String rowStyle;
		private final String rowStyleOdd;
		private final String selectedRowStyle;
		private final String cell;
		private final String cellStyle;
		private final String cellStyle2;
		private final String cellStyleOdd;
		private final String cellStyleOdd2;
		private final String selectedCellStyle;
		
		
		private int current;
		private boolean even;

		public CustomTableBuilder() {
			super(dataGrid);
			even = false;
			current = -1;
			// Cache styles for faster access.
			Style s = dataGrid.getResources().style();
			rowStyle = s.evenRow();
			rowStyleOdd = s.oddRow();
			selectedRowStyle = " " + s.selectedRow();
			cell =s.cell() + " " +s.evenRowCell();
			cellStyle = s.cell() + " " +resources.style().evenSupervisorRowStyle();
			cellStyle2 = s.cell() + " "+resources.style().evenSupervisorRowStyle2();
			cellStyleOdd = s.cell() + " " + resources.style().supervisorRowStyle();
			cellStyleOdd2 = s.cell() + " " + resources.style().supervisorRowStyle2();
			selectedCellStyle =s.cell()+ " " + resources.style().selected();			
		}

		@Override
		public void buildRowImpl(EvaluacionSupervisorDTO rowValue,
				int absRowIndex) {
			
			if(rowValue.getSupervisor().getId()!=current){
				current = rowValue.getSupervisor().getId();
				even = !even;
			}
			buildSupervisorRow(rowValue, absRowIndex,false);
			
		}

		private void buildSupervisorRow(EvaluacionSupervisorDTO rowValue,int absRowIndex, boolean isFirst) {
			SelectionModel<? super EvaluacionSupervisorDTO> selectionModel = dataGrid
					.getSelectionModel();
			boolean isSelected = (selectionModel == null || rowValue == null) ? false
					: selectionModel.isSelected(rowValue);
			boolean isEven = absRowIndex % 2 == 0;
			
			String evenCell ="";
			String oddCell ="";
			if(even){
				evenCell = cellStyle;
				oddCell = cellStyleOdd;
			}else{
				evenCell = cellStyle2;
				oddCell = cellStyleOdd2;
			}
			
			
			StringBuilder trClasses = new StringBuilder();
			String cellStyles = cell;
			if(isEven){
				trClasses.append(rowStyle+" "+evenCell);
				//cellStyles = eventCell;
			}else{
				trClasses.append(rowStyleOdd+" "+oddCell);
				//cellStyles = oddCell;
			}
			
			if (isSelected) {
				trClasses.append(selectedRowStyle);
				cellStyles = selectedCellStyle;
			}
			
			TableRowBuilder row = startRow();
			row.className(trClasses.toString());

			int i = -1;

			TableCellBuilder td = null;			
			// show Rut Column
			td = row.startTD();
			td.className(cellStyles);
			td.style().outlineStyle(OutlineStyle.NONE).endStyle();
			renderCell(td, createContext(++i), rutColumn, rowValue);
			td.endTD();

			// show Nombre Column
			td = row.startTD();
			td.className(cellStyles);
			td.style().outlineStyle(OutlineStyle.NONE).endStyle();
			renderCell(td, createContext(++i), nombreColumn, rowValue);
			td.endTD();

			// show RBD Column
			td = row.startTD();
			td.className(cellStyles);
			td.style().outlineStyle(OutlineStyle.NONE).endStyle();
			renderCell(td, createContext(++i), rbdColumn, rowValue);
			td.endTD();

			// show Establecimiento Column
			td = row.startTD();
			td.className(cellStyles);
			td.style().outlineStyle(OutlineStyle.NONE).endStyle();
			renderCell(td, createContext(++i), establecimientoColumn,
					rowValue);
			td.endTD();
			// show curso Column
			td = row.startTD();
			td.className(cellStyles);
			td.style().outlineStyle(OutlineStyle.NONE).endStyle();
			renderCell(td, createContext(++i), cursoColumn, rowValue);
			td.endTD();

			// show puntualidad Column
			td = row.startTD();
			td.className(cellStyles);
			td.style().outlineStyle(OutlineStyle.NONE).endStyle();
			renderCell(td, createContext(++i), puntualidadColumn,
					rowValue);
			td.endTD();

			// show puntualidad Column
			td = row.startTD();
			td.className(cellStyles);
			td.style().outlineStyle(OutlineStyle.NONE).endStyle();
			renderCell(td, createContext(++i), presentacionColumn,
					rowValue);
			td.endTD();

			// show puntualidad Column
			td = row.startTD();
			td.className(cellStyles);
			td.style().outlineStyle(OutlineStyle.NONE).endStyle();
			renderCell(td, createContext(++i), generalColumn, rowValue);
			td.endTD();
			
			row.endTR();
		}
	}

	@UiField
	OverMenuBar menu;
	@UiField
	MenuItem menuItem;
	@UiField
	TabLayoutPanel tabs;
	@UiField
	SuggestBox supervisorSearchBox;
	@UiField
	SuggestBox suplenteSearchBox;
	@UiField
	Button clean;
	@UiField
	Button suplenteClean;
	@UiField
	Button search;
	@UiField
	Button suplenteSearch;
	@UiField(provided = true)
	DataGrid<EvaluacionSupervisorDTO> dataGrid;
	@UiField(provided = true)
	SimplePager pager;
	@UiField(provided = true)
	DataGrid<EvaluacionSuplenteDTO> suplentesdataGrid;
	@UiField(provided = true)
	SimplePager suplentesPager;
	
	private Resources resources;

	private Column<EvaluacionSupervisorDTO, String> rutColumn;
	private Column<EvaluacionSupervisorDTO, String> nombreColumn;
	private Column<EvaluacionSupervisorDTO, String> rbdColumn;
	private Column<EvaluacionSupervisorDTO, String> establecimientoColumn;
	private Column<EvaluacionSupervisorDTO, String> cursoColumn;
	private Column<EvaluacionSupervisorDTO, Boolean> puntualidadColumn;
	private Column<EvaluacionSupervisorDTO, Boolean> presentacionColumn;
	private Column<EvaluacionSupervisorDTO, Boolean> generalColumn;
	private ListDataProvider<EvaluacionSupervisorDTO> dataProvider;
	
	private Column<EvaluacionSuplenteDTO, String> srutColumn;
	private Column<EvaluacionSuplenteDTO, String> snombreColumn;
	private Column<EvaluacionSuplenteDTO, String> coColumn;
	private Column<EvaluacionSuplenteDTO, Boolean> sgeneralColumn;
	private ListDataProvider<EvaluacionSuplenteDTO> sdataProvider;

	private AprobarSupervisoresPresenter presenter;

	public AprobarSupervisoresViewD() {
		resources = GWT.create(Resources.class);
	    resources.style().ensureInjected();
		dataGrid = new DataGrid<EvaluacionSupervisorDTO>(
				EvaluacionSupervisorDTO.KEY_PROVIDER);
		
		suplentesdataGrid = new DataGrid<EvaluacionSuplenteDTO>(
				EvaluacionSuplenteDTO.KEY_PROVIDER);
		
		dataGrid.setPageSize(100);
		suplentesdataGrid.setPageSize(100);
		
		//selectionModel = new SingleSelectionModel<EvaluacionSupervisorDTO>(EvaluacionSupervisorDTO.KEY_PROVIDER);
		//dataGrid.setSelectionModel(selectionModel);
		
		dataProvider = new ListDataProvider<EvaluacionSupervisorDTO>(
				EvaluacionSupervisorDTO.KEY_PROVIDER);
		dataGrid.setAutoHeaderRefreshDisabled(true);
		dataGrid.setKeyboardPagingPolicy(KeyboardPagingPolicy.CHANGE_PAGE);
		dataGrid.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.BOUND_TO_SELECTION);
		
		sdataProvider = new ListDataProvider<EvaluacionSuplenteDTO>(
				EvaluacionSuplenteDTO.KEY_PROVIDER);
		//suplentesdataGrid.setAutoHeaderRefreshDisabled(true);
		suplentesdataGrid.setKeyboardPagingPolicy(KeyboardPagingPolicy.CHANGE_PAGE);
		suplentesdataGrid.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.BOUND_TO_SELECTION);
		
		SimplePager.Resources pagerResources = GWT
				.create(SimplePager.Resources.class);
		pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0,
				true);
		suplentesPager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0,
				true);
		pager.setDisplay(dataGrid);
		suplentesPager.setDisplay(suplentesdataGrid);
		buildTable();
		
		dataGrid.addColumn(rutColumn);
		dataGrid.addColumn(nombreColumn);
		dataGrid.addColumn(rbdColumn);
		dataGrid.addColumn(establecimientoColumn);
		dataGrid.addColumn(cursoColumn);
		dataGrid.addColumn(puntualidadColumn);
		dataGrid.addColumn(presentacionColumn);
		dataGrid.addColumn(generalColumn);
		
		buildSuplenteTable();
		
		suplentesdataGrid.addColumn(srutColumn,"Rut");
		suplentesdataGrid.addColumn(snombreColumn,"Nombre");
		suplentesdataGrid.addColumn(coColumn,"C. O.");
		suplentesdataGrid.addColumn(sgeneralColumn,"Asistió");
		
		
		dataGrid.setTableBuilder(new CustomTableBuilder());
		dataGrid.setHeaderBuilder(new CustomHeaderBuilder());
		
		dataProvider.addDataDisplay(dataGrid);
		sdataProvider.addDataDisplay(suplentesdataGrid);
		initWidget(uiBinder.createAndBindUi(this));
		menu.setOverItem(menuItem);
		menu.setOverCommand(new Scheduler.ScheduledCommand() {

			@Override
			public void execute() {
				presenter.toggleMenu();
			}
		});
		
		
		
	}
	@UiHandler("tabs")
	void tabSelected(SelectionEvent<Integer> event){
		presenter.onTabSelected(tabs.getSelectedIndex());
	}
	
	@UiHandler("search")
	void onSearchClick(ClickEvent event){
		presenter.filter(supervisorSearchBox.getValue());
	}
	
	@UiHandler("suplenteSearch")
	void onSuplentesSearchClick(ClickEvent event){
		presenter.suplentefilter(suplenteSearchBox.getValue());
	}
	
	@UiHandler("suplenteClean")
	void onSuplenteCleanFilterClick(ClickEvent event){
		suplenteSearchBox.setValue("");
		presenter.suplentefilter(null);
	}
	
	@UiHandler("clean")
	void onCleanFilterClick(ClickEvent event){
		supervisorSearchBox.setValue("");
		presenter.filter(null);
	}

	@Override
	public void setPresenter(AprobarSupervisoresPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void setSupervisores(ArrayList<EvaluacionSupervisorDTO> supervisores) {
		dataProvider.setList(supervisores);
	}
	

	@Override
	public void setSuplentes(ArrayList<EvaluacionSuplenteDTO> suplentes) {
		sdataProvider.setList(suplentes);
	}
	
	@Override
	public void setSuggestions(ArrayList<String> suggestions) {
		MultiWordSuggestOracle mwso = (MultiWordSuggestOracle)supervisorSearchBox.getSuggestOracle();
		mwso.clear();
		mwso.addAll(suggestions);
	}

	@Override
	public void setSuplenteSuggestions(ArrayList<String> suggestions) {
		MultiWordSuggestOracle mwso = (MultiWordSuggestOracle)suplenteSearchBox.getSuggestOracle();
		mwso.clear();
		mwso.addAll(suggestions);
	}

	private void buildTable() {

		int i = -1;
		
		rutColumn = new Column<EvaluacionSupervisorDTO, String>(new TextCell()) {

			@Override
			public String getValue(EvaluacionSupervisorDTO o) {
				return o.getSupervisor().getRut();
			}
		};
		rutColumn.setSortable(false);
		dataGrid.setColumnWidth(++i, 10, Unit.EM);

		nombreColumn = new Column<EvaluacionSupervisorDTO, String>(
				new TextCell()) {

			@Override
			public String getValue(EvaluacionSupervisorDTO o) {
				return ViewUtils.limitarString(o.getSupervisor().getNombres() + " "
						+ o.getSupervisor().getApellidoPaterno() + " "
						+ o.getSupervisor().getApellidoMaterno(),30);
			}
		};
		nombreColumn.setSortable(false);
		dataGrid.setColumnWidth(++i, 26, Unit.EM);

		rbdColumn = new Column<EvaluacionSupervisorDTO, String>(new TextCell()) {

			@Override
			public String getValue(EvaluacionSupervisorDTO o) {
				return o.getRbd();
			}
		};
		rbdColumn.setSortable(false);
		dataGrid.setColumnWidth(++i, 5, Unit.EM);

		establecimientoColumn = new Column<EvaluacionSupervisorDTO, String>(
				new TextCell()) {

			@Override
			public String getValue(EvaluacionSupervisorDTO o) {
				return ViewUtils.limitarString(o.getEstablecimiento(),25);
			}
		};
		establecimientoColumn.setSortable(false);
		dataGrid.setColumnWidth(++i, 25, Unit.EM);

		cursoColumn = new Column<EvaluacionSupervisorDTO, String>(
				new TextCell()) {

			@Override
			public String getValue(EvaluacionSupervisorDTO o) {
				return o.getCurso();
			}
		};
		cursoColumn.setSortable(false);
		dataGrid.setColumnWidth(++i, 6, Unit.EM);

		puntualidadColumn = new Column<EvaluacionSupervisorDTO, Boolean>(
				new CheckboxCell()) {

			@Override
			public Boolean getValue(EvaluacionSupervisorDTO o) {
				return o.getPuntualidad() != null && o.getPuntualidad() > 0;
			}
		};
		puntualidadColumn.setSortable(false);
		puntualidadColumn.setFieldUpdater(new FieldUpdater<EvaluacionSupervisorDTO, Boolean>() {

			@Override
			public void update(int index, EvaluacionSupervisorDTO object,
					Boolean value) {
				object.setPuntualidad((value)?4:0);
				presenter.sinc(object);
				//dataProvider.refresh();
			}
		});
		dataGrid.setColumnWidth(++i, 8, Unit.EM);

		presentacionColumn = new Column<EvaluacionSupervisorDTO, Boolean>(
				new CheckboxCell()) {

			@Override
			public Boolean getValue(EvaluacionSupervisorDTO o) {
				return o.getPresentacionPersonal() != null
						&& o.getPresentacionPersonal() > 0;
			}
		};
		presentacionColumn.setSortable(false);
		presentacionColumn.setFieldUpdater(new FieldUpdater<EvaluacionSupervisorDTO, Boolean>() {

			@Override
			public void update(int index, EvaluacionSupervisorDTO object,
					Boolean value) {
				object.setPresentacionPersonal((value)?4:0);
				presenter.sinc(object);
				//dataProvider.refresh();
				
			}
		});
		dataGrid.setColumnWidth(++i, 12, Unit.EM);

		generalColumn = new Column<EvaluacionSupervisorDTO, Boolean>(
				new CheckboxCell()) {

			@Override
			public Boolean getValue(EvaluacionSupervisorDTO o) {
				return o.getGeneral() != null && o.getGeneral() > 0;
			}
		};
		generalColumn.setSortable(false);
		generalColumn.setFieldUpdater(new FieldUpdater<EvaluacionSupervisorDTO, Boolean>() {

			@Override
			public void update(int index, EvaluacionSupervisorDTO object,
					Boolean value) {
				object.setGeneral((value)?4:0);
				presenter.sinc(object);
				//dataProvider.refresh();
				
			}
		});
		dataGrid.setColumnWidth(++i, 7, Unit.EM);

	}
	
	private void buildSuplenteTable(){
		int i = -1;
		
		srutColumn = new Column<EvaluacionSuplenteDTO, String>(new TextCell()) {

			@Override
			public String getValue(EvaluacionSuplenteDTO o) {
				return o.getSuplente().getRut();
			}
		};
		srutColumn.setSortable(false);
		suplentesdataGrid.setColumnWidth(++i, 10, Unit.EM);

		snombreColumn = new Column<EvaluacionSuplenteDTO, String>(
				new TextCell()) {

			@Override
			public String getValue(EvaluacionSuplenteDTO o) {
				return ViewUtils.limitarString(o.getSuplente().getNombres() + " "
						+ o.getSuplente().getApellidoPaterno() + " "
						+ o.getSuplente().getApellidoMaterno(),30);
			}
		};
		snombreColumn.setSortable(false);
		suplentesdataGrid.setColumnWidth(++i, 26, Unit.EM);

		coColumn = new Column<EvaluacionSuplenteDTO, String>(new TextCell()) {

			@Override
			public String getValue(EvaluacionSuplenteDTO o) {
				return o.getCo();
			}
		};
		coColumn.setSortable(false);
		suplentesdataGrid.setColumnWidth(++i, 5, Unit.EM);

		sgeneralColumn = new Column<EvaluacionSuplenteDTO, Boolean>(
				new CheckboxCell()) {

			@Override
			public Boolean getValue(EvaluacionSuplenteDTO o) {
				return (o.getPresente()!=null)?o.getPresente():false;
			}
		};
		sgeneralColumn.setSortable(false);
		sgeneralColumn.setFieldUpdater(new FieldUpdater<EvaluacionSuplenteDTO, Boolean>() {

			@Override
			public void update(int index, EvaluacionSuplenteDTO object,
					Boolean value) {
				object.setPresente(value);
				presenter.sinc(object);
				
			}
		});
		suplentesdataGrid.setColumnWidth(++i, 7, Unit.EM);

	}
	@Override
	public int getTabSelected() {
		return tabs.getSelectedIndex();
	}
}
