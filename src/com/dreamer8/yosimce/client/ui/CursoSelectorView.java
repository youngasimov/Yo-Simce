package com.dreamer8.yosimce.client.ui;

import com.dreamer8.yosimce.shared.dto.CursoDTO;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.SingleSelectionModel;

public interface CursoSelectorView {
	
	void show();
	void hide();
	void setOkButtonEnabled(boolean enabled);
	void setSearchValue(String search);
	void setPresenter(CursoSelectorPresenter presenter);
	
	SingleSelectionModel<CursoDTO> getSelectionModel();
	HasData<CursoDTO> getDataDisplay();
	
	public interface CursoSelectorPresenter{
		void onSearchBoxChange(String search);
		void onCancel();
		void onConfirm();
		void cursoSelected(CursoDTO curso);
	}
}