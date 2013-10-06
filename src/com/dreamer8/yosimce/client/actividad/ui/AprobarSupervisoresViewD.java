package com.dreamer8.yosimce.client.actividad.ui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.dreamer8.yosimce.client.ui.OverMenuBar;
import com.dreamer8.yosimce.shared.dto.EvaluacionSupervisorDTO;
import com.dreamer8.yosimce.shared.dto.EvaluacionSupervisorDTO;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.builder.shared.TableCellBuilder;
import com.google.gwt.dom.builder.shared.TableRowBuilder;
import com.google.gwt.dom.client.Style.OutlineStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.text.shared.AbstractSafeHtmlRenderer;
import com.google.gwt.text.shared.SafeHtmlRenderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.AbstractCellTableBuilder;
import com.google.gwt.user.cellview.client.AbstractHeaderOrFooterBuilder;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortList;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.cellview.client.TextHeader;
import com.google.gwt.user.cellview.client.AbstractCellTable.Style;
import com.google.gwt.user.cellview.client.ColumnSortList.ColumnSortInfo;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionModel;

public class AprobarSupervisoresViewD extends Composite implements AprobarSupervisoresView {

	private static AprobarSupervisoresViewDUiBinder uiBinder = GWT
			.create(AprobarSupervisoresViewDUiBinder.class);

	interface AprobarSupervisoresViewDUiBinder extends
			UiBinder<Widget, AprobarSupervisoresViewD> {
	}
	
	 interface AsStyle extends CssResource {
		 String childCell();
		 String groupHeaderCell();
	 }
	 
