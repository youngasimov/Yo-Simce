package com.dreamer8.yosimce.client.actividad.ui;

import com.dreamer8.yosimce.client.ui.OverMenuBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;

public class AvanceRevisionViewD extends Composite implements AvanceRevisionView {

	private static AvanceRevisionViewDUiBinder uiBinder = GWT
			.create(AvanceRevisionViewDUiBinder.class);

	interface AvanceRevisionViewDUiBinder extends
			UiBinder<Widget, AvanceRevisionViewD> {
	}

	
	@UiField OverMenuBar menu;
	@UiField MenuItem menuItem;
	
	private AvanceRevisionPresenter presenter;
	
	public AvanceRevisionViewD() {
		initWidget(uiBinder.createAndBindUi(this));
		menu.insertSeparator(1);
		menu.setOverItem(menuItem);
		menu.setOverCommand(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				presenter.toggleMenu();
			}
		});
	}

	@Override
	public void setPresenter(AvanceRevisionPresenter presenter) {
		this.presenter = presenter;
	}


}
