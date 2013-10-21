package com.dreamer8.yosimce.client.general.ui;

import java.util.ArrayList;

import com.dreamer8.yosimce.shared.dto.CentroOperacionDTO;
import com.google.gwt.dev.util.collect.HashMap;
import com.google.gwt.maps.client.MapOptions;
import com.google.gwt.maps.client.MapTypeId;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.base.LatLngBounds;
import com.google.gwt.maps.client.overlays.Animation;
import com.google.gwt.maps.client.overlays.Marker;
import com.google.gwt.maps.client.overlays.MarkerOptions;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;

public class SimceMapWidget extends Composite{
	
	private SimpleLayoutPanel mapPanel;
	private MapWidget map;
	private HashMap<Integer,Marker> markers;
	
	
	
	public SimceMapWidget() {
		
		mapPanel = new SimpleLayoutPanel();
		initWidget(mapPanel);
		mapPanel.setSize("100%", "100%");
		markers = new HashMap<Integer, Marker>();
	}
	
	public boolean isLoaded(){
		return map != null;
	}
	
	public void createMapIfIsNull(){
		if(map==null){
			MapOptions opts = MapOptions.newInstance();
    	    opts.setZoom(11);
    	    opts.setCenter(LatLng.newInstance(-33.48708447755195, -70.64964294433594));
    	    opts.setMapTypeId(MapTypeId.ROADMAP);
    	    opts.setMapTypeControl(false);
    	    opts.setMaxZoom(16);
    	    opts.setMinZoom(6);
    	    opts.setBackgroundColor("#FFFFFF");
    	    map = new MapWidget(opts);
    		map.setSize("100%", "450px");
		}
		mapPanel.setWidget(map);
	}
	
	public void setCentros(int evento, ArrayList<CentroOperacionDTO> centros){
		ArrayList<LatLngBounds> bounds = new ArrayList<LatLngBounds>(centros.size());
		Marker m;
		MarkerOptions options;
		for(CentroOperacionDTO c:centros){
			bounds.add(LatLngBounds.newInstance(LatLng.newInstance(c.getLatitud()-5, c.getLongitud()-5), LatLng.newInstance(c.getLatitud()+5, c.getLongitud()+5)));
			if(markers.containsKey(c.getId())){
				m = markers.get(c.getId());
			}else{
				LatLng center = LatLng.newInstance(c.getLatitud(), c.getLongitud());
			    options = MarkerOptions.newInstance();
			    options.setPosition(center);
			    options.setTitle(c.getNombre());
			    options.setAnimation(Animation.DROP);
			    m = Marker.newInstance(options);
			    m.setMap(map);
			    markers.put(c.getId(), m);
			}
		}
		
	}
	
	public void updateCentros(int evento, ArrayList<CentroOperacionDTO> centros){
		
	}

}
