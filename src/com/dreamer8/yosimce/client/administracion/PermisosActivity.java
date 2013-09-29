package com.dreamer8.yosimce.client.administracion;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.MensajeEvent;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.SimceCallback;
import com.dreamer8.yosimce.client.Utils;
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
		view.setActualizarPermisosVisivility(Utils.hasPermisos(eventBus,getPermisos(), "AdministracionService", "setPermisos"));
		view.setActualizarTablaVisivility(Utils.hasPermisos(eventBus,getPermisos(), "AdministracionService", "getTiposUsuario") && Utils.hasPermisos(eventBus,getPermisos(), "AdministracionService", "getPermisos"));
		updateTable();
		if(Utils.hasPermisos(getPermisos(), "AdministracionService", "setPermisos")){
			view.setUpdateProgramerVisivility(true);
			getFactory().getLoginService().getActualizacionDate(new SimceCallback<String>(eventBus) {

				@Override
				public void success(String result) {
					view.setUpdateTime(result);
				}
			});
		}else{
			view.setUpdateProgramerVisivility(false);
		}
	}

	@Override
	public void onUpdatePermisosClick() {
		if(Utils.hasPermisos(eventBus,getPermisos(), "AdministracionService", "setPermisos")){
			getFactory().getAdministracionService().setPermisos(permisosModificados, new SimceCallback<Boolean>(eventBus,true) {
	
				@Override
				public void success(Boolean result) {
					permisosModificados.clear();
					eventBus.fireEvent(new MensajeEvent("Permisos actualizados",MensajeEvent.MSG_OK,true));
				}
			});
		}
	}

	@Override
	public void onUpdateTablaClick() {
		updateTable();
	}
	
	@Override
	public boolean hasUpdatePermisos() {
		return Utils.hasPermisos(getPermisos(), "AdministracionService", "setPermisos");
	}
	
	private void updateTable(){
		if(Utils.hasPermisos(eventBus,getPermisos(), "AdministracionService", "getTiposUsuario")){
			getFactory().getAdministracionService().getTiposUsuario(new SimceCallback<ArrayList<TipoUsuarioDTO>>(this.eventBus,true) {
				
				@Override
				public void success(ArrayList<TipoUsuarioDTO> result) {
					permisosModificados.clear();
					view.setTiposUsuarios(result);
					if(Utils.hasPermisos(eventBus,getPermisos(), "AdministracionService", "getPermisos")){
						getFactory().getAdministracionService().getPermisos(new SimceCallback<ArrayList<PermisoDTO>>(PermisosActivity.this.eventBus,true) {
		
							@Override
							public void success(ArrayList<PermisoDTO> result) {
								view.getDataDisplay().setRowCount(result.size());
								view.getDataDisplay().setVisibleRange(0,result.size());
								view.getDataDisplay().setRowData(0, result);
							}
						});
					}
				}
			});
		}
	}

	@Override
	public void permisoActualizado(PermisoDTO permiso) {
		if(!permisosModificados.contains(permiso)){
			permisosModificados.add(permiso);
		}
	}
	
	@Override
	public void onProgramarUpdate(String date) {
		if(Utils.hasPermisos(getPermisos(), "AdministracionService", "setPermisos")){
			if(date == null || date.isEmpty()){
				getFactory().getLoginService().setActualizacionDate(null, new SimceCallback<Boolean>(eventBus,true) {
	
					@Override
					public void success(Boolean result) {
						eventBus.fireEvent(new MensajeEvent("La actualización del sistema se ha cancelado",MensajeEvent.MSG_OK,true));
					}
				});
			}else{
				getFactory().getLoginService().setActualizacionDate(date, new SimceCallback<Boolean>(eventBus,true) {
	
					@Override
					public void success(Boolean result) {
						eventBus.fireEvent(new MensajeEvent("LA fecha de actualización del sistema se ha actualizado con éxito",MensajeEvent.MSG_OK,true));
					}
				});
			}
		}
	}

}
