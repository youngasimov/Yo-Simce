package com.dreamer8.yosimce.client.activity.planandresult;

import java.util.ArrayList;
import java.util.Map;

import com.dreamer8.yosimce.shared.dto.EstablecimientoDTO;
import com.dreamer8.yosimce.shared.exceptions.DBException;
import com.dreamer8.yosimce.shared.exceptions.NoAllowedException;
import com.dreamer8.yosimce.shared.exceptions.NoLoggedException;
import com.google.gwt.user.client.rpc.RemoteService;

public interface PlanAndResultService extends RemoteService {
	public ArrayList<EstablecimientoDTO> getEstablecimientos(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			Integer offset, Integer lenght, Map<String, String> filtros)
			throws NoAllowedException, NoLoggedException, DBException;
}
