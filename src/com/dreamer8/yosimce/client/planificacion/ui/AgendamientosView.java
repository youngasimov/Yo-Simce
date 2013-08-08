package com.dreamer8.yosimce.client.planificacion.ui;

import java.util.ArrayList;
import java.util.Date;

import com.dreamer8.yosimce.client.SimcePresenter;
import com.dreamer8.yosimce.shared.dto.AgendaPreviewDTO;
import com.dreamer8.yosimce.shared.dto.EstadoAgendaDTO;
import com.dreamer8.yosimce.shared.dto.SectorDTO;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortList;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;

public interface AgendamientosView extends IsWidget {

	HasData<AgendaPreviewDTO> getDataDisplay();
	ColumnSortList getColumnSortList();
    Column<AgendaPreviewDTO,?> getColumn(int index);
    int getColumnIndex(Column<AgendaPreviewDTO,?> column);
	void setPresenter(AgendamientosPresenter presenter);
	void clearCursoSelection();
	void setEstados(ArrayList<EstadoAgendaDTO> estados);
	void setRegiones(ArrayList<SectorDTO> regiones);
	void setComunas(ArrayList<SectorDTO> comunas);
	void setDesde(Date date);
	void setHasta(Date date);
	void setSelectedEstados(ArrayList<Integer> estadosId);
	void setSelectedRegion(int regionId);
	void setSelectedComuna(int comunaId);
	
	public interface AgendamientosPresenter extends SimcePresenter{
		void onColumnSort(int columnIndex);
		void onExportarClick();
		void onRegionChange(int regionId);
		void onCancelarFiltroClick();
		void onRangeChange(Range r);
	}
}
