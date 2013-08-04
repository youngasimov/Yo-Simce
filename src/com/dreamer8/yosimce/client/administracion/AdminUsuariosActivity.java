package com.dreamer8.yosimce.client.administracion;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.administracion.ui.AdminUsuariosView;
import com.dreamer8.yosimce.client.administracion.ui.AdminUsuariosView.AdminUsuariosPresenter;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class AdminUsuariosActivity extends SimceActivity implements
		AdminUsuariosPresenter {

	
	private AdminUsuariosView view;
	private AdminUsuariosPlace place;
	
	public AdminUsuariosActivity(ClientFactory factory, AdminUsuariosPlace place,
			HashMap<String, ArrayList<String>> permisos) {
		super(factory, place, permisos);
		this.place = place;
		this.view = getFactory().getAdminUsuariosView();
		this.view.setPresenter(this);
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		super.start(panel, eventBus);
		panel.setWidget(view.asWidget());
	}

	@Override
	public void onSelectUser(UserDTO user) {
		
	}

	@Override
	public void onSearchValueChange(String search) {
		
	}

	@Override
	public void onClearSearchClick() {
		
	}

	@Override
	public void onResetPasswordClick() {
		
	}

	@Override
	public void onUpdateUsuario() {
		
	}

	@Override
	public void onTipoUsuarioChange(Integer tipousuarioId) {
		
	}

	@Override
	public void onEmplazamientoChange(Integer emplazamientoId) {
		
	}

}
