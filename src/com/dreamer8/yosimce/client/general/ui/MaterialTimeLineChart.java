package com.dreamer8.yosimce.client.general.ui;


import com.google.gwt.user.client.ui.SimplePanel;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.LineChart;
import com.googlecode.gwt.charts.client.corechart.LineChartOptions;
import com.googlecode.gwt.charts.client.options.HAxis;
import com.googlecode.gwt.charts.client.options.VAxis;

public class MaterialTimeLineChart {
	
	private LineChart chart;
	private LineChartOptions options;
	
	public MaterialTimeLineChart(SimplePanel panel) {
		options = LineChartOptions.create();
		options.setBackgroundColor("#FFFFFF");
		options.setFontName("Tahoma");
		options.setTitle("Estado materiales");
		options.setHAxis(HAxis.create("Tiempo"));
		options.setVAxis(VAxis.create("% Materiales"));
		chart = new LineChart();
		chart.setSize("100%", "200px");
		panel.setWidget(chart);
	}
	
	public void draw(DataTable data){
		chart.draw(data,options);
	}
	
	public void redraw(){
		chart.redraw();
	}

}
