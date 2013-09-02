package com.dreamer8.yosimce.client.actividad.ui;

import com.dreamer8.yosimce.shared.dto.EvaluacionUsuarioDTO;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class AprobarSupervisoresViewD extends Composite implements AprobarSupervisoresView {

	private static AprobarSupervisoresViewDUiBinder uiBinder = GWT
			.create(AprobarSupervisoresViewDUiBinder.class);

	interface AprobarSupervisoresViewDUiBinder extends
			UiBinder<Widget, AprobarSupervisoresViewD> {
	}

	
	@UiField(provided = true) DataGrid<EvaluacionUsuarioDTO> dataGrid;
	
	private AprobarSupervisoresPresenter presenter;
	
	public AprobarSupervisoresViewD() {
		dataGrid = new DataGrid<EvaluacionUsuarioDTO>(EvaluacionUsuarioDTO.KEY_PROVIDER);
		initWidget(uiBinder.createAndBindUi(this));
		
		buildTable();
	}
	
	@Override
	public void setPresenter(AprobarSupervisoresPresenter presenter) {
		this.presenter = presenter;
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
		
		
		
	}

}
