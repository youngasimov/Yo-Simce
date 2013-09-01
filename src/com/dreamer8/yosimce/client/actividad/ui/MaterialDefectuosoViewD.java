package com.dreamer8.yosimce.client.actividad.ui;

import java.util.ArrayList;

import com.dreamer8.yosimce.client.ui.ImageButton;
import com.dreamer8.yosimce.client.ui.PlaceHolderTextBox;
import com.dreamer8.yosimce.client.ui.ViewUtils;
import com.dreamer8.yosimce.shared.dto.CursoDTO;
import com.dreamer8.yosimce.shared.dto.EstadoSincronizacionDTO;
import com.dreamer8.yosimce.shared.dto.MaterialDefectuosoDTO;
import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class MaterialDefectuosoViewD extends Composite implements MaterialDefectuosoView {

	private static MaterialDefectuosoViewDUiBinder uiBinder = GWT
			.create(MaterialDefectuosoViewDUiBinder.class);

	interface MaterialDefectuosoViewDUiBinder extends
			UiBinder<Widget, MaterialDefectuosoViewD> {
	}

	@UiField HTML establecimientoSeleccionado;
	@UiField ImageButton cambiarButton;
	@UiField PlaceHolderTextBox materialBox;
	@UiField ListBox estadoBox;
	@UiField Button addButton;
	@UiField FlowPanel addMaterialBar;
	@UiField(provided = true) DataGrid<MaterialDefectuosoDTO> dataGrid;
	
	private MaterialDefectuosoPresenter presenter;
	private ArrayList<EstadoSincronizacionDTO> estados;
	
	public MaterialDefectuosoViewD() {
		
		dataGrid = new DataGrid<MaterialDefectuosoDTO>(MaterialDefectuosoDTO.KEY_PROVIDER);
		
		initWidget(uiBinder.createAndBindUi(this));
		buildTable();
	}
	
	@UiHandler("cambiarButton")
	void onCambiarCursoButton(ClickEvent event){
		presenter.onCambiarCursoClick();
	}
	
	@UiHandler("addButton")
	void onAddMaterialButton(ClickEvent event){
		if(materialBox.getValue() == null || materialBox.getValue().length()<1){
			return;
		}
		MaterialDefectuosoDTO md = new MaterialDefectuosoDTO();
		md.setIdMaterial(materialBox.getValue());
		int idEstado = Integer.parseInt(estadoBox.getValue(estadoBox.getSelectedIndex()));
		for(EstadoSincronizacionDTO e:estados){
			if(e.getIdEstadoSincronizacion() == idEstado){
				md.setEstado(e);
				break;
			}
		}
		materialBox.setValue("");
		presenter.onAddMaterialDefectuoso(md);
	}
	
	@Override
	public void setAddMaterialDefectuosoPanelVisivility(boolean visible) {
		addMaterialBar.setVisible(visible);
	}
	
	@Override
	public void setMaterialDefectuoso(ArrayList<MaterialDefectuosoDTO> material) {
		dataGrid.setPageSize(material.size()+1);
		dataGrid.setRowCount(material.size());
		dataGrid.setRowData(material);
		dataGrid.setVisibleRange(0, material.size()+1);
	}

	@Override
	public void setEstadosSincronizacion(
			ArrayList<EstadoSincronizacionDTO> estados) {
		this.estados = estados;
		estadoBox.clear();
		for(EstadoSincronizacionDTO es:estados){
			estadoBox.addItem(es.getNombreEstado(),es.getIdEstadoSincronizacion()+"");
		}
	}
	
	@Override
	public void setCurso(CursoDTO curso) {
		establecimientoSeleccionado.setHTML(ViewUtils.limitarString(curso.getNombreEstablecimiento()+"-"+curso.getNombre(),35));
	}

	@Override
	public void setPresenter(MaterialDefectuosoPresenter presenter) {
		this.presenter = presenter;
	}
	
	private void buildTable(){
		
		Column<MaterialDefectuosoDTO,String> materialColumn = new Column<MaterialDefectuosoDTO, String>(new TextCell()) {

			@Override
			public String getValue(MaterialDefectuosoDTO o) {
				return o.getIdMaterial();
			}
		};
		materialColumn.setSortable(false);
		dataGrid.addColumn(materialColumn,"Indetificador de material");
		
		Column<MaterialDefectuosoDTO,String> estadoColumn = new Column<MaterialDefectuosoDTO, String>(new TextCell()) {

			@Override
			public String getValue(MaterialDefectuosoDTO o) {
				return o.getEstado().getNombreEstado();
			}
		};
		estadoColumn.setSortable(false);
		dataGrid.addColumn(estadoColumn,"Estado material");
		
		Column<MaterialDefectuosoDTO,String> removeColumn = new Column<MaterialDefectuosoDTO, String>(new ButtonCell()) {

			@Override
			public String getValue(MaterialDefectuosoDTO o) {
				return "Eliminar";
			}
		};
		removeColumn.setSortable(false);
		removeColumn.setFieldUpdater(new FieldUpdater<MaterialDefectuosoDTO, String>() {
			
			@Override
			public void update(int index, MaterialDefectuosoDTO object, String value) {
				presenter.onRemoveMaterialDefectuoso(object);
			}
		});
		dataGrid.addColumn(removeColumn,"");
	}
}
