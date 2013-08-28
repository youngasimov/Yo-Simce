package com.dreamer8.yosimce.client.actividad;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.shared.dto.ActividadDTO;
import com.dreamer8.yosimce.shared.dto.ActividadPreviewDTO;
import com.dreamer8.yosimce.shared.dto.EstadoAgendaDTO;
import com.dreamer8.yosimce.shared.dto.EstadoSincronizacionDTO;
import com.dreamer8.yosimce.shared.dto.EvaluacionUsuarioDTO;
import com.dreamer8.yosimce.shared.dto.MaterialDefectuosoDTO;
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

	public static final String FKEY_ACTIVIDADES_NO_INICIADAS = "ani";
	public static final String FKEY_ACTIVIDADES_TERMINADAS = "at";
	public static final String FKEY_ACTIVIDADES_CONTINGENCIA = "ac";
	public static final String FKEY_ACTIVIDADES_PROBLEMA = "ap";
	public static final String FKEY_ACTIVIDADES_SINCRONIZADAS = "as";
	public static final String FKEY_REGION = "rid";
	public static final String FKEY_COMUNA = "cid";

	ArrayList<ActividadPreviewDTO> getPreviewActividades(Integer offset,
			Integer length, HashMap<String, String> filtros)
			throws NoAllowedException, NoLoggedException, DBException;

	Integer getTotalPreviewActividades(HashMap<String, String> filtros)
			throws NoAllowedException, NoLoggedException, DBException;

	ArrayList<SincAlumnoDTO> getSincronizacionesCurso(Integer idCurso)
			throws NoAllowedException, NoLoggedException, DBException;

	Boolean updateSincronizacionAlumno(SincAlumnoDTO sinc)
			throws NoAllowedException, NoLoggedException, DBException;

	ArrayList<EvaluacionUsuarioDTO> getEvaluacionSupervisores()
			throws NoAllowedException, NoLoggedException, DBException;

	Boolean updateEvaluacionSupervisor(EvaluacionUsuarioDTO evaluacion)
			throws NoAllowedException, NoLoggedException, DBException;

	ArrayList<UserDTO> getExaminadores(String search)
			throws NoAllowedException, NoLoggedException, DBException;


	ArrayList<TipoContingenciaDTO> getTiposContingencia(Integer idCurso)
			throws NoAllowedException, NoLoggedException, DBException;

	ActividadDTO getActividad(Integer idCurso) throws NoAllowedException,
			NoLoggedException, DBException;

	Boolean actualizarActividad(ActividadDTO actividad)
			throws NoAllowedException, NoLoggedException, DBException;

	ArrayList<EstadoAgendaDTO> getEstadosActividad() throws NoAllowedException,
			NoLoggedException, DBException;

	ArrayList<EvaluacionUsuarioDTO> getEvaluacionExaminadores(Integer idCurso) throws NoAllowedException, NoLoggedException, DBException;
	
	Boolean updateEvaluacionExaminadores(Integer idCurso, ArrayList<EvaluacionUsuarioDTO> evaluaciones)
			throws NoAllowedException, NoLoggedException, DBException;
	
	ArrayList<EstadoSincronizacionDTO> getEstadosSincronizacionFallida() throws NoAllowedException, NoLoggedException, DBException;
	
	ArrayList<MaterialDefectuosoDTO> getMaterialDefectuoso(Integer idCurso) throws NoAllowedException, NoLoggedException, DBException;
	
	Boolean addOrUpdateMaterialDefectuoso(Integer idCurso, MaterialDefectuosoDTO material)  throws NoAllowedException, NoLoggedException, DBException;
	
	Boolean removeMaterialDefectuoso(Integer idCurso, String idMaterial)  throws NoAllowedException, NoLoggedException, DBException;
	
	

}
