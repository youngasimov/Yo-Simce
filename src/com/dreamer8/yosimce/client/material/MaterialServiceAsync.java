package com.dreamer8.yosimce.client.material;

import java.util.ArrayList;

import com.dreamer8.yosimce.shared.dto.DetallesMaterialDTO;
import com.dreamer8.yosimce.shared.dto.DocumentoDTO;
import com.dreamer8.yosimce.shared.dto.EmplazamientoDTO;
import com.dreamer8.yosimce.shared.dto.EtapaDTO;
import com.dreamer8.yosimce.shared.dto.LoteDTO;
import com.dreamer8.yosimce.shared.dto.MaterialDTO;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MaterialServiceAsync {

	void getCentrosOperacionAsociados(
			AsyncCallback<ArrayList<EmplazamientoDTO>> callback);

	void getEtapas(AsyncCallback<ArrayList<EtapaDTO>> callback);

	void getUser(String rut, AsyncCallback<UserDTO> callback);

	void exportar(ArrayList<Integer> idsMaterial,
			AsyncCallback<DocumentoDTO> callback);

	void getMaterial(String codigo, AsyncCallback<MaterialDTO> callback);

	void ingresarMateriales(Integer idCo, ArrayList<String> codigos,
			String folio, String file, AsyncCallback<Boolean> callback);

	void crearOEditarLote(Integer idCo, ArrayList<Integer> materiales,
			LoteDTO lote, AsyncCallback<Integer> callback);

	void getMateriales(Integer idCo,
			AsyncCallback<ArrayList<MaterialDTO>> callback);

	void getMaterialesByCodigos(ArrayList<String> codigos,
			AsyncCallback<ArrayList<MaterialDTO>> callback);

	void despacharMateriales(Integer idCo, EtapaDTO etapa, String rut,
			ArrayList<String> codigos, String folio, String file,
			AsyncCallback<Boolean> callback);
	
	void despacharMateriales(Integer idCo, Integer idCoDestino, String rut,
			ArrayList<String> codigos, String folio, String file,
			AsyncCallback<Boolean> callback);

	void getCentrosOperacion(AsyncCallback<ArrayList<EmplazamientoDTO>> callback);

	void getDetallesMaterial(Integer idMaterial,
			AsyncCallback<DetallesMaterialDTO> callback);

	void eliminarLote(Integer idCo, Integer loteId,
			AsyncCallback<Boolean> callback);

}
