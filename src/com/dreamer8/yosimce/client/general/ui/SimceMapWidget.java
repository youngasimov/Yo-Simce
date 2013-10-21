package com.dreamer8.yosimce.client.general.ui;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.shared.dto.CentroOperacionDTO;
import com.google.gwt.maps.client.MapOptions;
import com.google.gwt.maps.client.MapTypeId;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.base.LatLngBounds;
import com.google.gwt.maps.client.base.Point;
import com.google.gwt.maps.client.base.Size;
import com.google.gwt.maps.client.events.click.ClickMapEvent;
import com.google.gwt.maps.client.events.click.ClickMapHandler;
import com.google.gwt.maps.client.overlays.InfoWindow;
import com.google.gwt.maps.client.overlays.InfoWindowOptions;
import com.google.gwt.maps.client.overlays.Marker;
import com.google.gwt.maps.client.overlays.MarkerImage;
import com.google.gwt.maps.client.overlays.MarkerOptions;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class SimceMapWidget extends Composite{
	
	public interface MarkerHandler{
		void onMarkerSelected(int id);
	}
	
	public interface MapHandler{
		void onMapClicked();
	}
	
	private SimpleLayoutPanel mapPanel;
	private MapWidget map;
	private HashMap<Integer,Marker> markers;
	
	private boolean calculateBoundaries;
	
	private MarkerHandler markerHandler;
	private MapHandler mapHandler;
	private InfoWindow iw;
	
	public SimceMapWidget() {
		
		mapPanel = new SimpleLayoutPanel();
		initWidget(mapPanel);
		mapPanel.setSize("100%", "100%");
		markers = new HashMap<Integer, Marker>();
		calculateBoundaries = true;
	}
	
	public void setMarkerHandler(MarkerHandler handler){
		this.markerHandler = handler;
	}
	
	public void setMapHandler(MapHandler handler){
		this.mapHandler = handler;
	}
	
	public boolean isLoaded(){
		return map != null;
	}
	
	public void showInfoWindow(Widget w, double lat, double lng){
		iw.setContent(w);
		iw.setPosition(LatLng.newInstance(lat, lng));
		iw.open(map);
		
	}
	
	public void clearInfoWindow(){
		iw.close();
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
    		InfoWindowOptions iwo = InfoWindowOptions.newInstance();
    		iwo.setDisableAutoPan(false);
    		iwo.setMaxWidth(100);
    		iw = InfoWindow.newInstance(iwo);
		}
		mapPanel.setWidget(map);
		if(mapHandler!=null){
			map.addClickHandler(new ClickMapHandler() {
				
				@Override
				public void onEvent(ClickMapEvent event) {
					mapHandler.onMapClicked();
				}
			});
		}
	}
	
	public void clearMarkers(){
		for(Marker m:markers.values()){
			m.clear();
		}
		markers.clear();
		map.setZoom(16);
		calculateBoundaries = true;
		iw.close();
	}
	
	public void updateCentros(int evento, ArrayList<CentroOperacionDTO> centros){
		double minLat = 0;
		double minLng = 0;
		double maxLat = 0;
		double maxLng = 0;
		MarkerOptions options;
		Marker m;
		for(final CentroOperacionDTO c:centros){
			if(minLat == 0 || minLat>c.getLatitud()){
				minLat = c.getLatitud();
			}
			if(maxLat == 0 || maxLat<c.getLatitud()){
				maxLat = c.getLatitud();
			}
			if(minLng == 0 || minLng>c.getLongitud()){
				minLng = c.getLongitud();
			}
			if(maxLng == 0 || maxLng<c.getLongitud()){
				maxLng = c.getLongitud();
			}
			
			if(markers.containsKey(c.getId())){
				markers.get(c.getId()).setIcon(getIcon(evento,c));
			}else{			
				LatLng center = LatLng.newInstance(c.getLatitud(), c.getLongitud());
			    options = MarkerOptions.newInstance();
			    options.setPosition(center);
			    options.setTitle(c.getNombre());
			    options.setIcon(getIcon(evento,c));
			    m = Marker.newInstance(options);
			    m.setMap(map);
			    if(markerHandler!=null){
				    m.addClickHandler(new ClickMapHandler() {
						
						@Override
						public void onEvent(ClickMapEvent event) {
							markerHandler.onMarkerSelected(c.getId());
						}
					});
			    }
			    markers.put(c.getId(), m);
			}
		}
		if(calculateBoundaries){
			map.fitBounds(LatLngBounds.newInstance(LatLng.newInstance(minLat, minLng), LatLng.newInstance(maxLat, maxLng)));
			calculateBoundaries = false;
		}
	}
	
	private MarkerImage getIcon(int evento,CentroOperacionDTO centro){
		int material = 0;
		int materialTotal= centro.getEnCentro()+centro.getEnEstablecimiento()+centro.getEnImprenta()+centro.getEnMinisterio();
		
		if(materialTotal == 0){
			return MarkerImage.newInstance("images/iconos_mapa.png", Size.newInstance(31, 30), Point.newInstance(0, 30*6),Point.newInstance(10, 30));
		}
		if(evento == CentroControlView.EVENT_IMPRENTA){
			material = centro.getEnImprenta();
		}else if(evento == CentroControlView.EVENT_CENTRO){
			material = centro.getEnCentro();
		}else if(evento == CentroControlView.EVENT_ESTABLECIMIENTO){
			material = centro.getEnEstablecimiento();
		}else if(evento == CentroControlView.EVENT_MINISTERIO){
			material = centro.getEnMinisterio();
		}else{
			return MarkerImage.newInstance("images/iconos_mapa.png", Size.newInstance(31, 30), Point.newInstance(0, 30*6),Point.newInstance(10, 30));
		}
		double p = 100*((double)material/(double)materialTotal);
		if(p<17){
			return MarkerImage.newInstance("images/iconos_mapa.png", Size.newInstance(31, 30), Point.newInstance(0, 30*5),Point.newInstance(10, 30));
		}else if(p>=17 && p<34){
			return MarkerImage.newInstance("images/iconos_mapa.png", Size.newInstance(31, 30), Point.newInstance(0, 30*4),Point.newInstance(10, 30));
		}else if(p>=34 && p<50){
			return MarkerImage.newInstance("images/iconos_mapa.png", Size.newInstance(31, 30), Point.newInstance(0, 30*3),Point.newInstance(10, 30));
		}else if(p>=50 && p<67){
			return MarkerImage.newInstance("images/iconos_mapa.png", Size.newInstance(31, 30), Point.newInstance(0, 30*2),Point.newInstance(10, 30));
		}else if(p>=67 && p<84){
			return MarkerImage.newInstance("images/iconos_mapa.png", Size.newInstance(31, 30), Point.newInstance(0, 30),Point.newInstance(10, 30));
		}else if(p>=84){
			return MarkerImage.newInstance("images/iconos_mapa.png", Size.newInstance(31, 30), Point.newInstance(0, 0),Point.newInstance(10, 30));
		}
		return MarkerImage.newInstance("images/iconos_mapa.png", Size.newInstance(31, 30), Point.newInstance(0, 30*6),Point.newInstance(10, 30));
		
	}

}
