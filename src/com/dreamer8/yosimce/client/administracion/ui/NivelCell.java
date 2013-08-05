package com.dreamer8.yosimce.client.administracion.ui;

import com.dreamer8.yosimce.shared.dto.NivelDTO;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiRenderer;

public class NivelCell extends AbstractCell<NivelDTO> {

	interface NivelUiRenderer extends UiRenderer {
		void render(SafeHtmlBuilder sb, String nombre);
	}
	
	private static NivelUiRenderer renderer = GWT.create(NivelUiRenderer.class);

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context, NivelDTO value, SafeHtmlBuilder sb) {
		renderer.render(sb, value.getNombre());
	}

}
