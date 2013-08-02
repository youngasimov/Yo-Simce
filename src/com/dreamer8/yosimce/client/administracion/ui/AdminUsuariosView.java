package com.dreamer8.yosimce.client.administracion.ui;

import com.dreamer8.yosimce.client.SimcePresenter;
import com.google.gwt.user.client.ui.IsWidget;

public interface AdminUsuariosView extends IsWidget {

	void setPresenter(AdminUsuariosPresenter presenter);
	
	public interface AdminUsuariosPresenter extends SimcePresenter{
		
	}
}
