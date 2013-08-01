package com.dreamer8.yosimce.client.general;

import java.util.ArrayList;

import com.dreamer8.yosimce.shared.dto.EstablecimientoDTO;
import com.dreamer8.yosimce.shared.dto.HistorialCambioItemDTO;
import com.dreamer8.yosimce.shared.dto.SectorDTO;
import com.dreamer8.yosimce.shared.exceptions.DBException;
import com.dreamer8.yosimce.shared.exceptions.NoAllowedException;
import com.dreamer8.yosimce.shared.exceptions.NoLoggedException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("generalservice")
public interface GeneralService extends RemoteService {
	
	public ArrayList<EstablecimientoDTO> getEstablecimientos(String rbdSeach) throws NoAllowedException, NoLoggedException, DBException;
	public EstablecimientoDTO getEstablecimiento(Integer idEstablecimiento) throws NoAllowedException, NoLoggedException, DBException;
	
	public ArrayList<HistorialCambioItemDTO> getCambios(Integer idEstablecimiento) throws NoAllowedException, NoLoggedException, DBException;

	public ArrayList<SectorDTO> getRegiones();
	public ArrayList<SectorDTO> getComunas(Integer sectorId);
	
}
