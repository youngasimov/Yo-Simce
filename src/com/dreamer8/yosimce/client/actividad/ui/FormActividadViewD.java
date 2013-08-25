package com.dreamer8.yosimce.client.actividad.ui;

import java.util.ArrayList;
import java.util.Date;

import com.dreamer8.yosimce.client.ui.ImageButton;
import com.dreamer8.yosimce.client.ui.ScoreSelector;
import com.dreamer8.yosimce.client.ui.ViewUtils;
import com.dreamer8.yosimce.client.ui.eureka.TimeBox;
import com.dreamer8.yosimce.client.ui.resources.SimceResources;
import com.dreamer8.yosimce.shared.dto.ContingenciaDTO;
import com.dreamer8.yosimce.shared.dto.TipoContingenciaDTO;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.ImageCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class FormActividadViewD extends Composite implements FormActividadView {

	private static FormActividadViewDUiBinder uiBinder = GWT
			.create(FormActividadViewDUiBinder.class);

	interface FormActividadViewDUiBinder extends
			UiBinder<Widget, FormActividadViewD> {
	}

	
	@UiField HTML establecimientoSeleccionado;
	@UiField ImageButton changeButton;
	@UiField ImageButton saveButton;
	@UiField ImageButton save2Button;
	@UiField Label nombreEstablecimientoLabel;
	@UiField Label rbdLabel;
	@UiField Label cursoLabel;
	@UiField Label tipoLabel;
	@UiField Label regionLabel;
	@UiField Label comunaLabel;
	@UiField DecoratorPanel estadoAplicacionPanel;
	@UiField ListBox estadoBox;
	@UiField ListBox tipoContingenciaBox;
	@UiField TextBox detalleContingenciaBox;
	@UiField CheckBox inhabilitaContingenciaBox;
	@UiField  Button contingenciaButton;
	@UiField(provided=true) DataGrid<ContingenciaDTO> contingenciasTable;
	@UiField DecoratorPanel datosExaminadorPanel;
	@UiField Label nombreExaminadorLabel;
	@UiField Label rutExaminadorLabel;
	@UiField Button changeExaminadorButton;
	@UiField ScoreSelector presentacionPersonalScoreSelector;
	@UiField ScoreSelector puntualidadScoreSelector;
	@UiField ScoreSelector llenadoFormularioScoreSelector;
	@UiField ScoreSelector desempenoScoreSelector;
	@UiField DecoratorPanel horasActividadPanel;
	@UiField HTMLPanel formPanel;
	@UiField(provided=true) TimeBox inicioActividadBox;
	@UiField(provided=true) TimeBox inicioPruebaBox;
	@UiField(provided=true) TimeBox terminoPruebaBox;
	@UiField DecoratorPanel participacionPanel;
	@UiField IntegerBox totalAlumnosBox;
	@UiField IntegerBox alumnosAusentesBox;
	@UiField IntegerBox alumnosDsBox;
	@UiField DecoratorPanel cuestionariosPanel;
	@UiField IntegerBox cuestionariosTotalesBox;
	@UiField IntegerBox cuestionariosEntregadosBox;
	@UiField IntegerBox cuestionariosNoEntregadosBox;
	@UiField DecoratorPanel contingenciasPanel;
	@UiField CheckBox usoMaterialBox;
	@UiField TextBox detallesUsoBox;
	@UiField DecoratorPanel evaluacionPanel;
	@UiField ScoreSelector procedimientoScoreSelector;
	@UiField FileUpload formularioControlFile;
	
	private FormActividadPresenter presenter;
	private UserDTO examinador;
	private ArrayList<TipoContingenciaDTO> tiposContingencia;
	
	public FormActividadViewD() {
		inicioActividadBox = new TimeBox(new Date(0),false);
		inicioPruebaBox = new TimeBox(new Date(0),false);
		terminoPruebaBox = new TimeBox(new Date(0),false);
		contingenciasTable = new DataGrid<ContingenciaDTO>();
		initWidget(uiBinder.createAndBindUi(this));
		
		estadoBox.addItem("seleccione estado actividad","0");
		estadoBox.addItem("Realizado","1");
		estadoBox.addItem("Anulado", "2");
		
		buildTable();
	}
	
	
	@UiFactory
	public static SimceResources getResources() {
		return SimceResources.INSTANCE;
	}
	
	@UiHandler("changeButton")
	void onChangeActividadClick(ClickEvent event){
		presenter.onCambiarCursoClick();
	}
	
	@UiHandler("saveButton")
	void onSaveClick(ClickEvent event){
		presenter.guardarFormulario();
	}
	
	@UiHandler("save2Button")
	void onSave2Click(ClickEvent event){
		presenter.guardarFormulario();
	}
	
	@UiHandler("contingenciaButton")
	void onAgregarContingenciaClick(ClickEvent event){
		ContingenciaDTO c = new ContingenciaDTO();
		c.setDetalleContingencia(detalleContingenciaBox.getText());
		c.setInabilitante(inhabilitaContingenciaBox.getValue());
		int id = Integer.parseInt(tipoContingenciaBox.getValue(tipoContingenciaBox.getSelectedIndex()));
		for(TipoContingenciaDTO tipo:tiposContingencia){
			if(tipo.getId() == id){
				c.setTipoContingencia(tipo);
				break;
			}
		}
		presenter.onAgregarContingencia(c);
	}



	@Override
	public void setPresenter(FormActividadPresenter presenter) {
		this.presenter = presenter;
	}


	@Override
	public void setNombreEstablecimiento(String establecimiento) {
		this.establecimientoSeleccionado.setHTML(ViewUtils.limitarString(establecimiento,35));
		nombreEstablecimientoLabel.setText(establecimiento);
	}


	@Override
	public void setRbd(String rbd) {
		rbdLabel.setText(rbd);
	}


	@Override
	public void setCurso(String curso) {
		cursoLabel.setText(curso);
	}
	
	public void setTipoCurso(String tipo){
		tipoLabel.setText(tipo);
	}


	@Override
	public void setRegion(String region) {
		regionLabel.setText(region);
	}


	@Override
	public void setComuna(String comuna) {
		comunaLabel.setText(comuna);
	}
	
	@Override
	public void setTiposContingencia(ArrayList<TipoContingenciaDTO> tipos) {
		tiposContingencia = tipos;
		tipoContingenciaBox.clear();
		for(TipoContingenciaDTO tipo:tipos){
			tipoContingenciaBox.addItem(tipo.getContingencia(),tipo.getId()+"");
		}
		
	}


	@Override
	public void setContingencias(ArrayList<ContingenciaDTO> contingencias) {
		contingenciasTable.setRowCount(contingencias.size());
		contingenciasTable.setRowData(contingencias);
		contingenciasTable.setVisibleRange(0, contingencias.size());
	}


	@Override
	public void setExaminador(UserDTO user) {
		this.examinador = user;
		this.nombreExaminadorLabel.setText(examinador.getNombres()+" "+examinador.getApellidoPaterno()+" "+examinador.getApellidoMaterno());
		this.rutExaminadorLabel.setText(examinador.getRut());
	}


	@Override
	public UserDTO getExaminador() {
		return examinador;
	}
	
	private void buildTable(){
		Column<ContingenciaDTO,String> contingenciaColumn = new Column<ContingenciaDTO, String>(new TextCell()) {

			@Override
			public String getValue(ContingenciaDTO o) {
				return o.getTipoContingencia().getContingencia();
			}
		};
		contingenciaColumn.setSortable(false);
		contingenciasTable.addColumn(contingenciaColumn,"Contingencia");
		contingenciasTable.setColumnWidth(contingenciaColumn, "150px");
		
		Column<ContingenciaDTO,String> detalleColumn = new Column<ContingenciaDTO, String>(new TextCell()) {

			@Override
			public String getValue(ContingenciaDTO o) {
				return o.getDetalleContingencia();
			}
		};
		detalleColumn.setSortable(false);
		contingenciasTable.addColumn(detalleColumn,"Detalle");
		contingenciasTable.setColumnWidth(detalleColumn, "150px");
		
		Column<ContingenciaDTO,String> inhabilitaColumn = new Column<ContingenciaDTO, String>(new ImageCell()) {

			@Override
			public String getValue(ContingenciaDTO o) {
				if(o.getInabilitante()){
					return "/images/warning.png";
				}else{
					return "";
				}
			}
		};
		inhabilitaColumn.setSortable(false);
		contingenciasTable.addColumn(inhabilitaColumn,"Inhabilita");
		contingenciasTable.setColumnWidth(inhabilitaColumn, "100px");
		
		Column<ContingenciaDTO,String> deleteColumn = new Column<ContingenciaDTO,String>(new ButtonCell()){

			@Override
			public String getValue(ContingenciaDTO object) {
				return "Eliminar";
			}
		};
		
		deleteColumn.setSortable(false);
		deleteColumn.setFieldUpdater(new FieldUpdater<ContingenciaDTO, String>() {
			
			@Override
			public void update(int index, ContingenciaDTO object, String value) {
				presenter.onRemoveContingecia(object);
			}
		});
		contingenciasTable.addColumn(deleteColumn,"Inhabilita");
		contingenciasTable.setColumnWidth(deleteColumn, "100px");
		
	}
}