package com.dreamer8.yosimce.client;


import java.util.ArrayList;

import com.dreamer8.yosimce.client.ui.CursoSelectorView;
import com.dreamer8.yosimce.client.ui.CursoSelectorView.CursoSelectorPresenter;
import com.dreamer8.yosimce.shared.dto.CursoDTO;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Command;

public class CursoSelector implements
		CursoSelectorPresenter {

	private final ClientFactory factory;
	private final EventBus eventBus;
	private CursoSelectorView view;
	private Command cancelCommand;
	private Command confirmCommand;
	private CursoDTO currentCurso;
	
	public CursoSelector(ClientFactory factory,EventBus eventBus){
		this.factory = factory;
		this.eventBus = eventBus;
		view = factory.getCursoSelectorView();
		view.setPresenter(this);
		view.setOkButtonEnabled(false);
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
	
	public CursoDTO getSelectedCurso(){
		return currentCurso;
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
	
	public void setOnCursoChangeAction(Command c){
		this.confirmCommand = c;
	}
	
	public void hide(){
		currentCurso = null;
		view.setOkButtonEnabled(false);
		view.getDataDisplay().setRowCount(0);
		view.getDataDisplay().setVisibleRange(0, 20);
		view.getDataDisplay().setRowData(0, new ArrayList<CursoDTO>());
		view.hide();
	}

	@Override
	public void onSearchBoxChange(String search) {
		view.setOkButtonEnabled(false);
		if(search!=null && search.length()>2){
			factory.getGeneralService().getCursos(search, new SimceCallback<ArrayList<CursoDTO>>(eventBus) {

				@Override
				public void success(ArrayList<CursoDTO> result) {
					view.getDataDisplay().setRowCount(result.size());
					view.getDataDisplay().setVisibleRange(0, result.size());
					view.getDataDisplay().setRowData(0, result);
				}
				
			});
		}
		
		
	}


	@Override
	public void onCursoSelected(CursoDTO curso) {
		currentCurso = curso;
		view.setOkButtonEnabled(true);
	}
}