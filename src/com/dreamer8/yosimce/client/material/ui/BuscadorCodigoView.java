package com.dreamer8.yosimce.client.material.ui;

import java.util.ArrayList;

import com.dreamer8.yosimce.client.SimcePresenter;
import com.dreamer8.yosimce.shared.dto.HistorialMaterialItemDTO;
import com.google.gwt.user.client.ui.IsWidget;

public interface BuscadorCodigoView extends IsWidget {
	
	
	void setCentroOperacionAsignado(String nombre, String token);
	void setCentroOperacionIngresado(String nombre, String token);
	void setCodigo(String codigo);
	void setTipo(String tipo);
	void setEtapa(String etapa);
	void setRbd(String rbd);
	void setEstablecimiento(String establecimiento);
	void setNivel(String nivel);
	void setCurso(String curso);
	void setTipoActividad(String tipoActividad);
	void setHistorial(ArrayList<HistorialMaterialItemDTO> historial);
	
	void setPresenter(BuscadorCodigoPresenter presenter);
	
	void setFocusOnCodigoBox(); 
	
	public interface BuscadorCodigoPresenter extends SimcePresenter{
		void getDetalleCodigoMaterial(String codigo);
		void onClear();
	}

}