	 private class CustomHeaderBuilder extends
		AbstractHeaderOrFooterBuilder<EvaluacionSupervisorDTO> {

		 private Header<String> rutHeader = new TextHeader("RUT");
		 private Header<String> nombreHeader = new TextHeader("Nombre");
		 private Header<String> rbdHeader = new TextHeader("RBD");
		 private Header<String> establecimientoHeader = new TextHeader("Establecimiento");
		 private Header<String> cursoHeader = new TextHeader("Curso");
		 private Header<String> puHeader = new TextHeader("Puntualidad");
		 private Header<String> ppHeader = new TextHeader("Presentación personal");
		 private Header<String> geHeader = new TextHeader("Cumplió");

	public CustomHeaderBuilder() {
		super(dataGrid, false);
		setSortIconStartOfLine(false);
	}

	@Override
	protected boolean buildHeaderOrFooterImpl() {
		Style s = dataGrid.getResources().style();
		TableRowBuilder tr = startRow();
	      tr.startTH().colSpan(1).rowSpan(1)
	          .className(s.header() + " " + s.firstColumnHeader());
	      tr.endTH();
		
		/*
		 * Name group header. Associated with the last name column, so
		 * clicking on the group header sorts by last name.
		 */
		TableCellBuilder th = tr.startTH().colSpan(2).className(style.groupHeaderCell());
		th.style().trustedProperty("border-right", "10px solid white").endStyle();
		th.text("Supervisor").endTH();

		th = tr.startTH().colSpan(3).className(style.groupHeaderCell());
		th.style().trustedProperty("border-right", "10px solid white").endStyle();
		th.text("Establecimiento").endTH();
		
		th = tr.startTH().colSpan(3).className(style.groupHeaderCell());
		th.style().trustedProperty("border-right", "10px solid white").endStyle();
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
		buildHeader(tr, rutHeader, rutColumn, sortedColumn,isSortAscending, false, false);
		buildHeader(tr, nombreHeader, nombreColumn, sortedColumn,isSortAscending, false, false);
		buildHeader(tr, rbdHeader, rbdColumn, sortedColumn,isSortAscending, false, false);
		buildHeader(tr, establecimientoHeader, establecimientoColumn, sortedColumn,isSortAscending, false, false);
		buildHeader(tr, cursoHeader, cursoColumn, sortedColumn,isSortAscending, false, false);
		buildHeader(tr, puHeader, puntualidadColumn, sortedColumn,isSortAscending, false, false);
		buildHeader(tr, ppHeader, presentacionColumn, sortedColumn,isSortAscending, false, false);
		buildHeader(tr, geHeader, generalColumn, sortedColumn,isSortAscending, false, false);
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
	private void buildHeader(TableRowBuilder out, Header<?> header,Column<EvaluacionSupervisorDTO, ?> column,
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
	 
	 /*
	  private class CustomTableBuilder extends AbstractCellTableBuilder<EvaluacionSupervisorDTO> {

	    private final String childCell = " " + style.childCell();
	    private final String rowStyle;
	    private final String selectedRowStyle;
	    private final String cellStyle;
	    private final String selectedCellStyle;
	    private final String evenRow1Style;
	    private final String evenRow2Style;
	    private final String oddRow1Style;
	    private final String oddRow2Style;
	    
	    private boolean even;

	    @SuppressWarnings("deprecation")
	    public CustomTableBuilder() {
	      super(dataGrid);
	      even = true;
	      // Cache styles for faster access.
	      Style style = dataGrid.getResources().style();
	      rowStyle = style.evenRow();
	      selectedRowStyle = " " + style.selectedRow();
	      cellStyle = style.cell() + " " + style.evenRowCell();
	      selectedCellStyle = " " + style.selectedRowCell();
	      evenRow1Style = "";
	      evenRow2Style = "";
	      oddRow1Style = "";
	      oddRow2Style = "";
	    }

	    @SuppressWarnings("deprecation")
	    @Override
	    public void buildRowImpl(EvaluacionSupervisorDTO rowValue, int absRowIndex) {
	      buildSupervisorRow(rowValue, absRowIndex, false);

	      if (presenter!=null || showingRbds.contains(rowValue.getSupervisor().getId())) {
	        ArrayList<EvaluacionSupervisorDTO> rbds = presenter.getEvaluacionesByRbd(rowValue.getSupervisor().getId());
	        for (EvaluacionSupervisorDTO rbd : rbds) {
	          buildSupervisorRow(rbd, absRowIndex, true);
	        }
	      }
	    }
	    */
	    /*
	    private void buildSupervisorRow(EvaluacionSupervisorDTO rowValue, int absRowIndex, boolean isRbd){
	    	SelectionModel<? super EvaluacionSupervisorDTO> selectionModel = dataGrid.getSelectionModel();
	    	boolean isSelected =(selectionModel == null || rowValue == null) ? false : selectionModel.isSelected(rowValue);
	    	boolean isEven = absRowIndex % 2 == 0;
	    	StringBuilder trClasses = new StringBuilder(rowStyle);
	    	if (isSelected) {
	    		trClasses.append(selectedRowStyle);
	    	}
	    	
	    	String cellStyles = cellStyle;
		    if (isSelected) {
		    	cellStyles += selectedCellStyle;
		    }
		    if (!isSupervisor) {
		    	cellStyles += childCell;
		    }
		    TableRowBuilder row = startRow();
		     row.className(trClasses.toString());
		     
		     TableCellBuilder td = row.startTD();
		      td.className(cellStyles);
		      if (isSupervisor) {
		        td.style().outlineStyle(OutlineStyle.NONE).endStyle();
		        renderCell(td, createContext(1), viewFriendsColumn, rowValue);
		      }
		      td.endTD();
	    }

	    /**
	     * Build a row.
	     * 
	     * @param rowValue the contact info
	     * @param absRowIndex the absolute row index
	     *@param isFriend true if this is a subrow, false if a top level row
	     
	    @SuppressWarnings("deprecation")
	    private void buildContactRow(ContactInfo rowValue, int absRowIndex, boolean isFriend) {
	      // Calculate the row styles.
	      SelectionModel<? super ContactInfo> selectionModel = dataGrid.getSelectionModel();
	      boolean isSelected =
	          (selectionModel == null || rowValue == null) ? false : selectionModel
	              .isSelected(rowValue);
	      boolean isEven = absRowIndex % 2 == 0;
	      StringBuilder trClasses = new StringBuilder(rowStyle);
	      if (isSelected) {
	        trClasses.append(selectedRowStyle);
	      }

	      // Calculate the cell styles.
	      String cellStyles = cellStyle;
	      if (isSelected) {
	        cellStyles += selectedCellStyle;
	      }
	      if (isFriend) {
	        cellStyles += childCell;
	      }

	      TableRowBuilder row = startRow();
	      row.className(trClasses.toString());
*/
	      /*
	       * Checkbox column.
	       * 
	       * This table will uses a checkbox column for selection. Alternatively,
	       * you can call dataGrid.setSelectionEnabled(true) to enable mouse
	       * selection.
	       
	      TableCellBuilder td = row.startTD();
	      td.className(cellStyles);
	      td.style().outlineStyle(OutlineStyle.NONE).endStyle();
	      if (!isFriend) {
	        renderCell(td, createContext(0), checkboxColumn, rowValue);
	      }
	      td.endTD();
*/
	      /*
	       * View friends column.
	       * 
	       * Displays a link to "show friends". When clicked, the list of friends is
	       * displayed below the contact.
	       
	      td = row.startTD();
	      td.className(cellStyles);
	      if (!isFriend) {
	        td.style().outlineStyle(OutlineStyle.NONE).endStyle();
	        renderCell(td, createContext(1), viewFriendsColumn, rowValue);
	      }
	      td.endTD();

	      // First name column.
	      td = row.startTD();
	      td.className(cellStyles);
	      td.style().outlineStyle(OutlineStyle.NONE).endStyle();
	      if (isFriend) {
	        td.text(rowValue.getFirstName());
	      } else {
	        renderCell(td, createContext(2), firstNameColumn, rowValue);
	      }
	      td.endTD();

	      // Last name column.
	      td = row.startTD();
	      td.className(cellStyles);
	      td.style().outlineStyle(OutlineStyle.NONE).endStyle();
	      if (isFriend) {
	        td.text(rowValue.getLastName());
	      } else {
	        renderCell(td, createContext(3), lastNameColumn, rowValue);
	      }
	      td.endTD();

	      // Age column.
	      td = row.startTD();
	      td.className(cellStyles);
	      td.style().outlineStyle(OutlineStyle.NONE).endStyle();
	      td.text(NumberFormat.getDecimalFormat().format(rowValue.getAge())).endTD();

	      // Category column.
	      td = row.startTD();
	      td.className(cellStyles);
	      td.style().outlineStyle(OutlineStyle.NONE).endStyle();
	      if (isFriend) {
	        td.text(rowValue.getCategory().getDisplayName());
	      } else {
	        renderCell(td, createContext(5), categoryColumn, rowValue);
	      }
	      td.endTD();

	      // Address column.
	      td = row.startTD();
	      td.className(cellStyles);
	      DivBuilder div = td.startDiv();
	      div.style().outlineStyle(OutlineStyle.NONE).endStyle();
	      div.text(rowValue.getAddress()).endDiv();
	      td.endTD();

	      row.endTR();
	    }
	  }
	*/
	@UiField AsStyle style;

	@UiField OverMenuBar menu;
	@UiField MenuItem menuItem;
	@UiField(provided = true) DataGrid<EvaluacionSupervisorDTO> dataGrid;
	@UiField(provided = true) SimplePager pager;
	
	private Column<EvaluacionSupervisorDTO,String> showRbdsColumn;
	private Column<EvaluacionSupervisorDTO,String> rutColumn;
	private Column<EvaluacionSupervisorDTO,String> nombreColumn;
	private Column<EvaluacionSupervisorDTO,String> rbdColumn;
	private Column<EvaluacionSupervisorDTO,String> establecimientoColumn;
	private Column<EvaluacionSupervisorDTO,String> cursoColumn;
	private Column<EvaluacionSupervisorDTO,Boolean> puntualidadColumn;
	private Column<EvaluacionSupervisorDTO,Boolean> presentacionColumn;
	private Column<EvaluacionSupervisorDTO,Boolean> generalColumn;
	private ListDataProvider<EvaluacionSupervisorDTO> dataProvider;
	
	private final Set<Integer> showingRbds = new HashSet<Integer>();
	
	private AprobarSupervisoresPresenter presenter;
	
	public AprobarSupervisoresViewD() {
		dataGrid = new DataGrid<EvaluacionSupervisorDTO>(EvaluacionSupervisorDTO.KEY_PROVIDER);
		dataProvider = new ListDataProvider<EvaluacionSupervisorDTO>(EvaluacionSupervisorDTO.KEY_PROVIDER);
		dataGrid.setAutoHeaderRefreshDisabled(true);
		SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
	    pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
	    pager.setDisplay(dataGrid);
	    buildTable();
	    //dataGrid.setTableBuilder(new CustomTableBuilder());
	    //dataGrid.setHeaderBuilder(new CustomHeaderBuilder());
	    dataProvider.addDataDisplay(dataGrid);
		
		initWidget(uiBinder.createAndBindUi(this));
		menu.setOverItem(menuItem);
		menu.setOverCommand(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				presenter.toggleMenu();
			}
		});
	}
	
	@Override
	public void setPresenter(AprobarSupervisoresPresenter presenter) {
		this.presenter = presenter;
	}
	
	@Override
	public void setSupervisores(ArrayList<EvaluacionSupervisorDTO> supervisores) {
		dataGrid.setPageSize(supervisores.size()+1);
		dataGrid.setVisibleRange(0, supervisores.size()+1);
		dataGrid.setRowCount(supervisores.size());
		dataGrid.setRowData(supervisores);
	}
	
	@Override
	public void setGeneralFieldUpdater(
			FieldUpdater<EvaluacionSupervisorDTO, Boolean> updater) {
		generalColumn.setFieldUpdater(updater);
	}
	
	@Override
	public void setPuntualidadFieldUpdater(
			FieldUpdater<EvaluacionSupervisorDTO, Boolean> updater) {
		puntualidadColumn.setFieldUpdater(updater);
	}
	
	@Override
	public void setPresentacionFieldUpdater(
			FieldUpdater<EvaluacionSupervisorDTO, Boolean> updater) {
		presentacionColumn.setFieldUpdater(updater);
	}
	
	private void buildTable(){
	    SafeHtmlRenderer<String> anchorRenderer = new AbstractSafeHtmlRenderer<String>() {
	      @Override
	      public SafeHtml render(String object) {
	        SafeHtmlBuilder sb = new SafeHtmlBuilder();
	        sb.appendHtmlConstant("(<a href=\"javascript:;\">").appendEscaped(object)
	            .appendHtmlConstant("</a>)");
	        return sb.toSafeHtml();
	      }
	    };
	    showRbdsColumn = new Column<EvaluacionSupervisorDTO, String>(new ClickableTextCell(anchorRenderer)) {
	      @Override
	      public String getValue(EvaluacionSupervisorDTO o) {
	        if (showingRbds.contains(o.getSupervisor().getId())) {
	          return "ocultar";
	        } else {
	          return "expandir";
	        }
	      }
	    };
	    
	    showRbdsColumn.setFieldUpdater(new FieldUpdater<EvaluacionSupervisorDTO, String>() {
	      @Override
	      public void update(int index, EvaluacionSupervisorDTO o, String value) {
	        if (showingRbds.contains(o.getSupervisor().getId())) {
	          showingRbds.remove(o.getSupervisor().getId());
	        } else {
	          showingRbds.add(o.getSupervisor().getId());
	        }
	        // Redraw the modified row.
	        dataGrid.redrawRow(index);
	      }
	    });
	    dataGrid.setColumnWidth(0, 10, Unit.EM);
		
	    
		rutColumn = new Column<EvaluacionSupervisorDTO, String>(new TextCell()) {

			@Override
			public String getValue(EvaluacionSupervisorDTO o) {
				return o.getSupervisor().getRut();
			}
		};
		rutColumn.setSortable(false);
		dataGrid.setColumnWidth(1, 13, Unit.EM);
		
		
		nombreColumn = new Column<EvaluacionSupervisorDTO, String>(new TextCell()) {

			@Override
			public String getValue(EvaluacionSupervisorDTO o) {
				return o.getSupervisor().getNombres()+ " "+o.getSupervisor().getApellidoPaterno()+" "+o.getSupervisor().getApellidoMaterno() ;
			}
		};
		nombreColumn.setSortable(false);
		dataGrid.setColumnWidth(2, 25, Unit.EM);
		
		rbdColumn = new Column<EvaluacionSupervisorDTO, String>(new TextCell()) {

			@Override
			public String getValue(EvaluacionSupervisorDTO o) {
				return o.getRbd();
			}
		};
		rbdColumn.setSortable(false);
		dataGrid.setColumnWidth(3, 5, Unit.EM);
		
		establecimientoColumn = new Column<EvaluacionSupervisorDTO, String>(new TextCell()) {

			@Override
			public String getValue(EvaluacionSupervisorDTO o) {
				return o.getEstablecimiento();
			}
		};
		establecimientoColumn.setSortable(false);
		dataGrid.setColumnWidth(4, 25, Unit.EM);
		
		cursoColumn = new Column<EvaluacionSupervisorDTO, String>(new TextCell()) {

			@Override
			public String getValue(EvaluacionSupervisorDTO o) {
				return o.getCurso();
			}
		};
		cursoColumn.setSortable(false);
		dataGrid.setColumnWidth(5, 3, Unit.EM);
		
		puntualidadColumn = new Column<EvaluacionSupervisorDTO, Boolean>(new CheckboxCell()) {

			@Override
			public Boolean getValue(EvaluacionSupervisorDTO o) {
				return o.getPuntualidad() != null && o.getPuntualidad()>0;
			}
		};
		puntualidadColumn.setSortable(false);
		dataGrid.setColumnWidth(6, 10, Unit.EM);
		
		presentacionColumn = new Column<EvaluacionSupervisorDTO, Boolean>(new CheckboxCell()) {

			@Override
			public Boolean getValue(EvaluacionSupervisorDTO o) {
				return o.getPresentacionPersonal()!=null && o.getPresentacionPersonal()>0;
			}
		};
		presentacionColumn.setSortable(false);
		dataGrid.setColumnWidth(6, 10, Unit.EM);
		
		generalColumn = new Column<EvaluacionSupervisorDTO, Boolean>(new CheckboxCell()) {

			@Override
			public Boolean getValue(EvaluacionSupervisorDTO o) {
				return o.getGeneral()!= null && o.getGeneral()>0;
			}
		};
		generalColumn.setSortable(false);
		dataGrid.setColumnWidth(6, 10, Unit.EM);
		
	}

}
