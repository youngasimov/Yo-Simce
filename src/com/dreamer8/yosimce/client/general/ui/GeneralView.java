package com.dreamer8.yosimce.client.general.ui;

import com.google.gwt.user.client.ui.IsWidget;
import com.dreamer8.yosimce.client.SimcePlace;

public interface GeneralView extends IsWidget {

	void setDetalleEstablecimientoVisivility(boolean visible);
	void setHistorialEstablecimientoVisivility(boolean visible);
	
	void setPresenter(GeneralPresenter presenter);
	
	public interface GeneralPresenter{
		void goTo(SimcePlace place);
	}
}