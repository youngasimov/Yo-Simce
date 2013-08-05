package com.dreamer8.yosimce.client.administracion.ui;

import com.dreamer8.yosimce.shared.dto.AplicacionDTO;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiRenderer;

public class AplicacionCell extends AbstractCell<AplicacionDTO> {

	interface AplicacionUiRenderer extends UiRenderer {
		void render(SafeHtmlBuilder sb, String nombre);
	}
	
	private static AplicacionUiRenderer renderer = GWT.create(AplicacionUiRenderer.class);

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context, AplicacionDTO value, SafeHtmlBuilder sb) {
		renderer.render(sb, value.getNombre());
	}

}
