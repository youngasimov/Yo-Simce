package com.dreamer8.yosimce.client.general.ui;

import java.util.ArrayList;

import com.dreamer8.yosimce.client.ui.OverMenuBar;
import com.dreamer8.yosimce.shared.dto.EstadoActividadItemDTO;
import com.dreamer8.yosimce.shared.dto.EstadoMaterialItemDTO;
import com.dreamer8.yosimce.shared.dto.SectorDTO;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.PieChart;
import com.googlecode.gwt.charts.client.corechart.PieChartOptions;

public class GraficosViewD extends Composite implements GraficosView {

	private static GraficosViewDUiBinder uiBinder = GWT
			.create(GraficosViewDUiBinder.class);

	interface GraficosViewDUiBinder extends UiBinder<Widget, GraficosViewD> {
	}

	interface Styles extends CssResource {

		String full();

	}

	@UiField
	Styles style;
	@UiField
	OverMenuBar menu;
	@UiField
	MenuItem menuItem;
	@UiField
	HTMLPanel graphPanel;
	@UiField
	SimplePanel graficoEstadoMaterialPanel;
	@UiField
	SimplePanel graficoEstadoActividadPanel;
	@UiField(provided = true)
	DataGrid<Item> estadoMaterialDataGrid;
	@UiField(provided = true)
	DataGrid<Item> estadoActividadDataGrid;
	@UiField
	DivElement graphMaterialDiv;
	@UiField
	DivElement graphActividadDiv;
	@UiField
	ListBox regionBox;
	@UiField
	ListBox comunaBox;
	@UiField
	RadioButton agruparColegioButton;
	@UiField
	RadioButton agruparCursoButton;

	private GraficosPresenter presenter;
	private EstadoMaterialItemDTO estadoMaterial;
	private EstadoActividadItemDTO estadoActividad;
	private boolean chartApiLoaded;
	private PieChart materialPieChart;
	private PieChart actividadPieChart;

	public GraficosViewD() {
		chartApiLoaded = false;
		estadoMaterialDataGrid = new DataGrid<Item>();
		estadoActividadDataGrid = new DataGrid<Item>();
		initWidget(uiBinder.createAndBindUi(this));
		agruparColegioButton.setValue(true);
		menu.insertSeparator(1);
		menu.setOverItem(menuItem);
		menu.setOverCommand(new Scheduler.ScheduledCommand() {

			@Override
			public void execute() {
				presenter.toggleMenu();
			}
		});

		buildMaterialGrid();
		buildActividadGrid();
		ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
		chartLoader.loadApi(new Runnable() {
			@Override
			public void run() {

				chartApiLoaded = true;
				if (estadoMaterial != null) {
					buildMaterialGraph();
				}
				if (estadoActividad != null) {
					buildActividadGraph();
				}

			}
		});
		Window.addResizeHandler(new ResizeHandler() {

			@Override
			public void onResize(ResizeEvent event) {
				if (event.getWidth() < 1000) {
					graphPanel.addStyleName(style.full());
				} else {
					graphPanel.removeStyleName(style.full());
				}
				if(actividadPieChart!=null){
					actividadPieChart.redraw();
				}
				if(materialPieChart!=null){
					materialPieChart.redraw();
				}
			}
		});
	}

	@UiHandler("agruparColegioButton")
	void onGranularidadColegioChange(ValueChangeEvent<Boolean> event){
		if(agruparColegioButton.getValue()){
			presenter.onGranularidadActividadChange(false);
		}
	}
	
	@UiHandler("agruparCursoButton")
	void onGranularidadCursoChange(ValueChangeEvent<Boolean> event){
		if(agruparCursoButton.getValue()){
			presenter.onGranularidadActividadChange(true);
		}
	}
	
	@UiHandler("regionBox")
	void onRegionBoxChange(ChangeEvent event) {
		int regionId = Integer.parseInt(regionBox.getValue(regionBox
				.getSelectedIndex()));
		presenter.onRegionChange(regionId);
	}

	@UiHandler("comunaBox")
	void onComunaBoxChange(ChangeEvent event) {
		int comunaId = Integer.parseInt(comunaBox.getValue(comunaBox
				.getSelectedIndex()));
		presenter.onComunaChange(comunaId);
	}

	@Override
	public void setEstadoMaterialVisivility(boolean visible) {
		if (visible) {
			graphMaterialDiv.setPropertyString("style", "display: block;");
		} else {
			graphMaterialDiv.setPropertyString("style", "display: none;");
		}
	}

	@Override
	public void setEstadoActividadVisivility(boolean visible) {
		if (visible) {
			graphActividadDiv.setPropertyString("style", "display: block;");
		} else {
			graphActividadDiv.setPropertyString("style", "display: none;");
		}
	}

