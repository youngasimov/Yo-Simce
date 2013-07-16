package com.dreamer8.yosimce.client.planandresult.activity;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.planandresult.ui.PlanAndResultHeaderView;
import com.dreamer8.yosimce.shared.dto.ActividadTipoDTO;
import com.dreamer8.yosimce.shared.dto.AplicacionDTO;
import com.dreamer8.yosimce.shared.dto.NivelDTO;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class PlansAndResultHeaderActivity extends AbstractActivity implements PlanAndResultHeaderView.Presenter{

	private ClientFactory factory;
	private PlanAndResultPlace place;
	private PlanAndResultHeaderView view;
	private UserDTO user;
	
	public PlansAndResultHeaderActivity(PlanAndResultPlace place, ClientFactory factory, UserDTO user){
		this.factory = factory;
		this.place = place;
		this.view = factory.getPlanAndResultHeaderView();
		view.setPresenter(this);
		this.user = user;
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view.asWidget());
	}


	@Override
	public void onExportClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAplicacionChange(AplicacionDTO aplicacion) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNivelChange(NivelDTO nivel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTipoChange(ActividadTipoDTO tipo) {
		// TODO Auto-generated method stub
		
	}

}
