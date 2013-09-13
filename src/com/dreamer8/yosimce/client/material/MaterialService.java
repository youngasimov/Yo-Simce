package com.dreamer8.yosimce.client.material;

import java.util.ArrayList;

import com.dreamer8.yosimce.shared.dto.DocumentoDTO;
import com.dreamer8.yosimce.shared.dto.EmplazamientoDTO;
import com.dreamer8.yosimce.shared.dto.EtapaDTO;
import com.dreamer8.yosimce.shared.dto.HistorialMaterialItemDTO;
import com.dreamer8.yosimce.shared.dto.LoteDTO;
import com.dreamer8.yosimce.shared.dto.MaterialDTO;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.dreamer8.yosimce.shared.exceptions.ConsistencyException;
import com.dreamer8.yosimce.shared.exceptions.DBException;
import com.dreamer8.yosimce.shared.exceptions.NoAllowedException;
import com.dreamer8.yosimce.shared.exceptions.NoLoggedException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("materialservice")
public interface MaterialService extends RemoteService {

	
	ArrayList<EmplazamientoDTO> getCentrosOperacionAsociados() throws NoAllowedException, NoLoggedException, DBException,
	NullPointerException, ConsistencyException;
	
	ArrayList<EtapaDTO> getEtapas() throws NoAllowedException, NoLoggedException, DBException,
	NullPointerException, ConsistencyException;
	
	UserDTO getUser(String rut) throws NoAllowedException, NoLoggedException, DBException,
	NullPointerException, ConsistencyException;
	
	ArrayList<MaterialDTO> getMateriales(Integer idCo) throws NoAllowedException, NoLoggedException, DBException,
	NullPointerException, ConsistencyException;
	
	ArrayList<MaterialDTO> getMaterialesByCodigos(Integer idCo,ArrayList<String> codigos) throws NoAllowedException, NoLoggedException, DBException,
	NullPointerException, ConsistencyException;
	
	MaterialDTO getMaterial(String codigo) throws NoAllowedException, NoLoggedException, DBException,
	NullPointerException, ConsistencyException;
	
	ArrayList<HistorialMaterialItemDTO> getHistorialMaterial(Integer idMaterial) throws NoAllowedException, NoLoggedException, DBException,
	NullPointerException, ConsistencyException;
	
	DocumentoDTO exportar(ArrayList<Integer> idsMaterial) throws NoAllowedException, NoLoggedException, DBException,
	NullPointerException, ConsistencyException;
	
	Boolean IngresarMateriales(Integer idCo,ArrayList<String> codigos, String folio, String file) throws NoAllowedException, NoLoggedException, DBException,
	NullPointerException, ConsistencyException;
	
	Boolean crearOEditarLote(Integer idCo,ArrayList<Integer> materiales, LoteDTO lote) throws NoAllowedException, NoLoggedException, DBException,
	NullPointerException, ConsistencyException;
	
	Boolean DespacharMateriales(Integer idCo, ArrayList<String> codigos, String folio, String file) throws NoAllowedException, NoLoggedException, DBException,
	NullPointerException, ConsistencyException;
	
	
}
