package com.dreamer8.yosimce.client.planificacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.CursoSelector;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.SimceCallback;
import com.dreamer8.yosimce.client.planificacion.ui.AgendarVisitaView;
import com.dreamer8.yosimce.client.planificacion.ui.AgendarVisitaView.AgendarVisitaPresenter;
import com.dreamer8.yosimce.shared.dto.AgendaDTO;
import com.dreamer8.yosimce.shared.dto.AgendaItemDTO;
import com.dreamer8.yosimce.shared.dto.EstadoAgendaDTO;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class AgendarVisitaActivity extends SimceActivity implements
		AgendarVisitaPresenter {
	
	private final AgendarVisitaView view;
	private final AgendarVisitaPlace place;
	private final CursoSelector selector;
	private EventBus eventBus;
	private AgendaDTO agenda;
	
	private ArrayList<EstadoAgendaDTO> estados;
	
	
	public AgendarVisitaActivity(ClientFactory factory,AgendarVisitaPlace place, HashMap<String, ArrayList<String>> permisos) {
		super(factory, place,permisos);
		this.place = place;
		this.view = factory.getAgendarVisitaView();
		view.setPresenter(this);
		selector = new CursoSelector(factory);
		selector.setOnEstablecimientoChangeAction(new Command() {
			
			@Override
			public void execute() {
				AgendarVisitaPlace avp = new AgendarVisitaPlace(AgendarVisitaActivity.this.place.getAplicacionId(),AgendarVisitaActivity.this.place.getNivelId(),AgendarVisitaActivity.this.place.getTipoId(),selector.getSelectedEstablecimiento().getId());
				AgendarVisitaActivity.this.getFactory().getPlaceController().goTo(avp);
			}
		});
	}
	
	@Override
	public void onCambiarEstablecimientoClick() {
		selector.setCancelable(true);
		selector.setGlassEnabled(false);
		selector.showRelativeTo(view.getCambiarButton());
	}
	
	@Override
	public void onModificarAgendaClick() {
		AgendaItemDTO aidto = new AgendaItemDTO();
		
		for(EstadoAgendaDTO eadto:estados){
			if(eadto.getId() == view.getIdEstadoAgendaSeleccionado()){
				aidto.setEstado(eadto);
				break;
			}
		}
		
		getFactory().getPlanificacionService().AgendarVisita(place.getCursoId(), aidto,new SimceCallback<AgendaItemDTO>(eventBus) {

			@Override
			public void success(AgendaItemDTO result) {
				agenda.getItems().add(0, result);
				view.getDataDisplay().setRowData(0, agenda.getItems());
				view.getDataDisplay().setVisibleRange(0,agenda.getItems().size());
			}
		});
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		super.start(panel,eventBus);
		panel.setWidget(view.asWidget());
		this.eventBus = eventBus;
		if(place.getCursoId()<0){
			selector.setOnCancelAction(new Command() {
				
				@Override
				public void execute() {
					AgendarVisitaActivity.this.getFactory().getPlaceController().goTo(new PlanificacionPlace(AgendarVisitaActivity.this.place.getAplicacionId(),AgendarVisitaActivity.this.place.getNivelId(),AgendarVisitaActivity.this.place.getTipoId()));
				}
			});
			selector.setCancelable(true);
			selector.setGlassEnabled(true);
			selector.show();
		}else{
			view.setIdCurso(place.getCursoId());
			
			getFactory().getPlanificacionService().getEstadosAgenda(new SimceCallback<ArrayList<EstadoAgendaDTO>>(eventBus) {

				@Override
				public void success(ArrayList<EstadoAgendaDTO> result) {
					estados = result;
					view.setEstadosAgenda(result);
				}
			});
			
			getFactory().getPlanificacionService().getAgendaCurso(place.getCursoId(), new SimceCallback<AgendaDTO>(eventBus) {

				@Override
				public void success(AgendaDTO result) {
					agenda = result;
					view.setNombreEstablecimiento(result.getEstablecimiento()+"-"+result.getCurso());
					view.getDataDisplay().setRowCount(result.getItems().size());
					
					Collections.sort(result.getItems(), new Comparator<AgendaItemDTO>(){

						@Override
						public int compare(AgendaItemDTO arg0,AgendaItemDTO arg1) {
							return arg1.getFecha().compareTo(arg0.getFecha());
						}
					});
					view.getDataDisplay().setRowData(0, result.getItems());
					view.getDataDisplay().setVisibleRange(0,result.getItems().size());
				}
			});
			
		}		
	}
	
	@Override
	public void onStop() {
		super.onStop();
		view.getDataDisplay().setRowCount(0);
		view.setNombreEstablecimiento("");
		agenda = null;
	}
}
