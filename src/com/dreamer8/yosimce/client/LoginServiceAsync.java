package com.dreamer8.yosimce.client;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.shared.dto.ActividadTipoDTO;
import com.dreamer8.yosimce.shared.dto.AplicacionDTO;
import com.dreamer8.yosimce.shared.dto.NivelDTO;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginServiceAsync {

	void getUser(String token, AsyncCallback<UserDTO> callback);

	void getAplicaciones(AsyncCallback<ArrayList<AplicacionDTO>> callback);

	void getNiveles(Integer idAplicacion,
			AsyncCallback<ArrayList<NivelDTO>> callback);

	void getActividadTipos(Integer idAplicacion, Integer idNivel,
			AsyncCallback<ArrayList<ActividadTipoDTO>> callback);

	void getUsuarioPermisos(Integer idAplicacion,
			AsyncCallback<HashMap<String, ArrayList<String>>> callback);

}
