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

	void getNiveles(AsyncCallback<ArrayList<NivelDTO>> callback);

	void getActividadTipos(AsyncCallback<ArrayList<ActividadTipoDTO>> callback);

	void getUsuarioPermisos(
			AsyncCallback<HashMap<String, ArrayList<String>>> callback);

	void getUserToken(String username, AsyncCallback<String> callback);

	void logout(AsyncCallback<Boolean> callback);

	void keepAlive(AsyncCallback<Boolean> callback);

}
