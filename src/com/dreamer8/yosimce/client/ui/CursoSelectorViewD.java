package com.dreamer8.yosimce.client.ui;

import com.dreamer8.yosimce.shared.dto.CursoDTO;
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
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class CursoSelectorViewD implements CursoSelectorView {

	private static CursoSelectorViewDUiBinder uiBinder = GWT
			.create(CursoSelectorViewDUiBinder.class);

	interface CursoSelectorViewDUiBinder extends
			UiBinder<Widget, CursoSelectorViewD> {
	}
	
	class CursoCell extends AbstractCell<CursoDTO>{

		@Override
		public void render(com.google.gwt.cell.client.Cell.Context context,CursoDTO value, SafeHtmlBuilder sb) {
			sb.appendHtmlConstant("<table><tr><td>");
				sb.appendEscaped(value.getRbd()+" "+value.getNombreEstablecimiento());
			sb.appendHtmlConstant("</td></tr><tr><td>");
				sb.appendEscaped(value.getNivel()+ " "+ value.getNombre());
			sb.appendHtmlConstant("</td></tr></table>");
		}
		
	}

	@UiField DialogBox panel;
	@UiField TextBox rbdBox;
	@UiField(provided=true) CellList<CursoDTO> cursosList;
	@UiField Button cancelBox;
	@UiField Button seleccionarBox;
	
	private CursoSelectorPresenter presenter;
	
	private SingleSelectionModel<CursoDTO> selectionModel;
	
	public CursoSelectorViewD() {
		cursosList = new CellList<CursoDTO>(new CursoCell(),CursoDTO.KEY_PROVIDER);
		selectionModel = new SingleSelectionModel<CursoDTO>(CursoDTO.KEY_PROVIDER);
		cursosList.setSelectionModel(selectionModel);
		uiBinder.createAndBindUi(this);
		panel.setAutoHideEnabled(false);
		panel.setAutoHideOnHistoryEventsEnabled(false);
		panel.setGlassEnabled(true);
		panel.setAnimationEnabled(true);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler(){

			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				presenter.cursoSelected(selectionModel.getSelectedObject());
			}
		});
	}
	
	@UiHandler("cancelBox")
	void onCancelBoxClick(ClickEvent event){
		presenter.onCancel();
	}
	
	@UiHandler("seleccionarBox")
	void onSeleccionarBoxClick(ClickEvent event){
		presenter.onConfirm();
	}
	
	@UiHandler("rbdBox")
	void onBuscadorKeyUp(KeyUpEvent event){
		presenter.onSearchBoxChange(rbdBox.getText());
	}

	@Override
	public void setPresenter(CursoSelectorPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void show() {
		panel.center();
		Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				rbdBox.setFocus(true);
			}
		});
	}
	
	@Override
	public void hide() {
		panel.hide(false);
	}

	@Override
	public HasData<CursoDTO> getDataDisplay() {
		return cursosList;
	}

	@Override
	public void setOkButtonEnabled(boolean enabled) {
		seleccionarBox.setEnabled(enabled);
	}

	@Override
	public void setSearchValue(String search) {
		rbdBox.setValue(search);
	}

	@Override
	public SingleSelectionModel<CursoDTO> getSelectionModel() {
		return selectionModel;
	}
}