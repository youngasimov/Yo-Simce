package com.dreamer8.yosimce.client.actividad.ui;

import java.util.ArrayList;

import com.dreamer8.yosimce.client.SimcePresenter;
import com.dreamer8.yosimce.shared.dto.ActividadPreviewDTO;
import com.dreamer8.yosimce.shared.dto.SectorDTO;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;

public interface ActividadesView extends IsWidget {

	HasData<ActividadPreviewDTO> getDataDisplay();
	Column<ActividadPreviewDTO,?> getColumn(int index);
	int getColumnIndex(Column<ActividadPreviewDTO,?> column);
	void clearCursoSelection();
	void setPresenter(ActividadesPresenter presenter);
	void setActividadesNoIniciadas(boolean value);
	void setActividadesTerminadas(boolean value);
	void setActividadesContingencia(boolean value);
	void setActividadesProblema(boolean value);
	void setActividadesSincronizadas(boolean value);
	void setRegiones(ArrayList<SectorDTO> regiones);
	void setComunas(ArrayList<SectorDTO> comunas);
	void setSelectedRegion(int regionId);
	void setSelectedComuna(int comunaId);
	
	public interface ActividadesPresenter extends SimcePresenter{
		void onExportarClick();
		void onRegionChange(int regionId);
		void onCancelarFiltroClick();
		void onRangeChange(Range r);
	}
}
