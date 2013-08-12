package com.dreamer8.yosimce.client.ui;

import com.dreamer8.yosimce.shared.dto.CursoDTO;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.view.client.HasData;

public interface CursoSelectorView {

	void setPresenter(CursoSelectorPresenter presenter);
	void show();
	void showRelativeTo(UIObject object);
	void hide();
	void setCancelable(boolean cancelable);
	void setGlassEnabled(boolean enabled);
	void setOkButtonEnabled(boolean enabled);
	
	HasData<CursoDTO> getDataDisplay();
	
	public interface CursoSelectorPresenter{
		void onSearchBoxChange(String search);
		void onCancel();
		void onConfirm();
		void onCursoSelected(CursoDTO curso);
	}
}