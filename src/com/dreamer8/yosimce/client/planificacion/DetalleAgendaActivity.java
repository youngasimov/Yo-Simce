package com.dreamer8.yosimce.client.planificacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.CursoSelector;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.SimceCallback;
import com.dreamer8.yosimce.client.planificacion.ui.DetalleAgendaView;
import com.dreamer8.yosimce.client.planificacion.ui.DetalleAgendaView.DetalleAgendaPresenter;
import com.dreamer8.yosimce.shared.dto.AgendaDTO;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class DetalleAgendaActivity extends SimceActivity
		implements DetalleAgendaPresenter {

	
	private final DetalleAgendaPlace place;
	private final DetalleAgendaView view;
	private CursoSelector selector;
	private AgendaDTO agenda;
	
	public DetalleAgendaActivity(ClientFactory factory, DetalleAgendaPlace place,HashMap<String, ArrayList<String>> permisos) {
		super(factory, place,permisos);
		this.place = place;
		this.view = factory.getDetalleAgendaView();
		this.view.setPresenter(this);
	}
	
	@Override
	public void init(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view.asWidget());
		selector = new CursoSelector(getFactory(),eventBus);
		selector.setOnCursoChangeAction(new Command() {
			
			@Override
			public void execute() {
				DetalleAgendaPlace avp = new DetalleAgendaPlace(DetalleAgendaActivity.this.place.getAplicacionId(),DetalleAgendaActivity.this.place.getNivelId(),DetalleAgendaActivity.this.place.getTipoId(),selector.getSelectedCurso().getId());
				selector.hide();
				DetalleAgendaActivity.this.getFactory().getPlaceController().goTo(avp);
			}
		});
		
		if(place.getCursoId()<0){
			selector.setOnCancelAction(new Command() {
				
				@Override
				public void execute() {
					goTo(new PlanificacionPlace());
				}
			});
			selector.show();
		}else{
			view.setIdCurso(place.getCursoId());
			
			getFactory().getPlanificacionService().getAgendaCurso(place.getCursoId(), new SimceCallback<AgendaDTO>(eventBus) {
				
				@Override
				public void success(AgendaDTO result) {
					agenda = result;
					view.setNombreEstablecimiento(result.getEstablecimiento()+"-"+result.getCurso());
					view.getDataDisplay().setRowCount(result.getItems().size());
					
					Collections.reverse(agenda.getItems());

					view.getDataDisplay().setVisibleRange(0,result.getItems().size());
					view.getDataDisplay().setRowData(0, result.getItems());
				}
			});	
		}
	}

	@Override
	public void onCambiarCursoClick() {
		selector.show();
	}
	
	@Override
	public void onStop() {
		super.onStop();
		view.getDataDisplay().setRowCount(0);
		view.setNombreEstablecimiento("");
		agenda = null;
	}
}
