package com.dreamer8.yosimce.client.administracion.ui;

import java.util.ArrayList;

import com.dreamer8.yosimce.client.SimcePresenter;
import com.dreamer8.yosimce.shared.dto.PermisoDTO;
import com.dreamer8.yosimce.shared.dto.TipoUsuarioDTO;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.view.client.HasData;

public interface PermisosView extends IsWidget {

	void setTiposUsuarios(ArrayList<TipoUsuarioDTO> tiposUsuario);
	
	void setPresenter(PermisosPresenter presenter);
	
	int getColumnUserId(int column);
	
	HasData<PermisoDTO> getDataDisplay();
	
	void setActualizarPermisosVisivility(boolean visible);
	void setActualizarTablaVisivility(boolean visible);
	void setUpdateProgramerVisivility(boolean visible);
	
	void setUpdateTime(String update);
	
	
	public interface PermisosPresenter extends SimcePresenter{
		void onUpdatePermisosClick();
		void onUpdateTablaClick();
		void permisoActualizado(PermisoDTO permiso);
		void onProgramarUpdate(String date);
		boolean hasUpdatePermisos();
		
		void onEnviarCorreosSimceClick();
		void onEnviarCorreosSimceTicClick();
	}
}
