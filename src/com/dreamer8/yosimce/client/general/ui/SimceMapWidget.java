package com.dreamer8.yosimce.client.general.ui;

import java.util.ArrayList;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.maps.client.MapOptions;
import com.google.gwt.maps.client.MapTypeId;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.overlays.Marker;
import com.google.gwt.user.client.ui.SimplePanel;

public class SimceMapWidget {

	private MapWidget mapWidget;
	private ArrayList<Marker> cosMarkers;
	 
	
	
	public SimceMapWidget(SimplePanel panel) {
		MapOptions opts = MapOptions.newInstance();
	    opts.setZoom(4);
	    opts.setMapTypeId(MapTypeId.ROADMAP);
	    mapWidget = new MapWidget(opts);
	    panel.setWidget(mapWidget);
		mapWidget.setSize("100%", "450px");
	}

}
