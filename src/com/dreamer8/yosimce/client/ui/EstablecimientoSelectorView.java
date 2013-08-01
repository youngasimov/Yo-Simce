package com.dreamer8.yosimce.client.ui;

import java.util.ArrayList;

import com.dreamer8.yosimce.shared.dto.EstablecimientoDTO;
import com.google.gwt.user.client.ui.UIObject;

public interface EstablecimientoSelectorView {

	void setPresenter(EstablecimientoSelectorPresenter presenter);
	void show();
	void showRelativeTo(UIObject object);
	void hide();
	void setCancelable(boolean cancelable);
	void setGlassEnabled(boolean enabled);
	
	public interface EstablecimientoSelectorPresenter{
		ArrayList<EstablecimientoDTO> getEstablecimientos(String search);
		void onCancel();
		void onConfirm();
	}
}
