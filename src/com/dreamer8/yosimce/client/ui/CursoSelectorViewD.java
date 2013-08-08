package com.dreamer8.yosimce.client.ui;

import com.dreamer8.yosimce.shared.dto.CursoDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

public class CursoSelectorViewD implements CursoSelectorView {

	private static CursoSelectorViewDUiBinder uiBinder = GWT
			.create(CursoSelectorViewDUiBinder.class);

	interface CursoSelectorViewDUiBinder extends
			UiBinder<Widget, CursoSelectorViewD> {
	}

	@UiField DialogBox panel;
	@UiField SuggestBox rbdBox;
	@UiField CellList<CursoDTO> cursosList;
	@UiField Button cancelBox;
	
	private CursoSelectorPresenter presenter;
	
	public CursoSelectorViewD() {
		uiBinder.createAndBindUi(this);
	}
	
	@UiHandler("cancelBox")
	void onCancelBoxClick(ClickEvent event){
		rbdBox.setValue("");
		panel.hide(true);
	}
	
	@UiHandler("panel")
	void onDialogBoxClose(CloseEvent<PopupPanel> event){
		if(event.getTarget()!=null){
			cancel();
		}
	}

	@Override
	public void setPresenter(CursoSelectorPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void show() {
		panel.center();
	}

	@Override
	public void showRelativeTo(UIObject object) {
		panel.showRelativeTo(object);
	}
	
	@Override
	public void hide() {
		panel.hide(false);
	}

	@Override
	public void setCancelable(boolean cancelable) {
		panel.setAutoHideEnabled(cancelable);
		cancelBox.setVisible(cancelable);
	}
	
	private void cancel(){
		presenter.onCancel();
	}

	@Override
	public void setGlassEnabled(boolean enabled) {
		panel.setGlassEnabled(enabled);
	}
}