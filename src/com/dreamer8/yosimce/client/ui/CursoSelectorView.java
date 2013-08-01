package com.dreamer8.yosimce.client.ui;

import java.util.ArrayList;

import com.dreamer8.yosimce.shared.dto.EstablecimientoDTO;
import com.google.gwt.user.client.ui.UIObject;

public interface CursoSelectorView {

	void setPresenter(CursoSelectorPresenter presenter);
	void show();
	void showRelativeTo(UIObject object);
	void hide();
	void setCancelable(boolean cancelable);
	void setGlassEnabled(boolean enabled);
	
	public interface CursoSelectorPresenter{
		ArrayList<EstablecimientoDTO> getCursos(String search);
		void onCancel();
		void onConfirm();
	}
}
