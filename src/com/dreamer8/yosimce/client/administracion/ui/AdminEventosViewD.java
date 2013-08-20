package com.dreamer8.yosimce.client.administracion.ui;

import java.util.ArrayList;

import com.dreamer8.yosimce.client.ui.ImageButton;
import com.dreamer8.yosimce.client.ui.resources.SimceResources;
import com.dreamer8.yosimce.shared.dto.ActividadTipoDTO;
import com.dreamer8.yosimce.shared.dto.AplicacionDTO;
import com.dreamer8.yosimce.shared.dto.NivelDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class AdminEventosViewD extends Composite implements AdminEventosView{

	private static AdminEventosViewDUiBinder uiBinder = GWT
			.create(AdminEventosViewDUiBinder.class);

	interface AdminEventosViewDUiBinder extends
			UiBinder<Widget, AdminEventosViewD> {
	}

	@UiField ImageButton nuevaAplicacionButton;
	@UiField ImageButton editarAplicacionButton;
	@UiField ImageButton nuevoNivelButton;
	@UiField ImageButton editarNivelButton;
	@UiField ImageButton nuevoTipoButton;
	@UiField ImageButton editarTipoButton;
	@UiField ListBox aplicacionBox;
	@UiField ListBox nivelBox;
	@UiField ListBox tipoBox;
	
	
	private AdminEventosPresenter presenter;
	
	public AdminEventosViewD() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiFactory
	public static SimceResources getResources() {
		return SimceResources.INSTANCE;
	}

	@Override
	public void setPresenter(AdminEventosPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void setNuevoNivelVisibility(boolean visible) {
		nuevoNivelButton.setVisible(visible);
	}

	@Override
	public void setNuevoTipoVisibility(boolean visible) {
		nuevoTipoButton.setVisible(visible);
	}
	
	@Override
	public void setAplicaciones(ArrayList<AplicacionDTO> aplicaciones) {
		aplicacionBox.clear();
		for(AplicacionDTO a:aplicaciones){
			aplicacionBox.addItem(a.getNombre(),a.getId()+"");
		}
	}

	@Override
	public void setNiveles(ArrayList<NivelDTO> niveles) {
		nivelBox.clear();
		for(NivelDTO n:niveles){
			nivelBox.addItem(n.getNombre(),n.getId()+"");
		}
	}

	@Override
	public void setTipos(ArrayList<ActividadTipoDTO> tipos) {
		tipoBox.clear();
		for(ActividadTipoDTO t:tipos){
			tipoBox.addItem(t.getNombre(),t.getId()+"");
		}
	}

	@Override
	public void setAplicacionesBoxVisivility(boolean visible) {
		aplicacionBox.setVisible(visible);
	}

	@Override
	public void setNivelesBoxVisivility(boolean visible) {
		nivelBox.setVisible(visible);
	}

	@Override
	public void setTiposBoxVisivility(boolean visible) {
		tipoBox.setVisible(visible);
	}

	@Override
	public void setNuevaAplicacionVisibility(boolean visible) {
		nuevaAplicacionButton.setVisible(visible);
	}

	@Override
	public void setEditarAplicacionVisivility(boolean visible) {
		editarAplicacionButton.setVisible(visible);
	}

	@Override
	public void setEditarNivelVisivility(boolean visible) {
		editarNivelButton.setVisible(visible);
	}

	@Override
	public void setEditarTipoVisivility(boolean visible) {
		editarTipoButton.setVisible(visible);
	}
}
