package com.dreamer8.yosimce.client.material.ui;

import com.dreamer8.yosimce.client.ui.OverMenuBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;

public class CentroOperacionViewD extends Composite implements CentroOperacionView {

	private static CentroOperacionViewDUiBinder uiBinder = GWT
			.create(CentroOperacionViewDUiBinder.class);

	interface CentroOperacionViewDUiBinder extends
			UiBinder<Widget, CentroOperacionViewD> {
	}

	
	@UiField OverMenuBar menu;
	@UiField MenuItem menuItem;
	@UiField MenuItem cosItem;
	@UiField MenuItem filtrosItem;
	@UiField MenuItem exportarItem;
	
	private CentroOperacionPresenter presenter;
	
	public CentroOperacionViewD() {
		initWidget(uiBinder.createAndBindUi(this));
		menu.insertSeparator(2);
		menu.setOverItem(menuItem);
		menu.setOverCommand(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				presenter.toggleMenu();
			}
		});
	}

	@Override
	public void setPresenter(CentroOperacionPresenter presenter) {
		this.presenter = presenter;
	}

}
