package com.dreamer8.yosimce.client.administracion.ui;

import java.util.ArrayList;

import com.dreamer8.yosimce.shared.dto.EmplazamientoDTO;
import com.dreamer8.yosimce.shared.dto.TipoEmplazamientoDTO;
import com.dreamer8.yosimce.shared.dto.TipoUsuarioDTO;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class AdminUsuariosViewD extends Composite implements AdminUsuariosView{

	private static AdminUsuariosViewDUiBinder uiBinder = GWT
			.create(AdminUsuariosViewDUiBinder.class);

	interface AdminUsuariosViewDUiBinder extends
			UiBinder<Widget, AdminUsuariosViewD> {
	}

	@UiField HTML usuarioSeleccionado;
	@UiField Button reestablecerPassButton;
	@UiField Button updateButton;
	@UiField TextBox searchBox;
	@UiField Button clearButton;
	@UiField(provided=true) CellList<UserDTO> usuariosList;
	@UiField Label nombreLabel;
	@UiField Label rutLabel;
	@UiField ListBox tipoBox;
	@UiField Label emplazamientoLabel;
	@UiField ListBox emplazamientoBox;
	
	private AdminUsuariosPresenter presenter;
	private UsuarioCell cell;
	private SingleSelectionModel<UserDTO> selectionModel;
	
	public AdminUsuariosViewD() {
		cell = new UsuarioCell();
		usuariosList = new CellList<UserDTO>(cell);
		initWidget(uiBinder.createAndBindUi(this));
		selectionModel = new SingleSelectionModel<UserDTO>(UserDTO.KEY_PROVIDER);
		usuariosList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				presenter.onSelectUser(selectionModel.getSelectedObject());
			}
		});
		
	}
	
	@UiHandler("reestablecerPassButton")
	void onResetPasswordClick(ClickEvent event){
		presenter.onResetPasswordClick();
	}
	
	@UiHandler("updateButton")
	void onUpdateUsuarioClick(ClickEvent event){
		presenter.onUpdateUsuario();
	}
	
	@UiHandler("clearButton")
	void onClearSearchClick(ClickEvent event){
		presenter.onClearSearchClick();
	}
	
	@UiHandler("tipoBox")
	void onChangeTipoUsuario(ChangeEvent event){
		presenter.onTipoUsuarioChange(Integer.parseInt(tipoBox.getValue(tipoBox.getSelectedIndex())));
	}
	
	@UiHandler("emplazamientoBox")
	void onChangeEmplazamiento(ChangeEvent event){
		presenter.onEmplazamientoChange(Integer.parseInt(emplazamientoBox.getValue(emplazamientoBox.getSelectedIndex())));
	}
	
	@UiHandler("searchBox")
	void onChangeSearchString(KeyUpEvent event){
		presenter.onSearchValueChange(searchBox.getText());
	}

	@Override
	public void setPresenter(AdminUsuariosPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public HasData<UserDTO> getDataDisplay() {
		return usuariosList;
	}

	@Override
	public void setNombre(String nombre) {
		usuarioSeleccionado.setText(nombre);
	}

	@Override
	public void setTiposUsuarios(ArrayList<TipoUsuarioDTO> tiposUsuario) {
		tipoBox.clear();
		for(TipoUsuarioDTO tipo:tiposUsuario){
			tipoBox.addItem(tipo.getTipoUsuario(), tipo.getId()+"");
		}
	}

	@Override
	public void setTipoEmplazamiento(TipoEmplazamientoDTO tipoEmplazamiento) {
		emplazamientoLabel.setText(tipoEmplazamiento.getTipoEmplazamiento());
	}

	@Override
	public void setEmplazamientos(ArrayList<EmplazamientoDTO> emplazamientos) {
		emplazamientoBox.clear();
		for(EmplazamientoDTO emplazamiento:emplazamientos){
			emplazamientoBox.addItem(emplazamiento.getNombre(),emplazamiento.getId()+"");
		}
	}

	@Override
	public void setResetPasswordVisivility(boolean visible) {
		reestablecerPassButton.setVisible(visible);
	}

	@Override
	public void setUpdateUsuarioVisivility(boolean visible) {
		updateButton.setVisible(visible);
	}

}
