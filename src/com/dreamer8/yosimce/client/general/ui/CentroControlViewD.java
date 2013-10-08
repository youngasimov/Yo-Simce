package com.dreamer8.yosimce.client.general.ui;

import com.dreamer8.yosimce.client.general.CentroControlActivity;
import com.dreamer8.yosimce.client.ui.OverMenuBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;

public class CentroControlViewD extends Composite implements CentroControlView {

	private static CentroControlViewDUiBinder uiBinder = GWT
			.create(CentroControlViewDUiBinder.class);

	interface CentroControlViewDUiBinder extends
			UiBinder<Widget, CentroControlViewD> {
	}

	@UiField OverMenuBar menu;
	@UiField MenuItem menuItem;
	@UiField MenuItem actualizarMenu;
	@UiField MenuItem autoCarga2Menu;
	@UiField MenuItem autoCarga5Menu;
	@UiField MenuItem autoCarga10Menu;
	@UiField MenuItem sendToMonitorItem;
	@UiField MenuItem removeFromMonitorItem;
	@UiField(provided=true) DataGrid<CentroControlActivity.CentroOperacionWrap> monitorTable;
	@UiField(provided=true) DataGrid<CentroControlActivity.CentroOperacionWrap> incompleteTable;
	@UiField(provided=true) DataGrid<CentroControlActivity.CentroOperacionWrap> completeTable;
	@UiField(provided=true) DataGrid<CentroControlActivity.CentroOperacionWrap> allTable;
	
	private ListDataProvider<CentroControlActivity.CentroOperacionWrap> monitorDataProvider;
	private ListDataProvider<CentroControlActivity.CentroOperacionWrap> incompleteDataProvider;
	private ListDataProvider<CentroControlActivity.CentroOperacionWrap> completeDataProvider;
	private ListDataProvider<CentroControlActivity.CentroOperacionWrap> allDataProvider;
	
	private MultiSelectionModel<CentroControlActivity.CentroOperacionWrap> monitorSelectionModel;
	private MultiSelectionModel<CentroControlActivity.CentroOperacionWrap> allSelectionModel;
	
	private CentroControlPresenter presenter;
	
	private boolean autoRecargaActivated;
	
	
	public CentroControlViewD() {
		
		monitorTable = new DataGrid<CentroControlActivity.CentroOperacionWrap>(CentroControlActivity.CentroOperacionWrap.KEY_PROVIDER);
		incompleteTable = new DataGrid<CentroControlActivity.CentroOperacionWrap>(CentroControlActivity.CentroOperacionWrap.KEY_PROVIDER);
		completeTable = new DataGrid<CentroControlActivity.CentroOperacionWrap>(CentroControlActivity.CentroOperacionWrap.KEY_PROVIDER);
		allTable = new DataGrid<CentroControlActivity.CentroOperacionWrap>(CentroControlActivity.CentroOperacionWrap.KEY_PROVIDER);
		
		monitorDataProvider = new ListDataProvider<CentroControlActivity.CentroOperacionWrap>();
		incompleteDataProvider = new ListDataProvider<CentroControlActivity.CentroOperacionWrap>();
		completeDataProvider = new ListDataProvider<CentroControlActivity.CentroOperacionWrap>();
		allDataProvider = new ListDataProvider<CentroControlActivity.CentroOperacionWrap>();
		
		
		
		initWidget(uiBinder.createAndBindUi(this));
		autoRecargaActivated = false;
		menu.setOverItem(menuItem);
		menu.insertSeparator(2);
		menu.setOverCommand(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				presenter.toggleMenu();
			}
		});
		actualizarMenu.setScheduledCommand(new Scheduler.ScheduledCommand(){

			@Override
			public void execute() {
				if(autoRecargaActivated){
					presenter.activarAutoRecarga(false, 0);
					autoRecargaActivated = false;
				}
				presenter.actualizar();
			}
		});
		autoCarga2Menu.setScheduledCommand(new Scheduler.ScheduledCommand(){

			@Override
			public void execute() {
				autoRecargaActivated = true;
				presenter.activarAutoRecarga(true, 120000);
			}
		});
		autoCarga5Menu.setScheduledCommand(new Scheduler.ScheduledCommand(){

			@Override
			public void execute() {
				autoRecargaActivated = true;
				presenter.activarAutoRecarga(true, 300000);
			}
		});
		autoCarga10Menu.setScheduledCommand(new Scheduler.ScheduledCommand(){

			@Override
			public void execute() {
				autoRecargaActivated = true;
				presenter.activarAutoRecarga(true, 600000);
			}
		});
	}

	@Override
	public void setPresenter(CentroControlPresenter presenter) {
		this.presenter = presenter;
	}

}
