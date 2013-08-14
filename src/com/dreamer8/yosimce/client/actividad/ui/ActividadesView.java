package com.dreamer8.yosimce.client.actividad.ui;

import com.dreamer8.yosimce.client.SimcePresenter;
import com.dreamer8.yosimce.shared.dto.ActividadPreviewDTO;
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
	
	public interface ActividadesPresenter extends SimcePresenter{
		void onExportarClick();
		void onRangeChange(Range r);
	}
}
