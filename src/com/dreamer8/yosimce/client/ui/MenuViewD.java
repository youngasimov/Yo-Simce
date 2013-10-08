package com.dreamer8.yosimce.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;

public class MenuViewD extends Composite implements MenuView {

	private static MenuViewDUiBinder uiBinder = GWT
			.create(MenuViewDUiBinder.class);

	interface MenuViewDUiBinder extends UiBinder<Widget, MenuViewD> {
	}

	@UiField OverMenuBar menu;
	@UiField MenuItem menuItem;
	@UiField HTMLPanel informacionPanel;
	
	private MenuPresenter presenter;
	
	public MenuViewD() {
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
	public void setPresenter(MenuPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void setInformacionVisible(boolean visible) {
		informacionPanel.setVisible(visible);
	}

}
