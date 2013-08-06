package com.dreamer8.yosimce.client.administracion;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.SimceCallback;
import com.dreamer8.yosimce.client.administracion.ui.AdminUsuariosView;
import com.dreamer8.yosimce.client.administracion.ui.AdminUsuariosView.AdminUsuariosPresenter;
import com.dreamer8.yosimce.shared.dto.TipoUsuarioDTO;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.view.client.RangeChangeEvent;

public class AdminUsuariosActivity extends SimceActivity implements
		AdminUsuariosPresenter {

	
	private AdminUsuariosView view;
	private AdminUsuariosPlace place;
	private String filtro;
	private int offset;
	private int length;
	private EventBus eventBus;
	private UserDTO user;
	private ArrayList<TipoUsuarioDTO> tiposUsuario;
	
	public AdminUsuariosActivity(ClientFactory factory, AdminUsuariosPlace place,
			HashMap<String, ArrayList<String>> permisos) {
		super(factory, place, permisos);
		this.place = place;
		this.view = getFactory().getAdminUsuariosView();
		this.view.setPresenter(this);
		length = 20;
		filtro = "";
		offset = 0;
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		super.start(panel, eventBus);
		this.eventBus = eventBus;
		panel.setWidget(view.asWidget());
		
		view.setResetPasswordVisivility(false);
		view.setUpdateUsuarioVisivility(false);
		
		this.view.getDataDisplay().addRangeChangeHandler(new RangeChangeEvent.Handler() {
			
			@Override
			public void onRangeChange(RangeChangeEvent event) {
				offset = event.getNewRange().getStart();
				length = event.getNewRange().getLength();
				updateUsuarios();
			}
		});
		updateUsuarios();
	}

	@Override
	public void onSelectUser(UserDTO user) {
		this.user = user;
		view.setNombre(user.getNombres()+" "+user.getApellidoPaterno()+" "+user.getApellidoMaterno());
		view.setResetPasswordVisivility(true);
		view.setUpdateUsuarioVisivility(true);
		
		getFactory().getAdministracionService().getTiposUsuario(new SimceCallback<ArrayList<TipoUsuarioDTO>>(eventBus) {

			@Override
			public void success(ArrayList<TipoUsuarioDTO> result) {
				tiposUsuario = result;
				view.setTiposUsuarios(tiposUsuario);
			}
		});
	}

	@Override
	public void onSearchValueChange(String search) {
		filtro = search;
		offset = 0;
		updateUsuarios();
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
	
	private void updateUsuarios(){
		getFactory().getAdministracionService().getUsuarios(filtro, offset, length, new SimceCallback<ArrayList<UserDTO>>(eventBus) {

			@Override
			public void success(ArrayList<UserDTO> result) {
				view.getDataDisplay().setRowData(offset, result);
			}
		});
	}

}
