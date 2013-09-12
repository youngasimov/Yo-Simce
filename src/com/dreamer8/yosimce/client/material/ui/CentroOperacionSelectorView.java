package com.dreamer8.yosimce.client.material.ui;

import com.dreamer8.yosimce.shared.dto.EmplazamientoDTO;
import com.google.gwt.view.client.HasData;


public interface CentroOperacionSelectorView{
	
	void show();
	void hide();
	void setPresenter(CentroOperacionSelectorPresenter presenter);
	
	HasData<EmplazamientoDTO> getDataDisplay();
	
	public interface CentroOperacionSelectorPresenter{
		void centroOperacionSelected(EmplazamientoDTO co);
		void onAutoHide();
	}

}
