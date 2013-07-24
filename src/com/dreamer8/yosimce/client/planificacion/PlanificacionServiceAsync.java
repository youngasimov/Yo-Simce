package com.dreamer8.yosimce.client.planificacion;

import java.util.ArrayList;
import java.util.Map;

import com.dreamer8.yosimce.shared.dto.ContactoDTO;
import com.dreamer8.yosimce.shared.dto.EstablecimientoDTO;
import com.dreamer8.yosimce.shared.dto.ExaminadorDTO;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PlanificacionServiceAsync {

	void getEstablecimientos(Integer offset, Integer lenght,
			Map<String, String> filtros,
			AsyncCallback<ArrayList<EstablecimientoDTO>> callback);

	void getEstablecimiento(Integer idEstablecimiento,
			AsyncCallback<EstablecimientoDTO> callback);

	void getExaminadores(Integer idEstablecimiento,
			AsyncCallback<ArrayList<ExaminadorDTO>> callback);

	void getSupervisor(Integer idEstablecimiento,
			AsyncCallback<SupervisorDTO> callback);

	void getContacto(Integer idEstablecimiento,
			AsyncCallback<ContactoDTO> callback);

}
