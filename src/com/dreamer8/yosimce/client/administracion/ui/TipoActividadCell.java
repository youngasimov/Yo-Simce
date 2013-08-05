package com.dreamer8.yosimce.client.administracion.ui;

import com.dreamer8.yosimce.shared.dto.ActividadTipoDTO;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiRenderer;

public class TipoActividadCell extends AbstractCell<ActividadTipoDTO> {

	interface TipoActividadUiRenderer extends UiRenderer {
		void render(SafeHtmlBuilder sb, String nombre);
	}
	
	private static TipoActividadUiRenderer renderer = GWT.create(TipoActividadUiRenderer.class);

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context, ActividadTipoDTO value, SafeHtmlBuilder sb) {
		renderer.render(sb, value.getNombre());
	}

}
