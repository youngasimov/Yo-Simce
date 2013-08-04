package com.dreamer8.yosimce.client.administracion.ui;

import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiRenderer;

public class UsuarioCell extends AbstractCell<UserDTO> {

	interface UsuarioUiRenderer extends UiRenderer {
		void render(SafeHtmlBuilder sb, String nombre);
	}
	
	private static UsuarioUiRenderer renderer = GWT.create(UsuarioUiRenderer.class);

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context, UserDTO value, SafeHtmlBuilder sb) {
		renderer.render(sb, value.getNombres()+" "+value.getApellidoPaterno()+" "+value.getApellidoMaterno());
	}

}
