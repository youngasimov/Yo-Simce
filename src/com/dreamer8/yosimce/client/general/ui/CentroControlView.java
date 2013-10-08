package com.dreamer8.yosimce.client.general.ui;


import java.util.ArrayList;

import com.dreamer8.yosimce.client.SimcePresenter;
import com.dreamer8.yosimce.client.general.CentroControlActivity;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.view.client.ListDataProvider;

public interface CentroControlView extends IsWidget {
	
	ListDataProvider<CentroControlActivity.CentroOperacionWrap> getMonitorDataProvider();
	ListDataProvider<CentroControlActivity.CentroOperacionWrap> getAllDataProvider();
	ListDataProvider<CentroControlActivity.CentroOperacionWrap> getCompleteDataProvider();
	ListDataProvider<CentroControlActivity.CentroOperacionWrap> getIncompleteDataProvider();
	
	
	
	
	void setPresenter(CentroControlPresenter presenter);
	
	public interface CentroControlPresenter extends SimcePresenter{
		void activarAutoRecarga(boolean activada, int time);
		void actualizar();
		void addToMonitor(ArrayList<CentroControlActivity.CentroOperacionWrap> centros);
		void removeFromMonitor(ArrayList<CentroControlActivity.CentroOperacionWrap> centros);
		void setMonitorEtapaEstablecimiento();
		void setMonitorEtapaCentro();
		void setMonitorEtapaImprenta();
		void setMonitorEtapaMinisterio();
	}
}
