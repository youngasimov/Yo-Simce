package com.dreamer8.yosimce.client.planificacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.CursoSelector;
import com.dreamer8.yosimce.client.MensajeEvent;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.SimceCallback;
import com.dreamer8.yosimce.client.SimcePlace;
import com.dreamer8.yosimce.client.Utils;
import com.dreamer8.yosimce.client.planificacion.ui.AgendarVisitaView;
import com.dreamer8.yosimce.client.planificacion.ui.AgendarVisitaView.AgendarVisitaPresenter;
import com.dreamer8.yosimce.shared.dto.AgendaDTO;
import com.dreamer8.yosimce.shared.dto.AgendaItemDTO;
import com.dreamer8.yosimce.shared.dto.CargoDTO;
import com.dreamer8.yosimce.shared.dto.ContactoDTO;
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
		
		view.setEditarContactoVisivility(Utils.hasPermisos(getPermisos(), "PlanificacionService", "editarContacto"));
		view.setEditarDirectorVisivility(Utils.hasPermisos(getPermisos(), "PlanificacionService", "editarDirector"));
		view.setInformacionVisivility(Utils.hasPermisos(getPermisos(), "GeneralService","getDetalleCurso"));
		
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
					//goTo(new PlanificacionPlace());
					goTo(new SimcePlace());
				}
			});
			selector.show();
		}else{
			
			view.setIdCurso(place.getCursoId());
			if(Utils.hasPermisos(eventBus,getPermisos(), "PlanificacionService", "getContacto")){
				getFactory().getPlanificacionService().getContacto(place.getCursoId(), new SimceCallback<ContactoDTO>(eventBus,false) {
	
					@Override
					public void success(ContactoDTO result) {
						view.setContacto(result);
					}
				});
			}
			
			if(Utils.hasPermisos(eventBus,getPermisos(), "PlanificacionService", "getDirector")){
				getFactory().getPlanificacionService().getDirector(place.getCursoId(), new SimceCallback<ContactoDTO>(eventBus,false) {
	
					@Override
					public void success(ContactoDTO result) {
						view.setDirector(result);
					}
				});
			}
			if(Utils.hasPermisos(eventBus,getPermisos(), "PlanificacionService", "getCargos")){
				getFactory().getPlanificacionService().getCargos(new SimceCallback<ArrayList<CargoDTO>>(eventBus,false) {
	
					@Override
					public void success(ArrayList<CargoDTO> result) {
						view.setCargos(result);
					}
				});
			}
			if(Utils.hasPermisos(eventBus,getPermisos(), "PlanificacionService", "getEstadosAgenda")){
				getFactory().getPlanificacionService().getEstadosAgenda(new SimceCallback<ArrayList<EstadoAgendaDTO>>(eventBus,false) {
	
					@Override
					public void success(ArrayList<EstadoAgendaDTO> result) {
						estados = result;
						view.setEstadosAgenda(result);
					}
				});
			}
			
			if(Utils.hasPermisos(eventBus,getPermisos(), "PlanificacionService", "getAgendaCurso")){
				getFactory().getPlanificacionService().getAgendaCurso(place.getCursoId(), new SimceCallback<AgendaDTO>(eventBus,true) {
					
					@Override
					public void success(AgendaDTO result) {
						agenda = result;
						view.setNombreEstablecimiento(result.getEstablecimiento()+"-"+result.getCurso());
						view.getDataDisplay().setRowCount(result.getItems().size());
						
						Collections.reverse(agenda.getItems());
						
						view.getDataDisplay().setVisibleRange(0,result.getItems().size());
						view.getDataDisplay().setRowData(0, result.getItems());
						
						if(agenda.getItems() != null && !agenda.getItems().isEmpty()){
							view.setUltimoEstado(agenda.getItems().get(0));
						}
					}
				});
			}
		}
	}
	
	@Override
	public void onCambiarCursoClick() {
		selector.show();
	}
	
	@Override
	public void onEditarContacto(final ContactoDTO contacto) {
		if(Utils.hasPermisos(eventBus,getPermisos(), "PlanificacionService", "editarContacto")){
			getFactory().getPlanificacionService().editarContacto(place.getCursoId(),contacto,new SimceCallback<Boolean>(eventBus,true) {
	
				@Override
				public void success(Boolean result) {
					view.setContacto(contacto);
					eventBus.fireEvent(new MensajeEvent("Los datos de contacto se han modificado", MensajeEvent.MSG_OK, true));
				}
			});
		}
	}
	
	@Override
	public void onEditarDirector(final ContactoDTO director) {
		if(Utils.hasPermisos(eventBus,getPermisos(), "PlanificacionService", "editarDirector")){
			getFactory().getPlanificacionService().editarDirector(place.getCursoId(), director, new SimceCallback<Boolean>(eventBus,true) {
	
				@Override
				public void success(Boolean result) {
					view.setDirector(director);
					eventBus.fireEvent(new MensajeEvent("Los datos del director se han modificado", MensajeEvent.MSG_OK, true));
				}
			});
		}
	}
	
	@Override
	public void onModificarAgendaClick() {
		
		if(!agenda.getItems().isEmpty() && agenda.getItems().get(0).getEstado().getId()==view.getIdEstadoAgendaSeleccionado()){
			view.setFocusOnEstado();
			eventBus.fireEvent(new MensajeEvent("El estado debe ser distinto al último estado agendado",MensajeEvent.MSG_WARNING,false));
			return;
		}
		
		if(agenda.getItems()!=null && !agenda.getItems().isEmpty()){
			AgendaItemDTO ai = agenda.getItems().get(0);
			if(!ai.getFecha().equals(view.getFechaHoraSeleccionada()) && (view.getComentario() == null || view.getComentario().isEmpty())){
				view.setFocusOnComment();
				eventBus.fireEvent(new MensajeEvent("Debe ingresar un comentario",MensajeEvent.MSG_WARNING,false));
				return;
			}
		}
		
		AgendaItemDTO aidto = new AgendaItemDTO();
		
		for(EstadoAgendaDTO eadto:estados){
			if(eadto.getId() == view.getIdEstadoAgendaSeleccionado()){
				aidto.setEstado(eadto);
				break;
			}
		}
		aidto.setFecha(view.getFechaHoraSeleccionada());
		
		aidto.setComentario(view.getComentario());
		if(Utils.hasPermisos(eventBus,getPermisos(), "PlanificacionService", "AgendarVisita")){
			getFactory().getPlanificacionService().AgendarVisita(place.getCursoId(), aidto,new SimceCallback<AgendaItemDTO>(eventBus,true) {
	
				@Override
				public void success(AgendaItemDTO result) {
					agenda.getItems().add(0, result);
					view.getDataDisplay().setRowCount(agenda.getItems().size());
					view.getDataDisplay().setVisibleRange(0,agenda.getItems().size());
					view.getDataDisplay().setRowData(0, agenda.getItems());
				}
			});
		}
	}
	
	@Override
	public void onStop() {
		super.onStop();
		view.clear();
		agenda = null;
	}
}
