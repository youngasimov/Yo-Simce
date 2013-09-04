package com.dreamer8.yosimce.client.planificacion.ui;

import com.dreamer8.yosimce.client.SimcePresenter;
import com.dreamer8.yosimce.shared.dto.AgendaItemDTO;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.view.client.HasData;

public interface DetalleAgendaView extends IsWidget {

	void setIdCurso(int idCurso);
	HasData<AgendaItemDTO> getDataDisplay();
	void setNombreEstablecimiento(String establecimiento);
	UIObject getCambiarButton();
	void setPresenter(DetalleAgendaPresenter presenter);
	void clear();
	
	public interface DetalleAgendaPresenter extends SimcePresenter{
		void onCambiarCursoClick();
	}
}
