package com.dreamer8.yosimce.client.actividad;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.shared.dto.ActividadPreviewDTO;
import com.dreamer8.yosimce.shared.exceptions.DBException;
import com.dreamer8.yosimce.shared.exceptions.NoAllowedException;
import com.dreamer8.yosimce.shared.exceptions.NoLoggedException;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ActividadServiceAsync {

	void getPreviewActividades(Integer offset, Integer length,HashMap<String, String> filtros,AsyncCallback<ArrayList<ActividadPreviewDTO>> callback) throws NoAllowedException, NoLoggedException, DBException;

	void getTotalPreviewActividades(HashMap<String, String> filtros,
			AsyncCallback<Integer> callback);

	
}