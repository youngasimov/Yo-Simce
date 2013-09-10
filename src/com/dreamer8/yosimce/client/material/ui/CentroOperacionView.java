package com.dreamer8.yosimce.client.material.ui;

import java.util.ArrayList;

import com.dreamer8.yosimce.client.SimcePresenter;
import com.dreamer8.yosimce.shared.dto.DocumentoDTO;
import com.dreamer8.yosimce.shared.dto.EmplazamientoDTO;
import com.dreamer8.yosimce.shared.dto.EtapaDTO;
import com.dreamer8.yosimce.shared.dto.HistorialMaterialItemDTO;
import com.dreamer8.yosimce.shared.dto.LoteDTO;
import com.dreamer8.yosimce.shared.dto.MaterialDTO;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.view.client.HasData;

public interface CentroOperacionView extends IsWidget {

	
	HasData<HistorialMaterialItemDTO> getHistorialDataDisplay();
	void setHistorialSortHandler(ListHandler<HistorialMaterialItemDTO> handler);
	HasData<MaterialDTO> getMaterialDataDisplay();
	void setMaterialSortHandler(ListHandler<MaterialDTO> handler);
	HasData<MaterialDTO> getIngresoDataDisplay();
	void setIngresoSortHandler(ListHandler<MaterialDTO> handler);
	HasData<MaterialDTO> getPredespachoDataDisplay();
	void setPredespachoSortHandler(ListHandler<MaterialDTO> handler);
	HasData<MaterialDTO> getDespachoDataDisplay();
	void setDespachoSortHandler(ListHandler<MaterialDTO> handler);
	
	void clearCodigoIngresoBox();
	void clearCodigoPredespachoBox();
	void clearCodigoDespachoBox();
	void clearRutRetiranteBox();
	void clearNuevoLoteBox();
	
	void setFocusOnIngresoCodigoBox();
	void setFocusOnPredespachoCodigoBox();
	void setFocusOnDespachoCodigoBox();
	
	void setTotalMaterialIngresando(int total);
	void setTotalMaterialEnLote(int total);
	void setTotalMaterialDespachando(int total);
	
	void setIngresoDocumento(DocumentoDTO doc);
	void setDespachoDocumento(DocumentoDTO doc);
	
	void setLotes(ArrayList<LoteDTO> lotes);
	
	void setAddByLote(boolean addByLote);
	boolean getAddByLote();
	
	void setEtapas(ArrayList<EtapaDTO> etapas);
	void setChangeCoButtonVisivility(boolean visible);
	void setSelectedCo(EmplazamientoDTO co);
	void setRetirante(UserDTO user);
	
	void setPresenter(CentroOperacionPresenter presenter);
	
	public interface CentroOperacionPresenter extends SimcePresenter{
		void onMaterialSelected(MaterialDTO material);
		
		void onMaterialAddedToIngresoStack(String id);
		void onMaterialAddedToPredespachoStack(String id);
		void onMaterialAddedToDespachoStack(String id);
		
		void onRemoveIngresoItem(MaterialDTO material);
		void onRemovePredespachoItem(MaterialDTO material);
		void onRemoveDespachoItem(MaterialDTO material);
		
		void onIngresoDocUploaded(DocumentoDTO doc);
		void onDespachoDocUploaded(DocumentoDTO doc);
		
		void onRealizarIngresoStackActualClick();
		void onRealizarDespachoStackActualClick();
		
		void onCrearLote(String nuevoLote);
		void onDeleteLote(int loteId);
		void onLoteSelected(int loteId);
		
		void onEtapaChange(int etapaId);
		void onChangeSelectedStageCo();
		void onRutRetiranteChange(String rut);
		
		
	}
}
