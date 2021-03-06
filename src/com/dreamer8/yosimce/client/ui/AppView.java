package com.dreamer8.yosimce.client.ui;

import com.google.gwt.media.client.MediaBase;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;

public interface AppView extends IsWidget {

	void setSidebarPanelState(boolean open);
	SimpleLayoutPanel getHeaderView();
	SimpleLayoutPanel getSideBarPanel();
	SimpleLayoutPanel getContentPanel();
	
	void setBarLoadVisibility(boolean visible);
	
	void setCirularLoadVisibility(boolean visible);
	
	void showErrorMessage(String message,boolean autoclose, int tiempo);
	void showWarningMessage(String message,boolean autoclose, int tiempo);
	void showOkMessage(String message,boolean autoclose, int tiempo);
	void showPermisoMessage(String message,boolean autoclose,int tiempo);
	void openLoginPopup(String mensaje1, String mensaje2);
	
	void setManualHref(String href);
	
	MediaBase getNotificationSound();
	MediaBase getErrorSound();
	
	void setPresenter(AppPresenter presenter);
	
	public interface AppPresenter extends Presenter{
		void onMouseOutFromPanel();
		void onLogout();
		void onMouseMoveOnWindow();
	}
}
