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
import com.google.gwt.view.client.CellPreviewEvent;

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
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		super.start(panel, eventBus);
		this.eventBus = eventBus;
		panel.setWidget(view.asWidget());
		permisosModificados = new ArrayList<PermisoDTO>();
		view.getDataDisplay().addCellPreviewHandler(new CellPreviewEvent.Handler<PermisoDTO>(){

			@Override
			public void onCellPreview(CellPreviewEvent<PermisoDTO> event) {
				if(event.getColumn()>2){
					int userPermiso = view.getColumnUserId(event.getColumn());
					if(event.getValue().getIdTiposUsuariosPermitidos().contains(userPermiso)){
						event.getValue().getIdTiposUsuariosPermitidos().remove(userPermiso);
					}else{
						event.getValue().getIdTiposUsuariosPermitidos().add(userPermiso);
					}
				}
				if(!permisosModificados.contains(event.getValue())){
					permisosModificados.add(event.getValue());
				}
			}
		});
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
				view.setTiposUsuarios(result);
				getFactory().getAdministracionService().getPermisos(new SimceCallback<ArrayList<PermisoDTO>>(PermisosActivity.this.eventBus) {

					@Override
					public void success(ArrayList<PermisoDTO> result) {
						view.getDataDisplay().setRowData(0, result);
					}
				});
			}
		});
	}

}
