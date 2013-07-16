package com.dreamer8.yosimce.client.planandresult.ui;

import java.util.ArrayList;
import java.util.HashMap;

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
		
	}
	
	@UiHandler("nivelBox")
	void onNivelBoxChange(ChangeEvent event){
		
	}
	
	@UiHandler("tipoBox")
	void onTipoBoxChange(ChangeEvent event){
		
	}

	@Override
	public void exportActionVisivility(boolean visible) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actividadBoxVisivility(boolean visible) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nivelBoxVisivility(boolean visible) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tipoBoxVisivility(boolean visible) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAplicacionList(ArrayList<AplicacionDTO> aplicaciones) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNivelList(ArrayList<NivelDTO> niveles) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTipoList(ArrayList<ActividadTipoDTO> tipos) {
		// TODO Auto-generated method stub
		
	}

}
