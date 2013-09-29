package com.dreamer8.yosimce.client.administracion;

import java.util.ArrayList;

import com.dreamer8.yosimce.shared.dto.DocumentoDTO;
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
	
	public static final int MOVIMIENTO_MATERIAL = 1;
	public static final int TRACKING_POR_CO = 2;
	
	public ArrayList<UserDTO> getUsuarios(String filtro, Integer offset,
			Integer length) throws NoAllowedException, NoLoggedException,
			DBException, ConsistencyException;

	public ArrayList<TipoUsuarioDTO> getTiposUsuario()
			throws NoAllowedException, NoLoggedException, DBException,
			NullPointerException, ConsistencyException;

	public ArrayList<EmplazamientoDTO> getEmplazamientos(
			String tipoEmplazamiento) throws NoAllowedException,
			NoLoggedException, DBException, ConsistencyException,
			NullPointerException;

	public Boolean setPerfilUsuario(Integer idUsuario, Integer idTipoUsuario,
			EmplazamientoDTO emplazamiento) throws ConsistencyException,
			NoAllowedException, NoLoggedException, DBException,
			NullPointerException;

	public Boolean reiniciarPassword(Integer idUsuario)
			throws NoAllowedException, NoLoggedException, DBException,
			NullPointerException, ConsistencyException;

	public ArrayList<PermisoDTO> getPermisos() throws NoAllowedException,
			NoLoggedException, DBException, NullPointerException,
			ConsistencyException;

	public Boolean setPermisos(ArrayList<PermisoDTO> permisos)
			throws NoAllowedException, NoLoggedException, DBException,
			ConsistencyException, NullPointerException;
	
	public DocumentoDTO getReporte(Integer tipo, Integer region, Integer comuna, String desde);

}
