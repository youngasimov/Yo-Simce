package com.dreamer8.yosimce.client.general.ui;


import java.util.ArrayList;

import com.dreamer8.yosimce.client.SimcePresenter;
import com.dreamer8.yosimce.shared.dto.CentroOperacionDTO;
import com.dreamer8.yosimce.shared.dto.SectorDTO;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.view.client.ListDataProvider;

public interface CentroControlView extends IsWidget {
	
	ListDataProvider<CentroOperacionDTO> getAllDataProvider();
	ListDataProvider<CentroOperacionDTO> getMonitoringDataProvider();
	
	void setRegiones(ArrayList<SectorDTO> regiones);
	void setComunas(ArrayList<SectorDTO> comunas);
	void setZonas(ArrayList<SectorDTO> zonas);
	
	void setMonitoreados(int monitoreados);
	
	void setPresenter(CentroControlPresenter presenter);
	
	public interface CentroControlPresenter extends SimcePresenter{
		void activarAutoRecarga(boolean activada, int time);
		void actualizar();
		void addToMonitor(ArrayList<CentroOperacionDTO> centros);
		void removeFromMonitor(ArrayList<CentroOperacionDTO> centros);
		String getCentroOperacionToken(int centroId);
		void onRegionChange(int regionId);
	}
}
