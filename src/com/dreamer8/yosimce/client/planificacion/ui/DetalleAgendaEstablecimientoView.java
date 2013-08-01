package com.dreamer8.yosimce.client.planificacion.ui;

import com.dreamer8.yosimce.shared.dto.AgendaItemDTO;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.view.client.HasData;

public interface DetalleAgendaEstablecimientoView extends IsWidget {

	HasData<AgendaItemDTO> getDataDisplay();
	void setNombreEstablecimiento(String establecimiento);
	UIObject getCambiarButton();
	void setPresenter(DetalleAgendaEstablecimientoPresenter presenter);
	
	public interface DetalleAgendaEstablecimientoPresenter{
		void onCambiarEstablecimientoClick();
	}
}
