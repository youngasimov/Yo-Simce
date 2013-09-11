package com.dreamer8.yosimce.client.material.ui;

import com.dreamer8.yosimce.shared.dto.EmplazamientoDTO;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

public class CentroOperacionSelectorViewD extends Composite {

	private static CentroOperacionSelectorViewDUiBinder uiBinder = GWT
			.create(CentroOperacionSelectorViewDUiBinder.class);

	interface CentroOperacionSelectorViewDUiBinder extends
			UiBinder<Widget, CentroOperacionSelectorViewD> {
	}
	
	private class CentroOperacionCell extends AbstractCell<EmplazamientoDTO>{

		@Override
		public void render(com.google.gwt.cell.client.Cell.Context context,
				EmplazamientoDTO value, SafeHtmlBuilder sb) {
			
			sb.appendHtmlConstant("<div style='display:block; width: 95%';height: 40px; float:left; text-align: center; font-size: 20px; font-weight: bolder;>");
			sb.appendEscaped(value.getNombre());
			sb.appendHtmlConstant("</div>");
		}
		
	}
	
	@UiField ScrollPanel scroll;
	@UiField(provided=true) CellList<EmplazamientoDTO> coList;

	public CentroOperacionSelectorViewD() {
		coList = new CellList<EmplazamientoDTO>(new CentroOperacionCell(),EmplazamientoDTO.KEY_PROVIDER);
		initWidget(uiBinder.createAndBindUi(this));
	}

}
