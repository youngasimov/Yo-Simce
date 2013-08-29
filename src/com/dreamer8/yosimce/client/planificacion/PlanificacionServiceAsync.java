package com.dreamer8.yosimce.client.planificacion;

import java.util.ArrayList;
import java.util.Map;

import com.dreamer8.yosimce.shared.dto.AgendaDTO;
import com.dreamer8.yosimce.shared.dto.AgendaItemDTO;
import com.dreamer8.yosimce.shared.dto.AgendaPreviewDTO;
import com.dreamer8.yosimce.shared.dto.CargoDTO;
import com.dreamer8.yosimce.shared.dto.ContactoDTO;
import com.dreamer8.yosimce.shared.dto.DocumentoDTO;
import com.dreamer8.yosimce.shared.dto.EstadoAgendaDTO;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PlanificacionServiceAsync {

	void getContacto(Integer idCurso,
			AsyncCallback<ContactoDTO> callback);

	void getPreviewAgendamientos(Integer offset, Integer length,
			Map<String, String> filtros,
			AsyncCallback<ArrayList<AgendaPreviewDTO>> callback);

	void getAgendaCurso(Integer idCurso, AsyncCallback<AgendaDTO> callback);

	void AgendarVisita(Integer idCurso, AgendaItemDTO itemAgenda,
			AsyncCallback<AgendaItemDTO> callback);

	void getEstadosAgenda(AsyncCallback<ArrayList<EstadoAgendaDTO>> callback);

	void getTotalPreviewAgendamientos(Map<String, String> filtros, AsyncCallback<Integer> callback);

	void editarContacto(Integer idCurso, ContactoDTO contacto,
			AsyncCallback<Boolean> callback);

	void getCargos(AsyncCallback<ArrayList<CargoDTO>> callback);

	void getDocumentoPreviewAgendamientos(Map<String, String> filtros,
			AsyncCallback<DocumentoDTO> callback);

	

}
