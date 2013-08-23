package com.dreamer8.yosimce.client.planificacion.ui;

import java.util.ArrayList;
import java.util.Date;

import com.dreamer8.yosimce.client.SimcePresenter;
import com.dreamer8.yosimce.shared.dto.AgendaItemDTO;
import com.dreamer8.yosimce.shared.dto.CargoDTO;
import com.dreamer8.yosimce.shared.dto.ContactoDTO;
import com.dreamer8.yosimce.shared.dto.EstadoAgendaDTO;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.view.client.HasData;

public interface AgendarVisitaView extends IsWidget {

	void setIdCurso(int idCurso);
	
	HasData<AgendaItemDTO> getDataDisplay();
	
	void setNombreEstablecimiento(String establecimiento);
	
	void setEstadosAgenda(ArrayList<EstadoAgendaDTO> estados);
	
	void setPresenter(AgendarVisitaPresenter presenter);
	
	void setContacto(ContactoDTO contacto);
	
	void setCargos(ArrayList<CargoDTO> cargos);
	
	int getIdEstadoAgendaSeleccionado();
	
	Date getFechaHoraSeleccionada();
	
	String getComentario();
	
	
	public interface AgendarVisitaPresenter extends SimcePresenter{
		void onCambiarCursoClick();
		void onModificarAgendaClick();
		void onEditarContacto(ContactoDTO contacto);
	}
}
