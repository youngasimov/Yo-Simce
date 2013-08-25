package com.dreamer8.yosimce.client.actividad;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.shared.dto.ActividadPreviewDTO;
import com.dreamer8.yosimce.shared.dto.ContingenciaDTO;
import com.dreamer8.yosimce.shared.dto.SincAlumnoDTO;
import com.dreamer8.yosimce.shared.dto.TipoContingenciaDTO;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.dreamer8.yosimce.shared.exceptions.DBException;
import com.dreamer8.yosimce.shared.exceptions.NoAllowedException;
import com.dreamer8.yosimce.shared.exceptions.NoLoggedException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("actividadservice")
public interface ActividadService extends RemoteService {
	
	public static final String FKEY_ACTIVIDADES_NO_INICIADAS="ani";
	public static final String FKEY_ACTIVIDADES_TERMINADAS="at";
	public static final String FKEY_ACTIVIDADES_CONTINGENCIA="ac";
	public static final String FKEY_ACTIVIDADES_PROBLEMA="ap";
	public static final String FKEY_ACTIVIDADES_SINCRONIZADAS="as";
	public static final String FKEY_REGION="rid";
	public static final String FKEY_COMUNA="cid";

	ArrayList<ActividadPreviewDTO> getPreviewActividades(Integer offset, Integer length, HashMap<String,String> filtros) throws NoAllowedException, NoLoggedException, DBException;
	
	Integer getTotalPreviewActividades(HashMap<String, String> filtros) throws NoAllowedException, NoLoggedException, DBException;
	
	ArrayList<SincAlumnoDTO> getSincronizacionesCurso(Integer idCurso) throws NoAllowedException, NoLoggedException, DBException;
	
	Boolean updateSincronizacionAlumno(SincAlumnoDTO sinc) throws NoAllowedException, NoLoggedException, DBException;
	
	UserDTO getExaminadorPrincipal(Integer idCurso) throws NoAllowedException, NoLoggedException, DBException;
	
	ArrayList<UserDTO> getExaminadores(String search) throws NoAllowedException, NoLoggedException, DBException;
	
	Boolean cambiarExaminadorPrincipal(Integer idCurso, Integer idNuevoExaminador) throws NoAllowedException, NoLoggedException, DBException;
	
	ArrayList<TipoContingenciaDTO> getTiposContingencia(Integer idCurso) throws NoAllowedException, NoLoggedException, DBException;
	
}
