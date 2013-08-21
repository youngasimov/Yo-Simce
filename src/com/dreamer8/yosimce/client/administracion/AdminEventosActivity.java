package com.dreamer8.yosimce.client.administracion;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.administracion.ui.AdminEventosView;
import com.dreamer8.yosimce.client.administracion.ui.AdminEventosView.AdminEventosPresenter;
import com.dreamer8.yosimce.shared.dto.ActividadTipoDTO;
import com.dreamer8.yosimce.shared.dto.AplicacionDTO;
import com.dreamer8.yosimce.shared.dto.NivelDTO;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class AdminEventosActivity extends SimceActivity implements
		AdminEventosPresenter {

	private AdminEventosView view;
	private ArrayList<AplicacionDTO> aplicaciones;
	private HashMap<Integer,ArrayList<NivelDTO>> niveles;
	private HashMap<Integer,ArrayList<ActividadTipoDTO>> tipos;
	
	private AplicacionDTO selectedAplicacion;
	private NivelDTO selectedNivel;
	private ActividadTipoDTO selectedTipo;
	
	public AdminEventosActivity(ClientFactory factory, AdminEventosPlace place,HashMap<String, ArrayList<String>> permisos) {
		super(factory, place, permisos);
		view = getFactory().getAdminEventosView();
		view.setPresenter(this);
		aplicaciones = new ArrayList<AplicacionDTO>();
		niveles = new HashMap<Integer, ArrayList<NivelDTO>>();
		tipos = new HashMap<Integer, ArrayList<ActividadTipoDTO>>();
	}
	
	@Override
	public void init(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view.asWidget());
		view.setNuevaAplicacionVisibility(true);
		
		view.setAplicacionesBoxVisivility(false);
		view.setNivelesBoxVisivility(false);
		view.setTiposBoxVisivility(false);
		view.setNuevoNivelVisibility(false);
		view.setNuevoTipoVisibility(false);
		view.setEditarAplicacionVisivility(false);
		view.setEditarNivelVisivility(false);
		view.setEditarTipoVisivility(false);
	}

	@Override
	public void onAplicacionSelected(int idAplicacion) {
		
	}

	@Override
	public void onNivelSelected(int idNivel) {
		
	}

	@Override
	public void onTipoSelected(int idTipo) {
		
	}
}
