package com.dreamer8.yosimce.client.activity.planandresult;

import java.util.ArrayList;
import java.util.Map;

import com.dreamer8.yosimce.shared.dto.EstablecimientoDTO;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PLanAndResultServiceAsync {

	void getEstablecimientos(Integer idAplicacion, Integer idNivel,
			Integer idActividadTipo, Integer offset, Integer lenght,
			Map<String, String> filtros,
			AsyncCallback<ArrayList<EstablecimientoDTO>> callback);

}
