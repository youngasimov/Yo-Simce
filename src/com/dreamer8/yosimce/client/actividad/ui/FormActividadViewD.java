package com.dreamer8.yosimce.client.actividad.ui;

import java.util.Date;

import com.dreamer8.yosimce.client.ui.ImageButton;
import com.dreamer8.yosimce.client.ui.ScoreSelector;
import com.dreamer8.yosimce.client.ui.ViewUtils;
import com.dreamer8.yosimce.client.ui.eureka.TimeBox;
import com.dreamer8.yosimce.client.ui.resources.SimceResources;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
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
	@UiField TextBox motivoEstadoBox;
	@UiField DecoratorPanel datosExaminadorPanel;
	@UiField Label nombreExaminadorLabel;
	@UiField Label rutExaminadorLabel;
	@UiField Button changeExaminadorButton;
	@UiField ScoreSelector presentacionPersonalScoreSelector;
	@UiField ScoreSelector puntualidadScoreSelector;
	@UiField ScoreSelector llenadoFormularioScoreSelector;
	@UiField ScoreSelector desempenoScoreSelector;
	@UiField DecoratorPanel horasActividadPanel;
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
	@UiField IntegerBox cuestionariosRespondidosBox;
	@UiField IntegerBox cuestionariosSinResponderBox;
	@UiField DecoratorPanel contingenciasPanel;
	@UiField CheckBox usoMaterialBox;
	@UiField TextBox detallesUsoBox;
	@UiField TextArea contingenciaBox;
	@UiField CheckBox afectaEnPruebaBox;
	@UiField DecoratorPanel evaluacionPanel;
	@UiField ScoreSelector procedimientoScoreSelector;
	@UiField FileUpload formularioControlFile;
	
	private FormActividadPresenter presenter;
	private UserDTO examinador;
	
	public FormActividadViewD() {
		inicioActividadBox = new TimeBox(new Date(0),false);
		inicioPruebaBox = new TimeBox(new Date(0),false);
		terminoPruebaBox = new TimeBox(new Date(0),false);
		initWidget(uiBinder.createAndBindUi(this));
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
	public void setMotivoEstado(String motivo) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getMotivoEstado() {
		// TODO Auto-generated method stub
		return null;
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
}