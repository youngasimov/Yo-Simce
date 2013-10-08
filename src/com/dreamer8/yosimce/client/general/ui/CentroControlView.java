package com.dreamer8.yosimce.client.general.ui;


import com.dreamer8.yosimce.client.SimcePresenter;
import com.google.gwt.user.client.ui.IsWidget;

public interface CentroControlView extends IsWidget {
	
	
	
	void setPresenter(CentroControlPresenter presenter);
	
	public interface CentroControlPresenter extends SimcePresenter{
		void activarAutoRecarga(boolean activada, int time);
		void actualizar();
	}
}
