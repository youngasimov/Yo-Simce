package com.dreamer8.yosimce.client.actividad.ui;

import com.dreamer8.yosimce.client.ui.ImageButton;
import com.dreamer8.yosimce.client.ui.OverMenuBar;
import com.dreamer8.yosimce.client.ui.PlaceHolderTextBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.PieChart;
import com.googlecode.gwt.charts.client.corechart.PieChartOptions;

public class AvanceRevisionViewD extends Composite implements
		AvanceRevisionView {

	private static AvanceRevisionViewDUiBinder uiBinder = GWT
			.create(AvanceRevisionViewDUiBinder.class);

	interface AvanceRevisionViewDUiBinder extends
			UiBinder<Widget, AvanceRevisionViewD> {
	}

	@UiField
	OverMenuBar menu;
	@UiField
	MenuItem menuItem;
	@UiField
	SimplePanel graphPanel;
	@UiField
	ImageButton revisadaButton;
	@UiField
	Button noRevisadaButton;
	@UiField
	PlaceHolderTextBox codigoBox;
	@UiField
	CheckBox enterReviewBox;

	private AvanceRevisionPresenter presenter;

	private boolean chartApiLoaded;
	private PieChart pieChart;

	public AvanceRevisionViewD() {
		initWidget(uiBinder.createAndBindUi(this));
		chartApiLoaded = false;
		menu.insertSeparator(1);
		menu.setOverItem(menuItem);
		menu.setOverCommand(new Scheduler.ScheduledCommand() {

			@Override
			public void execute() {
				presenter.toggleMenu();
			}
		});

		ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
		chartLoader.loadApi(new Runnable() {
			@Override
			public void run() {
				chartApiLoaded = true;
			}
		});
	}
	
	@UiHandler("revisadaButton")
	void onRevisarButtonClick(ClickEvent event){
		presenter.materialProcesado(codigoBox.getValue());
	}
	
	@UiHandler("codigoBox")
	void onCodigoBoxKeyUp(KeyUpEvent event){
		if(enterReviewBox.getValue() && event.getNativeKeyCode() == KeyCodes.KEY_ENTER){
			presenter.materialProcesado(codigoBox.getValue());
		}
	}
	
	@UiHandler("enterReviewBox")
	void onNoRevisarButtonClick(ClickEvent event){
		presenter.materialNoProcesado(codigoBox.getValue());
	}

	@Override
	public void setPresenter(AvanceRevisionPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void updateGraph(int total, int revisados) {
		if(!chartApiLoaded){
			return;
		}
		
		if (pieChart == null) {
			pieChart = new PieChart();
			pieChart.setSize("100px", "100px");
			graphPanel.setWidget(pieChart);
		}
		DataTable dataTable = DataTable.create();
		dataTable.addColumn(ColumnType.STRING, "Avance");
		dataTable.addColumn(ColumnType.NUMBER, "Porcentaje avance");
		dataTable.addRows(4);
		dataTable.setValue(0, 0, "Procesados");
		dataTable.setValue(1, 0, "Por procesar");
		dataTable.setValue(0, 1, revisados);
		dataTable.setValue(1, 1, total-revisados);

		// Set options
		PieChartOptions options = PieChartOptions.create();

		options.setFontName("Tahoma");
		options.setIs3D(false);
		options.setTitle("Avance de procesamiento de material");

		// Draw the chart
		pieChart.draw(dataTable,options);
	}

	@Override
	public void clearBox() {
		codigoBox.setValue("");
	}

}
