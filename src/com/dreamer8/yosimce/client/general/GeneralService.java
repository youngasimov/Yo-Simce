package com.dreamer8.yosimce.client.general;

import java.util.ArrayList;

import com.dreamer8.yosimce.shared.dto.CentroOperacionDTO;
import com.dreamer8.yosimce.shared.dto.ControlCentroOperacionDTO;
import com.dreamer8.yosimce.shared.dto.CursoDTO;
import com.dreamer8.yosimce.shared.dto.DetalleCursoDTO;
import com.dreamer8.yosimce.shared.dto.SectorDTO;
import com.dreamer8.yosimce.shared.exceptions.ConsistencyException;
import com.dreamer8.yosimce.shared.exceptions.DBException;
import com.dreamer8.yosimce.shared.exceptions.NoAllowedException;
import com.dreamer8.yosimce.shared.exceptions.NoLoggedException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("generalservice")
public interface GeneralService extends RemoteService {

	public DetalleCursoDTO getDetalleCurso(Integer idCurso)
			throws NoAllowedException, NoLoggedException, DBException,
			ConsistencyException, NullPointerException;
	
	public DetalleCursoDTO getDetalleCurso(Integer idCurso, Integer tipoActividad)
			throws NoAllowedException, NoLoggedException, DBException,
			ConsistencyException, NullPointerException;

	public ArrayList<SectorDTO> getRegiones() throws NoAllowedException,
			NoLoggedException, DBException, ConsistencyException,
			NullPointerException;

	public ArrayList<SectorDTO> getComunas(SectorDTO parent)
			throws NoAllowedException, NoLoggedException, DBException,
			ConsistencyException, NullPointerException;
	
	public ArrayList<SectorDTO> getZonas(SectorDTO parent)
			throws NoAllowedException, NoLoggedException, DBException,
			ConsistencyException, NullPointerException;

	public ArrayList<CursoDTO> getCursos(String rbdSeach)
			throws NoAllowedException, NoLoggedException, DBException,
			ConsistencyException, NullPointerException;

	public CursoDTO getCurso(Integer idCurso) throws NoAllowedException,
			NoLoggedException, DBException, ConsistencyException,
			NullPointerException;
	
	public ArrayList<CentroOperacionDTO> getCentrosOperacion()  throws NoAllowedException,
			NoLoggedException, DBException, ConsistencyException,
			NullPointerException;
	
	public ArrayList<ControlCentroOperacionDTO> getCentrosOperacionParaControl() throws NoAllowedException,
	NoLoggedException, DBException, ConsistencyException,
	NullPointerException;

}
