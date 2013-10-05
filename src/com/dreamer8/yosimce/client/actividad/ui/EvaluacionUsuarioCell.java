package com.dreamer8.yosimce.client.actividad.ui;

import com.dreamer8.yosimce.shared.dto.EvaluacionUsuarioDTO;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiRenderer;

public class EvaluacionUsuarioCell extends AbstractCell<EvaluacionUsuarioDTO> {

	interface EvaluacionUsuarioUiRenderer extends UiRenderer {
		void render(SafeHtmlBuilder sb, String name, String rut, String pp, String pu, String lf, String ge);
		void onBrowserEvent(EvaluacionUsuarioCell o, NativeEvent e, Element p, EvaluacionUsuarioDTO value);
	}
	
	private static EvaluacionUsuarioUiRenderer renderer = GWT.create(EvaluacionUsuarioUiRenderer.class);

	public EvaluacionUsuarioCell() {
		super("click");
	}

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context,
			EvaluacionUsuarioDTO value, SafeHtmlBuilder sb) {
		String name = value.getUsuario().getNombres()+" "+value.getUsuario().getApellidoPaterno()+" "+value.getUsuario().getApellidoMaterno();
		if(value.getPresentacionPersonal()==null){
			value.setPresentacionPersonal(0);
		}
		if(value.getFormulario()==null){
			value.setFormulario(0);
		}
		if(value.getPuntualidad()==null){
			value.setPuntualidad(0);
		}
		if(value.getGeneral()==null){
			value.setGeneral(0);
		}
		renderer.render(sb, name , value.getUsuario().getRut(), value.getPresentacionPersonal()+"", value.getPuntualidad()+"", value.getFormulario()+"", value.getGeneral()+"");
	}
	
	@Override
	public void onBrowserEvent(Context context, Element parent, EvaluacionUsuarioDTO value,
			NativeEvent event, ValueUpdater<EvaluacionUsuarioDTO> updater) {
		renderer.onBrowserEvent(this, event, parent, value);
	}
	
	@UiHandler({"cell"})
	void onNameGotPressed(ClickEvent event, Element parent, EvaluacionUsuarioDTO value) {
		
	}

}
