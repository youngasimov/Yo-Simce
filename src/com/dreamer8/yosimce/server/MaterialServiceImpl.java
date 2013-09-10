package com.dreamer8.yosimce.server;

import java.util.ArrayList;

import com.dreamer8.yosimce.client.material.MaterialService;
import com.dreamer8.yosimce.shared.dto.DocumentoDTO;
import com.dreamer8.yosimce.shared.dto.EmplazamientoDTO;
import com.dreamer8.yosimce.shared.dto.EtapaDTO;
import com.dreamer8.yosimce.shared.dto.HistorialMaterialItemDTO;
import com.dreamer8.yosimce.shared.dto.MaterialDTO;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.dreamer8.yosimce.shared.exceptions.ConsistencyException;
import com.dreamer8.yosimce.shared.exceptions.DBException;
import com.dreamer8.yosimce.shared.exceptions.NoAllowedException;
import com.dreamer8.yosimce.shared.exceptions.NoLoggedException;

public class MaterialServiceImpl extends CustomRemoteServiceServlet implements
		MaterialService {

	private String className = "MaterialService";

	@Override
	public ArrayList<EmplazamientoDTO> getCentrosOperacionAsociados()
			throws NoAllowedException, NoLoggedException, DBException,
			NullPointerException, ConsistencyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<EtapaDTO> getEtapas() throws NoAllowedException,
			NoLoggedException, DBException, NullPointerException,
			ConsistencyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDTO getUser(String rut) throws NoAllowedException,
			NoLoggedException, DBException, NullPointerException,
			ConsistencyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<MaterialDTO> getMateriales(Integer idCo)
			throws NoAllowedException, NoLoggedException, DBException,
			NullPointerException, ConsistencyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MaterialDTO getMaterial(String codigo) throws NoAllowedException,
			NoLoggedException, DBException, NullPointerException,
			ConsistencyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<HistorialMaterialItemDTO> getHistorialMaterial(
			Integer idMaterial) throws NoAllowedException, NoLoggedException,
			DBException, NullPointerException, ConsistencyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DocumentoDTO exportar(ArrayList<Integer> idsMaterial)
			throws NoAllowedException, NoLoggedException, DBException,
			NullPointerException, ConsistencyException {
		// TODO Auto-generated method stub
		return null;
	}
}
