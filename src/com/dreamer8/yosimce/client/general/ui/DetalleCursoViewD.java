package com.dreamer8.yosimce.client.general.ui;

import java.util.ArrayList;

import com.dreamer8.yosimce.client.ui.ImageButton;
import com.dreamer8.yosimce.client.ui.resources.SimceResources;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
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
		personasTable.setWidget(1, 0, new HTML("Supervisor"));
		personasTable.setWidget(1, 1, new HTML(supervisor.getNombres()+" "+supervisor.getApellidoPaterno()+" "+supervisor.getApellidoMaterno()));
		personasTable.setWidget(1, 2, new HTML(supervisor.getTelefono()));
		personasTable.setWidget(1, 3, new HTML(supervisor.getEmail()));
		
	}

	@Override
	public void setExaminadores(ArrayList<UserDTO> examinadores) {
		int i = 2;
		for(UserDTO examinador:examinadores){
			personasTable.setWidget(i, 0, new HTML("Examinador"));
			personasTable.setWidget(i, 1, new HTML(examinador.getNombres()+" "+examinador.getApellidoPaterno()+" "+examinador.getApellidoMaterno()));
			personasTable.setWidget(i, 2, new HTML(examinador.getTelefono()));
			personasTable.setWidget(i, 3, new HTML(examinador.getEmail()));
			i++;
		}
	}

	@Override
	public void setDirector(String director) {
		contactosTable.setWidget(1, 0, new HTML("Director"));
		contactosTable.setWidget(1, 1, new HTML(director));
	}

	@Override
	public void setEmailDirector(String email) {
		contactosTable.setWidget(1, 3, new HTML(email));
	}

	@Override
	public void setTelefonoDirector(String telefono) {
		contactosTable.setWidget(1, 2, new HTML(telefono));
	}

	@Override
	public void setContacto(String director) {
		contactosTable.setWidget(2, 1, new HTML(director));
	}
	
	@Override
	public void setCargoContacto(String cargo){
		contactosTable.setWidget(2, 0, new HTML(cargo));
	}

	@Override
	public void setEmailContacto(String email) {
		contactosTable.setWidget(2, 3, new HTML(email));
	}

	@Override
	public void setTelefonoContacto(String telefono) {
		contactosTable.setWidget(2, 2, new HTML(telefono));
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
		personasTable.setHTML(0, 0, SafeHtmlUtils.fromString("<div style='font-weight: bolder;'>Cargo</div>"));
		personasTable.setHTML(0, 1, SafeHtmlUtils.fromString("<div style='font-weight: bolder;'>Nombre</div>"));
		personasTable.setHTML(0, 2, SafeHtmlUtils.fromString("<div style='font-weight: bolder;'>Teléfono</div>"));
		personasTable.setHTML(0, 3, SafeHtmlUtils.fromString("<div style='font-weight: bolder;'>Email</div>"));
		
		contactosTable.setHTML(0, 0, SafeHtmlUtils.fromString("<div style='font-weight: bolder;'>Cargo</div>"));
		contactosTable.setHTML(0, 1, SafeHtmlUtils.fromString("<div style='font-weight: bolder;'>Nombre</div>"));
		contactosTable.setHTML(0, 2, SafeHtmlUtils.fromString("<div style='font-weight: bolder;'>Teléfono</div>"));
		contactosTable.setHTML(0, 3, SafeHtmlUtils.fromString("<div style='font-weight: bolder;'>Email</div>"));
		
	}

	
}
