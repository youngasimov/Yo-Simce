package com.dreamer8.yosimce.client.actividad.ui;

import gwtupload.client.IUploader;
import gwtupload.client.SingleUploader;

import java.util.ArrayList;
import java.util.Date;

import com.dreamer8.yosimce.client.ui.ImageButton;
import com.dreamer8.yosimce.client.ui.ScoreSelector;
import com.dreamer8.yosimce.client.ui.ViewUtils;
import com.dreamer8.yosimce.client.ui.eureka.TimeBox;
import com.dreamer8.yosimce.client.ui.eureka.ValueTextBox;
import com.dreamer8.yosimce.client.ui.resources.SimceResources;
import com.dreamer8.yosimce.shared.dto.ContingenciaDTO;
import com.dreamer8.yosimce.shared.dto.EstadoAgendaDTO;
import com.dreamer8.yosimce.shared.dto.TipoContingenciaDTO;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.ImageCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
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
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
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
	@UiField ValueTextBox totalAlumnosBox;
	@UiField ValueTextBox alumnosAusentesBox;
	@UiField ValueTextBox alumnosDsBox;
	@UiField DecoratorPanel cuestionariosPanel;
	@UiField ValueTextBox cuestionariosTotalesBox;
	@UiField ValueTextBox cuestionariosEntregadosBox;
	@UiField ValueTextBox cuestionariosRecibidosBox;
	@UiField DecoratorPanel contingenciasPanel;
	@UiField CheckBox usoMaterialBox;
	@UiField TextBox detallesUsoBox;
	@UiField DecoratorPanel evaluacionPanel;
	@UiField ScoreSelector procedimientoScoreSelector;
	@UiField(provided=true) SingleUploader uploader;
	
	private FormActividadPresenter presenter;
	private UserDTO examinador;
	private ArrayList<TipoContingenciaDTO> tiposContingencia;
	private ExaminadorSelectorViewD examinadorSelector;
	private boolean uploading;
	
	public FormActividadViewD() {
		uploader = new SingleUploader();
		uploader.setAutoSubmit(true);
		inicioActividadBox = new TimeBox(new Date(0),false);
		inicioPruebaBox = new TimeBox(new Date(0),false);
		terminoPruebaBox = new TimeBox(new Date(0),false);
		contingenciasTable = new DataGrid<ContingenciaDTO>();
		initWidget(uiBinder.createAndBindUi(this));
		examinadorSelector = new ExaminadorSelectorViewD();
		estadoBox.addItem("seleccione estado actividad","-1");
		
		
		
		buildTable();
		contingenciasTable.setRowCount(0);
		
		uploader.addOnStartUploadHandler(new IUploader.OnStartUploaderHandler() {
			
			@Override
			public void onStart(IUploader uploader) {
				uploading = true;
			}
		});
		
		uploader.addOnFinishUploadHandler(new IUploader.OnFinishUploaderHandler() {
			
			@Override
			public void onFinish(IUploader uploader) {
				uploading = false;
			}
		});
		
	}
	
	
	@UiFactory
	public static SimceResources getResources() {
		return SimceResources.INSTANCE;
	}
	
	@UiHandler("estadoBox")
	void onEstadoChange(ChangeEvent event){
		String selected = estadoBox.getValue(estadoBox.getSelectedIndex());
		presenter.onEstadoChange(Integer.parseInt(selected));
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
		if(tiposContingencia == null || tiposContingencia.isEmpty()){
			return;
		}
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
		detalleContingenciaBox.setValue("");
		inhabilitaContingenciaBox.setValue(false);
		presenter.onAgregarContingencia(c);
	}
	
	@UiHandler("changeExaminadorButton")
	void onChangeExaminadorClick(ClickEvent event){
		examinadorSelector.show();
	}



	@Override
	public void setPresenter(FormActividadPresenter presenter) {
		this.presenter = presenter;
		examinadorSelector.setPresenter(presenter);
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
		examinadorSelector.hide();
	}


	@Override
	public UserDTO getExaminador() {
		return examinador;
	}
	
	@Override
	public void setExaminadoresSuplentes(ArrayList<UserDTO> examinadores) {
		examinadorSelector.setExaminadores(examinadores);
	}
	

	@Override
	public void setEstados(ArrayList<EstadoAgendaDTO> estados) {
		this.estadoBox.clear();
		for(EstadoAgendaDTO estado:estados){
			this.estadoBox.addItem(estado.getEstado(),estado.getId()+"");
		}
	}


	@Override
	public void selectEstado(EstadoAgendaDTO estado) {
		for(int i=0; i<estadoBox.getItemCount(); i++){
			if(Integer.parseInt(estadoBox.getValue(i)) == estado.getId()){
				estadoBox.setSelectedIndex(i);
				break;
			}
		}
	}


	@Override
	public void setInicioActividad(Date hora) {
		this.inicioActividadBox.setValue(hora.getTime());
	}


	@Override
	public Date getInicioActividad() {
		return new Date(inicioActividadBox.getTime());
	}


	@Override
	public void setInicioPrueba(Date hora) {
		this.inicioPruebaBox.setValue(hora.getTime());
	}


	@Override
	public Date getInicioPrueba() {
		return new Date(inicioPruebaBox.getTime());
	}


	@Override
	public void setTerminoPrueba(Date hora) {
		this.terminoPruebaBox.setValue(hora.getTime());
	}


	@Override
	public Date getTerminoPrueba() {
		return new Date(terminoPruebaBox.getTime());
	}


	@Override
	public void setTotalAlumnos(int total) {
		totalAlumnosBox.setValue(total+"");
	}


	@Override
	public int getTotalAlumnos() {
		return Integer.parseInt(totalAlumnosBox.getValue());
	}


	@Override
	public void setAlumnosAusentes(int ausentes) {
		alumnosAusentesBox.setValue(ausentes+"");
	}


	@Override
	public int getAlumnosAusentes() {
		return Integer.parseInt(alumnosAusentesBox.getValue());
	}


	@Override
	public void setAlumnosDS(int ds) {
		alumnosDsBox.setValue(ds+"");
	}


	@Override
	public int getAlumnosDS() {
		return Integer.parseInt(alumnosDsBox.getValue());
	}


	@Override
	public void setCuestionariosTotales(int total) {
		cuestionariosTotalesBox.setValue(total+"");
	}


	@Override
	public int getCuestionariosTotales() {
		return Integer.parseInt(cuestionariosTotalesBox.getValue());
	}


	@Override
	public void setCuestionariosEntregados(int entregados) {
		cuestionariosEntregadosBox.setValue(entregados+"");
	}


	@Override
	public int getCuestionariosEntregados() {
		return Integer.parseInt(cuestionariosEntregadosBox.getValue());
	}


	@Override
	public void setCuestionariosRecibidos(int recibidos) {
		cuestionariosRecibidosBox.setValue(recibidos+"");
	}


	@Override
	public int getCuestionariosRecibidos() {
		return Integer.parseInt(cuestionariosRecibidosBox.getValue());
	}


	@Override
	public void setUsoMaterialContingencia(boolean contingencia) {
		usoMaterialBox.setValue(contingencia);
	}


	@Override
	public boolean getUsoMaterialContingencia() {
		return usoMaterialBox.getValue();
	}


	@Override
	public void setDetalleUsoMaterialContingencia(String detalle) {
		detallesUsoBox.setValue(detalle);
	}


	@Override
	public String getDetalleUsoMaterialContingencia() {
		return detallesUsoBox.getValue();
	}


	@Override
	public void setEvaluacionGeneral(Integer evaluacion) {
		procedimientoScoreSelector.setValue(evaluacion);
	}


	@Override
	public Integer getEvaluacionGeneral() {
		return procedimientoScoreSelector.getValue();
	}
	

	@Override
	public void enableAddContingencia(boolean enable) {
		contingenciaButton.setEnabled(true);
	}
	
	@Override
	public void showSeccionExaminador(boolean show) {
		datosExaminadorPanel.setVisible(show);
	}


	@Override
	public void showForm(boolean show) {
		formPanel.setVisible(show);
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
		contingenciasTable.setColumnWidth(inhabilitaColumn, "90px");
		
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
		contingenciasTable.addColumn(deleteColumn,"");
		contingenciasTable.setColumnWidth(deleteColumn, "90px");
		
	}
}