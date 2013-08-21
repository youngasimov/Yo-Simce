package com.dreamer8.yosimce.client.administracion;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.SimceCallback;
import com.dreamer8.yosimce.client.administracion.ui.PermisosView;
import com.dreamer8.yosimce.client.administracion.ui.PermisosView.PermisosPresenter;
import com.dreamer8.yosimce.shared.dto.PermisoDTO;
import com.dreamer8.yosimce.shared.dto.TipoUsuarioDTO;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class PermisosActivity extends SimceActivity implements
		PermisosPresenter {

	
	private PermisosView view;
	private EventBus eventBus;
	private ArrayList<PermisoDTO> permisosModificados;
	
	public PermisosActivity(ClientFactory factory, PermisosPlace place,
			HashMap<String, ArrayList<String>> permisos) {
		super(factory, place, permisos);
		view = getFactory().getPermisosView();
		view.setPresenter(this);
	}
	
	@Override
	public void init(AcceptsOneWidget panel, EventBus eventBus) {
		this.eventBus = eventBus;
		panel.setWidget(view.asWidget());
		permisosModificados = new ArrayList<PermisoDTO>();
		updateTable();
	}

	@Override
	public void onUpdatePermisosClick() {
		getFactory().getAdministracionService().setPermisos(permisosModificados, new SimceCallback<Boolean>(eventBus) {

			@Override
			public void success(Boolean result) {
				permisosModificados.clear();
			}
		});
	}

	@Override
	public void onUpdateTablaClick() {
		updateTable();
	}
	
	private void updateTable(){
		
		getFactory().getAdministracionService().getTiposUsuario(new SimceCallback<ArrayList<TipoUsuarioDTO>>(this.eventBus) {
			
			@Override
			public void success(ArrayList<TipoUsuarioDTO> result) {
				permisosModificados.clear();
				view.setTiposUsuarios(result);
				getFactory().getAdministracionService().getPermisos(new SimceCallback<ArrayList<PermisoDTO>>(PermisosActivity.this.eventBus) {

					@Override
					public void success(ArrayList<PermisoDTO> result) {
						view.getDataDisplay().setRowCount(result.size());
						view.getDataDisplay().setVisibleRange(0,result.size());
						view.getDataDisplay().setRowData(0, result);
					}
				});
			}
		});
	}

	@Override
	public void permisoActualizado(PermisoDTO permiso) {
		if(!permisosModificados.contains(permiso)){
			permisosModificados.add(permiso);
		}
	}

}
