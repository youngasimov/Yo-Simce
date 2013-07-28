package com.dreamer8.yosimce.client.planificacion.ui;

import com.dreamer8.yosimce.client.SimcePlace;
import com.dreamer8.yosimce.shared.dto.AgendaPreviewDTO;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortList;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.view.client.HasData;

public interface AgendamientosView extends IsWidget {

	HasData<AgendaPreviewDTO> getDataDisplay();
	ColumnSortList getColumnSortList();
    Column<AgendaPreviewDTO,?> getColumn(int index);
    int getColumnIndex(Column<AgendaPreviewDTO,?> column);
	void setPresenter(AgendamientosPresenter presenter);
	
	public interface AgendamientosPresenter{
		void onColumnSort(int columnIndex);
		void goTo(SimcePlace place);
	}
	
}
