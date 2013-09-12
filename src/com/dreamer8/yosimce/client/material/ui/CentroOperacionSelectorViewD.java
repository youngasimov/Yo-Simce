package com.dreamer8.yosimce.client.material.ui;

import com.dreamer8.yosimce.shared.dto.EmplazamientoDTO;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class CentroOperacionSelectorViewD extends Composite implements CentroOperacionSelectorView {

	private static CentroOperacionSelectorViewDUiBinder uiBinder = GWT
			.create(CentroOperacionSelectorViewDUiBinder.class);

	interface CentroOperacionSelectorViewDUiBinder extends
			UiBinder<Widget, CentroOperacionSelectorViewD> {
	}
	
	private class CentroOperacionCell extends AbstractCell<EmplazamientoDTO>{
		
		@Override
		public void render(com.google.gwt.cell.client.Cell.Context context,
				EmplazamientoDTO value, SafeHtmlBuilder sb) {
			if(context.getIndex()%2 == 0){
				sb.appendHtmlConstant("<table style='height: 50px; border-bottom: 1px solid #AAAAAA; font-size: 20px; font-weight: bold; width: 90%' ><tr><td>");
			}else{
				sb.appendHtmlConstant("<table style='background= #DEDEDE; height: 50px; border-bottom: 1px solid #AAAAAA; font-size: 20px; font-weight: bold; width: 90%' ><tr><td>");
			}
				sb.appendEscaped("Centro Operación "+value.getNombre());
			sb.appendHtmlConstant("</td></tr></table>");
		}
		
	}
	
	@UiField HTMLPanel panel;
	@UiField ScrollPanel scroll;
	@UiField(provided=true) CellList<EmplazamientoDTO> coList;
	
	private PopupPanel popup;
	
	private SingleSelectionModel<EmplazamientoDTO> selectionModel;
	
	private CentroOperacionSelectorPresenter presenter;

	public CentroOperacionSelectorViewD() {
		coList = new CellList<EmplazamientoDTO>(new CentroOperacionCell(),EmplazamientoDTO.KEY_PROVIDER);
		selectionModel = new SingleSelectionModel<EmplazamientoDTO>(EmplazamientoDTO.KEY_PROVIDER);
		coList.setSelectionModel(selectionModel);
		coList.setPageSize(300);
		initWidget(uiBinder.createAndBindUi(this));
		popup = new PopupPanel(true, true);
		popup.setAnimationEnabled(true);
		popup.setAutoHideOnHistoryEventsEnabled(true);
		popup.setGlassEnabled(false);
		popup.setModal(true);
		popup.setWidget(this);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler(){

			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				if(selectionModel.getSelectedObject()!=null){
					presenter.centroOperacionSelected(selectionModel.getSelectedObject());
				}
			}
		});
		popup.addCloseHandler(new CloseHandler<PopupPanel>() {
			
			@Override
			public void onClose(CloseEvent<PopupPanel> event) {
				if(event.isAutoClosed()){
					presenter.onAutoHide();
				}
			}
		});
	}

	@Override
	public void show() {
		int h = Window.getClientHeight();
		h = (9*h)/10;
		panel.getElement().setAttribute("style", "max-height: "+(h+20)+"px;");
		selectionModel.clear();
		if(coList.getRowCount()*50>h){
			scroll.setHeight(h+"px");
		}else{
			scroll.setHeight(((coList.getRowCount()+1)*50)+"px");
		}
		Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				popup.center();
			}
		});
		
	}

	@Override
	public void hide() {
		popup.hide(false);
	}

	@Override
	public void setPresenter(CentroOperacionSelectorPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public HasData<EmplazamientoDTO> getDataDisplay() {
		return coList;
	}

}
