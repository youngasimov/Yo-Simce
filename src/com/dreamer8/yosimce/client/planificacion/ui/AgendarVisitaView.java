package com.dreamer8.yosimce.client.planificacion.ui;

import com.dreamer8.yosimce.shared.dto.AgendaItemDTO;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.view.client.HasData;

public interface AgendarVisitaView extends IsWidget {

	HasData<AgendaItemDTO> getDataDisplay();
	void setNombreEstablecimiento(String establecimiento);
	void setPresenter(AgendarVisitaPresenter presenter);
	UIObject getCambiarButton();
	
	
	public interface AgendarVisitaPresenter{
		void onCambiarEstablecimientoClick();
	}
}
