package com.dreamer8.yosimce.client.administracion.ui;

import java.util.ArrayList;

import com.dreamer8.yosimce.client.SimcePresenter;
import com.dreamer8.yosimce.shared.dto.EmplazamientoDTO;
import com.dreamer8.yosimce.shared.dto.TipoUsuarioDTO;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.view.client.HasData;

public interface AdminUsuariosView extends IsWidget {

	void setPresenter(AdminUsuariosPresenter presenter);
	
	HasData<UserDTO> getDataDisplay();
	void setNombre(String nombre);
	void setTiposUsuarios(ArrayList<TipoUsuarioDTO> tiposUsuario);
	
	void selectTipoUsuario(TipoUsuarioDTO tipoUsuario);
	
	void setEmplazamientos(ArrayList<EmplazamientoDTO> emplazamientos);
	void setResetPasswordVisivility(boolean visible);
	void setUpdateUsuarioVisivility(boolean visible);
	void setPanelVisivility(boolean visible);
	void setTiposUsuarioBoxVisivility(boolean visible);
	void setEmplazamientoBoxVisivility(boolean visible);
	
	
	public interface AdminUsuariosPresenter extends SimcePresenter{
		
		void onSelectUser(UserDTO user);
		void onSearchValueChange(String search);
		void onClearSearchClick();
		void onResetPasswordClick();
		void onUpdateUsuario();
		
		void onTipoUsuarioChange(Integer tipousuarioId);
		void onEmplazamientoChange(Integer emplazamientoId);
		
	}
}
