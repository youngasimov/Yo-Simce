package com.dreamer8.yosimce.client.planandresult.ui;

import java.util.ArrayList;

import com.dreamer8.yosimce.shared.dto.ActividadTipoDTO;
import com.dreamer8.yosimce.shared.dto.AplicacionDTO;
import com.dreamer8.yosimce.shared.dto.NivelDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class PlanAndResultHeaderViewD extends Composite implements PlanAndResultHeaderView  {

	private static PlanAndResultHeaderViewDUiBinder uiBinder = GWT
			.create(PlanAndResultHeaderViewDUiBinder.class);

	interface PlanAndResultHeaderViewDUiBinder extends
			UiBinder<Widget, PlanAndResultHeaderViewD> {
	}

	@UiField Anchor exportAction;
	@UiField ListBox actividadBox;
	@UiField ListBox nivelBox;
	@UiField ListBox tipoBox;
	
	private Presenter presenter;
	private ArrayList<AplicacionDTO> aplicaciones;
	private ArrayList<NivelDTO> niveles;
	private ArrayList<ActividadTipoDTO> tipos;
	
	public PlanAndResultHeaderViewD() {
		initWidget(uiBinder.createAndBindUi(this));
		exportAction.setText("Exportar");
		
	}

	@Override
	public void setPresenter(Presenter p) {
		this.presenter = p;
	}
	
	@UiHandler("exportAction")
	void onExportClick(ClickEvent event){
		presenter.onExportClick();
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
	public void exportActionVisivility(boolean visible) {
		exportAction.setVisible(visible);
	}

	@Override
	public void actividadBoxVisivility(boolean visible) {
		actividadBox.setVisible(visible);
	}

	@Override
	public void nivelBoxVisivility(boolean visible) {
		nivelBox.setVisible(visible);
	}

	@Override
	public void tipoBoxVisivility(boolean visible) {
		tipoBox.setVisible(visible);
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

}
