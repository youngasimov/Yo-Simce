package com.dreamer8.yosimce.client.actividad.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.dreamer8.yosimce.client.actividad.ActividadesPlace;
import com.dreamer8.yosimce.client.actividad.FormActividadPlace;
import com.dreamer8.yosimce.client.actividad.SincronizacionPlace;
import com.dreamer8.yosimce.client.general.DetalleCursoPlace;
import com.dreamer8.yosimce.client.ui.HyperTextCell;
import com.dreamer8.yosimce.client.ui.OverMenuBar;
import com.dreamer8.yosimce.client.ui.ViewUtils;
import com.dreamer8.yosimce.client.ui.resources.SimceResources;
import com.dreamer8.yosimce.shared.dto.ActividadPreviewDTO;
import com.dreamer8.yosimce.shared.dto.DocumentoDTO;
import com.dreamer8.yosimce.shared.dto.EstadoAgendaDTO;
import com.dreamer8.yosimce.shared.dto.SectorDTO;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.ImageCell;
import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.builder.shared.TableCellBuilder;
import com.google.gwt.dom.builder.shared.TableRowBuilder;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.AbstractHeaderOrFooterBuilder;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortList;
import com.google.gwt.user.cellview.client.ColumnSortList.ColumnSortInfo;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.HasKeyboardPagingPolicy.KeyboardPagingPolicy;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.cellview.client.TextHeader;
import com.google.gwt.user.cellview.client.AbstractCellTable.Style;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.MenuItem;
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

	/**
	 * Renders custom table headers. The top header row includes the groups
	 * "Name" and "Information", each of which spans multiple columns. The
	 * second row of the headers includes the contacts' first and last names
	 * grouped under the "Name" category. The second row also includes the age,
	 * category, and address of the contacts grouped under the "Information"
	 * category.
	 */
	private class CustomHeaderBuilder extends
			AbstractHeaderOrFooterBuilder<ActividadPreviewDTO> {

		private Header<String> rbdHeader = new TextHeader("RBD");
		private Header<String> pisaHeader = new TextHeader("C. Pisa");
		private Header<String> establecimientoHeader = new TextHeader("Establecimiento");
		private Header<String> cursoHeader = new TextHeader("Curso");
		private Header<String> tipoHeader = new TextHeader("Tipo");
		private Header<String> estadoHeader = new TextHeader("estado");
		
		private Header<String> alumnosTotalesHeader = new TextHeader("Total");
		private Header<String> porcentajeAsistenciaHeader = new TextHeader("Asistencia");
		private Header<String> alumnosEvaluadosHeader = new TextHeader("Evaluados");
		//private Header<String> alumnosSincronizadosHeader = new TextHeader("Sinc.");
		
		private Header<String> formEntregadosHeader = new TextHeader("Entregados");
		private Header<String> formRecibidosHeader = new TextHeader("Recibidos");
		private Header<String> formRecibidosAplicadosHeader = new TextHeader("Aplicados");
		
		private Header<String> pcTotalHeader = new TextHeader("Pcs");
		
		private Header<String> contingenciaHeader = new TextHeader("Momentánea");
		private Header<String> contingenciaLimitanteHeader = new TextHeader("Limitante");
		
		private Header<String> regionHeader = new TextHeader("Región");
		private Header<String> comunaHeader = new TextHeader("Comuna");
		private Header<String> docHeader = new TextHeader("Documento");
		
		private Header<String> examinadorHeader = new TextHeader("Examinador");
		private Header<String> supervisorHeader = new TextHeader("Coordinador");
		
		private Header<String> nombreContactoHeader = new TextHeader("Nombre");
		private Header<String> telefonoContactoHeader = new TextHeader("Teléfono");
		private Header<String> emailContactoHeader = new TextHeader("Email");

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
			TableCellBuilder th = tr.startTH().colSpan(6).className(style.groupHeaderCell());
			th.style().trustedProperty("border-right", "10px solid white").endStyle();
			th.text("Información de establecimiento").endTH();

			th = tr.startTH().colSpan(3).className(style.groupHeaderCell());
			th.style().trustedProperty("border-right", "10px solid white").endStyle();
			th.text("Alumnos").endTH();
			
			th = tr.startTH().colSpan(3).className(style.groupHeaderCell());
			th.style().trustedProperty("border-right", "10px solid white").endStyle();
			th.text("Form. P. y A.").endTH();
			
			tr.startTH().colSpan(1).endTH();
			
			th = tr.startTH().colSpan(2).className(style.groupHeaderCell());
			th.style().trustedProperty("border-right", "10px solid white").endStyle();
			th.text("Contingencia").endTH();
			
			tr.startTH().colSpan(5).endTH();
			
			th = tr.startTH().colSpan(3).className(style.groupHeaderCell());
			th.style().trustedProperty("border-right", "10px solid white").endStyle();
			th.text("Contacto").endTH();

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
			buildHeader(tr, rbdHeader, rbdColumn, sortedColumn,isSortAscending, false, false);
			buildHeader(tr, pisaHeader, pisaColumn, sortedColumn,isSortAscending, false, false);
			buildHeader(tr, establecimientoHeader, establecimientoColumn, sortedColumn,isSortAscending, false, false);
			buildHeader(tr, cursoHeader, cursoColumn, sortedColumn,isSortAscending, false, false);
			buildHeader(tr, tipoHeader, tipoColumn, sortedColumn,isSortAscending, false, false);
			buildHeader(tr, estadoHeader, estadoColumn, sortedColumn,isSortAscending, false, false);
			
			buildHeader(tr, alumnosTotalesHeader, alumnosTotalColumn, sortedColumn,isSortAscending, false, false);
			buildHeader(tr, porcentajeAsistenciaHeader, porcentajeAsistenciaColumn, sortedColumn,isSortAscending, false, false);
			buildHeader(tr, alumnosEvaluadosHeader, alumnosEvaluadosColumn, sortedColumn,isSortAscending, false, false);
			//buildHeader(tr, alumnosSincronizadosHeader, alumnosSincronizadosColumn, sortedColumn,isSortAscending, false, false);
			
			buildHeader(tr, formEntregadosHeader,cuestionarioEntregadosColumn , sortedColumn,isSortAscending, false, false);
			buildHeader(tr, formRecibidosHeader, cuestionarioRecibidosColumn, sortedColumn,isSortAscending, false, false);
			buildHeader(tr, formRecibidosAplicadosHeader, cuestionarioRecibidosAplicadosColumn, sortedColumn,isSortAscending, false, false);
			
			buildHeader(tr, pcTotalHeader, pcTotalColumn, sortedColumn,isSortAscending, false, false);
			
			buildHeader(tr, contingenciaHeader, contingenciaColumn, sortedColumn,isSortAscending, false, false);
			buildHeader(tr, contingenciaLimitanteHeader, contingenciaLimitanteColumn, sortedColumn,isSortAscending, false, false);
			
			buildHeader(tr, regionHeader, regionColumn, sortedColumn,isSortAscending, false, false);
			buildHeader(tr, comunaHeader, comunaColumn, sortedColumn,isSortAscending, false, false);
			buildHeader(tr, docHeader, docColumn, sortedColumn,isSortAscending, false, false);
			
			buildHeader(tr, examinadorHeader, examinadorColumn, sortedColumn,isSortAscending, false, false);
			buildHeader(tr, supervisorHeader, supervisorColumn, sortedColumn,isSortAscending, false, false);
			
			buildHeader(tr, nombreContactoHeader, nombreContactoColumn, sortedColumn,isSortAscending, false, false);
			buildHeader(tr, telefonoContactoHeader, telefonoContactoColumn, sortedColumn,isSortAscending, false, false);
			buildHeader(tr, emailContactoHeader, emailContactoColumn, sortedColumn,isSortAscending, false, false);
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
				Column<ActividadPreviewDTO, ?> column,
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

	@UiField
	Styles style;
	
	@UiField
	OverMenuBar menu;
	@UiField
	MenuItem menuItem;
	@UiField
	MenuItem filtrosItem;
	@UiField
	MenuItem exportarActividadesItem;
	@UiField
	MenuItem exportarAlumnosItem;
	@UiField
	MenuItem cursoItem;
	@UiField
	MenuItem formItem;
	@UiField
	MenuItem sincronizacionItem;
	@UiField
	MenuItem informacionItem;

	@UiField(provided = true)
	DataGrid<ActividadPreviewDTO> dataGrid;
	@UiField(provided = true)
	SimplePager pager;

	private HashMap<Integer, CheckBox> estadoCheckBoxs;

	private SingleSelectionModel<ActividadPreviewDTO> selectionModel;
	private ActividadPreviewDTO selectedItem;
	private ActividadesPresenter presenter;

	private DialogBox filtrosDialogBox;
	private FiltroActividadesPanelViewD filtrosPanel;

	private boolean sinc;
	private boolean form;
	private boolean info;
	
	private Column<ActividadPreviewDTO, String> rbdColumn;
	private Column<ActividadPreviewDTO, String> pisaColumn;
	private Column<ActividadPreviewDTO, String> establecimientoColumn;
	private Column<ActividadPreviewDTO, String> cursoColumn;
	private Column<ActividadPreviewDTO, String> tipoColumn;
	private Column<ActividadPreviewDTO, String> estadoColumn;
	
	private Column<ActividadPreviewDTO, Number> alumnosTotalColumn;
	private Column<ActividadPreviewDTO, String> porcentajeAsistenciaColumn;
	private Column<ActividadPreviewDTO, Number> alumnosEvaluadosColumn;
	//private Column<ActividadPreviewDTO, Number> alumnosSincronizadosColumn;
	
	private Column<ActividadPreviewDTO, Number> cuestionarioEntregadosColumn;
	private Column<ActividadPreviewDTO, Number> cuestionarioRecibidosColumn;
	private Column<ActividadPreviewDTO, Number> cuestionarioRecibidosAplicadosColumn;
	
	private Column<ActividadPreviewDTO, Number> pcTotalColumn;
	
	private Column<ActividadPreviewDTO, String> contingenciaColumn;
	private Column<ActividadPreviewDTO, String> contingenciaLimitanteColumn;
	
	private Column<ActividadPreviewDTO, String> regionColumn;
	private Column<ActividadPreviewDTO, String> comunaColumn;
	private Column<ActividadPreviewDTO, DocumentoDTO> docColumn;
	
	private Column<ActividadPreviewDTO, String> examinadorColumn;
	private Column<ActividadPreviewDTO, String> supervisorColumn;
	
	private Column<ActividadPreviewDTO, String> nombreContactoColumn;
	private Column<ActividadPreviewDTO, String> telefonoContactoColumn;
	private Column<ActividadPreviewDTO, String> emailContactoColumn;
	
	

	public ActividadesViewD() {
		dataGrid = new DataGrid<ActividadPreviewDTO>(
				ActividadPreviewDTO.KEY_PROVIDER);
		pager = new SimplePager(TextLocation.CENTER, false, false);
		dataGrid.setWidth("100%");
		dataGrid.setAutoHeaderRefreshDisabled(true);
		
		initWidget(uiBinder.createAndBindUi(this));
		filtrosPanel = new FiltroActividadesPanelViewD();
		filtrosDialogBox = new DialogBox(true, false);
		filtrosDialogBox.setWidget(filtrosPanel);
		estadoCheckBoxs = new HashMap<Integer, CheckBox>();

		menu.insertSeparator(1);
		menu.insertSeparator(5);

		menu.setOverItem(menuItem);
		menu.setOverCommand(new Scheduler.ScheduledCommand() {

			@Override
			public void execute() {
				presenter.toggleMenu();
			}
		});

		filtrosItem.setScheduledCommand(new Scheduler.ScheduledCommand() {

			@Override
			public void execute() {
				filtrosDialogBox.showRelativeTo(filtrosItem);
			}
		});

		exportarActividadesItem
				.setScheduledCommand(new Scheduler.ScheduledCommand() {

					@Override
					public void execute() {
						presenter.onExportarClick();
					}
				});

		exportarAlumnosItem
				.setScheduledCommand(new Scheduler.ScheduledCommand() {

					@Override
					public void execute() {
						presenter.onExportarAlumnosClick();
					}
				});

		formItem.setScheduledCommand(new Scheduler.ScheduledCommand() {

			@Override
			public void execute() {
				FormActividadPlace fap = new FormActividadPlace();
				if (selectedItem != null)
					fap.setIdCurso(selectedItem.getCursoId());
				presenter.goTo(fap);
			}
		});

		sincronizacionItem
				.setScheduledCommand(new Scheduler.ScheduledCommand() {

					@Override
					public void execute() {
						SincronizacionPlace place = new SincronizacionPlace();
						place.setIdCurso(selectedItem.getCursoId());
						presenter.goTo(place);
					}
				});

		informacionItem.setScheduledCommand(new Scheduler.ScheduledCommand() {

			@Override
			public void execute() {
				DetalleCursoPlace dcp = new DetalleCursoPlace();
				if (selectedItem != null)
					dcp.setCursoId(selectedItem.getCursoId());
				presenter.goTo(dcp);
			}
		});

		bind();
	}

	@UiFactory
	public static SimceResources getResources() {
		return SimceResources.INSTANCE;
	}

	@Override
	public void setExportarActividadesVisivility(boolean visible) {
		exportarActividadesItem.setVisible(visible);
	}

	@Override
	public void setExportarAlumnosVisivility(boolean visible) {
		exportarAlumnosItem.setVisible(visible);
	}

	@Override
	public void setSincronizacionVisivility(boolean visible) {
		this.sinc = visible;
	}

	@Override
	public void setFormularioVisivility(boolean visible) {
		this.form = visible;
	}

	@Override
	public void setInformacionVisivility(boolean visible) {
		this.info = visible;
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
	public void clear() {
		selectedItem = null;
		sinc = false;
		form = false;
		info = false;
		cursoItem.setHTML("");
		estadoCheckBoxs.clear();
		filtrosPanel.estadosPanel.clear();
	}

	@Override
	public void setPresenter(ActividadesPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void setActividadesMaterialContingencia(boolean value) {
		filtrosPanel.contingenciaBox.setValue(value);
	}

	@Override
	public void setActividadesSincronizadas(boolean value) {
		filtrosPanel.sincronizacionTotalBox.setValue(value);
	}

	@Override
	public void setActividadesParcialementeSincronizadas(boolean value) {
		filtrosPanel.sincronizacionParcialBox.setValue(value);
	}

	@Override
	public void setActividadesNoSincronizadas(boolean value) {
		filtrosPanel.sincronizadasNulaBox.setValue(value);
	}

	@Override
	public void setSelectedEstados(ArrayList<Integer> estados) {
		for (Entry<Integer, CheckBox> e : estadoCheckBoxs.entrySet()) {
			e.getValue().setValue(false);
		}
		for (Integer id : estados) {
			if (estadoCheckBoxs.containsKey(id)) {
				estadoCheckBoxs.get(id).setValue(true);
			}
		}
	}

	@Override
	public void setRegiones(ArrayList<SectorDTO> regiones) {
		filtrosPanel.regionBox.setVisible(true);
		filtrosPanel.regionBox.clear();
		filtrosPanel.regionBox.addItem("todas", -1 + "");
		filtrosPanel.comunaBox.setVisible(false);
		for (SectorDTO s : regiones) {
			filtrosPanel.regionBox.addItem(s.getSector(), s.getIdSector() + "");
		}
	}

	@Override
	public void setComunas(ArrayList<SectorDTO> comunas) {
		filtrosPanel.comunaBox.setVisible(true);
		filtrosPanel.comunaBox.clear();
		filtrosPanel.comunaBox.addItem("todas", "-1");
		for (SectorDTO s : comunas) {
			filtrosPanel.comunaBox.addItem(s.getSector(), s.getIdSector() + "");
		}
	}

	@Override
	public void setSelectedRegion(int regionId) {
		for (int i = 0; i < filtrosPanel.regionBox.getItemCount(); i++) {
			if (Integer.parseInt(filtrosPanel.regionBox.getValue(i)) == regionId) {
				filtrosPanel.regionBox.setItemSelected(i, true);
			}
		}
	}

	@Override
	public void setSelectedComuna(int comunaId) {
		for (int i = 0; i < filtrosPanel.comunaBox.getItemCount(); i++) {
			if (Integer.parseInt(filtrosPanel.comunaBox.getValue(i)) == comunaId) {
				filtrosPanel.comunaBox.setItemSelected(i, true);
			}
		}
	}
	
	@Override
	public void setEstadosActividad(ArrayList<EstadoAgendaDTO> estados) {
		estadoCheckBoxs.clear();
		filtrosPanel.estadosPanel.clear();
		for (EstadoAgendaDTO ea : estados) {
			CheckBox cb = new CheckBox(ea.getEstado());
			estadoCheckBoxs.put(ea.getId(), cb);
			filtrosPanel.estadosPanel.add(cb);
		}
	}
	
	@Override
	public void setColumnWidth(int column, String width){
		if(width.equals("0px")){
			dataGrid.clearColumnWidth(column);
		}else{
			dataGrid.setColumnWidth(column, width);
		}
		//dataGrid.setColumnWidth(column, width);
	}

	private void bind() {
		buildGrid();
		dataGrid.setHeaderBuilder(new CustomHeaderBuilder());
		sincronizacionItem.setVisible(false);
		informacionItem.setVisible(false);
		formItem.setVisible(false);

		pager.setDisplay(dataGrid);

		dataGrid.setKeyboardPagingPolicy(KeyboardPagingPolicy.CHANGE_PAGE);
		dataGrid.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.BOUND_TO_SELECTION);

		selectionModel = new SingleSelectionModel<ActividadPreviewDTO>(
				ActividadPreviewDTO.KEY_PROVIDER);

		dataGrid.setSelectionModel(selectionModel,
				new CellPreviewEvent.Handler<ActividadPreviewDTO>() {

					@Override
					public void onCellPreview(
							CellPreviewEvent<ActividadPreviewDTO> event) {
						if (!event.getNativeEvent().getType().contains("click")) {
							return;
						}
						selectionModel.setSelected(event.getValue(),
								!selectionModel.isSelected(event.getValue()));
					}
				});

		selectionModel
				.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {

					@Override
					public void onSelectionChange(SelectionChangeEvent event) {
						cursoItem
								.setHTML((selectionModel.getSelectedObject() != null) ? ViewUtils
										.limitarString(selectionModel
												.getSelectedObject()
												.getNombreEstablecimiento(), 40)
										: "");
						sincronizacionItem.setVisible(selectionModel
								.getSelectedObject() != null && sinc);
						informacionItem.setVisible(selectionModel
								.getSelectedObject() != null && info);
						formItem.setVisible(selectionModel.getSelectedObject() != null
								&& form);
						cursoItem.setVisible(sincronizacionItem.isVisible()
								|| informacionItem.isVisible()
								|| formItem.isVisible());

						selectedItem = selectionModel.getSelectedObject();
					}
				});

		dataGrid.addRangeChangeHandler(new RangeChangeEvent.Handler() {

			@Override
			public void onRangeChange(RangeChangeEvent event) {
				presenter.onRangeChange(event.getNewRange());
			}
		});

		filtrosPanel.regionBox.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				presenter.onRegionChange(Integer
						.parseInt(filtrosPanel.regionBox
								.getValue(filtrosPanel.regionBox
										.getSelectedIndex())));
			}
		});

		filtrosPanel.cancelarButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				presenter.onCancelarFiltroClick();
				filtrosDialogBox.hide();
			}
		});

		filtrosPanel.aplicarButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				ActividadesPlace ap = new ActividadesPlace();
				ap.setActividadesContintencia(true);
				ap.setActividadesContintenciaInhabilitante(true);
				/*ap.setActividadesContintencia(filtrosPanel.problemasBox
						.getValue());
				ap.setActividadesContintenciaInhabilitante(filtrosPanel.problemasHinabilitantesBox
						.getValue());
						*/
				ap.setActividadesMaterialContintencia(filtrosPanel.contingenciaBox
						.getValue());
				ap.setActividadesSincronizadas(filtrosPanel.sincronizacionTotalBox
						.getValue());
				ap.setActividadesParcialmenteSincronizadas(filtrosPanel.sincronizacionParcialBox
						.getValue());
				ap.setActividadesNoSincronizadas(filtrosPanel.sincronizadasNulaBox
						.getValue());
				if (filtrosPanel.regionBox.getValue(filtrosPanel.regionBox
						.getSelectedIndex()) != "-1") {
					ap.setRegionId(Integer.parseInt(filtrosPanel.regionBox
							.getValue(filtrosPanel.regionBox.getSelectedIndex())));
				}
				if (filtrosPanel.comunaBox.isVisible()
						&& filtrosPanel.comunaBox
								.getValue(filtrosPanel.comunaBox
										.getSelectedIndex()) != "-1") {
					ap.setComunaId(Integer.parseInt(filtrosPanel.comunaBox
							.getValue(filtrosPanel.comunaBox.getSelectedIndex())));
				}
				ArrayList<Integer> es = new ArrayList<Integer>();
				for (Entry<Integer, CheckBox> e : estadoCheckBoxs.entrySet()) {
					if (e.getValue().getValue()) {
						es.add(e.getKey());
					}
				}
				if (es.size() > 0) {
					ap.setEstadosSeleccionados(es);
				}
				filtrosDialogBox.hide();
				presenter.goTo(ap);
			}
		});
	}

	private void buildGrid() {

		int i = -1;
		rbdColumn = new Column<ActividadPreviewDTO, String>(
				new TextCell()) {

			@Override
			public String getValue(ActividadPreviewDTO o) {
				return o.getRbd();
			}
		};
		rbdColumn.setSortable(false);
		dataGrid.addColumn(rbdColumn);
		dataGrid.setColumnWidth(i=i+1, 60, Unit.PX);
		
		pisaColumn = new Column<ActividadPreviewDTO, String>(
				new TextCell()) {

			@Override
			public String getValue(ActividadPreviewDTO o) {
				return o.getCodigoPisa();
			}
		};
		pisaColumn.setSortable(false);
		dataGrid.addColumn(pisaColumn);
		dataGrid.setColumnWidth(i=i+1, 90, Unit.PX);
		
		establecimientoColumn = new Column<ActividadPreviewDTO, String>(
				new TextCell()) {

			@Override
			public String getValue(ActividadPreviewDTO o) {
				return o.getNombreEstablecimiento();
			}
		};
		establecimientoColumn.setSortable(false);
		dataGrid.addColumn(establecimientoColumn);
		dataGrid.setColumnWidth(i=i+1, 270, Unit.PX);
		

		cursoColumn = new Column<ActividadPreviewDTO, String>(
				new TextCell()) {

			@Override
			public String getValue(ActividadPreviewDTO o) {
				return o.getCurso();
			}
		};
		cursoColumn.setSortable(false);
		dataGrid.addColumn(cursoColumn);
		dataGrid.setColumnWidth(i=i+1, 80, Unit.PX);

		tipoColumn = new Column<ActividadPreviewDTO, String>(
				new TextCell()) {

			@Override
			public String getValue(ActividadPreviewDTO o) {
				return o.getTipoEstablecimiento();
			}
		};
		tipoColumn.setSortable(false);
		dataGrid.addColumn(tipoColumn);
		dataGrid.setColumnWidth(i=i+1, 95, Unit.PX);

		estadoColumn = new Column<ActividadPreviewDTO, String>(
				new TextCell()) {

			@Override
			public String getValue(ActividadPreviewDTO o) {
				return o.getEstadoAgenda();
			}
		};
		estadoColumn.setSortable(false);
		dataGrid.addColumn(estadoColumn);
		dataGrid.setColumnWidth(i=i+1, 150, Unit.PX);
		
		alumnosTotalColumn = new Column<ActividadPreviewDTO, Number>(
				new NumberCell()) {

			@Override
			public Number getValue(ActividadPreviewDTO o) {
				return o.getAlumnosTotales();
			}
		};
		alumnosTotalColumn.setSortable(false);
		dataGrid.addColumn(alumnosTotalColumn);
		dataGrid.setColumnWidth(i=i+1, 80, Unit.PX);
		
		porcentajeAsistenciaColumn = new Column<ActividadPreviewDTO, String>(new TextCell()) {

			@Override
			public String getValue(ActividadPreviewDTO o) {
				if(o.getAlumnosTotales()!=null && o.getAlumnosEvaluados()!=null && o.getAlumnosTotales()>0){
					double x = 100*(((double) o.getAlumnosEvaluados())/((double) o.getAlumnosTotales()));
					String p = x+"";
					if(p.length()>4){
						p = p.substring(0,4);
					}
					if(p.endsWith(".")){
						p = p.substring(0,p.length()-1);
					}
					return p + "%";
					
				}else{
					return "0%";
				}
			}
		};
		porcentajeAsistenciaColumn.setSortable(false);
		dataGrid.addColumn(porcentajeAsistenciaColumn);
		dataGrid.setColumnWidth(i=i+1, 80, Unit.PX);
		
		alumnosEvaluadosColumn = new Column<ActividadPreviewDTO, Number>(
				new NumberCell()) {

			@Override
			public Number getValue(ActividadPreviewDTO o) {
				return o.getAlumnosEvaluados();
			}
		};
		alumnosEvaluadosColumn.setSortable(false);
		dataGrid.addColumn(alumnosEvaluadosColumn);
		dataGrid.setColumnWidth(i=i+1, 90, Unit.PX);
/*
		alumnosSincronizadosColumn = new Column<ActividadPreviewDTO, Number>(
				new NumberCell()) {

			@Override
			public Number getValue(ActividadPreviewDTO o) {
				return (o.getAlumnosSincronizados()!=null)?o.getAlumnosSincronizados():0;
			}
		};
		alumnosSincronizadosColumn.setSortable(false);
		dataGrid.addColumn(alumnosSincronizadosColumn);
		dataGrid.setColumnWidth(i=i+1, 90, Unit.PX);
*/
		cuestionarioEntregadosColumn = new Column<ActividadPreviewDTO, Number>(
				new NumberCell()) {

			@Override
			public Number getValue(ActividadPreviewDTO o) {
				return (o.getCuestionariosPadresApoderadosEntregados()!=null)?o.getCuestionariosPadresApoderadosEntregados():0;
			}
		};
		cuestionarioEntregadosColumn.setSortable(false);
		dataGrid.addColumn(cuestionarioEntregadosColumn);
		dataGrid.setColumnWidth(i=i+1, 90, Unit.PX);
		
		cuestionarioRecibidosColumn = new Column<ActividadPreviewDTO, Number>(
				new NumberCell()) {

			@Override
			public Number getValue(ActividadPreviewDTO o) {
				return (o.getCuestionariosPadresApoderadosRecibidos()!=null)?o.getCuestionariosPadresApoderadosRecibidos():0;
			}
		};
		cuestionarioRecibidosColumn.setSortable(false);
		dataGrid.addColumn(cuestionarioRecibidosColumn);
		dataGrid.setColumnWidth(i=i+1, 90, Unit.PX);

		cuestionarioRecibidosAplicadosColumn = new Column<ActividadPreviewDTO, Number>(
				new NumberCell()) {

			@Override
			public Number getValue(ActividadPreviewDTO o) {
				return (o.getCuestionariosPadresApoderadosRecibidosAplicados()!=null)?o.getCuestionariosPadresApoderadosRecibidosAplicados():0;
			}
		};
		cuestionarioRecibidosAplicadosColumn.setSortable(false);
		dataGrid.addColumn(cuestionarioRecibidosAplicadosColumn);
		dataGrid.setColumnWidth(i=i+1, 90, Unit.PX);

		pcTotalColumn = new Column<ActividadPreviewDTO, Number>(new NumberCell()) {

			@Override
			public Number getValue(ActividadPreviewDTO o) {
				return (o.getTotalPc()!=null)?o.getTotalPc():0;
			}
		};
		pcTotalColumn.setSortable(false);
		dataGrid.addColumn(pcTotalColumn);
		dataGrid.setColumnWidth(i=i+1, 90, Unit.PX);
		
		contingenciaColumn = new Column<ActividadPreviewDTO, String>(
				new ImageCell()) {

			@Override
			public String getValue(ActividadPreviewDTO o) {
				if (o.getContingencia()) {
					return "images/warning.png";
				} else {
					return "images/blank.png";
				}
			}
		};
		contingenciaColumn.setSortable(false);
		dataGrid.addColumn(contingenciaColumn);
		dataGrid.setColumnWidth(i=i+1, 100, Unit.PX);

		contingenciaLimitanteColumn = new Column<ActividadPreviewDTO, String>(
				new ImageCell()) {

			@Override
			public String getValue(ActividadPreviewDTO o) {
				if (o.getContingenciaLimitante()) {
					return "images/warning.png";
				} else {
					return "images/blank.png";
				}
			}
		};
		contingenciaLimitanteColumn.setSortable(false);
		dataGrid.addColumn(contingenciaLimitanteColumn);
		dataGrid.setColumnWidth(i=i+1, 90, Unit.PX);

		regionColumn = new Column<ActividadPreviewDTO, String>(
				new TextCell()) {

			@Override
			public String getValue(ActividadPreviewDTO o) {
				if (o.getRegion().startsWith("Región")) {
					return o.getRegion().substring(6);
				}
				return o.getRegion();
			}
		};
		regionColumn.setSortable(false);
		dataGrid.addColumn(regionColumn);
		dataGrid.setColumnWidth(i=i+1, 140, Unit.PX);

		comunaColumn = new Column<ActividadPreviewDTO, String>(
				new TextCell()) {

			@Override
			public String getValue(ActividadPreviewDTO o) {
				return o.getComuna();
			}
		};
		comunaColumn.setSortable(false);
		dataGrid.addColumn(comunaColumn);
		dataGrid.setColumnWidth(i=i+1, 120, Unit.PX);

		docColumn = new Column<ActividadPreviewDTO, DocumentoDTO>(
				new HyperTextCell()) {

			@Override
			public DocumentoDTO getValue(ActividadPreviewDTO o) {
				return o.getDocumento();
			}
		};
		docColumn.setSortable(false);
		dataGrid.addColumn(docColumn);
		dataGrid.setColumnWidth(i=i+1, 250, Unit.PX);
		
		examinadorColumn = new Column<ActividadPreviewDTO, String>(
				new TextCell()) {

			@Override
			public String getValue(ActividadPreviewDTO o) {
				return (o.getNombreExaminador()!=null)?o.getNombreExaminador():"";
			}
		};
		examinadorColumn.setSortable(false);
		dataGrid.addColumn(examinadorColumn);
		dataGrid.setColumnWidth(i=i+1, 220, Unit.PX);
		
		supervisorColumn = new Column<ActividadPreviewDTO, String>(
				new TextCell()) {

			@Override
			public String getValue(ActividadPreviewDTO o) {
				return (o.getNombreSupervisor()!=null)?o.getNombreSupervisor():"";
			}
		};
		supervisorColumn.setSortable(false);
		dataGrid.addColumn(supervisorColumn);
		dataGrid.setColumnWidth(i=i+1, 220, Unit.PX);
		
		nombreContactoColumn = new Column<ActividadPreviewDTO, String>(
				new TextCell()) {

			@Override
			public String getValue(ActividadPreviewDTO o) {
				return (o.getNombreContacto()!=null)?o.getNombreContacto():"";
			}
		};
		nombreContactoColumn.setSortable(false);
		dataGrid.addColumn(nombreContactoColumn);
		dataGrid.setColumnWidth(i=i+1, 200, Unit.PX);
		
		telefonoContactoColumn = new Column<ActividadPreviewDTO, String>(
				new TextCell()) {

			@Override
			public String getValue(ActividadPreviewDTO o) {
				return (o.getTelefonoContacto()!=null)?o.getTelefonoContacto():"";
			}
		};
		telefonoContactoColumn.setSortable(false);
		dataGrid.addColumn(telefonoContactoColumn);
		dataGrid.setColumnWidth(i=i+1, 140, Unit.PX);
		
		emailContactoColumn = new Column<ActividadPreviewDTO, String>(
				new TextCell()) {

			@Override
			public String getValue(ActividadPreviewDTO o) {
				return (o.getMailContacto()!=null)?o.getMailContacto():"";
			}
		};
		emailContactoColumn.setSortable(false);
		dataGrid.addColumn(emailContactoColumn);
		dataGrid.setColumnWidth(i=i+1, 210, Unit.PX);

	}
}
