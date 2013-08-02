package com.dreamer8.yosimce.client.general.ui;

import com.dreamer8.yosimce.client.general.HistorialCursoPlace;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

public class DetalleCursoViewD extends Composite implements DetalleCursoView{

	private static DetalleCursoViewDUiBinder uiBinder = GWT
			.create(DetalleCursoViewDUiBinder.class);

	interface DetalleCursoViewDUiBinder extends
			UiBinder<Widget, DetalleCursoViewD> {
	}

	
	@UiField HTML establecimiento;
	@UiField Button historialButton;
	@UiField Button cambiarButton;
	@UiField Label rbdLabel;
	@UiField Label regionLabel;
	@UiField Label comunaLabel;
	@UiField Label cursoLabel;
	@UiField Label tipoLabel;
	@UiField Label supervisorLabel;
	@UiField Label examinadorLabel;
	@UiField Label examinador2Label;
	@UiField Label directorLabel;
	@UiField Label correoLabel;
	@UiField Label telefonoLabel;
	
	private int idCurso;
	private DetalleCursoPresenter presenter;
	
	public DetalleCursoViewD() {
		initWidget(uiBinder.createAndBindUi(this));
		idCurso = -1;
	}
	
	@UiHandler("cambiarButton")
	void onCambiarButtonClick(ClickEvent event){
		presenter.onCambiarCursoClick();
	}
	
	@UiHandler("historialButton")
	void onHistorialButtonClick(ClickEvent event){
		HistorialCursoPlace hcp = new HistorialCursoPlace();
		hcp.setCursoId(idCurso);
		presenter.goTo(hcp);
	}

	@Override
	public void setPresenter(DetalleCursoPresenter presenter) {
		this.presenter = presenter;
	}

	public void setIdCurso(int idCurso){
		this.idCurso = idCurso;
	}
	
	@Override
	public void setNombreEstablecimiento(String nombre) {
		establecimiento.setHTML(nombre);
	}

	@Override
	public void setRbd(String rbd) {
		rbdLabel.setText(rbd);
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
	public void setCurso(String curso) {
		cursoLabel.setText(curso);
	}

	@Override
	public void setTipo(String tipo) {
		tipoLabel.setText(tipo);
	}

	@Override
	public void setSupervisor(String supervisor) {
		supervisorLabel.setText(supervisor);
	}

	@Override
	public void setExaminador(String examinador) {
		examinadorLabel.setText(examinador);
	}

	@Override
	public void setExaminador2(String examinador) {
		examinador2Label.setText(examinador);
	}

	@Override
	public void setDirector(String director) {
		directorLabel.setText(director);
	}

	@Override
	public void setEmailContacto(String email) {
		correoLabel.setText(email);
	}

	@Override
	public void setTelefonoContacto(String telefono) {
		telefonoLabel.setText(telefono);
	}

	@Override
	public UIObject getCambiarButton() {
		return cambiarButton;
	}

	@Override
	public void clearAll() {
		idCurso = -1;
		establecimiento.setText("");
		rbdLabel.setText("");
		regionLabel.setText("");
		comunaLabel.setText("");
		cursoLabel.setText("");
		tipoLabel.setText("");
		supervisorLabel.setText("");
		examinadorLabel.setText("");
		examinador2Label.setText("");
		directorLabel.setText("");
		correoLabel.setText("");
		telefonoLabel.setText("");
	}
}
