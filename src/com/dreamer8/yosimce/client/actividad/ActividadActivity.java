package com.dreamer8.yosimce.client.actividad;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.actividad.ui.ActividadView;
import com.dreamer8.yosimce.client.actividad.ui.ActividadView.ActividadPresenter;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class ActividadActivity extends SimceActivity implements
		ActividadPresenter {

	private final ActividadView view;
	
	public ActividadActivity(ClientFactory factory, ActividadPlace place,HashMap<String, ArrayList<String>> permisos) {
		super(factory, place, permisos);
		
		view = getFactory().getActividadView();
		view.setPresenter(this);
	}
	
	@Override
	public void init(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view.asWidget());
		
		view.setActividadesVisivility(getPermisos().get("ActividadService").contains("getTotalPreviewActividades") && getPermisos().get("ActividadService").contains("getPreviewActividades"));
		view.setFormActividadVisivility(getPermisos().get("ActividadService").contains("getActividad"));
		view.setSincronizacionVisivility(getPermisos().get("ActividadService").contains("getSincronizacionesCurso"));
		view.setMaterialDefectuosoVisivility(getPermisos().get("ActividadService").contains("getMaterialDefectuoso"));
		view.setAprobarSupervisoresVisivility(getPermisos().get("ActividadService").contains("getEvaluacionSupervisores"));
	}

}
