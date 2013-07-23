package com.dreamer8.yosimce.client.planificacion;

import java.util.ArrayList;
import java.util.Map;

import com.dreamer8.yosimce.shared.dto.EstablecimientoDTO;
import com.dreamer8.yosimce.shared.exceptions.DBException;
import com.dreamer8.yosimce.shared.exceptions.NoAllowedException;
import com.dreamer8.yosimce.shared.exceptions.NoLoggedException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("planificacionservice")
public interface PlanificacionService extends RemoteService {
	public ArrayList<EstablecimientoDTO> getEstablecimientos(
			Integer offset, Integer lenght, Map<String, String> filtros)
			throws NoAllowedException, NoLoggedException, DBException;
}
