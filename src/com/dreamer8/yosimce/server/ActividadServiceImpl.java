package com.dreamer8.yosimce.server;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.actividad.ActividadService;
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

public class ActividadServiceImpl extends CustomRemoteServiceServlet implements
		ActividadService {

	private String className = "ActividadService";

	/**
	 * @permiso getPreviewActividades
	 */
	@Override
	public ArrayList<ActividadPreviewDTO> getPreviewActividades(Integer offset,
			Integer length, HashMap<String, String> filtros)
			throws NoAllowedException, NoLoggedException, DBException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @permiso getTotalPreviewActividades
	 */
	@Override
	public Integer getTotalPreviewActividades(HashMap<String, String> filtros)
			throws NoAllowedException, NoLoggedException, DBException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @permiso getSincronizacionesCurso
	 */
	@Override
	public ArrayList<SincAlumnoDTO> getSincronizacionesCurso(Integer idCurso)
			throws NoAllowedException, NoLoggedException, DBException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @permiso updateSincronizacionAlumno
	 */
	@Override
	public Boolean updateSincronizacionAlumno(SincAlumnoDTO sinc)
			throws NoAllowedException, NoLoggedException, DBException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @permiso getEvaluacionSupervisores
	 */
	@Override
	public ArrayList<EvaluacionUsuarioDTO> getEvaluacionSupervisores()
			throws NoAllowedException, NoLoggedException, DBException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @permiso updateEvaluacionSupervisor
	 */
	@Override
	public Boolean updateEvaluacionSupervisor(EvaluacionUsuarioDTO evaluacion)
			throws NoAllowedException, NoLoggedException, DBException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @permiso getExaminadores
	 */
	@Override
	public ArrayList<UserDTO> getExaminadores(String search)
			throws NoAllowedException, NoLoggedException, DBException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @permiso getTiposContingencia
	 */
	@Override
	public ArrayList<TipoContingenciaDTO> getTiposContingencia(Integer idCurso)
			throws NoAllowedException, NoLoggedException, DBException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @permiso getActividad
	 */
	@Override
	public ActividadDTO getActividad(Integer idCurso)
			throws NoAllowedException, NoLoggedException, DBException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @permiso actualizarActividad
	 */
	@Override
	public Boolean actualizarActividad(ActividadDTO actividad)
			throws NoAllowedException, NoLoggedException, DBException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @permiso getEstadosActividad
	 */
	@Override
	public ArrayList<EstadoAgendaDTO> getEstadosActividad()
			throws NoAllowedException, NoLoggedException, DBException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @permiso getEvaluacionExaminadores
	 */
	@Override
	public ArrayList<EvaluacionUsuarioDTO> getEvaluacionExaminadores(
			Integer idCurso) throws NoAllowedException, NoLoggedException,
			DBException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @permiso updateEvaluacionExaminadores
	 */
	@Override
	public Boolean updateEvaluacionExaminadores(Integer idCurso,
			ArrayList<EvaluacionUsuarioDTO> evaluaciones)
			throws NoAllowedException, NoLoggedException, DBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<EstadoSincronizacionDTO> getEstadosSincronizacionFallida()
			throws NoAllowedException, NoLoggedException, DBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<MaterialDefectuosoDTO> getMaterialDefectuoso(
			Integer idCurso) throws NoAllowedException, NoLoggedException,
			DBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean addOrUpdateMaterialDefectuoso(Integer idCurso,
			MaterialDefectuosoDTO material) throws NoAllowedException,
			NoLoggedException, DBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean removeMaterialDefectuoso(Integer idCurso, String idMaterial)
			throws NoAllowedException, NoLoggedException, DBException {
		// TODO Auto-generated method stub
		return null;
	}



}
