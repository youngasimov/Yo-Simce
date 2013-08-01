package com.dreamer8.yosimce.client.general;

import java.util.ArrayList;

import com.dreamer8.yosimce.shared.dto.EstablecimientoDTO;
import com.dreamer8.yosimce.shared.dto.HistorialCambioItemDTO;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GeneralServiceAsync {

	void getEstablecimientos(String rbdSeach,
			AsyncCallback<ArrayList<EstablecimientoDTO>> callback);

	void getEstablecimiento(Integer id,
			AsyncCallback<EstablecimientoDTO> callback);

	void getCambios(Integer idEstablecimiento,
			AsyncCallback<ArrayList<HistorialCambioItemDTO>> callback);

}
