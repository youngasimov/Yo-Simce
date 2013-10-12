package com.dreamer8.yosimce.client.actividad.ui;

import java.util.ArrayList;

import com.dreamer8.yosimce.client.actividad.ui.FormActividadView.FormActividadPresenter;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class ExaminadorSelectorViewD {

	private static ExaminadorSelectorViewDUiBinder uiBinder = GWT
			.create(ExaminadorSelectorViewDUiBinder.class);

	interface ExaminadorSelectorViewDUiBinder extends
			UiBinder<Widget, ExaminadorSelectorViewD> {
	}
	
	class ExaminadorCell extends AbstractCell<UserDTO>{

		@Override
		public void render(com.google.gwt.cell.client.Cell.Context context,UserDTO value, SafeHtmlBuilder sb) {
			sb.appendHtmlConstant("<table><tr><td>");
				sb.appendEscaped(value.getNombres()+" "+value.getApellidoPaterno()+" "+value.getApellidoMaterno());
			sb.appendHtmlConstant("</td></tr><tr><td>");
				sb.appendEscaped(value.getRut());
			sb.appendHtmlConstant("</td></tr></table>");
		}
		
	}

	@UiField DialogBox panel;
	@UiField TextBox examinadorBox;
	@UiField(provided=true) CellList<UserDTO> examinadoresList;
	@UiField Button cancelBox;
	@UiField Button seleccionarBox;
	
	private SingleSelectionModel<UserDTO> selectionModel;
	private FormActividadPresenter presenter;
	private UserDTO selected;
	private Command c;
	
	public ExaminadorSelectorViewD() {
		examinadoresList = new CellList<UserDTO>(new ExaminadorCell(),UserDTO.KEY_PROVIDER);
		selectionModel = new SingleSelectionModel<UserDTO>(UserDTO.KEY_PROVIDER);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				selected = selectionModel.getSelectedObject();
				seleccionarBox.setEnabled(true);
			}
		});
		examinadoresList.setSelectionModel(selectionModel);
		uiBinder.createAndBindUi(this);
		panel.setAutoHideEnabled(false);
		panel.setAutoHideOnHistoryEventsEnabled(false);
		panel.setGlassEnabled(true);
		panel.setAnimationEnabled(true);
		examinadoresList.setRowCount(0);
		seleccionarBox.setEnabled(false);
	}
	
	@UiHandler("examinadorBox")
	void onSearchBoxKeyUp(KeyUpEvent event){
		if(examinadorBox.getText().length()>2){
			presenter.getExaminadoresSuplentes(examinadorBox.getText());
		}
	}
	
	@UiHandler("seleccionarBox")
	void onSeleccionarClick(ClickEvent event){
		if(selected!=null){
			c.execute();
		}
	}
	
	@UiHandler("cancelBox")
	void onCancelClick(ClickEvent event){
		hide();
	}
	
	void show(Command action){
		this.c = action;
		panel.center();
		Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				examinadorBox.setFocus(true);
			}
		});
	}
	
	UserDTO getSelectedUser(){
		return selected;
	}
	
	void hide(){
		examinadorBox.setValue("");
		examinadoresList.setRowCount(0);
		selected = null;
		panel.hide();
	}
	
	void setPresenter(FormActividadPresenter presenter){
		this.presenter = presenter;
	}
	
	void setExaminadores(ArrayList<UserDTO> examinadores){
		examinadoresList.setRowCount(examinadores.size());
		examinadoresList.setVisibleRange(0,examinadores.size());
		examinadoresList.setRowData(examinadores);
		if(selectionModel.getSelectedObject()!=null){
			selectionModel.setSelected(selectionModel.getSelectedObject(), false);
		}
		seleccionarBox.setEnabled(false);
	}

}
