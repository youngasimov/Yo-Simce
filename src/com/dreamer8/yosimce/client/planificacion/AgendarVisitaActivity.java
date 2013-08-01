package com.dreamer8.yosimce.client.planificacion;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.CursoSelector;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.planificacion.ui.AgendarVisitaView;
import com.dreamer8.yosimce.client.planificacion.ui.AgendarVisitaView.AgendarVisitaPresenter;
import com.dreamer8.yosimce.shared.dto.AgendaItemDTO;
import com.dreamer8.yosimce.shared.dto.EstadoAgendaDTO;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class AgendarVisitaActivity extends SimceActivity implements
		AgendarVisitaPresenter {
	
	private final AgendarVisitaView view;
	private final AgendarVisitaPlace place;
	private final CursoSelector selector;
	
	public AgendarVisitaActivity(ClientFactory factory,AgendarVisitaPlace place, HashMap<String, ArrayList<String>> permisos) {
		super(factory, permisos);
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
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		super.start(panel,eventBus);
		panel.setWidget(view.asWidget());
		
		if(place.getEstablecimientoId()<0){
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
			
		
		
		if(getFactory().onTesting()){
			
			view.setNombreEstablecimiento("Colegio Carmelitas Descalsas");
			
			ArrayList<AgendaItemDTO> items = new ArrayList<AgendaItemDTO>();
			AgendaItemDTO ai = new AgendaItemDTO();
			UserDTO u = new UserDTO();
			u.setNombres("Camilo");
			u.setApellidoPaterno("Vera");
			ai.setCreador(u);
			ai.setFecha(new Date());
			EstadoAgendaDTO ea = new EstadoAgendaDTO();
			ea.setEstado("Realizado");
			ea.setId(345);
			ai.setEstado(ea);
			ai.setComentario("");
			items.add(ai);
			
			ai = new AgendaItemDTO();
			u = new UserDTO();
			u.setNombres("Camilo");
			u.setApellidoPaterno("Vera");
			ai.setCreador(u);
			ai.setFecha(new Date());
			ea = new EstadoAgendaDTO();
			ea.setEstado("Confirmado con cambios");
			ea.setId(6457);
			ai.setEstado(ea);
			ai.setComentario("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula.");
			items.add(ai);
			
			ai = new AgendaItemDTO();
			u = new UserDTO();
			u.setNombres("Camilo");
			u.setApellidoPaterno("Vera");
			ai.setCreador(u);
			ai.setFecha(new Date());
			ea = new EstadoAgendaDTO();
			ea.setEstado("Confirmado");
			ea.setId(947);
			ai.setEstado(ea);
			ai.setComentario("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula.");
			items.add(ai);
			
			ai = new AgendaItemDTO();
			u = new UserDTO();
			u.setNombres("Camilo");
			u.setApellidoPaterno("Vera");
			ai.setCreador(u);
			ai.setFecha(new Date());
			ea = new EstadoAgendaDTO();
			ea.setEstado("Por confirmar");
			ea.setId(764);
			ai.setEstado(ea);
			ai.setComentario("Quiere la boca exhausta vid, kiwi, piña y fugaz jamón. Fabio me exige, sin tapuj");
			items.add(ai);
			view.getDataDisplay().setRowData(0, items);
		}
		}		
	}
	
	@Override
	public void onStop() {
		super.onStop();
		view.getDataDisplay().setRowCount(0);
		view.setNombreEstablecimiento("");
	}
}
