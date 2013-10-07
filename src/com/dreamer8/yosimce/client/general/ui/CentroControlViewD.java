package com.dreamer8.yosimce.client.general.ui;

import com.dreamer8.yosimce.client.ui.OverMenuBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;

public class CentroControlViewD extends Composite implements CentroControlView {

	private static CentroControlViewDUiBinder uiBinder = GWT
			.create(CentroControlViewDUiBinder.class);

	interface CentroControlViewDUiBinder extends
			UiBinder<Widget, CentroControlViewD> {
	}

	@UiField OverMenuBar menu;
	@UiField MenuItem menuItem;
	
	private CentroControlPresenter presenter;
	
	
	ChartLoader chartLoader;
	private boolean apiDownloaded;
	
	public CentroControlViewD() {
		apiDownloaded = false;
		chartLoader = new ChartLoader(ChartPackage.CORECHART);
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
	
	@Override
	public void getChartApi() {
		if(!apiDownloaded){
			chartLoader.loadApi(new Runnable() {
				
				@Override
				public void run() {
					
				}
			});
		}
	}

}
