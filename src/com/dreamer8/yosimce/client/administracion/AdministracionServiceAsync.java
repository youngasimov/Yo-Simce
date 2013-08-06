package com.dreamer8.yosimce.client.administracion;

import java.util.ArrayList;

import com.dreamer8.yosimce.shared.dto.EmplazamientoDTO;
import com.dreamer8.yosimce.shared.dto.PermisoDTO;
import com.dreamer8.yosimce.shared.dto.TipoUsuarioDTO;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AdministracionServiceAsync {

	void getUsuarios(String filtro, Integer offset, Integer length,
			AsyncCallback<ArrayList<UserDTO>> callback);

	void getTiposUsuario(AsyncCallback<ArrayList<TipoUsuarioDTO>> callback);

	void reiniciarPassword(Integer idUsuario, AsyncCallback<Boolean> callback);

	void setPerfilUsuario(Integer idUsuario, Integer idTipoUsuario,
			EmplazamientoDTO emplazamiento, AsyncCallback<Boolean> callback);

	void getEmplazamientos(String tipoEmplazamiento,
			AsyncCallback<ArrayList<EmplazamientoDTO>> callback);

	void getPermisos(AsyncCallback<ArrayList<PermisoDTO>> callback);

	void setPermisos(ArrayList<PermisoDTO> permisos,
			AsyncCallback<Boolean> callback);


}
