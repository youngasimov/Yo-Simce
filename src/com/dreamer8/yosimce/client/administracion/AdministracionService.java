package com.dreamer8.yosimce.client.administracion;

import java.util.ArrayList;

import com.dreamer8.yosimce.shared.dto.EmplazamientoDTO;
import com.dreamer8.yosimce.shared.dto.PermisoDTO;
import com.dreamer8.yosimce.shared.dto.TipoUsuarioDTO;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.dreamer8.yosimce.shared.exceptions.ConsistencyException;
import com.dreamer8.yosimce.shared.exceptions.DBException;
import com.dreamer8.yosimce.shared.exceptions.NoAllowedException;
import com.dreamer8.yosimce.shared.exceptions.NoLoggedException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("administracionservice")
public interface AdministracionService extends RemoteService {
	public ArrayList<UserDTO> getUsuarios(String filtro, Integer offset,
			Integer length) throws NoAllowedException, NoLoggedException,
			DBException;

	public ArrayList<TipoUsuarioDTO> getTiposUsuario()
			throws NoAllowedException, NoLoggedException, DBException;

	public ArrayList<EmplazamientoDTO> getEmplazamientos(
			String tipoEmplazamiento);

	public Boolean setPerfilUsuario(Integer idUsuario, Integer idTipoUsuario,
			EmplazamientoDTO emplazamiento) throws ConsistencyException,
			NoAllowedException, NoLoggedException, DBException;

	public Boolean reiniciarPassword(Integer idUsuario)
			throws NoAllowedException, NoLoggedException, DBException;

	public ArrayList<PermisoDTO> getPermisos() throws NoAllowedException,
			NoLoggedException, DBException;
	
	public Boolean setPermisos(ArrayList<PermisoDTO> permisos) throws NoAllowedException,
	NoLoggedException, DBException,ConsistencyException;
	

}
