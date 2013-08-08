package com.dreamer8.yosimce.client.administracion.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.dreamer8.yosimce.shared.dto.PermisoDTO;
import com.dreamer8.yosimce.shared.dto.TipoUsuarioDTO;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.SafeHtmlHeader;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.HasData;

public class PermisosViewD extends Composite implements PermisosView {

	private static PermisosViewDUiBinder uiBinder = GWT
			.create(PermisosViewDUiBinder.class);

	interface PermisosViewDUiBinder extends UiBinder<Widget, PermisosViewD> {
	}

	@UiField Button updateButton;
	@UiField Button updateViewButton;
	@UiField(provided = true) DataGrid<PermisoDTO> dataGrid;
	
	private PermisosPresenter presenter;
	
	public PermisosViewD() {
		dataGrid = new DataGrid<PermisoDTO>(PermisoDTO.KEY_PROVIDER);
		dataGrid.setPageSize(500);
		initWidget(uiBinder.createAndBindUi(this));
		buildStaticColumns();
	}
	
	@UiHandler("updateButton")
	void onUpdateButtonClick(ClickEvent event){
		presenter.onUpdatePermisosClick();
	}
	
	@UiHandler("updateViewButton")
	void onUpdateViewClick(ClickEvent event){
		presenter.onUpdateTablaClick();
	}

	@Override
	public void setTiposUsuarios(ArrayList<TipoUsuarioDTO> tiposUsuario) {
		dataGrid.setRowData(new ArrayList<PermisoDTO>());
		for(int i=0; i <dataGrid.getColumnCount(); i++){
			dataGrid.removeColumn(i);
		}
		buildStaticColumns();
		Collections.sort(tiposUsuario, new Comparator<TipoUsuarioDTO>(){
			@Override
			public int compare(TipoUsuarioDTO arg0, TipoUsuarioDTO arg1) {
				return arg0.getId().compareTo(arg1.getId());
			}
		});
		PermisoColumn hasPermisoColumn;
		for(int i = 0; i<tiposUsuario.size();i++){
			hasPermisoColumn =new PermisoColumn(new CheckboxCell(), tiposUsuario.get(i).getId());
			dataGrid.addColumn(hasPermisoColumn, new SafeHtmlHeader(SafeHtmlUtils.fromSafeConstant(tiposUsuario.get(i).getTipoUsuario())));
	        dataGrid.setColumnWidth(hasPermisoColumn, 150, Unit.PX);
		}
	}
	
	private void buildStaticColumns(){
		Column<PermisoDTO, String> moduloColumn =new Column<PermisoDTO, String>(new TextCell()) {
            @Override
            public String getValue(PermisoDTO object) {
                return object.getClase();
            }
        };
        moduloColumn.setSortable(false);
        dataGrid.addColumn(moduloColumn, new SafeHtmlHeader(SafeHtmlUtils.fromSafeConstant("Modulo")));
        dataGrid.setColumnWidth(moduloColumn, 200, Unit.PX);
        
        Column<PermisoDTO, String> permisoColumn =new Column<PermisoDTO, String>(new TextCell()) {
            @Override
            public String getValue(PermisoDTO object) {
                return object.getClase();
            }
        };
        permisoColumn.setSortable(false);
        dataGrid.addColumn(permisoColumn, new SafeHtmlHeader(SafeHtmlUtils.fromSafeConstant("Permiso")));
        dataGrid.setColumnWidth(permisoColumn, 200, Unit.PX);
	}
	
	public class PermisoColumn extends Column<PermisoDTO, Boolean>{

		private int idTipoUsuario;
		
		public PermisoColumn(CheckboxCell cell, int tipoUsuarioId) {
			super(cell);
			idTipoUsuario = tipoUsuarioId;
		}

		@Override
		public Boolean getValue(PermisoDTO p) {
			return p.getIdTiposUsuariosPermitidos().contains(idTipoUsuario);
		}
		
	}

	@Override
	public void setPresenter(PermisosPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public HasData<PermisoDTO> getDataDisplay() {
		return dataGrid;
	}

	@Override
	public int getColumnUserId(int column) {
		if(dataGrid.getColumn(column) instanceof PermisoColumn){
			PermisoColumn pc = (PermisoColumn)dataGrid.getColumn(column);
			return pc.idTipoUsuario;
		}
		return 0;
	}
}
