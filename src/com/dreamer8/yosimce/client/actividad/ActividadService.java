package com.dreamer8.yosimce.client.actividad;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.shared.dto.ActividadPreviewDTO;
import com.dreamer8.yosimce.shared.dto.SincAlumnoDTO;
import com.dreamer8.yosimce.shared.exceptions.DBException;
import com.dreamer8.yosimce.shared.exceptions.NoAllowedException;
import com.dreamer8.yosimce.shared.exceptions.NoLoggedException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("actividadservice")
public interface ActividadService extends RemoteService {

	ArrayList<ActividadPreviewDTO> getPreviewActividades(Integer offset, Integer length, HashMap<String,String> filtros) throws NoAllowedException, NoLoggedException, DBException;
	
	Integer getTotalPreviewActividades(HashMap<String, String> filtros) throws NoAllowedException, NoLoggedException, DBException;
	
	ArrayList<SincAlumnoDTO> getSincronizacionesCurso(Integer idCurso) throws NoAllowedException, NoLoggedException, DBException;
	
	Boolean updateSincronizacionAlumno(SincAlumnoDTO sinc) throws NoAllowedException, NoLoggedException, DBException;
}
