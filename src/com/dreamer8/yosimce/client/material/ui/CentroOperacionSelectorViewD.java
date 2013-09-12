package com.dreamer8.yosimce.client.material.ui;

import com.dreamer8.yosimce.shared.dto.EmplazamientoDTO;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
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
			
			sb.appendHtmlConstant("<div style='display:block; width: 95% ;height: 40px; float:left; text-align: center; font-size: 20px; font-weight: bolder;'>");
			sb.appendEscaped(value.getNombre());
			sb.appendHtmlConstant("</div>");
		}
		
	}
	
	@UiField HTMLPanel panel;
	@UiField(provided=true) CellList<EmplazamientoDTO> coList;
	
	private PopupPanel popup;
	
	private SingleSelectionModel<EmplazamientoDTO> selectionModel;
	
	private CentroOperacionSelectorPresenter presenter;

	public CentroOperacionSelectorViewD() {
		coList = new CellList<EmplazamientoDTO>(new CentroOperacionCell(),EmplazamientoDTO.KEY_PROVIDER);
		initWidget(uiBinder.createAndBindUi(this));
		popup = new PopupPanel(true, false);
		popup.setAnimationEnabled(true);
		popup.setAutoHideOnHistoryEventsEnabled(true);
		popup.setGlassEnabled(false);
		popup.setModal(true);
		popup.setWidget(this);
		selectionModel = new SingleSelectionModel<EmplazamientoDTO>(EmplazamientoDTO.KEY_PROVIDER);
		coList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler(){

			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				presenter.centroOperacionSelected(selectionModel.getSelectedObject());
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
		panel.getElement().setAttribute("style", "max-height: "+h+"px;");
		selectionModel.clear();
		popup.center();
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
