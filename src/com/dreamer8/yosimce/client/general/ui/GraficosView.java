package com.dreamer8.yosimce.client.general.ui;

import java.util.ArrayList;

import com.dreamer8.yosimce.client.SimcePresenter;
import com.dreamer8.yosimce.shared.dto.EstadoActividadItemDTO;
import com.dreamer8.yosimce.shared.dto.EstadoMaterialItemDTO;
import com.dreamer8.yosimce.shared.dto.SectorDTO;
import com.google.gwt.user.client.ui.IsWidget;

public interface GraficosView extends IsWidget {

	class Item{
		
		public Item(String item, double cantidad, double porcentaje){
			this.item = item;
			this.cantidad = cantidad;
			this.porcentaje = porcentaje;
		}
		
		String item;
		double cantidad;
		double porcentaje;
	}
	
	void setEstadoMaterialVisivility(boolean visible);
	
	void setEstadoActividadVisivility(boolean visible);
	
	void setRegiones(ArrayList<SectorDTO> regiones);
	
	void setComunas(ArrayList<SectorDTO> comunas);
	
	void setMaterialData(EstadoMaterialItemDTO estadoMaterial);
	
	void setActividadData(EstadoActividadItemDTO estadoMaterial);
	
	void setPresenter(GraficosPresenter presenter);
	
	public interface GraficosPresenter extends SimcePresenter{
		void actualizarEstadoMaterial();
		void actualizarEstadoActividad();
		void onRegionChange(int idRegion);
		void onComunaChange(int idComuna);
		void onGranularidadActividadChange(boolean toCurso);
	}
}
