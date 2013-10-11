package com.dreamer8.yosimce.client;


import java.util.ArrayList;

import com.dreamer8.yosimce.client.ui.CursoSelectorView;
import com.dreamer8.yosimce.client.ui.CursoSelectorView.CursoSelectorPresenter;
import com.dreamer8.yosimce.shared.dto.CursoDTO;
import com.google.gwt.core.client.Scheduler;
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
		view.getDataDisplay().setVisibleRange(0, 20);
	}
	
	@Override
	public void onSearchBoxChange(String search) {
		if(search!=null && search.length()>2){
			factory.getGeneralService().getCursos(search, new SimceCallback<ArrayList<CursoDTO>>(eventBus,false) {

				@Override
				public void success(ArrayList<CursoDTO> result) {
					view.getDataDisplay().setRowCount(result.size());
					view.getDataDisplay().setRowData(0, result);
					view.setOkButtonEnabled(false);
					Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
						
						@Override
						public void execute() {
							view.getSelectionModel().clear();
						}
					});
					currentCurso = null;
				}
				
			});
		}else{
			view.getDataDisplay().setRowCount(0);
			view.setOkButtonEnabled(false);
			view.getSelectionModel().clear();
			currentCurso = null;
		}	
	}

	@Override
	public void cursoSelected(CursoDTO curso) {
		currentCurso = curso;
		view.setOkButtonEnabled(true);
	}

	@Override
	public void onConfirm() {
		eventBus.fireEvent(new MenuEvent(false));
		if(confirmCommand!=null){
			confirmCommand.execute();
		}
	}
	
	@Override
	public void onCancel() {
		hide();
		eventBus.fireEvent(new MenuEvent(false));
		if(cancelCommand!=null){
			cancelCommand.execute();
		}
	}
	
	public void show(){
		view.show();
	}
	
	public void hide(){
		currentCurso = null;
		view.setOkButtonEnabled(false);
		view.getDataDisplay().setRowCount(0);
		view.getDataDisplay().setRowData(0, new ArrayList<CursoDTO>());
		view.setSearchValue("");
		Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				view.getSelectionModel().clear();
			}
		});
		view.hide();
	}
	
	public CursoDTO getSelectedCurso(){
		return currentCurso;
	}
	
	public void setOnCancelAction(Command c){
		this.cancelCommand = c;
	}
	
	public void setOnCursoChangeAction(Command c){
		this.confirmCommand = c;
	}
}