package com.dreamer8.yosimce.client.planificacion;

import java.util.ArrayList;
import java.util.Collections;
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
	private CursoSelector selector;
	private EventBus eventBus;
	private AgendaDTO agenda;
	
	private ArrayList<EstadoAgendaDTO> estados;
	
	
	public AgendarVisitaActivity(ClientFactory factory,AgendarVisitaPlace place, HashMap<String, ArrayList<String>> permisos) {
		super(factory, place,permisos);
		this.place = place;
		this.view = factory.getAgendarVisitaView();
		view.setPresenter(this);
	}
	
	@Override
	public void init(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view.asWidget());
		this.eventBus = eventBus;
		selector = new CursoSelector(getFactory(),eventBus);
		selector.setOnCursoChangeAction(new Command() {
			
			@Override
			public void execute() {
				AgendarVisitaPlace avp = new AgendarVisitaPlace(AgendarVisitaActivity.this.place.getAplicacionId(),AgendarVisitaActivity.this.place.getNivelId(),AgendarVisitaActivity.this.place.getTipoId(),selector.getSelectedCurso().getId());
				selector.hide();
				AgendarVisitaActivity.this.getFactory().getPlaceController().goTo(avp);
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
					if(result.getEstablecimiento().length()>25){
						view.setNombreEstablecimiento(result.getEstablecimiento().substring(0,12)+"..."+result.getEstablecimiento().substring(result.getEstablecimiento().length()-12)+"-"+result.getCurso());
					}else{
						view.setNombreEstablecimiento(result.getEstablecimiento()+"-"+result.getCurso());
					}
					view.getDataDisplay().setRowCount(result.getItems().size());
					
					Collections.reverse(agenda.getItems());

					view.getDataDisplay().setVisibleRange(0,result.getItems().size());
					view.getDataDisplay().setRowData(0, result.getItems());
				}
			});	
		}
	}
	
	@Override
	public void onCambiarEstablecimientoClick() {
		selector.show();
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
		aidto.setFecha(view.getFechaHoraSeleccionada());
		
		aidto.setComentario(view.getComentario());
		
		getFactory().getPlanificacionService().AgendarVisita(place.getCursoId(), aidto,new SimceCallback<AgendaItemDTO>(eventBus) {

			@Override
			public void success(AgendaItemDTO result) {
				agenda.getItems().add(0, result);
				view.getDataDisplay().setRowCount(agenda.getItems().size());
				view.getDataDisplay().setVisibleRange(0,agenda.getItems().size());
				view.getDataDisplay().setRowData(0, agenda.getItems());
			}
		});
	}
	
	@Override
	public void onStop() {
		super.onStop();
		view.getDataDisplay().setRowCount(0);
		view.setNombreEstablecimiento("");
		agenda = null;
	}
}
