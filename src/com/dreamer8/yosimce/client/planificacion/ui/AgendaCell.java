package com.dreamer8.yosimce.client.planificacion.ui;

import java.util.Date;

import com.dreamer8.yosimce.shared.dto.AgendaItemDTO;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiRenderer;

public class AgendaCell extends AbstractCell<AgendaItemDTO> {

	interface AgendaUiRenderer extends UiRenderer {
		void render(SafeHtmlBuilder sb, String date, String estado, String nombre, String comentario);
	}
	
	private static AgendaUiRenderer renderer = GWT.create(AgendaUiRenderer.class);

	private DateTimeFormat xFormat;
	private DateTimeFormat dateFormat;
	private DateTimeFormat timeFormat;
	public AgendaCell() {
		xFormat = DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_MEDIUM);
		dateFormat = DateTimeFormat.getFormat(PredefinedFormat.DATE_FULL);
		timeFormat = DateTimeFormat.getFormat(PredefinedFormat.TIME_SHORT);
	}
	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context,AgendaItemDTO value, SafeHtmlBuilder sb) {
		
		Date d = xFormat.parse(value.getFecha());
		
		renderer.render(sb,dateFormat.format(d)+" a las "+timeFormat.format(d), value.getEstado().getEstado(),
				((value.getCreador() != null)?value.getCreador().getNombres()+" "+value.getCreador().getApellidoPaterno():""),
				((value.getComentario() != null)?value.getComentario():""));
	}
	
	

}
