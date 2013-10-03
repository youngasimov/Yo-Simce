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
import com.google.gwt.i18n.client.DateTimeFormat;
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
	private DateTimeFormat format;
	
	
	public AgendarVisitaActivity(ClientFactory factory,AgendarVisitaPlace place, HashMap<String, ArrayList<String>> permisos) {
		super(factory, place,permisos);
		this.place = place;
		this.view = factory.getAgendarVisitaView();
		view.setPresenter(this);
		format =DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_TIME_MEDIUM);
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
		if(contacto.getContactoEmail()!=null && !contacto.getContactoEmail().isEmpty() && !contacto.getContactoEmail().trim().matches("^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,3})$")){
			eventBus.fireEvent(new MensajeEvent("El mail ingresado no es válido", MensajeEvent.MSG_WARNING, true));
			return;
		}
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
		if(director.getContactoEmail()!=null && !director.getContactoEmail().isEmpty() && !director.getContactoEmail().trim().matches("^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,3})$")){
			eventBus.fireEvent(new MensajeEvent("El mail ingresado no es válido", MensajeEvent.MSG_WARNING, true));
			return;
		}
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
		
		AgendaItemDTO last = null;
		if(!agenda.getItems().isEmpty()){
			last = agenda.getItems().get(0);
		}
		
		AgendaItemDTO aidto = new AgendaItemDTO();
		for(EstadoAgendaDTO eadto:estados){
			if(eadto.getId() == view.getIdEstadoAgendaSeleccionado()){
				aidto.setEstado(eadto);
				break;
			}
		}
		if(view.getFechaHoraSeleccionada() !=null){
			aidto.setFecha(format.format(view.getFechaHoraSeleccionada()));
		}
		aidto.setComentario(view.getComentario());
		
		
		
		
		//Si no Existe información, se permite agregar un inte sin complicaciones
		if(last == null || last.getEstado().getEstado().equals(EstadoAgendaDTO.SIN_INFORMACION)){
			if(aidto.getComentario() == null || aidto.getComentario().isEmpty()){
				aidto.setComentario("Planificación inicial");
			}
			updateAgenda(aidto);
			return;
		}
		
		if(!last.getEstado().getEstado().equals(EstadoAgendaDTO.POR_CONFIRMAR) &&
				!last.getEstado().getEstado().equals(EstadoAgendaDTO.SIN_INFORMACION) &&
				(aidto.getComentario() == null || aidto.getComentario().isEmpty())){
			eventBus.fireEvent(new MensajeEvent("Si trata de cambiar una agenda ya confirmada, debe ingresar un comentario del cambio",MensajeEvent.MSG_WARNING,true));
			return;
		}
		
		//Si se esta intentando registrar otro item de agenda, con el mismo estado, se exige un comentario
		if(last.getEstado().getId() == aidto.getEstado().getId() && (aidto.getComentario() == null || aidto.getComentario().isEmpty())){
			eventBus.fireEvent(new MensajeEvent("Debe ingresar un comentario",MensajeEvent.MSG_WARNING,true));
			return;
		}
		
		//Si se cambia el estado y la fecha, se debe ingresar un comentario
		if(last.getEstado().getId() != aidto.getEstado().getId() &&
				!last.getFecha().equals(aidto.getFecha()) &&
				(aidto.getComentario() == null ||
				aidto.getComentario().isEmpty())){
			eventBus.fireEvent(new MensajeEvent("Debe ingresar un comentario que justifique el cambio de fecha",MensajeEvent.MSG_WARNING,false));
			return;
		}
		
		//Si el estado es 'por confirmar', se permite modificar la agenda simpre que tenga un comentario
		if(aidto.getEstado().getEstado().equals(EstadoAgendaDTO.POR_CONFIRMAR) && aidto.getComentario() != null && !aidto.getComentario().isEmpty()){
			updateAgenda(aidto);
			return;
		}
		
		//Si el estado es 'confirmado' o posterior, se permite modificar la agenda simpre que tenga un comentario
		if(!last.getEstado().getEstado().equals(EstadoAgendaDTO.POR_CONFIRMAR) &&
				!last.getEstado().getEstado().equals(EstadoAgendaDTO.SIN_INFORMACION) &&
				aidto.getComentario() != null && !aidto.getComentario().isEmpty()){
			updateAgenda(aidto);
			return;
		}
		
		//Si el estado y la fecha no se han modificado, y hay un comentario, se permite agendar
		if(last.getEstado().getId() == aidto.getEstado().getId() &&
				last.getFecha().equals(aidto.getFecha()) &&
				aidto.getComentario() != null &&
				!aidto.getComentario().isEmpty()){
			updateAgenda(aidto);
			return;
		}
		//si el estado anterior es distinto al actual, se permite modificar la agenda
		if(last.getEstado().getId() != aidto.getEstado().getId()){
			updateAgenda(aidto);
			return;
		}
	}
	
	@Override
	public void onStop() {
		super.onStop();
		view.clear();
		agenda = null;
	}
	
	private void updateAgenda(AgendaItemDTO aidto){
		if(Utils.hasPermisos(eventBus,getPermisos(), "PlanificacionService", "AgendarVisita")){
			getFactory().getPlanificacionService().AgendarVisita(place.getCursoId(), aidto,new SimceCallback<AgendaItemDTO>(eventBus,true) {
	
				@Override
				public void success(AgendaItemDTO result) {
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
			});
		}
	}
}
