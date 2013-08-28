package com.dreamer8.yosimce.client.administracion.ui;

import com.google.gwt.user.client.ui.IsWidget;
import com.dreamer8.yosimce.client.SimcePresenter;

public interface AdminView extends IsWidget {

	void setAdminUsersVisivility(boolean visible);
	void setAdminEventsVisivility(boolean visible);
	void setAdminPermisosVisivility(boolean visible);
	
	void setPresenter(AdminPresenter presenter);
	
	public interface AdminPresenter extends SimcePresenter{
		
	}
}
