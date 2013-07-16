package com.dreamer8.yosimce.client.ui;

import com.google.gwt.user.client.ui.IsWidget;

public interface NotLoggedView extends IsWidget {

	void setMessage(String message);
	void setLink(String url, String message);
	
}
