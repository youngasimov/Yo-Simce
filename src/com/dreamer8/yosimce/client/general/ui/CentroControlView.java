package com.dreamer8.yosimce.client.general.ui;


import java.util.ArrayList;
import java.util.Date;

import com.dreamer8.yosimce.client.SimcePresenter;
import com.dreamer8.yosimce.shared.dto.CentroOperacionDTO;
import com.dreamer8.yosimce.shared.dto.SectorDTO;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.view.client.ListDataProvider;

public interface CentroControlView extends IsWidget {
	
	public static final int EVENT_IMPRENTA = 1;
	public static final int EVENT_CENTRO = 2;
	public static final int EVENT_ESTABLECIMIENTO = 3;
	public static final int EVENT_MINISTERIO = 4;
	
	public class GraphItem{
		public Date d;
		public int mi;
		public int mc;
		public int me;
		public int mm;
		public int ci;
		public int cc;
		public int ce;
		public int cm;
	}
	
	ListDataProvider<CentroOperacionDTO> getAllDataProvider();
	ListDataProvider<CentroOperacionDTO> getMonitoringDataProvider();
	
	void setRegiones(ArrayList<SectorDTO> regiones);
	void setComunas(ArrayList<SectorDTO> comunas);
	void setZonas(ArrayList<SectorDTO> zonas);
	
	int getSelectedRegion();
	int getSelectedZona();
	int getSelectedComuna();
	
	void setSelectedCos(ArrayList<Integer> selected);
	
	void setMonitoreados(int monitoreados);
	
	void setPresenter(CentroControlPresenter presenter);
	
	void updateGraphs(ArrayList<GraphItem> data);
	
	void initApis();
	boolean isMapApiReady();
	boolean isChartApiReady();
	
	public interface CentroControlPresenter extends SimcePresenter{
		void activarAutoRecarga(boolean activada, int time);
		void actualizar();
		void addToMonitor(ArrayList<CentroOperacionDTO> centros);
		String getCentroOperacionToken(int centroId);
		void onRegionChange(int regionId);
		void selectUsingFiltro();
		void onApisReady();
		void onEventChange(int event);
	}
}
