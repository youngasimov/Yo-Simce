package com.dreamer8.yosimce.client.actividad.ui;

import com.dreamer8.yosimce.client.ui.ScoreSelector;
import com.dreamer8.yosimce.client.ui.eureka.TimeBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
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
	@UiField Button saveButton;
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
	@UiField TimeBox inicioActividadBox;
	@UiField TimeBox inicioPruebaBox;
	@UiField TimeBox terminoPruebaBox;
	@UiField DecoratorPanel participacionPanel;
	@UiField IntegerBox totalAlumnosBox;
	@UiField IntegerBox alumnosTitularesBox;
	@UiField IntegerBox alumnosSuplentesBox;
	@UiField IntegerBox alumnosAgregadosBox;
	@UiField IntegerBox alumnosAgregadosNoParticipantesBox;
	@UiField IntegerBox alumnosNeeBox;
	@UiField DecoratorPanel cuestionariosPanel;
	@UiField IntegerBox cuestionariosTotalesBox;
	@UiField IntegerBox cuestionariosEntregadosBox;
	@UiField IntegerBox cuestionariosDevueltosBox;
	@UiField DecoratorPanel contingenciasPanel;
	@UiField CheckBox usoMaterialBox;
	@UiField TextBox detallesUsoBox;
	@UiField TextArea contingenciaBox;
	@UiField CheckBox afectaEnPruebaBox;
	@UiField DecoratorPanel evaluacionPanel;
	@UiField ScoreSelector procedimientoScoreSelector;
	@UiField FileUpload formularioControlFile;
	
	private FormActividadPresenter presenter;
	
	public FormActividadViewD() {
		initWidget(uiBinder.createAndBindUi(this));
	}



	@Override
	public void setPresenter(FormActividadPresenter presenter) {
		this.presenter = presenter;
	}
}