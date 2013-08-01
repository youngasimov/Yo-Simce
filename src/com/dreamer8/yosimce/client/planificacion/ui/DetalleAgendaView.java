package com.dreamer8.yosimce.client.planificacion.ui;

import com.dreamer8.yosimce.shared.dto.AgendaItemDTO;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.view.client.HasData;

public interface DetalleAgendaView extends IsWidget {

	HasData<AgendaItemDTO> getDataDisplay();
	void setNombreEstablecimiento(String establecimiento);
	UIObject getCambiarButton();
	void setPresenter(DetalleAgendaPresenter presenter);
	
	public interface DetalleAgendaPresenter{
		void onCambiarCursoClick();
	}
}
