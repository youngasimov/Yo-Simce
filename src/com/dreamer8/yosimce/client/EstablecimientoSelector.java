package com.dreamer8.yosimce.client;

import java.util.ArrayList;

import com.dreamer8.yosimce.client.ui.EstablecimientoSelectorView;
import com.dreamer8.yosimce.client.ui.EstablecimientoSelectorView.EstablecimientoSelectorPresenter;
import com.dreamer8.yosimce.shared.dto.EstablecimientoDTO;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.UIObject;

public class EstablecimientoSelector implements
		EstablecimientoSelectorPresenter {

	private final ClientFactory factory;
	private EstablecimientoSelectorView view;
	private Command cancelCommand;
	private Command confirmCommand;
	private EstablecimientoDTO currentEstablecimiento;
	
	public EstablecimientoSelector(ClientFactory factory){
		this.factory = factory;
		view = factory.getEstablecimientoSelectorView();
		view.setPresenter(this);
		
	}
	
	
	@Override
	public ArrayList<EstablecimientoDTO> getEstablecimientos(String search) {
		return null;
	}
	

	@Override
	public void onConfirm() {
		if(confirmCommand!=null){
			confirmCommand.execute();
		}
	}
	
	@Override
	public void onCancel() {
		if(cancelCommand!=null){
			cancelCommand.execute();
		}
	}
	
	public void show(){
		view.show();
	}
	
	public EstablecimientoDTO getSelectedEstablecimiento(){
		return currentEstablecimiento;
	}
	
	public void showRelativeTo(UIObject object){
		view.showRelativeTo(object);
	}
	
	public void setCancelable(boolean cancelable){
		view.setCancelable(cancelable);
	}
	
	public void setGlassEnabled(boolean enabled){
		view.setGlassEnabled(enabled);
	}
	
	public void setOnCancelAction(Command c){
		this.cancelCommand = c;
	}
	
	public void setOnEstablecimientoChangeAction(Command c){
		this.confirmCommand = c;
	}
	
	public void hide(){
		view.hide();
	}
}
