package com.dreamer8.yosimce.client.general.ui;

import com.dreamer8.yosimce.client.ui.OverMenuBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;

public class CentroControlViewD extends Composite implements CentroControlView {

	private static CentroControlViewDUiBinder uiBinder = GWT
			.create(CentroControlViewDUiBinder.class);

	interface CentroControlViewDUiBinder extends
			UiBinder<Widget, CentroControlViewD> {
	}

	@UiField OverMenuBar menu;
	@UiField MenuItem menuItem;
	
	private CentroControlPresenter presenter;
	
	public CentroControlViewD() {
		initWidget(uiBinder.createAndBindUi(this));
		menu.setOverItem(menuItem);
		menu.setOverCommand(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				presenter.toggleMenu();
			}
		});
	}

	@Override
	public void setPresenter(CentroControlPresenter presenter) {
		this.presenter = presenter;
	}

}
