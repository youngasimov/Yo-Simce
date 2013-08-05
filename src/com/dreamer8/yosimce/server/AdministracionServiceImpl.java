package com.dreamer8.yosimce.server;

import java.util.ArrayList;

import com.dreamer8.yosimce.client.administracion.AdministracionService;
import com.dreamer8.yosimce.shared.dto.EmplazamientoDTO;
import com.dreamer8.yosimce.shared.dto.TipoEmplazamientoDTO;
import com.dreamer8.yosimce.shared.dto.TipoUsuarioDTO;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.dreamer8.yosimce.shared.exceptions.ConsistencyException;
import com.dreamer8.yosimce.shared.exceptions.DBException;
import com.dreamer8.yosimce.shared.exceptions.NoAllowedException;
import com.dreamer8.yosimce.shared.exceptions.NoLoggedException;

public class AdministracionServiceImpl extends CustomRemoteServiceServlet
		implements AdministracionService {

	/**
	 * @permiso getUsuarios
	 */
	@Override
	public ArrayList<UserDTO> getUsuarios(String filtro, Integer offset,
			Integer length) throws NoAllowedException, NoLoggedException,
			DBException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @permiso getTiposUsuario
	 */
	@Override
	public ArrayList<TipoUsuarioDTO> getTiposUsuario()
			throws NoAllowedException, NoLoggedException, DBException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @permiso getEmplazamientos
	 */
	@Override
	public ArrayList<EmplazamientoDTO> getEmplazamientos(
			Integer idTipoEmplazamiento) throws NoAllowedException,
			NoLoggedException, DBException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @permiso getTipoEmplazamiento
	 */
	@Override
	public TipoEmplazamientoDTO getTipoEmplazamiento(Integer idTipoUsuario)
			throws NoAllowedException, NoLoggedException, DBException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @permiso setPerfilUsuario
	 */
	@Override
	public Boolean setPerfilUsuario(Integer idUsuario, Integer idTipoUsuario,
			EmplazamientoDTO emplazamiento) throws ConsistencyException,
			NoAllowedException, NoLoggedException, DBException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @permiso reiniciarPassword
	 */
	@Override
	public Boolean reiniciarPassword(Integer idUsuario)
			throws NoAllowedException, NoLoggedException, DBException {
		// TODO Auto-generated method stub
		return null;
	}

}
