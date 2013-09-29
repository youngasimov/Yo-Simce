package com.dreamer8.yosimce.client.administracion.ui;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.SimcePresenter;
import com.dreamer8.yosimce.shared.dto.SectorDTO;
import com.google.gwt.user.client.ui.IsWidget;

public interface ReportesView extends IsWidget {

	
	void setPresenter(ReportesPresenter presenter);
	void setReportesVisibility(boolean visible);
	void setRegiones(ArrayList<SectorDTO> regiones);
	void setReportes(HashMap<Integer,String> reportes);
	void setComunas(ArrayList<SectorDTO> comunas);
	
	int getSelectedRegion();
	int getSelectedComuna();
	String getSelectedDate();
	int getSelectedReporte();
	
	public interface ReportesPresenter extends SimcePresenter{
		void onGenerarReporte();
		void onRegionChange(int region);
	}
}
