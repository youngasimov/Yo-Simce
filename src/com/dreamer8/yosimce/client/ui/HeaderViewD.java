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
		presenter.onAplicacionChange(aplicacionId);
	}
	
	@UiHandler("nivelBox")
	void onNivelBoxChange(ChangeEvent event){
		int nivelId = Integer.parseInt(nivelBox.getValue(nivelBox.getSelectedIndex()));
		presenter.onNivelChange(nivelId);
	}
	
	@UiHandler("tipoBox")
	void onTipoBoxChange(ChangeEvent event){
		int tipoId = Integer.parseInt(tipoBox.getValue(tipoBox.getSelectedIndex()));
		presenter.onTipoChange(tipoId);
	}

	@Override
	public void setAplicacionList(ArrayList<AplicacionDTO> aplicaciones) {
		actividadBox.clear();
		actividadBox.addItem("--------","-1");
		for(AplicacionDTO aplicacion:aplicaciones){
			actividadBox.addItem(aplicacion.getNombre(), aplicacion.getId()+"");
		}
	}

	@Override
	public void setNivelList(ArrayList<NivelDTO> niveles) {
		nivelBox.clear();
		nivelBox.addItem("--------","-1");
		for(NivelDTO nivel:niveles){
			nivelBox.addItem(nivel.getNombre(),nivel.getId()+"");
		}
	}

	@Override
	public void setTipoList(ArrayList<ActividadTipoDTO> tipos) {
		tipoBox.clear();
		tipoBox.addItem("--------","-1");
		for(ActividadTipoDTO tipo:tipos){
			tipoBox.addItem(tipo.getNombre(), tipo.getId()+"");
		}
	}
	


	@Override
	public void selectAplicacion(int aplicacionId) {
		for(int i=0;i<actividadBox.getItemCount();i++){
			if(actividadBox.getValue(i).equals(aplicacionId+"")){
				actividadBox.setSelectedIndex(i);
				return;
			}
		}
	}

	@Override
	public void selectNivel(int nivelId) {
		for(int i=0;i<nivelBox.getItemCount();i++){
			if(nivelBox.getValue(i).equals(nivelId+"")){
				nivelBox.setSelectedIndex(i);
				return;
			}
		}
	}

	@Override
	public void selectTipo(int tipoId) {
		for(int i=0;i<tipoBox.getItemCount();i++){
			if(tipoBox.getValue(i).equals(tipoId+"")){
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

}
