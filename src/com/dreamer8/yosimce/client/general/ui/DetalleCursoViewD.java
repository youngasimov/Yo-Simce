package com.dreamer8.yosimce.client.general.ui;

import java.util.ArrayList;

import com.dreamer8.yosimce.client.ui.ImageButton;
import com.dreamer8.yosimce.client.ui.resources.SimceResources;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.resources.client.CssResource;
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

	interface Style extends CssResource {
		String line();
	}
	
	@UiField Style style;
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
		personasTable.getColumnFormatter().setWidth(0, "30%");
		personasTable.getColumnFormatter().setWidth(1, "70%");
		contactosTable.getColumnFormatter().setWidth(0, "30%");
		contactosTable.getColumnFormatter().setWidth(1, "70%");
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
		personasTable.setWidget(2, 0, new HTML(supervisor.getTelefono()));
		personasTable.setWidget(2, 1, new HTML(supervisor.getEmail()));
		personasTable.getFlexCellFormatter().addStyleName(2, 0, style.line());
		personasTable.getFlexCellFormatter().addStyleName(2, 1, style.line());
		
	}

	@Override
	public void setExaminadores(ArrayList<UserDTO> examinadores) {
		int i = 3;
		for(UserDTO examinador:examinadores){
			personasTable.setWidget(i, 0, new HTML("Examinador"));
			personasTable.setWidget(i, 1, new HTML(examinador.getNombres()+" "+examinador.getApellidoPaterno()+" "+examinador.getApellidoMaterno()));
			personasTable.setWidget(i+1, 0, new HTML(examinador.getTelefono()));
			personasTable.setWidget(i+1, 1, new HTML(examinador.getEmail()));
			personasTable.getFlexCellFormatter().addStyleName(i+1, 0, style.line());
			personasTable.getFlexCellFormatter().addStyleName(i+1, 1, style.line());
			i = i + 2;
		}
	}

	@Override
	public void setDirector(String director) {
		contactosTable.setWidget(1, 0, new HTML("Director"));
		contactosTable.setWidget(1, 1, new HTML(director));
	}

	@Override
	public void setEmailDirector(String email) {
		contactosTable.setWidget(2, 1, new HTML(email));
		contactosTable.getFlexCellFormatter().addStyleName(2, 1, style.line());
		contactosTable.getFlexCellFormatter().addStyleName(2, 0, style.line());
	}

	@Override
	public void setTelefonoDirector(String telefono) {
		contactosTable.setWidget(2, 0, new HTML(telefono));
		contactosTable.getFlexCellFormatter().addStyleName(2, 0, style.line());
		contactosTable.getFlexCellFormatter().addStyleName(2, 1, style.line());
	}

	@Override
	public void setContacto(String director) {
		contactosTable.setWidget(3, 1, new HTML(director));
	}
	
	@Override
	public void setCargoContacto(String cargo){
		contactosTable.setWidget(3, 0, new HTML(cargo));
	}

	@Override
	public void setEmailContacto(String email) {
		contactosTable.setWidget(4, 1, new HTML(email));
	}

	@Override
	public void setTelefonoContacto(String telefono) {
		contactosTable.setWidget(4, 0, new HTML(telefono));
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
