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
import com.dreamer8.yosimce.shared.exceptions.DBException;
import com.dreamer8.yosimce.shared.exceptions.NoAllowedException;
import com.dreamer8.yosimce.shared.exceptions.NoLoggedException;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ActividadServiceAsync {

	void getPreviewActividades(Integer offset, Integer length,
			HashMap<String, String> filtros,
			AsyncCallback<ArrayList<ActividadPreviewDTO>> callback)
			throws NoAllowedException, NoLoggedException, DBException;

	void getTotalPreviewActividades(HashMap<String, String> filtros,
			AsyncCallback<Integer> callback);

	void getSincronizacionesCurso(Integer idCurso,
			AsyncCallback<ArrayList<SincAlumnoDTO>> callback);

	void getExaminadores(String search,
			AsyncCallback<ArrayList<UserDTO>> callback);

	void getTiposContingencia(Integer idCurso,
			AsyncCallback<ArrayList<TipoContingenciaDTO>> callback);

	void actualizarActividad(ActividadDTO actividad,
			AsyncCallback<Boolean> callback);

	void getActividad(Integer idCurso, AsyncCallback<ActividadDTO> callback);

	void getEvaluacionSupervisores(
			AsyncCallback<ArrayList<EvaluacionUsuarioDTO>> callback);

	void updateEvaluacionSupervisor(EvaluacionUsuarioDTO evaluacion,
			AsyncCallback<Boolean> callback);

	void getEstadosActividad(AsyncCallback<ArrayList<EstadoAgendaDTO>> callback);

	void getEvaluacionExaminadores(Integer idCurso,
			AsyncCallback<ArrayList<EvaluacionUsuarioDTO>> callback);

	void updateEvaluacionExaminadores(Integer idCurso,
			ArrayList<EvaluacionUsuarioDTO> evaluaciones,
			AsyncCallback<Boolean> callback);

	void getEstadosSincronizacionFallida(
			AsyncCallback<ArrayList<EstadoSincronizacionDTO>> callback);

	void getMaterialDefectuoso(Integer idCurso,
			AsyncCallback<ArrayList<MaterialDefectuosoDTO>> callback);

	void addOrUpdateMaterialDefectuoso(Integer idCurso,
			MaterialDefectuosoDTO material, AsyncCallback<Boolean> callback);

	void removeMaterialDefectuoso(Integer idCurso, String idMaterial,
			AsyncCallback<Boolean> callback);

	void updateSincronizacionAlumno(Integer idCurso, SincAlumnoDTO sinc,
			AsyncCallback<Boolean> callback);

	void getDocumentoPreviewActividades(HashMap<String, String> filtros,
			AsyncCallback<DocumentoDTO> callback);

	void getEstadosSincronizacion(
			AsyncCallback<ArrayList<EstadoSincronizacionDTO>> callback);

	void getDocumentoAlumnos(HashMap<String, String> filtros,
			AsyncCallback<DocumentoDTO> callback);

}