	@Override
	public void setPresenter(GraficosPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void setRegiones(ArrayList<SectorDTO> regiones) {
		regionBox.clear();
		regionBox.addItem("todas las regiones", "-1");
		if(regionBox!=null){
			for (SectorDTO sector : regiones) {
				regionBox.addItem(sector.getSector(), sector.getIdSector() + "");
			}
		}
	}

	@Override
	public void setComunas(ArrayList<SectorDTO> comunas) {
		comunaBox.clear();
		comunaBox.addItem("todas las comunas", "-1");
		if(comunas!=null){
			for (SectorDTO sector : comunas) {
				comunaBox.addItem(sector.getSector(), sector.getIdSector() + "");
			}
		}
	}

	@Override
	public void setMaterialData(EstadoMaterialItemDTO estadoMaterial) {
		this.estadoMaterial = estadoMaterial;
		ArrayList<GraficosView.Item> materiales = new ArrayList<GraficosView.Item>();
		double total = 0;
		materiales.add(new Item("Imprenta", estadoMaterial.getTotalImprenta(),
				0));
		total += estadoMaterial.getTotalImprenta();
		materiales.add(new Item("Centro operación", estadoMaterial
				.getTotalCentro(), 0));
		total += estadoMaterial.getTotalCentro();
		materiales.add(new Item("Establecimiento", estadoMaterial
				.getTotalEstablecimiento(), 0));
		total += estadoMaterial.getTotalEstablecimiento();
		materiales
				.add(new Item("Captura", estadoMaterial.getTotalCaptura(), 0));
		total += estadoMaterial.getTotalCaptura();
		materiales.add(new Item("Bodega", estadoMaterial.getTotalBodega(), 0));
		total += estadoMaterial.getTotalBodega();
		materiales
				.add(new Item("Agencia", estadoMaterial.getTotalAgencia(), 0));
		total += estadoMaterial.getTotalAgencia();
		for (Item item : materiales) {
			item.porcentaje = item.cantidad / total;
		}

		estadoMaterialDataGrid.setPageSize(materiales.size());
		estadoMaterialDataGrid.setRowData(materiales);
		estadoMaterialDataGrid.setRowCount(materiales.size());
		estadoMaterialDataGrid.setVisibleRange(0, materiales.size());

		if (this.chartApiLoaded) {
			buildMaterialGraph();
		}

	}

	@Override
	public void setActividadData(EstadoActividadItemDTO estadoActividad) {
		this.estadoActividad = estadoActividad;
		ArrayList<GraficosView.Item> actividades = new ArrayList<GraficosView.Item>();
		double total = 0;
		actividades.add(new Item("Por confirmar", estadoActividad
				.getTotalPorConfirmar(), 0));
		total += estadoActividad.getTotalPorConfirmar();
		actividades.add(new Item("Confirmado", estadoActividad
				.getTotalConfirmado(), 0));
		total += estadoActividad.getTotalConfirmado();
		actividades.add(new Item("Confirmado con cambios", estadoActividad
				.getTotalConfirmadoConCambios(), 0));
		total += estadoActividad.getTotalConfirmadoConCambios();
		actividades.add(new Item("Realizada", estadoActividad
				.getTotalRealizada(), 0));
		total += estadoActividad.getTotalRealizada();
		actividades.add(new Item("Anulada", estadoActividad.getTotalAnulada(),
				0));
		total += estadoActividad.getTotalAnulada();
		actividades.add(new Item("Sin información", estadoActividad
				.getTotalSinInformacion(), 0));
		total += estadoActividad.getTotalSinInformacion();
		for (Item item : actividades) {
			item.porcentaje = item.cantidad / total;
		}
		estadoActividadDataGrid.setPageSize(actividades.size());
		estadoActividadDataGrid.setRowData(actividades);
		estadoActividadDataGrid.setRowCount(actividades.size());
		estadoActividadDataGrid.setVisibleRange(0, actividades.size());

		if (this.chartApiLoaded) {
			buildActividadGraph();
		}
	}

	private void buildMaterialGrid() {
		Column<Item, String> column = new Column<GraficosView.Item, String>(
				new TextCell()) {

			@Override
			public String getValue(Item o) {
				return o.item;
			}
		};
		estadoMaterialDataGrid.addColumn(column, "Estado material");

		column = new Column<GraficosView.Item, String>(new TextCell()) {

			@Override
			public String getValue(Item o) {
				return o.cantidad + "";
			}
		};
		estadoMaterialDataGrid.addColumn(column, "Cantidad");
		column = new Column<GraficosView.Item, String>(new TextCell()) {

			@Override
			public String getValue(Item o) {
				String value = ((o.porcentaje * 100)+"").substring(0,4);
				return value + "%";
			}
		};
		estadoMaterialDataGrid.addColumn(column, "porcentaje");
		
		estadoMaterialDataGrid.setPageSize(10);
		estadoMaterialDataGrid.setRowCount(0);
		estadoMaterialDataGrid.setVisibleRange(0,10);
	}

	private void buildActividadGrid() {
		Column<Item, String> column = new Column<GraficosView.Item, String>(
				new TextCell()) {

			@Override
			public String getValue(Item o) {
				return o.item;
			}
		};
		estadoActividadDataGrid.addColumn(column, "Estado actividad");
		column = new Column<GraficosView.Item, String>(new TextCell()) {

			@Override
			public String getValue(Item o) {
				return o.cantidad + "";
			}
		};
		estadoActividadDataGrid.addColumn(column, "Cantidad");
		column = new Column<GraficosView.Item, String>(new TextCell()) {

			@Override
			public String getValue(Item o) {
				return (o.porcentaje * 100) + "%";
			}
		};
		estadoActividadDataGrid.addColumn(column, "porcentaje");
		
		estadoActividadDataGrid.setPageSize(10);
		estadoActividadDataGrid.setRowCount(0);
		estadoActividadDataGrid.setVisibleRange(0, 10);
	}

	private void buildMaterialGraph() {
		if (this.estadoMaterial == null) {
			return;
		}
		if (materialPieChart == null) {
			materialPieChart = new PieChart();
			materialPieChart.setSize("100%", "400px");
			materialPieChart.setTitle("Estado Materiales");
			graficoEstadoMaterialPanel.setWidget(materialPieChart);
		}
		DataTable dataTable = DataTable.create();
		dataTable.addColumn(ColumnType.STRING, "Estado material");
		dataTable.addColumn(ColumnType.NUMBER, "Cantidad");
		dataTable.addRows(4);
		dataTable.setValue(0, 0, "Imprenta");
		dataTable.setValue(1, 0, "Centro operaciones");
		dataTable.setValue(2, 0, "Establecimiento");
		dataTable.setValue(3, 0, "Captura");
		dataTable.setValue(4, 0, "Bodega");
		dataTable.setValue(5, 0, "Agencia");
		dataTable.setValue(0, 1, estadoMaterial.getTotalImprenta());
		dataTable.setValue(1, 1, estadoMaterial.getTotalCentro());
		dataTable.setValue(2, 1, estadoMaterial.getTotalEstablecimiento());
		dataTable.setValue(3, 1, estadoMaterial.getTotalCaptura());
		dataTable.setValue(4, 1, estadoMaterial.getTotalBodega());
		dataTable.setValue(5, 1, estadoMaterial.getTotalAgencia());

		// Set options
		PieChartOptions options = PieChartOptions.create();

		options.setFontName("Tahoma");
		options.setIs3D(false);
		options.setTitle("Estado Materiales");

		// Draw the chart
		materialPieChart.draw(dataTable, options);
	}

	private void buildActividadGraph() {
		if (this.estadoActividad == null) {
			return;
		}
		if (actividadPieChart == null) {
			actividadPieChart = new PieChart();
			actividadPieChart.setSize("100%", "400px");
			graficoEstadoActividadPanel.setWidget(actividadPieChart);
		}
		DataTable dataTable = DataTable.create();
		dataTable.addColumn(ColumnType.STRING, "Estado actividad");
		dataTable.addColumn(ColumnType.NUMBER, "Cantidad");
		dataTable.addRows(4);
		dataTable.setValue(0, 0, "Por confirmar");
		dataTable.setValue(1, 0, "Confirmada");
		dataTable.setValue(2, 0, "Confirmada con cambios");
		dataTable.setValue(3, 0, "Realizada");
		dataTable.setValue(4, 0, "Anulada");
		dataTable.setValue(5, 0, "Sin información");
		dataTable.setValue(0, 1, estadoActividad.getTotalPorConfirmar());
		dataTable.setValue(1, 1, estadoActividad.getTotalConfirmado());
		dataTable.setValue(2, 1, estadoActividad.getTotalConfirmadoConCambios());
		dataTable.setValue(3, 1, estadoActividad.getTotalRealizada());
		dataTable.setValue(4, 1, estadoActividad.getTotalAnulada());
		dataTable.setValue(5, 1, estadoActividad.getTotalSinInformacion());

		// Set options
		PieChartOptions options = PieChartOptions.create();

		options.setFontName("Tahoma");
		options.setIs3D(false);
		options.setTitle("Estado Actividad");

		// Draw the chart
		actividadPieChart.draw(dataTable,options);
	}
}
