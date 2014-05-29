package com.dreamer8.yosimce.client.actividad.ui;

import gwtupload.client.IFileInput.FileInputType;
import gwtupload.client.IUploadStatus;
import gwtupload.client.IUploader;
import gwtupload.client.SingleUploader;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.dreamer8.yosimce.client.ui.ImageButton;
import com.dreamer8.yosimce.client.ui.OverMenuBar;
import com.dreamer8.yosimce.client.ui.ScoreSelector;
import com.dreamer8.yosimce.client.ui.ViewUtils;
import com.dreamer8.yosimce.client.ui.eureka.TimeBox;
import com.dreamer8.yosimce.client.ui.eureka.ValueTextBox;
import com.dreamer8.yosimce.client.ui.resources.SimceResources;
import com.dreamer8.yosimce.shared.dto.ContingenciaDTO;
import com.dreamer8.yosimce.shared.dto.DocumentoDTO;
import com.dreamer8.yosimce.shared.dto.EstadoAgendaDTO;
import com.dreamer8.yosimce.shared.dto.EvaluacionUsuarioDTO;
import com.dreamer8.yosimce.shared.dto.TipoContingenciaDTO;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.ImageCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class FormActividadViewD extends Composite implements FormActividadView {

	private static FormActividadViewDUiBinder uiBinder = GWT
			.create(FormActividadViewDUiBinder.class);

	interface FormActividadViewDUiBinder extends
			UiBinder<Widget, FormActividadViewD> {
	}
	

	@UiField OverMenuBar menu;
	@UiField MenuItem menuItem;
	@UiField MenuItem cursoItem;
	@UiField MenuItem saveItem;
	@UiField MenuItem cambiarItem;
	@UiField ImageButton save2Button;
	@UiField Label nombreEstablecimientoLabel;
	@UiField Label rbdLabel;
	@UiField Label cursoLabel;
	@UiField Label tipoLabel;
	@UiField Label regionLabel;
	@UiField Label comunaLabel;
	@UiField DecoratorPanel estadoAplicacionPanel;
	@UiField ListBox estadoBox;
	@UiField DateBox dateBox;
	@UiField ListBox tipoContingenciaBox;
	@UiField TextBox detalleContingenciaBox;
	@UiField CheckBox inhabilitaContingenciaBox;
	@UiField  Button contingenciaButton;
	@UiField(provided=true) DataGrid<ContingenciaDTO> contingenciasTable;
	@UiField DecoratorPanel datosExaminadorPanel;
	@UiField CheckBox realizadaPorSupervisorBox;
	@UiField Button agregarExaminadorButton;
	@UiField Button ausenteButton;
	@UiField(provided=true) CellList<EvaluacionUsuarioDTO> examinadoresList;
	@UiField HTMLPanel evaluacionExaminadorPanel;
	@UiField Label examinadorSeleccionadoLabel;
	@UiField ScoreSelector ppScoreSelector;
	@UiField ScoreSelector puScoreSelector;
	@UiField ScoreSelector lfScoreSelector;
	@UiField ScoreSelector geScoreSelector;
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
	@UiField Anchor fileLink;
	@UiField Label fileLabel;
	
	@UiField TextBox pcsTotalesBox;
	
	private FormActividadPresenter presenter;
	private ArrayList<TipoContingenciaDTO> tiposContingencia;
	private ExaminadorSelectorViewD examinadorSelector;
	private boolean uploading;
	private boolean fileUploaded;
	//private ArrayList<EvaluacionExaminador> evaluaciones;
	private String file;
	private ListDataProvider<ContingenciaDTO> contingenciasProvider;
	private ListDataProvider<EvaluacionUsuarioDTO> examinadoresProvider;
	private SingleSelectionModel<EvaluacionUsuarioDTO> examinadoresSelectionModel;
	
	private Logger logger = Logger.getLogger("");
	
	public FormActividadViewD() {
		file = null;
		uploader = new SingleUploader(FileInputType.LABEL);
		uploader.setAutoSubmit(true);
		inicioActividadBox = new TimeBox(new Date(0),false);
		inicioPruebaBox = new TimeBox(new Date(0),false);
		terminoPruebaBox = new TimeBox(new Date(0),false);
		contingenciasTable = new DataGrid<ContingenciaDTO>();
		contingenciasProvider = new ListDataProvider<ContingenciaDTO>();
		contingenciasProvider.addDataDisplay(contingenciasTable);
		examinadoresList = new CellList<EvaluacionUsuarioDTO>(new EvaluacionUsuarioCell());
		examinadoresList.setPageSize(5);
		examinadoresList.setVisibleRange(0, 5);
		examinadoresProvider = new ListDataProvider<EvaluacionUsuarioDTO>();
		examinadoresProvider.addDataDisplay(examinadoresList);
		examinadoresSelectionModel = new SingleSelectionModel<EvaluacionUsuarioDTO>();
		examinadoresList.setSelectionModel(examinadoresSelectionModel);
		initWidget(uiBinder.createAndBindUi(this));
		
		dateBox.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat(PredefinedFormat.DATE_LONG)));
		
		ppScoreSelector.setGroupName("pp");
		puScoreSelector.setGroupName("pu");
		lfScoreSelector.setGroupName("lf");
		geScoreSelector.setGroupName("ge");
		procedimientoScoreSelector.setGroupName("procedimiento");
		examinadorSelector = new ExaminadorSelectorViewD();
		estadoBox.addItem("seleccione estado actividad","-1");
		uploading = false;
		fileUploaded= false;
		examinadoresSelectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				presenter.onExaminadorSelected(examinadoresSelectionModel.getSelectedObject());
			}
		});
		menu.insertSeparator(2);
		menu.setOverItem(menuItem);
		menu.setOverCommand(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				presenter.toggleMenu();
			}
		});
		saveItem.setScheduledCommand(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				presenter.guardarFormulario();
			}
		});
		
		cambiarItem.setScheduledCommand(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				presenter.onCambiarCursoClick();
			}
		});
		
		buildTable();
		contingenciasTable.setRowCount(0);
		
		uploader.addOnStartUploadHandler(new IUploader.OnStartUploaderHandler() {
			
			@Override
			public void onStart(IUploader uploader) {
				uploading = true;
				fileUploaded = false;
				logger.log(Level.INFO, "Archivo start upload");
			}
		});
		
		uploader.addOnFinishUploadHandler(new IUploader.OnFinishUploaderHandler() {
			
			@Override
			public void onFinish(IUploader uploader) {
				uploading = false;
				if(uploader.getStatus().equals(IUploadStatus.Status.SUCCESS)){
					logger.log(Level.INFO, "Archivo uploaded success");
					fileUploaded = true;
					file = uploader.getServerInfo().getFileName();
					presenter.onDocumentoUploaded(file);
				}
			}
		});
		
	}
	
	
	
	@UiFactory
	public static SimceResources getResources() {
		return SimceResources.INSTANCE;
	}
	
	@UiHandler("ppScoreSelector")
	void onPresentacionPersonalValueChange(ValueChangeEvent<Integer> event){
		presenter.onEvaluacionPresentacionPersonalChange(event.getValue());
	}
	
	@UiHandler("puScoreSelector")
	void onPuntualidadValueChange(ValueChangeEvent<Integer> event){
		presenter.onEvaluacionPuntualidadChange(event.getValue());
	}
	
	@UiHandler("lfScoreSelector")
	void onLlenadoFormularioValueChange(ValueChangeEvent<Integer> event){
		presenter.onEvaluacionFormularioChange(event.getValue());
	}
	
	@UiHandler("geScoreSelector")
	void onGeneralValueChange(ValueChangeEvent<Integer> event){
		presenter.onEvaluacionGeneralChange(event.getValue());
	}
	
	@UiHandler("estadoBox")
	void onEstadoChange(ChangeEvent event){
		String selected = estadoBox.getValue(estadoBox.getSelectedIndex());
		presenter.onEstadoChange(Integer.parseInt(selected));
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
	
	@UiHandler("agregarExaminadorButton")
	void onAgregarExaminadorClick(ClickEvent event){
		examinadorSelector.show(new Command() {
			
			@Override
			public void execute() {
				UserDTO u = examinadorSelector.getSelectedUser();
				EvaluacionUsuarioDTO eval = new EvaluacionUsuarioDTO();
				eval.setUsuario(u);
				eval.setFormulario(0);
				eval.setGeneral(0);
				eval.setPresentacionPersonal(0);
				eval.setPuntualidad(0);
				presenter.onAddExaminador(eval);
				examinadorSelector.hide();
			}
		});
	}
	
	@UiHandler("realizadaPorSupervisorBox")
	void onEealizadaPorSupervisorBoxChange(ValueChangeEvent<Boolean> event){
		presenter.onActividadRealizadaPorSupervisor(event.getValue());
	}
	
	@UiHandler("ausenteButton")
	void onAusenteButtonClick(ClickEvent event){
		presenter.setSelectedExaminadorAusente();
	}
	
	@Override
	public void clear() {
		nombreEstablecimientoLabel.setText("");
		rbdLabel.setText("");
		cursoLabel.setText("");
		tipoLabel.setText("");
		regionLabel.setText("");
		comunaLabel.setText("");
		estadoBox.clear();
		dateBox.setValue(null);
		tipoContingenciaBox.clear();
		detalleContingenciaBox.setValue("");
		inhabilitaContingenciaBox.setValue(null);
		contingenciasProvider.getList().clear();
		realizadaPorSupervisorBox.setValue(null);
		examinadoresProvider.getList().clear();
		examinadorSeleccionadoLabel.setText("");
		ppScoreSelector.setValue(0);
		puScoreSelector.setValue(0);
		lfScoreSelector.setValue(0);
		geScoreSelector.setValue(0);
		Date d = new Date();
		inicioActividadBox.setValue(d.getTime());
		inicioPruebaBox.setValue(d.getTime());
		terminoPruebaBox.setValue(d.getTime());
		totalAlumnosBox.setValue("0");
		alumnosAusentesBox.setValue("0");
		alumnosDsBox.setValue("0");
		cuestionariosTotalesBox.setValue("0");
		cuestionariosEntregadosBox.setValue("0");
		cuestionariosRecibidosBox.setValue("0");
		usoMaterialBox.setValue(null);
		detallesUsoBox.setValue("");
		procedimientoScoreSelector.setValue(0);
		fileLink.setHref("");
		fileLink.setTarget("");
		fileLink.setText("");
		fileLabel.setText("");
		examinadorSelector.examinadorBox.setValue("");
		
		pcsTotalesBox.setValue("0");
		
		uploading = false;
		fileUploaded = false;
		file = null;
		enableAddContingencia(false);
		enableExaminadorActions(false);
		showForm(false);
	}
	
	@Override
	public void enableAddExaminador(boolean enabled) {
		agregarExaminadorButton.setEnabled(enabled);
	}
	
	@Override
	public void enableRemoveExaminador(boolean enabled) {
		ausenteButton.setEnabled(enabled);
	}
	
	@Override
	public void setSaveVisibility(boolean visible) {
		saveItem.setVisible(visible);
		save2Button.setVisible(visible);
	}

	@Override
	public boolean isUploading(){
		return uploading;
	}

	@Override
	public boolean isFileUploaded() {
		return fileUploaded;
	}
	
	@Override
	public Date getFechaActividad() {
		return dateBox.getValue();
	}
	
	@Override
	public void setFechaActividad(Date date) {
		dateBox.setValue(date);
	}
	
	@Override
	public void setPresenter(FormActividadPresenter presenter) {
		this.presenter = presenter;
		examinadorSelector.setPresenter(presenter);
	}


	@Override
	public void setNombreEstablecimiento(String establecimiento) {
		cursoItem.setHTML(ViewUtils.limitarString(establecimiento,40));
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
		contingenciasProvider.setList(contingencias);
	}


	@Override
	public void setExaminadores(ArrayList<EvaluacionUsuarioDTO> evaluacionesUsuario) {
		examinadoresProvider.setList(evaluacionesUsuario);
		Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				if(examinadoresProvider.getList() == null || examinadoresProvider.getList().isEmpty()){
					examinadoresSelectionModel.clear();
					presenter.onExaminadorSelected(null);
				}else{
					examinadoresSelectionModel.setSelected(examinadoresProvider.getList().get(0), true);
				}
			}
		});
	}
	
	@Override
	public ArrayList<EvaluacionUsuarioDTO> getExaminadores() {
		return (ArrayList<EvaluacionUsuarioDTO>)examinadoresProvider.getList();
	}
	
	@Override
	public void setExaminadoresSuplentes(ArrayList<UserDTO> examinadores) {
		examinadorSelector.setExaminadores(examinadores);
	}
	

	@Override
	public void setEstados(ArrayList<EstadoAgendaDTO> estados) {
		this.estadoBox.clear();
		this.estadoBox.addItem("Seleccionar estado...", "-1");
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
		this.inicioActividadBox.setValue((hora==null)?0:hora.getTime());
	}


	@Override
	public Date getInicioActividad() {
		return new Date(inicioActividadBox.getTime());
	}


	@Override
	public void setInicioPrueba(Date hora) {
		this.inicioPruebaBox.setValue((hora==null)?0:hora.getTime());
	}


	@Override
	public Date getInicioPrueba() {
		return new Date(inicioPruebaBox.getTime());
	}


	@Override
	public void setTerminoPrueba(Date hora) {
		this.terminoPruebaBox.setValue((hora==null)?0:hora.getTime());
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
	public String getUploadFile() {
		return file;
	}
	
	@Override
	public void setHyperlink(DocumentoDTO documento) {
		fileLink.setTarget("_blank");
		
		if(documento == null){
			fileLink.setHref("");
			fileLink.setText("");
			fileLink.setVisible(false);
			fileLabel.setText("");
			fileLabel.setVisible(false);
			file = null;
			fileUploaded = false;
		}
		else if((documento.getUrl() == null || documento.getUrl().isEmpty()) && documento.getName() != null && !documento.getName().isEmpty()){
			fileLink.setHref("");
			fileLink.setText("");
			fileLink.setVisible(false);
			fileLabel.setText(documento.getName());
			fileLabel.setVisible(true);
		}else if((documento.getUrl() == null || documento.getUrl().isEmpty()) && (documento.getName() == null || documento.getName().isEmpty())){
			fileLink.setHref("");
			fileLink.setText("");
			fileLink.setVisible(false);
			fileLabel.setText(documento.getName());
			fileLabel.setVisible(true);
		}else if(documento.getUrl()!=null && !documento.getUrl().isEmpty() && documento.getName()!=null && !documento.getName().isEmpty()){
			fileLink.setHref(documento.getUrl());
			fileLink.setText(documento.getName());
			fileLink.setVisible(true);
			fileLabel.setText("");
			fileLabel.setVisible(false);
		}else{
			fileLink.setHref("");
			fileLink.setText("");
			fileLink.setVisible(false);
			fileLabel.setText("");
			fileLabel.setVisible(false);
		}
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

	@Override
	public void setTotalAlumnosEnabled(boolean enabled) {
		totalAlumnosBox.setEnabled(enabled);
	}


	@Override
	public void setAlumnosDSEnabled(boolean enabled) {
		alumnosDsBox.setEnabled(enabled);
	}


	@Override
	public void setCuestionariosTotalesEnabled(boolean enabled) {
		cuestionariosTotalesBox.setEnabled(enabled);
	}


	@Override
	public void setCuestionariosEntregadosEnabled(boolean enabled) {
		cuestionariosEntregadosBox.setEnabled(enabled);
	}


	@Override
	public void showUsoMaterialComplementarioPanel(boolean visible) {
		contingenciasPanel.setVisible(visible);
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
					return "images/warning.png";
				}else{
					return "images/blank.png";
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


	@Override
	public void setEvaluacion(int pp, int pu, int lf, int ge) {
		ppScoreSelector.setValue(pp);
		puScoreSelector.setValue(pu);
		lfScoreSelector.setValue(lf);
		geScoreSelector.setValue(ge);
	}


	@Override
	public int getEvaluacionPresentacionPersonal() {
		return ppScoreSelector.getValue();
	}


	@Override
	public int getEvaluacionPuntualidad() {
		return puScoreSelector.getValue();
	}


	@Override
	public int getEvaluacionLlenadoFormulario() {
		return lfScoreSelector.getValue();
	}


	@Override
	public int getEvaluacionGeneralExaminador() {
		return geScoreSelector.getValue();
	}


	@Override
	public void showEvaluacionExaminadores(boolean show) {
		agregarExaminadorButton.setVisible(show);
		ausenteButton.setVisible(show);
		examinadoresList.setVisible(show);
		evaluacionExaminadorPanel.setVisible(show);
		
	}


	@Override
	public void setNombreExaminadorSelected(String nombre) {
		examinadorSeleccionadoLabel.setText(nombre);
	}


	@Override
	public void enableExaminadorActions(boolean enabled) {
		ausenteButton.setEnabled(enabled);
		//updateEvaluacionButton.setEnabled(enabled);
	}
	
	@Override
	public void setRealizadaPorSupervisor(boolean realizada) {
		realizadaPorSupervisorBox.setValue(realizada);
	}

	@Override
	public void updateEvaluacionExaminador(EvaluacionUsuarioDTO evaluacion) {
		examinadoresProvider.getList().set(examinadoresProvider.getList().indexOf(evaluacion), evaluacion);
	}



	@Override
	public void setPcsTotales(int total) {
		pcsTotalesBox.setValue(total+"");
	}



	@Override
	public int getPcsTotales() {
		return Integer.parseInt(pcsTotalesBox.getValue());
	}



	@Override
	public void setPcsTotalEnabled(boolean enabled) {
		pcsTotalesBox.setEnabled(enabled);
	}
}