package com.dreamer8.yosimce.client.ui;

import com.dreamer8.yosimce.shared.dto.TipoUsuarioDTO;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
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

public class TipoUsuarioSelectorViewD extends Composite implements TipoUsuarioSelectorView {

	private static TipoUsuarioSelectorViewDUiBinder uiBinder = GWT
			.create(TipoUsuarioSelectorViewDUiBinder.class);

	interface TipoUsuarioSelectorViewDUiBinder extends
			UiBinder<Widget, TipoUsuarioSelectorViewD> {
	}
	
	private class TipoUsuarioCell extends AbstractCell<TipoUsuarioDTO>{
		
		@Override
		public void render(com.google.gwt.cell.client.Cell.Context context,
				TipoUsuarioDTO value, SafeHtmlBuilder sb) {
			if(context.getIndex()%2 == 0){
				sb.appendHtmlConstant("<table style='height: 50px; border-bottom: 1px solid #AAAAAA; font-size: 20px; font-weight: bold; width: 90%' ><tr><td>");
			}else{
				sb.appendHtmlConstant("<table style='background= #DEDEDE; height: 50px; border-bottom: 1px solid #AAAAAA; font-size: 20px; font-weight: bold; width: 90%' ><tr><td>");
			}
				sb.appendEscaped(value.getTipoUsuario());
			sb.appendHtmlConstant("</td></tr></table>");
		}
		
	}
	
	@UiField HTMLPanel panel;
	@UiField ScrollPanel scroll;

	@UiField(provided=true) CellList<TipoUsuarioDTO> coList;
	
	private PopupPanel popup;
	
	private SingleSelectionModel<TipoUsuarioDTO> selectionModel;
	
	private TipoUsuarioSelectorPresenter presenter;
	
	public TipoUsuarioSelectorViewD() {
		coList = new CellList<TipoUsuarioDTO>(new TipoUsuarioCell(),TipoUsuarioDTO.KEY_PROVIDER);
		selectionModel = new SingleSelectionModel<TipoUsuarioDTO>(TipoUsuarioDTO.KEY_PROVIDER);
		coList.setSelectionModel(selectionModel);
		coList.setPageSize(300);
		initWidget(uiBinder.createAndBindUi(this));
		popup = new PopupPanel(false, true);
		popup.setAnimationEnabled(true);
		popup.setAutoHideOnHistoryEventsEnabled(false);
		popup.setGlassEnabled(false);
		popup.setModal(true);
		popup.setAutoHideEnabled(false);
		popup.setWidget(this);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler(){

			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				if(selectionModel.getSelectedObject()!=null){
					presenter.onTipoUsuarioSelected(selectionModel.getSelectedObject());
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
	public HasData<TipoUsuarioDTO> getDataDisplay() {
		return coList;
	}

	@Override
	public void setPresenter(TipoUsuarioSelectorPresenter presenter) {
		this.presenter = presenter;
	}

}
