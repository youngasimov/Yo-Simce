package com.dreamer8.yosimce.client.administracion;

import java.util.ArrayList;
import java.util.HashMap;
import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.SimceCallback;
import com.dreamer8.yosimce.client.administracion.ui.AdminUsuariosView;
import com.dreamer8.yosimce.client.administracion.ui.AdminUsuariosView.AdminUsuariosPresenter;
import com.dreamer8.yosimce.shared.dto.EmplazamientoDTO;
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
	private ArrayList<EmplazamientoDTO> emplazamientos;
	private int tipoUsuarioSelected;
	private EmplazamientoDTO emplazamientoSelected;
	
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
		view.setPanelVisivility(false);
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
				view.setPanelVisivility(true);
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
		filtro = "";
		offset = 0;
		updateUsuarios();
	}

	@Override
	public void onResetPasswordClick() {
		if(user==null){return;}
		getFactory().getAdministracionService().reiniciarPassword(user.getId(), new SimceCallback<Boolean>(eventBus) {

			@Override
			public void success(Boolean result) {
				
			}
		});
	}

	@Override
	public void onUpdateUsuario() {
		if(user!=null && tipoUsuarioSelected!=user.getTipo().getId() && emplazamientoSelected!=null){
			getFactory().getAdministracionService().setPerfilUsuario(user.getId(), tipoUsuarioSelected, emplazamientoSelected, new SimceCallback<Boolean>(eventBus) {

				@Override
				public void success(Boolean result) {
					
				}
			});
		}
	}

	@Override
	public void onTipoUsuarioChange(Integer tipousuarioId) {
		tipoUsuarioSelected = tipousuarioId;
		for (TipoUsuarioDTO t: tiposUsuario) {
			if(t.getId() == tipoUsuarioSelected){
				getFactory().getAdministracionService().getEmplazamientos(t.getTipoEmplazamientoAsociado(), new SimceCallback<ArrayList<EmplazamientoDTO>>(eventBus) {

					@Override
					public void success(ArrayList<EmplazamientoDTO> result) {
						emplazamientos = result;
						view.setEmplazamientos(result);
					}
				});
				return;
			}
		}
		
		
		
	}

	@Override
	public void onEmplazamientoChange(Integer emplazamientoId) {
		for(EmplazamientoDTO e:emplazamientos){
			if(e.getId() == emplazamientoId){
				emplazamientoSelected = e;
				return;
			}
		}
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
