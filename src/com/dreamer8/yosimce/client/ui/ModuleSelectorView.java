package com.dreamer8.yosimce.client.ui;

import com.google.gwt.user.client.ui.IsWidget;

public interface ModuleSelectorView extends IsWidget {

	void setPresenter(ModuleSelectorPresenter presenter);
	
	public interface ModuleSelectorPresenter{
		void onPlanAndResultButtonClick();
	}
	
}
