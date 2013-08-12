package com.dreamer8.yosimce.server;

import java.util.ArrayList;

import com.dreamer8.yosimce.client.general.GeneralService;
import com.dreamer8.yosimce.shared.dto.CursoDTO;
import com.dreamer8.yosimce.shared.dto.EstablecimientoDTO;
import com.dreamer8.yosimce.shared.dto.HistorialCambioItemDTO;
import com.dreamer8.yosimce.shared.dto.SectorDTO;
import com.dreamer8.yosimce.shared.exceptions.DBException;
import com.dreamer8.yosimce.shared.exceptions.NoAllowedException;
import com.dreamer8.yosimce.shared.exceptions.NoLoggedException;

public class GeneralServiceImpl extends CustomRemoteServiceServlet implements
		GeneralService {
	
	private String className = "GeneralService";

	@Override
	public ArrayList<EstablecimientoDTO> getEstablecimientos(String rbdSeach)
			throws NoAllowedException, NoLoggedException, DBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EstablecimientoDTO getEstablecimiento(Integer idEstablecimiento)
			throws NoAllowedException, NoLoggedException, DBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<HistorialCambioItemDTO> getCambios(
			Integer idEstablecimiento) throws NoAllowedException,
			NoLoggedException, DBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<SectorDTO> getRegiones() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<SectorDTO> getComunas(Integer sectorId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<CursoDTO> getCursos(String rbdSeach)
			throws NoAllowedException, NoLoggedException, DBException {
		// TODO Auto-generated method stub
		return null;
	}

	

}
