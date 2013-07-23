package com.dreamer8.yosimce.client.ui;

import java.util.ArrayList;

import com.dreamer8.yosimce.shared.dto.ActividadTipoDTO;
import com.dreamer8.yosimce.shared.dto.AplicacionDTO;
import com.dreamer8.yosimce.shared.dto.NivelDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class HeaderViewD extends Composite implements HeaderView  {

	private static HeaderViewDUiBinder uiBinder = GWT
			.create(HeaderViewDUiBinder.class);

	interface HeaderViewDUiBinder extends
			UiBinder<Widget, HeaderViewD> {
	}

	@UiField ListBox actividadBox;
	@UiField ListBox nivelBox;
	@UiField ListBox tipoBox;
	@UiField HTML username;
	
	private HeaderPresenter presenter;
	private ArrayList<AplicacionDTO> aplicaciones;
	private ArrayList<NivelDTO> niveles;
	private ArrayList<ActividadTipoDTO> tipos;
	
	public HeaderViewD() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public void setPresenter(HeaderPresenter p) {
		this.presenter = p;		
	}
	
	@UiHandler("actividadBox")
	void onActividadBoxChange(ChangeEvent event){
		int aplicacionId = Integer.parseInt(actividadBox.getValue(actividadBox.getSelectedIndex()));
		for(AplicacionDTO aplicacion: aplicaciones){
			if(aplicacion.getId() == aplicacionId){
				presenter.onAplicacionChange(aplicacion);
				return;
			}
		}
	}
	
	@UiHandler("nivelBox")
	void onNivelBoxChange(ChangeEvent event){
		int nivelId = Integer.parseInt(nivelBox.getValue(nivelBox.getSelectedIndex()));
		for(NivelDTO nivel:niveles){
			if(nivel.getId() == nivelId){
				presenter.onNivelChange(nivel);
				return;
			}
		}
	}
	
	@UiHandler("tipoBox")
	void onTipoBoxChange(ChangeEvent event){
		int tipoId = Integer.parseInt(tipoBox.getValue(tipoBox.getSelectedIndex()));
		for(ActividadTipoDTO tipo:tipos){
			if(tipo.getId() == tipoId){
				presenter.onTipoChange(tipo);
				return;
			}
		}
	}

	@Override
	public void setAplicacionList(ArrayList<AplicacionDTO> aplicaciones) {
		actividadBox.clear();
		for(AplicacionDTO aplicacion:aplicaciones){
			actividadBox.addItem(aplicacion.getNombre(), aplicacion.getId()+"");
		}
		this.aplicaciones = aplicaciones;
	}

	@Override
	public void setNivelList(ArrayList<NivelDTO> niveles) {
		nivelBox.clear();
		for(NivelDTO nivel:niveles){
			nivelBox.addItem(nivel.getNombre(),nivel.getId()+"");
		}
		this.niveles = niveles;
	}

	@Override
	public void setTipoList(ArrayList<ActividadTipoDTO> tipos) {
		tipoBox.clear();
		for(ActividadTipoDTO tipo:tipos){
			tipoBox.addItem(tipo.getNombre(), tipo.getId()+"");
		}
		this.tipos = tipos;
	}
	


	@Override
	public void selectAplicacion(AplicacionDTO aplicacion) {
		for(int i=0;i<actividadBox.getItemCount();i++){
			if(actividadBox.getValue(i).equals(aplicacion.getId()+"")){
				actividadBox.setSelectedIndex(i);
				return;
			}
		}
	}

	@Override
	public void selectNivel(NivelDTO nivel) {
		for(int i=0;i<nivelBox.getItemCount();i++){
			if(nivelBox.getValue(i).equals(nivel.getId()+"")){
				nivelBox.setSelectedIndex(i);
				return;
			}
		}
	}

	@Override
	public void selectTipo(ActividadTipoDTO tipo) {
		for(int i=0;i<tipoBox.getItemCount();i++){
			if(tipoBox.getValue(i).equals(tipo.getId()+"")){
				tipoBox.setSelectedIndex(i);
				return;
			}
		}
	}


	@Override
	public void setAplicacionBoxVisivility(boolean visible) {
		actividadBox.setVisible(visible);
	}

	@Override
	public void setNivelBoxVisivility(boolean visible) {
		nivelBox.setVisible(visible);
	}

	@Override
	public void setTipoBoxVisivility(boolean visible) {
		tipoBox.setVisible(visible);
	}

	@Override
	public void setUserName(String user) {
		username.setText(user);
	}

	@Override
	public void error(boolean visible) {
		
	}

}
