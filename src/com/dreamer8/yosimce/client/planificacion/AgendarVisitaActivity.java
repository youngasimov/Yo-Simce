package com.dreamer8.yosimce.client.planificacion;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.planificacion.ui.AgendarVisitaView;
import com.dreamer8.yosimce.client.planificacion.ui.AgendarVisitaView.AgendarVisitaPresenter;
import com.dreamer8.yosimce.shared.dto.AgendaItemDTO;
import com.dreamer8.yosimce.shared.dto.EstadoAgendaDTO;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class AgendarVisitaActivity extends SimceActivity implements
		AgendarVisitaPresenter {
	
	private static AgendarVisitaView view;
	private static AgendarVisitaPlace place;
	
	public AgendarVisitaActivity(ClientFactory factory,AgendarVisitaPlace place, HashMap<String, ArrayList<String>> permisos) {
		super(factory, permisos);
		this.place = place;
		this.view = factory.getAgendarVisitaView();
		view.setPresenter(this);
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view.asWidget());
		
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
