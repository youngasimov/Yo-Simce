package com.dreamer8.yosimce.client.planandresult.ui;

import com.dreamer8.yosimce.shared.dto.EstablecimientoDTO;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiRenderer;

public class EstablecimientoCell extends AbstractCell<EstablecimientoDTO> {
	
	interface EstablecimientoCellUiRenderer extends UiRenderer{
		void render(SafeHtmlBuilder sb, Boolean selected, String name, String estadoActividad, String estadoAgendamiento, String estadoSincronizacion);
	}
	
	private static EstablecimientoCellUiRenderer renderer = GWT.create(EstablecimientoCellUiRenderer.class);


	public EstablecimientoCell() {
		
	}

	@Override
	public void render(Context context, EstablecimientoDTO value, SafeHtmlBuilder sb) {
		renderer.render(sb, false, value.getName(), value.getEstadoActividad(), value.getEstadoAgendamiento(), value.getEstadoMaterialSincronizado());		
	}

}
