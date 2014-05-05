package com.dreamer8.yosimce.client.general;

import java.util.ArrayList;

import com.dreamer8.yosimce.shared.dto.CentroOperacionDTO;
import com.dreamer8.yosimce.shared.dto.ControlCentroOperacionDTO;
import com.dreamer8.yosimce.shared.dto.CursoDTO;
import com.dreamer8.yosimce.shared.dto.DetalleCursoDTO;
import com.dreamer8.yosimce.shared.dto.SectorDTO;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GeneralServiceAsync {

	void getComunas(SectorDTO parent,
			AsyncCallback<ArrayList<SectorDTO>> callback);

	void getRegiones(AsyncCallback<ArrayList<SectorDTO>> callback);

	void getCursos(String rbdSeach, AsyncCallback<ArrayList<CursoDTO>> callback);

	void getDetalleCurso(Integer idCurso,
			AsyncCallback<DetalleCursoDTO> callback);

	void getCurso(Integer idCurso, AsyncCallback<CursoDTO> callback);

	void getCentrosOperacion(
			AsyncCallback<ArrayList<CentroOperacionDTO>> callback);

	void getZonas(SectorDTO parent, AsyncCallback<ArrayList<SectorDTO>> callback);

	void getDetalleCurso(Integer idCurso, Integer tipoActividad,
			AsyncCallback<DetalleCursoDTO> callback);

	void getCentrosOperacionParaControl(
			AsyncCallback<ArrayList<ControlCentroOperacionDTO>> callback);

}
