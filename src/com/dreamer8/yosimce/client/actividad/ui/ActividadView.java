package com.dreamer8.yosimce.client.actividad.ui;

import com.dreamer8.yosimce.client.SimcePresenter;
import com.google.gwt.user.client.ui.IsWidget;

public interface ActividadView extends IsWidget {

	void setActividadesVisivility(boolean visible);
	void setFormActividadVisivility(boolean visible);
	void setSincronizacionesVisivility(boolean visible);
	void setSincronizacionVisivility(boolean visible);
	void setAprobarSupervisoresVisivility(boolean visible);
	
	void setPresenter(ActividadPresenter presenter);
	
	public interface ActividadPresenter extends SimcePresenter{
		
	}
	
}
