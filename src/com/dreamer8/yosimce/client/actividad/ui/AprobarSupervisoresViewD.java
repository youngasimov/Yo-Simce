package com.dreamer8.yosimce.client.actividad.ui;

import java.util.ArrayList;

import com.dreamer8.yosimce.client.ui.OverMenuBar;
import com.dreamer8.yosimce.shared.dto.EvaluacionSupervisorDTO;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;

public class AprobarSupervisoresViewD extends Composite implements AprobarSupervisoresView {

	private static AprobarSupervisoresViewDUiBinder uiBinder = GWT
			.create(AprobarSupervisoresViewDUiBinder.class);

	interface AprobarSupervisoresViewDUiBinder extends
			UiBinder<Widget, AprobarSupervisoresViewD> {
	}
	
	 interface Style extends CssResource {
		 String childCell();
		 String groupHeaderCell();
	 }

	@UiField OverMenuBar menu;
	@UiField MenuItem menuItem;
	@UiField(provided = true) DataGrid<EvaluacionSupervisorDTO> dataGrid;
	@UiField(provided = true) SimplePager pager;
	
	private Column<EvaluacionSupervisorDTO,Boolean> puntualidadColumn;
	private Column<EvaluacionSupervisorDTO,Boolean> presentacionColumn;
	private Column<EvaluacionSupervisorDTO,Boolean> generalColumn;
	
	private AprobarSupervisoresPresenter presenter;
	
	public AprobarSupervisoresViewD() {
		dataGrid = new DataGrid<EvaluacionSupervisorDTO>(EvaluacionSupervisorDTO.KEY_PROVIDER);
		initWidget(uiBinder.createAndBindUi(this));
		
		menu.setOverItem(menuItem);
		menu.setOverCommand(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				presenter.toggleMenu();
			}
		});
		buildTable();
	}
	
	@Override
	public void setPresenter(AprobarSupervisoresPresenter presenter) {
		this.presenter = presenter;
	}
	
	@Override
	public void setSupervisores(ArrayList<EvaluacionSupervisorDTO> supervisores) {
		dataGrid.setPageSize(supervisores.size()+1);
		dataGrid.setVisibleRange(0, supervisores.size()+1);
		dataGrid.setRowCount(supervisores.size());
		dataGrid.setRowData(supervisores);
	}
	
	@Override
	public void setGeneralFieldUpdater(
			FieldUpdater<EvaluacionSupervisorDTO, Boolean> updater) {
		generalColumn.setFieldUpdater(updater);
	}
	
	@Override
	public void setPuntualidadFieldUpdater(
			FieldUpdater<EvaluacionSupervisorDTO, Boolean> updater) {
		puntualidadColumn.setFieldUpdater(updater);
	}
	
	@Override
	public void setPresentacionFieldUpdater(
			FieldUpdater<EvaluacionSupervisorDTO, Boolean> updater) {
		presentacionColumn.setFieldUpdater(updater);
	}
	
	private void buildTable(){
		Column<EvaluacionSupervisorDTO,String> rutColumn = new Column<EvaluacionSupervisorDTO, String>(new TextCell()) {

			@Override
			public String getValue(EvaluacionSupervisorDTO o) {
				return o.getSupervisor().getRut();
			}
		};
		rutColumn.setSortable(false);
		dataGrid.addColumn(rutColumn,"Rut");
		
		Column<EvaluacionSupervisorDTO,String> nombreColumn = new Column<EvaluacionSupervisorDTO, String>(new TextCell()) {

			@Override
			public String getValue(EvaluacionSupervisorDTO o) {
				return o.getSupervisor().getNombres()+ " "+o.getSupervisor().getApellidoPaterno()+" "+o.getSupervisor().getApellidoMaterno() ;
			}
		};
		nombreColumn.setSortable(false);
		dataGrid.addColumn(nombreColumn,"Nombres");
		
		puntualidadColumn = new Column<EvaluacionSupervisorDTO, Boolean>(new CheckboxCell()) {

			@Override
			public Boolean getValue(EvaluacionSupervisorDTO o) {
				return o.getPuntualidad() != null && o.getPuntualidad()>0;
			}
		};
		puntualidadColumn.setSortable(false);
		dataGrid.addColumn(puntualidadColumn,"Puntualidad");
		
		presentacionColumn = new Column<EvaluacionSupervisorDTO, Boolean>(new CheckboxCell()) {

			@Override
			public Boolean getValue(EvaluacionSupervisorDTO o) {
				return o.getPresentacionPersonal()!=null && o.getPresentacionPersonal()>0;
			}
		};
		presentacionColumn.setSortable(false);
		dataGrid.addColumn(presentacionColumn,"Presentación personal");
		
		generalColumn = new Column<EvaluacionSupervisorDTO, Boolean>(new CheckboxCell()) {

			@Override
			public Boolean getValue(EvaluacionSupervisorDTO o) {
				return o.getGeneral()!= null && o.getGeneral()>0;
			}
		};
		generalColumn.setSortable(false);
		dataGrid.addColumn(generalColumn,"Cumplió");
		
	}

}
