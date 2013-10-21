package com.dreamer8.yosimce.client.general.ui;


import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.LineChart;
import com.googlecode.gwt.charts.client.corechart.LineChartOptions;
import com.googlecode.gwt.charts.client.options.HAxis;
import com.googlecode.gwt.charts.client.options.VAxis;

public class MaterialTimeLineChart extends Composite {
	
	private SimpleLayoutPanel chartPanel;
	private LineChart chart;
	private LineChartOptions options;
	
	
	public MaterialTimeLineChart() {
		
		chartPanel = new SimpleLayoutPanel();
		initWidget(chartPanel);
		chartPanel.setSize("100%","250px");
	}
	
	public boolean isLoaded(){
		return chart!=null;
	}
	
	public void createChartIfIsNull(String name){
		if(chart == null){
			options = LineChartOptions.create();
			options.setBackgroundColor("#FFFFFF");
			options.setFontName("Tahoma");
			options.setTitle(name);
			options.setHAxis(HAxis.create("Tiempo"));
			options.setVAxis(VAxis.create("% Materiales"));
			chart = new LineChart();
			chart.setSize("100%", "250px");
		}
		chartPanel.setWidget(chart);
	}
	
	public void draw(DataTable data){
		if(chart==null){
			return;
		}
		chart.draw(data,options);
	}
	
	public void redraw(){
		if(chart==null){
			return;
		}
		chart.redraw();
	}

}
