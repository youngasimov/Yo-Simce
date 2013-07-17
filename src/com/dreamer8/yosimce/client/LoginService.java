package com.dreamer8.yosimce.client;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.shared.dto.ActividadTipoDTO;
import com.dreamer8.yosimce.shared.dto.AplicacionDTO;
import com.dreamer8.yosimce.shared.dto.NivelDTO;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.dreamer8.yosimce.shared.exceptions.DBException;
import com.dreamer8.yosimce.shared.exceptions.NoAllowedException;
import com.dreamer8.yosimce.shared.exceptions.NoLoggedException;
import com.google.gwt.user.client.rpc.RemoteService;

public interface LoginService extends RemoteService {

	public UserDTO getUser(String token) throws NoLoggedException, DBException;

	public ArrayList<AplicacionDTO> getAplicaciones()
			throws NoAllowedException, NoLoggedException, DBException;

	public ArrayList<NivelDTO> getNiveles(Integer idAplicacion)
			throws NoAllowedException, NoLoggedException, DBException;

	public ArrayList<ActividadTipoDTO> getActividadTipos(Integer idAplicacion,
			Integer idNivel) throws NoAllowedException, NoLoggedException,
			DBException;

	public HashMap<String, ArrayList<String>> getUsuarioPermisos(
			Integer idAplicacion) throws NoAllowedException, NoLoggedException,
			DBException;
}
