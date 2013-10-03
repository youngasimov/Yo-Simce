package com.dreamer8.yosimce.client;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.shared.dto.ActividadTipoDTO;
import com.dreamer8.yosimce.shared.dto.AplicacionDTO;
import com.dreamer8.yosimce.shared.dto.NivelDTO;
import com.dreamer8.yosimce.shared.dto.TipoUsuarioDTO;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.dreamer8.yosimce.shared.exceptions.ConsistencyException;
import com.dreamer8.yosimce.shared.exceptions.DBException;
import com.dreamer8.yosimce.shared.exceptions.NoAllowedException;
import com.dreamer8.yosimce.shared.exceptions.NoLoggedException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("loginservice")
public interface LoginService extends RemoteService {
	public static final String USUARIO_TIPO_COOKIE_NAME = "ut";
	
	public static final int SESION_ACTIVA = 0;
	public static final int SESION_INACTIVA = 1;
	public static final int PRONTA_ACTUALIZACION = 2;
	

	public UserDTO getUser(String token) throws NoLoggedException, DBException,
			NullPointerException, ConsistencyException;

	public String getUserToken(String username) throws DBException,
			NullPointerException, ConsistencyException;

	public ArrayList<AplicacionDTO> getAplicaciones()
			throws NoAllowedException, NoLoggedException, DBException,
			NullPointerException, ConsistencyException;

	public ArrayList<NivelDTO> getNiveles() throws NoAllowedException,
			NoLoggedException, DBException, NullPointerException,
			ConsistencyException;

	public ArrayList<ActividadTipoDTO> getActividadTipos()
			throws NoAllowedException, NoLoggedException, DBException,
			NullPointerException, ConsistencyException;

	public HashMap<String, ArrayList<String>> getUsuarioPermisos()
			throws NoAllowedException, NoLoggedException, DBException,
			NullPointerException, ConsistencyException;

	public Boolean logout();

	public Integer keepAlive() throws NoLoggedException;
	
	public ArrayList<TipoUsuarioDTO> getUsuarioTipos()
			throws NoAllowedException, NoLoggedException, DBException,
			NullPointerException, ConsistencyException;
	
	public String getActualizacionDate();
	
	public Boolean setActualizacionDate(String date);
	
	public UserDTO getYoSimceUser() throws NoLoggedException, DBException,
			NullPointerException, ConsistencyException;
	
	public UserDTO getTrackingUser(String user, String password) throws NoLoggedException, DBException,
			NullPointerException, ConsistencyException;;
}
