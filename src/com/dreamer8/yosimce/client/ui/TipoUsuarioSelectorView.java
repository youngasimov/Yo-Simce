package com.dreamer8.yosimce.client.ui;

import com.dreamer8.yosimce.shared.dto.TipoUsuarioDTO;
import com.google.gwt.view.client.HasData;

public interface TipoUsuarioSelectorView {
	
	
	HasData<TipoUsuarioDTO> getDataDisplay();
	
	void show();
	
	void hide();
	
	void setPresenter(TipoUsuarioSelectorPresenter presenter);
	
	public interface TipoUsuarioSelectorPresenter{
		void onTipoUsuarioSelected(TipoUsuarioDTO tudto);
	}
}
