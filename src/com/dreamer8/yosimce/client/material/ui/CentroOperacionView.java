package com.dreamer8.yosimce.client.material.ui;

import java.util.ArrayList;

import com.dreamer8.yosimce.client.SimcePresenter;
import com.dreamer8.yosimce.client.material.MaterialWrap;
import com.dreamer8.yosimce.shared.dto.DocumentoDTO;
import com.dreamer8.yosimce.shared.dto.EmplazamientoDTO;
import com.dreamer8.yosimce.shared.dto.EtapaDTO;
import com.dreamer8.yosimce.shared.dto.HistorialMaterialItemDTO;
import com.dreamer8.yosimce.shared.dto.LoteDTO;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.view.client.HasData;

public interface CentroOperacionView extends IsWidget {

	void setCO(EmplazamientoDTO emplazamiento);
	
	void setMaterialVisivility(boolean visible);
	void setIngresoVisivility(boolean visible);
	void setPredespachoVisivility(boolean visible);
	void setDespachoVisivility(boolean visible);
	
	HasData<HistorialMaterialItemDTO> getHistorialDataDisplay();
	void setHistorialSortHandler(ListHandler<HistorialMaterialItemDTO> handler);
	HasData<MaterialWrap> getMaterialDataDisplay();
	void setMaterialSortHandler(ListHandler<MaterialWrap> handler);
	HasData<MaterialWrap> getIngresoDataDisplay();
	void setIngresoSortHandler(ListHandler<MaterialWrap> handler);
	HasData<MaterialWrap> getPredespachoDataDisplay();
	void setPredespachoSortHandler(ListHandler<MaterialWrap> handler);
	HasData<MaterialWrap> getDespachoDataDisplay();
	void setDespachoSortHandler(ListHandler<MaterialWrap> handler);
	
	void clearIngresoFolioBox();
	void clearDespachoFolioBox();
	void clearRutRetiranteBox();
	void clearNuevoLoteBox();
	
	void setFocusOnIngresoCodigoBox(boolean cleanFirst);
	void setFocusOnPredespachoCodigoBox(boolean cleanFirst);
	void setFocusOnDespachoCodigoBox(boolean cleanFirst);
	
	void setTotalMaterialIngresando(int total);
	void setTotalMaterialEnLote(int total);
	void setTotalMaterialDespachando(int total);
	
	void setIngresoDocumento(DocumentoDTO doc);
	void setDespachoDocumento(DocumentoDTO doc);
	
	void setLotes(ArrayList<LoteDTO> lotes);
	void selectLote(int loteId);
	
	void setAddByLote(boolean addByLote);
	boolean getAddByLote();
	
	String getIngresoFolio();
	String getDespachoFolio();
	
	void setEtapas(ArrayList<EtapaDTO> etapas);
	void setChangeCoButtonVisivility(boolean visible);
	void setSelectedCo(EmplazamientoDTO co);
	void setRetirante(UserDTO user);
	
	void setPresenter(CentroOperacionPresenter presenter);
	
	public interface CentroOperacionPresenter extends SimcePresenter{
		void onMaterialSelected(MaterialWrap material);
		
		void onMaterialTabSelected();
		void onIngresoTabSelected();
		void onPredespachoTabSelected();
		void onDespachoTabSelected();
		
		void onMaterialAddedToIngresoStack(String id);
		void onMaterialAddedToPredespachoStack(String id);
		void onMaterialAddedToDespachoStack(String id);
		
		void onRemoveIngresoItem(MaterialWrap material);
		void onRemovePredespachoItem(MaterialWrap material);
		void onRemoveDespachoItem(MaterialWrap material);
		
		void onIngresoDocUploaded(DocumentoDTO doc);
		void onDespachoDocUploaded(DocumentoDTO doc);
		
		void onRealizarIngresoStackActualClick();
		void guardarLote();
		void onRealizarDespachoStackActualClick();
		
		void onCrearLote(String nuevoLote);
		void onDeleteLote(int loteId);
		void onLoteSelected(int loteId);
		
		void onEtapaChange(int etapaId);
		void onChangeSelectedStageCo();
		void onRutRetiranteChange(String rut);
		
		
	}
}
