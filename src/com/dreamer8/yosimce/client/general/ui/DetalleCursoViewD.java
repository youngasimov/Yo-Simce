package com.dreamer8.yosimce.client.general.ui;

import java.util.ArrayList;

import com.dreamer8.yosimce.client.ui.ImageButton;
import com.dreamer8.yosimce.client.ui.resources.SimceResources;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
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
	@UiField ImageButton cambiarButton;
	@UiField Label rbdLabel;
	@UiField Label regionLabel;
	@UiField Label comunaLabel;
	@UiField Label cursoLabel;
	@UiField Label tipoLabel;
	@UiField FlexTable personasTable;
	@UiField FlexTable contactosTable;
	
	private DetalleCursoPresenter presenter;
	
	public DetalleCursoViewD() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiHandler("cambiarButton")
	void onCambiarButtonClick(ClickEvent event){
		presenter.onCambiarCursoClick();
	}
	
	@UiFactory
	public static SimceResources getResources() {
		return SimceResources.INSTANCE;
	}

	@Override
	public void setPresenter(DetalleCursoPresenter presenter) {
		this.presenter = presenter;
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
	public void setSupervisor(UserDTO supervisor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setExaminadores(ArrayList<UserDTO> examinadores) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDirector(String director) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEmailDirector(String email) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTelefonoDirector(String telefono) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setContacto(String director) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEmailContacto(String email) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTelefonoContacto(String telefono) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearAll() {
		establecimiento.setText("");
		rbdLabel.setText("");
		regionLabel.setText("");
		comunaLabel.setText("");
		cursoLabel.setText("");
		tipoLabel.setText("");
		personasTable.clear();
		contactosTable.clear();
	}

	
}
