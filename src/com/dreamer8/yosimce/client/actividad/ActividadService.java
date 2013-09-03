package com.dreamer8.yosimce.client.actividad;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.shared.dto.ActividadDTO;
import com.dreamer8.yosimce.shared.dto.ActividadPreviewDTO;
import com.dreamer8.yosimce.shared.dto.DocumentoDTO;
import com.dreamer8.yosimce.shared.dto.EstadoAgendaDTO;
import com.dreamer8.yosimce.shared.dto.EstadoSincronizacionDTO;
import com.dreamer8.yosimce.shared.dto.EvaluacionUsuarioDTO;
import com.dreamer8.yosimce.shared.dto.MaterialDefectuosoDTO;
import com.dreamer8.yosimce.shared.dto.SincAlumnoDTO;
import com.dreamer8.yosimce.shared.dto.TipoContingenciaDTO;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.dreamer8.yosimce.shared.exceptions.ConsistencyException;
import com.dreamer8.yosimce.shared.exceptions.DBException;
import com.dreamer8.yosimce.shared.exceptions.NoAllowedException;
import com.dreamer8.yosimce.shared.exceptions.NoLoggedException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("actividadservice")
public interface ActividadService extends RemoteService {

	public static final String FKEY_ESTADOS = "es";
	public static final String SEPARATOR = ":";
	public static final String FKEY_ACTIVIDADES_MATERIAL_CONTINGENCIA = "amc";
	public static final String FKEY_ACTIVIDADES_CONTINGENCIA = "ac";
	public static final String FKEY_ACTIVIDADES_CONTINGENCIA_INHABILITANTE = "aci";
	public static final String FKEY_ACTIVIDADES_SINCRONIZADAS = "as";
	public static final String FKEY_ACTIVIDADES_PARCIALMENTE_SINCRONIZADAS = "aps";
	public static final String FKEY_ACTIVIDADES_NO_SINCRONIZADAS = "aps";
	public static final String FKEY_REGION = "rid";
	public static final String FKEY_COMUNA = "cid";

	ArrayList<ActividadPreviewDTO> getPreviewActividades(Integer offset,
			Integer length, HashMap<String, String> filtros)
			throws NoAllowedException, NoLoggedException, DBException, NullPointerException;

	Integer getTotalPreviewActividades(HashMap<String, String> filtros)
			throws NoAllowedException, NoLoggedException, DBException, NullPointerException;

	DocumentoDTO getDocumentoPreviewActividades(HashMap<String, String> filtros)
			throws NoAllowedException, NoLoggedException, DBException, NullPointerException;

	DocumentoDTO getDocumentoAlumnos(HashMap<String, String> filtros)
			throws NoAllowedException, NoLoggedException, DBException, NullPointerException;

	ArrayList<SincAlumnoDTO> getSincronizacionesCurso(Integer idCurso)
			throws NoAllowedException, NoLoggedException, DBException, NullPointerException;

	Boolean updateSincronizacionAlumno(Integer idCurso, SincAlumnoDTO sinc)
			throws NoAllowedException, NoLoggedException, DBException, NullPointerException,
			ConsistencyException;

	ArrayList<EvaluacionUsuarioDTO> getEvaluacionSupervisores()
			throws NoAllowedException, NoLoggedException, DBException, NullPointerException;

	Boolean updateEvaluacionSupervisor(EvaluacionUsuarioDTO evaluacion)
			throws NoAllowedException, NoLoggedException, DBException, NullPointerException,
			ConsistencyException;

	ArrayList<UserDTO> getExaminadores(String search)
			throws NoAllowedException, NoLoggedException, DBException, NullPointerException;

	ArrayList<TipoContingenciaDTO> getTiposContingencia(Integer idCurso)
			throws NoAllowedException, NoLoggedException, DBException, NullPointerException;

	ActividadDTO getActividad(Integer idCurso) throws NoAllowedException,
			NoLoggedException, DBException, NullPointerException;

	Boolean actualizarActividad(ActividadDTO actividad)
			throws NoAllowedException, NoLoggedException, DBException,
			ConsistencyException, NullPointerException;

	ArrayList<EstadoAgendaDTO> getEstadosActividad() throws NoAllowedException,
			NoLoggedException, DBException, NullPointerException;

	ArrayList<EvaluacionUsuarioDTO> getEvaluacionExaminadores(Integer idCurso)
			throws NoAllowedException, NoLoggedException, DBException, NullPointerException;

	Boolean updateEvaluacionExaminadores(Integer idCurso,
			ArrayList<EvaluacionUsuarioDTO> evaluaciones)
			throws NoAllowedException, NoLoggedException, DBException,
			ConsistencyException, NullPointerException;

	ArrayList<EstadoSincronizacionDTO> getEstadosSincronizacionFallida()
			throws NoAllowedException, NoLoggedException, DBException, NullPointerException;

	ArrayList<MaterialDefectuosoDTO> getMaterialDefectuoso(Integer idCurso)
			throws NoAllowedException, NoLoggedException, DBException, NullPointerException;

	Boolean addOrUpdateMaterialDefectuoso(Integer idCurso,
			MaterialDefectuosoDTO material) throws NoAllowedException,
			NoLoggedException, DBException, ConsistencyException, NullPointerException;

	Boolean removeMaterialDefectuoso(Integer idCurso, String idMaterial)
			throws NoAllowedException, NoLoggedException, DBException,
			ConsistencyException, NullPointerException;

	ArrayList<EstadoSincronizacionDTO> getEstadosSincronizacion()
			throws NoAllowedException, NoLoggedException, DBException, NullPointerException;
}
