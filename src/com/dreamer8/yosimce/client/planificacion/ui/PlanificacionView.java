package com.dreamer8.yosimce.client.planificacion.ui;

import com.dreamer8.yosimce.client.SimcePresenter;
import com.google.gwt.user.client.ui.IsWidget;

public interface PlanificacionView extends IsWidget {

	void setAgendamientosVisivility(boolean visible);
	void setDetalleAgendaVisivility(boolean visible);
	void setAgendarVisitaVisivility(boolean visible);
	void setPresenter(PlanificacionPresenter presenter);
	
	public interface PlanificacionPresenter extends SimcePresenter {
	}
	
}
