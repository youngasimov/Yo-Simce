package com.dreamer8.yosimce.client.material;

import java.util.ArrayList;

import com.dreamer8.yosimce.shared.dto.DocumentoDTO;
import com.dreamer8.yosimce.shared.dto.EmplazamientoDTO;
import com.dreamer8.yosimce.shared.dto.EtapaDTO;
import com.dreamer8.yosimce.shared.dto.HistorialMaterialItemDTO;
import com.dreamer8.yosimce.shared.dto.LoteDTO;
import com.dreamer8.yosimce.shared.dto.MaterialDTO;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MaterialServiceAsync {

	void getCentrosOperacionAsociados(
			AsyncCallback<ArrayList<EmplazamientoDTO>> callback);

	void getHistorialMaterial(Integer idMaterial,
			AsyncCallback<ArrayList<HistorialMaterialItemDTO>> callback);

	void getEtapas(AsyncCallback<ArrayList<EtapaDTO>> callback);

	void getUser(String rut, AsyncCallback<UserDTO> callback);

	void exportar(ArrayList<Integer> idsMaterial,
			AsyncCallback<DocumentoDTO> callback);

	void getMaterial(String codigo, AsyncCallback<MaterialDTO> callback);

	void IngresarMateriales(Integer idCo, ArrayList<String> codigos,
			String folio, String file, AsyncCallback<Boolean> callback);

	void crearOEditarLote(Integer idCo, ArrayList<Integer> materiales,
			LoteDTO lote, AsyncCallback<Boolean> callback);

	void DespacharMateriales(Integer idCo, ArrayList<String> codigos,
			String folio, String file, AsyncCallback<Boolean> callback);

	void getMateriales(Integer idCo,
			AsyncCallback<ArrayList<MaterialDTO>> callback);

	void getMaterialesByCodigos(Integer idCo, ArrayList<String> codigos,
			AsyncCallback<ArrayList<MaterialDTO>> callback);

}
