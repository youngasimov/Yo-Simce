package com.dreamer8.yosimce.client.actividad.ui;

import java.util.ArrayList;

import com.dreamer8.yosimce.client.ui.OverMenuBar;
import com.dreamer8.yosimce.shared.dto.EvaluacionUsuarioDTO;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;

public class AprobarSupervisoresViewD extends Composite implements AprobarSupervisoresView {

	private static AprobarSupervisoresViewDUiBinder uiBinder = GWT
			.create(AprobarSupervisoresViewDUiBinder.class);

	interface AprobarSupervisoresViewDUiBinder extends
			UiBinder<Widget, AprobarSupervisoresViewD> {
	}

	@UiField OverMenuBar menu;
	@UiField MenuItem menuItem;
	@UiField(provided = true) DataGrid<EvaluacionUsuarioDTO> dataGrid;
	
	private Column<EvaluacionUsuarioDTO,Boolean> puntualidadColumn;
	private Column<EvaluacionUsuarioDTO,Boolean> presentacionColumn;
	private Column<EvaluacionUsuarioDTO,Boolean> formColumn;
	private Column<EvaluacionUsuarioDTO,Boolean> generalColumn;
	
	private AprobarSupervisoresPresenter presenter;
	
	public AprobarSupervisoresViewD() {
		dataGrid = new DataGrid<EvaluacionUsuarioDTO>(EvaluacionUsuarioDTO.KEY_PROVIDER);
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
	public void setSupervisores(ArrayList<EvaluacionUsuarioDTO> supervisores) {
		dataGrid.setPageSize(supervisores.size()+1);
		dataGrid.setRowCount(supervisores.size());
		dataGrid.setVisibleRange(0, supervisores.size()+1);
		dataGrid.setRowData(supervisores);
	}
	
	@Override
	public void setGeneralFieldUpdater(
			FieldUpdater<EvaluacionUsuarioDTO, Boolean> updater) {
		generalColumn.setFieldUpdater(updater);
	}
	
	@Override
	public void setPuntualidadFieldUpdater(
			FieldUpdater<EvaluacionUsuarioDTO, Boolean> updater) {
		puntualidadColumn.setFieldUpdater(updater);
	}
	
	@Override
	public void setFormularioFieldUpdater(
			FieldUpdater<EvaluacionUsuarioDTO, Boolean> updater) {
		formColumn.setFieldUpdater(updater);
	}
	
	@Override
	public void setPresentacionFieldUpdater(
			FieldUpdater<EvaluacionUsuarioDTO, Boolean> updater) {
		presentacionColumn.setFieldUpdater(updater);
	}
	
	private void buildTable(){
		Column<EvaluacionUsuarioDTO,String> rutColumn = new Column<EvaluacionUsuarioDTO, String>(new TextCell()) {

			@Override
			public String getValue(EvaluacionUsuarioDTO o) {
				return o.getUsuario().getRut();
			}
		};
		rutColumn.setSortable(false);
		dataGrid.addColumn(rutColumn,"Rut");
		
		Column<EvaluacionUsuarioDTO,String> nombresColumn = new Column<EvaluacionUsuarioDTO, String>(new TextCell()) {

			@Override
			public String getValue(EvaluacionUsuarioDTO o) {
				return o.getUsuario().getNombres();
			}
		};
		nombresColumn.setSortable(false);
		dataGrid.addColumn(nombresColumn,"Nombres");
		
		Column<EvaluacionUsuarioDTO,String> paternoColumn = new Column<EvaluacionUsuarioDTO, String>(new TextCell()) {

			@Override
			public String getValue(EvaluacionUsuarioDTO o) {
				return o.getUsuario().getApellidoPaterno();
			}
		};
		paternoColumn.setSortable(false);
		dataGrid.addColumn(paternoColumn,"A. paterno");
		
		Column<EvaluacionUsuarioDTO,String> maternoColumn = new Column<EvaluacionUsuarioDTO, String>(new TextCell()) {

			@Override
			public String getValue(EvaluacionUsuarioDTO o) {
				return o.getUsuario().getApellidoMaterno();
			}
		};
		maternoColumn.setSortable(false);
		dataGrid.addColumn(maternoColumn,"A. materno");
		
		puntualidadColumn = new Column<EvaluacionUsuarioDTO, Boolean>(new CheckboxCell()) {

			@Override
			public Boolean getValue(EvaluacionUsuarioDTO o) {
				return o.getPuntualidad() != null && o.getPuntualidad()>0;
			}
		};
		puntualidadColumn.setSortable(false);
		dataGrid.addColumn(puntualidadColumn,"Puntualidad");
		
		presentacionColumn = new Column<EvaluacionUsuarioDTO, Boolean>(new CheckboxCell()) {

			@Override
			public Boolean getValue(EvaluacionUsuarioDTO o) {
				return o.getPresentacionPersonal()!=null && o.getPresentacionPersonal()>0;
			}
		};
		presentacionColumn.setSortable(false);
		dataGrid.addColumn(presentacionColumn,"Presentación personal");
		
		formColumn = new Column<EvaluacionUsuarioDTO, Boolean>(new CheckboxCell()) {

			@Override
			public Boolean getValue(EvaluacionUsuarioDTO o) {
				return o.getFormulario()!= null && o.getFormulario()>0;
			}
		};
		formColumn.setSortable(false);
		dataGrid.addColumn(formColumn,"Llenado formulario");
		
		generalColumn = new Column<EvaluacionUsuarioDTO, Boolean>(new CheckboxCell()) {

			@Override
			public Boolean getValue(EvaluacionUsuarioDTO o) {
				return o.getGeneral()!= null && o.getGeneral()>0;
			}
		};
		generalColumn.setSortable(false);
		dataGrid.addColumn(generalColumn,"Cumplió");
		
	}

}
